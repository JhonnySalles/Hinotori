package pdv;

import java.util.logging.Level;
import java.util.logging.Logger;

import comum.model.utils.ViewGerenciador;
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
import pdv.controller.DashboardController;

public class App extends Application {

	private final static Logger LOGGER = Logger.getLogger(App.class.getName());

	private static Scene mainScene;
	private static DashboardController mainController;

	@Override
	public synchronized void start(Stage primaryStage) {
		try {
			// Classe inicial
			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Dashboard.fxml"));
			AnchorPane scPnTelaPrincipal = loader.load();
			mainController = loader.getController();

			mainScene = new Scene(scPnTelaPrincipal); // Carrega a scena
			mainScene.setFill(Color.BLACK);

			primaryStage.setScene(mainScene); // Seta a cena principal
			primaryStage.setTitle("Ponto de venda");
			primaryStage.getIcons()
					.add(new Image(getClass().getResourceAsStream("resources/imagens/icon/icoPDV_400.png")));
			primaryStage.setMinHeight(600);
			primaryStage.setMinWidth(600);
			primaryStage.initStyle(StageStyle.DECORATED);
			primaryStage.centerOnScreen();
			primaryStage.setMaximized(true);

			primaryStage.show(); // Mostra a tela.

			Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
			primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
			primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
			preCarregamentoTelas();

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "{Erro ao iniciar o programa}", e);
		}
	}

	private static void preCarregamentoTelas() {
		String[] telas = {"/pdv/view/view/cadastros/CadCliente.fxml",
				"/pdv/view/view/cadastros/CadEmpresa.fxml", "/pdv/view/view/cadastros/CadUsuario.fxml",
				"/pdv/view/view/cadastros/CadProduto.fxml"};
		ViewGerenciador.setPre_Carregamento(telas);
		
		String[] css = {App.class.getResource("/pdv/resources/css/Paleta_Cores.css").toExternalForm(),
				App.class.getResource("/pdv/resources/css/White_Cadastros.css").toExternalForm(),
				App.class.getResource("/pdv/resources/css/White_Dashboard.css").toExternalForm(),
				App.class.getResource("/pdv/resources/css/White_Dashboard_Botoes.css").toExternalForm(),
				App.class.getResource("/pdv/resources/css/White_Dashboard_Graficos.css").toExternalForm(),
				App.class.getResource("/pdv/resources/css/White_Frame.css").toExternalForm()};
		ViewGerenciador.carregaCss(css);
		ViewGerenciador.preCarregamentoTelas();
	}

	public static DashboardController getMainController() {
		return mainController;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
