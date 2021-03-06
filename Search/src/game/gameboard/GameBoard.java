package game.gameboard;

import java.util.ArrayList;

public class GameBoard implements Cloneable{
	public final static int EMPTY = 0;
	public final static int WHITE = 1;
	public final static int BLACK = 2;
	private int[][] gamePieces;
	public int playerColor = WHITE;
	public int opponentColor = BLACK;
	public boolean playerTurn = true;
	

	public GameBoard() {
		gamePieces = new int[8][8];
		for (int r=0; r < gamePieces.length; r++) {
			for (int c=0; c < gamePieces[0].length; c++) {
				gamePieces[r][c] = GameBoard.EMPTY;
			}
		}
	}
	
	public GameBoard(GameBoard gameBoardToCopy) {
		this.gamePieces = copyGamePieces(gameBoardToCopy.gamePieces);
		this.playerColor = gameBoardToCopy.playerColor;
		this.opponentColor = gameBoardToCopy.opponentColor;
		this.playerTurn = gameBoardToCopy.playerTurn;
	}
	
	private int[][] copyGamePieces(int[][] gamePiecesToCopy){
		int[][] copy = new int[8][8];
		for (int r=0; r < 8; r++) {
			for (int c=0; c < 8; c++) {
				copy[r][c] = gamePiecesToCopy[r][c];
			}
		}
		return copy;
	}
	
	public GameBoard copy() {
		return new GameBoard(this);
	}
	
	public int getCurrentColor() {
		if(playerTurn) {
			return playerColor;
		}
		return opponentColor;
	}
	
	public void update(int row, int col, int color) {
		gamePieces[row][col] = color;
		for(int r = -1; r <= 1; r++) {
			for (int c = -1; c <= 1; c++) {
				update(row + r, col + c, r, c, color);
			}
		}
		if (color == playerColor) {
			playerTurn = false;
		} else {
			playerTurn = true;
		}
	}
		
	private boolean update(int row, int col, int rowDir, int colDir, int color) {
		if(row > 7 || row < 0 || col > 7 || col  < 0) {
			return false;
		}
		if(oppositeColor(gamePieces[row][col ], color)){
			if(update(row + rowDir, col + colDir, rowDir, colDir, color)) {
				gamePieces[row][col] = color;
				return true;
			}else {
				return false;
			}
		}
		return gamePieces[row][col] == color;
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
	
	
	public GameBoard actionResult(int action, int color){
		GameBoard gbCopy = new GameBoard(this);
		int col = action%8;
		int row = action/8;
		gbCopy.update(row, col, color);
		return gbCopy;
	}
	
	public int count(int color) {
		int count = 0;
		for (int r=0; r < gamePieces.length; r++) {
			for (int c=0; c < gamePieces[0].length; c++) {
				if(gamePieces[r][c] == color) {
					count++;
				}
			}
		}
		return count;
	}
	
	public ArrayList<Integer> evaluate(int color){
		ArrayList<Integer> possibleActions = new ArrayList<>();
		for(int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if(gamePieces[r][c] == EMPTY) {
					if(evaluateHere(r, c, color)){
						possibleActions.add(r*8+c);
					}
				}
			}
		}
		return possibleActions;
	}
	
	private boolean oppositeColor(int color1, int color2) {
		return color1 != EMPTY && color1 != color2;
	}
	
	private boolean evaluateHere(int row, int col, int color) {
		for(int r = 1; r >= -1; r--) {
			for (int c = -1; c <= 1; c++) {
				//System.out.println((col+1) + " " + (row+1) + " " + c + " " + r);
				if(row+r < 8 && row+r >= 0 && col+c < 8 && col+c >= 0) {
					if(oppositeColor(gamePieces[row+r][col+c], color)) {
						//System.out.println((col+1) + " " + (row+1) + " " + c + " " + r + " " + gamePieces[row+r][col+c]);
						if(evaluateDirection(row+r, col+c, r, c, color)) {
							return true;
						}
					}
				}	
			}
		}
		return false;
	}
	
	private boolean evaluateDirection(int row, int col, int rowDir, int colDir, int color) {
		row += rowDir;
		col += colDir;
		if(row >= 8 || row < 0 || col >= 8 || col < 0) {
			return false;
		}
		if(oppositeColor(gamePieces[row][col],color)){
			return evaluateDirection(row, col, rowDir, colDir, color);
		}
		return gamePieces[row][col] == color;
	}
}
