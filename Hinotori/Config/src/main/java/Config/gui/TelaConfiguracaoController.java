package Config.gui;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import Config.App;
import Config.alerts.Alertas;
import Config.connection.ConexaoMySQL;
import Config.util.Animacao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.config.ProcessaConfig;
import model.entitis.Conexao;

public class TelaConfiguracaoController implements Initializable {

	@FXML
	AnchorPane background;

	@FXML
	Button btnExit;

	@FXML
	Button btnTestarConexao;

	@FXML
	Button btnConfirmar;

	@FXML
	Button btnCancelar;

	@FXML
	ChoiceBox<String> chBoxBase;

	@FXML
	ChoiceBox<String> chBoxDataBase;

	@FXML
	TextField txtIP;

	@FXML
	TextField txtPorta;

	@FXML
	TextField txtUsuario;

	@FXML
	PasswordField pswSenha;

	@FXML
	Pane pnImgAviso;

	@FXML
	Label lblAviso;

	@FXML
	ImageView imgViewConexao;

	Conexao dadosConexao;

	static String arquivo;

	@FXML
	public void onBtExitAction() {
		System.exit(0);
	}

	@FXML
	public void onBtnTesteEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnTestarConexao.fire();
		}
	}

	@FXML
	public void onBtnTesteClick() {

		lblAviso.setText("");
		lblAviso.setVisible(false);
		pnImgAviso.setVisible(false);
		chBoxBase.getItems().clear();

		if (validaCampos() && Constraints.validaIp(txtIP)) {

			background.getScene().getRoot().setCursor(Cursor.WAIT);
			Animacao.inicia(imgViewConexao);

			// Criacao da thread para que esteja validando a conexao e nao trave a tela.
			Task<Boolean> verificaConexao = new Task<Boolean>() {

				@Override
				protected Boolean call() throws Exception {
					return ConexaoMySQL.testaConexaoMySQL(txtIP.getText().toString(), txtPorta.getText().toString(),
							chBoxDataBase.getSelectionModel().getSelectedItem().toString(),
							txtUsuario.getText().toString(), pswSenha.getText().toString());
				}

				@Override
				protected void succeeded() {
					Animacao.timeline.stop();
					Boolean conectado = getValue();

					if (conectado) {
						TelaConfiguracaoController.this.aviso("", "Conectado com sucesso!");

						imgViewConexao.setImage(
								new Image(getClass().getResourceAsStream("/resources/images/icoDataConectado_48.png")));
						imgViewConexao.setFitWidth(App.imgBancoWidth);
						imgViewConexao.setFitHeight(App.imgBancoHeight);

						chBoxBase.getItems().addAll(ConexaoMySQL.bases);
						chBoxBase.setDisable(false);
						chBoxBase.getSelectionModel().select(0);

					} else {
						TelaConfiguracaoController.this.aviso("",
								"Não foi possivel conectar ao banco, verifique os dados de conexão!");

						imgViewConexao.setImage(new Image(
								getClass().getResourceAsStream("/resources/images/icoDataSemConexao_48.png")));
						imgViewConexao.setFitWidth(App.imgBancoWidth);
						imgViewConexao.setFitHeight(App.imgBancoHeight);
					}
					background.getScene().getRoot().setCursor(null);
				}
			};
			Thread t = new Thread(verificaConexao);
			t.setDaemon(true);
			t.start();
		}
	}

	@FXML
	public void onBtnCancelarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnCancelar.fire();
		}
	}

	@FXML
	public void onBtnCancelarClick() {
		if (Alertas.Confirmacao("Sair", "Deseja realmente cancelar? \nToda a alteração será descartada.")) {
			System.exit(0);
		}
	}

	@FXML
	public void onBtnConfirmarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnConfirmar.fire();
		}
	}

	@FXML
	public void onBtnConfirmarClick() {
		if (chBoxBase.getSelectionModel().getSelectedIndex() < 0) {
			TelaConfiguracaoController.this.aviso("icoAviso_48.png",
					"Necessário seleceionar uma base, por favor selecione uma base válida!");
		} else {
			dadosConexao.setBase(chBoxBase.getSelectionModel().getSelectedItem());
			ProcessaConfig.gravaConfig();
		}
	}

	@FXML
	public void onTxtPortaEventAction() {
		Constraints.setTxtFieldPort(txtIP);
	}

	@FXML
	public void onIpEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			txtPorta.requestFocus();
		}
	}

	@FXML
	public void onPortaEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			chBoxDataBase.requestFocus();
		}
	}

	@FXML
	public void onDataBaseEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			txtUsuario.requestFocus();
		}
	}

	@FXML
	public void onUsuarioEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			pswSenha.requestFocus();
		}
	}

	@FXML
	public void onSenhaEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnTestarConexao.requestFocus();
		}
	}

	public void onTxtIPExitAction() {
		if (txtIP.textProperty().get().toString().isEmpty()) {
			txtIP.setStyle("");
		} else {
			if (Constraints.validaIp(txtIP)) {
				txtIP.setStyle("-fx-border-color: green;");
			} else {
				txtIP.setStyle("-fx-border-color: red;");
			}
		}
	}

	public static String getArquivo() {
		return arquivo;
	}

	@SuppressWarnings("static-access")
	public void setArquivo(String arquivos) {
		this.arquivo = arquivos;
	}

	public void aviso(String imagem, String texto) {
		lblAviso.setVisible(false);
		pnImgAviso.setVisible(false);

		if (!imagem.isEmpty()) {
			ImageView img = new ImageView(new Image(getClass().getResourceAsStream("/resources/images/" + imagem)));
			img.setFitWidth(20);
			img.setFitHeight(20);

			pnImgAviso.getChildren().clear();
			pnImgAviso.getChildren().add(img);
			pnImgAviso.setVisible(true);
		}

		if (!texto.isEmpty()) {
			lblAviso.setText(texto);
			lblAviso.setVisible(true);
		}
	}

	public void setConfig(Conexao dadosConexao) {
		this.dadosConexao = dadosConexao;

		txtIP.setText(dadosConexao.getHost());
		txtPorta.setText(dadosConexao.getPorta());
		txtUsuario.setText(dadosConexao.getUsuario());
		pswSenha.setText(dadosConexao.getSenha());
		chBoxDataBase.getSelectionModel().select(dadosConexao.getDatabase());
		validaCampos();
	}

	public void processaDados() {
		dadosConexao.setHost(txtIP.getText());
		dadosConexao.setPorta(txtPorta.getText());
		dadosConexao.setBase(chBoxBase.getSelectionModel().getSelectedItem());
		dadosConexao.setUsuario(txtUsuario.getText());
		dadosConexao.setSenha(pswSenha.getText());
		dadosConexao.setDatabase(chBoxDataBase.getSelectionModel().getSelectedItem());
	}

	public Boolean validaCampos() {
		Boolean result = false;
		Boolean informe = true; // Utilizado para apenas apresentar uma descrição.

		if (!txtIP.getText().isEmpty() && !txtPorta.getText().isEmpty() && !txtUsuario.getText().isEmpty()
				&& !pswSenha.getText().isEmpty()) {
			txtIP.setStyle("-fx-border-color: green;");
			txtPorta.setStyle("-fx-border-color: green;");
			txtUsuario.setStyle("-fx-border-color: green;");
			pswSenha.setStyle("-fx-border-color: green;");
			result = true;
		} else {
			if (txtIP.getText().isEmpty()) {
				txtIP.setStyle("-fx-border-color: red;");
				if (informe) {

					TelaConfiguracaoController.this.aviso("icoSinaliza_48.png", "Porfavor, informe um ip válido!");
					informe = false;
				}
			}

			if (txtPorta.getText().isEmpty()) {
				txtPorta.setStyle("-fx-border-color: red;");

				if (informe) {

					TelaConfiguracaoController.this.aviso("icoSinaliza_48.png", "Porfavor, informe a porta!");
					informe = false;
				}
			}

			if (txtUsuario.getText().isEmpty()) {
				txtUsuario.setStyle("-fx-border-color: red;");

				if (informe) {

					TelaConfiguracaoController.this.aviso("icoSinaliza_48.png", "Porfavor, informe o usuário!");
					informe = false;
				}
			}

			if (pswSenha.getText().isEmpty()) {
				pswSenha.setStyle("-fx-border-color: red;");

				if (informe) {

					TelaConfiguracaoController.this.aviso("icoSinaliza_48.png", "Porfavor, informe a senha!");
					informe = false;
				}
			}
		}
		return result;
	}

	@Override
	public synchronized void initialize(URL arg0, ResourceBundle arg1) {
		Constraints.setTxtFieldPort(txtPorta);

		txtIP.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!newPropertyValue) {
					onTxtIPExitAction();
				} else {
					txtIP.setStyle("");
				}
			}
		});

		txtPorta.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue) { // Usado para limpar o stilo para que pinte quando entra
					txtPorta.setStyle("");
				} else { // Após, na saida faz então a validacao.
					if (txtPorta.textProperty().get().toString().isEmpty()) {
						txtPorta.setStyle("");
					} else {
						txtPorta.setStyle("-fx-border-color: green;");
					}
				}
			}
		});

		txtUsuario.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue) {
					txtUsuario.setStyle("");
				} else {
					if (txtUsuario.textProperty().get().toString().isEmpty()) {
						txtUsuario.setStyle("");
					} else {
						txtUsuario.setStyle("-fx-border-color: green;");
					}
				}
			}
		});

		pswSenha.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue) {
					pswSenha.setStyle("");
				} else {
					if (pswSenha.textProperty().get().toString().isEmpty()) {
						pswSenha.setStyle("");
					} else {
						pswSenha.setStyle("-fx-border-color: green;");
					}
				}
			}
		});

		chBoxDataBase.getItems().add("MySQL");
		// chBoxDataBase.getItems().add("SQL");
		chBoxDataBase.getSelectionModel().select("MySQL");
	}

}
