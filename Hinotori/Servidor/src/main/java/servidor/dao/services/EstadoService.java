package servidor.dao.services;

import java.util.List;

import servidor.dao.EstadoDao;
import servidor.entities.Estado;

public class EstadoService {

	private EstadoDao service = new EstadoDao();

	public EstadoDao getService() {
		return service;
	}
	
	public Estado salvar(Estado estado) {
		service.salvarAtomico(estado);
		return estado;
	}

	public void deletar(Long id) {
		service.removerAtomico(id);
	}

	public Estado pesquisar(String nome_uf) {
		return service.pesquisar(nome_uf);
	}

	public Estado pesquisar(Long id) {
		return service.pesquisar(id);
	}

	public List<Estado> listar() {
		return service.listar();
	}

}
