package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	private int turn;
	private Color currentPlayer;
	private Board board;
	private List<ChessPiece> _capturedPieces = new ArrayList<>();
	private List<ChessPiece> _piecesOnTheBoard = new ArrayList<>();
	
	private boolean check;
	private boolean checkMate;
	
	public ChessMatch() {
		board = new Board(8,8);
		this.turn = 0;
		this.currentPlayer = Color.BLUE;
		initialSetup();
		
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public ChessPiece[][] getPieces(){
		ChessPiece[][] cp = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				cp[i][j] = (ChessPiece)board.piece(i,j);
			}
			
		}	
		
		return cp;
	}
	
	public ChessPiece performChessMove(ChessPosition originPos, ChessPosition targetPos) {
		Position source = originPos.toPosition();	
		Position target = targetPos.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source,target);
		Piece capturedPiece = makeMove(source,target);
		
		if(testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You cant put yourself in check");
		}
		
		check = testCheck(opponent(currentPlayer));
		checkMate = testCheckMate(opponent(currentPlayer));
		
		nextTurn();
		
		return(ChessPiece)capturedPiece;
	}
	
	public boolean[][] possibleMoves(ChessPosition pos){
		Position p = pos.toPosition();
		validateSourcePosition(p);
		return board.piece(p).possibleMoves();
	}
	
	public List<ChessPiece> getCapturedList(){
		return _capturedPieces;
	}
	
	public List<ChessPiece> getPiecesOnTheBoard(){
		return _piecesOnTheBoard;
	}
	
	public boolean getCheck() {
		return check;
	}
	public boolean getCheckMate() {
		return checkMate;
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column,row).toPosition());
		_piecesOnTheBoard.add(piece);
	}
	
	private Piece makeMove(Position src, Position target) {
		Piece p = board.removePiece(src);
		Piece capturedPiece = board.removePiece(target);
		if(capturedPiece != null) {
			_piecesOnTheBoard.remove(capturedPiece);
			_capturedPieces.add((ChessPiece)capturedPiece);
		}
		board.placePiece(p, target);
		
		return capturedPiece;	
	}
	
	private void undoMove(Position origin, Position target, Piece captured) {
		Piece p = board.removePiece(target);
		board.placePiece(p, origin);
		
		if(captured != null) {
			board.placePiece(captured, target);
			_capturedPieces.remove((ChessPiece)captured);
			_piecesOnTheBoard.add((ChessPiece)captured);
			
		}
	}
	
	private boolean testCheckMate(Color c) {
		List<ChessPiece> allys = _piecesOnTheBoard.stream().filter(x -> x.getColor() == c).collect(Collectors.toList());
		System.out.println(c);
		for(ChessPiece p : allys) {
			boolean [][] movesList = p.possibleMoves();	

			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					if(movesList[i][j]) {
						Position src = p.getChessPosition().toPosition();
						Position trg = new Position(i,j);
						
						Piece captured = makeMove(src, trg);
						boolean test = testCheck(c);
						undoMove(src, trg, captured);
						
						if(!test)
							return false;
					}
					
					
				}
			}
		}
		
		return true;
		
	}
	
	private Color opponent(Color c) {
		return (c == Color.BLUE)? Color.RED : Color.BLUE;
	}
	
	private ChessPiece getKing(Color c) {
		List<ChessPiece> list = _piecesOnTheBoard.stream().filter(x -> (x.getColor() == c && x instanceof King)).collect(Collectors.toList());
		return list.get(0);
	}
	
	private boolean testCheck(Color c) {
		Position kingPosition = getKing(c).getChessPosition().toPosition();
		List<ChessPiece> opponentPieces = _piecesOnTheBoard.stream().filter(x -> x.getColor() == opponent(c)).collect(Collectors.toList());
		for (ChessPiece p : opponentPieces ) {
			boolean[][] mat = p.possibleMoves();
			if(mat[kingPosition.getRow()][kingPosition.getColumn()])
				return true;
		}
		return false;
	}
	
	private void validateSourcePosition(Position pos) {
		if(!board.thereIsAPiece(pos))
			throw new ChessException("Could not find source piece");
		
		if(!board.piece(pos).isThereAnyPossibleMoves())
			throw new ChessException("There is no possible moves for this piece.");
		
		if(((ChessPiece)board.piece(pos)).getColor() != this.currentPlayer)
			throw new ChessException("The source Piece not belongs to current Player");
	}
	
	private void validateTargetPosition(Position origin, Position target) {
		if(!board.piece(origin).possibleMove(target))
			throw new ChessException("The chosen piece can't move to target");
	}
	
	private void nextTurn() {
		this.turn ++;
		currentPlayer = (currentPlayer == Color.RED )?  Color.BLUE : Color.RED;
	}
	
	private void initialSetup() {
		placeNewPiece('h', 7, new Rook(board,Color.BLUE));
		placeNewPiece('d', 1, new Rook(board,Color.BLUE));
		placeNewPiece('e', 1, new King(board,Color.BLUE));
		
		placeNewPiece('b', 8, new Rook(board,Color.RED));
		placeNewPiece('a', 8, new King(board,Color.RED));
	}
	
	
}
