package boardgame;

public abstract class Piece {
	
	protected Position position;
	private Board board;
	
	public Piece(Board board) {
		this.board = board;
		position = null;
	}
	
	protected Board getBoard() {
		return this.board;
	}
	
	public abstract boolean[][] possibleMoves();
	
	public boolean possibleMove(Position pos) {
		return possibleMoves()[pos.getRow()][pos.getColumn()];
	}
	
	public boolean isThereAnyPossibleMoves() {
		boolean[][] b = possibleMoves();
		for (int i = 0; i <  b.length; i++) {
			for (int j = 0; j < b.length; j++) {
				if(b[i][j])
					return true;
			}
		}
		return false;
	}
}
