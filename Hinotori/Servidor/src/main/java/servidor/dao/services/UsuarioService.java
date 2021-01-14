package servidor.dao.services;

import java.util.List;

import comum.model.enums.TamanhoImagem;
import servidor.dao.UsuarioDao;
import servidor.entities.Usuario;

public class UsuarioService {

	private UsuarioDao service = new UsuarioDao();

	public UsuarioDao getService() {
		return service;
	}
	
	public Usuario salvar(Usuario usuario) {
		service.salvarAtomico(usuario);
		return usuario;
	}

	public void deletar(Long id) {
		service.removerAtomico(id);
	}

	public Boolean validaLogin(Long id, String login) {
		return service.validaLogin(id, login);
	}

	public Usuario pesquisar(String login) {
		return service.pesquisar(login);
	}

	public Usuario pesquisar(Long id) {
		return service.pesquisar(id);
	}

	public List<Usuario> listar(TamanhoImagem tamanho) {
		return service.listar();
	}

	public List<String> carregaLogins() {
		return service.carregaLogins();
	}

}
