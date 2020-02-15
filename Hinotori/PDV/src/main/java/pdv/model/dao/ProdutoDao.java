package pdv.model.dao;

import java.util.List;

import comum.model.exceptions.ExcessaoBd;
import pdv.model.entities.Produto;

public interface ProdutoDao {

	void insert(Produto obj) throws ExcessaoBd;
	void update(Produto obj) throws ExcessaoBd;
	void delete(Long id) throws ExcessaoBd;
	Produto find(Long id) throws ExcessaoBd;
	List<Produto> findAll() throws ExcessaoBd;
	
}
