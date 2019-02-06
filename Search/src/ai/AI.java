package ai;

import java.util.ArrayList;

import game.gameboard.GameBoard;

public class AI {
	
	public AI() {
		
	}
	
	public String miniMaxDecision(GameBoard gameBoard) {
		ArrayList<String> possibleActions = gameBoard.evaluate();
		int maxValue = 0;
		String maxAction = "";
		for (String action : possibleActions) {
			try {
				if (minValue(gameBoard.actionResult(action)) > maxValue) {
					maxAction = action;
				}
			} catch (Exception e) {
				
			}
		}
		return "";
	}
	
	private int maxValue(GameBoard gameBoard) {
		return 0;
	}
	
	private int minValue(GameBoard gameBoard) {
		return 0;
	}
}
