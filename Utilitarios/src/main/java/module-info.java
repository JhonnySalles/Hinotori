/**
 * @author Jhonny
 *
 */
module Utilitarios {
	exports utilitarios.gui;
	exports utilitarios.gui.encode;
	exports utilitarios;
	exports utilitarios.model.encode;

	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires com.jfoenix;
	requires controlsfx;

	/* Faz a abertura dos pacotes do javafx para ser utilizado nas clases */
	opens utilitarios.gui to javafx.fxml;
	opens utilitarios.gui.encode to javafx.fxml;
}