package model.alerts;

import java.awt.TextArea;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ErroController implements Initializable {
	
	@FXML
	AnchorPane background;
	
	@FXML
	Button btnOk;
	
	@FXML
	Label lblTitulo;
	
	@FXML
	ImageView ImgVwIconeTitulo;
	
	@FXML
	ImageView ImgVwIcone;
	
	@FXML
	Label lblPrincipal;
	
	@FXML
	TextArea txtAreaErro;
	
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
}
