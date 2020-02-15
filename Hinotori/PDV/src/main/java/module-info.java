module PDV {
	exports pdv;
	exports pdv.controller;
	exports pdv.controller.cadastros;
	exports pdv.controller.frame;
	exports pdv.controller.metricas;
	exports pdv.model.entities;

	requires transitive Comum;
	requires com.jfoenix;
	requires javafx.base;
	requires javafx.controls;
	requires transitive javafx.fxml;
	requires transitive javafx.graphics;
	requires java.desktop;
	requires org.controlsfx.controls;
	requires AnimateFX;
	requires java.sql;

	/* Faz a abertura dos pacotes do javafx para ser utilizado nas clases */
	opens pdv.controller to javafx.fxml;
	opens pdv.controller.cadastros to javafx.fxml;
	opens pdv.controller.frame to javafx.fxml;
	opens pdv.controller.metricas to javafx.fxml;
}