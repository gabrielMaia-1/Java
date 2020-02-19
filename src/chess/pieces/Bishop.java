package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {

	public Bishop(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "B";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] movMat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0, 0);
		int x = this.position.getRow();
		int y = this.position.getColumn();

		// SE
		p.setValues(x + 1, y + 1);
		for (;;) {
			if (!getBoard().positionExists(p)) {
				break;
			} else {
				if (isThereOpponentChessPiece(p)) {
					movMat[p.getRow()][p.getColumn()] = true;
					break;
				} else if (!getBoard().thereIsAPiece(p)) {
					movMat[p.getRow()][p.getColumn()] = true;
				} else {
					break;
				}
			}
			p.setValues(p.getRow() + 1, p.getColumn() + 1);
		}

		// NE
		p.setValues(x - 1, y + 1);
		for (;;) {
			if (!getBoard().positionExists(p)) {
				break;
			} else {
				if (isThereOpponentChessPiece(p)) {
					movMat[p.getRow()][p.getColumn()] = true;
					break;
				} else if (!getBoard().thereIsAPiece(p)) {
					movMat[p.getRow()][p.getColumn()] = true;
				} else {
					break;
				}
			}
			p.setValues(p.getRow() - 1, p.getColumn() + 1);
		}

		// SW
		p.setValues(x + 1, y - 1);
		for (;;) {
			if (!getBoard().positionExists(p))
				break;
			else {
				if (isThereOpponentChessPiece(p)) {
					movMat[p.getRow()][p.getColumn()] = true;
					break;
				} else if (!getBoard().thereIsAPiece(p)) {
					movMat[p.getRow()][p.getColumn()] = true;
				} else {
					break;
				}
			}
			p.setValues(p.getRow() + 1, p.getColumn() - 1);
		}

		// NW
		p.setValues(x - 1, y - 1);
		for (;;) {
			if (!getBoard().positionExists(p))
				break;
			else {
				if (isThereOpponentChessPiece(p)) {
					movMat[p.getRow()][p.getColumn()] = true;
					break;
				} else if (!getBoard().thereIsAPiece(p)) {
					movMat[p.getRow()][p.getColumn()] = true;
				} else {
					break;
				}
			}
			p.setValues(p.getRow() - 1, p.getColumn() - 1);
		}

		return movMat;
	}

}
