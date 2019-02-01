package game.states;

import controller.Controller;
import game.gameboard.GameBoard;

public class Setup implements State{
	private Controller controller;
	private GameBoard gameBoard;
	
	public Setup(Controller controller, GameBoard gameBoard) {
		this.controller = controller;
		this.gameBoard = gameBoard;
	}

	@Override
	public void execute() {
		System.out.print("Welcome to Reversi!\n"
				+ "Please note that white is represented as O and black is represented as X.\n");
		String input = "black";
		do {
			System.out.print("Please choose your color (white/black): \n");
			//input = controller.handleInput();
		}while(!input.equals("white") && !input.equals("black"));
		if(input.equals("white")) {
			gameBoard.playerColor = GameBoard.WHITE;
			gameBoard.opponentColor = GameBoard.BLACK;
		}
		gameBoardSetup();
		gameBoard.print();
	}
	
	public void gameBoardSetup() {
		gameBoard.add(3, 3, GameBoard.WHITE);
		gameBoard.add(3, 4, GameBoard.BLACK);
		gameBoard.add(4, 3, GameBoard.BLACK);
		gameBoard.add(4, 4, GameBoard.WHITE);
	}
	

}
