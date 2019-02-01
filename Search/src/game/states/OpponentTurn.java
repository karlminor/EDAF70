package game.states;

import controller.Controller;
import game.gameboard.GameBoard;

public class OpponentTurn implements State {
	private Controller controller;
	private GameBoard gameBoard;
	private int color;
	
	public OpponentTurn(Controller controller, GameBoard gameBoard, int color) {
		this.controller = controller;
		this.gameBoard = gameBoard;
		this.color = color;
	}

	@Override
	public void execute() {
		
	}

}
