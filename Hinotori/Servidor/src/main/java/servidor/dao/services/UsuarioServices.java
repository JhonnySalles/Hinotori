package servidor.dao.services;

import java.util.List;

import comum.model.enums.TamanhoImagem;
import servidor.dao.UsuarioDao;
import servidor.entities.Usuario;

public class UsuarioServices {

	private UsuarioDao usuarioDao = new UsuarioDao();

	public Boolean validaLogin(Long id, String login) {
		return usuarioDao.validaLogin(id, login);
	}

	public Usuario salvar(Usuario usuario) {
		usuarioDao.salvarAtomico(usuario);
		return usuario;
	}

	public void deletar(Long id) {
		usuarioDao.remover(id);
	};

	public Usuario pesquisar(String login) {
		return usuarioDao.pesquisar(login);
	};

	public Usuario pesquisar(Long id) {
		return usuarioDao.pesquisar(id);
	};

	public List<Usuario> listar(TamanhoImagem tamanho) {
		return usuarioDao.listar();
	};

	public List<String> carregaLogins() {
		return usuarioDao.carregaLogins();
	};

}
