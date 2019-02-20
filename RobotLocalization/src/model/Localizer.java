package model;


import control.EstimatorInterface;
import model.reason.HMM;
import model.reason.ObservationMatrix;
import model.reason.TransitionMatrix;

public class Localizer implements EstimatorInterface {

	private int rows, cols, head;
	private TransitionMatrix transMatrix;
	private ObservationMatrix obsMatrix;
	private Robot mrRoboto;
	private HMM hmm;

	public Localizer(int rows, int cols, int head) {
		this.rows = rows;
		this.cols = cols;
		this.head = head;
		transMatrix = new TransitionMatrix(rows, cols, head);
		obsMatrix = new ObservationMatrix(rows, cols, head);
		hmm = new HMM(transMatrix, obsMatrix, rows, cols, head);
		mrRoboto = new Robot(0, 0, cols, rows, head);
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

	/*
	 * returns the probability entry (Tij) of the transition matrix T to go from
	 * pose i = (x, y, h) to pose j = (nX, nY, nH)
	 */
	public double getTProb(int x, int y, int h, int nX, int nY, int nH) {
		return transMatrix.getP(x, y, h, nX, nY, nH);
	}

	/*
	 * returns the probability entry of the sensor matrices O to get reading r
	 * corresponding to position (rX, rY) when actually in position (x, y) (note
	 * that you have to take care of potentially necessary transformations from
	 * states i = <x, y, h> to positions (x, y)).
	 */
	public double getOrXY(int rX, int rY, int x, int y, int h) {
		return obsMatrix.getP(rX, rY, x, y);
	}

	/*
	 * returns the currently known true position i.e., after one simulation step of
	 * the robot as (x,y)-pair.
	 */
	public int[] getCurrentTrueState() {
		int[] ret = { mrRoboto.y, mrRoboto.x, mrRoboto.h };
		return ret;
	}

	/*
	 * returns the currently available sensor reading obtained for the true position
	 * after the simulation step returns null if the reading was "nothing" (whatever
	 * that stands for in your model)
	 */
	public int[] getCurrentReading() {
		int[] ret = obsMatrix.getSensorReading();
		if (ret[0] == -1 || ret[1] == -1) {
			return null;
		}

		int[] reading = { ret[1], ret[0] };
		return reading;
	}

	/*
	 * returns the currently estimated (summed) probability for the robot to be in
	 * position (x,y) in the grid. The different headings are not considered, as it
	 * makes the view somewhat unclear.
	 */
	public double getCurrentProb(int x, int y) {
		return hmm.getProb(x, y);
	}

	/*
	 * should trigger one step of the estimation, i.e., true position, sensor
	 * reading and the probability distribution for the position estimate should be
	 * updated one step after the method has been called once.
	 */
	public void update() {
		mrRoboto.move();
		obsMatrix.updateSensor(mrRoboto.x, mrRoboto.y);
		hmm.updateProb(obsMatrix.getSensorReading());
	}

}
