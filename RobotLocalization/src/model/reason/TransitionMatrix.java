package model.reason;

public class TransitionMatrix {
	State[][] matrix;
	int row, col, head;

	public TransitionMatrix(int row, int col, int head) {
		this.row = row;
		this.col = col;
		this.head = head;
		matrix = new State[row * col * head][row * col * head];

		setupMatrix();
	}

	private void setupMatrix() {
		for (int matrixRow = 0; matrixRow < row * col * head; matrixRow++) {
			for (int r = 0; r < row; r++) {
				for (int c = 0; c < col; c++) {
					for (int h = 0; h < head; h++) {
						matrix[matrixRow][r*col*head+c*head+h] = new State(c, r, h);
						//System.out.println(matrixRow + " " + (r*col*head+c*head+h));
					}
				}
			}
		}
	}
}
