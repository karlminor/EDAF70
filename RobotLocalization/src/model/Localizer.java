package model;

import control.EstimatorInterface;
import model.reason.TransitionMatrix;

public class Localizer implements EstimatorInterface {
	
	private int rows, cols, head;
	private TransitionMatrix transMatrix;
	private Robot mrRoboto;
	
	public Localizer(int rows, int cols, int head) {
		this.rows = rows;
		this.cols = cols;
		this.head = head;	
		transMatrix = new TransitionMatrix(rows,cols,head);
		mrRoboto = new Robot(0,0,1, cols, rows, head);
	}

	public int getNumRows() {
		return rows;
	}
	
	public int getNumCols() {
		return cols;
	}
	
	public int getNumHead() {
		return head;
	}

	public double getTProb(int x, int y, int h, int nX, int nY, int nH) {
		return 0.0;
	}

	public double getOrXY(int rX, int rY, int x, int y, int h) {
		return 0.1;
	}

	/*
	 * returns the currently known true position i.e., after one simulation step
	 * of the robot as (x,y)-pair.
	 */
	public int[] getCurrentTrueState() {
		int[] ret = {mrRoboto.y, mrRoboto.x, mrRoboto.h};
		return ret;
	}

	public int[] getCurrentReading() {
		int[] ret = null;
		return ret;
	}


	public double getCurrentProb(int x, int y) {
		double ret = 0.0;
		return ret;
	}
	
	public void update() {
		mrRoboto.move();
	}

}
