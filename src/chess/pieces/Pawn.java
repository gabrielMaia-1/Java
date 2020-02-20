package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece{
	private ChessMatch chessMatch;

	public Pawn(Board board, Color color , ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] moveMat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0, 0);
		Position p2 = new Position(0, 0);
		int x = this.position.getRow();
		int y = this.position.getColumn();
		
		if(getColor() == Color.RED) {
			//Front
			p.setValues(x + 1,y);
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				moveMat[p.getRow()][p.getColumn()] = true;
				
				p.setValues(x + 2,y);
				if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && this.moveCount == 0)
					moveMat[p.getRow()][p.getColumn()] = true;
			}
			//Side 1
			p.setValues(x + 1,y - 1);
			if(isThereOpponentChessPiece(p) && getBoard().positionExists(p)) 
				moveMat[p.getRow()][p.getColumn()] = true;

			//Side 2
			p.setValues(x + 1,y + 1);
			if(isThereOpponentChessPiece(p) && getBoard().positionExists(p)) 
				moveMat[p.getRow()][p.getColumn()] = true;
			
			//EnPassant
			p.setValues(x + 1, y - 1);
			p2.setValues(x, y - 1);
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && isThereOpponentChessPiece(p2) && (chessMatch.getEnPassantVulnerable() == (ChessPiece)getBoard().piece(p2))) {
				moveMat[p.getRow()][p.getColumn()] = true;
			}
			
			p.setValues(x + 1, y + 1);
			p2.setValues(x, y + 1);
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && isThereOpponentChessPiece(p2) && (chessMatch.getEnPassantVulnerable() == (ChessPiece)getBoard().piece(p2))) {
				moveMat[p.getRow()][p.getColumn()] = true;
			}
			
		} else {
			
			//Front
			p.setValues(x - 1,y);
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				moveMat[p.getRow()][p.getColumn()] = true;
				
				p.setValues(x - 2,y);
				if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && this.moveCount == 0)
					moveMat[p.getRow()][p.getColumn()] = true;
			}
			//Side 1
			p.setValues(x - 1,y - 1);
			if(isThereOpponentChessPiece(p) && getBoard().positionExists(p)) 
				moveMat[p.getRow()][p.getColumn()] = true;

			//Side 2
			p.setValues(x - 1,y + 1);
			if(isThereOpponentChessPiece(p) && getBoard().positionExists(p)) 
				moveMat[p.getRow()][p.getColumn()] = true;
			
			//EnPassant
			p.setValues(x - 1, y - 1);
			p2.setValues(x, y - 1);
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && isThereOpponentChessPiece(p2) && (chessMatch.getEnPassantVulnerable() == (ChessPiece)getBoard().piece(p2))) {
				moveMat[p.getRow()][p.getColumn()] = true;
			}
			
			p.setValues(x - 1, y + 1);
			p2.setValues(x, y + 1);
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && isThereOpponentChessPiece(p2) && (chessMatch.getEnPassantVulnerable() == (ChessPiece)getBoard().piece(p2))) {
				moveMat[p.getRow()][p.getColumn()] = true;
			}
		}
		
		return moveMat;
	}
	
	@Override
	public String toString() {
		return "P";
	}

}
