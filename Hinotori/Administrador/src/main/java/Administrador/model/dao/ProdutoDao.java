package Administrador.model.dao;

import java.util.List;

import Administrador.model.entities.Produto;

public interface ProdutoDao {

	void insert(Produto obj);
	void update(Produto obj);
	void delete(Long id);
	Produto find(Long id);
	List<Produto> findAll();
	
}
