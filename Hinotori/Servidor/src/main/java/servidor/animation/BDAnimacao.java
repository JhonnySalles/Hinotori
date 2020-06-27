package servidor.animation;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import servidor.util.MySqlUtil;

public class BDAnimacao {

	final public static Timeline TIMELINE = new Timeline();

	synchronized public static void inicia(ImageView img) {

		TIMELINE.getKeyFrames().addAll(new KeyFrame(Duration.millis(250), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				img.setImage(MySqlUtil.BANCO);
				img.setFitWidth(MySqlUtil.IMG_BANCO_WIDTH);
				img.setFitHeight(MySqlUtil.IMG_BANCO_HEIGHT);
			}
		}), new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				img.setImage(MySqlUtil.CONECTANDO);
				img.setFitWidth(MySqlUtil.IMG_BANCO_WIDTH);
				img.setFitHeight(MySqlUtil.IMG_BANCO_HEIGHT);
			}
		}));
		TIMELINE.setCycleCount(Animation.INDEFINITE);
		TIMELINE.play();
	}
}
