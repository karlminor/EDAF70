package game.states;


import ai.AI;
import controller.Controller;
import game.Game;
import game.gameboard.Action;
import game.gameboard.GameBoard;

public class PlayerTurn implements State {
	private Controller controller;
	private int color;
	private AI ai;
	private Game game;
	
	public PlayerTurn(Controller controller, Game game, int color) {
		this.controller = controller;
		this.color = color;
		ai = new AI(5);
		this.game = game;
	}

	@Override
	public boolean execute() {
		System.out.println("AI turn");
		GameBoard gameBoard = game.currentGameBoard();
//		ArrayList<Integer> possibleActions = gameBoard.evaluate(color);
//		if(possibleActions.isEmpty()) {
//			System.out.println("No possible actions");
//			return false;
//		}
//		System.out.print("Possible moves: ");
//		for(int pos : possibleActions) {
//			System.out.print(Action.parseInt(pos) + " ");
//		}
//		System.out.println();
		//System.out.println("Before ai: ");
		//gameBoard.print();
		int move = ai.miniMaxDecision(gameBoard, color);
		if(move == -1) {
			return false;
		}
		System.out.println("AI chose: " + Action.parseInt(move));
		//System.out.println("After ai: ");
		//gameBoard.print();
//		int move = getMove(possibleActions);
		int col = move%8;
		int row = move/8;
		game.action(row, col, color);
		return true;
	}
	
	@Override
	public int getColor() {
		return color;
	}
	
//	private int getMove(ArrayList<Integer> possibleActions) {
//		int move;
//		do {
//			System.out.print("Input your move (e.g. 'a1'): ");
//			move = Action.parseString(controller.handleInput());
//			if(move == -1) {
//				System.out.println("Invalid input");
//			}else if(!possibleActions.contains(move)) {
//				System.out.println("Not a valid move");
//			}
//		} while(move == -1 || !possibleActions.contains(move));
//		return move;
//	}
}
