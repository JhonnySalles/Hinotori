package cadastro.controller.cadastros;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import cadastro.controller.lista.ListaContatoController;
import cadastro.controller.lista.ListaEnderecoController;
import comum.form.CadastroFormPadrao;
import comum.model.constraints.Limitadores;
import comum.model.constraints.TecladoUtils;
import comum.model.constraints.Validadores;
import comum.model.enums.Enquadramento;
import comum.model.enums.PessoaTipo;
import comum.model.enums.Situacao;
import comum.model.exceptions.ExcessaoCadastro;
import comum.model.mask.Mascaras;
import comum.model.messages.Mensagens;
import comum.model.notification.Notificacoes;
import comum.model.utils.Utils;
import comum.model.utils.ViewGerenciador;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import servidor.entities.Cliente;
import servidor.validations.ValidaCliente;

public class CadClienteController extends CadastroFormPadrao {

	private final static Logger LOGGER = Logger.getLogger(CadUsuarioController.class.getName());

	@FXML
	private JFXTextField txtId;

	@FXML
	private JFXTextField txtNome;

	@FXML
	private JFXTextField txtRazaoSocial;

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
	private JFXComboBox<Enquadramento> cbEnquadramento;

	@FXML
	private JFXComboBox<PessoaTipo> cbPessoaTipo;

	@FXML
	private JFXButton btnContato;

	@FXML
	private JFXButton btnEndereco;

	private Cliente cliente;

	@Override
	public void onBtnConfirmarClick() {
		atualizaEntidade();
		if (validaCampos()) {
			try {
				spRoot.cursorProperty().set(Cursor.WAIT);
				desabilitaBotoes().salvar(cliente);
			} finally {
				spRoot.cursorProperty().set(null);
				habilitaBotoes();
			}
		}
	}

	@Override
	public void onBtnCancelarClick() {
		limpaCampos();
	}

	@Override
	public void onBtnExcluirClick() {
		if ((cliente.getId() == null) || (cliente.getId() == 0) || txtId.getText().isEmpty()
				|| txtId.getText().equalsIgnoreCase("0"))
			Notificacoes.notificacao(AlertType.INFORMATION, Mensagens.AVISO,
					Mensagens.CADASTRO_EXCLUIR + " Nenhum cliente selecionado.");
		else {
			try {
				spRoot.cursorProperty().set(Cursor.WAIT);
				desabilitaBotoes().atualizaEntidade().excluir(cliente);
				Notificacoes.notificacao(AlertType.NONE, Mensagens.CONCLUIDO, "Cliente excluído com sucesso.");
			} finally {
				spRoot.cursorProperty().set(null);
				habilitaBotoes();
			}
		}
	}

	@Override
	public void onBtnVoltarClick() {
		ViewGerenciador.closeTela(spRoot);
		onClose();
	}

	@FXML
	public void onTxtIdClick() {
		txtId.getSelectedText();
	}

	@FXML
	public void onTxtIdEnter(KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER)) {
			if (!txtId.getText().equalsIgnoreCase("0") && !txtId.getText().isEmpty())
				onTxtIdExit();
			else
				limpaCampos();

			Utils.clickTab();
		}
	}

	@FXML
	public void onBtnEnderecoEnter(KeyEvent e) {
		if (e.getCode().equals(KeyCode.ENTER))
			btnEndereco.fire();
	}

	@FXML
	public void onBtnEnderecoClick() {
		ListaEnderecoController ctn = (ListaEnderecoController) ViewGerenciador
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
	public void onBtnContatoClick() {
		ListaContatoController ctn = (ListaContatoController) ViewGerenciador
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
		if (!txtId.getText().isEmpty() && !txtId.getText().equalsIgnoreCase("0"))
			carregar(pesquisar(new Cliente(Long.valueOf(txtId.getText()))));
		else if (txtId.getText().isEmpty() || txtId.getText().equalsIgnoreCase("0"))
			limpaCampos();
	}

	@Override
	protected <T> void salvar(T entidade) {
		/*
		 * try {
		 * 
		 * Notificacoes.notificacao(AlertType.NONE, Mensagens.CONCLUIDO,
		 * "Cliente salvo com sucesso."); limpaCampos(); } catch (ExcessaoBd e) {
		 * Notificacoes.notificacao(AlertType.ERROR, Mensagens.ERRO, e.getMessage());
		 * LOGGER.log(Level.INFO, "{Erro ao salvar o cliente}", e); }
		 */
	}

	@Override
	protected <T> void excluir(T entidade) {
		/*
		 * try { Notificacoes.notificacao(AlertType.NONE, Mensagens.CONCLUIDO,
		 * "Cliente excluído com sucesso."); limpaCampos(); } catch (ExcessaoBd e) {
		 * Notificacoes.notificacao(AlertType.ERROR, Mensagens.ERRO, e.getMessage());
		 * LOGGER.log(Level.INFO, "{Erro ao excluir o cliente}", e); }
		 */
	}

	@Override
	public <T> void carregar(T entidade) {
		if (entidade == null)
			limpaCampos();
		else
			atualizaTela((Cliente) entidade);
	}

	@Override
	protected <T> T pesquisar(T entidade) {
		/*
		 * try {
		 * 
		 * } catch (ExcessaoBd e) { e.printStackTrace(); LOGGER.log(Level.INFO,
		 * "{Erro ao pesquisar o cliente}", e); }
		 */
		return entidade;
	}

	@Override
	protected boolean validaCampos() {
		try {
			return ValidaCliente.validaCliente(cliente);
		} catch (ExcessaoCadastro e) {
			e.printStackTrace();
		}

		txtNome.validate();
		txtCpf.validate();
		txtCnpj.validate();
		txtRazaoSocial.validate();

		Notificacoes.notificacao(AlertType.INFORMATION, Mensagens.AVISO, Mensagens.CADASTRO_SALVAR);
		return false;
	}

	@Override
	protected void limpaCampos() {
		txtId.setText("0");
		txtNome.setText("");
		txtRazaoSocial.setText("");
		txtCpf.setText("");
		txtCnpj.setText("");
		txtAreaObservacao.setText("");
		cbPessoaTipo.getSelectionModel().selectFirst();
		cbSituacao.getSelectionModel().selectFirst();
		cbEnquadramento.getSelectionModel().selectFirst();
		dtPkCadastro.setValue(LocalDate.now());
	}

	@Override
	public CadClienteController atualizaEntidade() {
		cliente = new Cliente(Long.valueOf(txtId.getText()), txtNome.getText(), txtRazaoSocial.getText(),
				Utils.removeMascaras(txtCpf.getText()), Utils.removeMascaras(txtCnpj.getText()),
				txtAreaObservacao.getText(), cbPessoaTipo.getSelectionModel().getSelectedItem(),
				cbEnquadramento.getSelectionModel().getSelectedItem(),
				cbSituacao.getSelectionModel().getSelectedItem());

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
		cbEnquadramento.getSelectionModel().select(cliente.getPessoaTipo().ordinal());
		cbPessoaTipo.getSelectionModel().select(cliente.getEnquadramento().ordinal());

		return this;
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

	public Cliente getCliente() {
		return cliente;
	}

	private CadClienteController configuraExitCampos() {
		cbPessoaTipo.valueProperty().addListener(new ChangeListener<PessoaTipo>() {
			@Override
			public void changed(ObservableValue<? extends PessoaTipo> observable, PessoaTipo oldValue,
					PessoaTipo newValue) {
				if (newValue != null)
					txtRazaoSocial.setDisable(newValue.equals(PessoaTipo.FISICO));
			}
		});

		return this;
	}

	@Override
	public synchronized void inicializa(URL arg0, ResourceBundle arg1) {
		Limitadores.setTextFieldInteger(txtId);

		Validadores.setTextFieldNotEmpty(txtNome);
		Validadores.setTextFieldCPFValidate(txtCpf);
		Validadores.setTextFieldCNPJValidate(txtCnpj);
		// Validadores.setTextFieldChangeBlack(txtRazaoSocial);

		Mascaras.cpfField(txtCpf);
		Mascaras.cnpjField(txtCnpj);

		TecladoUtils.onEnterConfigureTab(txtCpf);
		TecladoUtils.onEnterConfigureTab(txtCnpj);
		TecladoUtils.onEnterConfigureTab(txtRazaoSocial);
		TecladoUtils.onEnterConfigureTab(cbSituacao);
		TecladoUtils.onEnterConfigureTab(cbEnquadramento);
		TecladoUtils.onEnterConfigureTab(cbPessoaTipo);

		configuraExitCampos();

		cbSituacao.getItems().addAll(Situacao.values());
		cbPessoaTipo.getItems().addAll(PessoaTipo.values());
		cbEnquadramento.getItems().addAll(Enquadramento.values());

		limpaCampos();

	}

	public static URL getFxmlLocate() {
		return CadClienteController.class.getResource("/cadastro/view/cadastros/CadCliente.fxml");
	}

}
