package comum.model.animation;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class TelaAnimation {

	public void abrirPane(StackPane stackPane) {
		stackPane.setDisable(true);
		Node nodeBaixo = stackPane.getChildren().get(stackPane.getChildren().size() - 2);
		Node nodeCima = stackPane.getChildren().get(stackPane.getChildren().size() - 1);

		nodeCima.setTranslateX(nodeBaixo.getBoundsInParent().getWidth());
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300),
				new KeyValue(nodeCima.translateXProperty(), 0, Interpolator.EASE_BOTH),
				new KeyValue(nodeBaixo.translateXProperty(), -nodeBaixo.getBoundsInParent().getWidth(),
						Interpolator.EASE_BOTH)));
		timeline.setOnFinished(event -> {
			nodeBaixo.setVisible(false);
			stackPane.setDisable(false);
			stackPane.requestLayout();
		});
		stackPane.requestLayout();
		timeline.play();
	}

	public void fecharPane(StackPane stackPane) {
		stackPane.setDisable(true);
		Node nodeBaixo = stackPane.getChildren().get(stackPane.getChildren().size() - 2);
		Node nodeCima = stackPane.getChildren().get(stackPane.getChildren().size() - 1);
		nodeBaixo.setVisible(true);

		nodeBaixo.translateXProperty().set(-nodeBaixo.getBoundsInParent().getWidth());
		KeyValue kvBaixo = new KeyValue(nodeBaixo.translateXProperty(), 0, Interpolator.EASE_BOTH);
		KeyFrame kfBaixo = new KeyFrame(Duration.millis(300), kvBaixo);

		KeyValue kvCima = new KeyValue(nodeCima.translateXProperty(), nodeCima.getBoundsInParent().getWidth(),
				Interpolator.EASE_BOTH);
		KeyFrame kfCima = new KeyFrame(Duration.millis(300), kvCima);

		Timeline timeline = new Timeline();
		timeline.getKeyFrames().add(kfCima);
		timeline.getKeyFrames().add(kfBaixo);
		timeline.setOnFinished(event -> {
			stackPane.getChildren().remove(stackPane.getChildren().indexOf(nodeCima));
			stackPane.setDisable(false);
		});
		timeline.play();
	}
}
