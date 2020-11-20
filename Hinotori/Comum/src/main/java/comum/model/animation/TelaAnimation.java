package comum.model.animation;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
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

	// Função responsável pela transição de opacidade do fundo do cadastro e dos
	// botões.
	private double initY = -1;
	private final Scale scale = new Scale(1, 1, 0, 0);
	private Transform oldSceneTransform = null;

	public void scrollTitulo(ScrollPane background, AnchorPane containerInterno, HBox titulo,
			HBox tituloBotoes) {
		tituloBotoes.localToSceneTransformProperty().addListener((o, oldVal, newVal) -> oldSceneTransform = oldVal);
		background.vvalueProperty().addListener((o, oldVal, newVal) -> {
			if (initY == -1)
				initY = oldSceneTransform.getTy();

			// translation
			double ty = containerInterno.getLocalToSceneTransform().getTy();
			double opacity = Math.abs(ty - initY) / 100;
			opacity = opacity > 1 ? 1 : (opacity < 0) ? 0 : opacity;

			titulo.setOpacity(1 - opacity);
			tituloBotoes.setOpacity(opacity);

			// scale
			scale.setX(map(opacity, 0, 1, 1, 0.75));
			scale.setY(map(opacity, 0, 1, 1, 0.75));
		});
	}

	private double map(double val, double min1, double max1, double min2, double max2) {
		return min2 + (max2 - min2) * ((val - min1) / (max1 - min1));
	}
}
