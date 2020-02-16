package pdv.controller.pesquisas;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import pdv.App;
import pdv.controller.DashboardController;

public class PesquisasController implements Initializable {
	
	@FXML
	JFXButton btnPsqCliente;
	
	@FXML
	JFXButton btnPsqEmpresa;
	
	@FXML
	JFXButton btnPsqUsuario;
	
	@FXML
	JFXButton btnPsqProduto;
	
	final DashboardController main = App.getMainController();

	
	@FXML
	public void onBtnPsqClienteAction() {
		main.loadView("/pdv/view/pesquisas/PsqCliente.fxml", "Pesquisa de clientes", "");
	}
	
	@FXML
	public void onBtnPsqEmpresaAction() {
		main.loadView("/pdv/view/pesquisas/PsqEmpresa.fxml", "Pesquisa de empresas", "");
	}
	
	@FXML
	public void onBtnPsqUsuarioAction() {
		main.loadView("/pdv/view/pesquisas/PsqUsuario.fxml", "Pesquisa de usuários", "");
	}

	@FXML
	public void onBtnPsqProdutoAction() {
		main.loadView("/pdv/view/pesquisas/PsqProduto.fxml", "Pesquisa de produtos", "");
	}
	
	
	@Override
	public synchronized void initialize(URL location, ResourceBundle resources) {


	}
}
