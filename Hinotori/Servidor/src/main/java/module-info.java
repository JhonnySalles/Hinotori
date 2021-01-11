/**
 * @author Jhonny
 *
 */
module Servidor {

	exports servidor.dao;
	exports servidor.dao.services;
	exports servidor.dto;
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
	requires transitive java.sql;
	requires busca.cep.java.client;

	opens servidor.entities to org.hibernate.orm.core;
}