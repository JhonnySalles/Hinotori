package comum.form;

import java.util.HashMap;
import java.util.Map;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;

public class CadastroFormPadrao {

	private Map<KeyCodeCombination, Runnable> atalhosTecla = new HashMap<>();

	@FXML
	protected StackPane spRoot;

	@FXML
	protected ScrollPane spBackground;

	@FXML
	protected HBox hbTitulo;

	@FXML
	protected HBox hbTituloBotoes;

	@FXML
	protected AnchorPane apContainer;

	@FXML
	protected JFXButton btnPesquisar;

	@FXML
	protected JFXButton btnConfirmar;

	@FXML
	protected JFXButton btnCancelar;

	@FXML
	protected JFXButton btnExcluir;

	// Função responsável pela transição de opacidade do fundo do cadastro e dos
	// botões.
	private double initY = -1;
	private final Scale scale = new Scale(1, 1, 0, 0);
	private Transform oldSceneTransform = null;

	private void configureScroll() {

		hbTituloBotoes.localToSceneTransformProperty().addListener((o, oldVal, newVal) -> oldSceneTransform = oldVal);
		spBackground.vvalueProperty().addListener((o, oldVal, newVal) -> {
			if (initY == -1) {
				initY = oldSceneTransform.getTy();
			}

			// translation
			double ty = apContainer.getLocalToSceneTransform().getTy();
			double opacity = Math.abs(ty - initY) / 100;
			opacity = opacity > 1 ? 1 : (opacity < 0) ? 0 : opacity;

			hbTitulo.setOpacity(1 - opacity);
			hbTituloBotoes.setOpacity(opacity);

			// scale
			scale.setX(map(opacity, 0, 1, 1, 0.75));
			scale.setY(map(opacity, 0, 1, 1, 0.75));
		});
	}

	private double map(double val, double min1, double max1, double min2, double max2) {
		return min2 + (max2 - min2) * ((val - min1) / (max1 - min1));
	}

	// Será necessário verificar uma forma de configurar o scene após a exibição,
	// pois é ele que adiciona os atalhos do teclado, porém na construção a scene
	// não existe, somente na exibição.
	public void ativaAtalhos() {
		apContainer.getScene().getAccelerators().clear();
		apContainer.getScene().getAccelerators().putAll(atalhosTecla);
	}

	private void configuraAtalhosTeclado() {
		atalhosTecla.put(new KeyCodeCombination(KeyCode.F2), new Runnable() {
			@FXML
			public void run() {
				btnConfirmar.fire();
			}
		});
		atalhosTecla.put(new KeyCodeCombination(KeyCode.F3), new Runnable() {
			@FXML
			public void run() {
				btnCancelar.fire();
			}
		});
		atalhosTecla.put(new KeyCodeCombination(KeyCode.F4), new Runnable() {
			@FXML
			public void run() {
				btnExcluir.fire();
			}
		});
		atalhosTecla.put(new KeyCodeCombination(KeyCode.F5), new Runnable() {
			@FXML
			public void run() {
				btnPesquisar.fire();
			}
		});
	}

	public synchronized void inicializaHeranca() {
		configureScroll();
		configuraAtalhosTeclado();
	}

}
