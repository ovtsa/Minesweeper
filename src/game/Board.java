package game;

import java.util.Random;

/** Board - an organization of 2D arrays that stores information regarding
 * the locations of mines, and the states of certain squares -
 * visible, invisible, flagged, etc.
 *
 * @author Nathan Jobe
 * @version %I%, %G%
 */
public class Board {
	// Default values for the shape of a Board and its concentration of mines
	private static final int DEFAULT_BOARD_HEIGHT = 20;
	private static final int DEFAULT_BOARD_WIDTH = 20;
	private static final int DEFAULT_NUM_MINES = 50;
	// -1 is chosen to represent a MINE
	private static final int MINE = -1;

	// each board location stores a value 0-8 for how many MINEs are around it
	private int[][] board;
	// each board location is either UNKNOWN, KNOWN, a revealed MINE, or you're DEAD
	private SquareState[][] state;
	// Any board will have a set number of MINEs related to the difficulty of the game
	private int numMines;
	// A random number generator rgn is used to place MINEs in random locations
	private Random rgn;
	// Board needs to know if the player has clicked on a mine so it can show locations
	private boolean hasDied;

	/** The default constructor creates a Board of size 20x20 with 50
	 * randomly-placed MINEs, so 1/8 of the squares are MINEs, an easy game.
	 */
	public Board() {
		this.board = new int[DEFAULT_BOARD_HEIGHT][DEFAULT_BOARD_WIDTH];
		this.state = new SquareState[DEFAULT_BOARD_HEIGHT][DEFAULT_BOARD_WIDTH];
		this.rgn = new Random();
		this.numMines = DEFAULT_NUM_MINES;
		this.hasDied = false;

		this.initializeSquareStates();
		this.placeMines();
		this.fillNumbers();
	}

	/** This constructor allows you to create a Board of a modified size.
	 *
	 * @param height is the number of rows on the board
	 * @param width is the number of columns on the board
	 * @param numMines is the number of mines the board should have
	 */
	public Board(int height, int width, int numMines) {
		this.board = new int[height][width];
		this.state = new SquareState[height][width];
		this.rgn = new Random();
		this.numMines = numMines;
		this.hasDied = false;

		this.initializeSquareStates();
		this.placeMines();
		this.fillNumbers();
	}

	/** click - a sometimes recursive function that simulates a "click" on a square
	 *
	 * @param row is the row index to be clicked upon
	 * @param col is the column index to  be clicked upon
	 * @return the revealed SquareState of that square after clicking
	 */
	public SquareState click(int row, int col) {
		if (board[row][col] == MINE && state[row][col] != SquareState.FLAGGED) {
			// this block executes if you clicked on an unflagged mine
			state[row][col] = SquareState.DEAD;
			hasDied = true;
		} else if (state[row][col] != SquareState.FLAGGED) {
			// this block executes if you clicked on a safe square
			state[row][col] = SquareState.KNOWN;
			/* if this square had no surrounding mines, you know it's safe to click the
			   surrounding squares too */
			if (board[row][col] == 0) {
				// recurse on surroundings, because you know it's safe
				if (row - 1 >= 0 && col - 1 >= 0 && state[row - 1][col - 1] == SquareState.UNKNOWN) click(row - 1, col - 1);
				if (row - 1 >= 0 && state[row - 1][col] == SquareState.UNKNOWN) click(row - 1, col);
				if (row - 1 >= 0 && col + 1 < board[0].length && state[row - 1][col + 1] == SquareState.UNKNOWN) click(row - 1, col + 1);
				if (col - 1 >= 0 && state[row ][col - 1] == SquareState.UNKNOWN) click(row, col - 1);
				if (col + 1 < board[0].length && state[row][col + 1] == SquareState.UNKNOWN) click(row, col + 1);
				if (row + 1 < board.length && col - 1 >= 0 && state[row + 1][col - 1] == SquareState.UNKNOWN) click(row + 1, col - 1);
				if (row + 1 < board.length && state[row + 1][col] == SquareState.UNKNOWN) click(row + 1, col);
				if (row + 1 < board.length && col + 1 < board[0].length && state[row + 1][col + 1] == SquareState.UNKNOWN) click(row + 1, col + 1);
			}
		}
		return state[row][col];
	}

	/** flag - either places or removes a flag at location given
	 *
	 * @param row is the row index of the flag to add/remove
	 * @param col is the column index of the flag to add/remove
	 */
	public void flag(int row, int col) {
		if (state[row][col] == SquareState.UNKNOWN) state[row][col] = SquareState.FLAGGED;
		else if (state[row][col] == SquareState.FLAGGED) state[row][col] = SquareState.UNKNOWN;
	}

	/** getStateAt - gets the state information about the square at location row, col
	 *
	 * @param row the row index to examine
	 * @param col the col index to examine
	 * @return the SquareState at location row, col
	 */
	public SquareState getStateAt(int row, int col) {
		return state[row][col];
	}

	public boolean isMine(int row, int col) {
		return this.board[row][col] == MINE;
	}

	/** initializeSquareStates - sets all values in the state[][] array
	 *  to SquareState.UNKNOWN
	 */
	private void initializeSquareStates() {
		for (int row = 0; row < state.length; row++) {
			for (int col = 0; col < state[row].length; col++) {
				state[row][col] = SquareState.UNKNOWN;
			}
		}
	}

	/** placeMines - places numMines mines randomly across the board.
	 */
	private void placeMines() {
		assert numMines < board.length * board[0].length;
		int i = 0;
		while (i < numMines) {
			int boardRowIndexMineQuery = rgn.nextInt(board.length);
			int boardColIndexMineQuery = rgn.nextInt(board[0].length);
			if (board[boardRowIndexMineQuery][boardColIndexMineQuery] != MINE) {
				board[boardRowIndexMineQuery][boardColIndexMineQuery] = MINE;
				i++;
			}
		}
	}

	/** fillNumbers - given the locations of all MINEs on the board,
	 * fillNumbers calculates the numbers that should be in each square, and
	 * places them there.
	 */
	private void fillNumbers() {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				/* this code executes for each square in the board
				 * first let's make sure we're not looking at a MINE square */
				if (board[row][col] != MINE) {
					/* now let's tally up the number of MINEs in the surrounding 8 squares
					 * the minimum number should be 0, and max should be 8
					 * if we're in an edge or corner, we should ensure we don't check
					 * indices out of bounds of the board 2d array
					 */
					board[row][col] = numMinesAround(row, col);
				}
			}
		}
	}

	/** numMinesAround - a private method that calculates the number of mines
	 * in surrounding squares.
	 *
	 * @param row - the row index whose surrounding squares will be checked
	 * @param col - the column index whose surrounding squares will be checked
	 * @return the number of MINEs found in surrounding squares
	 */
	private int numMinesAround(int row, int col) {
		assert row >= 0 && row < board.length &&
			   col >= 0 && col < board[row].length;

		int sumSurroundingMines = 0;
		// Since Java short-circuits, these won't cause ArrayIndexOutOfBoundsExceptions
		// conditions below both refrain from checking out-of-bounds squares and
		// sum up the number of mines in extant squares
		if (row - 1 >= 0 && col - 1 >= 0 && board[row - 1][col - 1] == MINE) sumSurroundingMines++;
		if (row - 1 >= 0 && board[row - 1][col] == MINE) sumSurroundingMines++;
		if (row - 1 >= 0 && col + 1 < board[row].length && board[row - 1][col + 1] == MINE) sumSurroundingMines++;
		if (col - 1 >= 0 && board[row][col - 1] == MINE) sumSurroundingMines++;
		if (col + 1 < board[row].length && board[row][col + 1] == MINE) sumSurroundingMines++;
		if (row + 1 < board.length && col - 1 >= 0 && board[row + 1][col - 1] == MINE) sumSurroundingMines++;
		if (row + 1 < board.length && board[row + 1][col] == MINE) sumSurroundingMines++;
		if (row + 1 < board.length && col + 1 < board[row].length && board[row + 1][col + 1] == MINE) sumSurroundingMines++;

		return sumSurroundingMines;
	}

	/** reveal - sets the state at each location to SquareState.KNOWN
	 */
	public void reveal() {
		for (int row = 0; row < state.length; row++) {
			for (int col = 0; col < state[row].length; col++) {
				state[row][col] = SquareState.KNOWN;
			}
		}
	}

	/** hide - sets the state at each location to SquareState.UNKNOWN
	 */
	public void hide() {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				state[row][col] = SquareState.UNKNOWN;
			}
		}
	}

	/** revealMinesEndGame - after a player dies, this method can be used to
	 * reveal all the mines that were left on the Board.
	 */
	private void revealMinesEndGame() {
		for (int row = 0; row < state.length; row++) {
			for (int col = 0; col < state[0].length; col++) {
				if (board[row][col] == MINE && state[row][col] != SquareState.DEAD &&
						state[row][col] != SquareState.FLAGGED) {
					state[row][col] = SquareState.KNOWN;
				}
			}
		}
	}

	public int getHeight() {
			return this.board.length;
	}

	public int getWidth() {
			return this.board[0].length;
	}

	public int getNumMines() {
			return this.numMines;
	}

	/** toString - creates a visual representation of a  Minesweeper Board.
	 *
	 * @return the Board in String form
	 */
	public String toString() {
		// if player has died, mines should be revealed and compared to flags
		if (hasDied) revealMinesEndGame();

		// Beginning with empty String
		String stringRep = "\n     ";

		/* Corners should be pluses.
		 *
		 * All items placed horizontally are followed by a space, because
		 * most terminal windows give more space between rows than columns
		 * of text.
		 */
		// labeling columns' tens digits
		for (int i = 1; i <= board[0].length; i++) {
			if (i / 10 != 0) stringRep += Integer.toString(i / 10) + ' ';
			else stringRep += "  ";
		}
		stringRep += "\n     ";
		// labeling columns' ones digits
		for (int i = 1; i <= board[0].length; i++) {
			stringRep += Integer.toString(i % 10) + ' ';
		}

		stringRep += '\n';

		stringRep += "   + ";
		// horizontal borders are represented by dashes
		for (int i = 0; i < board[0].length; i++) stringRep += "- ";
		stringRep += "+\n";

		for (int i = 0; i < board.length; i++) {
			if (i  + 1 < 10) stringRep += " " + Integer.toString(i + 1);
			else stringRep += Integer.toString(i + 1);
			stringRep += " | ";
			for (int j = 0; j < board[i].length; j++) {
				if (state[i][j] == SquareState.KNOWN) {
					// squares bordering no MINEs will be represented as spaces
					if (board[i][j] == 0) stringRep += "  ";
					// squares that are MINEs will be represented as asterisks *
					else if (board[i][j] == MINE) stringRep += "* ";
					else stringRep += Integer.toString(board[i][j]) + ' ';
				} else if (state[i][j] == SquareState.UNKNOWN) {
					stringRep += "\u2588 ";
				} else if (state[i][j] == SquareState.FLAGGED) {
					if (hasDied) {
						if (board[i][j] == MINE) stringRep += "\u256C ";
						else stringRep += "X ";
					} else stringRep += "\u256C ";
				} else if (state[i][j] == SquareState.DEAD) {
					stringRep += "F ";
				}
			}
			stringRep += "| ";
			stringRep += Integer.toString(i + 1) + '\n';
		}

		// Repeating the horizontal line process
		stringRep += "   + ";
		for (int i = 0; i < board[0].length; i++) stringRep += "- ";
		stringRep += "+\n     ";

		for (int i = 1; i <= board[0].length; i++) {
			if (i / 10 != 0) stringRep += Integer.toString(i / 10) + ' ';
			else stringRep += "  ";
		}
		stringRep += "\n     ";
		// labeling columns' ones digits
		for (int i = 1; i <= board[0].length; i++) {
			stringRep += Integer.toString(i % 10) + ' ';
		}
		stringRep += '\n';

		return stringRep;
	}
}
