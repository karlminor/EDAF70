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
		f = new double[rows*cols*head];
		f[0] = 1;
	}
	
	public void updateProb(int[] xy) {
		int x = xy[0];
		int y = xy[1];
		int row = x*cols+y;
		if(x == -1 && y == -1) {
			row = rows*cols;
		}
		f = matrixMultiply(matrixMultiply(transMatrix.getTranspose(),obsMatrix.getDiagonal(row)),f);
		normalize();
	}
	
	public void normalize() {
		for (int x = 0; x < cols; x++) {
			for (int y = 0; y < rows; y++) {
				for (int h = 0; h < head; h++) {
					probabilities[x][y] += f[x*cols*head+y*head+h];
				}
			}
		}
	}
	
	public double getProb(int x, int y) {
		return probabilities[x][y];
	}
	
	private double[][] matrixMultiply(double[][] matrix1, double[][] matrix2) {
		int rows = matrix1.length;
		int cols = matrix1[0].length;
		double[][] product = new double[rows][cols];
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int k = 0; k < cols; k++) {
                    product[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return product;
	}
	
	private double[] matrixMultiply(double[][] matrix, double[] vector) {
		int rows = matrix.length;
		int cols = matrix[0].length;
		double[] product = new double[cols];
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
            	product[i] += matrix[i][j] * vector[j];
            }
        }
        return product;
	}
}
