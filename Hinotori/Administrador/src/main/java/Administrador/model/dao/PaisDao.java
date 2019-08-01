package Administrador.model.dao;

import java.util.List;

import Administrador.model.entities.Pais;

public interface PaisDao {
	
	void insert(Pais obj);
	void update(Pais obj);
	void delete(Integer id);
	Pais find(Integer id);
	List<Pais> findAll();

}
