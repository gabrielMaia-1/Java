package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	public King(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "K";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] b = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0, 0);
		
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if(i == 0 && j == 0)
					continue;
				p.setValues(this.position.getRow() + i, this.position.getColumn() + j);
				if (getBoard().positionExists(p)&& canMove(p) )
					b[p.getRow()][p.getColumn()] = true;
			}
		}
		return b;
	}

	private boolean canMove(Position pos) {
		ChessPiece p = (ChessPiece) getBoard().piece(pos);
		return p == null || p.getColor() != this.getColor();
	}

}
