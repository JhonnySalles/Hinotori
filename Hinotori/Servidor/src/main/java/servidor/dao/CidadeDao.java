package servidor.dao;

import javax.persistence.TypedQuery;

import servidor.entities.Cidade;

public class CidadeDao extends Dao<Cidade> {

	final static String SELECT_CIDADE = "SELECT u FROM " + Cidade.TABELA + " u WHERE UPPER(Nome) = UPPER(%s)";

	public Cidade pesquisar(String nome) {
		TypedQuery<Cidade> query = em.createQuery(String.format(SELECT_CIDADE, nome), Cidade.class);
		return query.getSingleResult();
	};

}
