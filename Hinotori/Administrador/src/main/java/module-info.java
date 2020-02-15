/**
 * @author Jhonny
 *
 */
module administrador {

	exports administrador;
	exports administrador.controller;
	exports administrador.controller.cadastros;
	exports administrador.controller.pesquisas;
	exports administrador.controller.frame;

	requires transitive Comum;
	requires transitive javafx.fxml;
	requires transitive javafx.graphics;
	requires transitive Servidor;
	requires javafx.base;
	requires javafx.controls;
	requires com.jfoenix;
	requires java.desktop;
	requires java.sql;
	requires org.controlsfx.controls;
	requires AnimateFX;

	/* Faz a abertura dos pacotes do javafx para ser utilizado nas clases */
	opens administrador.controller to javafx.fxml;
	opens administrador.controller.cadastros to javafx.fxml;
	opens administrador.controller.frame to javafx.fxml;
	opens administrador.controller.metricas to javafx.fxml;

}