package comum.model.animation;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class TelaAnimation {

	/**
	 * <p>
	 * Função que fará a animação de abertura das telas, onde será movimentada a
	 * esquerda.
	 * </p>
	 * 
	 * @param rootPane O rootPane da tela pai em que a filho será sobreposta.
	 * @param rootPane O rootPane da tela filho em que será feito a transição.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public synchronized void abrirPane(Pane rootPane, Node rootPaneFilho) {
		Node nodeBaixo = rootPane.getChildren().get(0);

		rootPaneFilho.setTranslateX(nodeBaixo.getBoundsInParent().getWidth());
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300),
				new KeyValue(rootPaneFilho.translateXProperty(), 0, Interpolator.EASE_BOTH),
				new KeyValue(nodeBaixo.translateXProperty(), -nodeBaixo.getBoundsInParent().getWidth(),
						Interpolator.EASE_BOTH)));
		timeline.setOnFinished(event -> {
			nodeBaixo.setVisible(false);
			nodeBaixo.setDisable(true);
		});
		timeline.play();
	}

	/**
	 * <p>
	 * Função que fará a animação de fechamento das telas, onde será movimentado a
	 * direita.
	 * </p>
	 * 
	 * @param rootPane O rootPane da tela que deve ser fechada.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public synchronized void fecharPane(Pane spRoot) {
		Node nodeBaixo = spRoot.getChildren().get(0);
		Node nodeCima = spRoot.getChildren().get(1);

		nodeBaixo.setDisable(false);
		nodeBaixo.setVisible(true);

		nodeBaixo.translateXProperty().set(-nodeBaixo.getBoundsInParent().getWidth());
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(300),
				new KeyValue(nodeBaixo.translateXProperty(), 0, Interpolator.EASE_BOTH),
				new KeyValue(nodeCima.translateXProperty(), nodeCima.getBoundsInParent().getWidth(),
						Interpolator.EASE_BOTH)));

		timeline.setOnFinished(event -> {
			spRoot.getChildren().remove(spRoot.getChildren().indexOf(nodeCima));
			nodeCima.setVisible(false);
		});
		timeline.play();

	}
}
