package Administrador.model.dao;

import java.util.List;

import Administrador.model.entities.Usuario;

public interface UsuarioDao {

	void insert(Usuario obj);
	void update(Usuario obj);
	void delete(Long id);
	Usuario find(Long id);
	List<Usuario> findAll();
	
}
