package Utilitarios.gui;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DashboardController implements Initializable {

	final static String EncodDecod = "encode/TelaCodificacao.fxml";
	private Map<URL, Stage> telas = new HashMap<>();
	
	@FXML
	Button btnEncodDecod;
	
	@FXML
	ScrollPane background;
	
	
	@FXML
	public void onEncodDecodAction() {
		try {
			if (telas.containsKey(EncodDecod)) {
				
				telas.get(EncodDecod).show();
				
			} else {
				FXMLLoader loader = new FXMLLoader(getClass().getResource(EncodDecod));
				AnchorPane newAnchorPane = loader.load();
				
				Scene mainScene = new Scene(newAnchorPane); // Carrega a scena
				mainScene.setFill(Color.BLACK);
	
				Stage stage = new Stage();
				stage.setScene(mainScene); // Seta a cena principal
				stage.setTitle("Encode Decode");
				stage.initStyle(StageStyle.DECORATED);
				stage.setResizable(false);
	
				stage.show(); // Mostra a tela.
				
				
				stage.setOnCloseRequest(e -> telas.remove(EncodDecod)); // Remove do mapeamento se fechou.
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		background.setFitToHeight(true);
		background.setFitToWidth(true);
	}
}
