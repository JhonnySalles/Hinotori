/**
 * @author Jhonny
 *
 */
module Config {

	exports Config.connection;
	exports Config;
	exports Config.entitis;
	exports Config.alerts;
	exports Config.util;
	exports Config.gui;

	requires Comum;
	requires java.sql;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires Servidor;
	
	/* Faz a abertura dos pacotes do javafx para ser utilizado nas clases */
	opens Config.gui to javafx.fxml;
	opens Config.alerts to javafx.fxml;
	
}