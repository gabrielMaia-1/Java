package boardgame;

public class Board {
	private int rows;
	private int columns;
	private Piece[][] pieces;

	
	public Board(int rows,int  columns) {
		if (rows < 1 || columns < 1 )
			throw new BoardException("Error: the board size must be at least 1x1");
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}


	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
	
	public Piece piece(int row, int column) {
		if(!positionExists(row,column))
			throw new BoardException("Position not on the board");
		return this.pieces[row][column];
	}
	
	public Piece pice(Position pos) {
		if(!positionExists(pos))
			throw new BoardException("Position not on the board");
		return this.pieces[pos.getRow()][pos.getColumn()];
	}
	
	public void placePiece(Piece piece, Position pos) {
		
		if(thereIsAPiece(pos))
			throw new BoardException("There is already a piece in this position:" + pos);
		pieces[pos.getRow()][pos.getColumn()] = piece;
		piece.position = pos;
	}
	
	public boolean positionExists(Position pos) {
		return positionExists(pos.getRow(), pos.getColumn());
	}
	
	private boolean positionExists(int row, int col) {
		return row >= 0 && row < this.rows && col >= 0 && col < this.columns;
	}
	
	public boolean thereIsAPiece(Position pos) {
		if(!positionExists(pos))
			throw new BoardException("Position not on the board");
		return pieces[pos.getRow()][pos.getColumn()] != null;
	}
}
