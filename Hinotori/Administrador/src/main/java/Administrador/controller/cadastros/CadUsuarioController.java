package Administrador.controller.cadastros;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import Administrador.model.entities.Usuario;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import model.alerts.Alertas;
import model.constraints.Validadores;
import model.enums.Situacao;
import model.enums.UsuarioNivel;
import model.mask.Mascaras;
import model.utils.Utils;

public class CadUsuarioController implements Initializable {
	
	final static Image ImagemPadrao = new Image(
			CadUsuarioController.class.getResourceAsStream("/Administrador/resources/images/icon/icoUsuarioImage_256.png"));

	@FXML
	private ImageView imgUsuario;

	@FXML
	private JFXTextField txtNome;

	@FXML
	private JFXTextField txtSobreNome;

	@FXML
	private JFXComboBox<Situacao> cbSituacao;

	@FXML
	private JFXTextField txtLogin;

	@FXML
	private JFXPasswordField pswSenha;

	@FXML
	private JFXComboBox<UsuarioNivel> cbNivel;

	@FXML
	private JFXTextField txtTelefone;

	@FXML
	private JFXTextField txtCelular;

	@FXML
	private JFXTextField txtEmail;

	@FXML
	private JFXTextArea txtObservacao;

	@FXML
	private JFXButton btnExcluirImagem;

	@FXML
	private JFXButton btnProcurarImagem;

	@FXML
	private AnchorPane psqEmpresa;

	@FXML
	private Pane paneBackground;

	@FXML
	private ScrollPane background;

	@FXML
	private ImageView imgLogo;

	@FXML
	private Button btnConfirmar;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnNovo;

	@FXML
	private Button btnExcluir;

	@FXML
	private Button btnVoltar;

	private Usuario usuario;
	private byte[] imagem;

	@FXML
	public void onBtnConfirmarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnConfirmar.fire();
		}
	}

	@FXML
	public void onBtnConfirmarClick() {

		if (validaCampos()) {
			background.getScene().getRoot().setCursor(Cursor.WAIT);
			usuario = new Usuario(txtNome.getText(), txtSobreNome.getText(), txtTelefone.getText().substring(1, 2),
					txtTelefone.getText().substring(4), txtCelular.getText().substring(1, 2),
					txtCelular.getText().substring(4), txtEmail.getText(), Long.valueOf(1), txtLogin.getText(),
					pswSenha.getText(), txtObservacao.getText(), imagem, cbNivel.getSelectionModel().getSelectedItem(),
					cbSituacao.getSelectionModel().getSelectedItem());
			background.getScene().getRoot().setCursor(null);
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
		if (verificaAlteracao()) {
			if (Alertas.Confirmacao("Cancelar", "Deseja realmente cancelar? \nToda a alteração será descartada.")) {
				limpaCampos();
			}
		} else
			limpaCampos();
	}

	@FXML
	public void onBtnNovoEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnNovo.fire();
		}
	}

	@FXML
	public void onBtnNovoClick() {
		limpaCampos();
	}

	@FXML
	public void onBtnExcluirEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnExcluir.fire();
		}
	}

	@FXML
	public void onBtnExcluirClick() {

	}

	@FXML
	public void onBtnVoltarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnVoltar.fire();
		}
	}

	@FXML
	public void onBtnVoltarClick() {
		if (verificaAlteracao()) {
			if (Alertas.Confirmacao("Sair", "Deseja realmente sair? \nToda a alteração será descartada.")) {
				System.exit(0);
			}
		} else
			System.exit(0);
	}

	@FXML
	public void onBtnProcurarImagemEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnProcurarImagem.fire();
		}
	}

	@FXML
	public void onBtnProcurarImagemClick() {
		
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg");
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setTitle("Selecione uma imagem.");
		File caminhoImagem = fileChooser.showOpenDialog(null);

		if (caminhoImagem != null) {
			try {
				imagem = null;

		        imgUsuario.setImage(new Image(caminhoImagem.toURI().toString()));
								
				BufferedImage bImage = ImageIO.read(caminhoImagem);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ImageIO.write(bImage, Utils.getFileExtension(caminhoImagem), bos );
				imagem = bos.toByteArray();
			} catch (IOException e) {
				e.printStackTrace();
				Alertas.Erro("Erro ao carregar imagem", "Erro ao carregar a imagem.", e.getMessage(), false, true);
			}	
		}		
	}

	@FXML
	public void onBtnExcluirImagemEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnExcluirImagem.fire();
		}
	}

	@FXML
	public void onBtnExcluirImagemClick() {
		imagem = null;
		setImagemPadrao();
	}

	private Boolean verificaAlteracao() {

		if (!txtNome.getText().isEmpty() || !txtSobreNome.getText().isEmpty() || !txtLogin.getText().isEmpty()
				|| !pswSenha.getText().isEmpty() || !txtTelefone.getText().isEmpty() || !txtCelular.getText().isEmpty()
				|| !txtEmail.getText().isEmpty() || !txtObservacao.getText().isEmpty()) {
			return true;
		} else
			return false;
	}
	
	private void carregaCampos() {
		txtNome.setText(usuario.getNome());
		txtSobreNome.setText(usuario.getSobreNome());
		txtLogin.setText(usuario.getLogin());
		pswSenha.setText(usuario.getSenha());
		txtTelefone.setText(usuario.getDddTelefone() + usuario.getTelefone());
		txtCelular.setText(usuario.getDddCelular() + usuario.getCelular());
		txtEmail.setText(usuario.getEmail());
		txtObservacao.setText(usuario.getObservacao());
		
		if (usuario.getImagem() != null)
			imgUsuario.setImage(new Image(new ByteArrayInputStream(usuario.getImagem())));
		
	}
	
	public void carregaUsuario(Usuario userLogin) {
		usuario = userLogin;
		carregaCampos();
	}

	private void limpaCampos() {
		txtNome.setText("");
		txtSobreNome.setText("");
		txtLogin.setText("");
		pswSenha.setText("");
		txtTelefone.setText("");
		txtCelular.setText("");
		txtEmail.setText("");
		txtObservacao.setText("");
		setImagemPadrao();
	}

	private void setImagemPadrao() {
		imgUsuario.setImage(ImagemPadrao);
	}

	private Boolean validaCampos() {
		if (!txtNome.getText().isEmpty() && !txtSobreNome.getText().isEmpty() && !txtLogin.getText().isEmpty()
				&& !pswSenha.getText().isEmpty()) {
			txtNome.setStyle("-fx-border-color: green;");
			txtSobreNome.setStyle("-fx-border-color: green;");
			txtLogin.setStyle("-fx-border-color: green;");
			pswSenha.setStyle("-fx-border-color: green;");
			return true;
		} else {
			if (txtNome.getText().isEmpty()) {
				txtNome.setUnFocusColor(Color.RED);
			}

			if (txtSobreNome.getText().isEmpty()) {
				txtSobreNome.setUnFocusColor(Color.RED);
			}

			if (txtLogin.getText().isEmpty()) {
				txtLogin.setUnFocusColor(Color.RED);
			}

			if (pswSenha.getText().isEmpty()) {
				pswSenha.setUnFocusColor(Color.RED);
			}
			return false;
		}

	}

	private void configuraCampos() {

		Validadores.setTextFieldNotEmptyGreen(txtNome);
		Validadores.setTextFieldNotEmptyGreen(txtSobreNome);
		Validadores.setTextFieldNotEmptyGreen(txtLogin);

		Mascaras.foneField(txtTelefone);
		Mascaras.foneField(txtCelular);

		pswSenha.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue) { // Usado para limpar o stilo para que pinte quando entra
					pswSenha.setUnFocusColor(Color.BLACK);
				} else { // Após, na saida faz então a validacao.
					if (pswSenha.textProperty().get().toString().isEmpty()) {
						pswSenha.setUnFocusColor(Color.BLACK);
					} else {
						pswSenha.setUnFocusColor(Color.GREEN);
					}
				}
			}
		});

		cbSituacao.getItems().addAll(Situacao.values());
		cbNivel.getItems().addAll(UsuarioNivel.values());

		cbSituacao.getSelectionModel().select(Situacao.ATIVO);
		cbNivel.getSelectionModel().select(UsuarioNivel.USUARIO);

	}

	public synchronized void initialize(URL arg0, ResourceBundle arg1) {
		background.setFitToHeight(true);
		background.setFitToWidth(true);

		configuraCampos();

	}
}
