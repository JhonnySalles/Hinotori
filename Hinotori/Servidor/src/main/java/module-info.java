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
	
	opens Servidor.persistencia to hibernate.core;
	opens Servidor.entidades to hibernate.core;
	opens Servidor.enumeracao to hibernate.core;

}