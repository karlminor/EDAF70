package ai;

import java.util.ArrayList;
import java.util.Random;

import game.gameboard.Action;
import game.gameboard.GameBoard;

public class AI {
	private int depth;
	private int counter;
	private int checkColor;
	private GameBoard gameBoard;
	
	public AI(int depth) {
		this.depth = depth;
	}
	
	public int miniMaxDecision(GameBoard gameBoard, int checkColor) {
		this.checkColor = checkColor;
		ArrayList<Integer> possibleActions = gameBoard.evaluate(checkColor);
		int maxValue = 0;
		int testValue;
		ArrayList<Integer> equalValues = new ArrayList<>();
		for (int action : possibleActions) {
			counter = 0;
			GameBoard gbCopy = gameBoard.actionResult(action, checkColor);
			testValue = minValue(gbCopy);
			if (testValue > maxValue) {
				maxValue = testValue;
				equalValues.clear();
				equalValues.add(action);
			} else if (testValue == maxValue) {
				equalValues.add(action);
			}
		}
		if (equalValues.isEmpty()) {
			return -1;
		}
		Random rand = new Random();
		return equalValues.get(rand.nextInt(equalValues.size()));
	}
	
	private int maxValue(GameBoard gameBoard) {
		int color = gameBoard.getCurrentColor();
		ArrayList<Integer> possibleActions = gameBoard.evaluate(color);
		if(possibleActions.isEmpty()) {
			return gameBoard.count(checkColor);
		}
		int maxValue = Integer.MIN_VALUE;
		int testValue;
		for (int action : possibleActions){
			testValue = minValue(gameBoard.actionResult(action, color));
			if (testValue > maxValue) {
				maxValue = testValue;
			}
		}
		return maxValue;
	}
	
	private int minValue(GameBoard gameBoard) {
		counter++;
		int color = gameBoard.getCurrentColor();
		ArrayList<Integer> possibleActions = gameBoard.evaluate(color);
		if(possibleActions.isEmpty() || counter >= depth) {
			return gameBoard.count(checkColor);
		}
		int minValue = Integer.MAX_VALUE;
		int testValue;
		for (int action : possibleActions){
			testValue = maxValue(gameBoard.actionResult(action,color));
			if (testValue < minValue) {
				minValue = testValue;
			}
		}
		return minValue;
	}
}
