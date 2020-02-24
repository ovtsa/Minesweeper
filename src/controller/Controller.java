package controller;

import game.Game;
import gui.MinesweeperApp;
import javafx.application.Application;

public class Controller {
		//Game game;

		public Controller() {
				//this.game = new Game();
		}

		/** A translator function from gui to game of a click on a gridbutton
		 *
		 * @param row - the row index of the gameButton being clicked on
		 * @param col - the column index of the gameButton being clicked on
		 * @return the value on the gridButton after click() is executed
		 */
		public int gridButtonClick(int row, int col) {
				System.out.printf("Controller.gridButtonClick(%d, %d)\n", row, col);
				return 0;
		}

		public int gameButtonClick() {
				System.out.printf("Controller.gameButtonClick()\n");
				return 0;
		}

		public static void main(String[] args) {
				Application.launch(MinesweeperApp.class, args);
		}
}
