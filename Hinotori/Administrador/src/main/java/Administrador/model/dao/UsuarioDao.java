package Administrador.model.dao;

import java.util.List;

import Administrador.model.entities.Usuario;

public interface UsuarioDao {

	void insert(Usuario obj);
	void update(Usuario obj);
	void delete(String login);
	Usuario find(String login);
	List<Usuario> findAll();
	Boolean validaLogin(String login);
	
}
