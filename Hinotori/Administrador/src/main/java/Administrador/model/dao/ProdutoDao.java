package administrador.model.dao;

import java.util.List;

import administrador.model.entities.Produto;
import comum.model.exceptions.ExcessaoBd;

public interface ProdutoDao {

	void insert(Produto obj) throws ExcessaoBd;
	void update(Produto obj) throws ExcessaoBd;
	void delete(Long id) throws ExcessaoBd;
	Produto find(Long id) throws ExcessaoBd;
	List<Produto> findAll() throws ExcessaoBd;
	
}
