package model.reason;

public class HMM {
	private TransitionMatrix transMatrix;
	private ObservationMatrix obsMatrix;
	private double[][] probabilities;
	private double[] f;
	private int rows, cols, head;

	public HMM(TransitionMatrix transMatrix, ObservationMatrix obsMatrix, int rows, int cols, int head) {
		this.transMatrix = transMatrix;
		this.obsMatrix = obsMatrix;
		this.rows = rows;
		this.cols = cols;
		this.head = head;
		probabilities = new double[rows][cols];
		f = new double[rows * cols * head];
		for (int i = 0; i < f.length; i++) {
			f[i] = 1.0 / f.length;
		}
	}

	//Updates the probabilities for Mr Roboto's location given a sensor reading
	public void updateProb(int[] xy) {
		int x = xy[0];
		int y = xy[1];
		int row = y * cols + x;
		if (x == -1 && y == -1) {
			row = rows * cols;
		}

		f = matrixMultiply(matrixMultiply(obsMatrix.getDiagonal(row), transMatrix.getTranspose()), f);
		normalize();
	}
	
	//Normalizes probabilities to ensure they add up to 1
	public void normalize() {
		double sumProb = 0;
		for (double d : f) {
			sumProb += d;
		}
		if (sumProb != 0) {
			for (int i = 0; i < f.length; i++) {
				f[i] /= sumProb;
			}
		}

		for (int x = 0; x < cols; x++) {
			for (int y = 0; y < rows; y++) {
				probabilities[x][y] = 0;
				for (int h = 0; h < head; h++) {
					probabilities[x][y] += f[x * cols * head + y * head + h];
				}
			}
		}
	}
	
	//Returns the probability that Mr Roboto is in position x,y
	public double getProb(int x, int y) {
		return probabilities[x][y];
	}

	//Matrix multiplication
	private double[][] matrixMultiply(double[][] matrix1, double[][] matrix2) {
		int rows = matrix1.length;
		int cols = matrix1[0].length;
		double[][] product = new double[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				for (int k = 0; k < cols; k++) {
					product[i][j] += matrix1[i][k] * matrix2[k][j];
				}
			}
		}
		return product;
	}
	
	//Multiplies a matrix with a vector
	private double[] matrixMultiply(double[][] matrix, double[] vector) {
		int rows = matrix.length;
		int cols = matrix[0].length;
		double[] product = new double[cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				product[i] += matrix[i][j] * vector[j];
			}
		}
		return product;
	}
}
