package cadastro.controller.cadastros;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import comum.form.CadastroFormPadrao;
import comum.form.DashboardFormPadrao;
import comum.model.constraints.Limitadores;
import comum.model.constraints.TecladoUtils;
import comum.model.constraints.Validadores;
import comum.model.enums.Situacao;
import comum.model.enums.TipoCliente;
import comum.model.enums.TipoPessoa;
import comum.model.exceptions.ExcessaoBd;
import comum.model.mask.Mascaras;
import comum.model.notification.Notificacoes;
import comum.model.utils.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import servidor.dao.services.ClienteServices;
import servidor.entities.Cliente;

public class CadClienteController extends CadastroFormPadrao implements Initializable {

	@FXML
	private JFXTextField txtId;

	@FXML
	private JFXTextField txtNome;

	@FXML
	private JFXTextField txtCpf;

	@FXML
	private JFXTextField txtCnpj;

	@FXML
	private JFXTextArea txtAreaObservacao;

	@FXML
	private JFXDatePicker dtPkCadastro;

	@FXML
	private JFXComboBox<Situacao> cbSituacao;

	@FXML
	private JFXComboBox<TipoCliente> cbClienteTipo;

	@FXML
	private JFXComboBox<TipoPessoa> cbPessoaTipo;

	@FXML
	private JFXButton btnContato;

	@FXML
	private JFXButton btnEndereco;

	private Cliente cliente;
	private ClienteServices clienteService;
	private String id;

	@FXML
	public void onBtnConfirmarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnConfirmar.fire();
		}
	}

	@FXML
	public void onBtnConfirmarClick() {
		if (validaCampos()) {
			try {
				spRoot.cursorProperty().set(Cursor.WAIT);
				desabilitaBotoes().atualizaEntidade().salvar(cliente);
			} finally {
				spRoot.cursorProperty().set(null);
				habilitaBotoes();
			}
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
		if ((cliente == null) || (cliente.getId() == null) || txtId.getText().isEmpty()
				|| txtId.getText().equalsIgnoreCase("0"))
			Notificacoes.notificacao(AlertType.INFORMATION, "Aviso",
					"Não foi possivel realizar a exclusão, nenhum cliente selecionado.");
		else {
			try {
				spRoot.cursorProperty().set(Cursor.WAIT);
				desabilitaBotoes().atualizaEntidade().excluir(cliente);
			} finally {
				spRoot.cursorProperty().set(null);
				habilitaBotoes();
			}
		}
	}

	@FXML
	public void onBtnPesquisarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnPesquisar.fire();
		}
	}

	@FXML
	public void onBtnPesquisarClick() {
		// limpaCampos();
	}

	@FXML
	public void onTxtIdClick() {
		txtId.getSelectedText();
	}

	@FXML
	public void onTxtIdEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			if (!txtId.getText().equalsIgnoreCase("0") && !txtId.getText().isEmpty())
				onTxtIdExit();
			else
				limpaCampos();

			Utils.clickTab();
		}
	}

	@FXML
	public void onBtnEnderecoEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnEndereco.fire();
		}
	}

	@FXML
	public void onBtnEnderecoClick() {
		ListaEnderecoController ctn = (ListaEnderecoController) DashboardFormPadrao
				.loadTela(ListaEnderecoController.getFxmlLocate(), spRoot);
		ctn.initData(txtNome.getText(), cliente.getEnderecos());
		ctn.setOnClose(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				cliente.setEnderecos(ctn.getEndereco());
			}
		});
	}

	@FXML
	public void onBtnContatoEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnContato.fire();
		}
	}

	@FXML
	public void onBtnContatoClick() {
		ListaContatoController ctn = (ListaContatoController) DashboardFormPadrao
				.loadTela(ListaContatoController.getFxmlLocate(), spRoot);
		ctn.initData(txtNome.getText(), cliente.getContatos());
		ctn.setOnClose(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				cliente.setContatos(ctn.getContato());
			}
		});
	}

	public void onTxtIdExit() {
		if (!txtId.getText().isEmpty() && !txtId.getText().equalsIgnoreCase("0")) {
			try {
				carregarCliente(clienteService.pesquisar(Long.valueOf(txtId.getText())));
			} catch (ExcessaoBd e) {
				e.printStackTrace();
			}
		} else {
			if (txtId.getText().isEmpty() || txtId.getText().equalsIgnoreCase("0"))
				limpaCampos();
		}
	}

	public Cliente getCliente() {
		return cliente;
	}

	public CadClienteController carregarCliente(Cliente cliente) {
		limpaCampos();
		this.cliente = cliente;
		if (cliente != null)
			atualizaTela(cliente);
		return this;
	}

	private Boolean validaCampos() {
		Boolean valida = true;

		if (txtNome.getText().isEmpty()) {
			txtNome.setUnFocusColor(Color.RED);
			valida = false;
		}

		switch (cbPessoaTipo.getSelectionModel().getSelectedItem()) {
		case FISICO:
			if (!Validadores.validaCpfCnpj(txtCpf.getText())) {
				txtCpf.setUnFocusColor(Color.RED);
				valida = false;
			}
			break;
		case JURIDICO:
			if (!Validadores.validaCpfCnpj(txtCnpj.getText())) {
				txtCnpj.setUnFocusColor(Color.RED);
				valida = false;
			}
			break;
		default:
			break;
		}

		return valida;
	}

	private CadClienteController limpaCampos() {
		cliente = new Cliente();
		txtId.setText("0");
		txtNome.setText("");
		txtCpf.setText("");
		txtCnpj.setText("");
		txtAreaObservacao.setText("");
		cbPessoaTipo.getSelectionModel().selectFirst();
		cbSituacao.getSelectionModel().selectFirst();
		cbClienteTipo.getSelectionModel().selectFirst();
		dtPkCadastro.setValue(LocalDate.now());

		return this;
	}

	private CadClienteController atualizaEntidade() {
		if (cliente == null)
			cliente = new Cliente();

		if (txtId.getText().isEmpty() || txtId.getText().equalsIgnoreCase("0"))
			cliente.setId(Long.valueOf(0));
		else
			cliente.setId(Long.valueOf(txtId.getText()));

		cliente.setNomeSobrenome(txtNome.getText());
		cliente.setDataCadastro(Timestamp.valueOf(dtPkCadastro.getValue().atTime(LocalTime.MIDNIGHT)));
		cliente.setCpf(Utils.removeMascaras(txtCpf.getText()));
		cliente.setCnpj(Utils.removeMascaras(txtCnpj.getText()));
		cliente.setObservacao(txtAreaObservacao.getText());
		cliente.setSituacao(cbSituacao.getSelectionModel().getSelectedItem());
		cliente.setTipoCliente(cbClienteTipo.getSelectionModel().getSelectedItem());
		cliente.setTipoPessoa(cbPessoaTipo.getSelectionModel().getSelectedItem());

		return this;
	}

	// Devido a um erro no componente, caso venha do banco o valor null, estoura
	// erro na edição do campo.
	// Necessário a validação para casos em que ouve problemas com o registro no
	// banco.
	private CadClienteController atualizaTela(Cliente cliente) {
		limpaCampos();

		this.cliente = cliente;

		txtId.setText(cliente.getId().toString());

		if (cliente.getNomeSobrenome() != null && !cliente.getNomeSobrenome().isEmpty())
			txtNome.setText(cliente.getNomeSobrenome());
		if (cliente.getCpf() != null && !cliente.getCpf().isEmpty())
			txtCpf.setText(cliente.getCpf());

		if (cliente.getCnpj() != null && !cliente.getCnpj().isEmpty())
			txtCnpj.setText(cliente.getCnpj());

		if (cliente.getObservacao() != null && !cliente.getObservacao().isEmpty())
			txtAreaObservacao.setText(cliente.getObservacao());

		cbSituacao.getSelectionModel().select(cliente.getSituacao().ordinal());
		cbClienteTipo.getSelectionModel().select(cliente.getTipoCliente().ordinal());
		cbPessoaTipo.getSelectionModel().select(cliente.getTipoPessoa().ordinal());

		return this;
	}

	private void salvar(Cliente cliente) {
		if (clienteService == null)
			setClienteServices(new ClienteServices());

		try {
			clienteService.salvar(cliente);
			Notificacoes.notificacao(AlertType.NONE, "Concluído", "Cliente salvo com sucesso.");
			limpaCampos();
		} catch (ExcessaoBd e) {
			Notificacoes.notificacao(AlertType.ERROR, "Erro", e.getMessage());
		}
	}

	private void excluir(Cliente cliente) {
		if (clienteService == null)
			setClienteServices(new ClienteServices());

		try {
			clienteService.deletar(cliente.getId());
			Notificacoes.notificacao(AlertType.NONE, "Concluído", "Cliente excluído com sucesso.");
			limpaCampos();
		} catch (ExcessaoBd e) {
			Notificacoes.notificacao(AlertType.ERROR, "Erro", e.getMessage());
		}
	}

	private CadClienteController desabilitaBotoes() {
		spRoot.setDisable(true);
		btnConfirmar.setDisable(true);
		btnCancelar.setDisable(true);
		btnExcluir.setDisable(true);
		return this;
	}

	private CadClienteController habilitaBotoes() {
		spRoot.setDisable(false);
		btnConfirmar.setDisable(false);
		btnCancelar.setDisable(false);
		btnExcluir.setDisable(false);
		return this;
	}

	private CadClienteController setClienteServices(ClienteServices clienteService) {
		this.clienteService = clienteService;
		return this;
	}

	private CadClienteController configuraExitId() {
		txtId.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue)
					id = txtId.getText();

				if (oldPropertyValue && !id.equalsIgnoreCase(txtId.getText()))
					onTxtIdExit();
			}
		});

		return this;
	}

	@Override
	public synchronized void initialize(URL arg0, ResourceBundle arg1) {
		inicializaHeranca();
		setClienteServices(new ClienteServices());
		Limitadores.setTextFieldInteger(txtId);

		Validadores.setTextFieldNotEmpty(txtNome);
		Validadores.setTextFieldCpnfCnpjExit(txtCpf);
		Validadores.setTextFieldCpnfCnpjExit(txtCnpj);

		Mascaras.cpfField(txtCpf);
		Mascaras.cnpjField(txtCnpj);

		TecladoUtils.onEnterConfigureTab(txtCpf);
		TecladoUtils.onEnterConfigureTab(txtCnpj);
		TecladoUtils.onEnterConfigureTab(cbSituacao);
		TecladoUtils.onEnterConfigureTab(cbClienteTipo);
		TecladoUtils.onEnterConfigureTab(cbPessoaTipo);

		configuraExitId();

		cbSituacao.getItems().addAll(Situacao.values());
		cbPessoaTipo.getItems().addAll(TipoPessoa.values());
		cbClienteTipo.getItems().addAll(TipoCliente.values());

		limpaCampos();
	}

	public static URL getFxmlLocate() {
		return CadClienteController.class.getResource("/cadastro/view/cadastros/CadCliente.fxml");
	}
}
