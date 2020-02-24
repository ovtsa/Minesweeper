package gui;

import controller.Controller;
import java.util.HashMap;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.Parent;

public class GridButton extends Parent {
		/* GRIDBUTTON_IMAGES keys: "0"-"9", "unknown", "clicking",
		   "mine", "mineDeath", "flagged", "flaggedWrong" */
		private static final HashMap<String, Image> GRIDBUTTON_IMAGES = instantiateMap();
		private static Controller controller;

    private final ImageView iv;
		private final int rowIndex;
		private final int colIndex;
		private boolean uncovered;

    public GridButton(int rowIndex, int colIndex, Controller controller) {
				if (this.controller == null) this.controller = controller;
        this.iv = new ImageView(GRIDBUTTON_IMAGES.get("unknown"));
        this.getChildren().add(this.iv);
				this.rowIndex = rowIndex;
				this.colIndex = colIndex;
				this.uncovered = false;

				// TODO: drag event handling
				setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
								//System.out.printf("MouseClicked event at r%d c%d\n", rowIndex, colIndex);
								click();
								//iv.setImage(CLICKING_IMAGE);
						}
				});

				setOnMousePressed(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
								//System.out.printf("MousePressed event at r%d c%d\n", rowIndex, colIndex);
								iv.setImage(GRIDBUTTON_IMAGES.get("clicking"));
						}
				});

				setOnMouseReleased(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
								//System.out.printf("MouseReleased event at r%d c%d\n", rowIndex, colIndex);
								iv.setImage(GRIDBUTTON_IMAGES.get("unknown"));
						}
				});
    }

		public void click() {
				int ret = controller.gridButtonClick(this.rowIndex, this.colIndex);
		}

		private static HashMap instantiateMap() {
				HashMap<String, Image> m = new HashMap<String, Image>();
				m.put("0", new Image("textures/squareKnown0_32x32.png"));
				m.put("1", new Image("textures/squareKnown1_32x32.png"));
				m.put("2", new Image("textures/squareKnown2_32x32.png"));
				m.put("3", new Image("textures/squareKnown3_32x32.png"));
				m.put("4", new Image("textures/squareKnown4_32x32.png"));
				m.put("5", new Image("textures/squareKnown5_32x32.png"));
				m.put("6", new Image("textures/squareKnown6_32x32.png"));
				m.put("7", new Image("textures/squareKnown7_32x32.png"));
				m.put("8", new Image("textures/squareKnown8_32x32.png"));
				m.put("unknown", new Image("textures/squareUnknown_32x32.png"));
				m.put("clicking", new Image("textures/squareClicking_32x32.png"));
				m.put("mine", new Image("textures/squareMine_32x32.png"));
				m.put("mineDeath", new Image("textures/squareMineClicked_32x32.png"));
				m.put("flagged", new Image("textures/squareFlagged_32x32.png"));
				m.put("flaggedWrong", new Image("textures/squareFlaggedIncorrectly.png"));

				return m;
		}
}
