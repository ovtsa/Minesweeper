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
		private static final Image DEAD_IMAGE = new Image("textures/gameButtonDead_52x52.png");
		private static final Image CLICKING_IMAGE = new Image("textures/gameButtonMakingMove_52x52.png");
		private static final Image WON_IMAGE = new Image("textures/gameButtonWon_52x52.png");

    private final ImageView iv;
    private final HBox buttonContainer;
		private static Controller controller;

    public GameButton(Controller controller) {
				if (this.controller == null) this.controller = controller;
        this.iv = new ImageView(NORMAL_IMAGE);
				Image baseImg = NORMAL_IMAGE;

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

		public void setImage(String s) {
				switch (s) {
						case "normal":
								this.iv.setImage(NORMAL_IMAGE);
								break;
						case "pressed":
								this.iv.setImage(PRESSED_IMAGE);
								break;
						case "dead":
								this.iv.setImage(DEAD_IMAGE);
								break;
						case "clicking":
								this.iv.setImage(CLICKING_IMAGE);
								break;
						case "won":
								this.iv.setImage(WON_IMAGE);
								break;
						default:
								this.iv.setImage(NORMAL_IMAGE);
								break;
				}
		}

		public void click() {
				controller.gameButtonClick();
		}

		public void giveController(Controller c) {
				this.controller = c;
		}
}
