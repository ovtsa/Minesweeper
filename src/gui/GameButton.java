package gui;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.Parent;

public class GameButton extends Parent {
	private static final Image NORMAL_IMAGE = new Image("textures/gameButtonNormal_52x52.png");
    private static final Image PRESSED_IMAGE = new Image("textures/gameButtonClicking_52x52.png");

    private final ImageView iv;

    public GameButton() {
        this.iv = new ImageView(NORMAL_IMAGE);
        this.getChildren().add(this.iv);

        this.iv.setOnMousePressed(new EventHandler<MouseEvent>() {
        		@Override
            public void handle(MouseEvent evt) {
                iv.setImage(PRESSED_IMAGE);
            }

        });

        // TODO implement game reset upon mouse release
        this.iv.setOnMouseReleased(new EventHandler<MouseEvent>() {
        		@Override
        		public void handle(MouseEvent evt) {
        			iv.setImage(NORMAL_IMAGE);
        		}
        });
    } 
}
