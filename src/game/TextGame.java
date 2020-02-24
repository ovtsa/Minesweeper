package game;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.regex.PatternSyntaxException;

/** Game - a driver for a text-based game of Minesweeper.
 *
 * @author Nathan Jobe
 * @version %I%, %G%
 */
public class TextGame {
	// DEFAULT VALUES
	private static final int EASY_HEIGHT = 12;
	private static final int EASY_WIDTH = 15;
	private static final int EASY_NUM_MINES = 20;
	private static final int NORMAL_HEIGHT = 20;
	private static final int NORMAL_WIDTH = 30;
	private static final int NORMAL_NUM_MINES = 75;
	private static final int HARD_HEIGHT = 30;
	private static final int HARD_WIDTH = 50;
	private static final int HARD_NUM_MINES = 400;

	// kb is the keyboard for user input
	private Scanner kb;
	// board is the particular game Board for this game
	private Board board;
	// a game is always either ACTIVE, WON, or LOST
	private GameState state;
	// The height, width, and number of mines chosen for this game.
	// needed in case a new board must be created
	private int heightChosen;
	private int widthChosen;
	private int numMinesChosen;

	/** The default constructor creates a TextGame and its board using specifications
	 * from user input, and then prints out the board to the console window.
	 */
	public TextGame() {
		// credits
		printCredits();

		this.kb = new Scanner(System.in);
		// a new game is not solved or lost yet
		this.state = GameState.ACTIVE;
		// creates board using user input
		this.board = getBoardSpecifications();
		System.out.println(this.board);
	}

	/** main - the main method, later to be implemented in a separate class.
	 *
	 * @param args command line args (unused)
	 */
	public static void main(String[] args) {
		TextGame game = new TextGame();
		game.play();
	}

	/** play - a shortcut method to execute a loop conducting moves on the board
	 * using user input.
	 */
	public void play() {
		System.out.println("Syntax: row,col,action OR x to quit");
		System.out.println("Actions: \"c\" for \"click,\" \"f\" for \"flag\"");
		/* passing true because this is the first move (necessary in case user clicks on mine)
		   on first move */
		// loop keeps executing moves until player decides to quit
		boolean firstMove = true;
		boolean stillPlaying = true;
		while (stillPlaying) {
			state = makeMove(firstMove);
			if (state != GameState.ACTIVE) {
				stillPlaying = askToPlayAgain();
				firstMove = true;
			} else firstMove = false;
		}
	}

	/** printCredits - prints contributors to this project and the last modified date.
	 */
	private void printCredits() {
		System.out.println("Minesweeper - Nathan Jobe");
		// TODO: Set a "last modified" date
		System.out.println("Last modified 22/11/2019");
	}

	/** getBoardSpecifications - uses user input to create a Board for this Game.
	 *
	 * @return a Board to be used in this Game.
	 */
	private Board getBoardSpecifications() {
		// TODO: Shorten, create more helper methods
		System.out.println("Please select a difficulty, or create a custom board:");
		// Difficulties to be tweaked later
		System.out.printf("EASY   - %dx%d grid, %d  mines\n",
						 	EASY_HEIGHT, EASY_WIDTH, EASY_NUM_MINES);
		System.out.printf("NORMAL - %dx%d grid, %d  mines\n",
							NORMAL_HEIGHT, NORMAL_WIDTH, NORMAL_NUM_MINES);
		System.out.printf("HARD   - %dx%d grid, %d mines\n",
							HARD_HEIGHT, HARD_WIDTH, HARD_NUM_MINES);
		System.out.println("CUSTOM - ?x?   grid, ?   mines");
		Board gameBoard = null;

		boolean validInput = false;
		while (!validInput) {
			System.out.print("Type selection: ");
			String response = kb.nextLine().trim().toUpperCase();
			// this if-else-if logic just creates the Board and sets this class's fields relevantly
			if (response.equals("EASY")) {
				gameBoard = new Board(EASY_HEIGHT, EASY_WIDTH,
									 EASY_NUM_MINES);
				heightChosen = EASY_HEIGHT;
				widthChosen = EASY_WIDTH;
				numMinesChosen = EASY_NUM_MINES;
				validInput = true;
			} else if (response.equals("NORMAL")) {
				gameBoard = new Board(NORMAL_HEIGHT, NORMAL_WIDTH,
									  NORMAL_NUM_MINES);
				heightChosen = NORMAL_HEIGHT;
				widthChosen = NORMAL_WIDTH;
				numMinesChosen = NORMAL_NUM_MINES;
				validInput = true;
			} else if (response.equals("HARD")) {
				gameBoard = new Board(HARD_HEIGHT, HARD_WIDTH,
									  HARD_NUM_MINES);
				heightChosen = HARD_HEIGHT;
				widthChosen = HARD_WIDTH;
				numMinesChosen = HARD_NUM_MINES;
				validInput = true;
			} else if (response.equals("CUSTOM")) {
				// danger: potential invalid input - not integers
				try {
					System.out.print("height: ");
					int boardHeight = kb.nextInt();
					System.out.print("width:  ");
					int boardWidth = kb.nextInt();
					System.out.print("mines:  ");
					int numMines = kb.nextInt();
					if (boardHeight > 50 || boardWidth > 50 || numMines >= boardHeight * boardWidth) {
						throw new IllegalArgumentException();
					}
					gameBoard = new Board(boardHeight, boardWidth, numMines);
					heightChosen = boardHeight;
					widthChosen = boardWidth;
					numMinesChosen = numMines;
					validInput = true;
				} catch (InputMismatchException ime) {
					System.out.println("Invalid response");
				} catch (IllegalArgumentException iae) {
					System.out.println("Number of rows or columns cannot exceed 50");
				}
			} else {
				// switch case default
				System.out.println("Invalid response");
			}
		}

		return gameBoard;
	}

	/** makeMove - gets user input on what to do next and where to do it, then executes
	 * that instruction.
	 *
	 * @param firstMove if this is the first move of the game
	 * @return the state of this Game after this move is made
	 */
	private GameState makeMove(boolean firstMove) {
		// to implement: exception checking
		/* this chunk of code gets user input for a move and divides it into a
		 * row, a column, and an action.
		 * DANGERS: row/col too low/high, row/col not integers, invalid action,
		 * 			improper format (not two comma separators) */
		System.out.print("> ");
		String userInput = kb.nextLine().toUpperCase();

		try {
			String[] choices = userInput.split(",");
			int row = Integer.parseInt(choices[0]);
			int col = Integer.parseInt(choices[1]);
			String action = choices[2];
			if (choices.length > 3) throw new InvalidActionException(userInput);
			if (row < 0 || row > heightChosen || col < 0 || col > widthChosen) throw new IllegalArgumentException();

			// The upper layer of this if-else-if block branches based off action chosen.
			if (action.equals("C")) {
				// This branch is reached if the "click" action was chosen
				SquareState result = board.click(row - 1, col - 1);
				if (result == SquareState.DEAD && firstMove) {
					// if the player clicked on a bomb on the first move,
					// we should get a different board so that doesn't happen
					while (result == SquareState.DEAD) {
						board = getNewBoardQuietly();
						result = board.click(row - 1, col - 1);
					}
				} else if (result == SquareState.DEAD) {
					// if the player clicked on a bomb
					state = GameState.LOST;
					System.out.println("Game over");
				} else if (gameWon()) {
					// if the game is won
					state = GameState.WON;
					System.out.println("Victory!");
				} else state = GameState.ACTIVE;
			} else if (action.equals("F")) {
				// This branch is reached if the "flag" action was chosen
				if (board.getStateAt(row - 1, col - 1) == SquareState.UNKNOWN ||
						board.getStateAt(row - 1, col - 1) == SquareState.FLAGGED) {
					board.flag(row - 1, col - 1);
				}
			} else throw new InvalidActionException(userInput);
			System.out.println(board);
		} catch (PatternSyntaxException | NumberFormatException |
				 ArrayIndexOutOfBoundsException | InvalidActionException ex1) {
			// this block is reached if the input String couldn't be split by commas
			if (userInput.toUpperCase().equals("X")) state = GameState.CANCELLED;
			else System.out.println("Invalid syntax\nCorrect syntax: row,col,action");
		} catch (IllegalArgumentException iae) {
			System.out.println("row/column values chosen out of bounds");
		}

		return state;
	}

	/** gameWon - checks to see if the game has been won.
	 *
	 * @return whether the game has been won.
	 */
	private boolean gameWon() {
		// game has been won if all non-mine squares have been clicked
		// number of non-mine squares is the number of all squares minus the mines
		int numNonMines = heightChosen * widthChosen - numMinesChosen;
		int sumRevealedSquares = 0;
		for (int i = 0; i < heightChosen; i++) {
			for (int j = 0; j < widthChosen; j++) {
				// for each revealed square, increment sumRevealedSquares by one
				if (board.getStateAt(i, j) == SquareState.KNOWN) sumRevealedSquares++;
			}
		}
		// if the sum of all the revealed squares is equal to the number of squares
		// that are not mines, the game has been won
		return sumRevealedSquares == numNonMines;
	}

	/** getNewBoardQuietly - using the current specifications of the user's preferences
	 * for the board, this method creates a new Board without printing anything to the
	 * console. This is useful if the user happens to click on a mine on the first move.
	 *
	 * @return a new Board with the same specifications from the user
	 */
	private Board getNewBoardQuietly() {
		return new Board(heightChosen, widthChosen, numMinesChosen);
	}

	/** askToPlayAgain - if a game is over, this method can create a new board
	 * for a new game.
	 *
	 * @return whether the user chose to continue the game
	 */
	private boolean askToPlayAgain() {
		System.out.println("Play again? y/n");
		boolean validResponse = false;
		String response = "";
		while (!validResponse) {
			System.out.print("> ");
			response = kb.nextLine();
			if (response.toUpperCase().equals("Y")) {
				board = getBoardSpecifications();
				state = GameState.ACTIVE;
				System.out.println(board);
				validResponse = true;
			} else if (response.toUpperCase().equals("N")) validResponse = true;
			else System.out.println("Invalid input");
		}
		return response.toUpperCase().equals("Y");
	}
}
