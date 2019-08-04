package Administrador.controller.frame;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import Administrador.model.entities.PesquisaGenerica;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PesquisaGenericaController implements Initializable  {
	
	@FXML
	JFXTextField txt_Pesquisa;
	
	@FXML
	JFXButton btn_Limpar;
	
	@FXML
	JFXButton btn_Pesquisar;
	
	private String ID;
	private PesquisaGenerica Pesquisa;
		
	@FXML
	private void onTxtPesquisaEnter() {

	}
	
	@FXML
	private void onTxtPesquisaExit() {
		
	}
	
	@FXML
	private void onBtnLimparAction() {
		txt_Pesquisa.setText("");
		ID = "";		
	}
	
	@FXML
	private void onBtnPesquisarAction() {
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void setPesquisa(String CampoID, String CampoSelect, String Tabela, String Joins, String Where, String GroupOrder) {
        Pesquisa = new PesquisaGenerica(CampoID, CampoSelect, Tabela, Joins, Where, GroupOrder);
		
	}
	
	public void setDescricao(String descricao) {
		txt_Pesquisa.setPromptText(descricao);
	}
	
	public String getID() {
		return ID;
	}
	
	public void limpaCampos() {
		btn_Limpar.fire();
	}
		
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		/* Popup de descricao dos botoes */
        Tooltip toltLimpar = new Tooltip("Limpar");
        Tooltip toltPesquisar = new Tooltip("Pesquisar");
        
        btn_Limpar.setTooltip(toltLimpar);
        btn_Pesquisar.setTooltip(toltPesquisar);
        
        Pesquisa = new PesquisaGenerica("id", "Cli_Nome", "clientes", "", "CLi_Codigo > 5", "ORDER BY Cli_Nome");
                
	}

}
