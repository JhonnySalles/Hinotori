package cadastro.controller.lista;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cadastro.controller.cadastros.CadClienteController;
import comum.form.DashboardFormPadrao;
import comum.form.ListaFormPadrao;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import servidor.dao.services.ClienteServices;
import servidor.entities.Cliente;

public class ListaClienteController extends ListaFormPadrao implements Initializable {

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
	private ClienteServices clienteService;

	@Override
	public void onNovoKeyPress(KeyEvent e) {
		if (e.getCode().toString().equals("ENTER"))
			btnNovo.fire();
	}

	@Override
	public void onBtnNovoClick() {
		CadClienteController ctn = (CadClienteController) DashboardFormPadrao
				.loadTela(CadClienteController.getFxmlLocate(), spRoot);
		ctn.setOnClose(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				atualizar();
			}
		});
	}

	@Override
	public void onExcluirKeyPress(KeyEvent e) {
		if (e.getCode().toString().equals("ENTER"))
			btnExcluir.fire();
	}

	@Override
	public void onBtnExcluirClick() {

	}

	@Override
	public void onEditarKeyPress(KeyEvent e) {
		if (e.getCode().toString().equals("ENTER"))
			btnEditar.fire();
	}

	@Override
	public void onBtnEditarClick() {

	}

	@Override
	public void onAtualizarKeyPress(KeyEvent e) {
		if (e.getCode().toString().equals("ENTER"))
			btnAtualizar.fire();
	}

	@Override
	public void onBtnAtualizarClick() {

	}

	public void atualizar() {

	}

	@Override
	public synchronized void initialize(URL location, ResourceBundle resources) {
		inicializaHeranca();
	}

	public static URL getFxmlLocate() {
		return ListaClienteController.class.getResource("/cadastro/view/lista/ListaCliente.fxml");
	}

}