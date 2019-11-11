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
	
	// each board location stores a value 0-8 for how many mines are around it
	private int[][] board;
	// Any board will have a set number of mines related to the difficulty of the game
	private int numMines;
	// A random number generator rgn is used to place mines in random locations
	private Random rgn;
	
	/** The default constructor creates a Board of size 20x20 with 50
	 * randomly-placed mines, so 1/8 of the squares are MINEs, an easy game.
	 */
	public Board() {
		this.board = new int[DEFAULT_BOARD_HEIGHT][DEFAULT_BOARD_WIDTH];
		this.rgn = new Random();
		this.numMines = DEFAULT_NUM_MINES;
		
		this.placeMines();
		this.fillNumbers();
	}
	
	/** placeMines - places numMines MINEs on the board. */
	private void placeMines() {
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
		// INCOMPLETE: needs to be made more efficient, less wordy
		// should be divided into multiple helper functions
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				/* this code executes for each square in the board
				 * first let's make sure we're not looking at a mine square */
				if (board[row][col] != MINE) {
					/* now let's tally up the number of mines in the surrounding 8 squares
					 * the minimum number should be 0, and max should be 8
					 * if we're in an edge or corner, we should ensure we don't check
					 * indices out of bounds of the board 2d array
					 */
					int sumSurroundingMines = 0;
					if (row == 0 && col == 0) {
						// top left corner case
						if (board[row][col + 1] == MINE) sumSurroundingMines++;
						if (board[row + 1][col] == MINE) sumSurroundingMines++;
						if (board[row + 1][col + 1] == MINE) sumSurroundingMines++;
					} else if (row == 0 && col == board[row].length - 1) {
						// top right corner case
						if (board[row][col - 1] == MINE) sumSurroundingMines++;
						if (board[row + 1][col] == MINE) sumSurroundingMines++;
						if (board[row + 1][col - 1] == MINE) sumSurroundingMines++;
					} else if (row == 0) {
						// catch-all top row case
						if (board[row][col - 1] == MINE) sumSurroundingMines++;
						if (board[row][col + 1] == MINE) sumSurroundingMines++;
						if (board[row + 1][col - 1] == MINE) sumSurroundingMines++;
						if (board[row + 1][col] == MINE) sumSurroundingMines++;
						if (board[row + 1][col + 1] == MINE) sumSurroundingMines++;
					} else if (row == board.length - 1 && col == 0) {
						// bottom left corner case
						if (board[row - 1][col] == MINE) sumSurroundingMines++;
						if (board[row - 1][col + 1] == MINE) sumSurroundingMines++;
						if (board[row][col + 1] == MINE) sumSurroundingMines++;
					} else if (row == board.length - 1 && col == board[row].length - 1) {
						// bottom right corner case
						if (board[row - 1][col - 1] == MINE) sumSurroundingMines++;
						if (board[row - 1][col] == MINE) sumSurroundingMines++;
						if (board[row][col - 1] == MINE) sumSurroundingMines++;
					} else if (row == board.length - 1) {
						// catch-all bottom row case
						if (board[row - 1][col - 1] == MINE) sumSurroundingMines++;
						if (board[row - 1][col] == MINE) sumSurroundingMines++;
						if (board[row - 1][col + 1] == MINE) sumSurroundingMines++;
						if (board[row][col - 1] == MINE) sumSurroundingMines++;
						if (board[row][col + 1] == MINE) sumSurroundingMines++;
					} else if (col == 0) {
						// catch-all leftmost column case
						if (board[row - 1][col] == MINE) sumSurroundingMines++;
						if (board[row - 1][col + 1] == MINE) sumSurroundingMines++;
						if (board[row][col + 1] == MINE) sumSurroundingMines++;
						if (board[row + 1][col] == MINE) sumSurroundingMines++;
						if (board[row + 1][col + 1] == MINE) sumSurroundingMines++;
					} else if (col == board[row].length - 1) {
						// catch-all rightmost column case
						if (board[row - 1][col - 1] == MINE) sumSurroundingMines++;
						if (board[row - 1][col] == MINE) sumSurroundingMines++;
						if (board[row][col - 1] == MINE) sumSurroundingMines++;
						if (board[row + 1][col - 1] == MINE) sumSurroundingMines++;
						if (board[row + 1][col] == MINE) sumSurroundingMines++;
					} else {
						// all other squares case
						if (board[row - 1][col - 1] == MINE) sumSurroundingMines++;
						if (board[row - 1][col] == MINE) sumSurroundingMines++;
						if (board[row - 1][col + 1] == MINE) sumSurroundingMines++;
						if (board[row][col - 1] == MINE) sumSurroundingMines++;
						if (board[row][col + 1] == MINE) sumSurroundingMines++;
						if (board[row + 1][col - 1] == MINE) sumSurroundingMines++;
						if (board[row + 1][col] == MINE) sumSurroundingMines++;
						if (board[row + 1][col + 1] == MINE) sumSurroundingMines++;
					}
					board[row][col] = sumSurroundingMines;
				}
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
				// squares bordering no mines will be represented as spaces
				if (board[i][j] == 0) stringRep += "  ";
				// squares that are mines will be represented as asterisks *
				else if (board[i][j] == MINE) stringRep += "* ";
				else stringRep += Integer.toString(board[i][j]) + ' ';
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
