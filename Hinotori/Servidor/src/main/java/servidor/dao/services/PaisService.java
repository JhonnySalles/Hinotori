package servidor.dao.services;

import java.util.List;

import servidor.dao.PaisDao;
import servidor.entities.Pais;

public class PaisService {

	private PaisDao service = new PaisDao();

	public PaisDao getService() {
		return service;
	}
	
	public Pais salvar(Pais pais) {
		service.salvarAtomico(pais);
		return pais;
	}

	public void deletar(Long id) {
		service.removerAtomico(id);
	}

	public Pais pesquisar(String nome) {
		return service.pesquisar(nome);
	}

	public Pais pesquisar(Long id) {
		return service.pesquisar(id);
	}

	public List<Pais> listar() {
		return service.listar();
	}

}
