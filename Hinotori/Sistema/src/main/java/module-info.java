/**
 * @author Jhonny
 *
 */
module Sistema {
	
	exports Sistema.util;
	exports Sistema.guiMain;
	exports Sistema;

	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	
	/* Faz a abertura dos pacotes do javafx para ser utilizado nas clases */
	opens Sistema.guiMain to javafx.fxml;
	
}