package Administrador.model.dao.services;

import java.util.List;

import Administrador.model.dao.DaoFactory;
import Administrador.model.dao.UsuarioDao;
import Administrador.model.entities.Usuario;

public class UsuarioServices {

	private UsuarioDao usuarioDao = DaoFactory.createUsuarioDao();

	public Boolean validaLogin(String login) {
		return usuarioDao.validaLogin(login);
	}

	public void salvar(Usuario usuario) {
		if (validaLogin(usuario.getLogin()))
			usuarioDao.update(usuario);
		else
			usuarioDao.insert(usuario);
	}

	public void deletar(String login) {
		usuarioDao.delete(login);
	};

	public Usuario pesquisar(String login) {
		return usuarioDao.find(login);
	};

	public List<Usuario> pesquisarTodos() {
		return usuarioDao.findAll();
	};

}
