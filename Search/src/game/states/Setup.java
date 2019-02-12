package game.states;

import controller.Controller;
import game.gameboard.GameBoard;

public class Setup{
	private Controller controller;
	private GameBoard gameBoard;
	public long timeLimit;
	
	public Setup(Controller controller, GameBoard gameBoard) {
		this.controller = controller;
		this.gameBoard = gameBoard;
	}

	public void execute() {
		System.out.print("Welcome to Reversi!\n"
				+ "Please note that white is represented as O and black is represented as X.\n");
		String input;
		do {
			System.out.print("Please choose your color (white/black): \n");
			input = controller.handleInput();
		}while(!input.toLowerCase().equals("white") && !input.toLowerCase().equals("black"));
		if(input.equals("white")) {
			gameBoard.playerColor = GameBoard.BLACK;
			gameBoard.opponentColor = GameBoard.WHITE;
			gameBoard.playerTurn = false;
		}
		do {
			System.out.print("Please input maximum time for AI in milliseconds: \n");
			input = controller.handleInput();
			try {
				timeLimit = Long.parseLong(input);
			}
			catch (NumberFormatException e){
				System.out.println("Invalid input!");
				input = null;
			}
		}while(input == null);
		gameBoardSetup();
		gameBoard.print();
	}
	
	public void gameBoardSetup() {
		gameBoard.update(3, 3, GameBoard.WHITE);
		gameBoard.update(3, 4, GameBoard.BLACK);
		gameBoard.update(4, 3, GameBoard.BLACK);
		gameBoard.update(4, 4, GameBoard.WHITE);
	}

	

}
