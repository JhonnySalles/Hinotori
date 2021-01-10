package servidor.dao.services;

import java.util.List;

import javax.persistence.TypedQuery;

import comum.model.enums.TamanhoImagem;
import servidor.entities.Usuario;

public class UsuarioServices extends HibernateService {

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

	public Usuario salvar(Usuario usuario) {
		return super.salvar(usuario);
	}

	public void deletar(Long id) {
		super.deletar(id, Usuario.TABELA);
	};

	public Usuario pesquisar(String login) {
		TypedQuery<Usuario> query = em.createQuery(SELECT_LOGIN, Usuario.class);
		return query.getSingleResult();
	};

	public Usuario pesquisar(Long id) {
		return em.find(Usuario.class, id);
	};

	public List<Usuario> pesquisarTodos(TamanhoImagem tamanho) {
		return em.createQuery(SELECT_ALL, Usuario.class).getResultList();
	};

	public List<String> carregaLogins() {
		TypedQuery<String> stringQuery = em.createQuery(SELECT_LISTA_LOGIN, String.class);
		return stringQuery.getResultList();
	};

}
