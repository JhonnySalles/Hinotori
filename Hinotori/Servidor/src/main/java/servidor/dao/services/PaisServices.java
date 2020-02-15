package servidor.dao.services;

import java.util.List;

import servidor.dao.DaoFactory;
import servidor.dao.PaisDao;
import servidor.entities.Pais;

public class PaisServices {
	
	private PaisDao paisDao = DaoFactory.createPaisDao();

	public void salvar(Pais pais) {
		if (pais.getId() != null && pais.getId() != 0)
			paisDao.update(pais);
		else
			paisDao.insert(pais);
	}

	public void deletar(Long id) {
		paisDao.delete(id);
	};

	public Pais pesquisar(Long id) {
		return paisDao.find(id);
	};

	public List<Pais> pesquisarTodos() {
		return paisDao.findAll();
	};
}
