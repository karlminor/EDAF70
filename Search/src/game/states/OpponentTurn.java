package game.states;

import java.util.ArrayList;
import controller.Controller;
import game.Game;
import game.gameboard.Action;
import game.gameboard.GameBoard;

public class OpponentTurn implements State {
	private Controller controller;
	private Game game;
	private int color;
	
	public OpponentTurn(Controller controller, Game game, int color) {
		this.controller = controller;
		this.color = color;
		this.game = game;
	}

	@Override
	public boolean execute() {
		System.out.println("Your turn");
		GameBoard gameBoard = game.currentGameBoard();
		ArrayList<Integer> possibleActions = gameBoard.evaluate(color);
		if(possibleActions.isEmpty()) {
			System.out.println("No possible actions");
			return false;
		}
		System.out.print("Possible moves: ");
		for(int pos : possibleActions) {
			System.out.print(Action.parseInt(pos) + " ");
		}
		System.out.println();
		int move = getMove(possibleActions);
		int col = move%8;
		int row = move/8;
		game.action(row, col, color);
		return true;
	}
	
	@Override
	public int getColor() {
		return color;
	}
	
	private int getMove(ArrayList<Integer> possibleActions) {
		int move;
		do {
			System.out.print("Input your move (e.g. 'a1'): ");
			move = Action.parseString(controller.handleInput());
			if(move == -1) {
				System.out.println("Invalid input");
			}else if(!possibleActions.contains(move)) {
				System.out.println("Not a valid move");
			}
		} while(move == -1 || !possibleActions.contains(move));
		return move;
	}
}
