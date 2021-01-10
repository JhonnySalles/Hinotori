package servidor.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import comum.model.enums.TamanhoImagem;
import servidor.entities.Usuario;

public class UsuarioDao extends Dao<Usuario> {

	final static String SELECT_LISTA_LOGIN = "SELECT Login FROM usuarios WHERE SITUACAO = 'ATIVO' ";
	final static String SELECT_LOGIN = "SELECT u FROM usuarios u WHERE Login = ? AND SITUACAO = 'ATIVO' ";
	final static String SELECT_ALL = "SELECT u FROM usuarios u WHERE Situacao <> 'EXCLUIDO' AND Id <> 0";
	final static String SELECT_EXISTE_LOGIN = "SELECT Login FROM usuarios u WHERE Login = ? AND ID <> ?";
	
	public Boolean validaLogin(Long id, String login) {
		String query = SELECT_EXISTE_LOGIN;
		query.replaceFirst("?", login).replaceFirst("?", id.toString());
		TypedQuery<Boolean> booleanQuery = em.createQuery(query, Boolean.class);
		return booleanQuery.getSingleResult();
	}

	public Usuario pesquisar(String login) {
		TypedQuery<Usuario> query = em.createQuery(SELECT_LOGIN, Usuario.class);
		return query.getSingleResult();
	};

	public List<Usuario> pesquisarTodos(TamanhoImagem tamanho) {
		return em.createQuery(SELECT_ALL, Usuario.class).getResultList();
	};

	public List<String> carregaLogins() {
		TypedQuery<String> stringQuery = em.createQuery(SELECT_LISTA_LOGIN, String.class);
		return stringQuery.getResultList();
	};

}
