package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Rook;

public class ChessMatch {
	private Board board;
	
	public ChessMatch() {
		board = new Board(8,8);
		initialSetup();
		
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
	
	private void initialSetup() {
		placeNewPiece('a', 1, new Rook(board,Color.RED));
		placeNewPiece('b', 1, new Rook(board,Color.RED));
		placeNewPiece('a', 7, new Rook(board,Color.BLUE));
		placeNewPiece('h', 7, new Rook(board,Color.BLUE));
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column,row).toPosition());
	}
	
	public ChessPiece performChessMove(ChessPosition originPos, ChessPosition targetPos) {
		Position source = originPos.toPosition();	
		Position target = targetPos.toPosition();
		validateSourcePosition(source);
		Piece capturedPiece = makeMove(source,target);
		return(ChessPiece)capturedPiece;
	}
	
	private Piece makeMove(Position src, Position target) {
		Piece p = board.removePiece(src);
		Piece capturePiece = board.removePiece(target);
		board.placePiece(p, target);
		return capturePiece;	
	}
	
	private void validateSourcePosition(Position pos) {
		if(!board.thereIsAPiece(pos))
			throw new ChessException("Could not find source piece");
		if(!board.piece(pos).isThereAnyPossibleMoves())
			throw new ChessException("There is no possible moves for this piece.");
	}
	
}
