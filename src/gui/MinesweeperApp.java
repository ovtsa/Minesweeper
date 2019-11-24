package gui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;

public class MinesweeperApp extends Application {
	// menuScene useful fields
	private Scene menuScene;
	private ChoiceBox<String> menuDifficultyChoiceBox;
	private Slider menuHeightSlider;
	private Slider menuWidthSlider;
	private Slider menuNumMinesSlider;
	private Button menuContinueButton;
	
	@Override
	public void start(Stage stage) {
		initializeStage(stage);
		initializeMenu();
		
		switchStageToScene(stage, menuScene);
	}
	
	public void initializeStage(Stage stage) {
		stage.setTitle("Minesweeper");
		stage.initStyle(StageStyle.DECORATED);
	}
	
	public void switchStageToScene(Stage stage, Scene scene) {
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
		stage.setMinHeight(stage.getHeight());
		stage.setMinWidth(stage.getWidth());
	}
	
	public Scene initializeMenu() {
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
		
		menuWidthSlider = new Slider(1, 50, 16);
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
		
		VBox menuSceneDivider = new VBox(20, menuGrid,
										new Separator(Orientation.HORIZONTAL),
										menuContinueButton);
		menuSceneDivider.setPadding(new Insets(0, 0, 20, 0));
		menuSceneDivider.setAlignment(Pos.CENTER);
		
		menuScene = new Scene(menuSceneDivider);
		return menuScene;
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
