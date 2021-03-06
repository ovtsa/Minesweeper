package game;

import gui.GameButton;
import gui.GridButton;
import gui.NumberField;

public class Game {
    private GameButton guiGameButton;
    private GridButton[][] guiGridButtons;
    private NumberField guiMineCounterNumbers;
    private NumberField guiTimerNumbers;
    private Board board;
    private boolean won;
    private boolean lost;
    private TimerThread timer;

    public Game(int height, int width, int numMines,
                GameButton guiGameButton, GridButton[][] guiGridButtons,
                NumberField guiTimerNumbers, NumberField guiMineCounterNumbers) {
        this.board = new Board(height, width, numMines);
        this.guiGameButton = guiGameButton;
        this.guiGridButtons = guiGridButtons;
        this.guiTimerNumbers = guiTimerNumbers;
        this.guiMineCounterNumbers = guiMineCounterNumbers;
        this.won = false;
        this.lost = false;
        this.timer = new TimerThread(this.guiTimerNumbers);
    }

    public void startTimerThread() {
        timer.start();
    }

    public void click(int row, int col) {
        if (board.isMine(row, col)) {
            die(row, col);
        } else {
            int boardValue = board.getValueAt(row, col);
            guiGridButtons[row][col].setImage(Integer.toString(boardValue));
            guiGridButtons[row][col].setStatus(true, false, false);
            board.setClicked(row, col);
            if (boardValue == 0) {
                // recursive logic for clicking
                // top-left corner
                if (row == 0 && col == 0) {
                    if (!board.isKnown(row, col + 1) && !board.isFlagged(row, col + 1))
                        click(row, col + 1);
                    if (!board.isKnown(row + 1, col) && !board.isFlagged(row + 1, col))
                        click(row + 1, col);
                    if (!board.isKnown(row + 1, col + 1) && !board.isFlagged(row + 1, col + 1))
                        click(row + 1, col + 1);
                // top-middle
                } else if (row == 0 && col > 0 && col < guiGridButtons[0].length - 1) {
                    if (!board.isKnown(row, col - 1) && !board.isFlagged(row, col - 1))
                        click(row, col - 1);
                    if (!board.isKnown(row, col + 1) && !board.isFlagged(row, col + 1))
                        click(row, col + 1);
                    if (!board.isKnown(row + 1, col - 1) && !board.isFlagged(row + 1, col - 1))
                        click(row + 1, col - 1);
                    if (!board.isKnown(row + 1, col) && !board.isFlagged(row + 1, col))
                        click(row + 1, col);
                    if (!board.isKnown(row + 1, col + 1) && !board.isFlagged(row + 1, col + 1))
                        click(row + 1, col + 1);
                // top-right corner
                } else if (row == 0 && col == guiGridButtons[0].length - 1) {
                    if (!board.isKnown(row, col - 1) && !board.isFlagged(row, col - 1))
                        click(row, col - 1);
                    if (!board.isKnown(row + 1, col) && !board.isFlagged(row + 1, col))
                        click(row + 1, col);
                    if (!board.isKnown(row + 1, col - 1) && !board.isFlagged(row + 1, col - 1))
                        click(row + 1, col - 1);
                // middle-left
                } else if (row > 0 && row < guiGridButtons.length - 1 && col == 0) {
                    if (!board.isKnown(row - 1, col) && !board.isFlagged(row - 1, col))
                        click(row - 1, col);
                    if (!board.isKnown(row - 1, col + 1) && !board.isFlagged(row - 1, col + 1))
                        click(row - 1, col + 1);
                    if (!board.isKnown(row, col + 1) && !board.isFlagged(row, col + 1))
                        click(row, col + 1);
                    if (!board.isKnown(row + 1, col) && !board.isFlagged(row + 1, col))
                        click(row + 1, col);
                    if (!board.isKnown(row + 1, col + 1) && !board.isFlagged(row + 1, col + 1))
                        click(row + 1, col + 1);
                // true middle
                } else if (row > 0 && row < guiGridButtons.length - 1 &&
                           col > 0 && col < guiGridButtons[0].length - 1) {
                    if (!board.isKnown(row - 1, col - 1) && !board.isFlagged(row - 1, col - 1))
                        click(row - 1, col - 1);
                    if (!board.isKnown(row - 1, col) && !board.isFlagged(row - 1, col))
                        click(row - 1, col);
                    if (!board.isKnown(row - 1, col + 1) && !board.isFlagged(row - 1, col + 1))
                        click(row - 1, col + 1);
                    if (!board.isKnown(row, col - 1) && !board.isFlagged(row, col - 1))
                        click(row, col - 1);
                    if (!board.isKnown(row, col + 1) && !board.isFlagged(row, col + 1))
                        click(row, col + 1);
                    if (!board.isKnown(row + 1, col - 1) && !board.isFlagged(row + 1, col - 1))
                        click(row + 1, col - 1);
                    if (!board.isKnown(row + 1, col) && !board.isFlagged(row + 1, col))
                        click(row + 1, col);
                    if (!board.isKnown(row + 1, col + 1) && !board.isFlagged(row + 1, col + 1))
                        click(row + 1, col + 1);
                // middle-right
                } else if (row > 0 && row < guiGridButtons.length - 1 &&
                           col == guiGridButtons[0].length - 1) {
                    if (!board.isKnown(row - 1, col - 1) && !board.isFlagged(row - 1, col - 1))
                        click(row - 1, col - 1);
                    if (!board.isKnown(row - 1, col) && !board.isFlagged(row - 1, col))
                        click(row - 1, col);
                    if (!board.isKnown(row, col - 1) && !board.isFlagged(row, col - 1))
                        click(row, col - 1);
                    if (!board.isKnown(row + 1, col - 1) && !board.isFlagged(row + 1, col - 1))
                        click(row + 1, col - 1);
                    if (!board.isKnown(row + 1, col) && !board.isFlagged(row + 1, col))
                        click(row + 1, col);
                // bottom-left
                } else if (row == guiGridButtons.length - 1 && col == 0) {
                    if (!board.isKnown(row - 1, col) && !board.isFlagged(row - 1, col))
                        click(row - 1, col);
                    if (!board.isKnown(row - 1, col + 1) && !board.isFlagged(row - 1, col + 1))
                        click(row - 1, col + 1);
                    if (!board.isKnown(row , col + 1) && !board.isFlagged(row, col + 1))
                        click(row, col + 1);
                // bottom-middle
                } else if (row == guiGridButtons.length - 1 && col > 0 &&
                           col < guiGridButtons[0].length - 1) {
                    if (!board.isKnown(row - 1, col - 1) && !board.isFlagged(row - 1, col - 1))
                        click(row - 1, col - 1);
                    if (!board.isKnown(row - 1, col) && !board.isFlagged(row - 1, col))
                        click(row - 1, col);
                    if (!board.isKnown(row - 1, col + 1) && !board.isFlagged(row - 1, col + 1))
                        click(row - 1, col + 1);
                    if (!board.isKnown(row, col - 1) && !board.isFlagged(row, col - 1))
                        click(row, col - 1);
                    if (!board.isKnown(row, col + 1) && !board.isFlagged(row, col + 1))
                        click(row, col + 1);
                // bottom-right
                } else if (row == guiGridButtons.length - 1 && col == guiGridButtons[0].length - 1) {
                    if (!board.isKnown(row - 1, col - 1) && !board.isFlagged(row - 1, col - 1))
                        click(row - 1, col - 1);
                    if (!board.isKnown(row - 1, col) && !board.isFlagged(row - 1, col))
                        click(row - 1, col);
                    if (!board.isKnown(row, col - 1) && !board.isFlagged(row, col - 1))
                        click(row, col - 1);
                }
            }
        }
        isWon();
    }

    // flags a GridButton
    public void flag(int row, int col) {
        System.out.printf("Game flagging r%d c%d\n", row, col);
        guiGridButtons[row][col].setStatus(false, true, false);
        guiGridButtons[row][col].setImage("flagged");
        guiMineCounterNumbers.setNumber(guiMineCounterNumbers.getValue() - 1);
        board.flag(row, col);
    }

    public void unflag(int row, int col) {
        System.out.printf("Game unflagging r%d c%d\n", row, col);
        guiGridButtons[row][col].setStatus(false, false, false);
        guiGridButtons[row][col].setImage("unknown");
        guiMineCounterNumbers.setNumber(guiMineCounterNumbers.getValue() + 1);
        board.flag(row, col);
    }

    public void reset() {
        this.board = new Board(board.getHeight(), board.getWidth(), board.getNumMines());
        this.guiMineCounterNumbers.setNumber(board.getNumMines());
        this.timer.stopRunning();
        this.guiTimerNumbers.setNumber(0);
        this.timer = new TimerThread(this.guiTimerNumbers);
        this.won = false;
        this.lost = false;
        for (int i = 0; i < guiGridButtons.length; i++) {
            for (int j = 0; j < guiGridButtons[0].length; j++) {
                guiGridButtons[i][j].setStatus(false, false, false);
                guiGridButtons[i][j].setImage("unknown");
            }
        }
        GridButton.setFirstClick(true);
    }

    private boolean isWon() {
        // game is won if all squares that aren't mines are known
        // search through board to find an unknown non-mine
        for (int i = 0; i < guiGridButtons.length; i++) {
            for (int j = 0; j < guiGridButtons[0].length; j++) {
                if (!board.isKnown(i, j) && !board.isFlagged(i, j) && !board.isMine(i, j))
                    return false;
            }
        }
        timer.stopRunning();
        guiGameButton.setImage("won");
        GridButton.setOver(true);
        this.won = true;
        for (int i = 0; i < guiGridButtons.length; i++) {
            for (int j = 0; j < guiGridButtons[0].length; j++) {
                if (!board.isKnown(i, j) && !board.isFlagged(i, j))
                    guiGridButtons[i][j].flag();
            }
        }
        return true;
    }

    private void die(int row, int col) {
        timer.stopRunning();
        guiGridButtons[row][col].setImage("mineDeath");
        guiGridButtons[row][col].setStatus(true, false, true);
        guiGameButton.setImage("dead");
        this.lost = true;

        for (int i = 0; i < guiGridButtons.length; i++) {
            for (int j = 0; j < guiGridButtons[0].length; j++) {
                if (board.isMine(i, j) && !(i == row && j == col)) {
                    if (!board.isFlagged(i, j)) {
                        guiGridButtons[i][j].setImage("mine");
                        guiGridButtons[i][j].setStatus(true, false, true);
                    }
                } else if (board.isFlagged(i, j)) {
                    guiGridButtons[i][j].setImage("flaggedWrong");
                    guiGridButtons[i][j].setStatus(true, true, true);
                }
            }
        }
    }
}
