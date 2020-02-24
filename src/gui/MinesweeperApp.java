package gui;

import controller.Controller;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;

public class MinesweeperApp extends Application {
	// Always useful fields
	private Stage stage;
	private Scene currentScene;
	private Controller controller;

	// menuScene useful fields
	private Scene menuScene;
	private ChoiceBox<String> menuDifficultyChoiceBox;
	private Slider menuHeightSlider;
	private Slider menuWidthSlider;
	private Slider menuNumMinesSlider;
	private Button menuContinueButton;

	// gameScene useful fields
	private Scene gameScene;
	private Button gameBackButton;
	private GameButton gameButton;
	private NumberField mineCounterNumbers;
	private NumberField timerNumbers;
	private GridButton[][] gridButtons;

	@Override
	public void start(Stage primaryStage) {
		initializeStage(primaryStage);
		initializeMenu();

		switchStageToScene(primaryStage, menuScene);
	}

	public Scene getMenuScene() {
		return menuScene;
	}

	public String getMenuDifficultyChoice() {
		return menuDifficultyChoiceBox.getValue();
	}

	public int getMenuHeightSliderValue() {
		return menuHeightSlider.valueProperty().intValue();
	}

	public int getMenuWidthSliderValue() {
		return menuWidthSlider.valueProperty().intValue();
	}

	public int getMenuNumMinesSliderValue() {
		return menuNumMinesSlider.valueProperty().intValue();
	}

	private void initializeStage(Stage primaryStage) {
		primaryStage.setTitle("Minesweeper");
		primaryStage.initStyle(StageStyle.DECORATED);
		this.stage = primaryStage;
	}

	private void switchStageToScene(Stage stage, Scene scene) {
		stage.setMinHeight(0);
		stage.setMinWidth(0);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
		stage.centerOnScreen();
		stage.setMinHeight(stage.getHeight());
		stage.setMinWidth(stage.getWidth());
		currentScene = scene;
	}

	private void initializeMenu() {
		// TODO: Shorten this method
		menuDifficultyChoiceBox = new ChoiceBox<String>();
		menuDifficultyChoiceBox.getItems().add("EASY");
		menuDifficultyChoiceBox.getItems().add("MODERATE");
		menuDifficultyChoiceBox.getItems().add("HARD");
		menuDifficultyChoiceBox.getItems().add("CUSTOM");
		menuDifficultyChoiceBox.getSelectionModel().select(1);

		menuNumMinesSlider = new Slider(0, 255, 40);
		Label numMinesLabel = new Label("40");
		menuNumMinesSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue,
								Number oldValue, Number newValue) {
				numMinesLabel.textProperty().setValue(String.valueOf(newValue.intValue()));
				menuDifficultyChoiceBox.getSelectionModel().select(3);
			}
		});

		menuHeightSlider = new Slider(1, 50, 16);
		Label heightLabel = new Label("16");
		// This code makes the heightLabel actively listen to the heightSlider value
		menuHeightSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue,
								Number oldValue, Number newValue) {
				heightLabel.textProperty().setValue(String.valueOf(newValue.intValue()));
				int newMaxNumMines = newValue.intValue() * (int) menuWidthSlider.getValue() - 1;
				menuNumMinesSlider.setMax(newMaxNumMines);
				menuDifficultyChoiceBox.getSelectionModel().select(3);
			}
		});

		menuWidthSlider = new Slider(8, 50, 16);
		Label widthLabel = new Label("16");
		menuWidthSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue,
								Number oldValue, Number newValue) {
				widthLabel.textProperty().setValue(String.valueOf(newValue.intValue()));
				int newMaxNumMines = newValue.intValue() * (int) menuHeightSlider.getValue() - 1;
				menuNumMinesSlider.setMax(newMaxNumMines);
				menuDifficultyChoiceBox.getSelectionModel().select(3);
			}
		});


		menuDifficultyChoiceBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue,
								String oldValue, String newValue) {
				if (newValue.equals("EASY")) {
					menuHeightSlider.setValue(9);
					menuWidthSlider.setValue(9);
					menuNumMinesSlider.setValue(10);
					menuDifficultyChoiceBox.getSelectionModel().select(0);
				} else if (newValue.equals("MODERATE")) {
					menuHeightSlider.setValue(16);
					menuWidthSlider.setValue(16);
					menuNumMinesSlider.setValue(40);
					menuDifficultyChoiceBox.getSelectionModel().select(1);
				} else if (newValue.equals("HARD")) {
					menuHeightSlider.setValue(16);
					menuWidthSlider.setValue(30);
					menuNumMinesSlider.setValue(99);
					menuDifficultyChoiceBox.getSelectionModel().select(2);
				}
			}
		});

		int padding = 30;
		GridPane menuGrid = new GridPane();
		menuGrid.setPadding(new Insets(padding, padding, padding, padding));
		menuGrid.setHgap(30);
		menuGrid.setVgap(30);
		menuGrid.setAlignment(Pos.CENTER);
		menuGrid.add(new Label("Difficulty: "), 0, 0, 1, 1);
		menuGrid.add(menuDifficultyChoiceBox,   1, 0, 1, 1);
		menuGrid.add(new Label("Height: "),     0, 1, 1, 1);
		menuGrid.add(menuHeightSlider,          1, 1, 1, 1);
		menuGrid.add(heightLabel,               2, 1, 2, 1);
		menuGrid.add(new Label("Width: "),      0, 2, 1, 1);
		menuGrid.add(menuWidthSlider,           1, 2, 1, 1);
		menuGrid.add(widthLabel,                2, 2, 2, 1);
		menuGrid.add(new Label("Mine count: "), 0, 3, 1, 1);
		menuGrid.add(menuNumMinesSlider,        1, 3, 1, 1);
		menuGrid.add(numMinesLabel,             2, 3, 3, 1);

		menuContinueButton = new Button("Continue");
		menuContinueButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				initializeGame(getMenuHeightSliderValue(),
							   getMenuWidthSliderValue(), getMenuNumMinesSliderValue());

				switchStageToScene(stage, gameScene);
			}
		});

		VBox menuSceneDivider = new VBox(20, menuGrid,
										new Separator(Orientation.HORIZONTAL),
										menuContinueButton);
		menuSceneDivider.setPadding(new Insets(0, 0, 20, 0));
		menuSceneDivider.setAlignment(Pos.CENTER);

		menuScene = new Scene(menuSceneDivider);
	}

	private void initializeGame(int height, int width, int numMines) {
		// This code creates the button to return to the menu scene
		gameBackButton = new Button("Back");
		gameBackButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				switchStageToScene(stage, menuScene);
			}
		});

		// This separator divides the back button from the game GUI
		Separator gameSeparator = new Separator();
		gameSeparator.setPadding(new Insets(10, 0, 10, 0));


		// This code creates the top border of the game
		HBox topBorder = new HBox();
		topBorder.getChildren().add(new ImageView(new Image("textures/tlCorner_20x20.png")));
		for (int i = 0; i < width; i++) {
			topBorder.getChildren().add(new ImageView(new Image("textures/hBorder_32x20.png")));
		}
		topBorder.getChildren().add(new ImageView(new Image("textures/trCorner_20x20.png")));

		// This code creates the "menu bar," with the game button, timer, and mine count
		VBox lMenuBorder = new VBox(new ImageView(new Image("textures/vBorder_20x32.png")),
								    new ImageView(new Image("textures/vBorder_20x32.png")));
		mineCounterNumbers = new NumberField(numMines);
		mineCounterNumbers.setPadding(new Insets(8, 12, 10, 12));
		Region menuBuffer1 = new Region();
		Region menuBuffer2 = new Region();
		HBox.setHgrow(menuBuffer1, Priority.ALWAYS);
		HBox.setHgrow(menuBuffer2, Priority.ALWAYS);

		gameButton = new GameButton(this.controller);
		gameButton.setPadding(new Insets(6, 0, 0, 0));

		timerNumbers = new NumberField(0);
		timerNumbers.setPadding(new Insets(8, 12, 10, 12));

		HBox gameMenuBar = new HBox(mineCounterNumbers, menuBuffer1, gameButton,
								    menuBuffer2, timerNumbers);
		gameMenuBar.setBackground(new Background(new BackgroundFill(Color.rgb(192, 192, 192), null, null)));
		gameMenuBar.setPrefWidth(gameMenuBar.getWidth() + 32 * width);
		VBox rMenuBorder = new VBox(new ImageView(new Image("textures/vBorder_20x32.png")),
			    new ImageView(new Image("textures/vBorder_20x32.png")));
		HBox menuRow = new HBox(lMenuBorder, gameMenuBar, rMenuBorder);

		// Middle dividing row
		HBox middleBorder = new HBox();
		middleBorder.getChildren().add(new ImageView(new Image("textures/mlCorner_20x20.png")));
		for (int i = 0; i < width; i++) {
			middleBorder.getChildren().add(new ImageView(new Image("textures/hBorder_32x20.png")));
		}
		middleBorder.getChildren().add(new ImageView(new Image("textures/mrCorner_20x20.png")));

		// game vertical borders
		VBox lGameBorder = new VBox();
		VBox rGameBorder = new VBox();
		for (int i = 0; i < height; i++) {
			lGameBorder.getChildren().add(new ImageView(new Image("textures/vBorder_20x32.png")));
			rGameBorder.getChildren().add(new ImageView(new Image("textures/vBorder_20x32.png")));
		}

		// game grid
		GridPane gameGrid = new GridPane();
		gridButtons = new GridButton[height][width];
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				gridButtons[row][col] = new GridButton(row, col);
				gameGrid.add(gridButtons[row][col], col, row);
			}
		}

		// define this game's controller
		this.controller = new Controller(height, width, numMines, gameButton, gridButtons);
		gameButton.giveController(controller);
		gridButtons[0][0].giveController(controller);
		gridButtons[0][0].giveGameButton(gameButton);

		// game HBox
		HBox gameBox = new HBox(lGameBorder, gameGrid, rGameBorder);

		// bottom border
		HBox bottomBorder = new HBox();
		bottomBorder.getChildren().add(new ImageView(new Image("textures/blCorner_20x20.png")));
		for (int i = 0; i < width; i++) {
			bottomBorder.getChildren().add(new ImageView(new Image("textures/hBorder_32x20.png")));
		}
		bottomBorder.getChildren().add(new ImageView(new Image("textures/brCorner_20x20.png")));

		// gameLayout is the primary Node of the game scene
		VBox gameLayout = new VBox(gameBackButton, gameSeparator, topBorder, menuRow,
								   middleBorder, gameBox, bottomBorder);

		gameScene = new Scene(gameLayout);
	}
}
