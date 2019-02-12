package game;

import controller.Controller;
import game.gameboard.GameBoard;
import game.states.*;

public class Game {
	private GameBoard gameBoard;
	private Controller controller;
	private State playerTurnState;
	private State oppTurnState;
	private Setup setupState;
	private boolean turnUnavailable;
	
	public Game() {
		this.controller = new Controller();
		gameBoard = new GameBoard();
		
		setupState = new Setup(controller, gameBoard);

	}
	
	public void start() {
		setupState.execute();
		
		playerTurnState = new PlayerTurn(this, gameBoard.playerColor, setupState.timeLimit);
		oppTurnState = new OpponentTurn(controller, this, gameBoard.opponentColor);
		State[] playStates = new State[2];
		playStates[0] = playerTurnState;
		playStates[1] = oppTurnState;
		if(gameBoard.playerColor == GameBoard.WHITE) {
			playStates[0] = oppTurnState;
			playStates[1] = playerTurnState;
		}
		int i = 0;
		CurrentState currentState = new CurrentState();
		turnUnavailable = false;
		while(true) {
			currentState.setState(playStates[i]);
			if(currentState.execute()) {
				turnUnavailable = false;
			} else if (turnUnavailable){
				break;
			} else {
				turnUnavailable = true;
			}
			i = 1-i;
		}
		if(gameBoard.count(gameBoard.playerColor) > gameBoard.count(gameBoard.opponentColor)) {
			System.out.println("AI won!");
		}
		else {
			System.out.println("AI lost!");
		}
		System.out.println("AI score: " + gameBoard.count(gameBoard.playerColor) + ". Opponent score: " + gameBoard.count(gameBoard.opponentColor));
	}
	
	public GameBoard currentGameBoard() {
		return gameBoard.copy();
	}
	
	public void action(int row, int col, int color) {
		gameBoard.update(row, col, color);
		gameBoard.print();
	}
	

}
