package Administrador.controller.cadastros;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import Administrador.controller.frame.PesquisaGenericaController;
import Administrador.controller.pesquisas.PsqEmpresaController;
import Administrador.model.dao.services.EmpresaServices;
import Administrador.model.entities.Empresa;
import Administrador.model.entities.PesquisaGenericaDados;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.constraints.Limitadores;
import model.enums.Situacao;
import model.mask.Mascaras;
import model.notification.Notificacao;

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
	private AnchorPane rootPane;

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
	private Button btnExcluir;

	@FXML
	private Button btnVoltar;

	private EmpresaServices empresaService;
	private Empresa empresa;
	private PsqEmpresaController PsqEmpresa;

	@FXML
	public void onBtnConfirmarEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btnConfirmar.fire();
		}
	}

	@FXML
	public void onBtnConfirmarClick() {
		try {
			if (validaCampos()) {
				background.getScene().getRoot().setCursor(Cursor.WAIT);
				atualizaEntidade();
				salvar();
				Notificacao.Dark("Processo concluído", "Cliente salvo com sucesso.", 3.0, Pos.BASELINE_RIGHT);
				limpaCampos();
			}
		} finally {
			background.getScene().getRoot().setCursor(null);
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
		PsqEmpresa.returnView();
	}

	public PsqEmpresaController getPsqEmpresa() {
		return PsqEmpresa;
	}

	public void setPsqEmpresa(PsqEmpresaController PsqEmpresa) {
		this.PsqEmpresa = PsqEmpresa;
	}

	public void carregaEmpresa(Empresa empresa) {
		this.empresa = empresa;
		atualizaTela();
	}

	private void salvar() {
		if (empresaService == null)
			setEmpresaServices(new EmpresaServices());
		empresaService.salvar(empresa);
	}

	public void loadView(String tela) {
		PsqEmpresa.loadView(tela);
	}

	public void returnView() {
		PsqEmpresa.returnView();
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
		empresa = null;
		txtEndereco.setText("");
		txtNumero.setText("");
		txtCep.setText("");
		txtComplemento.setText("");
		// txtAreaObservacao.setText("");
		cbSituacao.getSelectionModel().selectFirst();
		frameBairroController.limpaCampos();
	}

	private void atualizaTela() {
		txtNomeFantasia.setText(empresa.getNomeFantasia());
		txtRazaoSocial.setText(empresa.getRazaoSocial());
		txtCnpj.setText(empresa.getCnpj());
		txtEndereco.setText(empresa.getEndereco());
		txtNumero.setText(empresa.getNumero());
		txtComplemento.setText(empresa.getComplemento());
		txtCep.setText(empresa.getCep());
		cbSituacao.getSelectionModel().getSelectedItem().equals(empresa.getSituacao());
		frameCidadeController.setItemSelecionado(new PesquisaGenericaDados(
				empresa.getBairro().getCidade().getId().toString(), empresa.getBairro().getCidade().getNome() + "/"
						+ empresa.getBairro().getCidade().getEstado().getSigla()));
		frameBairroController.setItemSelecionado(
				new PesquisaGenericaDados(empresa.getBairro().getId().toString(), empresa.getBairro().getNome()));
	}

	private void atualizaEntidade() {
		/*
		 * if (empresa == null) { empresa = new ClienteEndereco(txtEndereco.getText(),
		 * txtNumero.getText(), txtCep.getText(), txtComplemento.getText(),
		 * txtAreaObservacao.getText(),
		 * cbSituacao.getSelectionModel().getSelectedItem(),
		 * bairroService.pesquisar(Long.parseLong(frameBairroController.getID())));
		 * empresa.add(selecionado); } else {
		 * empresa.setEndereco(txtEndereco.getText());
		 * empresa.setNumero(txtNumero.getText()); empresa.setCep(txtCep.getText());
		 * empresa.setComplemento(txtComplemento.getText());
		 * empresa.setSituacao(cbSituacao.getSelectionModel().getSelectedItem());
		 * empresa.setBairro(bairroService.pesquisar(Long.parseLong(
		 * frameBairroController.getID()))); }
		 */
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
								" AND IdCidade = " + frameCidadeController.getID(), "ORDER BY Id_Cidade, Nome");
					else
						frameBairroController.setPesquisa("Id", "Nome", "Id, IdCidade, Nome", "bairros", "", "",
								"ORDER BY IdCidade, Nome");
				}
			}
		});

		frameBairroController.setPesquisa("Id", "Nome", "Id, IdCidade, Nome", "bairros", "", "",
				"ORDER BY IdCidade, Nome");

		frameCidadeController.txt_Pesquisa.setPromptText("Pesquisa de cidades");
		frameBairroController.txt_Pesquisa.setPromptText("Pesquisa de bairros");

		Mascaras.cnpjField(txtCnpj);
		Mascaras.cepField(txtCep);
		Limitadores.setTextFieldInteger(txtNumero);

		setEmpresaServices(new EmpresaServices());
	}

	private void setEmpresaServices(EmpresaServices empresaService) {
		this.empresaService = empresaService;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		background.setFitToHeight(true);
		background.setFitToWidth(true);
		cbSituacao.getItems().addAll(Situacao.values());
		cbSituacao.getSelectionModel().select(Situacao.ATIVO);
		configuraCampos();
	}

}
