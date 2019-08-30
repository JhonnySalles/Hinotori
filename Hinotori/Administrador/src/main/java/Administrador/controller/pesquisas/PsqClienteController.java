package Administrador.controller.pesquisas;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Administrador.controller.cadastros.CadClienteController;
import Administrador.model.dao.services.ClienteServices;
import Administrador.model.entities.Cliente;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import model.animation.ClienteTranslate;
import model.enums.Situacao;
import model.enums.TipoCliente;
import model.notification.Notificacao;

public class PsqClienteController implements Initializable {

	@FXML
	private TableView<Cliente> tabela;

	@FXML
	private TableColumn<Cliente, String> nome;

	@FXML
	private TableColumn<Cliente, String> telefone;

	@FXML
	private TableColumn<Cliente, String> celular;

	@FXML
	private TableColumn<Cliente, String> email;

	@FXML
	private TableColumn<Cliente, TipoCliente> tipo;

	@FXML
	private TableColumn<Cliente, Date> dataCadastro;

	@FXML
	private TableColumn<Cliente, Situacao> situacao;

	@FXML
	private Pane paneBackground;

	@FXML
	private ScrollPane background;

	@FXML
	private StackPane stackPane;

	@FXML
	private AnchorPane rootPane;

	@FXML
	private JFXButton btnNovo;

	@FXML
	private JFXButton btnEditar;

	@FXML
	private JFXButton btnAtualizar;

	private ObservableList<Cliente> obsLClientes;
	private FXMLLoader loader;
	private ClienteServices clienteService;

	@FXML
	public void onBtnNovoEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnNovo.fire();
		}
	}

	@FXML
	public void onBtnNovoClick() {
		loadView("/Administrador/view/cadastros/CadCliente.fxml");
		CadClienteController cadCliente = loader.getController();
		cadCliente.setPsqCliente(this);
	}

	@FXML
	public void onBtnEditarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnEditar.fire();
		}
	}

	@FXML
	public void onBtnEditarClick() {
		if (tabela.getSelectionModel().isEmpty()) {
			Notificacao.Dark("Nenhum item selecionado", "Favor selecionar algum item.", 5.0, Pos.BASELINE_RIGHT);
			return;
		}

		desabilitaBotoes();
		loadView("/Administrador/view/cadastros/CadCliente.fxml");
		CadClienteController cadCliente = loader.getController();
		cadCliente.setPsqCliente(this);
		cadCliente.carregarCliente(clienteService.pesquisar(tabela.getSelectionModel().getSelectedItem().getId()));
		habilitaBotoes();
	}

	@FXML
	public void onBtnAtualizarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnAtualizar.fire();
		}
	}

	@FXML
	public void onBtnAtualizarClick() {
		carregaGrid();
	}

	public void loadView(String tela) {
		try {
			loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(tela));
			Parent rootCima = loader.load();
			stackPane.getChildren().add(rootCima);

			new ClienteTranslate().abrirPane(stackPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public FXMLLoader getLoader() {
		return loader;
	}

	public void returnView() {
		new ClienteTranslate().fecharPane(stackPane, t -> {
			stackPane.getChildren().remove(stackPane.getChildren().size() - 1);
			if (stackPane.getChildren().size() <= 1)
				carregaGrid();
		});
	}

	private void desabilitaBotoes() {
		btnNovo.setDisable(true);
		btnEditar.setDisable(true);
		btnAtualizar.setDisable(true);
	}

	private void habilitaBotoes() {
		btnNovo.setDisable(false);
		btnEditar.setDisable(false);
		btnAtualizar.setDisable(false);
	}

	private void carregaGrid() {
		if (clienteService == null)
			clienteService = new ClienteServices();

		List<Cliente> lista = clienteService.pesquisarTodos();
		obsLClientes = FXCollections.observableArrayList(lista);
		tabela.setItems(obsLClientes);
	}

	private void inicializaGrid() {
		nome.setCellValueFactory(new Callback<CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Cliente, String> p) {
				// p.getValue() returns the PersonType instance for a particular TableView row
				if (p.getValue() != null && !p.getValue().getNome().isEmpty()) {
					return new SimpleStringProperty(p.getValue().getNome() + " " + p.getValue().getSobreNome());
				} else {
					return new SimpleStringProperty("");
				}
			}
		});

		telefone.setCellValueFactory(new Callback<CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Cliente, String> p) {
				// p.getValue() returns the PersonType instance for a particular TableView row
				if (p.getValue() != null && p.getValue().getTelefone() != null
						&& !p.getValue().getTelefone().isEmpty()) {
					return new SimpleStringProperty(
							"(" + p.getValue().getDddTelefone() + ")" + p.getValue().getTelefone());
				} else {
					return new SimpleStringProperty("");
				}
			}
		});

		celular.setCellValueFactory(new Callback<CellDataFeatures<Cliente, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Cliente, String> p) {
				// p.getValue() returns the PersonType instance for a particular TableView row
				if (p.getValue() != null && p.getValue().getTelefone() != null) {
					return new SimpleStringProperty(
							"(" + p.getValue().getDddCelular() + ")" + p.getValue().getCelular());
				} else {
					return new SimpleStringProperty("");
				}
			}
		});

		email.setCellValueFactory(new PropertyValueFactory<>("email"));
		tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
		dataCadastro.setCellValueFactory(new PropertyValueFactory<>("dataCadastro"));
		situacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));

		tabela.setRowFactory(tv -> {
			TableRow<Cliente> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty()))
					onBtnEditarClick();
			});
			return row;
		});

		carregaGrid();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		background.setFitToHeight(true);
		background.setFitToWidth(true);
		inicializaGrid();
	}
}
