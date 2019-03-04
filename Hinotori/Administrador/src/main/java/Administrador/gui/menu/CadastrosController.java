package Administrador.gui.menu;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Administrador.App;
import Administrador.gui.DashboardController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tooltip;

public class CadastrosController implements Initializable {
	
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		/* Popup de descricao dos botoes */
        Tooltip toltCliente = new Tooltip("Cadastro de cliente");
        Tooltip toltEmpresa = new Tooltip("Cadastro de empresa");
        Tooltip toltUsuario = new Tooltip("Cadastro de usuario");
        
        btnCadCliente.setTooltip(toltCliente);
        btnCadEmpresa.setTooltip(toltEmpresa);
        btnCadUsuario.setTooltip(toltUsuario);

	}
}
