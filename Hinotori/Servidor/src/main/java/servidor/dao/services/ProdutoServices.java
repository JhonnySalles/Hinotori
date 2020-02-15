package servidor.dao.services;

import java.util.List;

import comum.model.enums.TamanhoImagem;
import comum.model.exceptions.ExcessaoBd;
import servidor.dao.DaoFactory;
import servidor.dao.ProdutoDao;
import servidor.entities.Produto;

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

	public Produto pesquisar(Long id, TamanhoImagem tamanho) throws ExcessaoBd {
		return produtoDao.find(id, tamanho);
	};

	public List<Produto> pesquisarTodos(TamanhoImagem tamanho) throws ExcessaoBd {
		return produtoDao.findAll(tamanho);
	};
}
