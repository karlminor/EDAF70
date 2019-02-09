package game.gameboard;

public class Action {

	public static int parseString(String action){
		if(action.length() == 2) {
			int col = (int)(action.toLowerCase().charAt(0)) - 97;
			int row = (int)action.charAt(1) - 49;
			if(col >= 0 && col < 8 && row >= 0 && row < 8) {
				return row * 8 + col;
			}
		}
		return -1;
	}
	
	public static String parseInt(int pos) {
		int col = pos%8;
		int row = (pos/8)+1;
		char colChar = (char)(97+col);
		char rowChar = (char)(row+'0');
		return new String(new char[] {colChar, rowChar});
	}
	
}
