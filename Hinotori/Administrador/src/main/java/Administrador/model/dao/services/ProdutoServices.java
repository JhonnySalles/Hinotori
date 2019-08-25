package Administrador.model.dao.services;

import java.util.List;

import Administrador.model.dao.DaoFactory;
import Administrador.model.dao.ProdutoDao;
import Administrador.model.entities.Produto;

public class ProdutoServices {
	private ProdutoDao produtoDao = DaoFactory.createProdutoDao();

	public void salvar(Produto produto) {
		if (produto.getId() != null && produto.getId() != 0)
			produtoDao.update(produto);
		else
			produtoDao.insert(produto);
	}

	public void deletar(Long id) {
		produtoDao.delete(id);
	};

	public Produto pesquisar(Long id) {
		return produtoDao.find(id);
	};

	public List<Produto> pesquisarTodos() {
		return produtoDao.findAll();
	};
}
