package pdv.controller.pesquisas;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import comum.model.constraints.Limitadores;
import comum.model.constraints.TecladoUtils;
import comum.model.constraints.Validadores;
import comum.model.enums.Situacao;
import comum.model.enums.TipoCliente;
import comum.model.enums.TipoPessoa;
import comum.model.mask.Mascaras;
import comum.model.utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import pdv.model.dao.services.ClienteServices;
import pdv.model.entities.Cliente;

public class PsqClienteController implements Initializable {

	private Map<KeyCodeCombination, Runnable> atalhosTecla = new HashMap<>();

	@FXML
	private StackPane spRoot;

	@FXML
	private ScrollPane background;

	@FXML
	private AnchorPane rootCliente;

	@FXML
	private JFXTextField txtIdInicial;

	@FXML
	private JFXTextField txtIdFinal;

	@FXML
	private JFXTextField txtNome;

	@FXML
	private JFXTextField txtCpf;

	@FXML
	private JFXTextField txtCnpj;

	@FXML
	private JFXDatePicker dtPkCadastroInicial;

	@FXML
	private JFXDatePicker dtPkCadastroFinal;

	@FXML
	private JFXComboBox<Situacao> cbSituacao;

	@FXML
	private JFXComboBox<TipoCliente> cbClienteTipo;

	@FXML
	private JFXComboBox<TipoPessoa> cbPessoaTipo;

	@FXML
	private TableView<Cliente> tbClientes;

	@FXML
	private TableColumn<Cliente, String> tbClId;

	@FXML
	private TableColumn<Cliente, String> tbClNome;

	@FXML
	private TableColumn<Cliente, String> tbClCpf;

	@FXML
	private TableColumn<Cliente, String> tbClCnpj;

	@FXML
	private TableColumn<Cliente, LocalDate> tbClDataCadastro;

	@FXML
	private TableColumn<Cliente, String> tbClEnderecoPadrao;

	@FXML
	private JFXButton btnAtualizar;

	@FXML
	private JFXButton btnConfirmar;

	@FXML
	private JFXButton btnCancelar;

	@FXML
	private JFXButton btnVoltar;

	private List<Cliente> clientes;
	private ClienteServices clienteService;

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

	}

	@FXML
	public void onBtnAtualizarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnAtualizar.fire();
		}
	}

	@FXML
	public void onBtnAtualizarClick() {

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

	@FXML
	public void onTxtIdClick() {

	}

	@FXML
	public void onTxtIdEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {

			Utils.clickTab();
		}
	}

	public void onTxtIdExit() {

	}

	public PsqClienteController carregarClientes(List<Cliente> clientes) {
		this.clientes = clientes;
		return this;
	}

	private PsqClienteController desabilitaBotoes() {
		spRoot.setDisable(true);
		btnConfirmar.setDisable(true);
		btnCancelar.setDisable(true);
		btnAtualizar.setDisable(true);
		btnVoltar.setDisable(true);
		return this;
	}

	private PsqClienteController habilitaBotoes() {
		spRoot.setDisable(false);
		btnConfirmar.setDisable(false);
		btnCancelar.setDisable(false);
		btnAtualizar.setDisable(false);
		btnVoltar.setDisable(false);
		return this;
	}

	// Será necessário verificar uma forma de configurar o scene após a exibição,
	// pois é ele que adiciona os atalhos do teclado, porém na construção a scene
	// não existe, somente na exibição.
	public void ativaAtalhos() {
		rootCliente.getScene().getAccelerators().clear();
		rootCliente.getScene().getAccelerators().putAll(atalhosTecla);
	}

	private PsqClienteController configuraAtalhosTeclado() {
		atalhosTecla.put(new KeyCodeCombination(KeyCode.F2), new Runnable() {
			@FXML
			public void run() {
				btnConfirmar.fire();
			}
		});
		atalhosTecla.put(new KeyCodeCombination(KeyCode.F3), new Runnable() {
			@FXML
			public void run() {
				btnCancelar.fire();
			}
		});
		atalhosTecla.put(new KeyCodeCombination(KeyCode.BACK_SPACE), new Runnable() {
			@FXML
			public void run() {
				btnVoltar.fire();
			}
		});
		atalhosTecla.put(new KeyCodeCombination(KeyCode.BACK_SPACE), new Runnable() {
			@FXML
			public void run() {
				btnVoltar.fire();
			}
		});
		atalhosTecla.put(new KeyCodeCombination(KeyCode.F5), new Runnable() {
			@FXML
			public void run() {
				btnAtualizar.fire();
			}
		});
		return this;
	}

	private PsqClienteController setClienteServices(ClienteServices clienteService) {
		this.clienteService = clienteService;
		return this;
	}

	@Override
	public synchronized void initialize(URL arg0, ResourceBundle arg1) {
		setClienteServices(new ClienteServices());

		Limitadores.setTextFieldInteger(txtIdInicial);
		Limitadores.setTextFieldInteger(txtIdFinal);

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

		configuraAtalhosTeclado();

		cbSituacao.getItems().addAll(Situacao.values());
		cbPessoaTipo.getItems().addAll(TipoPessoa.values());
		cbClienteTipo.getItems().addAll(TipoCliente.values());

		dtPkCadastroInicial.setValue(LocalDate.now());
		dtPkCadastroFinal.setValue(LocalDate.now());
	}
}
