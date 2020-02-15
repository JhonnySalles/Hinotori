package administrador.controller.cadastros;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import administrador.App;
import administrador.controller.DashboardController;
import administrador.model.dao.services.EmpresaServices;
import administrador.model.entities.Empresa;
import administrador.model.entities.Imagem;
import comum.model.constraints.Limitadores;
import comum.model.constraints.TecladoUtils;
import comum.model.constraints.Validadores;
import comum.model.enums.Notificacao;
import comum.model.enums.Padrao;
import comum.model.enums.Situacao;
import comum.model.enums.TamanhoImagem;
import comum.model.exceptions.ExcessaoBd;
import comum.model.mask.Mascaras;
import comum.model.notification.Notificacoes;
import comum.model.utils.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class CadEmpresaController implements Initializable {

	final static Image LogoPadrao = new Image(CadEmpresaController.class
			.getResourceAsStream("/administrador/resources/imagens/icon/icoPedidosVenda_1000.png"));
	
	@FXML
	private ScrollPane background;

	@FXML
	private StackPane spTelas;

	@FXML
	private AnchorPane rootEmpresa;

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

	@FXML
	private JFXButton btnPesquisar;

	@FXML
	private JFXButton btnConfirmar;

	@FXML
	private JFXButton btnCancelar;

	@FXML
	private JFXButton btnExcluir;

	@FXML
	private JFXButton btnVoltar;

	private EmpresaServices empresaService;
	private Empresa empresa;
	private String id;
	private List<Imagem> imagens;

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
				background.getScene().getRoot().setCursor(Cursor.WAIT);
				desabilitaBotoes().atualizaEntidade().salvar(empresa);
			} finally {
				background.getScene().getRoot().setCursor(null);
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
		if ((empresa.getId() == null) || txtId.getText().isEmpty() || txtId.getText().equalsIgnoreCase("0"))
			Notificacoes.notificacao(Notificacao.AVISO, "Aviso",
					"Não foi possivel realizar a exclusão, nenhum cliente selecionado.");
		else {
			try {
				background.cursorProperty().set(Cursor.WAIT);
				desabilitaBotoes().atualizaEntidade().excluir(empresa);
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
	public void onBtnEnderecoEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnEndereco.fire();
		}
	}

	@FXML
	public void onBtnEnderecoClick() {
		CadEnderecoDadosController ctn = (CadEnderecoDadosController) DashboardController
				.loadView("/pdv/view/cadastros/CadEnderecoDados.fxml", spTelas);
		ctn.initData(txtNomeFantasia.getText(), empresa.getEnderecos(), spTelas);
	}

	@FXML
	public void onBtnContatoEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnContato.fire();
		}
	}

	@FXML
	public void onBtnContatoClick() {
		CadContatoDadosController ctn = (CadContatoDadosController) DashboardController
				.loadView("/pdv/view/cadastros/CadContatoDados.fxml", spTelas);
		ctn.initData(txtRazaoSocial.getText(), empresa.getContatos(), spTelas);
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

				imgLogo.setImage(new Image(caminhoImagem.toURI().toString()));

				if (imagens == null)
					imagens = new ArrayList<Imagem>();

				BufferedImage bImage = ImageIO.read(caminhoImagem);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ImageIO.write(bImage, imagemExtenssao, bos);

				imagens.add(
						new Imagem(imagemNome, imagemExtenssao, bos.toByteArray(), Padrao.NAO, TamanhoImagem.ORIGINAL));

				BufferedImage bImg100 = Utils.resizeImage(bImage, 100, 100);
				ImageIO.write(bImg100, imagemExtenssao, bos);
				imagens.add(
						new Imagem(imagemNome, imagemExtenssao, bos.toByteArray(), Padrao.NAO, TamanhoImagem.PEQUENA));

				BufferedImage bImg600 = Utils.resizeImage(bImage, 600, 600);
				ImageIO.write(bImg600, imagemExtenssao, bos);
				imagens.add(
						new Imagem(imagemNome, imagemExtenssao, bos.toByteArray(), Padrao.NAO, TamanhoImagem.MEDIA));

			} catch (IOException e) {
				e.printStackTrace();
				Notificacoes.notificacao(Notificacao.ERRO, "Erro", "Não foi possível carregar a imagem.");
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
		setImagemPadrao();
	}

	public void onTxtIdExit() {
		if (!txtId.getText().isEmpty()) {
			try {
				carregaEmpresa(empresaService.pesquisar(Long.valueOf(txtId.getText())));
			} catch (ExcessaoBd e) {
				e.printStackTrace();
			}
		} else {
			if (txtId.getText().isEmpty() || txtId.getText().equalsIgnoreCase("0"))
				limpaCampos();
		}
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public CadEmpresaController carregaEmpresa(Empresa empresa) {
		this.empresa = empresa;
		if (empresa == null)
			limpaCampos();
		else
			atualizaTela(empresa);
		return this;
	}

	private void salvar(Empresa empresa) {
		if (empresaService == null)
			setEmpresaServices(new EmpresaServices());

		try {
			empresaService.salvar(empresa);
			Notificacoes.notificacao(Notificacao.SUCESSO, "Concluído", "Cliente salvo com sucesso.");
			limpaCampos();
		} catch (ExcessaoBd e) {
			Notificacoes.notificacao(Notificacao.ERRO, "Erro", e.getMessage());
		}

	}

	private void excluir(Empresa empresa) {
		if (empresaService == null)
			setEmpresaServices(new EmpresaServices());

		try {
			empresaService.deletar(empresa.getId());
			Notificacoes.notificacao(Notificacao.SUCESSO, "Concluído", "Cliente excluído com sucesso.");
			limpaCampos();
		} catch (ExcessaoBd e) {
			Notificacoes.notificacao(Notificacao.ERRO, "Erro", e.getMessage());
		}
	}

	private Boolean validaCampos() {
		Boolean valida = true;

		if (txtRazaoSocial.getText().isEmpty()) {
			txtRazaoSocial.setUnFocusColor(Color.RED);
			valida = false;
		}

		return valida;
	}

	private CadEmpresaController limpaCampos() {
		empresa = new Empresa();
		txtId.setText("0");
		txtNomeFantasia.setText("");
		txtRazaoSocial.setText("");
		txtCnpj.setText("");
		cbSituacao.getSelectionModel().selectFirst();
		setImagemPadrao();
		return this;
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

		// Necessário por um bug na tela ao carregar ela.
		App.getMainController().atualizaTabPane();
		return this;
	}

	private CadEmpresaController desabilitaBotoes() {
		background.setDisable(true);
		btnConfirmar.setDisable(true);
		btnCancelar.setDisable(true);
		btnExcluir.setDisable(true);
		return this;
	}

	private CadEmpresaController habilitaBotoes() {
		background.setDisable(false);
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

	private CadEmpresaController setEmpresaServices(EmpresaServices empresaService) {
		this.empresaService = empresaService;
		return this;
	}

	@Override
	public synchronized void initialize(URL location, ResourceBundle resources) {
		setEmpresaServices(new EmpresaServices());

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

}
