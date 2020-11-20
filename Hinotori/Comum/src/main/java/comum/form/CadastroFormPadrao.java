package comum.form;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import comum.model.animation.TelaAnimation;
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

public abstract class CadastroFormPadrao implements Initializable {

	private Map<KeyCodeCombination, Runnable> atalhosTecla = new HashMap<>();

	static protected CadastroFormPadrao CADASTRO_MAIN;

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
	protected JFXButton btnExcluir;

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
	protected abstract void onBtnExcluirClick();

	@FXML
	protected abstract void onExcluirKeyPress(KeyEvent e);

	@FXML
	protected abstract void onBtnVoltarClick();

	@FXML
	protected abstract void onVoltarKeyPress(KeyEvent e);

	protected abstract <T> void salvar(T entidade);

	protected abstract <T> void excluir(T entidade);

	protected abstract <T> T pesquisar(T entidade);

	public abstract <T> void carregar(T entidade);

	protected abstract boolean validaCampos();

	protected abstract void limpaCampos();

	/**
	 * Função a ser executada quando a tela for fechada. {@code ListaFormPadrao}.
	 *
	 * @defaultValue null
	 */
	protected ObjectProperty<EventHandler<ActionEvent>> onClose;
	protected static final EventHandler<ActionEvent> DEFAULT_ON_CLOSE = null;

	public final void setOnClose(EventHandler<ActionEvent> value) {
		if ((onClose != null) || (value != null /* DEFAULT_ON_CLOSE */))
			onCloseProperty().set(value);
	}

	public final EventHandler<ActionEvent> getOnClose() {
		return (onClose == null) ? DEFAULT_ON_CLOSE : onClose.get();
	}

	public final ObjectProperty<EventHandler<ActionEvent>> onCloseProperty() {
		if (onClose == null)
			onClose = new SimpleObjectProperty<EventHandler<ActionEvent>>(this, "onClose", DEFAULT_ON_CLOSE);
		return onClose;
	}

	protected final void onClose() {
		final EventHandler<ActionEvent> handler = getOnClose();
		if (handler != null)
			handler.handle(new ActionEvent(this, null));
	}

	private void configuraScroll() {
		new TelaAnimation().scrollTitulo(spBackground, apContainerInterno, hbTitulo, hbTituloBotoes);
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
	}

	public String teste = "";

	protected abstract void inicializa(URL arg0, ResourceBundle arg1);

	@Override
	public synchronized void initialize(URL arg0, ResourceBundle arg1) {
		CADASTRO_MAIN = this;
		configuraScroll();
		configuraAtalhosTeclado();
		inicializa(arg0, arg1);
	}

}
