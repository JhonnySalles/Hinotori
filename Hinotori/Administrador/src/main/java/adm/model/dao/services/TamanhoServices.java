package administrador.model.dao.services;

import java.util.List;

import administrador.model.dao.DaoFactory;
import administrador.model.dao.TamanhoDao;
import administrador.model.entities.Tamanho;

public class TamanhoServices {

	private TamanhoDao tamanhoDao = DaoFactory.createTamanhoDao();

	public void salvar(Tamanho tamanho) {
		if (tamanho.getId() != null && tamanho.getId() != 0)
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
