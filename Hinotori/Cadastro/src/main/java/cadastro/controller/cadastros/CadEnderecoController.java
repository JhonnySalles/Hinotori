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

import cadastro.controller.frame.PesquisaGenericaController;
import comum.form.DashboardFormPadrao;
import comum.model.constraints.Limitadores;
import comum.model.constraints.TecladoUtils;
import comum.model.constraints.Validadores;
import comum.model.enums.Situacao;
import comum.model.enums.TipoEndereco;
import comum.model.mask.Mascaras;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import servidor.dao.services.BairroServices;
import servidor.entities.Bairro;
import servidor.entities.Endereco;

public class CadEnderecoController implements Initializable {

	/* Referencia para o controlador pai, onde é utilizado para realizar o refresh na tela */
	private DashboardFormPadrao dashBoard;
	
	@FXML
	private AnchorPane rootPane;

	@FXML
	private JFXComboBox<TipoEndereco> cbTipo;

	@FXML
	private JFXComboBox<Situacao> cbSituacao;

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
	private PesquisaGenericaController frameCidadeController;

	// O formato do arquivo incluido é um anchorpane "conforme criei a tela",
	// o nome aqui será o mesmo que no id do fxml incluido.
	@FXML
	private AnchorPane frameBairro;

	// Para utilizar o controlador do frame incluido, basta colocar a descrição
	// "Controller" na frente do id do fxml incluido conforme abaixo.
	@FXML
	private PesquisaGenericaController frameBairroController;

	@FXML
	private JFXTextArea txtAreaObservacao;

	@FXML
	private JFXButton btnConfirmar;

	@FXML
	private JFXButton btnCancelar;

	@FXML
	public JFXButton btnVoltar;

	private Set<Endereco> enderecos;
	private Endereco endereco;

	@FXML
	public void onBtnConfirmarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnConfirmar.fire();
		}
	}

	@FXML
	public void onBtnConfirmarClick() {
		if (validaCampos()) {
			atualizaEntidade().salvar(endereco);
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

		if (frameBairroController.txtFraPesquisa.getText().isEmpty()) {
			frameBairroController.txtFraPesquisa.setUnFocusColor(Color.RED);
			valida = false;
		}

		if (txtEndereco.getText().isEmpty()) {
			txtEndereco.setUnFocusColor(Color.RED);
			valida = false;
		}

		if (!Validadores.validaCep(txtCep.getText())) {
			txtCep.setUnFocusColor(Color.RED);
			valida = false;
		}

		return valida;
	}

	private void salvar(Endereco endereco) {
		if (enderecos == null)
			enderecos = new HashSet<>();

		if (enderecos.size() < 1)
			endereco.setPadrao(true);

		if (!enderecos.contains(endereco))
			enderecos.add(endereco);

		limpaCampos();
	}

	private CadEnderecoController atualizaEntidade() {
		if (endereco == null)
			endereco = new Endereco();

		endereco.setEndereco(txtEndereco.getText());
		endereco.setNumero(txtNumero.getText());
		endereco.setComplemento(txtComplemento.getText());
		endereco.setCep(txtCep.getText());
		endereco.setObservacao(txtAreaObservacao.getText());
		endereco.setSituacao(cbSituacao.getSelectionModel().getSelectedItem());
		endereco.setTipoEndereco(cbTipo.getSelectionModel().getSelectedItem());

		if (frameBairroController.getId() != null)
			endereco.setBairro(new BairroServices().pesquisar(Long.parseLong(frameBairroController.getId())));
		else {
			endereco.setBairro(new Bairro());
			endereco.getBairro().setId(Long.valueOf(0));
		}

		return this;
	}

	public CadEnderecoController limpaCampos() {
		endereco = new Endereco();

		cbTipo.getSelectionModel().selectFirst();
		cbSituacao.getSelectionModel().selectFirst();

		txtEndereco.setText("");
		txtNumero.setText("");
		txtComplemento.setText("");
		txtCep.setText("");
		txtAreaObservacao.setText("");

		frameCidadeController.limpaCampos();
		frameBairroController.limpaCampos();

		return this;
	}

	public CadEnderecoController carregaEndereco(Endereco endereco) {
		limpaCampos();

		this.endereco = endereco;

		if (endereco.getEndereco() != null && !endereco.getEndereco().isEmpty())
			txtEndereco.setText(endereco.getEndereco());

		if (endereco.getNumero() != null && !endereco.getNumero().isEmpty())
			txtNumero.setText(endereco.getNumero());

		if (endereco.getComplemento() != null && !endereco.getComplemento().isEmpty())
			txtComplemento.setText(endereco.getComplemento());

		if (endereco.getCep() != null && !endereco.getCep().isEmpty())
			txtCep.setText(endereco.getCep());

		if (endereco.getObservacao() != null && !endereco.getObservacao().isEmpty())
			txtAreaObservacao.setText(endereco.getObservacao());

		cbSituacao.getSelectionModel().select(endereco.getSituacao().ordinal());
		cbTipo.getSelectionModel().select(endereco.getTipoEndereco().ordinal());

		dashBoard.atualizaTabPane();

		return this;
	}

	public CadEnderecoController setEnderecos(Set<Endereco> enderecos) {
		this.enderecos = enderecos;
		return this;
	}

	private CadEnderecoController setSqlFrame() {
		frameCidadeController.setPesquisa("Id", "Cidade",
				"cidades.Id, CONCAT(cidades.Nome, '/', estados.Sigla) AS Cidade", "cidades",
				"INNER JOIN estados ON cidades.IdEstado = estados.Id", "", "ORDER BY Cidade");

		frameCidadeController.txtFraPesquisa.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue.booleanValue()) {
					if (frameCidadeController.getId() != null)
						frameBairroController.setPesquisa("Id", "Bairro", "Id, Nome As Bairro", "bairros", "",
								" AND IdCidade = " + frameCidadeController.getId(), "ORDER BY Bairro");
					else
						frameBairroController.setPesquisa("Id", "Bairro",
								"Bairros.Id, Cidades.Nome AS Cidade, Bairros.Nome AS Bairro", "bairros",
								"INNER JOIN Cidades ON cidades.id = bairros.IdCidade", "", "ORDER BY Cidade, Bairro");
				}
			}
		});

		frameBairroController.setPesquisa("Id", "Bairro", "Bairros.Id, Cidades.Nome AS Cidade, Bairros.Nome AS Bairro",
				"bairros", "INNER JOIN Cidades ON cidades.id = bairros.IdCidade", "", "ORDER BY Cidade, Bairro");

		frameBairroController.txtFraPesquisa.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {

				if (oldPropertyValue && frameCidadeController.getId() == null)
					frameCidadeController.txtFraPesquisa.setUnFocusColor(Color.RED);
			}
		});
		frameBairroController.setColunaIdVisivel(false);

		frameCidadeController.txtFraPesquisa.setPromptText("Pesquisa de cidades");
		frameBairroController.txtFraPesquisa.setPromptText("Pesquisa de bairros");

		return this;
	}

	@Override
	public synchronized void initialize(URL location, ResourceBundle resources) {
		setSqlFrame();

		Validadores.setTextFieldNotEmpty(frameCidadeController.txtFraPesquisa);
		Validadores.setTextFieldNotEmpty(frameBairroController.txtFraPesquisa);
		Validadores.setTextFieldNotEmpty(txtEndereco);

		Limitadores.setTextFieldMaxLenght(txtNumero, 10);

		Mascaras.cepField(txtCep);

		TecladoUtils.onEnterConfigureTab(txtEndereco);
		TecladoUtils.onEnterConfigureTab(txtComplemento);
		TecladoUtils.onEnterConfigureTab(txtCep);
		TecladoUtils.onEnterConfigureTab(txtNumero);
		TecladoUtils.onEnterConfigureTab(cbTipo);
		TecladoUtils.onEnterConfigureTab(cbSituacao);

		cbSituacao.getItems().addAll(Situacao.values());
		cbSituacao.getSelectionModel().selectFirst();
		cbTipo.getItems().addAll(TipoEndereco.values());
		cbTipo.getSelectionModel().selectFirst();
	}
	
	public DashboardFormPadrao getDashBoard() {
		return dashBoard;
	}

	public void setDashBoard(DashboardFormPadrao dashBoard) {
		this.dashBoard = dashBoard;
	}
	
	public static URL getFxmlLocate() {
		return CadEnderecoController.class.getResource("/cadastro/view/cadastros/CadEndereco.fxml");
	}

}
