package game.states;

public class CurrentState implements State {
	private State currentState;
	
	public void setState(State state) {
		this.currentState = state;
	}
	
	public State getState() {
		return currentState;
	}
	
	public int getColor() {
		return currentState.getColor();
	}

	public boolean execute() {
		System.out.println("currentstate execute");
		return currentState.execute();
	}

}
