package Administrador.controller.frame;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextField;

import Administrador.model.dao.services.PesquisaGenericaServices;
import Administrador.model.entities.PesquisaGenerica;
import Administrador.model.entities.PesquisaGenericaDados;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class PesquisaGenericaController implements Initializable {

	@FXML
	private JFXTextField txt_Pesquisa;

	@FXML
	private JFXButton btn_Limpar;

	@FXML
	private JFXButton btn_Pesquisar;
	
	@FXML
	private AnchorPane background;

	private PesquisaGenerica pesquisa;
	private PesquisaGenericaServices pesquisaService;
	private PesquisaGenericaDados itemSelecionado;
	private List<PesquisaGenericaDados> resultado;
	
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
		
		URL url = getClass().getResource("PesquisaGenericaGrid.fxml");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(url);
		PopOver pop = new PopOver();
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10));//10px padding todos os lados

		
		try {
			vbox.getChildren().add(loader.load());
			pop.setContentNode(vbox);
			pop.show(btn_Pesquisar);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		//background.getIen
		/*
		URL url = getClass().getResource("frame/PesquisaGenericaGrid.fxml");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(url);
		AnchorPane newAnchorPane;
		try {
			newAnchorPane = loader.load();

			Scene mainScene = new Scene(newAnchorPane); // Carrega a scena
			mainScene.setFill(Color.BLACK);

			Stage stage = new Stage();
			stage.setScene(mainScene); // Seta a cena principal
			stage.setTitle("Tela de teste");
			stage.initStyle(StageStyle.DECORATED);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.setResizable(true);
			stage.setMinWidth(870);
			stage.setMinHeight(500);
			stage.show(); // Mostra a tela.

		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

	public void setPesquisa(String campoID, String campoDescricao, String select, String tabela, String joins,
			String where, String groupOrder) {
		pesquisa = new PesquisaGenerica(campoID, campoDescricao, select, tabela, joins, where, groupOrder);
		resultado = pesquisaService.pesquisar(pesquisa);
	}
	
	public void setItemSelecionado(PesquisaGenericaDados item) {
		this.itemSelecionado = item;
	}

	public void setDescricao(String descricao) {
		txt_Pesquisa.setPromptText(descricao);
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

		/* Popup de descricao dos botoes */
		Tooltip toltLimpar = new Tooltip("Limpar");
		Tooltip toltPesquisar = new Tooltip("Pesquisar");

		btn_Limpar.setTooltip(toltLimpar);
		btn_Pesquisar.setTooltip(toltPesquisar);

	}

}
