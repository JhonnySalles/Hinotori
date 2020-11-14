package comum.model.animation;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class TelaAnimation {

	public synchronized void abrirPane(StackPane spRoot, AnchorPane apFilho) {
		Node nodeBaixo = spRoot.getChildren().get(0);
		nodeBaixo.setDisable(true);
		apFilho.setDisable(true);

		apFilho.setTranslateX(nodeBaixo.getBoundsInParent().getWidth());
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300),
				new KeyValue(apFilho.translateXProperty(), 0, Interpolator.EASE_BOTH),
				new KeyValue(nodeBaixo.translateXProperty(), -nodeBaixo.getBoundsInParent().getWidth(),
						Interpolator.EASE_BOTH)));
		timeline.setOnFinished(event -> {
			apFilho.setDisable(false);
		});
		timeline.play();
	}

	public synchronized void fecharPane(StackPane spRoot) {
		Node nodeBaixo = spRoot.getChildren().get(0);
		Node nodeCima = spRoot.getChildren().get(1);

		nodeCima.setDisable(true);
		nodeBaixo.setVisible(true);

		nodeBaixo.translateXProperty().set(-nodeBaixo.getBoundsInParent().getWidth());
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300),
				new KeyValue(nodeBaixo.translateXProperty(), 0, Interpolator.EASE_BOTH),
				new KeyValue(nodeCima.translateXProperty(), nodeCima.getBoundsInParent().getWidth(),
						Interpolator.EASE_BOTH)));

		timeline.setOnFinished(event -> {
			spRoot.getChildren().remove(spRoot.getChildren().indexOf(nodeCima));
			nodeBaixo.setDisable(false);
			nodeCima.setVisible(false);
		});
		timeline.play();

	}
}
