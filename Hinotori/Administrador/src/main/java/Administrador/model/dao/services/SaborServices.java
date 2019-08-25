package Administrador.model.dao.services;

import java.util.List;

import Administrador.model.dao.DaoFactory;
import Administrador.model.dao.SaborDao;
import Administrador.model.entities.Sabor;

public class SaborServices {
	private SaborDao saborDao = DaoFactory.createSaborDao();

	public void salvar(Sabor sabor) {
		if (sabor.getId() != null && sabor.getId() != 0)
			saborDao.update(sabor);
		else
			saborDao.insert(sabor);
	}

	public void deletar(Long id) {
		saborDao.delete(id);
	};

	public Sabor pesquisar(Long id) {
		return saborDao.find(id);
	};

	public List<Sabor> pesquisarTodos() {
		return saborDao.findAll();
	};
}
