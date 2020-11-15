package cadastro.controller.pesquisas;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import comum.form.DashboardFormPadrao;
import comum.form.PesquisaFormPadrao;
import comum.model.constraints.Limitadores;
import comum.model.constraints.TecladoUtils;
import comum.model.enums.Situacao;
import comum.model.enums.TamanhoImagem;
import comum.model.enums.TipoProduto;
import comum.model.exceptions.ExcessaoBd;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import servidor.dao.services.ProdutoServices;
import servidor.entities.Imagem;
import servidor.entities.Produto;

public class PsqProdutoController extends PesquisaFormPadrao implements Initializable {

	@FXML
	private JFXTextField txtIdInicial;

	@FXML
	private JFXTextField txtIdFinal;

	@FXML
	private JFXTextField txtDescricao;

	@FXML
	private JFXTextField txtCodigoBarras;

	@FXML
	private JFXTextField txtUnidade;

	@FXML
	private JFXTextField txtMarca;

	@FXML
	private JFXDatePicker dtPkCadastroInicial;

	@FXML
	private JFXDatePicker dtPkCadastroFinal;

	@FXML
	private JFXComboBox<TipoProduto> cbProdutoTipo;

	@FXML
	private JFXComboBox<Situacao> cbSituacao;

	@FXML
	private TableView<Produto> tbProdutos;

	@FXML
	private TableColumn<Produto, String> tbClId;

	@FXML
	private TableColumn<Produto, List<Imagem>> tbClImagem;

	@FXML
	private TableColumn<Produto, String> tbClDescricao;

	@FXML
	private TableColumn<Produto, String> tbClCodigoBarras;

	@FXML
	private TableColumn<Produto, String> tbClUnidade;

	@FXML
	private TableColumn<Produto, String> tbClMarca;

	@FXML
	private TableColumn<Produto, Double> tbClPeso;

	@FXML
	private TableColumn<Produto, Double> tbClVolume;

	@FXML
	private TableColumn<Produto, String> tbClObservacao;

	@FXML
	private TableColumn<Produto, String> tbClDataCadastro;

	private List<Produto> produtos;
	private ObservableList<Produto> obsProdutos;
	private FilteredList<Produto> filteredData;
	private ProdutoServices produtoService;

	@FXML
	public void onBtnConfirmarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnConfirmar.fire();
		}
	}

	@FXML
	public void onBtnConfirmarClick() {

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
	public void onBtnAtualizarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnAtualizar.fire();
		}
	}

	@FXML
	public void onBtnAtualizarClick() {
		try {
			spRoot.cursorProperty().set(Cursor.WAIT);
			carregarClientes();
		} catch (ExcessaoBd e) {
			e.printStackTrace();
		} finally {
			spRoot.cursorProperty().set(null);
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
		DashboardFormPadrao.closeTela(spRoot);
		onClose();
	}

	@FXML
	public void onBtnLimparClick() {
		txtIdInicial.setText("0");
		txtIdFinal.setText("0");
		txtDescricao.setText("");
		txtCodigoBarras.setText("");
		txtUnidade.setText("");
		txtMarca.setText("");
		dtPkCadastroInicial.setValue(null);
		dtPkCadastroFinal.setValue(null);
		cbProdutoTipo.getSelectionModel().clearSelection();
		cbSituacao.getSelectionModel().clearSelection();
		if (filteredData != null)
			filteredData.setPredicate(null);
	}

	public PsqProdutoController carregarProdutoss(List<Produto> produtos) {
		this.produtos = produtos;
		return this;
	}

	public PsqProdutoController carregarClientes() throws ExcessaoBd {
		this.produtos = produtoService.pesquisarTodos(TamanhoImagem.PEQUENA);
		obsProdutos = FXCollections.observableArrayList(this.produtos);
		tbProdutos.setItems(obsProdutos);
		tbProdutos.refresh();
		configuraGrid();
		return this;
	}

	private Boolean validaPredicate(Produto obj) {
		if ((txtIdInicial.getText().isEmpty() || obj.getId() >= Long.valueOf(txtIdInicial.getText()))
				&& (txtIdFinal.getText().isEmpty() || obj.getId() <= Long.valueOf(txtIdFinal.getText()))
				&& (txtDescricao.getText().isEmpty()
						|| obj.getDescricao().toString().toLowerCase().contains(txtDescricao.getText().toLowerCase()))
				&& (txtCodigoBarras.getText().isEmpty() || obj.getCodigoBarras().contains(txtCodigoBarras.getText()))
				&& (txtUnidade.getText().isEmpty() || obj.getUnidade().contains(txtUnidade.getText()))
				&& (txtMarca.getText().isEmpty() || obj.getUnidade().contains(txtMarca.getText()))

				&& (dtPkCadastroInicial.getValue() == null || obj.getDataCadastro()
						.after(Timestamp.valueOf(dtPkCadastroInicial.getValue().atStartOfDay())))
				&& (dtPkCadastroFinal.getValue() == null
						|| obj.getDataCadastro().after(Timestamp.valueOf(dtPkCadastroFinal.getValue().atStartOfDay())))

				&& (cbProdutoTipo.getSelectionModel().getSelectedIndex() < 0
						|| obj.getTipoProduto() == cbProdutoTipo.getSelectionModel().getSelectedItem())
				&& (cbSituacao.getSelectionModel().getSelectedIndex() < 0
						|| obj.getSituacao() == cbSituacao.getSelectionModel().getSelectedItem()))
			return true;
		else
			return false;

	}

	private PsqProdutoController configuraGrid() {
		filteredData = new FilteredList<>(obsProdutos, p -> true);

		txtIdInicial.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		txtIdFinal.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		txtDescricao.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		txtCodigoBarras.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		txtUnidade.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		txtMarca.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		dtPkCadastroInicial.valueProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		dtPkCadastroFinal.valueProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		cbProdutoTipo.setOnAction(filtrar -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		cbSituacao.setOnAction(filtrar -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		SortedList<Produto> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tbProdutos.comparatorProperty());
		tbProdutos.setItems(sortedData);

		return this;
	}

	private PsqProdutoController setProdutoServices(ProdutoServices produtoService) {
		this.produtoService = produtoService;
		return this;
	}

	private PsqProdutoController linkaCelulas() {
		tbClId.setCellValueFactory(new PropertyValueFactory<>("id"));

		tbClImagem.setCellValueFactory(new PropertyValueFactory<>("imagens"));
		tbClImagem.setCellFactory(new Callback<TableColumn<Produto, List<Imagem>>, TableCell<Produto, List<Imagem>>>() {
			@Override
			public TableCell<Produto, List<Imagem>> call(TableColumn<Produto, List<Imagem>> param) {
				TableCell<Produto, List<Imagem>> cell = new TableCell<Produto, List<Imagem>>() {
					final ImageView img = new ImageView();

					@Override
					public void updateItem(List<Imagem> item, boolean empty) {
						if (item != null && item.size() > 0) {
							img.setImage(new Image(new ByteArrayInputStream(item.get(0).getImagem())));
							img.setFitHeight(25);
							img.setFitWidth(25);
							setGraphic(img);
						}
					}
				};
				return cell;
			}
		});
		tbClImagem.setPrefWidth(60);

		tbClDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		tbClCodigoBarras.setCellValueFactory(new PropertyValueFactory<>("codigoBarras"));
		tbClUnidade.setCellValueFactory(new PropertyValueFactory<>("unidade"));
		tbClMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
		tbClPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
		tbClVolume.setCellValueFactory(new PropertyValueFactory<>("volume"));
		tbClObservacao.setCellValueFactory(new PropertyValueFactory<>("observacao"));

		tbClDataCadastro.setCellValueFactory(data -> {
			SimpleStringProperty property = new SimpleStringProperty();
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			property.setValue(dateFormat.format(data.getValue().getDataCadastro()));
			return property;
		});

		return this;
	}

	@Override
	public synchronized void initialize(URL arg0, ResourceBundle arg1) {
		inicializaHeranca();
		setProdutoServices(new ProdutoServices());

		Limitadores.setTextFieldInteger(txtIdInicial);
		Limitadores.setTextFieldInteger(txtIdFinal);

		TecladoUtils.onEnterConfigureTab(txtDescricao);
		TecladoUtils.onEnterConfigureTab(txtCodigoBarras);
		TecladoUtils.onEnterConfigureTab(txtUnidade);
		TecladoUtils.onEnterConfigureTab(txtMarca);
		TecladoUtils.onEnterConfigureTab(cbProdutoTipo);
		TecladoUtils.onEnterConfigureTab(cbSituacao);

		linkaCelulas();

		cbSituacao.getItems().addAll(Situacao.values());
		cbProdutoTipo.getItems().addAll(TipoProduto.values());
	}

	public static URL getFxmlLocate() {
		return PsqProdutoController.class.getResource("/cadastro/view/pesquisas/PsqProduto.fxml");
	}
}
