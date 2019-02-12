package ai;

import java.util.ArrayList;
import java.util.Random;

import game.gameboard.Action;
import game.gameboard.GameBoard;

public class ABAI {
	private long timeLimit;
	private int checkColor;
	private long currentTime;
	private long startTime;
	
	public ABAI(long timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public int alphaBetaDecision(GameBoard gameBoard, int checkColor) {
		startTime = System.currentTimeMillis();
		this.checkColor = checkColor;
		ArrayList<Integer> possibleActions = gameBoard.evaluate(checkColor);
		int maxValue = 0;
		int testValue;
		ArrayList<Integer> equalValues = new ArrayList<>();
		for (int action : possibleActions) {
			GameBoard gbCopy = gameBoard.actionResult(action, checkColor);
			testValue = maxValue(gbCopy, Integer.MIN_VALUE, Integer.MAX_VALUE);
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
	
	private int maxValue(GameBoard gameBoard, int a, int b) {
		currentTime = System.currentTimeMillis();
		int color = gameBoard.getCurrentColor();
		ArrayList<Integer> possibleActions = gameBoard.evaluate(color);
		if(possibleActions.isEmpty() || (currentTime - startTime) > timeLimit) {
			return gameBoard.count(checkColor);
		}
		int v = Integer.MIN_VALUE;
		for (int action : possibleActions){
			v = Integer.max(v, minValue(gameBoard.actionResult(action, color), a, b));
			if(v >= b) {
				return v;
			}
			a = Integer.max(a, v);
		}
		return v;
	}
	
	private int minValue(GameBoard gameBoard, int a, int b) {
		currentTime = System.currentTimeMillis();
		int color = gameBoard.getCurrentColor();
		ArrayList<Integer> possibleActions = gameBoard.evaluate(color);
		if(possibleActions.isEmpty() || (currentTime - startTime) > timeLimit) {
			return gameBoard.count(checkColor);
		}
		int v = Integer.MAX_VALUE;
		for (int action : possibleActions){
			v = Integer.min(v, maxValue(gameBoard.actionResult(action,color), a, b));
			if (v <= a) {
				return v;
			}
			b = Integer.min(b, v);
		}
		return v;
	}
}
