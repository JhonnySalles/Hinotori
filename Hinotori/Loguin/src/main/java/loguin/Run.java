package loguin;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import loguin.controller.LoadingController;
import loguin.controller.LoginController;

public class Run extends Application {

	@Override
	public synchronized void start(Stage primaryStage) {
		// Comentar esta linha para teste.
		FXMLLoader loader = new FXMLLoader(LoadingController.getFxmlLocate());
		Parent scPnTelaPrincipal = null;
		try {
			scPnTelaPrincipal = loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LoadingController controller = loader.getController();

		Scene mainScene = new Scene(scPnTelaPrincipal); // Carrega a scena
		mainScene.setFill(Color.TRANSPARENT);

		primaryStage.setScene(mainScene); // Seta a cena principal
		primaryStage.setTitle("Hinotori");
		primaryStage.getIcons().add(new Image(Login.class.getResourceAsStream(LoginController.getIcon())));
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
