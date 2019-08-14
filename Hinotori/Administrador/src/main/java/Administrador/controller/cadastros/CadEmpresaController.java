package Administrador.controller.cadastros;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import Administrador.controller.frame.PesquisaGenericaController;
import Administrador.model.dao.services.EmpresaServices;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.constraints.Limitadores;
import model.enums.Situacao;
import model.mask.Mascaras;

public class CadEmpresaController implements Initializable {

	@FXML
	private JFXTextField txtNomeFantasia;

	@FXML
	private JFXTextField txtRazaoSocial;

	@FXML
	private JFXComboBox<Situacao> cbSituacao;

	@FXML
	private JFXTextField txtCnpj;

	@FXML
	private JFXTextField txtEndereco;

	@FXML
	private JFXTextField txtNumero;

	@FXML
	private JFXTextField txtComplemento;

	@FXML
	private JFXTextField txtCep;

	@FXML
	private Pane paneBackground;

	@FXML
	private ScrollPane background;

	@FXML
	private AnchorPane frameCidade;

	@FXML
	private AnchorPane frameBairro;

	// Para utilizar o controlador genérico, basta colocar o nome Controller na
	// frente do id do fxml incluido.
	@FXML
	private PesquisaGenericaController frameCidadeController;

	@FXML
	private PesquisaGenericaController frameBairroController;

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

	private EmpresaServices empresaServices;
	private Boolean edicao;

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
	public void onBtnNovoEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnNovo.fire();
		}
	}

	@FXML
	public void onBtnNovoClick() {

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

		Mascaras.cnpjField(txtCnpj);
		Mascaras.cepField(txtCep);
		Limitadores.setTextFieldInteger(txtNumero);
		Limitadores.setTextFieldCep(txtCep);

		setEmpresaServices(new EmpresaServices());
	}

	private void setEmpresaServices(EmpresaServices empresaServices) {
		this.empresaServices = empresaServices;
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		background.setFitToHeight(true);
		background.setFitToWidth(true);
		configuraCampos();
		edicao = false;
	}

}
