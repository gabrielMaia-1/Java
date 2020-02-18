package chess;

import boardgame.Position;

public class ChessPosition {
	private int row;
	private char column;
	
	public ChessPosition( char column, int row) {
		if(column < 'a' || column > 'h' || row < 0 || row > 8) {
			throw new ChessException("ChessPosition out of Board " + row + column);
		}
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public char getColumn() {
		return column;
	}
	
	protected Position toPosition() {
		return new Position(8 - row,  column - 'a');
	}

	protected static ChessPosition fromPosition(Position pos) {
		return new ChessPosition((char)(pos.getColumn() + 'a'), 8 - pos.getRow())	;
	}
	
	@Override
	public String toString() {
		return "" + column + row;
	}
}
