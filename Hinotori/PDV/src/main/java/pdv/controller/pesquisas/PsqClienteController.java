package pdv.controller.pesquisas;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import comum.model.constraints.Limitadores;
import comum.model.constraints.TecladoUtils;
import comum.model.enums.Situacao;
import comum.model.enums.TipoCliente;
import comum.model.enums.TipoPessoa;
import comum.model.exceptions.ExcessaoBd;
import comum.model.mask.ConverterMascaras;
import comum.model.mask.Mascaras;
import comum.model.utils.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import pdv.App;
import servidor.dao.services.ClienteServices;
import servidor.entities.Cliente;
import servidor.entities.Contato;
import servidor.entities.Endereco;

public class PsqClienteController implements Initializable {

	private Map<KeyCodeCombination, Runnable> atalhosTecla = new HashMap<>();

	@FXML
	private StackPane spRoot;

	@FXML
	private ScrollPane background;

	@FXML
	private HBox titulo;

	@FXML
	private HBox fundoBotoes;

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
	private TableColumn<Cliente, String> tbClDataCadastro;

	@FXML
	private TableColumn<Cliente, String> tbClContatoPadrao;
	
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
	private ObservableList<Cliente> obsClientes;
	private FilteredList<Cliente> filteredData;
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
		try {
			spRoot.cursorProperty().set(Cursor.WAIT);
			carregarClientes();
			// Necessário por um bug na tela ao carregar ela.
			App.getMainController().atualizaTabPane();
		} catch (ExcessaoBd e) {
			e.printStackTrace();
		} finally {
			spRoot.cursorProperty().set(null);
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

	}

	@FXML
	public void onBtnLimparClick() {
		txtIdInicial.setText("0");
		txtIdFinal.setText("0");
		txtNome.setText("");
		txtCpf.setText("");
		txtCnpj.setText("");
		dtPkCadastroInicial.setValue(null);
		dtPkCadastroFinal.setValue(null);
		cbPessoaTipo.getSelectionModel().clearSelection();
		cbClienteTipo.getSelectionModel().clearSelection();
		cbSituacao.getSelectionModel().clearSelection();
		if (filteredData != null)
			filteredData.setPredicate(null);
		App.getMainController().atualizaTabPane();
	}

	public PsqClienteController carregarClientes(List<Cliente> clientes) {
		this.clientes = clientes;
		return this;
	}

	public PsqClienteController carregarClientes() throws ExcessaoBd {
		this.clientes = clienteService.pesquisarTodos();
		obsClientes = FXCollections.observableArrayList(this.clientes);
		tbClientes.setItems(obsClientes);
		tbClientes.refresh();
		configuraGrid();
		// Necessário por um bug na tela ao carregar ela.
		App.getMainController().atualizaTabPane();
		return this;
	}

	private Boolean validaPredicate(Cliente obj) {
		if ((txtIdInicial.getText().isEmpty() || obj.getId() >= Long.valueOf(txtIdInicial.getText()))
				&& (txtIdFinal.getText().isEmpty() || obj.getId() <= Long.valueOf(txtIdFinal.getText()))
				&& (txtNome.getText().isEmpty()
						|| obj.getNomeSobrenome().toString().toLowerCase().contains(txtNome.getText().toLowerCase()))
				&& (txtCnpj.getText().isEmpty() || obj.getCnpj().contains(Utils.removeMascaras(txtCnpj.getText())))
				&& (txtCpf.getText().isEmpty() || obj.getCpf().contains(Utils.removeMascaras(txtCpf.getText())))

				&& (dtPkCadastroInicial.getValue() == null || obj.getDataCadastro()
						.after(Timestamp.valueOf(dtPkCadastroInicial.getValue().atStartOfDay())))
				&& (dtPkCadastroFinal.getValue() == null
						|| obj.getDataCadastro().after(Timestamp.valueOf(dtPkCadastroFinal.getValue().atStartOfDay())))

				&& (cbPessoaTipo.getSelectionModel().getSelectedIndex() < 0
						|| obj.getTipoPessoa() == cbPessoaTipo.getSelectionModel().getSelectedItem())
				&& (cbClienteTipo.getSelectionModel().getSelectedIndex() < 0
						|| obj.getTipoCliente() == cbClienteTipo.getSelectionModel().getSelectedItem())
				&& (cbSituacao.getSelectionModel().getSelectedIndex() < 0
						|| obj.getSituacao() == cbSituacao.getSelectionModel().getSelectedItem()))
			return true;
		else
			return false;

	}

	private PsqClienteController configuraGrid() {
		filteredData = new FilteredList<>(obsClientes, p -> true);

		txtIdInicial.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		txtIdFinal.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		txtNome.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		txtCnpj.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		txtCpf.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		dtPkCadastroInicial.valueProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		dtPkCadastroFinal.valueProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		cbPessoaTipo.setOnAction(filtrar -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		cbClienteTipo.setOnAction(filtrar -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		cbSituacao.setOnAction(filtrar -> {
			filteredData.setPredicate(person -> validaPredicate(person));
		});

		SortedList<Cliente> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tbClientes.comparatorProperty());
		tbClientes.setItems(sortedData);

		return this;
	}

	/*
	 * private PsqClienteController configuraGrid() { FilteredList<Cliente>
	 * filteredData = new FilteredList<>(obsClientes, p -> true);
	 * 
	 * txtIdInicial.textProperty().addListener((observable, oldValue, newValue) -> {
	 * filteredData.setPredicate(person -> { if (newValue == null ||
	 * newValue.isEmpty()) { return true; }
	 * 
	 * if (person.getId() >= Long.valueOf(newValue)) return true; else return false;
	 * }); });
	 * 
	 * txtIdFinal.textProperty().addListener((observable, oldValue, newValue) -> {
	 * filteredData.setPredicate(person -> { if (newValue == null ||
	 * newValue.isEmpty()) { return true; }
	 * 
	 * if (person.getId() <= Long.valueOf(newValue)) return true; else return false;
	 * }); });
	 * 
	 * txtNome.textProperty().addListener((observable, oldValue, newValue) -> {
	 * filteredData.setPredicate(person -> { if (newValue == null ||
	 * newValue.isEmpty()) { return true; }
	 * 
	 * if (person.getNomeSobrenome().toString().toLowerCase().contains(newValue.
	 * toLowerCase())) return true; else return false; }); });
	 * 
	 * txtCnpj.textProperty().addListener((observable, oldValue, newValue) -> {
	 * filteredData.setPredicate(person -> { if (newValue == null ||
	 * newValue.isEmpty()) { return true; }
	 * 
	 * if (person.getCnpj().toString().toLowerCase().contains(Utils.removeMascaras(
	 * newValue.toLowerCase()))) return true; else return false; }); });
	 * 
	 * txtCpf.textProperty().addListener((observable, oldValue, newValue) -> {
	 * filteredData.setPredicate(person -> { if (newValue == null ||
	 * newValue.isEmpty()) { return true; }
	 * 
	 * if (person.getCpf().toString().toLowerCase().contains(Utils.removeMascaras(
	 * newValue.toLowerCase()))) return true; else return false; }); });
	 * 
	 * dtPkCadastroInicial.valueProperty().addListener((observable, oldValue,
	 * newValue) -> { filteredData.setPredicate(person -> { if (newValue == null) {
	 * return true; }
	 * 
	 * Timestamp timestamp = Timestamp.valueOf(newValue.atStartOfDay()); if
	 * (person.getDataCadastro().after(timestamp)) return true; else return false;
	 * });
	 * 
	 * });
	 * 
	 * dtPkCadastroFinal.valueProperty().addListener((observable, oldValue,
	 * newValue) -> { filteredData.setPredicate(person -> { if (newValue == null) {
	 * return true; }
	 * 
	 * Timestamp timestamp = Timestamp.valueOf(newValue.atStartOfDay()); if
	 * (person.getDataCadastro().before(timestamp)) { return true; } else return
	 * false; });
	 * 
	 * });
	 * 
	 * cbPessoaTipo.setOnAction(filtrar -> { filteredData.setPredicate(person -> {
	 * if (cbPessoaTipo.getSelectionModel().getSelectedIndex() > 0) { if
	 * (person.getSituacao().equals(cbPessoaTipo.getSelectionModel().getSelectedItem
	 * ())) return true; else return false; } else return true; });
	 * 
	 * });
	 * 
	 * cbClienteTipo.setOnAction(filtrar -> { filteredData.setPredicate(person -> {
	 * if (cbClienteTipo.getSelectionModel().getSelectedIndex() > 0) { if
	 * (person.getSituacao().equals(cbClienteTipo.getSelectionModel().
	 * getSelectedItem())) return true; else return false; } else return true; });
	 * 
	 * });
	 * 
	 * cbSituacao.setOnAction(filtrar -> { filteredData.setPredicate(person -> { if
	 * (cbSituacao.getSelectionModel().getSelectedIndex() > 0) { if
	 * (person.getSituacao().equals(cbSituacao.getSelectionModel().getSelectedItem()
	 * )) return true; else return false; } else return true; });
	 * 
	 * });
	 * 
	 * SortedList<Cliente> sortedData = new SortedList<>(filteredData);
	 * sortedData.comparatorProperty().bind(tbClientes.comparatorProperty());
	 * tbClientes.setItems(sortedData);
	 * 
	 * return this; }
	 */

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

	// Função responsável pela transição de opacidade do fundo do cadastro e dos
	// botões.
	private double initY = -1;
	private final Scale scale = new Scale(1, 1, 0, 0);
	private Transform oldSceneTransform = null;

	private PsqClienteController configureScroll() {

		fundoBotoes.localToSceneTransformProperty().addListener((o, oldVal, newVal) -> oldSceneTransform = oldVal);
		background.vvalueProperty().addListener((o, oldVal, newVal) -> {
			if (initY == -1) {
				initY = oldSceneTransform.getTy();
			}

			// translation
			double ty = rootCliente.getLocalToSceneTransform().getTy();
			double opacity = Math.abs(ty - initY) / 100;
			opacity = opacity > 1 ? 1 : (opacity < 0) ? 0 : opacity;

			titulo.setOpacity(1 - opacity);
			fundoBotoes.setOpacity(opacity);

			// scale
			scale.setX(map(opacity, 0, 1, 1, 0.75));
			scale.setY(map(opacity, 0, 1, 1, 0.75));
		});
		return this;
	}

	private double map(double val, double min1, double max1, double min2, double max2) {
		return min2 + (max2 - min2) * ((val - min1) / (max1 - min1));
	}

	private PsqClienteController linkaCelulas() {
		tbClId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tbClNome.setCellValueFactory(new PropertyValueFactory<>("nomeSobrenome"));

		tbClCpf.setCellValueFactory(data -> {
			return new SimpleStringProperty(ConverterMascaras.formataCPF(data.getValue().getCpf()));
		});

		tbClCnpj.setCellValueFactory(data -> {
			return new SimpleStringProperty(ConverterMascaras.formataCNPJ(data.getValue().getCnpj()));
		});

		tbClDataCadastro.setCellValueFactory(data -> {
			SimpleStringProperty property = new SimpleStringProperty();
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			property.setValue(dateFormat.format(data.getValue().getDataCadastro()));
			return property;
		});
		
		tbClContatoPadrao.setCellValueFactory(data -> {
			List<Contato> psq = data.getValue().getContatos().stream().filter(item -> item.isPadrao())
					.collect(Collectors.toList());
			SimpleStringProperty contato = new SimpleStringProperty();
			if (psq != null && psq.size() > 0)
				contato.setValue(ConverterMascaras.formataFone(psq.get(0).getTelefone()) + " / " + ConverterMascaras.formataFone(psq.get(0).getCelular()));
			else
				contato.setValue("");
			
			return contato;
		});

		tbClEnderecoPadrao.setCellValueFactory(data -> {
			List<Endereco> psq = data.getValue().getEnderecos().stream().filter(item -> item.isPadrao())
					.collect(Collectors.toList());
			SimpleStringProperty endereco = new SimpleStringProperty();
			if (psq != null && psq.size() > 0)
				endereco.setValue(psq.get(0).getEndereco() + ", " + psq.get(0).getNumero());
			else
				endereco.setValue("");

			return endereco;
		});

		return this;
	}

	@Override
	public synchronized void initialize(URL arg0, ResourceBundle arg1) {
		setClienteServices(new ClienteServices());

		Limitadores.setTextFieldInteger(txtIdInicial);
		Limitadores.setTextFieldInteger(txtIdFinal);

		Mascaras.cpfField(txtCpf);
		Mascaras.cnpjField(txtCnpj);

		TecladoUtils.onEnterConfigureTab(txtCpf);
		TecladoUtils.onEnterConfigureTab(txtCnpj);
		TecladoUtils.onEnterConfigureTab(cbSituacao);
		TecladoUtils.onEnterConfigureTab(cbClienteTipo);
		TecladoUtils.onEnterConfigureTab(cbPessoaTipo);

		configuraAtalhosTeclado().configureScroll();
		linkaCelulas();

		cbSituacao.getItems().addAll(Situacao.values());
		cbPessoaTipo.getItems().addAll(TipoPessoa.values());
		cbClienteTipo.getItems().addAll(TipoCliente.values());
	}
}
