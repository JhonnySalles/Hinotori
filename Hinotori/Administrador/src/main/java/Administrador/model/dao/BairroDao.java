package Administrador.model.dao;

import java.util.List;

import Administrador.model.entities.Bairro;

public interface BairroDao {

	void insert(Bairro obj);
	void update(Bairro obj);
	void delete(Integer id);
	Bairro find(Integer id);
	List<Bairro> findAll();
	
}
