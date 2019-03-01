/**
 * @author Jhonny
 *
 */
module Administrador {
	exports br.com.hinotori.administrador;
	exports br.com.hinotori.administrador.gui;
	exports br.com.hinotori.administrador.gui.cadastros;
	exports br.com.hinotori.administrador.gui.menu;

	requires Comum;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires jfoenix;
}