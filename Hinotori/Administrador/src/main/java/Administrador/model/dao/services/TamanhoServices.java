package Administrador.model.dao.services;

import java.util.List;

import Administrador.model.dao.DaoFactory;
import Administrador.model.dao.TamanhoDao;
import Administrador.model.entities.Tamanho;

public class TamanhoServices {

	private TamanhoDao tamanhoDao = DaoFactory.createTamanhoDao();

	public void salvar(Tamanho tamanho) {
		if (tamanho.getId() == 0)
			tamanhoDao.update(tamanho);
		else
			tamanhoDao.insert(tamanho);
	}

	public void deletar(Long id) {
		tamanhoDao.delete(id);
	};

	public Tamanho pesquisar(Long id) {
		return tamanhoDao.find(id);
	};

	public List<Tamanho> pesquisarTodos() {
		return tamanhoDao.findAll();
	};

}
