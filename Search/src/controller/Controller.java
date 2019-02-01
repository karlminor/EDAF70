package controller;

import java.util.Scanner;

public class Controller {
	Scanner scanner;
	
	public Controller() {
		scanner = new Scanner(System.in);
	}
	
	public String handleInput() {
		return scanner.nextLine();
	}
}
