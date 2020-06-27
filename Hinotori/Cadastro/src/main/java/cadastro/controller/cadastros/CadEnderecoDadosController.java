package cadastro.controller.cadastros;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.Set;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;

import comum.form.DashboardFormPadrao;
import comum.model.animation.TelaAnimation;
import comum.model.enums.Situacao;
import comum.model.notification.Notificacoes;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import servidor.entities.Endereco;

public class CadEnderecoDadosController implements Initializable {

	/*
	 * Referencia para o controlador pai, onde é utilizado para realizar o refresh
	 * na tela
	 */
	private DashboardFormPadrao dashBoard;

	@FXML
	private StackPane root;

	@FXML
	private AnchorPane rootPane;

	@FXML
	private TableView<Endereco> tbEnderecos;

	@FXML
	private TableColumn<Endereco, String> tbClCidade;

	@FXML
	private TableColumn<Endereco, String> tbClBairro;

	@FXML
	private TableColumn<Endereco, String> tbClEndereco;

	@FXML
	private TableColumn<Endereco, String> tbClNumero;

	@FXML
	private TableColumn<Endereco, String> tbClCep;

	@FXML
	private TableColumn<Endereco, Boolean> tbClPadrao;

	@FXML
	private JFXTextField txtTitulo;

	@FXML
	private JFXButton btnAdicionar;

	@FXML
	private JFXButton btnRemover;

	@FXML
	private JFXButton btnVoltar;

	private AnchorPane cadastroEndereco;
	private CadEnderecoController controller;

	private ObservableList<Endereco> obsEnderecos;
	private Set<Endereco> enderecos;
	private StackPane spRoot;
	private ToggleGroup tgGroup;

	final PseudoClass excluido = PseudoClass.getPseudoClass("excluido");

	@FXML
	public void onBtnVoltarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnVoltar.fire();
		}
	}

	@FXML
	public void onBtnVoltarClick() {
		new TelaAnimation().fecharPane(spRoot);
	}

	@FXML
	public void onBtnAdicionarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnAdicionar.fire();
		}
	}

	@FXML
	public void onBtnAdicionarClick() {
		adicionarEndereco();
	}

	@FXML
	public void onBtnRemoverEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnRemover.fire();
		}
	}

	@FXML
	public void onBtnRemoverClick() {
		if (enderecos.isEmpty() || tbEnderecos.getSelectionModel().isEmpty())
			if (enderecos.isEmpty())
				Notificacoes.notificacao(AlertType.INFORMATION, "Não foi possível apagar item",
						"Não existe nenhum endereço cadastrado.");
			else
				Notificacoes.notificacao(AlertType.INFORMATION, "Não foi possível apagar item",
						"Não foi possível apagar o endereço, nenhum item selecionado.");
		else
			tbEnderecos.getSelectionModel().getSelectedItem().setSituacao(Situacao.EXCLUIDO);
	}

	private void adicionarEndereco() {
		abreTelaEndereco(null);
	}

	private void abreTelaEndereco(Endereco endereco) {
		BoxBlur blur = new BoxBlur(3, 3, 3);

		controller.setEnderecos(enderecos);
		JFXDialog dialog = new JFXDialog(root, cadastroEndereco, JFXDialog.DialogTransition.CENTER);

		rootPane.setDisable(true);
		dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
			rootPane.setEffect(null);
			rootPane.setDisable(false);
			carregaGrid();
		});

		controller.btnVoltar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent) -> {
			dialog.close();
		});

		rootPane.setEffect(blur);

		if (endereco != null)
			controller.carregaEndereco(endereco);

		dialog.show();
	}

	private void carregaGrid() {
		if (enderecos == null)
			return;

		obsEnderecos = FXCollections.observableArrayList(this.enderecos);
		tbEnderecos.setItems(obsEnderecos);
		tbEnderecos.refresh();
	}

	private CadEnderecoDadosController linkaCelulas() {
		tbClCidade.setCellValueFactory(new Callback<CellDataFeatures<Endereco, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Endereco, String> p) {
				if (p.getValue() != null && p.getValue().getBairro() != null
						&& p.getValue().getBairro().getCidade() != null) {
					return new SimpleStringProperty(p.getValue().getBairro().getCidade().getNome() + "/"
							+ p.getValue().getBairro().getCidade().getEstado().getSigla());
				} else {
					return new SimpleStringProperty("");
				}
			}
		});

		tbClBairro.setCellValueFactory(new Callback<CellDataFeatures<Endereco, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Endereco, String> p) {
				// p.getValue() returns the PersonType instance for a particular TableView row
				if (p.getValue() != null && p.getValue().getBairro() != null) {
					return new SimpleStringProperty(p.getValue().getBairro().getNome());
				} else {
					return new SimpleStringProperty("");
				}
			}
		});

		tbClEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
		tbClNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
		tbClCep.setCellValueFactory(new PropertyValueFactory<>("cep"));

		// Fará o controle dos radios button para que apenas um fique marcado
		tgGroup = new ToggleGroup();

		tbClPadrao.setCellValueFactory(new PropertyValueFactory<>("padrao"));
		tbClPadrao.setCellFactory(param -> new TableCell<>() {
			JFXRadioButton indicator = new JFXRadioButton();
			{
				indicator.selectedColorProperty().setValue(Color.BLUE);
				setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
				tgGroup.getToggles().add(indicator);

				indicator.selectedProperty().addListener((obs, wasActive, isNowActive) -> {
					if (getIndex() > -1)
						getTableView().getItems().get(getIndex()).setPadrao(isNowActive);
				});
			}

			@Override
			protected void updateItem(Boolean isPadrao, boolean empty) {
				super.updateItem(isPadrao, empty);

				if (empty || isPadrao == null) {
					setGraphic(null);
				} else {
					indicator.setSelected(isPadrao);
					setGraphic(indicator);
				}
			}
		});

		tbEnderecos.setRowFactory(tv -> {
			TableRow<Endereco> row = new TableRow<>() {
				@Override
				public void updateItem(Endereco item, boolean empty) {
					super.updateItem(item, empty);
					if (item == null) {
						setStyle("");
						pseudoClassStateChanged(excluido, false);
					} else {
						if (item.getSituacao() == Situacao.EXCLUIDO)
							pseudoClassStateChanged(excluido, true);
						else
							pseudoClassStateChanged(excluido, false);
					}
				}
			};

			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					abreTelaEndereco(row.getItem());
				}
			});

			return row;
		});

		return this;
	}

	private CadEnderecoDadosController iniciaCadEndereco() {
		FXMLLoader loader = new FXMLLoader(CadEnderecoController.getFxmlLocate());
		try {
			cadastroEndereco = loader.load();
			controller = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return this;
	}

	@Override
	public synchronized void initialize(URL location, ResourceBundle resources) {
		linkaCelulas().iniciaCadEndereco();
	}

	public void initData(String pessoa, Set<Endereco> enderecos, StackPane spRoot) {
		txtTitulo.setText(pessoa);
		this.spRoot = spRoot;
		this.enderecos = enderecos;
		carregaGrid();

		// Necessário por um bug na tela ao carregar ela.
		dashBoard.atualizaTabPane();
	}

	public DashboardFormPadrao getDashBoard() {
		return dashBoard;
	}

	public void setDashBoard(DashboardFormPadrao dashBoard) {
		this.dashBoard = dashBoard;
	}

	public static URL getFxmlLocate() {
		return CadEnderecoDadosController.class.getResource("/cadastro/view/cadastros/CadEnderecoDados.fxml");
	}
}
