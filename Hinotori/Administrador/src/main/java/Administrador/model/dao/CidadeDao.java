package Administrador.model.dao;

import java.util.List;

import Administrador.model.entities.Cidade;

public interface CidadeDao {

	void insert(Cidade obj);
	void update(Cidade obj);
	void delete(Integer id);
	Cidade find(Integer id);
	List<Cidade> findAll();
	
}
