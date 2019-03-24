/**
 * @author Jhonny
 *
 */
module Utilitarios {
	exports Utilitarios.gui;
	exports Utilitarios.gui.encode;
	exports Utilitarios;
	exports Utilitarios.model.encode;

	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires com.jfoenix;
	
	/* Faz a abertura dos pacotes do javafx para ser utilizado nas clases */
	opens Utilitarios.gui to javafx.fxml;
	opens Utilitarios.gui.encode to javafx.fxml;
}