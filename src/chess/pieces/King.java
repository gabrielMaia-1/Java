package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	private ChessMatch chessMatch;
	
	public King(Board board, Color color , ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public String toString() {
		return "K";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] b = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0, 0);
		Position p2 = new Position(0, 0);
		Position p3 = new Position(0, 0);
		Position p4 = new Position(0, 0);
		int x = this.position.getRow();
		int y = this.position.getColumn();
		
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if(i == 0 && j == 0)
					continue;
				p.setValues(x + i, y + j);
				if (getBoard().positionExists(p)&& canMove(p) )
					b[p.getRow()][p.getColumn()] = true;
			}
		}
		
		//Castling
		if(this.getMoveCount() == 0 && !chessMatch.getCheck() ) {
			p.setValues(x, y + 3);
			p2.setValues(x, y + 1);
			p3.setValues(x, y + 2);
			if(testRookCastling(p) && !getBoard().thereIsAPiece(p2) && !getBoard().thereIsAPiece(p3)) {
				b[x][y + 2] = true;
			}
		}
		
		//Castling
				if(this.getMoveCount() == 0 && !chessMatch.getCheck() ) {
					p.setValues(x, y - 4);
					p2.setValues(x, y - 1);
					p3.setValues(x, y - 2);
					p4.setValues(x, y - 3);
					if(testRookCastling(p) && !getBoard().thereIsAPiece(p2) && !getBoard().thereIsAPiece(p3) && !getBoard().thereIsAPiece(p4)) {
						b[x][y - 2] = true;
					}
				}
		
		return b;
	}

	private boolean canMove(Position pos) {
		ChessPiece p = (ChessPiece) getBoard().piece(pos);
		return p == null || p.getColor() != this.getColor();
	}
	
	private boolean testRookCastling(Position p) {
		ChessPiece piece = (ChessPiece)getBoard().piece(p);
		return piece != null && piece instanceof Rook && piece.getMoveCount() == 0 && piece.getColor() == this.getColor();
	}

}
