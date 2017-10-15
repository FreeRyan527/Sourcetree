
public class BoardPoint {

	private int row = -1;

	private int col = -1;

	public BoardPoint(int row, int column) {
		this.row = row;
		this.col = column;
	}

	public void setPoint(int row, int column) {
		this.row = row;
		this.col = column;
	}

	public int getRow() {
		return this.row;
	}

	public int getColumn() {
		return this.col;
	}
}