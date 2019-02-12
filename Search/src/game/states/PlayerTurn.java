package game.states;


import ai.ABAI;
import game.Game;
import game.gameboard.Action;
import game.gameboard.GameBoard;

public class PlayerTurn implements State {
	private int color;
	private ABAI abai;
	private Game game;
	
	public PlayerTurn(Game game, int color, long timeLimit) {
		this.color = color;
		abai = new ABAI(timeLimit);
		this.game = game;
	}

	@Override
	public boolean execute() {
		System.out.println("AI turn");
		GameBoard gameBoard = game.currentGameBoard();
		int move = abai.alphaBetaDecision(gameBoard, color);
		if(move == -1) {
			return false;
		}
		System.out.println("AI chose: " + Action.parseInt(move));
		int col = move%8;
		int row = move/8;
		game.action(row, col, color);
		return true;
	}
	
	@Override
	public int getColor() {
		return color;
	}
	
}
