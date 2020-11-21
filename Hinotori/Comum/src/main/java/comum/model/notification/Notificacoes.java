package comum.model.notification;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.controlsfx.control.Notifications;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import comum.form.DashboardFormPadrao;
import comum.model.alerts.AlertasPopup;
import comum.model.notification.controller.CentralNotificacaoController;
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

	private final static Logger LOGGER = Logger.getLogger(Notificacoes.class.getName());

	private final static LinkedList<Menssagem> LISTA_MENSSAGENS = new LinkedList<Menssagem>();

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

	private static DashboardFormPadrao DASHBOARD_MAIN;
	private static AnchorPane CENTRAL_NOTIFICACAO;
	private static CentralNotificacaoController CENTRAL_CONTROLLER;
	private static NotificacaoController CONTROLLER;
	private static AnchorPane NOTIFICACAO;
	private static AnchorPane ROOT_ANCHOR_PANE;
	private static Timeline TM_LINE_CLOSE;
	private static TranslateTransition TT_AP_NOTIFICACOES;
	private static Boolean APARECENDO;

	// ********************************************************************************//
	// Classe para guardar a referencia das telas aberta e controlador
	// *******************************************************************************//
	public static class Menssagem {
		private String titulo;
		private String menssagem;
		private LocalDateTime hora;
		private AlertType tipo;

		public String getTitulo() {
			return titulo;
		}

		public String getMenssagem() {
			return menssagem;
		}

		public LocalDateTime getHora() {
			return hora;
		}

		public AlertType getTipo() {
			return tipo;
		}

		public Menssagem(String titulo, String menssagem, AlertType tipo) {
			this.titulo = titulo;
			this.menssagem = menssagem;
			this.tipo = tipo;
			this.hora = LocalDateTime.now();
		}
	}

	public static AnchorPane getRootStackPane() {
		return ROOT_ANCHOR_PANE;
	}

	public static AnchorPane getCentralNotificacao() {
		return CENTRAL_NOTIFICACAO;
	}

	public static void limpaNotificacoes() {
		LISTA_MENSSAGENS.clear();
		CENTRAL_CONTROLLER.atualiza();

		DASHBOARD_MAIN.atualizaIcoCentralNotificacao(!LISTA_MENSSAGENS.isEmpty());
	}

	public static void addListaMenssagem(Menssagem menssagem) {
		LISTA_MENSSAGENS.add(menssagem);
		CENTRAL_CONTROLLER.add(menssagem);

		DASHBOARD_MAIN.atualizaIcoCentralNotificacao(!LISTA_MENSSAGENS.isEmpty());
	}

	public static void remListaMenssagem(Menssagem menssagem) {
		LISTA_MENSSAGENS.remove(menssagem);
		CENTRAL_CONTROLLER.remove(menssagem);

		DASHBOARD_MAIN.atualizaIcoCentralNotificacao(!LISTA_MENSSAGENS.isEmpty());
	}

	public static LinkedList<Menssagem> getMenssagens() {
		return LISTA_MENSSAGENS;
	}

	public static void atualizaCentralMenssagem() {
		if ((CENTRAL_NOTIFICACAO == null) || (CENTRAL_CONTROLLER == null))
			createCentralMenssagem();

		CENTRAL_CONTROLLER.atualiza();
	}

	public static void createCentralMenssagem() {
		if (CENTRAL_NOTIFICACAO == null) {
			DASHBOARD_MAIN = DashboardFormPadrao.getInstancia();
			CENTRAL_NOTIFICACAO = DASHBOARD_MAIN.getCentralNotificacoes();
		}

		try {
			FXMLLoader loaderConteudo = new FXMLLoader(CentralNotificacaoController.getFxmlLocate());
			AnchorPane spConteudo = loaderConteudo.load();
			CENTRAL_CONTROLLER = loaderConteudo.getController();
			CENTRAL_NOTIFICACAO.getChildren().add(spConteudo);
			AnchorPane.setTopAnchor(spConteudo, 0.0);
			AnchorPane.setBottomAnchor(spConteudo, 0.0);
			AnchorPane.setLeftAnchor(spConteudo, 0.0);
			AnchorPane.setRightAnchor(spConteudo, 0.0);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "{Erro ao carregar a central de menssagem do dashboard}", e);
		}
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

	public static void closeNotificacao() {
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
			if (ROOT_ANCHOR_PANE == null) {
				DASHBOARD_MAIN = DashboardFormPadrao.getInstancia();
				ROOT_ANCHOR_PANE = DASHBOARD_MAIN.getRootStackPane();
			}

			FXMLLoader loader = new FXMLLoader(NotificacaoController.getFxmlLocate());
			NOTIFICACAO = loader.load();
			CONTROLLER = loader.getController();
			ROOT_ANCHOR_PANE.getChildren().add(NOTIFICACAO);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "{Erro ao criar campo de aviso}", e);
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
	 * @param tipo   Tipo da notificação solicitada, podendo ser ALERTA (Alert),
	 *               AVISO (Warning), ERRO (Error), SUCESSO (None).
	 * @param titulo Titulo da notificação.
	 * @param texto  Texto da notificação.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public static void notificacao(AlertType tipo, String titulo, String texto) {
		if ((NOTIFICACAO == null) || (CONTROLLER == null))
			create();

		addListaMenssagem(new Menssagem(titulo, texto, tipo));

		if (DashboardFormPadrao.CENTRAL_OPENED)
			return;

		if (tipo == AlertType.INFORMATION)
			LOGGER.log(Level.INFO, "{Mensagem de aviso ou alerta: " + texto + "}");

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
		addListaMenssagem(new Menssagem(titulo, texto, AlertType.INFORMATION));
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
		addListaMenssagem(new Menssagem(titulo, texto, AlertType.INFORMATION));
		Notifications notificacao = Notifications.create().title(titulo).text(texto)
				.hideAfter(Duration.seconds(duracao)).position(posicao);
		notificacao.show();
	}

}
