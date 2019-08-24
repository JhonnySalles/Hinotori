package Administrador.controller.frame;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import Administrador.model.dao.services.PesquisaGenericaServices;
import Administrador.model.entities.PesquisaGenerica;
import Administrador.model.entities.PesquisaGenericaDados;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class PesquisaGenericaController implements Initializable {

	@FXML
	public JFXTextField txt_Pesquisa;

	@FXML
	private JFXButton btn_Limpar;

	@FXML
	private JFXButton btn_Pesquisar;

	@FXML
	public AnchorPane background;

	private PesquisaGenerica pesquisa;
	private PesquisaGenericaServices pesquisaService;
	private PesquisaGenericaDados itemSelecionado;
	private List<PesquisaGenericaDados> resultado;
	private PesquisaGenericaGridController controller;
	private PopOver pop;

	@FXML
	private void onTxtPesquisaEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (e.getCode().toString().equals("ENTER")) {
			btn_Pesquisar.fire();
		}
	}

	@FXML
	private void onTxtPesquisaExit() {
		btn_Pesquisar.fire();
	}

	@FXML
	private void onBtnLimparAction() {
		txt_Pesquisa.setText("");
		itemSelecionado = null;
	}

	@FXML
	private void onBtnPesquisarAction() {
		URL url = getClass().getResource("/Administrador/view/frame/PesquisaGenericaGrid.fxml");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(url);
		pop = new PopOver();
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(1));// 10px padding todos os lados

		try {
			vbox.getChildren().add(loader.load());

			controller = loader.getController();
			controller.setControllerPai(this);
			if (resultado != null)
				controller.carregaGrid(resultado);

			pop.setContentNode(vbox);
			pop.arrowLocationProperty().set(ArrowLocation.TOP_CENTER);
			pop.setCornerRadius(5);
			pop.show(txt_Pesquisa);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setPesquisa(String campoID, String campoDescricao, String select, String tabela, String joins,
			String where, String groupOrder) {
		pesquisa = new PesquisaGenerica(campoID, campoDescricao, select, tabela, joins, where, groupOrder);
		resultado = pesquisaService.pesquisar(pesquisa);
		limpaCampos();
	}

	public void setItemSelecionado(PesquisaGenericaDados item) {
		this.itemSelecionado = item;
		if (itemSelecionado != null)
			setDescricao(itemSelecionado.getDescricao());
	}

	public void duploClique(PesquisaGenericaDados item) {
		this.itemSelecionado = item;
		if (itemSelecionado != null)
			setDescricao(itemSelecionado.getDescricao());
		txt_Pesquisa.requestFocus();
		pop.hide();
	}

	public void setDescricao(String descricao) {
		txt_Pesquisa.setText(descricao);
	}

	public String getID() {
		if (itemSelecionado != null) {
			return itemSelecionado.getId();
		} else {
			return "";
		}
	}

	public void limpaCampos() {
		btn_Limpar.fire();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		itemSelecionado = null;
		resultado = null;

		pesquisaService = new PesquisaGenericaServices();

		/* Popup de descricao dos botoes */
		Tooltip toltLimpar = new Tooltip("Limpar");
		Tooltip toltPesquisar = new Tooltip("Pesquisar");

		btn_Limpar.setTooltip(toltLimpar);
		btn_Pesquisar.setTooltip(toltPesquisar);	
	}

}
