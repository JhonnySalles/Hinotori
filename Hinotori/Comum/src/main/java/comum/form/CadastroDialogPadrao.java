package comum.form;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public abstract class CadastroDialogPadrao {

	static protected CadastroDialogPadrao CADASTRO_DIALOG_MAIN;

	@FXML
	protected AnchorPane apContainer;

	@FXML
	protected JFXButton btnConfirmar;

	@FXML
	protected JFXButton btnCancelar;

	@FXML
	protected abstract void onBtnConfirmarClick();

	@FXML
	protected abstract void onConfirmarKeyPress(KeyEvent e);

	@FXML
	protected abstract void onBtnCancelarClick();

	@FXML
	protected abstract void onCancelarKeyPress(KeyEvent e);

	public synchronized void inicializaHeranca() {
		CADASTRO_DIALOG_MAIN = this;
	}

}
