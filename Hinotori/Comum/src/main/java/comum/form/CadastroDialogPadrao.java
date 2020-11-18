package comum.form;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public abstract class CadastroDialogPadrao implements Initializable {

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

	protected abstract void inicializa(URL arg0, ResourceBundle arg1);

	@Override
	public synchronized void initialize(URL arg0, ResourceBundle arg1) {
		CADASTRO_DIALOG_MAIN = this;
	}

}
