package gui;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.Parent;

public class NumberField extends Parent {
	private static final Image[] NUMBER_IMAGES = {
		new Image("textures/timer0_26x46.png"),
		new Image("textures/timer1_26x46.png"),
		new Image("textures/timer2_26x46.png"),
		new Image("textures/timer3_26x46.png"),
		new Image("textures/timer4_26x46.png"),
		new Image("textures/timer5_26x46.png"),
		new Image("textures/timer6_26x46.png"),
		new Image("textures/timer7_26x46.png"),
		new Image("textures/timer8_26x46.png"),
		new Image("textures/timer9_26x46.png"),
		new Image("textures/timerNeg_26x46.png")
	};
	
	private final ImageView hundreds;
	private final ImageView tens;
	private final ImageView ones;
	
	private final HBox imagesHbox;
	
	public NumberField(int value) {
		if (value >= 0) {
			// value is positive
			this.hundreds = new ImageView(NUMBER_IMAGES[(value % 1000) / 100]);
			this.tens = new ImageView(NUMBER_IMAGES[(value % 100) / 10]);
			this.ones = new ImageView(NUMBER_IMAGES[value % 10]);
		} else {
			// value is negative
			this.hundreds = new ImageView(NUMBER_IMAGES[10]);
			this.tens = new ImageView(NUMBER_IMAGES[-1 * (value % 100) / 10]);
			this.ones = new ImageView(NUMBER_IMAGES[-1 * value % 10]);
		}
		
		imagesHbox = new HBox(hundreds, tens, ones);
		
		this.getChildren().add(imagesHbox);
	}
	
	public void setPadding(Insets insets) {
		imagesHbox.setPadding(insets);
	}
	
	public void setNumber(int value) {
		if (value >= 0) {
			hundreds.setImage(NUMBER_IMAGES[(value % 1000) / 100]);
			tens.setImage(NUMBER_IMAGES[(value % 100) / 10]);
			ones.setImage(NUMBER_IMAGES[value % 10]);
		} else {
			hundreds.setImage(NUMBER_IMAGES[10]);
			tens.setImage(NUMBER_IMAGES[-1 * (value % 100) / 10]);
			ones.setImage(NUMBER_IMAGES[-1 * value % 10]);
		}
	}
}
