package Administrador.model.dao;

import java.util.List;

import Administrador.model.entities.Usuario;

public interface UsuarioDao {

	void insert(Usuario obj);
	void update(Usuario obj);
	void delete(Integer id);
	Usuario find(Integer id);
	List<Usuario> findAll();
	
}
