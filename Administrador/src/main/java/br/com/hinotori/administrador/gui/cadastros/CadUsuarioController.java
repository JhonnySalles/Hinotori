package br.com.hinotori.administrador.gui.cadastros;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class CadUsuarioController implements Initializable {
	
	@FXML
	JFXTextField txtCodigo;
	
	@FXML
	JFXTextField txtNome;
	
	@FXML
	JFXTextField txtSobreNome;
	
	@FXML
	JFXTextField txtEndereco;
	
	@FXML
	JFXTextField txtNumero;
	
	@FXML
	JFXTextField txtComplemento;
	
	@FXML
	JFXTextField txtCep;
	
	@FXML
	JFXButton btnPsqCidades;
	
	@FXML
	JFXTextField txtCidade;
	
	@FXML
	JFXComboBox cmbBoxEstado;
	
	@FXML
	JFXTextField txtTelefone;
	
	@FXML
	JFXTextField txtCelular;

	@FXML
	Pane paneBackground;
	
	@FXML
	ScrollPane background;
	
	@FXML
	private Button btnConfirmar;
	@FXML
	private Button btnCancelar;
	@FXML
	private Button btnNovo;
	@FXML
	private Button btnExcluir;
	@FXML
	private Button btnPesquisar;
	@FXML
	private Button btnVoltar;
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		background.setFitToHeight(true);
		background.setFitToWidth(true);
		
	}
}
