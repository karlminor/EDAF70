package model.reason;

import java.util.ArrayList;

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

	/*
	 * returns the probability entry (Tij) of the transition matrix T to go from
	 * pose i = (x, y, h) to pose j = (nX, nY, nH)
	 */
	public double getP(int x, int y, int h, int nX, int nY, int nH) {
		// Changed matrixRow from (y * col * head + x * head + h)
		// to (x * col * head + y * head + h)
		int matrixRow = x * col * head + y * head + h;
		int matrixCol = nX * col * head + nY * head + nH;
		return matrix[matrixRow][matrixCol].p;
	}

	public double[][] getTranspose() {
		double[][] product = new double[row * col * head][row * col * head];
		for (int i = 0; i < row * col * head; i++) {
			for (int n = 0; n < row * col * head; n++) {
				product[n][i] = matrix[i][n].p;
			}
		}
		return product;
	}

	private void setupMatrix() {
		int matrixRow = 0;
		for (int x = 0; x < col; x++) {
			for (int y = 0; y < row; y++) {
				for (int hd = 0; hd < head; hd++) {
					matrixRow = x * col * head + y * head + hd;
					for (int r = 0; r < row; r++) {
						for (int c = 0; c < col; c++) {
							for (int h = 0; h < head; h++) {
								matrix[matrixRow][r * col * head + c * head + h] = new State(x, y, hd, c, r, h);
								matrix[matrixRow][r * col * head + c * head + h].p = 0;
							}
						}
					}
				}
			}
		}

		for (int rr = 0; rr < row; rr++) {
			for (int cc = 0; cc < col; cc++) {
				for (int hh = 0; hh < head; hh++) {
					State s = new State(cc, rr, hh, cc, rr, hh);
					calcP(s);
				}
			}
		}
	}

	private void calcP(State s) {
		ArrayList<State> possibleStates = possibleNextStates(s.trueX, s.trueY, s.trueH);
		for (State st : possibleStates) {
			updateP(st, s.trueH, possibleStates.size());
		}
	}

	private void updateP(State s, int originalH, int nbrOfPossibleStates) {
		// Changed matrixRow from (s.trueX * col * head + s.trueY * head + s.trueH)
		// to (s.trueY * col * head + s.trueX * head + s.trueH)
		int matrixRow = s.trueY * col * head + s.trueX * head + s.trueH;
		int matrixCol = s.y * col * head + s.x * head + s.h;

		if (s.h == s.trueH) {
			matrix[matrixRow][matrixCol].p = 0.7;
		} else {
			if (check(s.trueX, s.trueY, s.trueH)) {
				matrix[matrixRow][matrixCol].p = (double) 0.3 / (nbrOfPossibleStates - 1);
			} else {
				matrix[matrixRow][matrixCol].p = (double) 1 / (nbrOfPossibleStates);
			}

		}
	}

	private ArrayList<State> possibleNextStates(int x, int y, int h) {
		ArrayList<State> nextStates = new ArrayList<>();
		for (int i = 0; i < head; i++) {
			switch (i) {
			case 0:
				if (y - 1 >= 0) {
					nextStates.add(new State(x, y, h, x, y - 1, i));
				}
				break;
			case 1:
				if (x + 1 < col) {
					nextStates.add(new State(x, y, h, x + 1, y, i));
				}
				break;
			case 2:
				if (y + 1 < row) {
					nextStates.add(new State(x, y, h, x, y + 1, i));
				}
				break;
			case 3:
				if (x - 1 >= 0) {
					nextStates.add(new State(x, y, h, x - 1, y, i));
				}
				break;
			}
		}
		return nextStates;
	}

	private boolean check(int x, int y, int h) {
		int dx = 0;
		int dy = 0;
		switch (h) {
		case 0:
			dy = -1;
			break;
		case 1:
			dx = 1;
			break;
		case 2:
			dy = 1;
			break;
		case 3:
			dx = -1;
			break;
		}
		return x + dx < col && x + dx >= 0 && y + dy < row && y + dy >= 0;
	}

}
