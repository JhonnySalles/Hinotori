package cadastro.controller.cadastros;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import cadastro.controller.frame.PesquisaGenericaController;
import cadastro.utils.CadastroUtils;
import comum.form.CadastroFormPadrao;
import comum.model.constraints.Limitadores;
import comum.model.constraints.Validadores;
import comum.model.enums.Situacao;
import comum.model.enums.TipoProduto;
import comum.model.exceptions.ExcessaoCadastro;
import comum.model.messages.Mensagens;
import comum.model.notification.Notificacoes;
import comum.model.utils.Utils;
import comum.model.utils.ViewGerenciador;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import servidor.entities.Imagem;
import servidor.entities.Produto;
import servidor.entities.ProdutoImagem;
import servidor.validations.ValidaProduto;

public class CadProdutoController extends CadastroFormPadrao {

	private final static Logger LOGGER = Logger.getLogger(CadProdutoController.class.getName());

	final static Image ImagemPadrao = new Image(
			CadUsuarioController.class.getResourceAsStream("/cadastro/imagens/white/geral/icoProdutoImage_256.png"));

	@FXML
	private JFXTabPane tpContainer;

	@FXML
	private Tab tabProduto;

	@FXML
	private Tab tabUnidade;

	// -------------------------------- Produto -------------------------------- //

	@FXML
	private JFXTextField txtId;

	@FXML
	private JFXTextField txtDescricao;

	@FXML
	private JFXTextField txtGrupo;

	@FXML
	private JFXTextField txtSubGrupo;

	@FXML
	private JFXTextField txtNCM;

	@FXML
	private JFXComboBox<Situacao> cbCategoria;

	@FXML
	private JFXComboBox<Situacao> cbOrigem;

	@FXML
	private Spinner<Double> spnPesoLiquido;

	@FXML
	private Spinner<Double> spnPesoBruto;

	@FXML
	private JFXTextArea txtAreaDescricaoComplementar;

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

	// -------------------------------- Unidade -------------------------------- //

	@FXML
	private JFXTextField txtEmpresa;

	@FXML
	private JFXTextField txtUnidade;

	@FXML
	private JFXTextField txtCodigoBarras;

	@FXML
	private JFXCheckBox cbUnidadePadrao;

	@FXML
	private JFXCheckBox cbEmbalagemPadrao;

	@FXML
	private Spinner<Double> spnQuantidade;

	@FXML
	private Spinner<Double> spnFatorConversao;

	@FXML
	private JFXButton btnAdicionar;

	@FXML
	private JFXButton btnRemover;

	@FXML
	private TableView<Produto> tbUnidade;

	@FXML
	private TableColumn<Produto, String> tbClFilial;

	@FXML
	private TableColumn<Produto, String> tbClUnidade;

	@FXML
	private TableColumn<Produto, String> tbClCodigoBarras;

	@FXML
	private TableColumn<Produto, String> tbClQuantidade;

	@FXML
	private TableColumn<Produto, String> tbClFatorConversao;

	@FXML
	private TableColumn<Produto, String> tbClUnidadePadrao;

	@FXML
	private TableColumn<Produto, String> tbClEmbalagemPadrao;

	// -------------------------------- -------------------------------- //

	private Set<ProdutoImagem> imagens;
	private Produto produto;

	@Override
	public void onBtnConfirmarClick() {
		atualizaEntidade();
		if (validaCampos()) {
			try {
				spBackground.getScene().getRoot().setCursor(Cursor.WAIT);
				desabilitaBotoes().salvar(produto);
			} finally {
				spBackground.getScene().getRoot().setCursor(null);
				habilitaBotoes();
			}
		}
	}

	@Override
	public void onBtnCancelarClick() {

	}

	@Override
	public void onBtnExcluirClick() {
		if ((produto.getId() == null) || txtId.getText().isEmpty() || txtId.getText().equalsIgnoreCase("0"))
			Notificacoes.notificacao(AlertType.INFORMATION, Mensagens.AVISO,
					Mensagens.CADASTRO_EXCLUIR + " Nenhum produto selecionado.");
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
					imagens = new HashSet<ProdutoImagem>();

				imgProduto.setImage(new Image(caminhoImagem.toURI().toString()));
				imagens.addAll(CadastroUtils.processaImagens(caminhoImagem).stream()
						.map(imagem -> ProdutoImagem.toProdutoImagem(imagem)).collect(Collectors.toList()));

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
			carregar(pesquisar(new Produto(Long.valueOf(txtId.getText()))));
		else if (txtId.getText().isEmpty())
			txtId.setText("0");
	}

	@Override
	protected <T> void salvar(T entidade) {
		/*
		 * try {
		 * 
		 * Notificacoes.notificacao(AlertType.NONE, Mensagens.CONCLUIDO,
		 * "Produto salvo com sucesso."); limpaCampos(); } catch (ExcessaoBd e) {
		 * Notificacoes.notificacao(AlertType.ERROR, Mensagens.ERRO, e.getMessage());
		 * LOGGER.log(Level.INFO, "{Erro ao salvar o produto}", e); }
		 */

	}

	@Override
	protected <T> void excluir(T entidade) {
		/*
		 * try { Notificacoes.notificacao(AlertType.NONE, Mensagens.CONCLUIDO,
		 * "Produto excluído com sucesso."); limpaCampos(); } catch (ExcessaoBd e) {
		 * Notificacoes.notificacao(AlertType.ERROR, Mensagens.ERRO, e.getMessage());
		 * LOGGER.log(Level.INFO, "{Erro ao excluir o produto}", e); }
		 */

	}

	@Override
	protected <T> T pesquisar(T entidade) {
		/*
		 * try {
		 * 
		 * } catch (ExcessaoBd e) { e.printStackTrace(); LOGGER.log(Level.INFO,
		 * "{Erro ao carregar o produto}", e); }
		 */
		return null;
	}

	@Override
	public <T> void carregar(T entidade) {
		if (entidade == null)
			limpaCampos();
		else
			atualizaTela((Produto) entidade);

	}

	@Override
	protected boolean validaCampos() {

		try {
			return ValidaProduto.validaProduto(produto);
		} catch (ExcessaoCadastro e) {
			e.printStackTrace();
		}

		txtDescricao.validate();

		try {
			ValidaProduto.validaCodigoBarras(produto.getCodigoBarras());
		} catch (ExcessaoCadastro e) {
			txtCodigoBarras.setUnFocusColor(Color.RED);
		}

		Notificacoes.notificacao(AlertType.INFORMATION, Mensagens.AVISO, Mensagens.CADASTRO_SALVAR);
		return false;
	}

	@Override
	protected void limpaCampos() {
		produto = new Produto();

		// ----------------- Produto ----------------- //
		txtId.setText("0");
		txtDescricao.setText("");
		txtCodigoBarras.setText("");
		txtUnidade.setText("");
		txtGrupo.setText("");
		txtSubGrupo.setText("");
		txtNCM.setText("");
		spnPesoLiquido.getEditor().setText("0");
		spnPesoBruto.getEditor().setText("0");
		txtAreaDescricaoComplementar.setText("");
		cbSituacao.getSelectionModel().selectFirst();
		cbTipoProduto.getSelectionModel().selectFirst();

		// ----------------- Unidade ----------------- //
		txtEmpresa.setText("");
		txtUnidade.setText("");
		txtCodigoBarras.setText("");
		cbUnidadePadrao.setSelected(true);
		cbEmbalagemPadrao.setSelected(false);
		spnQuantidade.getEditor().setText("0");
		spnFatorConversao.getEditor().setText("0");

		// ----------------- ----------------- //
		setImagemPadrao();
	}

	private CadProdutoController setImagemPadrao() {
		imagens = null;
		imgProduto.setImage(ImagemPadrao);
		return this;
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

	@Override
	public CadProdutoController atualizaEntidade() {
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
		/*
		 * produto.setPeso(spnPeso.getValue()); produto.setVolume(spnVolume.getValue());
		 * produto.setObservacao(txtAreaObservacao.getText());
		 */

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

		/*
		 * if (produto.getMarca() != null && !produto.getMarca().isEmpty())
		 * txtMarca.setText(produto.getMarca());
		 * 
		 * if (produto.getPeso() != null)
		 * spnPeso.getEditor().setText(produto.getPeso().toString());
		 * 
		 * if (produto.getVolume() != null)
		 * spnVolume.getEditor().setText(produto.getVolume().toString());
		 * 
		 * if (produto.getObservacao() != null && !produto.getObservacao().isEmpty())
		 * txtAreaObservacao.setText(produto.getObservacao());
		 */

		cbSituacao.getSelectionModel().select(produto.getSituacao().ordinal());
		cbTipoProduto.getSelectionModel().select(produto.getTipoProduto().ordinal());

		if (frameNCMController.getId() != null)
			frameNCMController.locateId(frameNCMController.getId());

		if (produto.getImagens() != null && produto.getImagens().size() > 0) {
			imagens = produto.getImagens();
			imgProduto
					.setImage(new Image(new ByteArrayInputStream(produto.getImagens().iterator().next().getImagem())));
		} else
			setImagemPadrao();

		return this;
	}

	private CadProdutoController setSqlFrame() {

		frameNCMController.setPesquisa("NCM", "Descricao", "NCM, CONCAT(NCM, ' - ', Descricao) AS Descricao", "ncm", "",
				"", "ORDER BY NCM");

		frameNCMController.txtFraPesquisa.setPromptText("Pesquisa de ncm");
		return this;
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
	public synchronized void inicializa(URL location, ResourceBundle resources) {
		Limitadores.setTextFieldInteger(txtId);
		Validadores.setTextFieldNotEmpty(txtDescricao);

		cbSituacao.getItems().addAll(Situacao.values());
		cbTipoProduto.getItems().addAll(TipoProduto.values());
		cbSituacao.getSelectionModel().select(Situacao.ATIVO);
		cbTipoProduto.getSelectionModel().select(TipoProduto.PRODUTOFINAL);

		txtId.setText("0");
		configuraExitId();
	}

	public static URL getFxmlLocate() {
		return DialogCadContatoController.class.getResource("/cadastro/view/cadastros/CadProduto.fxml");
	}

}
