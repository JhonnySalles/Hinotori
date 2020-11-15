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

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import cadastro.controller.frame.PesquisaGenericaController;
import cadastro.controller.pesquisas.PsqProdutoController;
import comum.form.CadastroFormPadrao;
import comum.form.DashboardFormPadrao;
import comum.model.constraints.Limitadores;
import comum.model.constraints.Validadores;
import comum.model.enums.Situacao;
import comum.model.enums.TamanhoImagem;
import comum.model.enums.TipoProduto;
import comum.model.exceptions.ExcessaoBd;
import comum.model.notification.Notificacoes;
import comum.model.utils.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import servidor.dao.services.ProdutoServices;
import servidor.entities.Imagem;
import servidor.entities.Produto;

public class CadProdutoController extends CadastroFormPadrao implements Initializable {

	final static Image ImagemPadrao = new Image(CadUsuarioController.class
			.getResourceAsStream("/cadastro/resources/imagens/white/geral/icoProdutoImage_256.png"));

	@FXML
	private JFXTextField txtId;

	@FXML
	private JFXTextField txtDescricao;

	@FXML
	private JFXTextField txtCodigoBarras;

	@FXML
	private JFXTextField txtUnidade;

	@FXML
	private JFXTextField txtMarca;

	@FXML
	private Spinner<Double> spnPeso;

	@FXML
	private Spinner<Double> spnVolume;

	@FXML
	private JFXTextArea txtAreaObservacao;

	@FXML
	private JFXComboBox<Situacao> cbSituacao;

	@FXML
	private JFXComboBox<TipoProduto> cbTipoProduto;

	// O formato do arquivo incluido é um anchorpane "conforme criei a tela",
	// o nome aqui será o mesmo que no id do fxml incluido.
	@FXML
	private AnchorPane frameNCM;

	// Para utilizar o controlador do frame incluido, basta colocar a descrição
	// "Controller" na frente do id do fxml incluido conforme abaixo.
	@FXML
	private PesquisaGenericaController frameNCMController;

	@FXML
	private ImageView imgProduto;

	@FXML
	private JFXButton btnExcluirImagem;

	@FXML
	private JFXButton btnProcurarImagem;

	private Set<Imagem> imagens;
	private Produto produto;
	private ProdutoServices produtoService;

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
				spBackground.getScene().getRoot().setCursor(Cursor.WAIT);
				desabilitaBotoes().atualizaEntidade().salvar(produto);
			} finally {
				spBackground.getScene().getRoot().setCursor(null);
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

	}

	@FXML
	public void onBtnExcluirEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnExcluir.fire();
		}
	}

	@FXML
	public void onBtnExcluirClick() {
		if ((produto.getId() == null) || txtId.getText().isEmpty() || txtId.getText().equalsIgnoreCase("0"))
			Notificacoes.notificacao(AlertType.INFORMATION, "Aviso",
					"Não foi possivel realizar a exclusão, nenhum cliente selecionado.");
		else {
			try {
				spBackground.cursorProperty().set(Cursor.WAIT);
				desabilitaBotoes().atualizaEntidade().excluir(produto);
			} finally {
				spBackground.cursorProperty().set(null);
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
		PsqProdutoController ctn = (PsqProdutoController) DashboardFormPadrao
				.loadTela(PsqProdutoController.getFxmlLocate(), spRoot);
		ctn.setAtivaBotoesPesquisa(true, true);
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

				imgProduto.setImage(new Image(caminhoImagem.toURI().toString()));

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

	public void onTxtIdExit() {

		if (!txtId.getText().isEmpty()) {
			try {
				carregarProduto(produtoService.pesquisar(Long.valueOf(txtId.getText()), TamanhoImagem.TODOS));
			} catch (ExcessaoBd e) {
				e.printStackTrace();
			}
		} else {
			if (txtId.getText().isEmpty())
				txtId.setText("0");
		}

	}

	private CadProdutoController desabilitaBotoes() {
		spBackground.setDisable(true);
		btnConfirmar.setDisable(true);
		btnCancelar.setDisable(true);
		btnExcluir.setDisable(true);
		return this;
	}

	private CadProdutoController habilitaBotoes() {
		spBackground.setDisable(false);
		btnConfirmar.setDisable(false);
		btnCancelar.setDisable(false);
		btnExcluir.setDisable(false);
		return this;
	}

	private Boolean validaCampos() {
		Boolean valida = true;

		if (txtDescricao.getText().isEmpty()) {
			txtDescricao.setUnFocusColor(Color.RED);
			valida = false;
		}

		return valida;
	}

	private CadProdutoController limpaCampos() {
		setImagemPadrao();

		produto = new Produto();
		txtId.setText("0");
		txtDescricao.setText("");
		txtCodigoBarras.setText("");
		txtUnidade.setText("");
		txtMarca.setText("");
		spnPeso.getEditor().setText("0");
		spnVolume.getEditor().setText("0");
		txtAreaObservacao.setText("");
		cbSituacao.getSelectionModel().selectFirst();
		cbTipoProduto.getSelectionModel().selectFirst();
		frameNCMController.limpaCampos();
		return this;
	}

	private CadProdutoController setImagemPadrao() {
		imagens = null;
		imgProduto.setImage(ImagemPadrao);
		return this;
	}

	private CadProdutoController atualizaEntidade() {
		if (produto == null)
			produto = new Produto();

		if (txtId.getText().isEmpty() || txtId.getText().equalsIgnoreCase("0"))
			produto.setId(Long.valueOf(0));
		else
			produto.setId(Long.valueOf(txtId.getText()));

		produto.setDescricao(txtDescricao.getText());
		produto.setCodigoBarras(txtCodigoBarras.getText());
		produto.setMarca(txtUnidade.getText());
		produto.setUnidade(txtUnidade.getText());
		produto.setPeso(spnPeso.getValue());
		produto.setVolume(spnVolume.getValue());
		produto.setObservacao(txtAreaObservacao.getText());

		// if (frameNCMController.getId() != null)
		// produto.setIdNcm(String.valueOf(frameNCMController.getId()));

		produto.setTipoProduto(cbTipoProduto.getSelectionModel().getSelectedItem());
		produto.setSituacao(cbSituacao.getSelectionModel().getSelectedItem());
		produto.setImagens(imagens);

		return this;
	}

	private CadProdutoController atualizaTela(Produto produto) {
		limpaCampos();

		this.produto = produto;

		txtId.setText(produto.getId().toString());

		if (produto.getDescricao() != null && !produto.getDescricao().isEmpty())
			txtDescricao.setText(produto.getDescricao());

		if (produto.getCodigoBarras() != null && !produto.getCodigoBarras().isEmpty())
			txtCodigoBarras.setText(produto.getCodigoBarras());

		if (produto.getUnidade() != null && !produto.getUnidade().isEmpty())
			txtUnidade.setText(produto.getUnidade());

		if (produto.getMarca() != null && !produto.getMarca().isEmpty())
			txtMarca.setText(produto.getMarca());

		if (produto.getPeso() != null)
			spnPeso.getEditor().setText(produto.getPeso().toString());

		if (produto.getVolume() != null)
			spnVolume.getEditor().setText(produto.getVolume().toString());

		if (produto.getObservacao() != null && !produto.getObservacao().isEmpty())
			txtAreaObservacao.setText(produto.getObservacao());

		cbSituacao.getSelectionModel().select(produto.getSituacao().ordinal());
		cbTipoProduto.getSelectionModel().select(produto.getTipoProduto().ordinal());

		if (frameNCMController.getId() != null)
			frameNCMController.locateId(frameNCMController.getId());

		if (produto.getImagens() != null && produto.getImagens().size() > 0) {
			imagens = produto.getImagens();
			imgProduto
					.setImage(new Image(new ByteArrayInputStream(produto.getImagens().iterator().next().getImagem())));
		} else {
			setImagemPadrao();
		}
		
		return this;
	}

	public CadProdutoController carregarProduto(Produto produto) {
		this.produto = produto;
		if (produto == null)
			limpaCampos();
		else
			atualizaTela(produto);
		return this;
	}

	private void salvar(Produto produto) {
		if (produtoService == null)
			setProdutoServices(new ProdutoServices());

		try {
			produtoService.salvar(produto);
			Notificacoes.notificacao(AlertType.NONE, "Concluído", "Produto salvo com sucesso.");
			limpaCampos();
		} catch (ExcessaoBd e) {
			Notificacoes.notificacao(AlertType.ERROR, "Erro", e.getMessage());
		}

	}

	private void excluir(Produto produto) {
		if (produtoService == null)
			setProdutoServices(new ProdutoServices());

		try {
			produtoService.deletar(produto.getId());
			Notificacoes.notificacao(AlertType.NONE, "Concluído", "Produto excluído com sucesso.");
			limpaCampos();
		} catch (ExcessaoBd e) {
			Notificacoes.notificacao(AlertType.ERROR, "Erro", e.getMessage());
		}
	}

	private CadProdutoController setSqlFrame() {

		frameNCMController.setPesquisa("NCM", "Descricao", "NCM, CONCAT(NCM, ' - ', Descricao) AS Descricao", "ncm", "",
				"", "ORDER BY NCM");

		frameNCMController.txtFraPesquisa.setPromptText("Pesquisa de ncm");
		return this;
	}

	private void setProdutoServices(ProdutoServices produtoService) {
		this.produtoService = produtoService;
	}

	private CadProdutoController configuraExitId() {
		txtId.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {

				if (!newPropertyValue) {
					if (!txtId.getText().equalsIgnoreCase("0"))
						onTxtIdExit();
				}
			}
		});

		return this;
	}

	@Override
	public synchronized void initialize(URL location, ResourceBundle resources) {
		inicializaHeranca();
		setProdutoServices(new ProdutoServices());

		Limitadores.setTextFieldInteger(txtId);
		Validadores.setTextFieldNotEmpty(txtDescricao);

		cbSituacao.getItems().addAll(Situacao.values());
		cbTipoProduto.getItems().addAll(TipoProduto.values());
		cbSituacao.getSelectionModel().select(Situacao.ATIVO);
		cbTipoProduto.getSelectionModel().select(TipoProduto.PRODUTOFINAL);

		txtId.setText("0");
		setSqlFrame().configuraExitId();
	}

	public static URL getFxmlLocate() {
		return CadContatoController.class.getResource("/cadastro/view/cadastros/CadProduto.fxml");
	}

}
