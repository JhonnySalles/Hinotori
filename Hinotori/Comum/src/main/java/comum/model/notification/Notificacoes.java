package comum.model.notification;

import java.io.IOException;

import org.controlsfx.control.Notifications;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import comum.model.alerts.AlertasPopup;
import comum.model.notification.controller.NotificacaoController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Notificacoes {

	public final static ImageView IMG_ALERTA = new ImageView(
			new Image(AlertasPopup.class.getResourceAsStream("/comum/resources/imagens/alerta/icoAlerta_48.png")));
	public final static ImageView IMG_AVISO = new ImageView(
			new Image(AlertasPopup.class.getResourceAsStream("/comum/resources/imagens/alerta/icoAviso_48.png")));
	public final static ImageView IMG_ERRO = new ImageView(
			new Image(AlertasPopup.class.getResourceAsStream("/comum/resources/imagens/alerta/icoErro_48.png")));
	public final static ImageView IMG_CONFIRMA = new ImageView(
			new Image(AlertasPopup.class.getResourceAsStream("/comum/resources/imagens/alerta/icoConfirma_48.png")));
	public final static ImageView IMG_SUCESSO = new ImageView(
			new Image(AlertasPopup.class.getResourceAsStream("/comum/resources/imagens/alerta/btnConfirma_48.png")));

	private static NotificacaoController CONTROLLER;
	private static AnchorPane NOTIFICACAO;
	private static AnchorPane ROOT_ANCHOR_PANE;
	private static Timeline TM_LINE_CLOSE;
	private static TranslateTransition TT_AP_NOTIFICACOES;
	private static Boolean APARECENDO;

	public static AnchorPane getRootStackPane() {
		return ROOT_ANCHOR_PANE;
	}

	public static void setRootStackPane(AnchorPane rootAnchorkPane) {
		Notificacoes.ROOT_ANCHOR_PANE = rootAnchorkPane;
	}

	
	private static void setTipo(AlertType tipo, NotificacaoController controller, AnchorPane root) {
		root.getStyleClass().clear();
		switch (tipo) {
		case WARNING:
			root.getStyleClass().add("Notificacao_AlertaBackground");
			controller.setImagem(IMG_ALERTA);
			break;

		case INFORMATION:
			root.getStyleClass().add("Notificacao_AvisoBackground");
			controller.setImagem(IMG_AVISO);
			break;

		case ERROR:
			root.getStyleClass().add("Notificacao_ErroBackground");
			controller.setImagem(IMG_ERRO);
			break;

		case NONE:
			root.getStyleClass().add("Notificacao_SucessoBackground");
			controller.setImagem(IMG_SUCESSO);
			break;

		default:
			root.getStyleClass().add("Notificacao_AvisoBackground");
			controller.setImagem(IMG_AVISO);
		}
	}

	private static void abreNotificacao() {
		TT_AP_NOTIFICACOES.stop();
		TT_AP_NOTIFICACOES.setToY(0);
		TT_AP_NOTIFICACOES.play();
	}

	private static void closeNotificacao() {
		TT_AP_NOTIFICACOES.stop();
		TT_AP_NOTIFICACOES.setToY(CONTROLLER.wheight + 5);
		TT_AP_NOTIFICACOES.play();
	}

	private static void clear() {
		APARECENDO = NOTIFICACAO.getTranslateY() == 0;
		if (!(NOTIFICACAO.getTranslateY() == 0)) {
			TM_LINE_CLOSE.stop();
			NOTIFICACAO.toBack();
		}
	}

	private static void create() {
		try {
			FXMLLoader loader = new FXMLLoader(NotificacaoController.getFxmlLocate());
			NOTIFICACAO = loader.load();
			CONTROLLER = loader.getController();
			ROOT_ANCHOR_PANE.getChildren().add(NOTIFICACAO);
		} catch (IOException e) {
			e.printStackTrace();
		}

		TT_AP_NOTIFICACOES = new TranslateTransition(new Duration(500), NOTIFICACAO);
		TT_AP_NOTIFICACOES.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				clear();
			}
		});
		TM_LINE_CLOSE = new Timeline(new KeyFrame(Duration.millis(10000), close -> closeNotificacao()));
		APARECENDO = false;
	}

	/**
	 * <p>
	 * Função para apresentar um popup com algumas informações como aviso, alertas
	 * etc.
	 * </p>
	 * 
	 * <p>
	 * A referência para o controle é obrigatóriamente recebida apenas uma vez na
	 * inicialização.
	 * </p>
	 * 
	 * @param tipo   Tipo da notificação solicitada, podendo ser ALERTA (Alert), AVISO (Warning),
	 *               ERRO (Error), SUCESSO (None).
	 * @param titulo Titulo da notificação.
	 * @param texto  Texto da notificação.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public static void notificacao(AlertType tipo, String titulo, String texto) {
		if ((NOTIFICACAO == null) || (CONTROLLER == null))
			create();

		TM_LINE_CLOSE.stop();
		if (!APARECENDO) {
			setTipo(tipo, CONTROLLER, NOTIFICACAO);

			NOTIFICACAO.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent e) {
					closeNotificacao();
				}
			});

			NOTIFICACAO.toFront();
			CONTROLLER.setTitulo(titulo).setTexto(texto);
			AnchorPane.setBottomAnchor(NOTIFICACAO, 5.0);
			AnchorPane.setRightAnchor(NOTIFICACAO, 5.0);
			NOTIFICACAO.setTranslateY(CONTROLLER.wheight + 5);

			abreNotificacao();
		} else {
			FadeOut fade = new FadeOut(NOTIFICACAO);
			fade.setOnFinished(fadeOut -> {
				setTipo(tipo, CONTROLLER, NOTIFICACAO);
				CONTROLLER.setTitulo(titulo).setTexto(texto);
				new FadeIn(NOTIFICACAO).play();
			});
			fade.setSpeed(100);
			fade.play();
		}

		TM_LINE_CLOSE.play();
	}

	/**
	 * <p>
	 * Notificação indepentende do sistema, onde é apresentado através da biblioteca
	 * <b>Notificatiions</b>.
	 * </p>
	 * 
	 * @param titulo  Titulo que será apresentado na notificação.
	 * @param texto   Texto a ser apresentado.
	 * @param duracao Duração da sua exibição.
	 * @param posicao Sua posição na tela.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public static void windowsDark(String titulo, String texto, Double duracao, Pos posicao) {
		Notifications notificacao = Notifications.create().title(titulo).text(texto)
				.hideAfter(Duration.seconds(duracao)).position(posicao).darkStyle();
		notificacao.show();
	}

	/**
	 * <p>
	 * Notificação indepentende do sistema, onde é apresentado através da biblioteca
	 * <b>Notificatiions</b>.
	 * </p>
	 * 
	 * @param titulo  Titulo que será apresentado na notificação.
	 * @param texto   Texto a ser apresentado.
	 * @param duracao Duração da sua exibição.
	 * @param posicao Sua posição na tela.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public static void windowsWhite(String titulo, String texto, Double duracao, Pos posicao) {
		Notifications notificacao = Notifications.create().title(titulo).text(texto)
				.hideAfter(Duration.seconds(duracao)).position(posicao);
		notificacao.show();
	}

}
