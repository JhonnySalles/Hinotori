/**
 * @author Jhonny
 *
 */
module Servidor {

	exports servidor.dao.services;
	exports servidor.entities;
	exports servidor.converter;
	exports servidor.util;
	exports servidor.validations;
	exports servidor.persistence;

	requires transitive Comum;
	requires transitive java.persistence;
	requires org.hibernate.orm.core;
	requires org.hibernate.commons.annotations;
	requires javafx.base;
	requires java.transaction;
	requires java.naming;
	requires java.sql;

	opens servidor.entities to org.hibernate.orm.core;
}