package Administrador.controller.cadastros;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import Administrador.model.dao.services.SaborServices;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import model.enums.Situacao;
import model.enums.TipoCliente;

public class CadSaborController implements Initializable {

	@FXML
	private JFXTextField txtNome;

	@FXML
	private JFXTextField txtSobreNome;

	@FXML
	private JFXTextField txtTelefone;

	@FXML
	private JFXTextField txtCelular;

	@FXML
	private JFXTextField txtCpfCnpj;

	@FXML
	private JFXComboBox<Situacao> cbSituacao;

	@FXML
	private JFXComboBox<TipoCliente> cbClienteTipo;

	@FXML
	private Pane paneBackground;

	@FXML
	private ScrollPane background;

	@FXML
	private JFXButton btnConfirmar;

	@FXML
	private JFXButton btnNovo;

	@FXML
	private JFXButton btnExcluir;

	@FXML
	private JFXButton btnVoltar;

	private SaborServices saborServices;
	private Boolean edicao;

	@FXML
	public void onBtnConfirmarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnConfirmar.fire();
		}
	}

	@FXML
	public void onBtnConfirmarClick() {
		if (validaCampos()) {
			atualizaEntidade();

			limpaCampos();
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
		limpaCampos();
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

	private Boolean validaCampos() {
		return true;
	}

	private void limpaCampos() {

	}

	private void atualizaEntidade() {

	}

	private void atualizaTela() {

	}

	private void configuraCampos() {

		setSaborServices(new SaborServices());
	}

	private void setSaborServices(SaborServices saborServices) {
		this.saborServices = saborServices;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		background.setFitToHeight(true);
		background.setFitToWidth(true);
		configuraCampos();
		edicao = false;
	}

}
