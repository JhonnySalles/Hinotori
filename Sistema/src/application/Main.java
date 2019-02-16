package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import servidor.persistencia.Controlador;


public class Main extends Application {
	
	private static Scene mainScene;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//Comentar esta linha para teste.
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/guiMain/Login.fxml"));
			AnchorPane scPnTelaPrincipal = loader.load();
			
		 //Usado para teste, setar o fxml da tela que está testando apra abrir ela ao invez da tela de usuario.
		//FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Main.fxml"));
		//	AnchorPane scPnTelaPrincipal = loader.load();
			
			//FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Entregas.fxml"));
			//AnchorPane scPnTelaPrincipal = loader.load();

			mainScene = new Scene(scPnTelaPrincipal); // Carrega a scena
			mainScene.setFill(Color.TRANSPARENT);
			
			primaryStage.setScene(mainScene); // Seta a cena principal
			primaryStage.setTitle("Shiyoken");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../images/login/Shiyoken.png")));
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.show(); // Mostra a tela.
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Controlador com = new Controlador();
		launch(args);
	}
}
