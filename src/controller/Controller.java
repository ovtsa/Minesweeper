package controller;

import game.Game;
import gui.MinesweeperApp;
import gui.GameButton;
import gui.GridButton;
import javafx.application.Application;

public class Controller {
		private Game game;

		public Controller(int height, int width, int numMines,
											GameButton guiGameButton, GridButton[][] guiGridButtons) {
				this.game = new Game(height, width, numMines, guiGameButton, guiGridButtons);
		}

		/** A translator function from gui to game of a click on a gridbutton
		 *
		 * @param row - the row index of the gameButton being clicked on
		 * @param col - the column index of the gameButton being clicked on
		 * @return the value on the gridButton after click() is executed
		 */
		public int gridButtonClick(int row, int col) {
				System.out.printf("Controller.gridButtonClick(%d, %d)\n", row, col);
				game.click(row, col);
				return 0;
		}

		public void flag(int row, int col) {
				game.flag(row, col);
		}

		public void unflag(int row, int col) {
				game.unflag(row, col);
		}

		public int gameButtonClick() {
				System.out.printf("Controller.gameButtonClick()\n");
				game.reset();
				return 0;
		}

		public static void main(String[] args) {
				Application.launch(MinesweeperApp.class, args);
		}
}
