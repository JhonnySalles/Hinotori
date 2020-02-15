package servidor.dao.services;

import java.util.List;

import servidor.dao.BairroDao;
import servidor.dao.DaoFactory;
import servidor.entities.Bairro;

public class BairroServices {
	private BairroDao bairroDao = DaoFactory.createBairroDao();

	public void salvar(Bairro bairro) {
		if (bairro.getId() != null && bairro.getId() != 0)
			bairroDao.update(bairro);
		else
			bairroDao.insert(bairro);
	}

	public void deletar(Long id) {
		bairroDao.delete(id);
	};
	
	public Bairro pesquisar(Long id) {
		return bairroDao.find(id);
	};

	public List<Bairro> pesquisarTodos() {
		return bairroDao.findAll();
	};
}
