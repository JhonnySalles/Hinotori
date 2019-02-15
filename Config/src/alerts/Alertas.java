package alerts;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Alertas {
	
	static boolean resposta;
	
	private static double xOffset = 0;
	private static double yOffset = 0;
	private static class Delta {
	    double x, y;
	}
	
	final static Delta dragDelta = new Delta();
	final static BooleanProperty inDrag = new SimpleBooleanProperty(false);
	
	public static boolean Confirmacao(String titulo, String texto1, String texto2) {
		try {
			FXMLLoader loader = new FXMLLoader(Alertas.class.getResource("/alerts/Confirmacao.fxml"));
			AnchorPane scPnTelaPrincipal = loader.load();
			
			// Obtem a referencia do controller para editar as label.
			ConfirmacaoController controller = loader.getController();
			
			if (!titulo.equals("")) {
				controller.lblTitulo.setText(titulo);			
			}
			
			if (!texto1.equals("")) {
				controller.lblPrincipal.setText(texto1);			
			}
			
			if (!texto2.equals("")) {
				controller.lblSecundario.setText(texto2);
			}
			
			Scene tela = new Scene(scPnTelaPrincipal); // Carrega a scena
			tela.setFill(Color.TRANSPARENT);
			
			Stage stageTela = new Stage();
			stageTela.setScene(tela); // Seta a cena principal
			
			// Eventos de clique do mouse realizando a movimentação da tela.
			tela.setOnMousePressed(new EventHandler<MouseEvent>() {
		        @Override
		        public void handle(MouseEvent event) {
		            dragDelta.x = stageTela.getX() - event.getScreenX();
		            dragDelta.y = stageTela.getY() - event.getScreenY();
		            xOffset = event.getSceneX();
		            yOffset = event.getSceneY();
		        }
		    });
	
			tela.setOnMouseDragged(new EventHandler<MouseEvent>() {
		        @Override
		        public void handle(MouseEvent event) {
		        	stageTela.setX(event.getScreenX() + dragDelta.x);
		        	stageTela.setY(event.getScreenY() + dragDelta.y);
		        	stageTela.getWidth();
		        	stageTela.getHeight();
		        	stageTela.getX();
		            stageTela.getY();
		            inDrag.set(true);
		        }
		    }); // Fim do evento.
			
			controller.btnConfirmar.setOnAction(e -> {
				resposta = true;
				stageTela.close();
			});
			
			controller.btnCancelar.setOnAction(e -> {
				resposta = false;
				stageTela.close();
			});
			
			// Faz a tela ser obrigatória para voltar ao voltar a tela anterior
			stageTela.setTitle(titulo);
			stageTela.getIcons().add(new Image(Alertas.class.getResourceAsStream("../images/config/icoInformePergunta_48.png")));
			stageTela.initModality(Modality.APPLICATION_MODAL); 
			stageTela.initStyle(StageStyle.TRANSPARENT);
			stageTela.showAndWait(); // Mostra a tela.
		} catch(Exception e) {
			System.out.println("Erro ao tentar carregar o alerta.");
			e.printStackTrace();
		}
		return resposta;
	}
	
	public static void Erro(String titulo, String texto1, String texto2) {
		try {
			FXMLLoader loader = new FXMLLoader(Alertas.class.getResource("/alerts/Erro.fxml"));
			AnchorPane scPnTelaPrincipal = loader.load();
			
			// Obtem a referencia do controller para editar as label.
			ErroController controller = loader.getController();
			
			if (!titulo.equals("")) {
				controller.lblTitulo.setText(titulo);			
			}
			
			if (!texto1.equals("")) {
				controller.lblPrincipal.setText(texto1);			
			}
			
			if (!texto2.equals("")) {
				controller.lblSecundario.setText(texto2);
			}
			
			Scene tela = new Scene(scPnTelaPrincipal); // Carrega a scena
			tela.setFill(Color.TRANSPARENT);
			
			System.out.println("asss");
			Stage stageTela = new Stage();
			stageTela.setScene(tela); // Seta a cena principal
			System.out.println("asss");
			// Eventos de clique do mouse realizando a movimentação da tela.
			tela.setOnMousePressed(new EventHandler<MouseEvent>() {
		        @Override
		        public void handle(MouseEvent event) {
		            dragDelta.x = stageTela.getX() - event.getScreenX();
		            dragDelta.y = stageTela.getY() - event.getScreenY();
		            xOffset = event.getSceneX();
		            yOffset = event.getSceneY();
		        }
		    });
			System.out.println("asss");
			tela.setOnMouseDragged(new EventHandler<MouseEvent>() {
		        @Override
		        public void handle(MouseEvent event) {
		        	stageTela.setX(event.getScreenX() + dragDelta.x);
		        	stageTela.setY(event.getScreenY() + dragDelta.y);
		        	stageTela.getWidth();
		        	stageTela.getHeight();
		        	stageTela.getX();
		            stageTela.getY();
		            inDrag.set(true);
		        }
		    }); // Fim do evento.
			
			controller.btnOk.setOnAction(e -> {
				stageTela.close();
			});
			
			// Faz a tela ser obrigatória para voltar ao voltar a tela anterior
			stageTela.setTitle(titulo);
			stageTela.getIcons().add(new Image(Alertas.class.getResourceAsStream("../images/config/icoErro_48.png")));
			stageTela.initModality(Modality.APPLICATION_MODAL); 
			stageTela.initStyle(StageStyle.TRANSPARENT);
			stageTela.showAndWait(); // Mostra a tela.
		} catch(Exception e) {
			System.out.println("Erro ao tentar carregar o alerta.");
			e.printStackTrace();
		}
	}
}
