package pdv.controller.cadastros;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import cadastro.controller.lista.ListaClienteController;
import cadastro.controller.lista.ListaEmpresaController;
import cadastro.controller.lista.ListaProdutoController;
import cadastro.controller.lista.ListaUsuarioController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import pdv.App;
import pdv.controller.DashboardController;

public class CadastrosController implements Initializable {

	@FXML
	JFXButton btnCadCliente;

	@FXML
	JFXButton btnCadEmpresa;

	@FXML
	JFXButton btnCadUsuario;

	@FXML
	JFXButton btnCadProduto;

	final DashboardController main = App.getMainController();

	@FXML
	public void onBtnCadClienteAction() {
		main.loadAbas(ListaClienteController.getFxmlLocate(), "Cadastros de clientes", "");
	}

	@FXML
	public void onBtnCadEmpresaAction() {
		main.loadAbas(ListaEmpresaController.getFxmlLocate(), "Cadastros de empresas", "");
	}

	@FXML
	public void onBtnCadUsuarioAction() {
		main.loadAbas(ListaUsuarioController.getFxmlLocate(), "Cadastros de usuários", "");
	}

	@FXML
	public void onBtnCadProdutoAction() {
		main.loadAbas(ListaProdutoController.getFxmlLocate(), "Cadastros de produtos", "");
	}

	@Override
	public synchronized void initialize(URL location, ResourceBundle resources) {

	}
}
