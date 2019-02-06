package ai;

import java.util.ArrayList;

import game.gameboard.GameBoard;

public class AI {
	
	public AI() {
		
	}
	
	public int miniMaxDecision(GameBoard gameBoard) {
		ArrayList<Integer> possibleActions = gameBoard.evaluate();
		int maxValue = 0;
		int maxAction = -1;
		int testValue;
		for (int action : possibleActions) {
			testValue = minValue(gameBoard.actionResult(action));
			if (testValue > maxValue) {
				maxValue = testValue;
				maxAction = action;
			}
		}
		return maxAction;
	}
	
	private int maxValue(GameBoard gameBoard) {
		return 0;
	}
	
	private int minValue(GameBoard gameBoard) {
		return 0;
	}
}
