/**
 * @author Jhonny
 *
 */
module Administrador {
	
	exports restaurante;
	exports restaurante.gui;

	requires Comum;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires com.jfoenix;
	requires java.desktop;
	
	/* Faz a abertura dos pacotes do javafx para ser utilizado nas clases */
	opens restaurante.gui to javafx.fxml;
	
}