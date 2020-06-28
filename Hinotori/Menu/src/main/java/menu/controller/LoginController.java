package menu.controller;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;

import comum.model.alerts.Alertas;
import comum.model.constraints.Validadores;
import comum.model.exceptions.ExcessaoBd;
import comum.model.exceptions.ExcessaoLogin;
import comum.model.messages.Mensagens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import servidor.dao.services.UsuarioServices;
import servidor.entities.Usuario;
import servidor.validations.ValidaLogin;

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
	private UsuarioServices usuarioService;

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
		try {
			usuarioService = new UsuarioServices();
			obsList = FXCollections.observableArrayList(usuarioService.carregaLogins());
			cbBoxUsuario.getItems().addAll(obsList); // Transfere a lista para o combobox
			cbBoxUsuario.requestFocus(); // Foco principal no combo box
		} catch (ExcessaoBd e) {
			Alertas.Alerta(AlertType.WARNING, "Erro", "Erro ao carregar os usuário, favor reiniciar o sistema.");
			e.printStackTrace();
		}
	}

	public void ValidaLogin() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		Usuario usuario = new Usuario();

		// Caso seja vazio, acreditasse que irá logar como nosso usuario.
		if (cbBoxUsuario.getValue() == null)
			usuario.setLogin(Mensagens.LOGIN_ADMINISTRADOR_PADRAO);
		else
			usuario.setLogin(cbBoxUsuario.getSelectionModel().getSelectedItem().toString());

		if (pswFieldPassword.getText().isEmpty()) {
			pswFieldPassword.setUnFocusColor(Color.RED);
			return;
		}

		usuario.setSenha(pswFieldPassword.getText());

		try {
			usuario = ValidaLogin.validaLogin(usuario);
		} catch (ExcessaoLogin e) {
			Alertas.Alerta(AlertType.INFORMATION, "Erro ao efetuar o login", e.getMessage());
			e.printStackTrace();
		} catch (ExcessaoBd e) {
			Alertas.Alerta(AlertType.INFORMATION, "Erro ao efetuar o login",
					"Erro ao tentar conectar ao banco, tente novamente.");
			e.printStackTrace();
		}
	}

}
