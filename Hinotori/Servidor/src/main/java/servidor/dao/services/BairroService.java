package servidor.dao.services;

import java.util.List;

import servidor.dao.BairroDao;
import servidor.entities.Bairro;

public class BairroService {

	private BairroDao service = new BairroDao();

	public BairroDao getService() {
		return service;
	}
	
	public Bairro salvar(Bairro bairro) {
		service.salvarAtomico(bairro);
		return bairro;
	}

	public void deletar(Long id) {
		service.removerAtomico(id);
	}

	public Bairro pesquisar(String nome) {
		return service.pesquisar(nome);
	}

	public Bairro pesquisar(Long id) {
		return service.pesquisar(id);
	}

	public List<Bairro> listar() {
		return service.listar();
	}

}
