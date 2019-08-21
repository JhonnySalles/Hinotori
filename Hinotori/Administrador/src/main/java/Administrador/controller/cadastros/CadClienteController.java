package Administrador.controller.cadastros;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import Administrador.model.entities.Cliente;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.constraints.Validadores;
import model.enums.Situacao;
import model.enums.UsuarioNivel;
import model.mask.Mascaras;

public class CadClienteController implements Initializable {

	@FXML
	private String Codigo;

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
	private JFXTextField txtEmail;

	@FXML
	private JFXDatePicker dtPicDataCadastro;

	@FXML
	private JFXComboBox<Situacao> cbSituacao;

	@FXML
	private JFXComboBox<UsuarioNivel> cbClienteTipo;

	@FXML
	private Pane paneBackground;

	@FXML
	private ScrollPane background;

	@FXML
	private JFXButton btnEndereco;

	@FXML
	private JFXButton btnContato;

	@FXML
	private ImageView imgLogo;

	@FXML
	private JFXButton btnConfirmar;

	@FXML
	private JFXButton btnCancelar;

	@FXML
	private JFXButton btnNovo;

	@FXML
	private JFXButton btnExcluir;

	@FXML
	private JFXButton btnVoltar;

	private Cliente cliente;

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
	public void onBtnCancelarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnCancelar.fire();
		}
	}

	@FXML
	public void onBtnCancelarClick() {
		limpaCampos();
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
		if (!txtNome.getText().isEmpty()) {
			txtNome.setUnFocusColor(Color.RED);
			return false;
		} else
			return true;
	}

	private void limpaCampos() {
		cliente = new Cliente();
		txtNome.setText("");
		txtSobreNome.setText("");
		txtTelefone.setText("");
		txtCelular.setText("");
		txtCpfCnpj.setText("");
		txtEmail.setText("");
		dtPicDataCadastro.setValue(null);
		cbSituacao.getSelectionModel().select(0);
		cbClienteTipo.getSelectionModel().select(0);
	}

	private void atualizaEntidade() {
		cliente.setNome(txtNome.getText());
		cliente.setSobreNome(txtSobreNome.getText());
		cliente.setDddTelefone(txtTelefone.getText().substring(0, 1));
		cliente.setTelefone(txtTelefone.getText().substring(2));
		cliente.setDddCelular(txtCelular.getText().substring(0, 1));
		cliente.setCelular(txtCelular.getText().substring(2));
		cliente.setCpfCnpj(txtCpfCnpj.getText());
		cliente.setEmail(txtEmail.getText());
		cliente.setSituacao(cbSituacao.getSelectionModel().getSelectedItem());
		cliente.setTipo(cbClienteTipo.getSelectionModel().getSelectedItem());

		LocalDate ld = dtPicDataCadastro.getValue();
		Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		cliente.setDataCadastro(Date.from(instant));
	}

	private void atualizaTela() {
		txtNome.setText(cliente.getNome());
		txtSobreNome.setText(cliente.getSobreNome());
		txtTelefone.setText(cliente.getDddTelefone() + cliente.getTelefone());
		txtCelular.setText(cliente.getDddCelular() + cliente.getCelular());
		txtCpfCnpj.setText(cliente.getCpfCnpj());
		txtEmail.setText(cliente.getEmail());
		cbSituacao.getSelectionModel().getSelectedItem().equals(cliente.getSituacao());
		cbClienteTipo.getSelectionModel().getSelectedItem().equals(cliente.getTipo());

		Instant instant = Instant.ofEpochMilli(cliente.getDataCadastro().getTime());
		dtPicDataCadastro.setValue(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate());
	}

	public void carregarCliente(Cliente cliente) {
		this.cliente = cliente;
		atualizaTela();
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		Validadores.setTextFieldNotEmptyGreen(txtNome);
		Mascaras.cpfCnpjField(txtCpfCnpj);
		Mascaras.foneField(txtTelefone);
		Mascaras.foneField(txtCelular);
		background.setFitToHeight(true);
		background.setFitToWidth(true);
		Codigo = "";
	}
}
