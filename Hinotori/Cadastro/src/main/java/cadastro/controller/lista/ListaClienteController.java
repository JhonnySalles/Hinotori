package cadastro.controller.lista;

import java.net.URL;
import java.util.ResourceBundle;

import cadastro.controller.cadastros.CadClienteController;
import comum.form.ListaFormPadrao;
import comum.model.enums.Situacao;
import comum.model.utils.ViewGerenciador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import servidor.dao.services.GenericService;
import servidor.entities.Cliente;

public class ListaClienteController extends ListaFormPadrao {

	@FXML
	private TableView<Cliente> tbClientes;

	@FXML
	private TableColumn<Cliente, String> tbClId;

	@FXML
	private TableColumn<Cliente, String> tbClNome;

	@FXML
	private TableColumn<Cliente, String> tbClCpf;

	@FXML
	private TableColumn<Cliente, String> tbClCnpj;

	@FXML
	private TableColumn<Cliente, String> tbClDataCadastro;

	@FXML
	private TableColumn<Cliente, String> tbClContatoPadrao;

	@FXML
	private TableColumn<Cliente, String> tbClEnderecoPadrao;

	private ObservableList<Cliente> obsClientes;
	private FilteredList<Cliente> filteredData;

	final PseudoClass excluido = PseudoClass.getPseudoClass("excluido");

	private GenericService<Cliente> service = new GenericService<Cliente>(Cliente.class);

	public void teste(String teste) {
		txtPesquisa.setText(teste);
	}

	@Override
	public void onBtnNovoClick() {
		CadClienteController ctn = (CadClienteController) ViewGerenciador.loadTela(CadClienteController.getFxmlLocate(),
				spRoot);
		ctn.setOnClose(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				atualizar();
			}
		});
	}

	@Override
	public void onBtnExcluirClick() {

	}

	@Override
	public void onBtnEditarClick() {
		editar(tbClientes.getSelectionModel().getSelectedItem());
	}

	@Override
	public void onBtnAtualizarClick() {
		atualizar();
	}

	private void editar(Cliente editar) {
		if (editar == null)
			return;

		CadClienteController ctn = (CadClienteController) ViewGerenciador.loadTela(CadClienteController.getFxmlLocate(),
				spRoot);
		ctn.setOnClose(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				atualizar();
			}
		});
		ctn.carregar(editar);
	}

	private void atualizar() {
		obsClientes = FXCollections.observableArrayList(service.listar());
		tbClientes.setItems(obsClientes);
		tbClientes.refresh();
	}

	private ListaClienteController linkaCelulas() {
		tbClId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tbClNome.setCellValueFactory(new PropertyValueFactory<>("nomeSobrenome"));
		tbClCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		tbClCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
		tbClDataCadastro.setCellValueFactory(new PropertyValueFactory<>("dataCadastro"));
		// tbClContatoPadrao.setCellValueFactory(new
		// PropertyValueFactory<>("tipoContato"));
		// tbClEnderecoPadrao.setCellValueFactory(new
		// PropertyValueFactory<>("tipoContato"));

		tbClientes.setRowFactory(tv -> {
			TableRow<Cliente> row = new TableRow<>() {
				@Override
				public void updateItem(Cliente item, boolean empty) {
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
				if (event.getClickCount() == 2 && (!row.isEmpty()))
					editar(row.getItem());
			});

			return row;
		});

		return this;
	}

	@Override
	public synchronized void inicializa(URL location, ResourceBundle resources) {
		linkaCelulas();
	}

	public static URL getFxmlLocate() {
		return ListaClienteController.class.getResource("/cadastro/view/lista/ListaCliente.fxml");
	}

}
