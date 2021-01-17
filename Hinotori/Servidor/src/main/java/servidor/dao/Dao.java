package servidor.dao;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import comum.model.entities.Entidade;
import servidor.configuration.ManagerFactory;

public class Dao<E extends Entidade> {

	final static String DELETE = "UPDATE %s SET Situacao = 'EXCLUIDO' WHERE id = %d";
	final static String SELECT = "SELECT e FROM %s e ";

	protected EntityManager em;
	private Class<E> classe;
	private E lastEntity;

	public EntityManager getEntityManager() {
		return em;
	}

	public E getLastEntity() {
		return lastEntity;
	}

	private String getTabela() {
		// Para pesquisa deve-se retornar o nome da classe.
		return classe.getName();
	}

	public Dao() {
		this(null);
	}

	public Dao(Class<E> classe) {
		this.classe = classe;
		em = ManagerFactory.getEtityManager();
	}

	public Dao<E> begin() {
		em.getTransaction().begin();
		return this;
	}

	public Dao<E> commit() {
		em.getTransaction().commit();
		return this;
	}

	public Dao<E> rolback() {
		em.getTransaction().rollback();
		return this;
	}

	public Dao<E> salvar(E entidade) throws EntityExistsException {
		lastEntity = entidade;
		if (entidade.getId().compareTo(0L) == 0)
			em.persist(entidade);
		else
			em.merge(entidade);

		return this;
	}

	public Dao<E> salvarAtomico(E entidade) throws EntityExistsException {
		return this.begin().salvar(entidade).commit();
	}

	public Dao<E> remover(Long id) {
		Query querry = em.createNativeQuery("BEGIN " + String.format(DELETE, getTabela(), id) + " END;");
		querry.executeUpdate();
		return this;
	}

	public Dao<E> removerAtomico(Long id) {
		return this.begin().remover(id).commit();
	}

	public Dao<E> remover(E entidade) {
		lastEntity = entidade;
		remover(entidade.getId());
		return this;
	}

	public Dao<E> removerAtomico(E entidade) {
		return this.begin().remover(entidade).commit();
	}

	public E pesquisar(Long id) {
		if (classe == null)
			throw new UnsupportedOperationException("Classe nula, deve-se informar a classe no construtor.");

		E entidade = em.find(classe, id);

		if (entidade == null)
			return entidade;

		// Desfaz o vinculo do hibernate para que não seja refletido ao banco a
		// alteração na classe.
		em.detach(entidade);
		return entidade;
	};

	public List<E> listar() {
		return listar(-1, -1);
	}

	public List<E> listar(int registros) {
		return listar(0, registros);
	}

	public List<E> listar(int primeiroRegistro, int registros) {
		if (classe == null)
			throw new UnsupportedOperationException("Classe nula, deve-se informar a classe no construtor.");

		TypedQuery<E> query = em.createQuery(String.format(SELECT, getTabela()), classe);

		if (registros >= 0)
			query.setMaxResults(registros);

		if (primeiroRegistro >= 0)
			query.setFirstResult(primeiroRegistro);

		return query.getResultList();
	}

	public void close() {
		em.close();
	}

}
