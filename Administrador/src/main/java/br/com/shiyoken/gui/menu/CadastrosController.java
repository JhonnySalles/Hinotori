package br.com.shiyoken.gui.menu;

import com.jfoenix.controls.JFXButton;

import br.com.shiyoken.administrador.App;
import br.com.shiyoken.gui.DashboardController;
import javafx.fxml.FXML;

public class CadastrosController {
	
	@FXML
	JFXButton btnCadCliente;
	
	@FXML
	JFXButton btnCadEmpresa;
	
	@FXML
	JFXButton btnCadUsuario;
	
	final DashboardController main = App.getMainController();
	
	
	
	@FXML
	public void onBtnCadClienteAction() {
		main.onBtnCadClienteAction();
	}
	
	@FXML
	public void onBtnCadEmpresaAction() {
		main.onBtnCadEmpresaAction();
	}
	
	@FXML
	public void onBtnCadUsuarioAction() {
		main.onBtnCadUsuarioAction();
	}
	
	
}
