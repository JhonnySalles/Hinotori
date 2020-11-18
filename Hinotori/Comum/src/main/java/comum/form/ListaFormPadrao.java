package comum.form;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;

public abstract class ListaFormPadrao implements Initializable {

	static protected ListaFormPadrao LISTA_MAIN;

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
	protected JFXButton btnNovo;

	@FXML
	protected JFXButton btnExcluir;

	@FXML
	protected JFXButton btnEditar;

	@FXML
	protected JFXButton btnAtualizar;

	@FXML
	protected JFXTextField txtPesquisa;

	@FXML
	protected abstract void onBtnNovoClick();

	@FXML
	protected abstract void onNovoKeyPress(KeyEvent e);

	@FXML
	protected abstract void onBtnExcluirClick();

	@FXML
	protected abstract void onExcluirKeyPress(KeyEvent e);

	@FXML
	protected abstract void onBtnEditarClick();

	@FXML
	protected abstract void onEditarKeyPress(KeyEvent e);

	@FXML
	protected abstract void onBtnAtualizarClick();

	@FXML
	protected abstract void onAtualizarKeyPress(KeyEvent e);

	/**
	 * Função a ser executada quando a tela for fechada. {@code ListaFormPadrao}.
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

	// Função responsável pela transição de opacidade do fundo do cadastro e dos
	// botões.
	private double initY = -1;
	private final Scale scale = new Scale(1, 1, 0, 0);
	private Transform oldSceneTransform = null;

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

	protected abstract void inicializa(URL arg0, ResourceBundle arg1);

	@Override
	public synchronized void initialize(URL arg0, ResourceBundle arg1) {
		LISTA_MAIN = this;
		configuraScroll();
	}
}
