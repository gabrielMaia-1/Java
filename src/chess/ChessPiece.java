package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece{
	private Color color;
	protected int moveCount;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}
	
	@Override
	public abstract String toString();
	
	public Color getColor() {
		return color;
	}
	
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}
	protected boolean isThereOpponentChessPiece(Position pos) {
		if(!getBoard().positionExists(pos))
			return false;
		ChessPiece p = (ChessPiece) getBoard().piece(pos);
		return p != null && p.getColor() != this.color;
	}
	
	
	public int getMoveCount() {
		return moveCount;
	}
	
	public void increaseMoveCount() {
		moveCount++;
	}
	
	public void decreaseMoveCount() {
		moveCount--;
	}
}
