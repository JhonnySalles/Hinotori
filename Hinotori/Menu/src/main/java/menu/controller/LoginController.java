package menu.controller;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;

import comum.model.constraints.Validadores;
import comum.model.encode.DecodeHash;
import comum.model.mysql.ConexaoMysql;
import comum.model.notification.Alertas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginController implements Initializable {

	public static String usuario;
	public static Stage loginStage;

	@FXML
	private StackPane stPaneLogin;

	@FXML
	private VBox vbLogin;

	@FXML
	private JFXPasswordField pswFieldPassword;

	@FXML
	private JFXButton btnExit;

	@FXML
	private JFXButton btnLogin;

	@FXML
	private JFXComboBox<String> cbBoxUsuario;
	private ObservableList<String> obsList;

	@FXML
	public void onBtLoginAction() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		ValidaLogin();
	}

	@FXML
	public void onBtSairAction() {
		System.exit(0);
	}

	@FXML
	public void onPasswordEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			ValidaLogin();
		}
	}

	@FXML
	public void onUserEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			pswFieldPassword.requestFocus();
		}
	}

	public void initialize(URL url, ResourceBundle rb) {
		Validadores.setTextFieldNotEmpty(pswFieldPassword);

		// Gera lista de usuario
		List<String> lista = ConexaoMysql.getDBUsuarios();
		obsList = FXCollections.observableArrayList(lista);
		cbBoxUsuario.getItems().addAll(obsList); // Transfere a lista para o combobox
		cbBoxUsuario.requestFocus(); // Foco principal no combo box
	}

	public void ValidaLogin() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String user = "";

		// Caso seja vazio, acreditasse que ir� logar como nosso usuario.
		if (cbBoxUsuario.getValue() == null) {
			user = "HINOTORI";
		} else {
			user = cbBoxUsuario.getSelectionModel().getSelectedItem().toString();
		}

		if (pswFieldPassword.getText().isEmpty()) {
			pswFieldPassword.setUnFocusColor(Color.RED);
			return;
		}

		String password = pswFieldPassword.getText();

		if (DecodeHash.ComparaPassword(user, password)) {

		} else {
			if (cbBoxUsuario.getValue() == null)
				Alertas.dialogLogin(stPaneLogin, vbLogin, "Usuário inválido", "Favor selecionar um usuário.");
			else
				Alertas.dialogLogin(stPaneLogin, vbLogin, "Senha inválida", "Favor verificar os dados de conexão.");
		}

	}
	
}
