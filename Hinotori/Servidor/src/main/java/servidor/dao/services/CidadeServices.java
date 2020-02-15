package servidor.dao.services;

import java.util.List;

import servidor.dao.CidadeDao;
import servidor.dao.DaoFactory;
import servidor.entities.Cidade;

public class CidadeServices {
	private CidadeDao cidadeDao = DaoFactory.createCidadeDao();

	public void salvar(Cidade cidade) {
		if (cidade.getId() != null && cidade.getId() != 0)
			cidadeDao.update(cidade);
		else
			cidadeDao.insert(cidade);
	}

	public void deletar(Long id) {
		cidadeDao.delete(id);
	};
	
	public Cidade pesquisar(Long id) {
		return cidadeDao.find(id);
	};

	public List<Cidade> pesquisarTodos() {
		return cidadeDao.findAll();
	};
}
