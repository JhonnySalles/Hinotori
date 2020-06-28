package servidor.dao.services;

import java.util.List;

import comum.model.enums.TamanhoImagem;
import comum.model.exceptions.ExcessaoBd;
import servidor.dao.DaoFactory;
import servidor.dao.UsuarioDao;
import servidor.entities.Usuario;

public class UsuarioServices {

	private UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();

	public Boolean validaLogin(Long id, String login) throws ExcessaoBd {
		return usuarioDao.validaLogin(id, login);
	}

	public Usuario salvar(Usuario usuario) throws ExcessaoBd {
		if (usuario.getId() != null && usuario.getId() != 0)
			return usuarioDao.update(usuario);
		else
			return usuarioDao.insert(usuario);
	}

	public void deletar(Long id) throws ExcessaoBd {
		usuarioDao.delete(id);
	};

	public Usuario pesquisar(String login) throws ExcessaoBd {
		return usuarioDao.find(login);
	};

	public Usuario pesquisar(Long id, TamanhoImagem tamanho) throws ExcessaoBd {
		return usuarioDao.find(id, tamanho);
	};

	public List<Usuario> pesquisarTodos(TamanhoImagem tamanho) throws ExcessaoBd {
		return usuarioDao.findAll(tamanho);
	};

	public List<String> carregaLogins() throws ExcessaoBd {
		return usuarioDao.findLogins();
	};

}
