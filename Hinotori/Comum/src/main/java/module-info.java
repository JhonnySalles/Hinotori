/**
 * @author Jhonny
 *
 */
module Comum {
	exports model.mask;
	exports model.constraints;
	exports model.cliping;
	exports model.animation;
	exports model.encode;
	exports model.config;
	exports model.mysql;
	exports model.log;
	exports model.alerts;
	exports model.enums;
	exports model.entitis;
	exports model.utils;
	exports model.notification;
	
	requires java.logging;
	requires javafx.base;
	requires transitive javafx.controls;
	requires transitive javafx.graphics;
	requires java.naming;
	requires transitive java.sql;
	requires javafx.fxml;
	requires java.desktop;
	requires transitive com.jfoenix;
	requires org.controlsfx.controls;
	
	opens model.alerts to javafx.fxml;
}