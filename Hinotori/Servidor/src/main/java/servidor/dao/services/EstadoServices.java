package servidor.dao.services;

import java.util.List;

import servidor.dao.DaoFactory;
import servidor.dao.EstadoDao;
import servidor.entities.Estado;

public class EstadoServices {
	private EstadoDao estadoDao = DaoFactory.createEstadoDao();

	public void salvar(Estado estado) {
		if (estado.getId() != null && estado.getId() != 0)
			estadoDao.update(estado);
		else
			estadoDao.insert(estado);
	}

	public void deletar(Long id) {
		estadoDao.delete(id);
	};

	public Estado pesquisar(Long id) {
		return estadoDao.find(id);
	};

	public List<Estado> pesquisarTodos() {
		return estadoDao.findAll();
	};
}
