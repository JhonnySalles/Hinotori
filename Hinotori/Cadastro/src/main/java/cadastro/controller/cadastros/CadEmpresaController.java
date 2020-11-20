package cadastro.controller.cadastros;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import cadastro.controller.lista.ListaContatoController;
import cadastro.controller.lista.ListaEnderecoController;
import comum.form.CadastroFormPadrao;
import comum.model.constraints.Limitadores;
import comum.model.constraints.TecladoUtils;
import comum.model.constraints.Validadores;
import comum.model.enums.Situacao;
import comum.model.enums.TamanhoImagem;
import comum.model.exceptions.ExcessaoBd;
import comum.model.exceptions.ExcessaoCadastro;
import comum.model.mask.Mascaras;
import comum.model.messages.Mensagens;
import comum.model.notification.Notificacoes;
import comum.model.utils.Utils;
import comum.model.utils.ViewGerenciador;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import servidor.dao.services.EmpresaServices;
import servidor.entities.Empresa;
import servidor.entities.Imagem;
import servidor.validations.ValidaEmpresa;

public class CadEmpresaController extends CadastroFormPadrao {

	private final static Logger LOGGER = Logger.getLogger(CadEmpresaController.class.getName());

	final static Image LogoPadrao = new Image(
			CadEmpresaController.class.getResourceAsStream("/cadastro/resources/imagens/icon/icoPrincipal_100.png"));

	@FXML
	private JFXTextField txtId;

	@FXML
	private JFXTextField txtRazaoSocial;

	@FXML
	private JFXTextField txtNomeFantasia;

	@FXML
	private JFXTextField txtCnpj;

	@FXML
	private JFXComboBox<Situacao> cbSituacao;

	@FXML
	private JFXButton btnContato;

	@FXML
	private JFXButton btnEndereco;

	@FXML
	private ImageView imgLogo;

	@FXML
	private JFXButton btnExcluirImagem;

	@FXML
	private JFXButton btnProcurarImagem;

	private Set<Imagem> imagens;
	private Empresa empresa;
	private EmpresaServices empresaService;
	private String id;

	@Override
	public void onBtnConfirmarClick() {
		atualizaEntidade();
		if (validaCampos()) {
			try {
				spBackground.getScene().getRoot().setCursor(Cursor.WAIT);
				desabilitaBotoes().salvar(empresa);
			} finally {
				spBackground.getScene().getRoot().setCursor(null);
				habilitaBotoes();
			}
		}
	}

	@Override
	public void onBtnCancelarClick() {
		limpaCampos();
	}

	@Override
	public void onBtnExcluirClick() {
		if ((empresa.getId() == null) || txtId.getText().isEmpty() || txtId.getText().equalsIgnoreCase("0"))
			Notificacoes.notificacao(AlertType.INFORMATION, Mensagens.AVISO,
					Mensagens.CADASTRO_EXCLUIR + " Nenhum cliente selecionado.");
		else {
			try {
				spBackground.cursorProperty().set(Cursor.WAIT);
				desabilitaBotoes().atualizaEntidade().excluir(empresa);
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

	@FXML
	public void onBtnEnderecoClick() {
		ListaEnderecoController ctn = (ListaEnderecoController) ViewGerenciador
				.loadTela(ListaEnderecoController.getFxmlLocate(), spRoot);
		ctn.initData(txtNomeFantasia.getText(), empresa.getEnderecos());
	}

	@FXML
	public void onBtnContatoClick() {
		ListaContatoController ctn = (ListaContatoController) ViewGerenciador
				.loadTela(ListaContatoController.getFxmlLocate(), spRoot);
		ctn.initData(txtRazaoSocial.getText(), empresa.getContatos());
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

				imgLogo.setImage(new Image(caminhoImagem.toURI().toString()));

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
	public void onBtnExcluirImagemClick() {
		setImagemPadrao();
	}

	public void onTxtIdExit() {
		if (!txtId.getText().isEmpty())
			carregar(pesquisar(new Empresa(Long.valueOf(txtId.getText()))));
		else if (txtId.getText().isEmpty() || txtId.getText().equalsIgnoreCase("0"))
			limpaCampos();
	}

	@Override
	protected <T> void salvar(T entidade) {
		/*
		 * try { Notificacoes.notificacao(AlertType.NONE, Mensagens.CONCLUIDO,
		 * "Cliente salvo com sucesso."); limpaCampos(); } catch (ExcessaoBd e) {
		 * Notificacoes.notificacao(AlertType.ERROR, Mensagens.ERRO, e.getMessage());
		 * LOGGER.log(Level.INFO, "{Erro ao salvar empresa}", e); }
		 */
	}

	@Override
	protected <T> void excluir(T entidade) {
		if ((empresa.getId() == null) || (empresa.getId() == 0) || txtId.getText().isEmpty()
				|| txtId.getText().equalsIgnoreCase("0"))
			Notificacoes.notificacao(AlertType.INFORMATION, Mensagens.AVISO,
					Mensagens.CADASTRO_EXCLUIR + " Nenhuma empresa selecionada.");
		else {
			try {
				empresaService.deletar(empresa.getId());
				Notificacoes.notificacao(AlertType.NONE, Mensagens.CONCLUIDO, "Empresa excluído com sucesso.");
				limpaCampos();
			} catch (ExcessaoBd e) {
				Notificacoes.notificacao(AlertType.ERROR, Mensagens.ERRO, e.getMessage());
				LOGGER.log(Level.INFO, "{Erro ao excluir empresa}", e);
			}
		}

	}

	@Override
	protected <T> T pesquisar(T entidade) {
		/*
		 * try { } catch (ExcessaoBd e) { LOGGER.log(Level.INFO,
		 * "{Erro ao pesquisar empresa}", e); e.printStackTrace(); }
		 */
		return entidade;
	}

	@Override
	public <T> void carregar(T entidade) {
		if (entidade == null)
			limpaCampos();
		else
			atualizaTela((Empresa) entidade);
	}

	@Override
	protected boolean validaCampos() {
		try {
			return ValidaEmpresa.validaEmpresa(empresa);
		} catch (ExcessaoCadastro e) {
			e.printStackTrace();
		}

		try {
			ValidaEmpresa.validaRazaoSocial(empresa.getRazaoSocial());
		} catch (ExcessaoCadastro e) {
			txtRazaoSocial.setUnFocusColor(Color.RED);
		}

		try {
			ValidaEmpresa.validaNomeFantasia(empresa.getNomeFantasia());
		} catch (ExcessaoCadastro e) {
			txtNomeFantasia.setUnFocusColor(Color.RED);
		}

		try {
			ValidaEmpresa.validaCNPJ(empresa.getCnpj());
		} catch (ExcessaoCadastro e) {
			txtCnpj.setUnFocusColor(Color.RED);
		}

		Notificacoes.notificacao(AlertType.INFORMATION, Mensagens.AVISO, Mensagens.CADASTRO_SALVAR);
		return false;
	}

	@Override
	protected void limpaCampos() {
		empresa = new Empresa();
		txtId.setText("0");
		txtNomeFantasia.setText("");
		txtRazaoSocial.setText("");
		txtCnpj.setText("");
		cbSituacao.getSelectionModel().selectFirst();
		setImagemPadrao();
	}

	private CadEmpresaController setImagemPadrao() {
		imagens = null;
		imgLogo.setImage(LogoPadrao);
		return this;
	}

	private CadEmpresaController atualizaTela(Empresa empresa) {
		limpaCampos();

		this.empresa = empresa;

		txtId.setText(empresa.getId().toString());

		if (empresa.getRazaoSocial() != null && !empresa.getRazaoSocial().isEmpty())
			txtRazaoSocial.setText(empresa.getRazaoSocial());

		if (empresa.getNomeFantasia() != null && !empresa.getNomeFantasia().isEmpty())
			txtNomeFantasia.setText(empresa.getNomeFantasia());

		if (empresa.getCnpj() != null && !empresa.getCnpj().isEmpty())
			txtCnpj.setText(empresa.getCnpj());

		cbSituacao.getSelectionModel().select(empresa.getSituacao().ordinal());

		return this;
	}

	private CadEmpresaController desabilitaBotoes() {
		spBackground.setDisable(true);
		btnConfirmar.setDisable(true);
		btnCancelar.setDisable(true);
		btnExcluir.setDisable(true);
		return this;
	}

	private CadEmpresaController habilitaBotoes() {
		spBackground.setDisable(false);
		btnConfirmar.setDisable(false);
		btnCancelar.setDisable(false);
		btnExcluir.setDisable(false);
		return this;
	}

	private CadEmpresaController atualizaEntidade() {
		if (empresa == null)
			empresa = new Empresa();

		if (txtId.getText().isEmpty() || txtId.getText().equalsIgnoreCase("0"))
			empresa.setId(Long.valueOf(0));
		else
			empresa.setId(Long.valueOf(txtId.getText()));

		empresa.setRazaoSocial(txtRazaoSocial.getText());
		empresa.setNomeFantasia(txtNomeFantasia.getText());
		empresa.setCnpj(Utils.removeMascaras(txtCnpj.getText()));
		empresa.setSituacao(cbSituacao.getSelectionModel().getSelectedItem());

		return this;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	private CadEmpresaController configuraExitId() {
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
	public synchronized void inicializa(URL location, ResourceBundle resources) {
		Limitadores.setTextFieldInteger(txtId);

		Validadores.setTextFieldNotEmpty(txtRazaoSocial);
		Validadores.setTextFieldCpnfCnpjExit(txtCnpj);

		Mascaras.cnpjField(txtCnpj);

		TecladoUtils.onEnterConfigureTab(txtNomeFantasia);
		TecladoUtils.onEnterConfigureTab(txtRazaoSocial);
		TecladoUtils.onEnterConfigureTab(txtCnpj);
		TecladoUtils.onEnterConfigureTab(cbSituacao);

		configuraExitId();

		cbSituacao.getItems().addAll(Situacao.values());
		cbSituacao.getSelectionModel().select(Situacao.ATIVO);

		txtId.setText("0");
		empresa = new Empresa();
	}

	public static URL getFxmlLocate() {
		return CadEmpresaController.class.getResource("/cadastro/view/cadastros/CadEmpresa.fxml");
	}

}
