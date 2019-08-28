package Administrador.controller.cadastros;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import Administrador.controller.pesquisas.PsqClienteController;
import Administrador.model.dao.services.ClienteServices;
import Administrador.model.entities.Cliente;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.constraints.Validadores;
import model.enums.Situacao;
import model.enums.TipoCliente;
import model.mask.Mascaras;
import model.notification.Notificacao;
import model.utils.Utils;

public class CadClienteController implements Initializable {

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
	private JFXTextArea txtAreaObservacao;

	@FXML
	private JFXComboBox<Situacao> cbSituacao;

	@FXML
	private JFXComboBox<TipoCliente> cbClienteTipo;

	@FXML
	private Pane paneBackground;

	@FXML
	private ScrollPane background;

	@FXML
	private AnchorPane rootPane;

	@FXML
	private JFXButton btnEndereco;

	@FXML
	private ImageView imgLogo;

	@FXML
	private JFXButton btnConfirmar;

	@FXML
	private JFXButton btnCancelar;

	@FXML
	private JFXButton btnExcluir;

	@FXML
	private JFXButton btnVoltar;

	private Cliente cliente;
	private ClienteServices clienteService;
	private PsqClienteController PsqCliente;

	@FXML
	public void onBtnConfirmarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnConfirmar.fire();
		}
	}

	@FXML
	public void onBtnConfirmarClick() {
		if (validaCampos()) {
			rootPane.cursorProperty().set(Cursor.WAIT);
			atualizaEntidade();
			salvar();
			Notificacao.Dark("Processo concluído", "Cliente salvo com sucesso.", 3.0, Pos.BASELINE_RIGHT);
			limpaCampos();
			rootPane.cursorProperty().set(null);
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
	public void onBtnExcluirEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnExcluir.fire();
		}
	}

	@FXML
	public void onBtnExcluirClick() {
		if (cliente.getId() == null) {
			Notificacao.Dark("Aviso", "Não foi possivel realizar a exclusão, nenhum cliente selecionado.", 5.0,
					Pos.BASELINE_RIGHT);
			return;
		} else {
			if (clienteService == null)
				clienteService = new ClienteServices();
			clienteService.deletar(cliente.getId());
			Notificacao.Dark("Processamento concluído", "Item excluído com sucesso.", 5.0, Pos.BASELINE_RIGHT);
			limpaCampos();
		}
	}

	@FXML
	public void onBtnVoltarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnVoltar.fire();
		}
	}

	@FXML
	public void onBtnVoltarClick() {
		PsqCliente.returnView();
	}

	@FXML
	public void onBtnEnderecosEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnEndereco.fire();
		}
	}

	@FXML
	public void onBtnEnderecosClick() {
		atualizaEntidade();
		desabilitaBotoes();
		loadView("/Administrador/view/cadastros/CadClienteEndereco.fxml");
		CadClienteEnderecoController endereco = PsqCliente.getLoader().getController();
		endereco.setCadCliente(this);
		habilitaBotoes();
	}

	public PsqClienteController getPsqCliente() {
		return PsqCliente;
	}

	public void setPsqCliente(PsqClienteController PsqCliente) {
		this.PsqCliente = PsqCliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	private Boolean validaCampos() {
		if (txtNome.getText().isEmpty()) {
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
		txtAreaObservacao.setText("");
		cbSituacao.getSelectionModel().selectFirst();
		cbClienteTipo.getSelectionModel().selectFirst();
	}

	private void atualizaEntidade() {
		if (cliente == null)
			cliente = new Cliente();

		cliente.setNome(txtNome.getText());
		cliente.setSobreNome(txtSobreNome.getText());

		if (!txtTelefone.getText().isEmpty()) {
			cliente.setDddTelefone(Utils.removeMascaras(txtTelefone.getText()).substring(0, 2));
			cliente.setTelefone(Utils.removeMascaras(txtTelefone.getText()).substring(2));
		}

		if (!txtCelular.getText().isEmpty()) {
			cliente.setDddCelular(Utils.removeMascaras(txtCelular.getText()).substring(0, 1));
			cliente.setCelular(Utils.removeMascaras(txtCelular.getText()).substring(2));
		}

		cliente.setCpfCnpj(Utils.removeMascaras(txtCpfCnpj.getText()));
		cliente.setEmail(txtEmail.getText());
		cliente.setObservacao(txtAreaObservacao.getText());
		cliente.setSituacao(cbSituacao.getSelectionModel().getSelectedItem());
		cliente.setTipo(cbClienteTipo.getSelectionModel().getSelectedItem());
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
	}

	private void salvar() {
		if (clienteService == null)
			clienteService = new ClienteServices();
		clienteService.salvar(cliente);
	}

	public void carregarCliente(Cliente cliente) {
		this.cliente = cliente;
		atualizaTela();
	}

	public void loadView(String tela) {
		PsqCliente.loadSecondView(tela);
	}

	public void returnView() {
		PsqCliente.returnView();
	}

	private void desabilitaBotoes() {
		btnEndereco.setDisable(true);
		btnConfirmar.setDisable(true);
		btnCancelar.setDisable(true);
		btnExcluir.setDisable(true);
		btnVoltar.setDisable(true);
	}

	private void habilitaBotoes() {
		btnEndereco.setDisable(false);
		btnConfirmar.setDisable(false);
		btnCancelar.setDisable(false);
		btnExcluir.setDisable(false);
		btnVoltar.setDisable(false);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Validadores.setTextFieldNotEmpty(txtNome);
		Mascaras.cpfCnpjField(txtCpfCnpj);
		Mascaras.foneField(txtTelefone);
		Mascaras.foneField(txtCelular);
		cbSituacao.getItems().addAll(Situacao.values());
		cbSituacao.getSelectionModel().selectFirst();
		cbClienteTipo.getItems().addAll(TipoCliente.values());
		cbClienteTipo.getSelectionModel().selectFirst();
		background.setFitToHeight(true);
		background.setFitToWidth(true);
		cliente = new Cliente();
	}
}
