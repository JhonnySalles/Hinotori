package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class TelaConfiguracaoController implements Initializable {
	
	@FXML
	Button btnExit;
	
	@FXML
	Button btnTestarConexao;
	
	@FXML
	Button btnConfirmar;
	
	@FXML
	Button btnCancelar;
	
	
	@FXML
	Label lblConfig;
	
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
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Font font = Font.loadFont(getClass().getResourceAsStream("../font/SwordKanji.ttf"), 36);
		lblConfig.setFont(font);
	}

}
