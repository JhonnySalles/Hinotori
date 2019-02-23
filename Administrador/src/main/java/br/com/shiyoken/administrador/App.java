package br.com.shiyoken.administrador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class App extends Application {
	
	private static Scene mainScene;

	@Override
	public void start(Stage primaryStage) {
		try {
			//Classe inicial
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/Dashboard.fxml"));
			AnchorPane scPnTelaPrincipal = loader.load();
			
			//FXMLLoader loader = new FXMLLoader(getClass().getResource("../gui/cadastros/cadCliente.fxml"));
			//AnchorPane scPnTelaPrincipal = loader.load();

			mainScene = new Scene(scPnTelaPrincipal); // Carrega a scena

			primaryStage.setScene(mainScene); // Seta a cena principal
			primaryStage.setTitle("Administrador");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../resources/images/icon/Shiyoken.png")));
			primaryStage.show(); // Mostra a tela.
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
    public static void main( String[] args )
    {
    	launch(args);
    }
}
