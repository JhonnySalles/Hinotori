package servidor.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.TypedQuery;

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
		// Checka a anotação da classe e obtem a tabela informada na anotação
		Table tabela = classe.getAnnotation(Table.class);
		return (tabela == null ? classe.getName() : tabela.name());
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

	public Dao<E> salvar(E entidade) {
		lastEntity = entidade;
		if (entidade.getId().compareTo(0L) == 0)
			em.persist(entidade);
		else
			em.merge(entidade);

		return this;
	}

	public Dao<E> salvarAtomico(E entidade) {
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
		E entidade = em.find(classe, id);
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
			throw new UnsupportedOperationException("Classe nula.");

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
