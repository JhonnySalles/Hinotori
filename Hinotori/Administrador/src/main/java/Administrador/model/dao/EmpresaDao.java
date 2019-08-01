package Administrador.model.dao;

import java.util.List;

import Administrador.model.entities.Empresa;

public interface EmpresaDao {

	void insert(Empresa obj);
	void update(Empresa obj);
	void delete(Integer id);
	Empresa find(Integer id);
	List<Empresa> findAll();
	
}
