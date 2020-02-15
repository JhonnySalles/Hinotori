module Config {
	exports config.gui;
	exports config.connection;
	exports config.util;
	exports config.alerts;
	exports config;

	requires transitive Comum;
	requires java.sql;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires com.jfoenix;
	
	/* Faz a abertura dos pacotes do javafx para ser utilizado nas clases */
	opens config.gui to javafx.fxml;
	opens config.alerts to javafx.fxml;	
}