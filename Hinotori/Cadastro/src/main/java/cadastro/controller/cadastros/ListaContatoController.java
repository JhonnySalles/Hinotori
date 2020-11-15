package cadastro.controller.cadastros;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.Set;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import comum.form.DashboardFormPadrao;
import comum.form.ListaFormPadrao;
import comum.model.enums.Situacao;
import comum.model.notification.Notificacoes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import servidor.entities.Contato;

public class ListaContatoController extends ListaFormPadrao implements Initializable {

	@FXML
	private AnchorPane rootPane;

	@FXML
	private TableView<Contato> tbContatos;

	@FXML
	private TableColumn<Contato, String> tbClNome;

	@FXML
	private TableColumn<Contato, String> tbClTelefone;

	@FXML
	private TableColumn<Contato, String> tbClCelular;

	@FXML
	private TableColumn<Contato, String> tbClEmail;

	@FXML
	private TableColumn<Contato, String> tbClTipoContato;

	@FXML
	private TableColumn<Contato, String> tbClSituacao;

	@FXML
	private TableColumn<Contato, Boolean> tbClPadrao;

	@FXML
	private JFXTextField txtTitulo;

	@FXML
	private JFXButton btnAdicionar;

	@FXML
	private JFXButton btnRemover;

	@FXML
	private JFXButton btnVoltar;

	private CadContatoController controller;

	private ObservableList<Contato> obsContatos;
	private Set<Contato> contatos;
	private ToggleGroup tgGroup;

	final PseudoClass excluido = PseudoClass.getPseudoClass("excluido");

	@FXML
	public void onBtnVoltarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER"))
			btnVoltar.fire();
	}

	@FXML
	public void onBtnVoltarClick() {
		DashboardFormPadrao.closeTela(rootPane);
		onClose();
	}

	@FXML
	public void onBtnAdicionarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnAdicionar.fire();
		}
	}

	@FXML
	public void onBtnAdicionarClick() {
		adicionarContato();
	}

	@FXML
	public void onBtnRemoverEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER"))
			btnRemover.fire();
	}

	@FXML
	public void onBtnRemoverClick() {
		if (contatos.isEmpty() || tbContatos.getSelectionModel().isEmpty())
			if (contatos.isEmpty())
				Notificacoes.notificacao(AlertType.INFORMATION, "Não foi possível apagar item",
						"Não existe nenhum endereço cadastrado.");
			else
				Notificacoes.notificacao(AlertType.INFORMATION, "Não foi possível apagar item",
						"Não foi possível apagar o endereço, nenhum item selecionado.");
		else
			tbContatos.getSelectionModel().getSelectedItem().setSituacao(Situacao.EXCLUIDO);
	}

	private void adicionarContato() {
		abreTelaContato(null);
	}

	private void abreTelaContato(Contato contato) {
		controller = (CadContatoController) DashboardFormPadrao.loadDialog(CadContatoController.getFxmlLocate(),
				rootPane, new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent t) {
						contatos = controller.getContato();
						carregaGrid();
					}
				});
		controller.carregaContato(contato);
	}

	private void carregaGrid() {
		if (contatos == null)
			return;

		obsContatos = FXCollections.observableArrayList(this.contatos);
		tbContatos.setItems(obsContatos);
		tbContatos.refresh();
	}

	public void initData(String pessoa, Set<Contato> contatos) {
		txtTitulo.setText(pessoa);
		this.contatos = contatos;
		carregaGrid();
	}

	public Set<Contato> getContato() {
		return contatos;
	}

	private ListaContatoController linkaCelulas() {
		tbClNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tbClTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
		tbClCelular.setCellValueFactory(new PropertyValueFactory<>("celular"));
		tbClEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tbClTipoContato.setCellValueFactory(new PropertyValueFactory<>("tipoContato"));
		tbClSituacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));

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

		tbContatos.setRowFactory(tv -> {
			TableRow<Contato> row = new TableRow<>() {
				@Override
				public void updateItem(Contato item, boolean empty) {
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
					abreTelaContato(row.getItem());
				}
			});

			return row;
		});

		return this;
	}

	@Override
	public synchronized void initialize(URL location, ResourceBundle resources) {
		linkaCelulas();
	}

	public static URL getFxmlLocate() {
		return ListaContatoController.class.getResource("/cadastro/view/cadastros/ListaContato.fxml");
	}

}
