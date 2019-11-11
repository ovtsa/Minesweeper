package minesweeper;

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
	
	/** The default constructor creates a Board of size 20x20 with 50
	 * randomly-placed MINEs, so 1/8 of the squares are MINEs, an easy game.
	 */
	public Board() {
		this.board = new int[DEFAULT_BOARD_HEIGHT][DEFAULT_BOARD_WIDTH];
		this.state = new SquareState[DEFAULT_BOARD_HEIGHT][DEFAULT_BOARD_WIDTH];
		this.rgn = new Random();
		this.numMines = DEFAULT_NUM_MINES;
		
		this.initializeSquareStates();
		this.placeMines();
		this.fillNumbers();
	}
	
	public Board(int height, int width, int numMines) {
		this.board = new int[height][width];
		this.state = new SquareState[height][width];
		this.rgn = new Random();
		this.numMines = numMines;
		
		this.initializeSquareStates();
		this.placeMines();
		this.fillNumbers();
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
	
	/** placeMines - places numMines MINEs on the board. */
	private void placeMines() {
		assert numMines <= board.length * board[0].length;
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
	
	/** revealBoard - sets all squares to being visible using the state[][] array. */
	public void revealBoard() {
		for (int row = 0; row < state.length; row++) {
			for (int col = 0; col < state[row].length; col++) {
				state[row][col] = SquareState.KNOWN;
			}
		}
	}
	
	/** toString - creates a visual representation of a  Minesweeper Board.
	 * 
	 * @return the Board in String form
	 */
	public String toString() {
		// Beginning with empty String
		String stringRep = "";
		
		/* Corners should be pluses.
		 * 
		 * All items placed horizontally are followed by a space, because
		 * most terminal windows give more space between rows than columns
		 * of text.
		 */
		stringRep += "+ ";
		// horizontal borders are represented by dashes
		for (int i = 0; i < board[0].length; i++) stringRep += "- ";
		stringRep += "+\n";
		
		for (int i = 0; i < board.length; i++) {
			stringRep += "| ";
			for (int j = 0; j < board[i].length; j++) {
				if (state[i][j] == SquareState.KNOWN) {
					// squares bordering no MINEs will be represented as spaces
					if (board[i][j] == 0) stringRep += "  ";
					// squares that are MINEs will be represented as asterisks *
					else if (board[i][j] == MINE) stringRep += "* ";
					else stringRep += Integer.toString(board[i][j]) + ' ';
				} else if (state[i][j] == SquareState.UNKNOWN) {
					stringRep += "? ";
				} else if (state[i][j] == SquareState.FLAGGED) {
					stringRep += "! ";
				} else if (state[i][j] == SquareState.DEAD) {
					stringRep += "F ";
				}
			}
			stringRep += "|\n";
		}

		// Repeating the horizontal line process
		stringRep += "+ ";
		for (int i = 0; i < board[0].length; i++) stringRep += "- ";
		stringRep += "+\n";
		
		return stringRep;
	}
}
