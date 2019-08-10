package Administrador.controller.cadastros;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import Administrador.controller.frame.PesquisaGenericaController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import model.alerts.Alertas;
import model.constraints.Limitadores;
import model.mask.Mascaras;

public class CadClienteController implements Initializable {

	@FXML
	private JFXTextField txtCodigo;

	@FXML
	private JFXTextField txtNome;

	@FXML
	private JFXTextField txtSobreNome;

	@FXML
	private JFXTextField txtEndereco;

	@FXML
	private JFXTextField txtNumero;

	@FXML
	private JFXTextField txtComplemento;

	@FXML
	private JFXTextField txtCep;

	@FXML
	private JFXButton btnPsqCidades;

	@FXML
	private JFXTextField txtCidade;

	@FXML
	private PesquisaGenericaController frame_cidade;

	@FXML
	private PesquisaGenericaController frame_bairro;

	@FXML
	private JFXTextField txtTelefone;

	@FXML
	private JFXTextField txtCelular;

	@FXML
	private Pane paneBackground;

	@FXML
	private ScrollPane background;

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
		if (Alertas.Confirmacao("Sair", "Deseja realmente cancelar? \nToda a alteração será descartada.")) {
			System.exit(0);
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

	public void initialize(URL arg0, ResourceBundle arg1) {

		frame_cidade.setPesquisa("Id", "Descricao", "cidades.Id, CONCAT(cidades.Nome, '/', estados.Sigla) AS Descricao",
				"cidades", "INNER JOIN estados ON cidades.Id_Estado = estados.Id", "", "ORDER BY Descricao");

		frame_bairro.setPesquisa("Id", "Nome", "Id, Id_Cidade, Nome", "bairros", "", "", "Id_Cidade, Nome");
		;

		background.setFitToHeight(true);
		background.setFitToWidth(true);

		Limitadores.setTextFieldID(txtCodigo, 11);
		Limitadores.setTextFieldInteger(txtNumero);

		Mascaras.cepField(txtCep);
		Mascaras.foneField(txtTelefone);
		Mascaras.foneField(txtCelular);

	}
}
