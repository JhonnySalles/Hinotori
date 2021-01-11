package servidor.dao;

import javax.persistence.TypedQuery;

import servidor.entities.Bairro;

public class BairroDao extends Dao<Bairro> {

	final static String SELECT_BAIRRO = "SELECT u FROM " + Bairro.TABELA + " u WHERE UPPER(Nome) = UPPER(%s)";

	public Bairro pesquisar(String nome) {
		TypedQuery<Bairro> query = em.createQuery(String.format(SELECT_BAIRRO, nome), Bairro.class);
		return query.getSingleResult();
	};

}
