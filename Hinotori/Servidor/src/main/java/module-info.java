/**
 * 
 */
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
	exports servidor.persistencia;

	requires java.persistence;
	requires org.hibernate.orm.core;
	requires java.sql;
	requires org.hibernate.commons.annotations;
	requires transitive Comum;

	opens servidor.entities to org.hibernate.orm.core;
}