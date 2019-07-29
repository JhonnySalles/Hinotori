/**
 * @author Jhonny
 *
 */
module Administrador {
	
	exports Administrador;
	exports Administrador.gui;
	exports Administrador.gui.cadastros;
	exports Administrador.gui.menu;
	exports Administrador.gui.frame;
	exports Administrador.entities;

	requires Comum;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires com.jfoenix;
	requires java.desktop;
	
	/* Faz a abertura dos pacotes do javafx para ser utilizado nas clases */
	opens Administrador.gui to javafx.fxml;
	opens Administrador.gui.cadastros to javafx.fxml;
	opens Administrador.gui.menu to javafx.fxml;
	opens Administrador.gui.frame to javafx.fxml;
	
}