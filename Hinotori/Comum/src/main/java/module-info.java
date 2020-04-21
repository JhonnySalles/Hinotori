module Comum {
	exports comum.model.animation;
	exports comum.model.config;
	exports comum.model.constraints;
	exports comum.model.encode;
	exports comum.model.entities;
	exports comum.model.enums;
	exports comum.model.exceptions;
	exports comum.model.mask;
	exports comum.model.messages;
	exports comum.model.mysql;
	exports comum.model.notification;
	exports comum.model.utils;
	exports comum.controller.alerts;
	exports comum.form;

	requires java.logging;
	requires javafx.base;
	requires javafx.fxml;
	requires org.controlsfx.controls;
	requires transitive com.jfoenix;
	requires transitive java.sql;
	requires transitive javafx.controls;
	requires transitive javafx.graphics;
	requires transitive java.desktop;
	requires AnimateFX;
	
	opens comum.controller.alerts to javafx.fxml;
	opens comum.form to javafx.fxml;
}