package Administrador.gui.menu;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Administrador.App;
import Administrador.gui.DashboardController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tooltip;

public class ConfiguracaoController implements Initializable {
	
	@FXML
	JFXButton btnImpressoras;
	
	final DashboardController main = App.getMainController();

	
	@FXML
	public void onBtnImpressorasAction() {
		main.onBtnCadClienteAction();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		/* Popup de descricao dos botoes */
        Tooltip toltImpressoras = new Tooltip("Cadastro de impressoras");
        
        btnImpressoras.setTooltip(toltImpressoras);
	}
}
