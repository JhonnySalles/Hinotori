package administrador.controller.cadastros;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;

import comum.model.animation.TelaAnimation;
import comum.model.enums.Notificacao;
import comum.model.enums.Situacao;
import comum.model.notification.Notificacoes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
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
import administrador.App;
import administrador.model.entities.Contato;

public class CadContatoDadosController implements Initializable {

	@FXML
	private StackPane root;

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
	private TableColumn<Contato, String> tbClTipo;

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

	private AnchorPane cadastroContato;
	private CadContatoController controller;

	private ObservableList<Contato> obsContatos;
	private List<Contato> contatos;
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
		adicionarContato();
	}

	@FXML
	public void onBtnRemoverEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnRemover.fire();
		}
	}

	@FXML
	public void onBtnRemoverClick() {
		if (contatos.isEmpty() || tbContatos.getSelectionModel().isEmpty())
			if (contatos.isEmpty())
				Notificacoes.notificacao(Notificacao.AVISO, "Não foi possível apagar item",
						"Não existe nenhum endereço cadastrado.");
			else
				Notificacoes.notificacao(Notificacao.AVISO, "Não foi possível apagar item",
						"Não foi possível apagar o endereço, nenhum item selecionado.");
		else
			tbContatos.getSelectionModel().getSelectedItem().setSituacao(Situacao.EXCLUIDO);
	}

	private void adicionarContato() {
		abreTelaContato(null);
	}

	private void abreTelaContato(Contato contato) {
		BoxBlur blur = new BoxBlur(3, 3, 3);

		controller.setContatos(contatos);
		JFXDialog dialog = new JFXDialog(root, cadastroContato, JFXDialog.DialogTransition.CENTER);

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

		if (contato != null)
			controller.carregaContato(contato);

		dialog.show();
	}

	private void carregaGrid() {
		if (contatos == null)
			return;

		obsContatos = FXCollections.observableArrayList(this.contatos);
		tbContatos.setItems(obsContatos);
		tbContatos.refresh();
	}

	private CadContatoDadosController linkaCelulas() {
		tbClNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tbClTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
		tbClCelular.setCellValueFactory(new PropertyValueFactory<>("celular"));
		tbClEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tbClTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
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

	private CadContatoDadosController iniciaCadContato() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/pdv/view/cadastros/CadContato.fxml"));
		try {
			cadastroContato = loader.load();
			controller = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return this;
	}

	@Override
	public synchronized void initialize(URL location, ResourceBundle resources) {
		linkaCelulas().iniciaCadContato();
	}

	public void initData(String pessoa, List<Contato> contatos, StackPane spRoot) {
		txtTitulo.setText(pessoa);
		this.spRoot = spRoot;
		this.contatos = contatos;
		carregaGrid();

		// Necessário por um bug na tela ao carregar ela.
		App.getMainController().atualizaTabPane();
	}

}
