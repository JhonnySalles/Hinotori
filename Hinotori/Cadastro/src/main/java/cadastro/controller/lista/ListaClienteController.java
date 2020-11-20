package cadastro.controller.lista;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cadastro.controller.cadastros.CadClienteController;
import comum.form.ListaFormPadrao;
import comum.model.utils.ViewGerenciador;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

	private List<Cliente> clientes;
	private ObservableList<Cliente> obsClientes;
	private FilteredList<Cliente> filteredData;

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

	}

	@Override
	public void onBtnAtualizarClick() {

	}

	public void atualizar() {

	}

	@Override
	public synchronized void inicializa(URL location, ResourceBundle resources) {

	}

	public static URL getFxmlLocate() {
		return ListaClienteController.class.getResource("/cadastro/view/lista/ListaCliente.fxml");
	}

}
