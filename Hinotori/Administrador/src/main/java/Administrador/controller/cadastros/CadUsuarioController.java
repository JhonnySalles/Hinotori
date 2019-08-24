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

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import Administrador.controller.frame.PesquisaGenericaController;
import Administrador.model.dao.services.UsuarioServices;
import Administrador.model.entities.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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
import javafx.util.Duration;
import model.alerts.Alertas;
import model.constraints.Validadores;
import model.enums.Situacao;
import model.enums.UsuarioNivel;
import model.mask.Mascaras;
import model.utils.Utils;

public class CadUsuarioController implements Initializable {

	final static Image ImagemPadrao = new Image(CadUsuarioController.class
			.getResourceAsStream("/Administrador/resources/geral/images/icon/icoUsuarioImage_256.png"));

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
	private AnchorPane frameEmpresa;

	@FXML
	private PesquisaGenericaController frameEmpresaController;

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
	private UsuarioServices usuarioServices;
	private Boolean edicao;
	private Notifications notificacao;

	@FXML
	public void onBtnConfirmarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnConfirmar.fire();
		}
	}

	@FXML
	public void onBtnConfirmarClick() {
		try {
			if (validaCampos()) {
				background.getScene().getRoot().setCursor(Cursor.WAIT);
				usuario = new Usuario(txtNome.getText(), txtSobreNome.getText(),
						(!txtTelefone.getText().isEmpty() ? txtTelefone.getText().substring(1, 3) : ""),
						(!txtTelefone.getText().isEmpty() ? txtTelefone.getText().substring(4).replace("-", "") : ""),
						(!txtCelular.getText().isEmpty() ? txtCelular.getText().substring(1, 3) : ""),
						(!txtCelular.getText().isEmpty() ? txtCelular.getText().substring(4).replace("-", "") : ""),
						txtEmail.getText(), Long.parseLong(frameEmpresaController.getID()),
						txtLogin.getText().toUpperCase(), pswSenha.getText(), txtObservacao.getText(), imagem,
						cbNivel.getSelectionModel().getSelectedItem(),
						cbSituacao.getSelectionModel().getSelectedItem());

				if (usuarioServices == null)
					setUsuarioServices(new UsuarioServices());

				usuarioServices.salvar(usuario);
				limpaCampos();

			}
		} finally {
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
		if ((usuario != null) && (!usuario.getLogin().isEmpty())) {
			if (usuarioServices == null)
				setUsuarioServices(new UsuarioServices());
			usuarioServices.deletar(usuario.getLogin());
			limpaCampos();
			notificacao = Notifications.create().title("Processo concluído")
					.text("Usuário excluido com sucesso.").hideAfter(Duration.seconds(5))
					.position(Pos.BASELINE_RIGHT).darkStyle();
			notificacao.show();
		} else {
			notificacao = Notifications.create().title("Nenhum usuário selecionado")
					.text("Favor informar um usuário.").hideAfter(Duration.seconds(5))
					.position(Pos.BASELINE_RIGHT).darkStyle();
			notificacao.show();
		}
				
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
				ImageIO.write(bImage, Utils.getFileExtension(caminhoImagem), bos);
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
		edicao = false;
		txtNome.setText("");
		txtSobreNome.setText("");
		txtLogin.setText("");
		pswSenha.setText("");
		txtTelefone.setText("");
		txtCelular.setText("");
		txtEmail.setText("");
		txtObservacao.setText("");
		setImagemPadrao();
		usuario = null;
	}

	private void setImagemPadrao() {
		imgUsuario.setImage(ImagemPadrao);
	}

	private Boolean validaCampos() {
		if (txtNome.getText().isEmpty())
			txtNome.setUnFocusColor(Color.RED);

		if (txtSobreNome.getText().isEmpty())
			txtSobreNome.setUnFocusColor(Color.RED);

		if (txtLogin.getText().isEmpty()) {
			txtLogin.setUnFocusColor(Color.RED);
		} else {
			if (!edicao && usuarioServices.validaLogin(txtLogin.getText())) {
				txtLogin.setUnFocusColor(Color.RED);
				notificacao = Notifications.create().title("Usuário já cadastrado")
						.text("Favor informar outro usuário.").hideAfter(Duration.seconds(5))
						.position(Pos.BASELINE_RIGHT).darkStyle();
				notificacao.show();
			}
		}

		if (pswSenha.getText().isEmpty())
			pswSenha.setUnFocusColor(Color.RED);

		if (frameEmpresaController.getID().isEmpty())
			frameEmpresaController.txt_Pesquisa.setUnFocusColor(Color.RED);

		if (txtNome.getText().isEmpty() || txtSobreNome.getText().isEmpty() || txtLogin.getText().isEmpty()
				|| pswSenha.getText().isEmpty() || frameEmpresaController.getID().isEmpty()
				|| !Validadores.validaEmail(txtEmail) || !Validadores.validaTelefone(txtTelefone)
				|| !Validadores.validaTelefone(txtCelular)) {
			return false;
		} else {
			return true;
		}
	}

	private void configuraCampos() {
		frameEmpresaController.setPesquisa("Id", "Nome_Fantasia", "Id, Nome_Fantasia", "empresas", "", "",
				"ORDER BY Id, Nome_Fantasia");
		frameEmpresaController.txt_Pesquisa.setPromptText("Pesquisa de empresas");

		Validadores.setTextFieldChangeBlack(txtNome);
		Validadores.setTextFieldChangeBlack(txtSobreNome);
		Validadores.setTextFieldChangeBlack(txtLogin);
		Validadores.setTextFieldChangeBlack(frameEmpresaController.txt_Pesquisa);
		Validadores.setPasswordFieldChangeBlack(pswSenha);
		Validadores.setTextFielEmailExitColor(txtEmail);
		Validadores.setTextFielTelefoneExitColor(txtTelefone);
		Validadores.setTextFielTelefoneExitColor(txtCelular);

		Mascaras.foneField(txtTelefone);
		Mascaras.foneField(txtCelular);

		cbSituacao.getItems().addAll(Situacao.values());
		cbNivel.getItems().addAll(UsuarioNivel.values());

		cbSituacao.getSelectionModel().select(Situacao.ATIVO);
		cbNivel.getSelectionModel().select(UsuarioNivel.USUARIO);
		setUsuarioServices(new UsuarioServices());
	}

	private void setUsuarioServices(UsuarioServices usuarioServices) {
		this.usuarioServices = usuarioServices;
	}

	public synchronized void initialize(URL arg0, ResourceBundle arg1) {
		background.setFitToHeight(true);
		background.setFitToWidth(true);
		configuraCampos();
		edicao = false;
	}
}
