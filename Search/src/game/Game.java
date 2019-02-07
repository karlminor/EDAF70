package game;

import ai.AI;
import controller.Controller;
import game.gameboard.Action;
import game.gameboard.GameBoard;
import game.states.*;

public class Game {
	GameBoard gameBoard;
	Controller controller;
	AI ai;
	State currentState;
	State playerTurnState;
	State oppTurnState;
	Setup setupState;
	boolean turnUnavailable;
	
	public Game() {
		this.controller = new Controller();
		gameBoard = new GameBoard();
		ai = new AI();
		
		currentState = new CurrentState();
		setupState = new Setup(controller, gameBoard);

	}
	
	public void start() {
		setupState.execute();
		
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
		/*for (int gbc : gameBoard.evaluate(GameBoard.BLACK)) {
			System.out.println(gbc);
			System.out.println(Action.parseInt(gbc));
			System.out.println(Action.parseString(Action.parseInt(gbc)));
		}*/
		CurrentState currentState = new CurrentState();
		turnUnavailable = false;
		while(true) {
			System.out.println("ny turn");
			System.out.println(i);
			currentState.setState(playStates[i]);
			if(currentState.execute()) {
				System.out.println("player turn");
				turnUnavailable = false;
			} else if (turnUnavailable){
				break;
			} else {
				turnUnavailable = true;
			}
			i = 1-i;
		}
	}
	

}
