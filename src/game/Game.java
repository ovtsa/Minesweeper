package game;

import gui.GameButton;
import gui.GridButton;

public class Game {
    private GameButton guiGameButton;
    private GridButton[][] guiGridButtons;
    private Board board;
    private boolean won;
    private boolean lost;

    public Game(int height, int width, int numMines,
                GameButton guiGameButton, GridButton[][] guiGridButtons) {
        this.board = new Board(height, width, numMines);
        this.guiGameButton = guiGameButton;
        this.guiGridButtons = guiGridButtons;
        this.won = false;
        this.lost = false;
    }

    public void click(int row, int col) {
        if (board.isMine(row, col)) {
            guiGridButtons[row][col].setImage("mineDeath");
            guiGridButtons[row][col].setStatus(true, false, true);
            guiGameButton.setImage("dead");
            this.lost = true;
        }
    }

    public void reset() {
        this.board = new Board(board.getHeight(), board.getWidth(), board.getNumMines());
        this.won = false;
        this.lost = false;
        for (int i = 0; i < guiGridButtons.length; i++) {
            for (int j = 0; j < guiGridButtons[0].length; j++) {
                guiGridButtons[i][j].setStatus(false, false, false);
                guiGridButtons[i][j].setImage("unknown");
            }
        }
    }
}
