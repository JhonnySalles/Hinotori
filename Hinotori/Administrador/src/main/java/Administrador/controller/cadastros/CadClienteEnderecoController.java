package Administrador.controller.cadastros;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import Administrador.controller.frame.PesquisaGenericaController;
import Administrador.model.entities.ClienteEndereco;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class CadClienteEnderecoController implements Initializable {

	@FXML
	private String Codigo;

	@FXML
	private JFXTextField txtCliente;

	@FXML
	private JFXTextField txtEndereco;

	@FXML
	private JFXTextField txtNumero;

	@FXML
	private JFXTextField txtComplemento;

	@FXML
	private JFXTextField txtCep;

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
	private JFXButton btnConfirmar;

	@FXML
	private JFXButton btnNovo;

	@FXML
	private JFXButton btnExcluir;

	@FXML
	private JFXButton btnVoltar;

	private List<ClienteEndereco> enderecos;
	private ClienteEndereco endereco;

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
		if (!txtEndereco.getText().isEmpty()) {
			txtEndereco.setUnFocusColor(Color.RED);
			return false;
		} else
			return true;
	}

	private void limpaCampos() {
		txtEndereco.setText("");
		txtNumero.setText("");
		txtComplemento.setText("");
		txtCep.setText("");
	}

	private void atualizaEntidade() {
		endereco.setEndereco(txtEndereco.getText());
		endereco.setNumero(txtNumero.getText());
		endereco.setComplemento(txtComplemento.getText());
		endereco.setCep(txtCep.getText());
	}

	private void atualizaTela() {
		txtEndereco.setText(endereco.getEndereco());
		txtNumero.setText(endereco.getNumero());
		txtComplemento.setText(endereco.getComplemento());
		txtCep.setText(endereco.getCep());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		frameCidadeController.setPesquisa("Id", "Descricao",
				"cidades.Id, CONCAT(cidades.Nome, '/', estados.Sigla) AS Descricao", "cidades",
				"INNER JOIN estados ON cidades.Id_Estado = estados.Id", "", "ORDER BY Descricao");

		frameCidadeController.txt_Pesquisa.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue.booleanValue()) {
					String idCidade = frameCidadeController.getID();
					if (idCidade != "")
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
	}

}
