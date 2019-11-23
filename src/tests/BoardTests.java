package tests;

import java.util.Random;

import game.Board;

/** Any tests related to the functions of the Board class go here.
 * 
 * @author Nathan Jobe
 */
public class BoardTests {
	
	/** main - main method for executing tests
	 * 
	 * @param args - command line arguments (unused)
	 */
	public static void main(String[] args) {
		defaultConstructorTests();
		manualConstructorTests();
	}
	
	/** defaultConstructorTests - for testing the default constructor */
	private static void defaultConstructorTests() {
		System.out.println("Default constructor test");
		System.out.println("Creating a 20x20 board with 50 mines");
		Board board = new Board();
		board.reveal();
		System.out.println(board);
	}
	
	/** manualConstructorTests - for testing the constructor with arguments */
	private static void manualConstructorTests() {
		Random rgn = new Random();
		int height = rgn.nextInt(50);
		int width = rgn.nextInt(50);
		int numMines = rgn.nextInt(height * width);
		Board board = new Board(height, width, numMines);
		board.reveal();
		System.out.println("Manual constructor test");
		System.out.printf("Creating a %dx%d board with %d mines\n", height, width, numMines);
		System.out.println(board);
	}
}
