package minesweeper;

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
	}
	
	/** defaultConstructorTests - for testing the default constructor */
	private static void defaultConstructorTests() {
		Board board = new Board();
		System.out.println(board);
	}
}
