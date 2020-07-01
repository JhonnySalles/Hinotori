/**
 * @author Jhonny
 *
 */
module Servidor {
	exports servidor;
	exports servidor.dao;
	exports servidor.dao.services;
	exports servidor.dao.implementJDBC;
	exports servidor.entities;
	exports servidor.converter;
	exports servidor.util;
	exports servidor.validations;

	requires transitive Comum;
	requires transitive java.persistence;
	requires org.hibernate.orm.core;
	requires org.hibernate.commons.annotations;
	requires javafx.base;
	requires java.transaction;
	requires java.naming;


	opens servidor.entities to org.hibernate.orm.core;
}