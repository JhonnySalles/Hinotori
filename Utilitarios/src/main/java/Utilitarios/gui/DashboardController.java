package Utilitarios.gui;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import com.jfoenix.controls.JFXDialog;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DashboardController implements Initializable {

	final static String EncodDecod = "encode/TelaCodificacao.fxml";
	final static String BaseChoice = "database/TelaDataBase.fxml";
	final static String imgIcoEncode = "/Utilitarios/resources/images/icoEncode_48.png";

	@FXML
	ImageView imgViewBanco;
	final Image imgBanco = new Image(
			getClass().getResourceAsStream("/Utilitarios/resources/images/dashboard/bntDataBase_48.png"));
	final Image imgBancoHover = new Image(
			getClass().getResourceAsStream("/Utilitarios/resources/images/dashboard/bntDataBaseHover_48.png"));

	@FXML
	ImageView imgViewUsuario;
	final Image imgUsuario = new Image(
			getClass().getResourceAsStream("/Utilitarios/resources/images/dashboard/bntUsuario_48.png"));
	final Image imgUsuarioHover = new Image(
			getClass().getResourceAsStream("/Utilitarios/resources/images/dashboard/bntUsuarioHover_48.png"));

	@FXML
	ImageView imgViewEncode;
	final Image imgEncode = new Image(
			getClass().getResourceAsStream("/Utilitarios/resources/images/dashboard/icoEncode_48.png"));
	final Image imgEncodeHover = new Image(
			getClass().getResourceAsStream("/Utilitarios/resources/images/dashboard/icoEncodeHover_48.png"));

	private Map<URL, Stage> telas = new HashMap<>();

	@FXML
	Button btnEncodDecod;

	@FXML
	Button btnBancoDados;

	@FXML
	Button btnUsuario;

	@FXML
	ScrollPane background;

	private static JFXDialog dialog = new JFXDialog();

	@FXML
	public void onEncodDecodAction() {
		loadTela(EncodDecod, imgIcoEncode, "Encode Decode");
	}

	/**
	 * Função criada para carregar as telas de forma genérica, basta apenas passar o
	 * endereço da tela, o endereço do icone e alguma frase para o titulo.
	 * 
	 * <p>
	 * Ex: loadTela("encode/TelaCodificacao.fxml",
	 * "/Utilitarios/resources/images/icoEncode_48.png", "Encode Decode");
	 * 
	 * @param tela   Parâmetro com o endereço da tela, partindo de onde o dashboard
	 *               está.
	 * @param icone  Endereço da imagem do icone da tela, começando pelo path java.
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
				stage.initModality(Modality.WINDOW_MODAL);
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

	// Utiliza a função de popup da biblioteca controlsfx.
	public void criaPopupBancoDados() {
		try {
			FXMLLoader loader = new FXMLLoader();
			URL url = getClass().getResource(BaseChoice);
			loader.setLocation(url);
			StackPane stack = loader.load();
			
			PopOver popover = new PopOver(stack);
		    popover.setArrowLocation(PopOver.ArrowLocation.RIGHT_TOP);
		    popover.setCornerRadius(4);
		    popover.setTitle("Seleção de banco de dados");

		    btnBancoDados.setOnAction(event -> {

		        if (popover.isShowing()) {
		            popover.hide();
		        } else if (!popover.isShowing()) {
		        	popover.show(btnBancoDados);
		        } else {
		            new RuntimeException("isShowing() state not recognised.");
		        }
		    });
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Erro ao criar o popup do banco dedados. ");
		}
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		background.setFitToHeight(true);
		background.setFitToWidth(true);

		btnBancoDados.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
			imgViewBanco.setImage(imgBancoHover);
		});

		btnBancoDados.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
			imgViewBanco.setImage(imgBanco);
			btnBancoDados.setStyle("");
		});

		btnUsuario.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
			imgViewUsuario.setImage(imgUsuarioHover);
		});

		btnUsuario.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
			imgViewUsuario.setImage(imgUsuario);
		});

		btnEncodDecod.addEventFilter(MouseEvent.MOUSE_ENTERED, e -> {
			imgViewEncode.setImage(imgEncodeHover);
			btnEncodDecod.setStyle("-fx-text-fill: #A5D6A7;");
		});

		btnEncodDecod.addEventFilter(MouseEvent.MOUSE_EXITED, e -> {
			imgViewEncode.setImage(imgEncode);
			btnEncodDecod.setStyle("");
		});
		
		criaPopupBancoDados();
	}
}
