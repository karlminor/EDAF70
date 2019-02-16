package model.reason;

public class State {
	public int trueX,trueY,trueH,x,y,h;
	public double p;
	
	public State(int trueX, int trueY, int trueH, int x, int y, int h) {
		this.trueX = trueX;
		this.trueY = trueY;
		this.trueH = trueH;
		this.x = x;
		this.y = y;
		this.h = h;
	}
	
	public void print() {
		System.out.println("True X: " + trueX + " True Y: " + trueY + " True H: " + trueH + " x: " + x + " y: " + y + " h: " + h + " p: " + p + "\n");
	}

}
