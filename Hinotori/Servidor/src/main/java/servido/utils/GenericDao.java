package Servidor.utils;


import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
@SuppressWarnings("unchecked")
public abstract class GenericDao<T, PK> {
	
	private final EntityManager em;
	private final EntityManagerFactory emf;
	
	private Class<?> c;

	public GenericDao() {
		this (DaoFactory.entityManagerFactorInstance());
		// TODO Auto-generated constructor stub
	}

	public GenericDao(EntityManagerFactory emf) {
		this.emf = emf;
		this.em = emf.createEntityManager();
		this.c = (Class<?>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	//Crud	
	public Object executeQuery(String query,Object... params) {
		Query createdQuery = this.em.createQuery(query);
		for (int i =0; i < params.length;i++) {
			createdQuery.setParameter(i,params[i]);
		}
		return createdQuery.getResultList();
	}
	
	public List<T> findAll(){
			return this.em.createQuery( (" From " + this.c.getName())).getResultList();
	}
	
	public T findById(PK pk) {
		return (T) this.em.find(this.c, pk);
	}
	
	public void save(T entity) {
		try {
			this.beginTransaction();
			this.em.persist(entity);
			this.commit();
		} catch (Exception e) {
			this.rollBack();
			throw e;
		}
	}
	
	public void update(T entity) {
		try {
			this.beginTransaction();
			this.em.merge(entity);
			this.commit();
		} catch (Exception e) {
			this.rollBack();
			throw e;
		}
	}
	
	public void delete(T entity) {
		try {
			this.beginTransaction();
			this.em.remove(entity);
			this.commit();
		} catch (Exception e) {
			this.rollBack();
			throw e;
		}
	}
	
	//Metodos de transição	
	public void beginTransaction() {
		this.em.getTransaction().begin();
	}
	
	public void commit() {
		this.em.getTransaction().commit();
	}
	
	public void close() {
		this.em.close();
		this.emf.close();
	}
	
	public void rollBack() {
		this.em.getTransaction().rollback();
	}
	
	public EntityManager getEntityManager() {
		return this.em;
	}
}
