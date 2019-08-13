package model.alerts;

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
import model.log.ManipulaLog;

public class Alertas {

	static boolean resposta;

	@SuppressWarnings("unused")
	private static double xOffset = 0;
	@SuppressWarnings("unused")
	private static double yOffset = 0;

	private static class Delta {
		double x, y;
	}

	final static Delta dragDelta = new Delta();
	final static BooleanProperty inDrag = new SimpleBooleanProperty(false);

	public static boolean Confirmacao(String titulo, String texto) {
		try {
			FXMLLoader loader = new FXMLLoader(Alertas.class.getResource("Confirmacao.fxml"));
			AnchorPane scPnTelaPrincipal = loader.load();

			// Obtem a referencia do controller para editar as label.
			ConfirmacaoController controller = loader.getController();

			if (!titulo.equals("")) {
				controller.lblTitulo.setText(titulo);
			}

			if (!texto.equals("")) {
				controller.lblPrincipal.setText(texto);
			}

			Scene tela = new Scene(scPnTelaPrincipal); // Carrega a scena
			tela.setFill(Color.TRANSPARENT);

			Stage stageTela = new Stage();
			stageTela.setScene(tela); // Seta a cena principal

			// Eventos de clique do mouse realizando a movimenta��o da tela.
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

			// Faz a tela ser obrigatoria para voltar ao voltar a tela anterior
			stageTela.setTitle(titulo);
			stageTela.getIcons()
					.add(new Image(Alertas.class.getResourceAsStream("/resources/alerts/icoInformePergunta_48.png")));
			stageTela.initModality(Modality.APPLICATION_MODAL);
			stageTela.initStyle(StageStyle.TRANSPARENT);
			stageTela.showAndWait(); // Mostra a tela.
		} catch (Exception e) {
			System.out.println("Erro ao tentar carregar o alerta.");
			e.printStackTrace();
			ManipulaLog.salvar(Alertas.class, "ERRO - AO ABRIR ALERTA", "", e.toString());
		}
		return resposta;
	}

	public static void Erro(String titulo, String texto, String erro, Boolean mostrarErro, Boolean salvarLog) {
		if (salvarLog)
			ManipulaLog.salvar(Alertas.class, "ERRO - ALERTA", "", erro);

		try {
			FXMLLoader loader = new FXMLLoader(Alertas.class.getResource("Erro.fxml"));
			AnchorPane scPnTelaPrincipal = loader.load();

			// Obtem a referencia do controller para editar as label.
			ErroController controller = loader.getController();

			if (!titulo.equals("")) {
				controller.lblTitulo.setText(titulo);
			}

			if (!texto.equals("")) {
				controller.lblPrincipal.setText(texto);
			}

			if (!erro.equals("") && mostrarErro) {
				controller.txtAreaErro.setText(erro);
				controller.txtAreaErro.setVisible(true);
				controller.txtAreaErro.setEditable(true);
			}

			Scene tela = new Scene(scPnTelaPrincipal); // Carrega a scena
			tela.setFill(Color.TRANSPARENT);

			Stage stageTela = new Stage();
			stageTela.setScene(tela); // Seta a cena principal

			// Eventos de clique do mouse realizando a movimentacao da tela.
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

			controller.btnOk.setOnAction(e -> {
				stageTela.close();
			});

			if (!erro.equals("") && mostrarErro) {
				stageTela.setHeight(300);
			}

			// Faz a tela ser obrigatoria para voltar ao voltar a tela anterior
			stageTela.setTitle(titulo);
			stageTela.getIcons().add(new Image(Alertas.class.getResourceAsStream("/resources/alerts/icoErro_48.png")));
			stageTela.initModality(Modality.APPLICATION_MODAL);
			stageTela.initStyle(StageStyle.TRANSPARENT);
			stageTela.showAndWait(); // Mostra a tela.
		} catch (Exception e) {
			System.out.println("Erro ao tentar carregar o alerta.");
			e.printStackTrace();
			ManipulaLog.salvar(Alertas.class, "ERRO - AO ABRIR ALERTA", "", e.toString());
		}
	}

	public static void Concluido(String titulo, String texto) {
		try {
			FXMLLoader loader = new FXMLLoader(Alertas.class.getResource("Concluido.fxml"));
			AnchorPane scPnTelaPrincipal = loader.load();

			// Obtem a referencia do controller para editar as label.
			ConcluidoController controller = loader.getController();

			if (!titulo.equals("")) {
				controller.lblTitulo.setText(titulo);
			}

			if (!texto.equals("")) {
				controller.lblPrincipal.setText(texto);
			}

			Scene tela = new Scene(scPnTelaPrincipal); // Carrega a scena
			tela.setFill(Color.TRANSPARENT);

			Stage stageTela = new Stage();
			stageTela.setScene(tela); // Seta a cena principal

			// Eventos de clique do mouse realizando a movimentacao da tela.
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

			controller.btnOk.setOnAction(e -> {
				stageTela.close();
			});

			// Faz a tela ser obrigatoria para voltar ao voltar a tela anterior
			stageTela.setTitle(titulo);
			stageTela.getIcons()
					.add(new Image(Alertas.class.getResourceAsStream("/resources/alerts/icoConfimacao_48.png")));
			stageTela.initModality(Modality.APPLICATION_MODAL);
			stageTela.initStyle(StageStyle.TRANSPARENT);
			stageTela.showAndWait(); // Mostra a tela.
		} catch (Exception e) {
			System.out.println("Erro ao tentar carregar o alerta.");
			e.printStackTrace();
			ManipulaLog.salvar(Alertas.class, "ERRO - AO ABRIR ALERTA", "", e.toString());
		}
	}

	public static void Aviso(String titulo, String texto) {
		try {
			FXMLLoader loader = new FXMLLoader(Alertas.class.getResource("Aviso.fxml"));
			AnchorPane scPnTelaPrincipal = loader.load();

			// Obtem a referencia do controller para editar as label.
			AvisoController controller = loader.getController();

			if (!titulo.equals("")) {
				controller.lblTitulo.setText(titulo);
			}

			if (!texto.equals("")) {
				controller.lblPrincipal.setText(texto);
			}

			Scene tela = new Scene(scPnTelaPrincipal); // Carrega a scena
			tela.setFill(Color.TRANSPARENT);

			Stage stageTela = new Stage();
			stageTela.setScene(tela); // Seta a cena principal

			// Eventos de clique do mouse realizando a movimentacao da tela.
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

			controller.btnOk.setOnAction(e -> {
				stageTela.close();
			});

			// Faz a tela ser obrigatoria para voltar ao voltar a tela anterior
			stageTela.setTitle(titulo);
			stageTela.getIcons().add(new Image(Alertas.class.getResourceAsStream("/resources/alerts/icoAviso_48.png")));
			stageTela.initModality(Modality.APPLICATION_MODAL);
			stageTela.initStyle(StageStyle.TRANSPARENT);
			stageTela.showAndWait(); // Mostra a tela.
		} catch (Exception e) {
			System.out.println("Erro ao tentar carregar o alerta.");
			e.printStackTrace();
			ManipulaLog.salvar(Alertas.class, "ERRO - AO ABRIR ALERTA", "", e.toString());
		}
	}

}
