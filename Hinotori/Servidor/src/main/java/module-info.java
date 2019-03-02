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
}