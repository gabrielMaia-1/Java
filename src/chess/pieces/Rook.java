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
		boolean[][] movMat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0, 0);
		int x = this.position.getRow();
		int y = this.position.getColumn();

		// DOWN
		p.setValues(x + 1, y);
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
			p.setValues(p.getRow() + 1, p.getColumn());
		}

		// UP
		p.setValues(x - 1, y);
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
			p.setValues(p.getRow() - 1, p.getColumn());
		}

		// LEFT
		p.setValues(x, y - 1);
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
			p.setValues(p.getRow(), p.getColumn() - 1);
		}

		// RIGHT
		p.setValues(x, y + 1);
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
			p.setValues(p.getRow(), p.getColumn() + 1);
		}

		return movMat;
	}
}
