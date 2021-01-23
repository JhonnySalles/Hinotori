package servidor.dao;

import javax.persistence.TypedQuery;

import servidor.entities.Cliente;

public class ClienteDao extends Dao<Cliente> {

	final static String SELECT_EXISTE_CFP = "SELECT c FROM " + Cliente.class.getName() + " c WHERE Cpf = '%s' ";

	final static String SELECT_EXISTE_CNPJ = "SELECT c FROM " + Cliente.class.getName() + " c WHERE Cnpj = '%s' ";

	public Cliente existeCPF(String cpf) {
		String query = String.format(SELECT_EXISTE_CFP, cpf);
		TypedQuery<Cliente> booleanQuery = em.createQuery(query, Cliente.class);

		if (booleanQuery.getFirstResult() == 1)
			return booleanQuery.getSingleResult();

		return null;
	}

	public Cliente existeCNPJ(String cnpj) {
		String query = String.format(SELECT_EXISTE_CNPJ, cnpj);
		TypedQuery<Cliente> booleanQuery = em.createQuery(query, Cliente.class);

		if (booleanQuery.getFirstResult() == 1)
			return booleanQuery.getSingleResult();

		return null;
	};
}
