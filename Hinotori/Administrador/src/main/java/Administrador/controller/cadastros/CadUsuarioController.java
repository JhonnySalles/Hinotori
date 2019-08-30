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

import Administrador.controller.frame.PesquisaGenericaController;
import Administrador.controller.pesquisas.PsqUsuarioController;
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
import model.alerts.Alertas;
import model.constraints.Validadores;
import model.enums.Situacao;
import model.enums.UsuarioNivel;
import model.mask.Mascaras;
import model.notification.Notificacao;
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
	private AnchorPane rootPane;

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
	private UsuarioServices usuarioService;
	private Boolean edicao;
	private PsqUsuarioController PsqUsuario;

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
				atualizaEntidade();
				salvar();
				Notificacao.Dark("Processo concluído", "Cliente salvo com sucesso.", 3.0, Pos.BASELINE_RIGHT);
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
			if (usuarioService == null)
				setUsuarioServices(new UsuarioServices());
			usuarioService.deletar(usuario.getLogin());
			limpaCampos();
			Notificacao.Dark("Processo concluído", "Usuário excluido com sucesso.", 5.0, Pos.BASELINE_RIGHT);
		} else {
			Notificacao.Dark("Nenhum usuário selecionado", "Favor informar um usuário.", 5.0, Pos.BASELINE_RIGHT);
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
		PsqUsuario.returnView();
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

	public PsqUsuarioController getPsqUsuario() {
		return PsqUsuario;
	}

	public void setPsqUsuario(PsqUsuarioController PsqUsuario) {
		this.PsqUsuario = PsqUsuario;
	}

	public void carregaUsuario(Usuario userLogin) {
		this.usuario = userLogin;
		atualizaTela();
	}

	private void salvar() {
		if (usuarioService == null)
			setUsuarioServices(new UsuarioServices());
		usuarioService.salvar(usuario);
	}

	public void loadView(String tela) {
		PsqUsuario.loadView(tela);
	}

	public void returnView() {
		PsqUsuario.returnView();
	}

	private void atualizaTela() {
		edicao = true;
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

	private void atualizaEntidade() {
		usuario = new Usuario(txtNome.getText(), txtSobreNome.getText(),
				(!txtTelefone.getText().isEmpty() ? txtTelefone.getText().substring(1, 3) : ""),
				(!txtTelefone.getText().isEmpty() ? txtTelefone.getText().substring(4).replace("-", "") : ""),
				(!txtCelular.getText().isEmpty() ? txtCelular.getText().substring(1, 3) : ""),
				(!txtCelular.getText().isEmpty() ? txtCelular.getText().substring(4).replace("-", "") : ""),
				txtEmail.getText(), Long.parseLong(frameEmpresaController.getID()), txtLogin.getText().toUpperCase(),
				pswSenha.getText(), txtObservacao.getText(), imagem, cbNivel.getSelectionModel().getSelectedItem(),
				cbSituacao.getSelectionModel().getSelectedItem());
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
			if (!edicao && usuarioService.validaLogin(txtLogin.getText())) {
				txtLogin.setUnFocusColor(Color.RED);
				Notificacao.Dark("Usuário já cadastrado", "Favor informar outro usuário.", 5.0, Pos.BASELINE_RIGHT);
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
		frameEmpresaController.setPesquisa("Id", "NomeFantasia", "Id, NomeFantasia", "empresas", "", "",
				"ORDER BY Id, NomeFantasia");
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

	private void setUsuarioServices(UsuarioServices usuarioService) {
		this.usuarioService = usuarioService;
	}

	@Override
	public synchronized void initialize(URL arg0, ResourceBundle arg1) {
		background.setFitToHeight(true);
		background.setFitToWidth(true);
		configuraCampos();
		edicao = false;
	}
}
