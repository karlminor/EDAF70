package model.reason;

public class Room {
	int[][] room;
	int row,col;
	
	public Room(int row, int col) {
		this.row = row;
		this.col = col;
		room = new int[row*2][col*2];
		
		setupRoom();
	}
	
	private void setupRoom() {
		for (int r = 0; r < row; r++) {
			for (int c = 0; c < col; c++) {
				for (int h = 0; h < 4; h++) {
					room[r][] = new Square(r,c,h);
				}
			}
		}
	}
}
