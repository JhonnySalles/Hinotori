package Config.util;

import Config.App;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Animacao  {
	
	final static Image banco = new Image(Animacao.class.getResource("") + "/../resources/images/icoDataEspera_48.png");
	final static Image conectando = new Image(Animacao.class.getResource(" ../ ../resources/images/icoDataEspera_48.png").toString());
	
	final public static Timeline timeline = new Timeline();
	
	static Boolean reproduzir = false;
	
	synchronized public static void inicia(ImageView img) {
		
		timeline.getKeyFrames().addAll(
				new KeyFrame(Duration.millis(250), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent t) {
						//img.setImage(banco);
						img.setFitWidth(App.imgBancoWidth);
						img.setFitHeight(App.imgBancoHeight);
					}
				}),
				new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent t) {
						//img.setImage(conectando);
						img.setFitWidth(App.imgBancoWidth);
						img.setFitHeight(App.imgBancoHeight);
					}
				})
				);
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		
	}
}
