/**
 * 
 */
/**
 * @author Jhonny
 *
 */
module Servidor {
	exports servidor.persistencia;
	exports servidor.entidades;
	exports servidor.enumeracao;
	exports servidor;

	requires java.persistence;
	requires org.hibernate.orm.core;
	requires java.sql;
	requires org.hibernate.commons.annotations;

	opens servidor.entidades to org.hibernate.orm.core;
}