package servidor.dao.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class HibernateService {

	final static String DELETE = "UPDATE %s SET Situacao = 'EXCLUIDO' WHERE id = %d";

	@PersistenceContext
	EntityManager em;

	protected <T> T salvar(T objeto) {
		em.getTransaction().begin();
		em.persist(objeto);
		em.getTransaction().commit();
		em.close();

		return objeto;
	}

	protected void deletar(Long id, String Tabela) {
		Query q = em.createNativeQuery("BEGIN " + String.format(DELETE, Tabela, id) + " END;");
		q.executeUpdate();
	};

}
