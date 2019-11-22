package minesweeper;

import java.util.Scanner;

public class Game {
	private static final int EASY_HEIGHT = 12;
	private static final int EASY_WIDTH = 15;
	private static final int EASY_NUM_MINES = 20;
	private static final int NORMAL_HEIGHT = 20;
	private static final int NORMAL_WIDTH = 30;
	private static final int NORMAL_NUM_MINES = 75;
	private static final int HARD_HEIGHT = 30;
	private static final int HARD_WIDTH = 50;
	private static final int HARD_NUM_MINES = 400;
	
	private Scanner kb;
	private Board board;
	private GameState state;
	private int heightChosen;
	private int widthChosen;
	private int numMinesChosen;
	
	
	public Game() {
		printCredits();
		
		this.kb = new Scanner(System.in);
		this.state = GameState.ACTIVE;
		// getBoardSpecifications sets diff as well
		this.board = getBoardSpecifications();
		System.out.println(this.board);
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.play();
	}
	
	private void printCredits() {
		System.out.println("Minesweeper - Nathan Jobe");
		// TODO: Set a "last modified" date
		System.out.println("Last modified 15/11/2019");
	}
	
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
		System.out.print("Type selection: ");
		String response = kb.nextLine().trim().toUpperCase();
		Board gameBoard;
		
		if (response.equals("EASY")) {
			gameBoard = new Board(EASY_HEIGHT, EASY_WIDTH,
								 EASY_NUM_MINES);
			heightChosen = EASY_HEIGHT;
			widthChosen = EASY_WIDTH;
			numMinesChosen = EASY_NUM_MINES;
		} else if (response.equals("NORMAL")) {
			gameBoard = new Board(NORMAL_HEIGHT, NORMAL_WIDTH,
								  NORMAL_NUM_MINES);
			heightChosen = NORMAL_HEIGHT;
			widthChosen = NORMAL_WIDTH;
			numMinesChosen = NORMAL_NUM_MINES;
		} else if (response.equals("HARD")) {
			gameBoard = new Board(HARD_HEIGHT, HARD_WIDTH,
								  HARD_NUM_MINES);
			heightChosen = HARD_HEIGHT;
			widthChosen = HARD_WIDTH;
			numMinesChosen = HARD_NUM_MINES;
		} else if (response.equals("CUSTOM")) {
			System.out.print("height: ");
			int boardHeight = kb.nextInt();
			System.out.print("width:  ");
			int boardWidth = kb.nextInt();
			System.out.print("mines:  ");
			int numMines = kb.nextInt();
			gameBoard = new Board(boardHeight, boardWidth, numMines);
		} else {
			System.out.println("Invalid response");
			System.out.println("Selecting default NORMAL difficulty");
			gameBoard = new Board(NORMAL_HEIGHT, NORMAL_WIDTH,
								  NORMAL_NUM_MINES);
		}
		
		return gameBoard;
	}
	
	public void play() {
		System.out.println("Syntax: row,col,action");
		System.out.println("Actions: \"c\" for \"click,\" \"f\" for \"flag\" or \"unflag\"");
		state = makeMove(true);
		while (state == GameState.ACTIVE) {
			makeMove(false);
		}
	}
	
	private GameState makeMove(boolean firstMove) {
		// to implement: exception checking
		System.out.print("> ");
		String[] choices = kb.nextLine().split(",");
		int row = Integer.parseInt(choices[0]);
		int col = Integer.parseInt(choices[1]);
		String action = choices[2];
		if (action.equals("c")) {
			SquareState result = board.click(row - 1, col - 1);
			if (result == SquareState.DEAD && firstMove) {
				while (result == SquareState.DEAD) {
					board = getNewBoardQuietly();
					result = board.click(row - 1, col - 1);
				}
			} else if (result == SquareState.DEAD) {
				state = GameState.LOST;
				System.out.println("Game over");
			} else if (gameWon()) {
				state = GameState.WON;
				System.out.println("Victory!");
			} else state = GameState.ACTIVE;
		} else if (action.equals("f")) {
			if (board.getStateAt(row - 1, col - 1) == SquareState.UNKNOWN ||
					board.getStateAt(row - 1, col - 1) == SquareState.FLAGGED) {
				board.flag(row - 1, col - 1);
			}
		}
		System.out.println(board);
		
		return state;
	}
	
	private boolean gameWon() {
		// game has been won if all non-mine squares have been clicked
		// number of non-mine squares is the number of all squares minus the mines
		int numNonMines = heightChosen * widthChosen - numMinesChosen;
		int sumRevealedSquares = 0;
		for (int i = 0; i < heightChosen; i++) {
			for (int j = 0; j < widthChosen; j++) {
				if (board.getStateAt(i, j) == SquareState.KNOWN) sumRevealedSquares++;
			}
		}
		return sumRevealedSquares == numNonMines;
	}
	
	private Board getNewBoardQuietly() {
		return new Board(heightChosen, widthChosen, numMinesChosen);
	}
}
