package game;

import controller.Controller;
import game.gameboard.GameBoard;
import game.states.*;

public class Game {
	GameBoard gameBoard;
	Controller controller;
	State currentState;
	State playerTurnState;
	State oppTurnState;
	State setupState;
	
	public Game() {
		this.controller = new Controller();
		gameBoard = new GameBoard();
		
		currentState = new CurrentState();
		setupState = new Setup(controller, gameBoard);

	}
	
	public void start() {
		CurrentState currentState = new CurrentState();
		currentState.setState(setupState);
		currentState.execute();
		playerTurnState = new PlayerTurn(controller, gameBoard, gameBoard.playerColor);
		oppTurnState = new OpponentTurn(controller, gameBoard, gameBoard.opponentColor);
		State[] playStates = new State[2];
		playStates[0] = playerTurnState;
		playStates[1] = oppTurnState;
		if(gameBoard.playerColor == GameBoard.WHITE) {
			playStates[0] = oppTurnState;
			playStates[1] = playerTurnState;
		}
		int i = 0;
		while(!gameBoard.evaluate().isEmpty()) {
			currentState.setState(playStates[i]);
			currentState.execute();
			i = 1-i;
		}
	}
	
	//check if the game has been won
	public boolean evaluate() {
		return true;
	}
}
