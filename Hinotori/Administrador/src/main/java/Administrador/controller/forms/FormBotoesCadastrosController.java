package Administrador.controller.forms;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;

public class FormBotoesCadastrosController implements Initializable {

	Class<?> controller;
	
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
	public void onBtnConfirmarAction() {

	}
	
	@FXML
	public void onBtnConfirmarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnConfirmar.fire();
		}
	}
	
	@FXML
	public void onBtnCancelarAction() {

	}
	
	@FXML
	public void onBtnCancelarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnCancelar.fire();
		}
	}
	
	@FXML
	public void onBtnNovoAction() {

	}
	
	@FXML
	public void onBtnNovoEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnNovo.fire();
		}
	}
	
	@FXML
	public void onBtnExcluirAction() {

	}
	
	@FXML
	public void onBtnExcluirEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnExcluir.fire();
		}
	}
	
	@FXML
	public void onBtnVoltarAction() {

	}
	
	@FXML
	public void onBtnVoltarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnVoltar.fire();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	public void setControllerPai(Class <?> pai) {
		this.controller = pai;
	}
	
	
}
