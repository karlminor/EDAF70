package model.reason;

import java.util.ArrayList;
import java.util.Random;

public class ObservationMatrix {
	State[][] matrix;
	int rows, cols, heads;
	private int[] currentSensorReading = { -1, -1 };

	public ObservationMatrix(int rows, int cols, int heads) {
		this.rows = rows;
		this.cols = cols;
		this.heads = heads;
		matrix = new State[rows * cols + 1][rows * cols];

		setupMatrix();
	}

	/*
	 * returns the probability entry of the sensor matrices O to get reading r
	 * corresponding to position (rX, rY) when actually in position (x, y) (note
	 * that you have to take care of potentially necessary transformations from
	 * states i = <x, y, h> to positions (x, y)).
	 */
	public double getP(int rX, int rY, int x, int y) {
		int matrixRow = x * cols + y;
		int matrixCol = rX * cols + rY;
		if (rX == -1 || rY == -1) {
			matrixRow = rows * cols;
			matrixCol = x * cols + y;
		}
		return matrix[matrixRow][matrixCol].p;
	}

	public double[][] getDiagonal(int row) {
		State[] rowVector = matrix[row];
		double[][] diagonalMatrix = new double[rows * cols * heads][rows * cols * heads];
		for (int i = 0; i < rows * cols; i++) {
			for (int h = 0; h < heads; h++) {
				diagonalMatrix[i * heads + h][i * heads + h] = rowVector[i].p;
			}
		}
		return diagonalMatrix;
	}

	public void updateSensor(int x, int y) {
		int[] reading = { -1, -1 };

		ArrayList<int[]> n_Ls_one = getNeighbors(x, y, 1);
		ArrayList<int[]> n_Ls_two = getNeighbors(x, y, 2);

		Random rand = new Random();

		int randomNbr = rand.nextInt(1000) + 1;

		int roof = 100 + 50 * n_Ls_one.size() + 25 * n_Ls_two.size();
		if (randomNbr <= roof) {
			if (randomNbr <= 100) {
				reading[0] = x;
				reading[1] = y;
			} else if (randomNbr <= 50 * n_Ls_one.size()) {
				reading = n_Ls_one.get(rand.nextInt(n_Ls_one.size()));
			} else {
				reading = n_Ls_two.get(rand.nextInt(n_Ls_two.size()));
			}
		}
		currentSensorReading = reading;
	}

	public int[] getSensorReading() {
		return currentSensorReading;
	}

	private void setupMatrix() {
		for (int x = 0; x < cols; x++) {
			for (int y = 0; y < rows; y++) {
				for (int xx = 0; xx < cols; xx++) {
					for (int yy = 0; yy < rows; yy++) {
						State s = new State(x, y, 0, xx, yy, 0);
						matrix[x * cols + y][xx * cols + yy] = s;
						calculateP(s);

					}
				}
			}
		}

		for (int xx = 0; xx < cols; xx++) {
			for (int yy = 0; yy < rows; yy++) {
				State s = new State(-1, -1, 0, xx, yy, 0);
				matrix[cols * rows][xx * cols + yy] = s;
				calculateP(s);
			}
		}
	}

	private void calculateP(State s) {
		if (s.trueX != -1) {
			matrix[s.trueX * cols + s.trueY][s.x * cols + s.y].p = findP(s.trueX, s.trueY, s.x, s.y);
		} else {
			matrix[cols * rows][s.x * cols + s.y].p = sumP(s.x, s.y);
		}

	}

	private double findP(int trueX, int trueY, int x, int y) {
		int distance = Math.max(Math.abs(trueX - x), Math.abs(trueY - y));
		switch (distance) {
		case 0:
			return 0.1;
		case 1:
			return 0.05;
		case 2:
			return 0.025;
		default:
			return 0;
		}
	}

	private double sumP(int trueX, int trueY) {
		double sum = 0;
		for (State s : matrix[trueX * cols + trueY]) {
			sum += s.p;
		}
		return 1 - sum;
	}

	private ArrayList<int[]> getNeighbors(int x, int y, int distance) {
		ArrayList<int[]> neighbors = new ArrayList<int[]>();
		for (int i = x - distance; i <= x + distance; i++) {
			for (int n = y - distance; n <= y + distance; n++) {
				if (Math.max(Math.abs(x - i), Math.abs(y - n)) == distance) {
					if (i >= 0 && i < cols && n >= 0 && n < rows) {
						neighbors.add(new int[] { i, n });
					}
				}
			}
		}
		return neighbors;
	}
}