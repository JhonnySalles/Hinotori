package Administrador;

import Administrador.controller.DashboardController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {

	private static Scene mainScene;
	private static DashboardController mainController;

	@Override
	public void start(Stage primaryStage) {
		try {
			// Classe inicial
			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Dashboard.fxml"));
			AnchorPane scPnTelaPrincipal = loader.load();
			mainController = loader.getController();

			// FXMLLoader loader = new
			// FXMLLoader(getClass().getResource("../gui/cadastros/cadCliente.fxml"));
			// AnchorPane scPnTelaPrincipal = loader.load();

			mainScene = new Scene(scPnTelaPrincipal); // Carrega a scena
			mainScene.setFill(Color.BLACK);

			primaryStage.setScene(mainScene); // Seta a cena principal
			primaryStage.setTitle("Administrador");
			primaryStage.getIcons()
					.add(new Image(getClass().getResourceAsStream("resources/geral/images/icon/Shiyoken.png")));
			primaryStage.initStyle(StageStyle.DECORATED);
			primaryStage.centerOnScreen();
			// primaryStage.setMaximized(true); // Teste

			primaryStage.show(); // Mostra a tela.

			Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
			primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
			primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DashboardController getMainController() {
		return mainController;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
