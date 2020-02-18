package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {

	public Rook(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "R";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] b = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0, 0);

		// UP
		p.setValues(this.position.getRow() - 1, this.position.getColumn());
		while (getBoard().positionExists(p)) {
			b[p.getRow()][p.getColumn()] = true;

			if (getBoard().positionExists(p) && isThereOpponentChessPiece(p)) {
				b[p.getRow()][p.getColumn()] = true;
				break;
			}
			
			if (getBoard().positionExists(p) && getBoard().thereIsAPiece(p)) {
				b[p.getRow()][p.getColumn()] = false;
				break;
				}
			
			p.setRow(p.getRow() - 1);
			
		}

		// DOWN
		p.setValues(this.position.getRow() + 1, this.position.getColumn());
		while (getBoard().positionExists(p)) {
			b[p.getRow()][p.getColumn()] = true;

			if (getBoard().positionExists(p) && isThereOpponentChessPiece(p)) {
				b[p.getRow()][p.getColumn()] = true;
				break;
			}
			
			if (getBoard().positionExists(p) && getBoard().thereIsAPiece(p)) {
				b[p.getRow()][p.getColumn()] = false;
				break;
				}
			p.setRow(p.getRow() + 1);
			
		}

		// LEFT
		p.setValues(this.position.getRow(), this.position.getColumn() - 1);
		while (getBoard().positionExists(p)) {
			b[p.getRow()][p.getColumn()] = true;

			if (getBoard().positionExists(p) && isThereOpponentChessPiece(p)) {
				b[p.getRow()][p.getColumn()] = true;
				break;
			}
			
			if (getBoard().positionExists(p) && getBoard().thereIsAPiece(p)) {
				b[p.getRow()][p.getColumn()] = false;
				break;
				}
			p.setColumn(p.getColumn() - 1);
			
		}
		
		// RIGHT
		p.setValues(this.position.getRow(), this.position.getColumn() + 1);
		while (getBoard().positionExists(p)) {
			b[p.getRow()][p.getColumn()] = true;

			if (getBoard().positionExists(p) && isThereOpponentChessPiece(p)) {
				b[p.getRow()][p.getColumn()] = true;
				break;
			}
			p.setColumn(p.getColumn() + 1);
			
		}

		return b;
	}
}
