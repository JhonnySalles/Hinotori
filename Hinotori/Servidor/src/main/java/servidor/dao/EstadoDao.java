package servidor.dao;

import javax.persistence.TypedQuery;

import servidor.entities.Estado;

public class EstadoDao extends Dao<Estado> {

	final static String SELECT_ESTADO = "SELECT u FROM " + Estado.TABELA
			+ " u WHERE UPPER(Nome) = '%s' OR UPPER(Sigla) = '%s'";

	/**
	 * <p>
	 * A pesquisa pode ser realizado por nome ou pela UF.
	 * </p>
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public Estado pesquisar(String nome_uf) {
		TypedQuery<Estado> query = em
				.createQuery(String.format(SELECT_ESTADO, nome_uf.toUpperCase(), nome_uf.toUpperCase()), Estado.class);
		return query.getSingleResult();
	};

	public EstadoDao() {
		super(Estado.class);
	};

}
