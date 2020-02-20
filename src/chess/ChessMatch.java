package chess;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {
	private int turn;
	private Color currentPlayer;
	private Board board;
	private List<ChessPiece> _capturedPieces = new ArrayList<>();
	private List<ChessPiece> _piecesOnTheBoard = new ArrayList<>();
	private ChessPiece enPassantVulnerable = null;
	private ChessPiece promoted;

	private boolean check;
	private boolean checkMate;

	public ChessMatch() {
		board = new Board(8, 8);
		this.turn = 0;
		this.currentPlayer = Color.BLUE;
		initialSetup();

	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] cp = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				cp[i][j] = (ChessPiece) board.piece(i, j);
			}

		}

		return cp;
	}

	public List<ChessPiece> getCapturedList() {
		return _capturedPieces;
	}

	public List<ChessPiece> getPiecesOnTheBoard() {
		return _piecesOnTheBoard;
	}

	public ChessPiece getPromoted() {
		return promoted;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}

	public ChessPiece performChessMove(ChessPosition originPos, ChessPosition targetPos) {
		Position source = originPos.toPosition();
		Position target = targetPos.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);

		ChessPiece cp = (ChessPiece) board.piece(target);
		Position cpPos = cp.getChessPosition().toPosition();

		if (cp instanceof Pawn && (Math.abs(originPos.getRow() - targetPos.getRow())) == 2)
			enPassantVulnerable = cp;
		else
			enPassantVulnerable = null;

		// Promotion
		promoted = null;
		if (cp instanceof Pawn) {
			if (cp.getColor() == Color.BLUE && cpPos.getRow() == 0
					|| cp.getColor() == Color.RED && cpPos.getRow() == 7) {
				promoted = cp;
				promoted = replacePromotedPiece("Q");
			}
		}

		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You cant put yourself in check");
		}

		check = testCheck(opponent(currentPlayer));
		checkMate = testCheckMate(opponent(currentPlayer));
		nextTurn();
		return (ChessPiece) capturedPiece;
	}

	public ChessPiece replacePromotedPiece(String type) {
		if (promoted == null)
			throw new IllegalStateException("There is no piece to Promote");
		if (!type.equals("R") && !type.equals("Q") && !type.equals("H") && !type.equals("B"))
			throw new InvalidParameterException("Invalid type for promotion");

		Position pos = promoted.getChessPosition().toPosition();
		Piece p = board.removePiece(pos);
		_piecesOnTheBoard.remove(p);

		ChessPiece newPiece;
		System.out.println("Going for >> " + type.charAt(0));

		if(type.equals("B"))
				newPiece = new Bishop(board, promoted.getColor());
		else if(type.equals("H"))
			newPiece = new Knight(board, promoted.getColor());
		else if(type.equals("Q"))
			newPiece = new Queen(board, promoted.getColor());
		else
			newPiece = new Rook(board, promoted.getColor());

		board.placePiece(newPiece, pos);
		_piecesOnTheBoard.add(newPiece);
		return newPiece;

	}

	public boolean[][] possibleMoves(ChessPosition pos) {
		Position p = pos.toPosition();
		validateSourcePosition(p);
		return board.piece(p).possibleMoves();
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		_piecesOnTheBoard.add(piece);
	}

	private Piece makeMove(Position src, Position target) {
		ChessPiece p = (ChessPiece) board.removePiece(src);
		Piece capturedPiece = board.removePiece(target);
		p.increaseMoveCount();

		// Castling
		if (p instanceof King) {
			if (target.getColumn() == src.getColumn() + 2)
				makeMove(new Position(src.getRow(), src.getColumn() + 3),
						new Position(src.getRow(), src.getColumn() + 1));
			else if (target.getColumn() == src.getColumn() - 2)
				makeMove(new Position(src.getRow(), src.getColumn() - 4),
						new Position(src.getRow(), src.getColumn() - 1));
		}

		// EnPassant
		if (p instanceof Pawn && src.getColumn() != target.getColumn() && capturedPiece == null) {
			Position p2 = new Position(0, 0);

			if (p.getColor() == Color.BLUE)
				p2.setValues(target.getRow() + 1, target.getColumn());
			else
				p2.setValues(target.getRow() - 1, target.getColumn());

			capturedPiece = board.removePiece(p2);
		}

		if (capturedPiece != null) {
			_piecesOnTheBoard.remove(capturedPiece);
			_capturedPieces.add((ChessPiece) capturedPiece);
		}

		board.placePiece(p, target);

		return capturedPiece;
	}

	private void undoMove(Position origin, Position target, Piece captured) {
		ChessPiece p = (ChessPiece) board.removePiece(target);
		board.placePiece(p, origin);
		p.decreaseMoveCount();

		if (p instanceof Rook) {
			ChessPiece cp1 = (ChessPiece) board.piece(new Position(target.getRow(), target.getColumn() + 1));
			ChessPiece cp2 = (ChessPiece) board.piece(new Position(target.getRow(), target.getColumn() - 1));
			if (target.getColumn() == origin.getColumn() - 2 && cp1 instanceof King)
				undoMove(new Position(target.getRow(), target.getColumn() - 1), cp1.getChessPosition().toPosition(),
						null);
			else if (target.getColumn() == origin.getColumn() + 3 && cp2 instanceof King)
				undoMove(new Position(target.getRow(), target.getColumn() + 1), cp1.getChessPosition().toPosition(),
						null);
		}

		if (p instanceof Pawn && origin.getColumn() != target.getColumn()
				&& (ChessPiece) captured == enPassantVulnerable) {
			if (p.getColor() == Color.BLUE)
				target.setRow(target.getRow() + 1);
			else
				target.setRow(target.getRow() - 1);

		}

		if (captured != null) {
			board.placePiece(captured, target);
			_capturedPieces.remove((ChessPiece) captured);
			_piecesOnTheBoard.add((ChessPiece) captured);
		}

	}

	private boolean testCheckMate(Color c) {
		List<ChessPiece> allys = _piecesOnTheBoard.stream().filter(x -> x.getColor() == c).collect(Collectors.toList());
		for (ChessPiece p : allys) {
			boolean[][] movesList = p.possibleMoves();

			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					if (movesList[i][j]) {
						Position src = p.getChessPosition().toPosition();
						Position trg = new Position(i, j);

						Piece captured = makeMove(src, trg);
						boolean test = testCheck(c);
						undoMove(src, trg, captured);

						if (!test)
							return false;
					}

				}
			}
		}

		return true;

	}

	private Color opponent(Color c) {
		return (c == Color.BLUE) ? Color.RED : Color.BLUE;
	}

	private ChessPiece getKing(Color c) {
		List<ChessPiece> list = _piecesOnTheBoard.stream().filter(x -> (x.getColor() == c && x instanceof King))
				.collect(Collectors.toList());
		return list.get(0);
	}

	private boolean testCheck(Color c) {
		Position kingPosition = getKing(c).getChessPosition().toPosition();
		List<ChessPiece> opponentPieces = _piecesOnTheBoard.stream().filter(x -> x.getColor() == opponent(c))
				.collect(Collectors.toList());
		for (ChessPiece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()])
				return true;
		}
		return false;
	}

	private void validateSourcePosition(Position pos) {
		if (!board.thereIsAPiece(pos))
			throw new ChessException("Could not find source piece");

		if (!board.piece(pos).isThereAnyPossibleMoves())
			throw new ChessException("There is no possible moves for this piece.");

		if (((ChessPiece) board.piece(pos)).getColor() != this.currentPlayer)
			throw new ChessException("The source Piece not belongs to current Player");
	}

	private void validateTargetPosition(Position origin, Position target) {
		if (!board.piece(origin).possibleMove(target))
			throw new ChessException("The chosen piece can't move to target");
	}

	private void nextTurn() {
		this.turn++;
		currentPlayer = (currentPlayer == Color.RED) ? Color.BLUE : Color.RED;
	}

	private void initialSetup() {

		for (char i = 'a'; i < 'i'; i++)
			placeNewPiece(i, 2, new Pawn(board, Color.BLUE, this));
		placeNewPiece('a', 1, new Rook(board, Color.BLUE));
		placeNewPiece('b', 1, new Knight(board, Color.BLUE));
		placeNewPiece('c', 1, new Bishop(board, Color.BLUE));
		placeNewPiece('d', 1, new Queen(board, Color.BLUE));
		placeNewPiece('e', 1, new King(board, Color.BLUE, this));
		placeNewPiece('f', 1, new Bishop(board, Color.BLUE));
		placeNewPiece('g', 1, new Knight(board, Color.BLUE));
		placeNewPiece('h', 1, new Rook(board, Color.BLUE));

		for (char i = 'a'; i < 'i'; i++)
			placeNewPiece(i, 7, new Pawn(board, Color.RED, this));
		placeNewPiece('a', 8, new Rook(board, Color.RED));
		placeNewPiece('b', 8, new Knight(board, Color.RED));
		placeNewPiece('c', 8, new Bishop(board, Color.RED));
		placeNewPiece('d', 8, new Queen(board, Color.RED));
		placeNewPiece('e', 8, new King(board, Color.RED, this));
		placeNewPiece('f', 8, new Bishop(board, Color.RED));
		placeNewPiece('g', 8, new Knight(board, Color.RED));
		placeNewPiece('h', 8, new Rook(board, Color.RED));

	}

}
