/**
 * @author Jhonny
 *
 */
module Servidor {

	exports servidor.configuration;
	exports servidor.dao;
	exports servidor.dao.services;
	exports servidor.dto;
	exports servidor.entities;
	exports servidor.converter;
	exports servidor.validations;
	exports servidor.util;

	requires transitive Comum;
	requires transitive java.persistence;
	requires org.hibernate.orm.core;
	requires org.hibernate.commons.annotations;
	requires javafx.base;
	requires java.transaction;
	requires java.naming;
	requires transitive java.sql;
	requires org.flywaydb.core;
	requires javafx.fxml;
	requires javafx.controls;
	requires com.jfoenix;
	requires javafx.graphics;

	opens servidor.entities to org.hibernate.orm.core;
	opens db.migration;
}