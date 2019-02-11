package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/TelaConfiguracao.fxml"));
			AnchorPane scPnTelaPrincipal = loader.load();

			Scene mainScene = new Scene(scPnTelaPrincipal); // Carrega a scena
			mainScene.setFill(Color.TRANSPARENT);
			
			primaryStage.setScene(mainScene); // Seta a cena principal
			primaryStage.setTitle("Shiyoken");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../images/config/Shiyoken.png")));
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.show(); // Mostra a tela.
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
