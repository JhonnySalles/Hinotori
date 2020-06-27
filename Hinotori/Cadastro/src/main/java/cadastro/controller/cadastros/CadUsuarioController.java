package cadastro.controller.cadastros;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import cadastro.controller.frame.PesquisaGenericaController;
import comum.form.DashboardFormPadrao;
import comum.model.constraints.Limitadores;
import comum.model.constraints.Validadores;
import comum.model.enums.Situacao;
import comum.model.enums.TamanhoImagem;
import comum.model.enums.UsuarioNivel;
import comum.model.exceptions.ExcessaoBd;
import comum.model.notification.Notificacoes;
import comum.model.utils.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import servidor.dao.services.UsuarioServices;
import servidor.entities.Imagem;
import servidor.entities.Usuario;

public class CadUsuarioController implements Initializable {

	/*
	 * Referencia para o controlador pai, onde é utilizado para realizar o refresh
	 * na tela
	 */
	private DashboardFormPadrao dashBoard;

	public final static Image ImagemPadrao = new Image(CadUsuarioController.class
			.getResourceAsStream("/cadastro/resources/imagens/white/geral/icoUsuarioImage_256.png"));

	@FXML
	private ScrollPane background;

	@FXML
	private StackPane spTelas;

	@FXML
	private AnchorPane rootUsuario;

	@FXML
	private JFXTextField txtId;

	// O formato do arquivo incluido é um anchorpane "conforme criei a tela",
	// o nome aqui será o mesmo que no id do fxml incluido.
	@FXML
	private AnchorPane frameTxtNome;

	// Para utilizar o controlador do frame incluido, basta colocar a descrição
	// "Controller" na frente do id do fxml incluido conforme abaixo.
	@FXML
	private PesquisaGenericaController frameTxtNomeController;

	@FXML
	private JFXTextField txtLogin;

	@FXML
	private JFXPasswordField pswSenha;

	@FXML
	private JFXTextArea txtObservacao;

	@FXML
	private JFXComboBox<Situacao> cbSituacao;

	@FXML
	private JFXComboBox<UsuarioNivel> cbNivel;

	@FXML
	private ImageView imgUsuario;

	@FXML
	private JFXButton btnExcluirImagem;

	@FXML
	private JFXButton btnProcurarImagem;

	@FXML
	private Button btnConfirmar;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnExcluir;

	@FXML
	private Button btnPesquisar;

	private Set<Imagem> imagens;
	private Usuario usuario;
	private UsuarioServices usuarioService;
	private String id;

	@FXML
	public void onBtnConfirmarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnConfirmar.fire();
		}
	}

	@FXML
	public void onBtnConfirmarClick() {
		if (validaCampos()) {
			try {
				background.cursorProperty().set(Cursor.WAIT);
				desabilitaBotoes().atualizaEntidade().salvar(usuario);
			} finally {
				background.cursorProperty().set(null);
				habilitaBotoes();
			}
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
	public void onBtnExcluirEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnExcluir.fire();
		}
	}

	@FXML
	public void onBtnExcluirClick() {
		if ((usuario.getId() == null) || txtId.getText().isEmpty() || txtId.getText().equalsIgnoreCase("0"))
			Notificacoes.notificacao(AlertType.INFORMATION, "Aviso",
					"Não foi possivel realizar a exclusão, nenhum usuário selecionado.");
		else {
			try {
				background.cursorProperty().set(Cursor.WAIT);
				desabilitaBotoes().atualizaEntidade().excluir(usuario);
			} finally {
				background.cursorProperty().set(null);
				habilitaBotoes();
			}
		}
	}

	@FXML
	public void onBtnPesquisarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnPesquisar.fire();
		}
	}

	@FXML
	public void onBtnPesquisarClick() {
		// limpaCampos();
	}

	@FXML
	public void onTxtIdClick() {
		txtId.getSelectedText();
	}

	@FXML
	public void onTxtIdEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			if (!txtId.getText().equalsIgnoreCase("0") && !txtId.getText().isEmpty())
				onTxtIdExit();
			else
				limpaCampos();

			Utils.clickTab();
		}
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
				String imagemNome = caminhoImagem.getName();
				String imagemExtenssao = Utils.getFileExtension(caminhoImagem);

				imgUsuario.setImage(new Image(caminhoImagem.toURI().toString()));

				if (imagens == null)
					imagens = new HashSet<Imagem>();

				BufferedImage bImage = ImageIO.read(caminhoImagem);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ImageIO.write(bImage, imagemExtenssao, bos);

				imagens.add(new Imagem(imagemNome, imagemExtenssao, bos.toByteArray(), TamanhoImagem.ORIGINAL));

				BufferedImage bImg100 = Utils.resizeImage(bImage, 100, 100);
				ImageIO.write(bImg100, imagemExtenssao, bos);
				imagens.add(new Imagem(imagemNome, imagemExtenssao, bos.toByteArray(), TamanhoImagem.PEQUENA));

				BufferedImage bImg600 = Utils.resizeImage(bImage, 600, 600);
				ImageIO.write(bImg600, imagemExtenssao, bos);
				imagens.add(new Imagem(imagemNome, imagemExtenssao, bos.toByteArray(), TamanhoImagem.MEDIA));

			} catch (IOException e) {
				e.printStackTrace();
				Notificacoes.notificacao(AlertType.ERROR, "Erro", "Não foi possível carregar a imagem.");
				setImagemPadrao();
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
		for (Imagem img : imagens) {
			img.setExcluir(true);
		}
		setImagemPadrao();
	}

	public void onTxtIdExit() {
		if (!txtId.getText().isEmpty() && !txtId.getText().equalsIgnoreCase(frameTxtNomeController.getId())) {
			try {
				carregaUsuario(usuarioService.pesquisar(Long.valueOf(txtId.getText()), TamanhoImagem.TODOS));
			} catch (ExcessaoBd e) {
				e.printStackTrace();
			}
		} else {
			if (txtId.getText().isEmpty() || txtId.getText().equalsIgnoreCase("0"))
				limpaCampos();
		}
	}

	public CadUsuarioController carregaUsuario(Usuario usuario) {
		this.usuario = usuario;
		if (usuario == null)
			limpaCampos();
		else
			atualizaTela(usuario);

		return this;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	private void salvar(Usuario usuario) {
		if (usuarioService == null)
			setUsuarioServices(new UsuarioServices());

		try {
			usuarioService.salvar(usuario);
			Notificacoes.notificacao(AlertType.NONE, "Concluído", "Cliente salvo com sucesso.");
			limpaCampos();
		} catch (ExcessaoBd e) {
			Notificacoes.notificacao(AlertType.ERROR, "Erro", e.getMessage());
		}
	}

	private void excluir(Usuario usuario) {
		if (usuarioService == null)
			setUsuarioServices(new UsuarioServices());

		try {
			usuarioService.deletar(usuario.getId());
			Notificacoes.notificacao(AlertType.NONE, "Concluído", "Usuário excluído com sucesso.");
			limpaCampos();
		} catch (ExcessaoBd e) {
			Notificacoes.notificacao(AlertType.ERROR, "Erro", e.getMessage());
		}

	}

	private CadUsuarioController desabilitaBotoes() {
		background.setDisable(true);
		btnConfirmar.setDisable(true);
		btnCancelar.setDisable(true);
		btnExcluir.setDisable(true);
		return this;
	}

	private CadUsuarioController habilitaBotoes() {
		background.setDisable(false);
		btnConfirmar.setDisable(false);
		btnCancelar.setDisable(false);
		btnExcluir.setDisable(false);
		return this;
	}

	// Devido a um erro no componente, caso venha do banco o valor null, estoura
	// erro na edição do campo.
	// Necessário a validação para casos em que ouve problemas com o registro no
	// banco.
	private synchronized CadUsuarioController atualizaTela(Usuario usuario) {
		limpaCampos();

		this.usuario = usuario;

		txtId.setText(usuario.getId().toString());

		if (usuario.getNomeSobrenome() != null && !usuario.getNomeSobrenome().isEmpty())
			frameTxtNomeController.setDescricao(usuario.getNomeSobrenome());

		if (usuario.getLogin() != null && !usuario.getLogin().isEmpty())
			txtLogin.setText(usuario.getLogin());

		if (usuario.getSenha() != null && !usuario.getSenha().isEmpty())
			pswSenha.setText(usuario.getSenha());

		if (usuario.getObservacao() != null && !usuario.getObservacao().isEmpty())
			txtObservacao.setText(usuario.getObservacao());

		cbSituacao.getSelectionModel().select(usuario.getSituacao().ordinal());
		cbNivel.getSelectionModel().select(usuario.getNivel().ordinal());

		if (usuario.getImagens() != null && usuario.getImagens().size() > 0) {
			imagens = usuario.getImagens();
			imgUsuario
					.setImage(new Image(new ByteArrayInputStream(usuario.getImagens().iterator().next().getImagem())));
		} else {
			setImagemPadrao();
		}

		// Necessário por um bug na tela ao carregar ela.
		dashBoard.atualizaTabPane();

		return this;
	}

	private CadUsuarioController atualizaEntidade() {
		if (usuario == null)
			usuario = new Usuario();

		if (txtId.getText().isEmpty() || txtId.getText().equalsIgnoreCase("0"))
			usuario.setId(Long.valueOf(0));
		else
			usuario.setId(Long.valueOf(txtId.getText()));

		usuario.setNomeSobrenome(frameTxtNomeController.getDescricao());
		usuario.setDataCadastro(Timestamp.valueOf(LocalDate.now().atTime(LocalTime.MIDNIGHT)));
		usuario.setLogin(txtLogin.getText());
		usuario.setSenha(pswSenha.getText());
		usuario.setObservacao(txtObservacao.getText());
		usuario.setNivel(cbNivel.getSelectionModel().getSelectedItem());
		usuario.setSituacao(cbSituacao.getSelectionModel().getSelectedItem());
		usuario.setImagens(imagens);

		return this;
	}

	private synchronized CadUsuarioController limpaCampos() {
		usuario = new Usuario();
		frameTxtNomeController.limpaCampos();
		txtId.setText("0");
		txtLogin.setText("");
		pswSenha.setText("");
		txtObservacao.setText("");
		cbNivel.getSelectionModel().selectFirst();
		cbSituacao.getSelectionModel().selectFirst();
		setImagemPadrao();
		return this;
	}

	private CadUsuarioController setImagemPadrao() {
		imagens = null;
		imgUsuario.setImage(ImagemPadrao);
		return this;
	}

	private Boolean validaCampos() {
		Boolean valida = true;

		if (frameTxtNomeController.txtFraPesquisa.getText().isEmpty()) {
			frameTxtNomeController.txtFraPesquisa.setUnFocusColor(Color.RED);
			valida = false;
		}

		if (txtLogin.getText().isEmpty()) {
			txtLogin.setUnFocusColor(Color.RED);
			valida = false;
		} else {
			try {
				if ((txtId.getText().isEmpty() || txtId.getText().equalsIgnoreCase("0"))
						&& usuarioService.validaLogin(txtLogin.getText())) {
					txtLogin.setUnFocusColor(Color.RED);
					Notificacoes.notificacao(AlertType.INFORMATION, "Usuário já cadastrado",
							"Favor informar outro usuário.");
					valida = false;
				}
			} catch (ExcessaoBd e) {
				e.printStackTrace();
				valida = false;
				Notificacoes.notificacao(AlertType.ERROR, "Erro", e.getMessage());
			}
		}

		if (pswSenha.getText().isEmpty()) {
			pswSenha.setUnFocusColor(Color.RED);
			valida = false;
		}

		return valida;
	}

	private CadUsuarioController setSqlFrame() {
		frameTxtNomeController.setPesquisa("Id", "Nome", "id, nomeSobrenome AS Nome, Login", "usuarios", "",
				" AND id <> 0 ", "ORDER BY Login");
		frameTxtNomeController.txtFraPesquisa.setPromptText("Nome");
		frameTxtNomeController.setOnDuploClique(carregaDados -> {
			if (frameTxtNomeController.getId() != null)
				try {
					carregaUsuario(usuarioService.pesquisar(Long.valueOf(frameTxtNomeController.getId()),
							TamanhoImagem.TODOS));
				} catch (ExcessaoBd e) {
					Notificacoes.notificacao(AlertType.ERROR, "Erro", e.getMessage());
				}
		});
		return this;
	}

	private void setUsuarioServices(UsuarioServices usuarioService) {
		this.usuarioService = usuarioService;
	}

	private CadUsuarioController configuraExitId() {
		txtId.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {

				if (newPropertyValue)
					id = txtId.getText();

				if (oldPropertyValue && !id.equalsIgnoreCase(txtId.getText()))
					onTxtIdExit();
			}
		});
		return this;
	}

	@Override
	public synchronized void initialize(URL arg0, ResourceBundle arg1) {
		setUsuarioServices(new UsuarioServices());
		Limitadores.setTextFieldInteger(txtId);

		Validadores.setTextFieldChangeBlack(frameTxtNomeController.txtFraPesquisa);
		Validadores.setTextFieldNotEmpty(txtLogin);
		Validadores.setTextFieldNotEmpty(pswSenha);

		cbSituacao.getItems().addAll(Situacao.values());
		cbNivel.getItems().add(UsuarioNivel.USUARIO);
		cbNivel.getItems().add(UsuarioNivel.ADMINISTRADOR);
		cbSituacao.getSelectionModel().select(Situacao.ATIVO);
		cbNivel.getSelectionModel().select(UsuarioNivel.USUARIO);

		txtId.setText("0");
		setSqlFrame().configuraExitId();

		usuario = new Usuario();
	}

	public DashboardFormPadrao getDashBoard() {
		return dashBoard;
	}

	public void setDashBoard(DashboardFormPadrao dashBoard) {
		this.dashBoard = dashBoard;
	}

	public static URL getFxmlLocate() {
		return CadUsuarioController.class.getResource("/cadastro/view/cadastros/CadUsuario.fxml");
	}
}
