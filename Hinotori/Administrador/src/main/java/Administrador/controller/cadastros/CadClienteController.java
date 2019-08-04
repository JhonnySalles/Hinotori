package Administrador.controller.cadastros;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import model.alerts.Alertas;
import model.constraints.Limitadores;
import model.mask.Mascaras;

public class CadClienteController implements Initializable {
	
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
	private ImageView imgLogo;
	

	@FXML
	private Button btnConfirmar;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnNovo;

	@FXML
	private Button btnExcluir;

	@FXML
	private Button btnVoltar;

	
	@FXML
	public void onBtnConfirmarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnConfirmar.fire();
		}
	}

	@FXML
	public void onBtnConfirmarClick() {
		
	}
	
	@FXML
	public void onBtnCancelarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnCancelar.fire();
		}
	}

	@FXML
	public void onBtnCancelarClick() {
		if (Alertas.Confirmacao("Sair", "Deseja realmente cancelar? \nToda a alteração será descartada.")) {
			System.exit(0);
		}
	}
	
	@FXML
	public void onBtnNovoEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnNovo.fire();
		}
	}

	@FXML
	public void onBtnNovoClick() {
		
	}
	
	@FXML
	public void onBtnExcluirEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnExcluir.fire();
		}
	}

	@FXML
	public void onBtnExcluirClick() {
		
	}
	
	@FXML
	public void onBtnVoltarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnVoltar.fire();
		}
	}

	@FXML
	public void onBtnVoltarClick() {
		
	}
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		background.setFitToHeight(true);
		background.setFitToWidth(true);
		
		Limitadores.setTextFieldID(txtCodigo, 11);
		Limitadores.setTextFieldInteger(txtNumero);
		
		Mascaras.cepField(txtCep);
		Mascaras.foneField(txtTelefone);
		Mascaras.foneField(txtCelular);		
		
	}
}
