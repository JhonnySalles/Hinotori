package servidor.dao;

import java.util.List;

import comum.model.enums.TamanhoImagem;
import comum.model.exceptions.ExcessaoBd;
import servidor.entities.Produto;

public interface ProdutoDao {

	void insert(Produto obj) throws ExcessaoBd;

	void update(Produto obj) throws ExcessaoBd;

	void delete(Long id) throws ExcessaoBd;

	Produto find(Long id, TamanhoImagem tamanho) throws ExcessaoBd;

	List<Produto> findAll(TamanhoImagem tamanho) throws ExcessaoBd;

}
