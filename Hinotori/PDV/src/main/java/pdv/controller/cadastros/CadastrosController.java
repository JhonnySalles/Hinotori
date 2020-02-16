package pdv.controller.cadastros;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

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
		main.loadView("/pdv/view/cadastros/CadCliente.fxml", "Cadastro de clientes", "");
	}
	
	@FXML
	public void onBtnCadEmpresaAction() {
		main.loadView("/pdv/view/cadastros/CadEmpresa.fxml", "Cadastro de empresas", "");
	}
	
	@FXML
	public void onBtnCadUsuarioAction() {
		main.loadView("/pdv/view/cadastros/CadUsuario.fxml", "Cadastro de usuários", "");
	}

	@FXML
	public void onBtnCadProdutoAction() {
		main.loadView("/pdv/view/cadastros/CadProduto.fxml", "Cadastro de produtos", "");
	}
	
	
	@Override
	public synchronized void initialize(URL location, ResourceBundle resources) {


	}
}
