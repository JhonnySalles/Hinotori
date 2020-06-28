package servidor.dao.implementJPA;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import comum.model.enums.TamanhoImagem;
import comum.model.exceptions.ExcessaoBd;
import servidor.dao.UsuarioDao;
import servidor.entities.Usuario;

public class UsuarioDaoJPA implements UsuarioDao {

	final String DELETE = "UPDATE Situacao = 'EXCLUIDO' WHERE id = ";

	final String SELECT_ALL = "SELECT u FROM usuarios u WHERE Situacao <> 'EXCLUIDO' AND Id <> 0";

	final String SELECT = "SELECT u FROM usuarios u WHERE Id = ?";

	final String SELECT_LOGIN = "SELECT u FROM usuarios u WHERE Login = ? AND SITUACAO = 'ATIVO' ";

	final String SELECT_LISTA_LOGIN = "SELECT Login FROM usuarios WHERE SITUACAO = 'ATIVO' ";

	final String SELECT_EXISTE_LOGIN = "SELECT Login FROM usuarios u WHERE Login = ? AND ID <> ?";

	@PersistenceContext
	EntityManager em;

	@Override
	public Usuario insert(Usuario obj) {
		em.getTransaction().begin();
		em.persist(obj);
		em.getTransaction().commit();
		em.close();

		return obj;
	}

	@Override
	public Usuario update(Usuario obj) throws ExcessaoBd {
		em.getTransaction().begin();
		em.persist(obj);
		em.getTransaction().commit();
		em.close();

		return obj;
	}

	@Override
	public Long delete(Long id) throws ExcessaoBd {
		Query q = em.createNativeQuery("BEGIN " + DELETE + id + " END;");
		q.executeUpdate();

		return id;
	}

	@Override
	public Usuario find(Long id, TamanhoImagem tamanho) {
		return em.find(Usuario.class, id);
	}

	@Override
	public List<Usuario> findAll(TamanhoImagem tamanho) {
		return em.createQuery(SELECT_ALL, Usuario.class).getResultList();
	}

	@Override
	public Boolean validaLogin(Long id, String login) {
		String query = SELECT_EXISTE_LOGIN;
		query.replaceFirst("?", login).replaceFirst("?", id.toString());
		TypedQuery<Boolean> booleanQuery = em.createQuery(query, Boolean.class);
		return booleanQuery.getSingleResult();
	}

	@Override
	public Usuario find(String login) throws ExcessaoBd {
		TypedQuery<Usuario> query = em.createQuery(SELECT_LOGIN, Usuario.class);
		return query.getSingleResult();
	}

	@Override
	public List<String> findLogins() throws ExcessaoBd {
		TypedQuery<String> stringQuery = em.createQuery(SELECT_LISTA_LOGIN, String.class);
		return stringQuery.getResultList();
	}
}
