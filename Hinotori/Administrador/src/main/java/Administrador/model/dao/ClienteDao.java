package Administrador.model.dao;

import java.util.List;

import Administrador.model.entities.Cliente;

public interface ClienteDao {
	
	void insert(Cliente obj);
	void update(Cliente obj);
	void delete(Long id);
	Cliente find(Long id);
	List<Cliente> findAll();

}
