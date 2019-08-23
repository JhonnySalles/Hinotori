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
import Administrador.model.dao.services.ClienteEnderecoServices;
import Administrador.model.entities.ClienteEndereco;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.constraints.Limitadores;
import model.enums.Situacao;
import model.mask.Mascaras;

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

	private List<ClienteEndereco> enderecos;
	private CadClienteController cadCliente;
	private ClienteEnderecoServices enderecoServices;

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
		cadCliente.loadPrimaryView();
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
		frameCidadeController.setPesquisa("Id", "Descricao",
				"cidades.Id, CONCAT(cidades.Nome, '/', estados.Sigla) AS Descricao", "cidades",
				"INNER JOIN estados ON cidades.Id_Estado = estados.Id", "", "ORDER BY Descricao");

		frameCidadeController.txt_Pesquisa.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue.booleanValue()) {
					if (frameCidadeController.getID() != "")
						frameBairroController.setPesquisa("Id", "Nome", "Id, Id_Cidade, Nome", "bairros", "",
								" AND Id_Cidade = " + frameCidadeController.getID(), "ORDER BY Id_Cidade, Nome");
					else
						frameBairroController.setPesquisa("Id", "Nome", "Id, Id_Cidade, Nome", "bairros", "", "",
								"ORDER BY Id_Cidade, Nome");
				}
			}
		});

		frameBairroController.setPesquisa("Id", "Nome", "Id, Id_Cidade, Nome", "bairros", "", "",
				"ORDER BY Id_Cidade, Nome");

		frameCidadeController.txt_Pesquisa.setPromptText("Pesquisa de cidades");
		frameBairroController.txt_Pesquisa.setPromptText("Pesquisa de bairros");

		Limitadores.setTextFieldInteger(txtNumero);
		Mascaras.cepField(txtCep);
		Limitadores.setTextFieldCep(txtCep);

		setClienteEnderecoServices(new ClienteEnderecoServices());
	}

	private void setClienteEnderecoServices(ClienteEnderecoServices enderecoServices) {
		this.enderecoServices = enderecoServices;
	}

	public CadClienteController getCadCliente() {
		return cadCliente;
	}

	public void setCadCliente(CadClienteController cadCliente) {
		this.cadCliente = cadCliente;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		background.setFitToHeight(true);
		background.setFitToWidth(true);
		configuraCampos();

	}

}
