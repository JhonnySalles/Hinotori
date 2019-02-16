package application;
	
import gui.TelaConfiguracaoController;
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
import util.ProcessaXML;


public class Main extends Application {
	
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
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/TelaConfiguracao.fxml"));
			AnchorPane scPnTelaPrincipal = loader.load();
			
			Scene mainScene = new Scene(scPnTelaPrincipal); // Carrega a scena
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
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../images/config/Shiyoken.png")));
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			primaryStage.show(); // Mostra a tela.
			
			ProcessaXML xml = new ProcessaXML();
			TelaConfiguracaoController controller = loader.getController();
			xml.verificaConfig(controller);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
