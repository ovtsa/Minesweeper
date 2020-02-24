package gui;

import controller.Controller;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.Parent;

public class GameButton extends Parent {
	private static final Image NORMAL_IMAGE = new Image("textures/gameButtonNormal_52x52.png");
    private static final Image PRESSED_IMAGE = new Image("textures/gameButtonClicking_52x52.png");

    private final ImageView iv;
    private final HBox buttonContainer;
		private Controller controller;

    public GameButton(Controller controller) {
				if (this.controller == null) this.controller = controller;
        this.iv = new ImageView(NORMAL_IMAGE);

				this.iv.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent evt) {
								click();
						}
				});

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

        this.buttonContainer = new HBox(this.iv);
        this.getChildren().add(this.buttonContainer);
    }

    public void setPadding(Insets insets) {
    		buttonContainer.setPadding(insets);
    }

		public void click() {
				controller.gameButtonClick();
		}
}
