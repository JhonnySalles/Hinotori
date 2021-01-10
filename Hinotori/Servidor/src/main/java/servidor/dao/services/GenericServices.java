package servidor.dao.services;

import java.util.List;

import servidor.dao.Dao;
import servidor.dao.Entidade;

public class GenericServices<E extends Entidade> {

	private Dao<E> service;

	public Dao<E> getService() {
		return service;
	}

	public GenericServices(Class<E> classe) {
		this.service = new Dao<E>(classe);
	}

	public E salvar(E entidade) {
		service.salvarAtomico(entidade);
		return entidade;
	}

	public void deletar(Long id) {
		service.removerAtomico(id);
	}

	public E pesquisar(Long id) {
		return service.pesquisar(id);
	}

	public List<E> listar() {
		return service.listar();
	}

}
