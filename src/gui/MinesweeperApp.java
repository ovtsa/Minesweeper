package gui;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MinesweeperApp extends Application {
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Minesweeper");
		primaryStage.initStyle(StageStyle.DECORATED);
		
		ChoiceBox<String> difficultyChoiceBox = new ChoiceBox<String>();
		difficultyChoiceBox.getItems().add("EASY");
		difficultyChoiceBox.getItems().add("NORMAL");
		difficultyChoiceBox.getItems().add("HARD");
		difficultyChoiceBox.getItems().add("CUSTOM");
		
		Slider heightSlider = new Slider(1, 50, 16);
		Label heightLabel = new Label("16");
		// This code makes the heightLabel actively listen to the heightSlider value
		heightSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue,
								Number oldValue, Number newValue) {
				heightLabel.textProperty().setValue(String.valueOf(newValue.intValue()));
			}
		});
		
		Slider widthSlider = new Slider(1, 50, 16);
		Label widthLabel = new Label("16");
		widthSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue,
								Number oldValue, Number newValue) {
				widthLabel.textProperty().setValue(String.valueOf(newValue.intValue()));
			}
		});
		
		GridPane menuGrid = new GridPane();
		menuGrid.add(new Label("Difficulty: "), 0, 0, 1, 1);
		menuGrid.add(difficultyChoiceBox, 1, 0, 1, 1);
		menuGrid.add(new Label("Height: "), 0, 1, 1, 1);
		menuGrid.add(heightSlider, 1, 1, 1, 1);
		menuGrid.add(heightLabel, 2, 1, 1, 1);
		menuGrid.add(new Label("Width: "), 0, 2, 1, 1);
		menuGrid.add(widthSlider, 1, 2, 1, 1);
		menuGrid.add(widthLabel, 2, 2, 1, 1);
		
		Scene menuScene = new Scene(menuGrid);
		primaryStage.setScene(menuScene);
		
		
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
