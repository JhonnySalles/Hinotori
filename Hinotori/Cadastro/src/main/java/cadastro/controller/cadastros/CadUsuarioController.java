package cadastro.controller.cadastros;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import comum.form.CadastroFormPadrao;
import comum.form.DashboardFormPadrao;
import comum.model.constraints.Limitadores;
import comum.model.constraints.Validadores;
import comum.model.encode.DecodeHash;
import comum.model.enums.Situacao;
import comum.model.enums.TamanhoImagem;
import comum.model.enums.UsuarioNivel;
import comum.model.exceptions.ExcessaoBd;
import comum.model.exceptions.ExcessaoCadastro;
import comum.model.messages.Mensagens;
import comum.model.notification.Notificacoes;
import comum.model.utils.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import servidor.dao.services.UsuarioServices;
import servidor.entities.Imagem;
import servidor.entities.Usuario;
import servidor.validations.ValidaUsuario;

public class CadUsuarioController extends CadastroFormPadrao implements Initializable {

	private final static Logger LOGGER = Logger.getLogger(CadUsuarioController.class.getName());

	public final static Image ImagemPadrao = new Image(CadUsuarioController.class
			.getResourceAsStream("/cadastro/resources/imagens/white/geral/icoUsuarioImage_256.png"));

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

	private Set<Imagem> imagens;
	private Usuario usuario;
	private UsuarioServices usuarioService;

	@Override
	public void onConfirmarKeyPress(KeyEvent e) {
		if (e.getCode().toString().equals("ENTER"))
			btnConfirmar.fire();
	}

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
	public void onCancelarKeyPress(KeyEvent e) {
		if (e.getCode().toString().equals("ENTER"))
			btnCancelar.fire();
	}

	@Override
	public void onBtnCancelarClick() {
		limpaCampos();
	}

	@Override
	public void onExcluirKeyPress(KeyEvent e) {
		if (e.getCode().toString().equals("ENTER"))
			btnExcluir.fire();
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
	public void onVoltarKeyPress(KeyEvent e) {
		if (e.getCode().toString().equals("ENTER"))
			btnVoltar.fire();
	}

	@Override
	public void onBtnVoltarClick() {
		DashboardFormPadrao.closeTela(spRoot);
		onClose();
	}

	@FXML
	public void onBtnProcurarImagemEnter(KeyEvent e) {
		if (e.getCode().toString().equals("ENTER"))
			btnProcurarImagem.fire();
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
				LOGGER.log(Level.INFO, "{Erro ao carregar e processar a imagem}", e);
				Notificacoes.notificacao(AlertType.ERROR, Mensagens.ERRO, "Não foi possível carregar a imagem.");
				setImagemPadrao();
			}
		}
	}

	@FXML
	public void onBtnExcluirImagemEnter(KeyEvent e) {
		if (e.getCode().toString().equals("ENTER"))
			btnExcluirImagem.fire();
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
		if (e.getCode().toString().equals("ENTER")) {
			if (!txtId.getText().equalsIgnoreCase("0") && !txtId.getText().isEmpty())
				onTxtIdExit();
			else
				limpaCampos();

			Utils.clickTab();
		}
	}

	public void onTxtIdExit() {
		if (!txtId.getText().isEmpty()) {
			try {
				carregaUsuario(usuarioService.pesquisar(Long.valueOf(txtId.getText()), TamanhoImagem.TODOS));
			} catch (ExcessaoBd e) {
				e.printStackTrace();
				LOGGER.log(Level.INFO, "{Erro ao pesquisar o usuário}", e);
			}
		} else if (txtId.getText().isEmpty() || txtId.getText().equalsIgnoreCase("0"))
			limpaCampos();
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
			Notificacoes.notificacao(AlertType.NONE, Mensagens.CONCLUIDO,
					"Cliente salvo com sucesso." + " Id:" + usuario.getId());
			limpaCampos();
		} catch (ExcessaoBd e) {
			Notificacoes.notificacao(AlertType.ERROR, Mensagens.ERRO, e.getMessage());
			LOGGER.log(Level.INFO, "{Erro ao conectar ao banco}", e);
		}
	}

	private void excluir(Usuario usuario) {
		if (usuarioService == null)
			setUsuarioServices(new UsuarioServices());

		try {
			usuarioService.deletar(usuario.getId());
			Notificacoes.notificacao(AlertType.NONE, Mensagens.CONCLUIDO, "Usuário excluído com sucesso.");
			limpaCampos();
		} catch (ExcessaoBd e) {
			Notificacoes.notificacao(AlertType.ERROR, Mensagens.ERRO, e.getMessage());
			LOGGER.log(Level.INFO, "{Erro ao excluir o usuário ao banco}", e);
		}

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
	private synchronized CadUsuarioController atualizaTela(Usuario usuario) {
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

	private synchronized CadUsuarioController limpaCampos() {
		atualizaTela(new Usuario());
		return this;
	}

	private CadUsuarioController setImagemPadrao() {
		imagens = null;
		imgUsuario.setImage(ImagemPadrao);
		return this;
	}

	private void setUsuarioServices(UsuarioServices usuarioService) {
		this.usuarioService = usuarioService;
	}

	private CadUsuarioController configuraExitCampos() {

		txtNome.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {

				if (oldPropertyValue) {
					usuario.setNomeSobrenome(txtNome.getText());

					try {
						ValidaUsuario.validaNome(usuario.getNomeSobrenome());
					} catch (ExcessaoCadastro e) {
						txtNome.setUnFocusColor(Color.RED);
						Notificacoes.notificacao(AlertType.INFORMATION, "Nome inválido", e.getMessage());
						e.printStackTrace();
					}
				}

			}
		});

		txtLogin.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {

				if (oldPropertyValue) {
					usuario.setLogin(txtLogin.getText().toUpperCase());

					try {
						ValidaUsuario.validaLogin(usuario);
					} catch (ExcessaoCadastro e) {
						txtNome.setUnFocusColor(Color.RED);
						Notificacoes.notificacao(AlertType.INFORMATION, "Login inválido", e.getMessage());
						e.printStackTrace();
					} catch (ExcessaoBd e) {
						Notificacoes.notificacao(AlertType.INFORMATION, "Não foi possível validar o login",
								e.getMessage());
						e.printStackTrace();
						LOGGER.log(Level.INFO, "{Erro ao conectar ao banco}", e);
					}
				}

			}
		});

		pswSenha.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {

				if (oldPropertyValue) {
					usuario.setSenha(pswSenha.getText());

					try {
						ValidaUsuario.validaSenha(usuario.getSenha());

						usuario.setSenha(DecodeHash.DecodePassword(usuario.getSenha()));
					} catch (ExcessaoCadastro e) {
						txtNome.setUnFocusColor(Color.RED);
						Notificacoes.notificacao(AlertType.INFORMATION, "Senha inválida", e.getMessage());
						e.printStackTrace();
					} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
						Notificacoes.notificacao(AlertType.ERROR, "Erro", "Não foi possível codificar a senha");
						e.printStackTrace();
						LOGGER.log(Level.WARNING, "{Erro ao codificar a senha}", e);
					}
				}

			}
		});

		txtObservacao.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {

				if (oldPropertyValue)
					usuario.setObservacao(txtObservacao.getText());

			}
		});

		cbNivel.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {

				if (oldPropertyValue)
					usuario.setNivel(cbNivel.getSelectionModel().getSelectedItem());

			}
		});

		cbSituacao.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {

				if (oldPropertyValue)
					usuario.setSituacao(cbSituacao.getSelectionModel().getSelectedItem());

			}
		});

		return this;
	}

	@Override
	public synchronized void initialize(URL arg0, ResourceBundle arg1) {
		inicializaHeranca();
		setUsuarioServices(new UsuarioServices());
		Limitadores.setTextFieldInteger(txtId);

		Validadores.setTextFieldNotEmpty(txtNome);
		Validadores.setTextFieldNotEmpty(txtLogin);
		Validadores.setTextFieldNotEmpty(pswSenha);

		cbSituacao.getItems().addAll(Situacao.values());
		cbNivel.getItems().add(UsuarioNivel.USUARIO);
		cbNivel.getItems().add(UsuarioNivel.ADMINISTRADOR);
		cbSituacao.getSelectionModel().select(Situacao.ATIVO);
		cbNivel.getSelectionModel().select(UsuarioNivel.USUARIO);

		configuraExitCampos();

		atualizaTela(new Usuario());
	}

	public static URL getFxmlLocate() {
		return CadUsuarioController.class.getResource("/cadastro/view/cadastros/CadUsuario.fxml");
	}
}
