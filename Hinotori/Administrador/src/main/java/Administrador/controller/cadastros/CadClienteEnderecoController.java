package Administrador.controller.cadastros;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import Administrador.controller.frame.PesquisaGenericaController;
import Administrador.model.dao.services.BairroServices;
import Administrador.model.dao.services.ClienteEnderecoServices;
import Administrador.model.entities.ClienteEndereco;
import Administrador.model.entities.PesquisaGenericaDados;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import model.constraints.Limitadores;
import model.constraints.Validadores;
import model.enums.Situacao;
import model.mask.Mascaras;
import model.notification.Notificacao;
import model.utils.Utils;

public class CadClienteEnderecoController implements Initializable {

	@FXML
	private JFXTextField txtCliente;

	@FXML
	private TableView<ClienteEndereco> tbEnderecos;

	@FXML
	private TableColumn<ClienteEndereco, String> tbClCidade;

	@FXML
	private TableColumn<ClienteEndereco, String> tbClBairro;

	@FXML
	private TableColumn<ClienteEndereco, String> tbClEndereco;

	@FXML
	private TableColumn<ClienteEndereco, String> tbClNumero;

	@FXML
	private TableColumn<ClienteEndereco, String> tbClCep;

	// Para utilizar o controlador genérico, basta colocar o nome Controller na
	// frente do id do fxml incluido.
	@FXML
	private PesquisaGenericaController frameCidadeController;

	@FXML
	private PesquisaGenericaController frameBairroController;

	@FXML
	private JFXTextField txtEndereco;

	@FXML
	private JFXTextField txtNumero;

	@FXML
	private JFXTextField txtCep;

	@FXML
	private JFXTextField txtComplemento;

	@FXML
	private JFXComboBox<Situacao> cbSituacao;

	@FXML
	private JFXTextArea txtAreaObservacao;

	@FXML
	private Pane paneBackground;

	@FXML
	private ScrollPane background;

	@FXML
	private AnchorPane rootPane;

	@FXML
	private JFXButton btnConfirmar;

	@FXML
	private JFXButton btnNovo;

	@FXML
	private JFXButton btnExcluir;

	@FXML
	private JFXButton btnVoltar;

	private ObservableList<ClienteEndereco> obsEnderecos;
	private List<ClienteEndereco> enderecos;
	private ClienteEndereco selecionado;
	private CadClienteController cadCliente;
	private BairroServices bairroService;
	private ClienteEnderecoServices enderecoService;

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
			atualizaGrid(enderecos);
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
		if (selecionado != null) {
			if (enderecoService == null)
				enderecoService = new ClienteEnderecoServices();

			if (selecionado.getId() != null)
				enderecoService.deletar(selecionado.getId());

			enderecos.remove(selecionado);
			atualizaGrid(enderecos);

			Notificacao.Dark("Processo concluído", "Exclusão concluida com sucesso.", 5.0, Pos.BASELINE_RIGHT);
		} else {
			Notificacao.Dark("Nenhum endereço selecionado",
					"Não foi possível realizar a exclusão, favor selecionar um endereço.", 5.0, Pos.BASELINE_RIGHT);
		}
		limpaCampos();
	}

	@FXML
	public void onBtnVoltarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnVoltar.fire();
		}
	}

	@FXML
	public void onBtnVoltarClick() {
		cadCliente.getCliente().setEnderecos(enderecos);
		cadCliente.loadPrimaryView();
	}

	private Boolean validaCampos() {
		if (!txtEndereco.getText().isEmpty() && !frameBairroController.getID().isEmpty()) {
			txtEndereco.setUnFocusColor(Color.BLACK);
			frameBairroController.txt_Pesquisa.setUnFocusColor(Color.BLACK);
			return true;
		} else {
			if (txtEndereco.getText().isEmpty())
				txtEndereco.setUnFocusColor(Color.RED);

			if (frameBairroController.getID().isEmpty())
				frameBairroController.txt_Pesquisa.setUnFocusColor(Color.RED);
			return false;
		}
	}

	private void limpaCampos() {
		txtEndereco.setText("");
		txtNumero.setText("");
		txtCep.setText("");
		txtComplemento.setText("");
		txtAreaObservacao.setText("");
		cbSituacao.getSelectionModel().selectFirst();
		frameBairroController.limpaCampos();
		selecionado = null;
	}

	private void atualizaEntidade() {
		if (selecionado == null) {
			selecionado = new ClienteEndereco(txtEndereco.getText(), txtNumero.getText(), txtCep.getText(),
					txtComplemento.getText(), txtAreaObservacao.getText(),
					cbSituacao.getSelectionModel().getSelectedItem(),
					bairroService.pesquisar(Long.parseLong(frameBairroController.getID())));
			enderecos.add(selecionado);
		} else {
			selecionado.setEndereco(txtEndereco.getText());
			selecionado.setNumero(txtNumero.getText());
			selecionado.setCep(txtCep.getText());
			selecionado.setComplemento(txtComplemento.getText());
			selecionado.setObservacao(txtAreaObservacao.getText());
			selecionado.setSituacao(cbSituacao.getSelectionModel().getSelectedItem());
			selecionado.setBairro(bairroService.pesquisar(Long.parseLong(frameBairroController.getID())));
			tbEnderecos.refresh();
		}
	}

	private void atualizaTela(ClienteEndereco endereco) {
		selecionado = endereco;
		txtEndereco.setText(selecionado.getEndereco());
		txtNumero.setText(selecionado.getNumero());
		txtCep.setText(Utils.removeMascaras(selecionado.getCep()));
		txtComplemento.setText(selecionado.getComplemento());
		txtAreaObservacao.setText(selecionado.getObservacao());
		cbSituacao.getSelectionModel().select(selecionado.getSituacao().ordinal());
		frameCidadeController.setItemSelecionado(new PesquisaGenericaDados(
				selecionado.getBairro().getCidade().getId().toString(), selecionado.getBairro().getCidade().getNome()
						+ "/" + selecionado.getBairro().getCidade().getEstado().getSigla()));
		frameBairroController.setItemSelecionado(new PesquisaGenericaDados(selecionado.getBairro().getId().toString(),
				selecionado.getBairro().getNome()));
		txtEndereco.requestFocus();
	}

	private void atualizaGrid(List<ClienteEndereco> enderecos) {
		obsEnderecos = FXCollections.observableArrayList(enderecos);
		tbEnderecos.setItems(obsEnderecos);
	}

	private void setBairroServices(BairroServices bairroService) {
		this.bairroService = bairroService;
	}

	public void setCadCliente(CadClienteController cadCliente) {
		this.cadCliente = cadCliente;
		inicializaGrid();
		setCliente();
	}

	private void inicializaGrid() {
		enderecos = cadCliente.getCliente().getEnderecos();
		atualizaGrid(enderecos);
	}

	private void setCliente() {
		txtCliente.setText(cadCliente.getCliente().getNome());
	}

	public void setEnderecos(ObservableList<ClienteEndereco> enderecos) {
		this.enderecos = enderecos;
	}

	private void linkaCelulas() {
		tbClCidade.setCellValueFactory(
				new Callback<CellDataFeatures<ClienteEndereco, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<ClienteEndereco, String> p) {
						if (p.getValue() != null && p.getValue().getBairro() != null
								&& p.getValue().getBairro().getCidade() != null) {
							return new SimpleStringProperty(p.getValue().getBairro().getCidade().getNome() + "/"
									+ p.getValue().getBairro().getCidade().getEstado().getSigla());
						} else {
							return new SimpleStringProperty("");
						}
					}
				});

		tbClBairro.setCellValueFactory(
				new Callback<CellDataFeatures<ClienteEndereco, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<ClienteEndereco, String> p) {
						// p.getValue() returns the PersonType instance for a particular TableView row
						if (p.getValue() != null && p.getValue().getBairro() != null) {
							return new SimpleStringProperty(p.getValue().getBairro().getNome());
						} else {
							return new SimpleStringProperty("");
						}
					}
				});

		tbClEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
		tbClNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
		tbClCep.setCellValueFactory(new PropertyValueFactory<>("cep"));

		tbEnderecos.setRowFactory(tv -> {
			TableRow<ClienteEndereco> row = new TableRow<>() {
			};

			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					ClienteEndereco rowData = row.getItem();
					atualizaTela(rowData);
				}
			});

			return row;
		});
	}

	private void configuraCampos() {
		frameCidadeController.setPesquisa("Id", "Descricao",
				"cidades.Id, CONCAT(cidades.Nome, '/', estados.Sigla) AS Descricao", "cidades",
				"INNER JOIN estados ON cidades.IdEstado = estados.Id", "", "ORDER BY Descricao");

		frameCidadeController.txt_Pesquisa.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue.booleanValue()) {
					if (frameCidadeController.getID() != "")
						frameBairroController.setPesquisa("Id", "Nome", "Id, IdCidade, Nome", "bairros", "",
								" AND IdCidade = " + frameCidadeController.getID(), "ORDER BY IdCidade, Nome");
					else
						frameBairroController.setPesquisa("Id", "Nome", "Id, IdCidade, Nome", "bairros", "", "",
								"ORDER BY IdCidade, Nome");
				}
			}
		});

		frameBairroController.setPesquisa("Id", "Nome", "Id, IdCidade, Nome", "bairros", "", "",
				"ORDER BY IdCidade, Nome");

		frameCidadeController.setTitulo("Pesquisa de cidades");
		frameCidadeController.setIdInvisivel(true);
		frameBairroController.setTitulo("Pesquisa de bairros");

		Limitadores.setTextFieldInteger(txtNumero);
		Mascaras.cepField(txtCep);
		Validadores.setTextFieldNotEmpty(txtEndereco);
		Validadores.setTextFieldNotEmpty(frameBairroController.txt_Pesquisa);

		setBairroServices(new BairroServices());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		background.setFitToHeight(true);
		background.setFitToWidth(true);

		cbSituacao.getItems().addAll(Situacao.values());
		cbSituacao.getSelectionModel().selectFirst();
		configuraCampos();
		linkaCelulas();
	}

}
