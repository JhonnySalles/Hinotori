/**
 * 
 */
/**
 * @author Jhonny
 *
 */
module Servidor {
	exports Servidor.persistencia;
	exports Servidor.entidades;
	exports Servidor.enumeracao;
	exports Servidor;

	requires java.persistence;
	requires org.hibernate.orm.core;
	requires java.sql;
	requires org.hibernate.commons.annotations;
	
	opens Servidor.entidades to org.hibernate.orm.core;
}