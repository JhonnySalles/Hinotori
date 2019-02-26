package application;
	
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import servidor.persistencia.Controlador;


public class Main extends Application {
	
	private static Scene mainScene;
	
	// Metodo para fazer a tela se movimentar
	@SuppressWarnings("unused")
	private double xOffset = 0;
	@SuppressWarnings("unused")
	private double yOffset = 0;
	private static class Delta {
	    double x, y;
	}
	
	final Delta dragDelta = new Delta();
	final BooleanProperty inDrag = new SimpleBooleanProperty(false);
	// Fim do metodo.
	
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
			
			// Eventos de clique do mouse realizando a movimentação da tela.
			mainScene.setOnMousePressed(new EventHandler<MouseEvent>() {
		        @Override
		        public void handle(MouseEvent event) {
		            dragDelta.x = primaryStage.getX() - event.getScreenX();
		            dragDelta.y = primaryStage.getY() - event.getScreenY();
		            xOffset = event.getSceneX();
		            yOffset = event.getSceneY();
		        }
		    });

			mainScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
		        @Override
		        public void handle(MouseEvent event) {
		            primaryStage.setX(event.getScreenX() + dragDelta.x);
		            primaryStage.setY(event.getScreenY() + dragDelta.y);
		            primaryStage.getWidth();
		            primaryStage.getHeight();
		            primaryStage.getX();
		            primaryStage.getY();
		            inDrag.set(true);

		        }
		    }); // Fim do evento.
			
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
