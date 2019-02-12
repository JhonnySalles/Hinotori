package application;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class TelaConfiguracaoController implements Initializable {
	
	@FXML
	AnchorPane background;
	
	@FXML
	Button btnExit;
	
	@FXML
	Button btnTestarConexao;
	
	@FXML
	Button btnConfirmar;
	
	@FXML
	Button btnCancelar;
	
	@FXML
	ChoiceBox chBoxBase;
	
	@FXML
	TextField txtIP;
	
	@FXML
	TextField txtPorta;
	
	@FXML
	TextField txtUsuario;

	@FXML
	TextField txtSenha;
	
	@FXML
	public void onBtExitAction() {
		System.exit(0);
	}
	
	@FXML
	public void onTxtPortaEventAction() {
		Constraints.setTxtFieldPort(txtIP);
	}
	
	public void onTxtIPExitAction() {
		Constraints.validaIp(txtIP);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		txtIP.focusedProperty().addListener(new ChangeListener<Boolean>(){
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
		        if (!newPropertyValue) {
		        	 onTxtIPExitAction();
		        } else {
		        	txtIP.setStyle("");
		        }
		    }
		});
		
		txtUsuario.focusedProperty().addListener(new ChangeListener<Boolean>(){
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
		    	if (txtUsuario.textProperty().get().toString().isEmpty()) {
		    		txtUsuario.setStyle("");
		        } else {
		        	txtUsuario.setStyle("-fx-border-color: green;");
		        }
		    }
		});
		
		txtSenha.focusedProperty().addListener(new ChangeListener<Boolean>(){
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
		        if (txtSenha.textProperty().get().toString().isEmpty()) {
		        	txtSenha.setStyle("");
		        } else {
		        	txtSenha.setStyle("-fx-border-color: green;");
		        }
		    }
		});
		
	}

}
