package game.states;

import java.util.ArrayList;

import controller.Controller;
import game.gameboard.Action;
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
	public boolean execute() {
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
		gameBoard.update(row, col, color);
		gameBoard.print();
		return true;
	}
	
	@Override
	public int getColor() {
		return color;
	}
	
	private int getMove(ArrayList<Integer> possibleActions) {
		int move = -1;
		do {
			System.out.print("Input your move (e.g. 'a1'): ");
			move = Action.parseString(controller.handleInput());
			if(move == -1) {
				System.out.println("Invalid input");
			}
			if(!possibleActions.contains(move)) {
				System.out.println("Not a valid move");
			}
		} while(move == -1 || !possibleActions.contains(move));
		return move;
	}
}
