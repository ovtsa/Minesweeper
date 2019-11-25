package gui;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.Parent;

public class GridButton extends Parent {
	private static final Image UNKNOWN_IMAGE = new Image("textures/squareUnknown_32x32.png");
    private static final Image CLICKING_IMAGE = new Image("textures/squareClicking_32x32.png");

    private final ImageView iv;

    public GridButton() {
        this.iv = new ImageView(UNKNOWN_IMAGE);
        this.getChildren().add(this.iv);

        this.iv.setOnMousePressed(new EventHandler<MouseEvent>() {
        		@Override
            public void handle(MouseEvent evt) {
                iv.setImage(CLICKING_IMAGE);
            }

        });

        // TODO fix this event
        this.iv.setOnMouseReleased(new EventHandler<MouseEvent>() {
        		@Override
        		public void handle(MouseEvent evt) {
        			iv.setImage(UNKNOWN_IMAGE);
        		}
        });
    } 
}
