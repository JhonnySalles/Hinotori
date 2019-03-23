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
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DashboardController implements Initializable {

	final static String EncodDecod = "encode/TelaCodificacao.fxml";
	final static String imgEncode  = "/Utilitarios/resources/images/icoEncode_48.png";

	private Map<URL, Stage> telas = new HashMap<>();

	@FXML
	Button btnEncodDecod;

	@FXML
	ScrollPane background;

	@FXML
	public void onEncodDecodAction() {
		loadTela(EncodDecod, imgEncode, "Encode Decode");
	}

	 /**
     * Função criada para carregar as telas de forma genérica, basta apenas passar o 
     * endereço da tela, o endereço do icone e alguma frase para o titulo.
     * 
     * <p> Ex:
     * loadTela("encode/TelaCodificacao.fxml", "/Utilitarios/resources/images/icoEncode_48.png", "Encode Decode");
     * 
     * @param tela Parâmetro com o endereço da tela, partindo de onde o dashboard está.
     * @param icone Endereço da imagem do icone da tela, começando pelo path java.
     * @param titulo Um titulo que será exibido no cabeçalho da tela.
     **/
	public void loadTela(String tela, String icone, String titulo) {
		try {
			URL url = getClass().getResource(tela);
			if (telas.containsKey(url)) {

				telas.get(url).show();
				telas.get(url).toFront();

			} else {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(url);
				AnchorPane newAnchorPane = loader.load();

				Scene mainScene = new Scene(newAnchorPane); // Carrega a scena
				mainScene.setFill(Color.BLACK);

				Stage stage = new Stage();
				stage.setScene(mainScene); // Seta a cena principal
				stage.setTitle(titulo);
				stage.initStyle(StageStyle.DECORATED);
				stage.setResizable(false);
				stage.getIcons().add(new Image(getClass().getResourceAsStream(icone)));

				telas.put(url, stage);
				stage.setOnCloseRequest(e -> telas.remove(url)); // Remove do mapeamento se fechou.
				stage.show(); // Mostra a tela.

			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Erro ao abrir a tela " + tela);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		background.setFitToHeight(true);
		background.setFitToWidth(true);
	}
}
