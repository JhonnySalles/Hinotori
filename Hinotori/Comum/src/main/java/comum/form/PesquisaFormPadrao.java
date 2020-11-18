package comum.form;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;

public abstract class PesquisaFormPadrao implements Initializable {

	private Map<KeyCodeCombination, Runnable> atalhosTecla = new HashMap<>();

	static protected PesquisaFormPadrao PESQUISA_MAIN;

	@FXML
	protected StackPane spRoot;

	@FXML
	protected AnchorPane apContainer;

	@FXML
	protected ScrollPane spBackground;

	@FXML
	protected AnchorPane apContainerInterno;

	@FXML
	protected HBox hbTitulo;

	@FXML
	protected HBox hbTituloBotoes;

	@FXML
	protected JFXButton btnConfirmar;

	@FXML
	protected JFXButton btnCancelar;

	@FXML
	protected JFXButton btnAtualizar;

	@FXML
	protected JFXButton btnVoltar;

	@FXML
	protected abstract void onBtnConfirmarClick();

	@FXML
	protected abstract void onConfirmarKeyPress(KeyEvent e);

	@FXML
	protected abstract void onBtnCancelarClick();

	@FXML
	protected abstract void onCancelarKeyPress(KeyEvent e);

	@FXML
	protected abstract void onBtnAtualizarClick();

	@FXML
	protected abstract void onAtualizarKeyPress(KeyEvent e);

	@FXML
	protected abstract void onBtnVoltarClick();

	@FXML
	protected abstract void onVoltarKeyPress(KeyEvent e);

	// Função responsável pela transição de opacidade do fundo do cadastro e dos
	// botões.
	private double initY = -1;
	private final Scale scale = new Scale(1, 1, 0, 0);
	private Transform oldSceneTransform = null;

	/**
	 * Função a ser executada quando a tela for fechada. {@code PesquisaFormPadrao}.
	 *
	 * @defaultValue null
	 */
	protected ObjectProperty<EventHandler<ActionEvent>> onClose;
	protected static final EventHandler<ActionEvent> DEFAULT_ON_CLOSE = null;

	public final void setOnClose(EventHandler<ActionEvent> value) {
		if ((onClose != null) || (value != null /* DEFAULT_ON_DUPLO_CLIQUE */)) {
			onCloseProperty().set(value);
		}
	}

	public final EventHandler<ActionEvent> getOnClose() {
		return (onClose == null) ? DEFAULT_ON_CLOSE : onClose.get();
	}

	public final ObjectProperty<EventHandler<ActionEvent>> onCloseProperty() {
		if (onClose == null) {
			onClose = new SimpleObjectProperty<EventHandler<ActionEvent>>(this, "onClose", DEFAULT_ON_CLOSE);
		}
		return onClose;
	}

	protected final void onClose() {
		final EventHandler<ActionEvent> handler = getOnClose();
		if (handler != null)
			handler.handle(new ActionEvent(this, null));
	}

	private void configuraScroll() {

		hbTituloBotoes.localToSceneTransformProperty().addListener((o, oldVal, newVal) -> oldSceneTransform = oldVal);
		spBackground.vvalueProperty().addListener((o, oldVal, newVal) -> {
			if (initY == -1) {
				initY = oldSceneTransform.getTy();
			}

			// translation
			double ty = apContainerInterno.getLocalToSceneTransform().getTy();
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
		atalhosTecla.put(new KeyCodeCombination(KeyCode.BACK_SPACE), new Runnable() {
			@FXML
			public void run() {
				btnVoltar.fire();
			}
		});
		atalhosTecla.put(new KeyCodeCombination(KeyCode.BACK_SPACE), new Runnable() {
			@FXML
			public void run() {
				btnVoltar.fire();
			}
		});
		atalhosTecla.put(new KeyCodeCombination(KeyCode.F5), new Runnable() {
			@FXML
			public void run() {
				btnAtualizar.fire();
			}
		});
	}

	protected abstract void inicializa(URL arg0, ResourceBundle arg1);

	@Override
	public synchronized void initialize(URL arg0, ResourceBundle arg1) {
		PESQUISA_MAIN = this;

		configuraScroll();
		configuraAtalhosTeclado();

		// Por padrão os botões não estão visiveis.
		btnConfirmar.setDisable(true);
		btnConfirmar.setVisible(false);
		btnCancelar.setDisable(true);
		btnCancelar.setVisible(false);
		btnVoltar.setDisable(true);
		btnVoltar.setVisible(false);
	}

}
