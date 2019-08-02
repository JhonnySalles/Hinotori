package Administrador.model.dao;

import java.util.List;

import Administrador.model.entities.Empresa;

public interface EmpresaDao {

	void insert(Empresa obj);
	void update(Empresa obj);
	void delete(Long id);
	Empresa find(Long id);
	List<Empresa> findAll();
	
}
