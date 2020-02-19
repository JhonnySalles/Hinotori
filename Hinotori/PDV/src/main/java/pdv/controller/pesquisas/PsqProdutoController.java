package pdv.controller.pesquisas;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.util.Callback;
import pdv.App;
import servidor.dao.services.ProdutoServices;
import servidor.entities.Imagem;
import servidor.entities.Produto;

public class PsqProdutoController implements Initializable {

	private Map<KeyCodeCombination, Runnable> atalhosTecla = new HashMap<>();

	@FXML
	private StackPane spRoot;

	@FXML
	private ScrollPane background;

	@FXML
	private HBox titulo;

	@FXML
	private HBox fundoBotoes;

	@FXML
	private AnchorPane rootProduto;

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

	@FXML
	private JFXButton btnAtualizar;

	@FXML
	private JFXButton btnConfirmar;

	@FXML
	private JFXButton btnCancelar;

	@FXML
	private JFXButton btnVoltar;

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
			// Necessário por um bug na tela ao carregar ela.
			App.getMainController().atualizaTabPane();
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
		App.getMainController().atualizaTabPane();
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
		// Necessário por um bug na tela ao carregar ela.
		App.getMainController().atualizaTabPane();
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

	// Será necessário verificar uma forma de configurar o scene após a exibição,
	// pois é ele que adiciona os atalhos do teclado, porém na construção a scene
	// não existe, somente na exibição.
	public void ativaAtalhos() {
		rootProduto.getScene().getAccelerators().clear();
		rootProduto.getScene().getAccelerators().putAll(atalhosTecla);
	}

	private PsqProdutoController configuraAtalhosTeclado() {
		atalhosTecla.put(new KeyCodeCombination(KeyCode.F2), new Runnable() {
			@FXML
			public void run() {
				btnConfirmar.fire();
			}
		});
		atalhosTecla.put(new KeyCodeCombination(KeyCode.F3), new Runnable() {
			@FXML
			public void run() {
				btnCancelar.fire();
			}
		});
		atalhosTecla.put(new KeyCodeCombination(KeyCode.BACK_SPACE), new Runnable() {
			@FXML
			public void run() {
				btnVoltar.fire();
			}
		});
		atalhosTecla.put(new KeyCodeCombination(KeyCode.BACK_SPACE), new Runnable() {
			@FXML
			public void run() {
				btnVoltar.fire();
			}
		});
		atalhosTecla.put(new KeyCodeCombination(KeyCode.F5), new Runnable() {
			@FXML
			public void run() {
				btnAtualizar.fire();
			}
		});
		return this;
	}

	private PsqProdutoController setProdutoServices(ProdutoServices produtoService) {
		this.produtoService = produtoService;
		return this;
	}

	// Função responsável pela transição de opacidade do fundo do cadastro e dos
	// botões.
	private double initY = -1;
	private final Scale scale = new Scale(1, 1, 0, 0);
	private Transform oldSceneTransform = null;

	private PsqProdutoController configureScroll() {

		fundoBotoes.localToSceneTransformProperty().addListener((o, oldVal, newVal) -> oldSceneTransform = oldVal);
		background.vvalueProperty().addListener((o, oldVal, newVal) -> {
			if (initY == -1) {
				initY = oldSceneTransform.getTy();
			}

			// translation
			double ty = rootProduto.getLocalToSceneTransform().getTy();
			double opacity = Math.abs(ty - initY) / 100;
			opacity = opacity > 1 ? 1 : (opacity < 0) ? 0 : opacity;

			titulo.setOpacity(1 - opacity);
			fundoBotoes.setOpacity(opacity);

			// scale
			scale.setX(map(opacity, 0, 1, 1, 0.75));
			scale.setY(map(opacity, 0, 1, 1, 0.75));
		});
		return this;
	}

	private double map(double val, double min1, double max1, double min2, double max2) {
		return min2 + (max2 - min2) * ((val - min1) / (max1 - min1));
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
		setProdutoServices(new ProdutoServices());

		Limitadores.setTextFieldInteger(txtIdInicial);
		Limitadores.setTextFieldInteger(txtIdFinal);

		TecladoUtils.onEnterConfigureTab(txtDescricao);
		TecladoUtils.onEnterConfigureTab(txtCodigoBarras);
		TecladoUtils.onEnterConfigureTab(txtUnidade);
		TecladoUtils.onEnterConfigureTab(txtMarca);
		TecladoUtils.onEnterConfigureTab(cbProdutoTipo);
		TecladoUtils.onEnterConfigureTab(cbSituacao);

		configuraAtalhosTeclado().configureScroll();
		linkaCelulas();

		cbSituacao.getItems().addAll(Situacao.values());
		cbProdutoTipo.getItems().addAll(TipoProduto.values());
	}
}
