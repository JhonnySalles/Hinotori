package Administrador.model.dao;

import java.util.List;

import Administrador.model.entities.Sabor;

public interface SaborDao {
	
	void insert(Sabor obj);
	void update(Sabor obj);
	void delete(Integer id);
	Sabor find(Integer id);
	List<Sabor> findAll();

}
