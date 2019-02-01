package game.states;

public class CurrentState implements State {
	private State currentState;
	
	public void setState(State state) {
		this.currentState = state;
	}
	
	public State getState() {
		return this.currentState;
	}

	@Override
	public void execute() {
		this.currentState.execute();
	}

}
