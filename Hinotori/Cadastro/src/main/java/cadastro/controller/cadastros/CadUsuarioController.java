package cadastro.controller.cadastros;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import cadastro.utils.CadastroUtils;
import comum.form.CadastroFormPadrao;
import comum.model.constraints.Limitadores;
import comum.model.constraints.Validadores;
import comum.model.encode.DecodeHash;
import comum.model.enums.Situacao;
import comum.model.enums.UsuarioNivel;
import comum.model.exceptions.ExcessaoBd;
import comum.model.exceptions.ExcessaoCadastro;
import comum.model.messages.Mensagens;
import comum.model.notification.Notificacoes;
import comum.model.utils.Utils;
import comum.model.utils.ViewGerenciador;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import servidor.entities.Imagem;
import servidor.entities.Usuario;
import servidor.entities.UsuarioImagem;
import servidor.validations.ValidaUsuario;

public class CadUsuarioController extends CadastroFormPadrao {

	private final static Logger LOGGER = Logger.getLogger(CadUsuarioController.class.getName());

	public final static Image ImagemPadrao = new Image(
			CadUsuarioController.class.getResourceAsStream("/cadastro/imagens/white/geral/icoUsuarioImage_256.png"));

	@FXML
	private JFXTextField txtId;

	@FXML
	private JFXTextField txtNome;

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

	private Set<UsuarioImagem> imagens;
	private Usuario usuario;

	@Override
	public void onBtnConfirmarClick() {
		try {
			spBackground.cursorProperty().set(Cursor.WAIT);
			if (ValidaUsuario.validaUsuario(usuario))
				desabilitaBotoes().salvar(usuario);

		} catch (ExcessaoCadastro e) {
			Notificacoes.notificacao(AlertType.INFORMATION, "Cadastro de usuário inválido", e.getMessage());
			e.printStackTrace();
		} catch (ExcessaoBd e) {
			Notificacoes.notificacao(AlertType.INFORMATION, "Cadastro de usuário inválido", e.getMessage());
			e.printStackTrace();
			LOGGER.log(Level.INFO, "{Erro ao salvar o usuário}", e);
		} finally {
			spBackground.cursorProperty().set(null);
			habilitaBotoes();
		}
	}

	@Override
	public void onBtnCancelarClick() {
		limpaCampos();
	}

	@Override
	public void onBtnExcluirClick() {
		if ((usuario.getId() == null) || usuario.getId() == 0 || txtId.getText().equalsIgnoreCase("0"))
			Notificacoes.notificacao(AlertType.INFORMATION, Mensagens.AVISO,
					Mensagens.CADASTRO_EXCLUIR + " Nenhum usuário selecionado.");
		else {
			try {
				spBackground.cursorProperty().set(Cursor.WAIT);
				desabilitaBotoes().excluir(usuario);
			} finally {
				spBackground.cursorProperty().set(null);
				habilitaBotoes();
			}
		}
	}

	@Override
	public void onBtnVoltarClick() {
		ViewGerenciador.closeTela(spRoot);
		onClose();
	}

	@FXML
	public void onBtnProcurarImagemClick() {
		File caminhoImagem = Utils.procurarImagem();

		if (caminhoImagem != null) {
			try {
				if (imagens == null)
					imagens = new HashSet<UsuarioImagem>();

				imgUsuario.setImage(new Image(caminhoImagem.toURI().toString()));
				imagens.addAll(CadastroUtils.processaImagens(caminhoImagem).stream()
						.map(imagem -> UsuarioImagem.toUsuarioImagem(imagem)).collect(Collectors.toList()));
			} catch (IOException e) {
				e.printStackTrace();
				LOGGER.log(Level.INFO, "{Erro ao carregar e processar a imagem}", e);
				Notificacoes.notificacao(AlertType.ERROR, Mensagens.ERRO, Mensagens.IMG_ERRO_CARREGAR);
				setImagemPadrao();
			}
		}
	}

	@FXML
	public void onBtnExcluirImagemClick() {
		for (Imagem img : imagens)
			img.setExcluir(true);

		setImagemPadrao();
	}

	@FXML
	public void onTxtIdClick() {
		txtId.getSelectedText();
	}

	@FXML
	public void onTxtIdEnter(KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER)) {
			if (!txtId.getText().equalsIgnoreCase("0") && !txtId.getText().isEmpty())
				onTxtIdExit();
			else
				limpaCampos();

			Utils.clickTab();
		}
	}

	public void onTxtIdExit() {
		if (!txtId.getText().isEmpty())
			carregar(pesquisar(new Usuario(Long.valueOf(txtId.getText()))));
		else if (txtId.getText().isEmpty() || txtId.getText().equalsIgnoreCase("0"))
			limpaCampos();
	}

	@Override
	protected <T> void salvar(T entidade) {
		/*
		 * try { Notificacoes.notificacao(AlertType.NONE, Mensagens.CONCLUIDO,
		 * "Cliente salvo com sucesso." + " Id:" + usuario.getId()); limpaCampos(); }
		 * catch (ExcessaoBd e) { Notificacoes.notificacao(AlertType.ERROR,
		 * Mensagens.ERRO, e.getMessage()); LOGGER.log(Level.INFO,
		 * "{Erro ao conectar ao banco}", e); }
		 */

	}

	@Override
	protected <T> void excluir(T entidade) {
		/*
		 * try {
		 * 
		 * Notificacoes.notificacao(AlertType.NONE, Mensagens.CONCLUIDO,
		 * "Usuário excluído com sucesso."); limpaCampos(); } catch (ExcessaoBd e) {
		 * Notificacoes.notificacao(AlertType.ERROR, Mensagens.ERRO, e.getMessage());
		 * LOGGER.log(Level.INFO, "{Erro ao excluir o usuário ao banco}", e); }
		 */
	}

	@Override
	protected <T> T pesquisar(T entidade) {
		/*
		 * try { } catch (ExcessaoBd e) { e.printStackTrace(); LOGGER.log(Level.INFO,
		 * "{Erro ao pesquisar o usuário}", e); }
		 */
		return entidade;
	}

	@Override
	public <T> void carregar(T entidade) {
		if (entidade == null)
			limpaCampos();
		else
			atualizaTela((Usuario) entidade);
	}

	@Override
	protected boolean validaCampos() {
		try {
			ValidaUsuario.validaSenha(usuario.getSenha());

			usuario.setSenha(DecodeHash.CriptografaSenha(usuario.getSenha()));
		} catch (ExcessaoCadastro e) {
			txtNome.setUnFocusColor(Color.RED);
			Notificacoes.notificacao(AlertType.INFORMATION, "Senha inválida", e.getMessage());
			e.printStackTrace();
			return false;
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			Notificacoes.notificacao(AlertType.ERROR, "Erro", "Não foi possível codificar a senha");
			e.printStackTrace();
			LOGGER.log(Level.WARNING, "{Erro ao codificar a senha}", e);
			return false;
		}

		try {
			return ValidaUsuario.validaUsuario(usuario);
		} catch (ExcessaoCadastro | ExcessaoBd e) {
			e.printStackTrace();
		}

		txtNome.validate();
		txtLogin.validate();
		pswSenha.validate();

		Validadores.setTextFieldNotEmpty(txtNome);
		Validadores.setTextFieldNotEmpty(txtLogin);
		Validadores.setPasswordFieldNotEmpty(pswSenha);

		try {
			ValidaUsuario.validaLogin(usuario);
		} catch (ExcessaoCadastro e) {
			txtNome.setUnFocusColor(Color.RED);
			Notificacoes.notificacao(AlertType.INFORMATION, "Login inválido", e.getMessage());
			e.printStackTrace();
		} catch (ExcessaoBd e) {
			Notificacoes.notificacao(AlertType.INFORMATION, "Não foi possível validar o login", e.getMessage());
			e.printStackTrace();
			LOGGER.log(Level.INFO, "{Erro ao conectar ao banco}", e);
		}

		return true;
	}

	@Override
	protected void limpaCampos() {
		usuario = new Usuario();

		txtId.setText("");
		txtNome.setText("");
		txtLogin.setText("");
		pswSenha.setText("");
		txtObservacao.setText("");
		cbSituacao.getSelectionModel().select(Situacao.ATIVO);
		cbNivel.getSelectionModel().select(UsuarioNivel.USUARIO);
		setImagemPadrao();
	}

	@Override
	public CadastroFormPadrao atualizaEntidade() {
		if (usuario == null)
			usuario = new Usuario();

		usuario.setNomeSobrenome(txtNome.getText());
		usuario.setLogin(txtLogin.getText().toUpperCase());
		usuario.setSenha(pswSenha.getText());
		usuario.setObservacao(txtObservacao.getText());
		usuario.setNivel(cbNivel.getSelectionModel().getSelectedItem());
		usuario.setSituacao(cbSituacao.getSelectionModel().getSelectedItem());

		return this;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	private CadUsuarioController desabilitaBotoes() {
		spBackground.setDisable(true);
		btnConfirmar.setDisable(true);
		btnCancelar.setDisable(true);
		btnExcluir.setDisable(true);
		return this;
	}

	private CadUsuarioController habilitaBotoes() {
		spBackground.setDisable(false);
		btnConfirmar.setDisable(false);
		btnCancelar.setDisable(false);
		btnExcluir.setDisable(false);
		return this;
	}

	// Devido a um erro no componente, caso venha do banco o valor null, estoura
	// erro na edição do campo.
	// Necessário a validação para casos em que ouve problemas com o registro no
	// banco.
	private CadUsuarioController atualizaTela(Usuario usuario) {
		limpaCampos();

		this.usuario = usuario;

		txtId.setText(usuario.getId().toString());
		txtNome.setText(usuario.getNomeSobrenome());
		txtLogin.setText(usuario.getLogin());
		pswSenha.setText(""); // A senha será necessária sempre informa-la ao editar o cadastro.
		txtObservacao.setText(usuario.getObservacao());
		cbSituacao.getSelectionModel().select(usuario.getSituacao().ordinal());
		cbNivel.getSelectionModel().select(usuario.getNivel().ordinal());

		if (usuario.getImagens() != null && usuario.getImagens().size() > 0) {
			imagens = usuario.getImagens();
			imgUsuario
					.setImage(new Image(new ByteArrayInputStream(usuario.getImagens().iterator().next().getImagem())));
		} else
			setImagemPadrao();

		return this;
	}

	private CadUsuarioController setImagemPadrao() {
		imagens = null;
		imgUsuario.setImage(ImagemPadrao);
		return this;
	}

	private CadUsuarioController configuraExitCampos() {

		return this;
	}

	@Override
	public synchronized void inicializa(URL arg0, ResourceBundle arg1) {
		Limitadores.setTextFieldInteger(txtId);

		Validadores.setTextFieldNotEmpty(txtNome);
		Validadores.setTextFieldNotEmpty(txtLogin);
		Validadores.setPasswordFieldNotEmpty(pswSenha);

		cbSituacao.getItems().addAll(Situacao.values());
		cbNivel.getItems().add(UsuarioNivel.USUARIO);
		cbNivel.getItems().add(UsuarioNivel.ADMINISTRADOR);

		limpaCampos();

		configuraExitCampos();

		atualizaTela(new Usuario());
	}

	public static URL getFxmlLocate() {
		return CadUsuarioController.class.getResource("/cadastro/view/cadastros/CadUsuario.fxml");
	}
}
