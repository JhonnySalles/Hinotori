package menu.controller;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import comum.model.alerts.Alertas;
import comum.model.constraints.Validadores;
import comum.model.exceptions.ExcessaoBd;
import comum.model.exceptions.ExcessaoLogin;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import servidor.entities.Usuario;
import servidor.validations.ValidaLogin;

public class LoginController implements Initializable {

	@FXML
	private AnchorPane background;

	@FXML
	private AnchorPane apLogo;

	@FXML
	private AnchorPane apLoginCadastro;

	@FXML
	private VBox vbLogin;

	@FXML
	private VBox vbCadastro;

	@FXML
	private JFXComboBox<String> cbBoxUsuario;

	@FXML
	private JFXPasswordField pswSenha;

	@FXML
	private JFXButton btnLogin;

	@FXML
	private JFXTextField txtNome;

	@FXML
	private JFXTextField txtLoguin;

	@FXML
	private JFXPasswordField pswPassword;

	@FXML
	private JFXButton btnCadastrar;

	@FXML
	private JFXButton btnEscolha;

	private ObservableList<String> obsList;

	ParallelTransition pt;

	@FXML
	private void onLoginClick() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		ValidaLogin();
	}

	@FXML
	public void onCadastrarClick() {
		System.exit(0);
	}

	@FXML
	public void onSairClick() {
		System.exit(0);
	}

	@FXML
	public void onEscolhaClick() {

		if (btnEscolha.getAccessibleText().equalsIgnoreCase("CADASTRAR")) {
			btnEscolha.setAccessibleText("LOGAR");
			moveDireita();
		} else {
			btnEscolha.setAccessibleText("CADASTRAR");
			moveEsquerda();
		}

	}

	private void moveDireita() {
		vbCadastro.setDisable(false);
		vbCadastro.setVisible(true);

		TranslateTransition trLogin = new TranslateTransition(Duration.millis(500), vbLogin);
		trLogin.setToX(-(vbLogin.getWidth() / 2));

		vbCadastro.setTranslateX(vbCadastro.getWidth() / 2);
		TranslateTransition trCadastro = new TranslateTransition(Duration.millis(500), vbCadastro);
		trCadastro.setToX(0);

		TranslateTransition trLogo = new TranslateTransition(Duration.millis(500), apLogo);
		trLogo.setToX(apLoginCadastro.getLayoutX() + (apLoginCadastro.getWidth() - apLogo.getWidth()));
		trLogo.setOnFinished(event -> {
			vbLogin.setDisable(true);
			vbLogin.setVisible(true);
		});

		trLogo.play();
		trLogin.play();
		trCadastro.play();
	}

	private void moveEsquerda() {
		vbLogin.setDisable(false);
		vbLogin.setVisible(true);

		TranslateTransition trLogin = new TranslateTransition(Duration.millis(500), vbLogin);
		trLogin.setToX(0);

		TranslateTransition trCadastro = new TranslateTransition(Duration.millis(500), vbCadastro);
		trCadastro.setToX(vbCadastro.getWidth() / 2);

		TranslateTransition trLogo = new TranslateTransition(Duration.millis(500), apLogo);
		trLogo.setToX(0);
		trLogo.setOnFinished(evento -> {
			vbCadastro.setDisable(true);
			vbCadastro.setVisible(false);
		});

		trLogo.play();
		trLogin.play();
		trCadastro.play();
	}

	public void ValidaLogin() throws NoSuchAlgorithmException, UnsupportedEncodingException {

		if (!cbBoxUsuario.validate())
			return;

		if (!pswPassword.validate())
			return;

		Usuario usuario = new Usuario(cbBoxUsuario.getSelectionModel().getSelectedItem().toString());

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

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Validadores.setPasswordFieldNotEmpty(pswSenha);
		Validadores.setPasswordFieldNotEmpty(pswPassword);
		Validadores.setTextFieldNotEmpty(txtLoguin);
		Validadores.setTextFieldNotEmpty(txtNome);
		Validadores.setComboBoxNotEmpty(cbBoxUsuario);
	}
	
	public static URL getFxmlLocate() {
		return LoginController.class.getResource("/menu/view/Login.fxml");
	}

}
