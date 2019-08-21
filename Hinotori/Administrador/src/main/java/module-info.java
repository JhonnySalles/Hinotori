/**
 * @author Jhonny
 *
 */
module Administrador {

	exports Administrador;
	exports Administrador.controller;
	exports Administrador.controller.cadastros;
	exports Administrador.controller.menu;
	exports Administrador.controller.frame;
	exports Administrador.model.entities;

	requires transitive Comum;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires com.jfoenix;
	requires java.desktop;
	requires java.sql;
	requires org.controlsfx.controls;
	requires AnimateFX;

	/* Faz a abertura dos pacotes do javafx para ser utilizado nas clases */
	opens Administrador.controller to javafx.fxml;
	opens Administrador.controller.cadastros to javafx.fxml;
	opens Administrador.controller.menu to javafx.fxml;
	opens Administrador.controller.frame to javafx.fxml;

}