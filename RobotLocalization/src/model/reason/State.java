package model.reason;

public class State {
	public int trueX, trueY, trueH, x, y, h;
	public double p;

	//A state corresponds to a position given an original position
	public State(int trueX, int trueY, int trueH, int x, int y, int h) {
		this.trueX = trueX;
		this.trueY = trueY;
		this.trueH = trueH;
		this.x = x;
		this.y = y;
		this.h = h;
	}
}
