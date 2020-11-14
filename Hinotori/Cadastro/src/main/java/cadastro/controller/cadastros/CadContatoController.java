package cadastro.controller.cadastros;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import comum.model.constraints.TecladoUtils;
import comum.model.constraints.Validadores;
import comum.model.enums.Situacao;
import comum.model.enums.TipoContato;
import comum.model.mask.Mascaras;
import comum.model.utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import servidor.entities.Contato;

public class CadContatoController implements Initializable {

	@FXML
	private AnchorPane apRoot;

	@FXML
	private JFXComboBox<TipoContato> cbTipo;

	@FXML
	private JFXComboBox<Situacao> cbSituacao;

	@FXML
	private JFXTextField txtNome;

	@FXML
	private JFXTextField txtTelefone;

	@FXML
	private JFXTextField txtCelular;

	@FXML
	private JFXTextField txtEmail;

	@FXML
	private JFXTextArea txtAreaObservacao;

	@FXML
	private JFXButton btnConfirmar;

	@FXML
	private JFXButton btnCancelar;

	private Set<Contato> contatos;
	private Contato contato;

	@FXML
	public void onBtnConfirmarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnConfirmar.fire();
		}
	}

	@FXML
	public void onBtnConfirmarClick() {
		if (validaCampos()) {
			atualizaEntidade().salvar(contato);
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

	private Boolean validaCampos() {
		Boolean valida = true;

		if (txtNome.getText().isEmpty()) {
			txtNome.setUnFocusColor(Color.RED);
			valida = false;
		}

		if (!Validadores.validaEmail(txtEmail.getText())) {
			txtEmail.setUnFocusColor(Color.RED);
			valida = false;
		}

		return valida;
	}

	private void salvar(Contato contato) {
		if (contatos == null)
			contatos = new HashSet<>();

		if (!contatos.contains(contato))
			contatos.add(contato);

		limpaCampos();
	}

	public Set<Contato> getContato() {
		return contatos;
	}

	private CadContatoController atualizaEntidade() {
		if (contato == null)
			contato = new Contato();

		contato.setNome(txtNome.getText());
		contato.setTelefone(Utils.removeMascaras(txtTelefone.getText()));
		contato.setCelular(Utils.removeMascaras(txtCelular.getText()));
		contato.setEmail(txtEmail.getText());
		contato.setObservacao(txtAreaObservacao.getText());
		contato.setSituacao(cbSituacao.getSelectionModel().getSelectedItem());
		contato.setTipoContato(cbTipo.getSelectionModel().getSelectedItem());

		return this;
	}

	public CadContatoController limpaCampos() {
		contato = new Contato();

		cbTipo.getSelectionModel().selectFirst();
		cbSituacao.getSelectionModel().selectFirst();

		txtNome.setText("");
		txtTelefone.setText("");
		txtCelular.setText("");
		txtEmail.setText("");
		txtAreaObservacao.setText("");

		return this;
	}

	public CadContatoController carregaContato(Contato contato) {
		limpaCampos();

		if (contato == null)
			return this;

		this.contato = contato;

		if (contato.getNome() != null && !contato.getNome().isEmpty())
			txtNome.setText(contato.getNome());

		if (contato.getTelefone() != null && !contato.getTelefone().isEmpty())
			txtTelefone.setText(contato.getTelefone());

		if (contato.getCelular() != null && !contato.getCelular().isEmpty())
			txtCelular.setText(contato.getCelular());

		if (contato.getEmail() != null && !contato.getEmail().isEmpty())
			txtEmail.setText(contato.getEmail());

		if (contato.getObservacao() != null && !contato.getObservacao().isEmpty())
			txtAreaObservacao.setText(contato.getObservacao());

		cbSituacao.getSelectionModel().select(contato.getSituacao().ordinal());
		cbTipo.getSelectionModel().select(contato.getTipoContato().ordinal());

		return this;
	}

	@Override
	public synchronized void initialize(URL location, ResourceBundle resources) {
		Validadores.setTextFieldNotEmpty(txtNome);
		Validadores.setTextFieldEmailExit(txtEmail);
		Validadores.setTextFieldTelefoneExit(txtTelefone);
		Validadores.setTextFieldTelefoneExit(txtCelular);

		Mascaras.foneField(txtTelefone);
		Mascaras.foneField(txtCelular);

		TecladoUtils.onEnterConfigureTab(txtNome);
		TecladoUtils.onEnterConfigureTab(txtTelefone);
		TecladoUtils.onEnterConfigureTab(txtCelular);
		TecladoUtils.onEnterConfigureTab(txtEmail);
		TecladoUtils.onEnterConfigureTab(cbTipo);
		TecladoUtils.onEnterConfigureTab(cbSituacao);

		cbSituacao.getItems().addAll(Situacao.values());
		cbSituacao.getSelectionModel().selectFirst();
		cbTipo.getItems().addAll(TipoContato.values());
		cbTipo.getSelectionModel().selectFirst();
	}

	public static URL getFxmlLocate() {
		return CadContatoController.class.getResource("/cadastro/view/cadastros/CadContato.fxml");
	}

}
