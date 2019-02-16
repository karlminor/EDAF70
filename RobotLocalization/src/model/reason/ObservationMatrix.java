package model.reason;

public class ObservationMatrix {
	State[][] matrix;
	int row, col, head;

	public ObservationMatrix(int row, int col, int head) {
		this.row = row;
		this.col = col;
		this.head = head;
		matrix = new State[row*col+1][row * col];

		setupMatrix();
	}
	
	private void setupMatrix() {
		for (int x = 0; x < col; x++) {
			for (int y = 0; y < row; y++) {
				for (int xx = 0; xx < col; xx++) {
					for (int yy = 0; yy < row; yy++) {
						State s = new State(x,y,0,xx,yy,0);
						setDistance(s);
						matrix[x*col+y*row][xx*col+yy*row] = s;
					}
				}
			}
		}
	}
	
	
	
}
