package minesweeper;

import java.util.Random;

public class Board {
	private static final int DEFAULT_BOARD_HEIGHT = 20;
	private static final int DEFAULT_BOARD_WIDTH = 20;
	private static final int DEFAULT_NUM_MINES = 50;
	private static final int MINE = -1;
	// each board location stores a value 0-8 for how many mines are around it
	// -1 means that that square IS a mine
	private int[][] board;
	private int numMines;
	private Random rgn;
	
	public Board() {
		this.board = new int[DEFAULT_BOARD_HEIGHT][DEFAULT_BOARD_WIDTH];
		this.rgn = new Random();
		this.numMines = DEFAULT_NUM_MINES;
		
		this.placeMines();
		this.fillNumbers();
	}
	
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
	
	private void fillNumbers() {
		// INCOMPLETE: needs to be made more efficient, less wordy
		// should be divided into multiple helper functions
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				// this code executes for each square in the board
				// first let's make sure we're not looking at a mine square
				if (board[row][col] != MINE) {
					// now let's tally up the number of mines in the surrounding 8 squares
					// the minimum number should be 0, and max should be 8
					// if we're in an edge or corner, we should ensure we don't check
					// indices out of bounds of the board 2d array
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
	
	public String toString() {
		String stringRep = "";
		
		stringRep += "+ ";
		for (int i = 0; i < board[0].length; i++) stringRep += "- ";
		stringRep += "+\n";
		
		for (int i = 0; i < board.length; i++) {
			stringRep += "| ";
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 0) stringRep += "  ";
				else if (board[i][j] == MINE) stringRep += "* ";
				else stringRep += Integer.toString(board[i][j]) + ' ';
			}
			stringRep += "|\n";
		}

		stringRep += "+ ";
		for (int i = 0; i < board[0].length; i++) stringRep += "- ";
		stringRep += "+\n";
		
		return stringRep;
	}
}
