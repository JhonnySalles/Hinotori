package model.animation;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class ClienteTranslate {

	public void abrirPane(StackPane stackPane) {
		Node nodeBaixo = stackPane.getChildren().get(stackPane.getChildren().size() - 2);
		Node nodeCima = stackPane.getChildren().get(stackPane.getChildren().size() - 1);

		nodeCima.translateXProperty().set(stackPane.getWidth());
		KeyValue kvCimat = new KeyValue(nodeCima.translateXProperty(), 0, Interpolator.EASE_IN);
		KeyFrame kfCima = new KeyFrame(Duration.millis(700), kvCimat);

		KeyValue kvBaixo = new KeyValue(nodeBaixo.translateXProperty(), -nodeBaixo.getBoundsInParent().getWidth(),
				Interpolator.EASE_IN);
		KeyFrame kfBaixo = new KeyFrame(Duration.millis(700), kvBaixo);

		Timeline timeline = new Timeline();
		timeline.setDelay(Duration.seconds(0));
		timeline.getKeyFrames().add(kfBaixo);
		timeline.getKeyFrames().add(kfCima);

		timeline.play();
	}

	public void fecharPane(StackPane stackPane, EventHandler<ActionEvent> eventoOnFinished) {
		Node nodeBaixo = stackPane.getChildren().get(stackPane.getChildren().size() - 2);
		Node nodeCima = stackPane.getChildren().get(stackPane.getChildren().size() - 1);
		
		nodeBaixo.translateXProperty().set(-nodeBaixo.getBoundsInParent().getWidth());
		KeyValue kvBaixo = new KeyValue(nodeBaixo.translateXProperty(), 0, Interpolator.EASE_IN);
		KeyFrame kfBaixo = new KeyFrame(Duration.millis(700), kvBaixo);

		KeyValue kvCima = new KeyValue(nodeCima.translateXProperty(), nodeCima.getBoundsInParent().getWidth(),
				Interpolator.EASE_IN);
		KeyFrame kfCima = new KeyFrame(Duration.millis(700), kvCima);

		Timeline timeline = new Timeline();
		timeline.getKeyFrames().add(kfCima);
		timeline.getKeyFrames().add(kfBaixo);
		timeline.setOnFinished(eventoOnFinished);
		timeline.play();
	}

}
