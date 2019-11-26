package gui;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.Parent;

public class GridButton extends Parent {
	private static final Image UNKNOWN_IMAGE = new Image("textures/squareUnknown_32x32.png");
    private static final Image CLICKING_IMAGE = new Image("textures/squareClicking_32x32.png");

    private final ImageView iv;

    public GridButton() {
        this.iv = new ImageView(UNKNOWN_IMAGE);
        this.getChildren().add(this.iv);
        
        /*
        this.iv.setOnDragDetected(new EventHandler<>() {
        		@Override
        		public void handle(MouseEvent evt) {
        			startDragAndDrop(TransferMode.ANY);
        			iv.setImage(CLICKING_IMAGE);
        			evt.consume();
        		}
        });

        this.iv.setOnDragEntered(new EventHandler<DragEvent>() {
        		@Override
        		public void handle(DragEvent evt) {
        			iv.setImage(CLICKING_IMAGE);
        		}
        });
        
        this.iv.setOnDragExited(new EventHandler<DragEvent>() {
        		@Override
        		public void handle(DragEvent evt) {
        			iv.setImage(UNKNOWN_IMAGE);
        		}
        });
        */
        
        // TODO fix this event
        this.iv.setOnMouseReleased(new EventHandler<MouseEvent>() {
        		@Override
        		public void handle(MouseEvent evt) {
        			iv.setImage(CLICKING_IMAGE);
        		}
        });
    } 
}
