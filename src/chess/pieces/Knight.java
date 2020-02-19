package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

	public Knight(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "H";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] moveMat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0, 0);
		int x = this.position.getRow();
		int y = this.position.getColumn();
		
		
		p.setValues(x + 2, y + 1);
		if(getBoard().positionExists(p) && (isThereOpponentChessPiece(p) || !getBoard().thereIsAPiece(p)))
				moveMat[p.getRow()][p.getColumn()] = true;
		
		p.setValues(x - 2, y + 1);
		if(getBoard().positionExists(p) && (isThereOpponentChessPiece(p) || !getBoard().thereIsAPiece(p)))
				moveMat[p.getRow()][p.getColumn()] = true;
		
		p.setValues(x + 2, y - 1);
		if(getBoard().positionExists(p) && (isThereOpponentChessPiece(p) || !getBoard().thereIsAPiece(p)))
				moveMat[p.getRow()][p.getColumn()] = true;
		
		p.setValues(x - 2, y - 1);
		if(getBoard().positionExists(p) && (isThereOpponentChessPiece(p) || !getBoard().thereIsAPiece(p)))
				moveMat[p.getRow()][p.getColumn()] = true;
		
		
		
		p.setValues(x + 1, y + 2);
		if(getBoard().positionExists(p) && (isThereOpponentChessPiece(p) || !getBoard().thereIsAPiece(p)))
				moveMat[p.getRow()][p.getColumn()] = true;
		
		p.setValues(x - 1, y + 2);
		if(getBoard().positionExists(p) && (isThereOpponentChessPiece(p) || !getBoard().thereIsAPiece(p)))
				moveMat[p.getRow()][p.getColumn()] = true;
		
		p.setValues(x + 1, y - 2);
		if(getBoard().positionExists(p) && (isThereOpponentChessPiece(p) || !getBoard().thereIsAPiece(p)))
				moveMat[p.getRow()][p.getColumn()] = true;
		
		p.setValues(x - 1, y - 2);
		if(getBoard().positionExists(p) && (isThereOpponentChessPiece(p) || !getBoard().thereIsAPiece(p)))
				moveMat[p.getRow()][p.getColumn()] = true;

		
		
		return moveMat;
	}

}
