package br.com.shiyoken.gui.cadastros;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import model.cliping.Recorte;
import model.constraints.Limitadores;
import model.mask.Mascaras;

public class CadClienteController implements Initializable {
	
	@FXML
	JFXTextField txtCodigo;
	
	@FXML
	JFXTextField txtNome;
	
	@FXML
	JFXTextField txtSobreNome;
	
	@FXML
	JFXTextField txtEndereco;
	
	@FXML
	JFXTextField txtNumero;
	
	@FXML
	JFXTextField txtComplemento;
	
	@FXML
	JFXTextField txtCep;
	
	@FXML
	JFXButton btnPsqCidades;
	
	@FXML
	JFXTextField txtCidade;
	
	@FXML
	JFXComboBox cmbBoxEstado;
	
	@FXML
	JFXTextField txtTelefone;
	
	@FXML
	JFXTextField txtCelular;

	@FXML
	Pane paneBackground;
	
	@FXML
	ScrollPane background;
	
	@FXML
	private Button btnConfirmar;
	@FXML
	private Button btnCancelar;
	@FXML
	private Button btnNovo;
	@FXML
	private Button btnExcluir;
	@FXML
	private Button btnPesquisar;
	@FXML
	private Button btnVoltar;
	
	
	@FXML
	private void txtCepKeyRelased() {
		
		
	}
	
	
	static void clipChildren(Region region, double arc) {

	    Rectangle outputClip = new Rectangle();
	    outputClip.setArcWidth(arc);
	    outputClip.setArcHeight(arc);
	    outputClip.setWidth(200);
	    outputClip.setHeight(200);
	    region.setClip(outputClip);

	    region.layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
	        outputClip.setWidth(newValue.getWidth());
	        outputClip.setHeight(newValue.getHeight());
	    });        
	}
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		background.setFitToHeight(true);
		background.setFitToWidth(true);
		
		Limitadores.setTextFieldID(txtCodigo, 11);
		Limitadores.setTextFieldInteger(txtNumero);
		
		Mascaras.cepField(txtCep);
		Mascaras.foneField(txtTelefone);
		Mascaras.foneField(txtCelular);
		
		// Recortar a area
		//Recorte.recorteQuadrado(paneBackground, 200, 200, 40, 40);

		/*//A funcao onlyDigitsValue retorna apenas os digitos do TextField passado como parâmetro.
        // Seria a mesma situação para CEP, FONE, CNPJ
        System.out.println("cpf informado: "+ MaskFieldUtil.onlyDigitsValue(this.fieldCpf));*/
		
		
	}
}
