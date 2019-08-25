package model.notification;

import org.controlsfx.control.Notifications;

import javafx.geometry.Pos;
import javafx.util.Duration;

public class Notificacao {	
	public static void Dark(String titulo, String texto, Double duracao, Pos posicao) {
		Notifications notificacao = Notifications.create().title(titulo)
				.text(texto).hideAfter(Duration.seconds(duracao))
				.position(posicao).darkStyle();
		notificacao.show();
	}
	
	public static void White(String titulo, String texto, Double duracao, Pos posicao) {
		Notifications notificacao = Notifications.create().title(titulo)
				.text(texto).hideAfter(Duration.seconds(duracao))
				.position(posicao);
		notificacao.show();
	}

}
