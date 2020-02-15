package administrador.model.dao.services;

import java.util.List;

import administrador.model.dao.DaoFactory;
import administrador.model.dao.ProdutoDao;
import administrador.model.entities.Produto;
import comum.model.exceptions.ExcessaoBd;

public class ProdutoServices {
	private ProdutoDao produtoDao = DaoFactory.createProdutoDao();

	public void salvar(Produto produto) throws ExcessaoBd {
		if (produto.getId() != null && produto.getId() != 0)
			produtoDao.update(produto);
		else
			produtoDao.insert(produto);
	}

	public void deletar(Long id) throws ExcessaoBd {
		produtoDao.delete(id);
	};

	public Produto pesquisar(Long id) throws ExcessaoBd {
		return produtoDao.find(id);
	};

	public List<Produto> pesquisarTodos() throws ExcessaoBd {
		return produtoDao.findAll();
	};
}
