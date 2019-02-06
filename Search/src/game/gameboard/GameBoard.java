package game.gameboard;

import java.util.ArrayList;

public class GameBoard {
	public final static int EMPTY = 0;
	public final static int WHITE = 1;
	public final static int BLACK = 2;
	private int[][] gamePieces;
	public int playerColor = BLACK;
	public int opponentColor = WHITE;
	

	public GameBoard() {
		gamePieces = new int[8][8];
		for (int r=0; r < gamePieces.length; r++) {
			for (int c=0; c < gamePieces[0].length; c++) {
				gamePieces[r][c] = GameBoard.EMPTY;
			}
		}
	}
	
	public GameBoard(int[][] gamePieces) {
		this.gamePieces = gamePieces;
	}
	
	public boolean add(int row, int col, int color) {
		if(row > 8 || col > 8 || (color != GameBoard.BLACK && color != GameBoard.WHITE) || gamePieces[row][col] != GameBoard.EMPTY){
			return false;
		}
		gamePieces[row][col] = color;
		return true;
	}
	
	public boolean update(int row, int col, int color) {
		if(row > 8 || col > 8 || (color != GameBoard.BLACK && color != GameBoard.WHITE)){
			return false;
		}
		gamePieces[row][col] = color;
		return true;
	}
	
	public void print() {
		printLetters();
		for (int i = 0; i < 17; i++) {
			if (i%2 == 0) {
				printHLine();
			} else {
				System.out.print((i+1)/2 + " |");
				for (int c = 0; c < 8; c++) {
					printPiece((i-1)/2, c);
				}
				System.out.print(" " + (i+1)/2);
				System.out.print("\n");
			}
		}
		printLetters();

	}
	private void printHLine() {
		System.out.print("  -");
		for (int i = 0; i < 8; i++) {
			System.out.print("----");
		}
		System.out.print("\n");
	}
	
	private void printLetters() {
		System.out.print("    a  " + " b  " + " c  " + " d  " + " e  " + " f  " + " g  " + " h  \n");
	}
	
	private void printPiece(int row, int col) {
		switch (gamePieces[row][col]) {
		case GameBoard.EMPTY:
			System.out.print("   ");
			break;
		case GameBoard.WHITE:
			System.out.print(" O ");
			break;
		case GameBoard.BLACK:
			System.out.print(" X ");
			break;
		}
		System.out.print("|");
	}
	
	
	public GameBoard actionResult(String action) throws Exception{
		GameBoard gbCopy = new GameBoard(gamePieces);
		int[][] pos = Action.parseString(action);
		if(pos == null) {
			throw new Exception();
		}
		return gbCopy;
	}
	
	public ArrayList<String> evaluate(){
		ArrayList<String> possibleActions = new ArrayList<>();
		for(int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				
			}
		}
		return possibleActions;
	}
}
