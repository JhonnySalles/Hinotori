package servidor.dao.services;

import java.util.List;

import comum.model.enums.TamanhoImagem;
import servidor.entities.Produto;

public class ProdutoServices extends HibernateService {

	final static String SELECT_ALL = "SELECT p FROM " + Produto.TABELA + " p WHERE Situacao <> 'EXCLUIDO' AND Id <> 0";

	public Produto salvar(Produto produto) {
		return super.salvar(produto);
	}

	public void deletar(Long id) {
		super.deletar(id, Produto.TABELA);
	};

	public Produto pesquisar(Long id) {
		return em.find(Produto.class, id);
	};

	public List<Produto> pesquisarTodos(TamanhoImagem tamanho) {
		return em.createQuery(SELECT_ALL, Produto.class).getResultList();
	};
}
