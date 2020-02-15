package servidor.dao.services;

import java.util.List;

import comum.model.enums.TamanhoImagem;
import comum.model.exceptions.ExcessaoBd;
import comum.model.messages.Mensagens;
import servidor.dao.DaoFactory;
import servidor.dao.UsuarioDao;
import servidor.entities.Usuario;

public class UsuarioServices {

	private UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();

	public Boolean validaLogin(String login) throws ExcessaoBd {
		return usuarioDao.validaLogin(login);
	}

	public void salvar(Usuario usuario) throws ExcessaoBd {
		if (usuario.getId() != null && usuario.getId() != 0)
			usuarioDao.update(usuario);
		else if (validaLogin(usuario.getLogin()))
			throw new ExcessaoBd(Mensagens.USR_LOGIN_EXITENTE);
		else
			usuarioDao.insert(usuario);
	}

	public void deletar(Long id) throws ExcessaoBd {
		usuarioDao.delete(id);
	};

	public Usuario pesquisar(Long id, TamanhoImagem tamanho) throws ExcessaoBd {
		return usuarioDao.find(id, tamanho);
	};

	public List<Usuario> pesquisarTodos(TamanhoImagem tamanho) throws ExcessaoBd {
		return usuarioDao.findAll(tamanho);
	};

}
