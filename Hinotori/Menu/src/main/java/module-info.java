
/**
 * @author Jhonny
 *
 */
module Menu {

	exports menu.controller;
	exports menu;

	requires Comum;
	requires Servidor;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	
	/* Faz a abertura dos pacotes do javafx para ser utilizado nas clases */
	opens menu.controller to javafx.fxml;

}