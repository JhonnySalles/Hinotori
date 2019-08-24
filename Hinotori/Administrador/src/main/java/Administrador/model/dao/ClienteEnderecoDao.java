package Administrador.model.dao;

import java.util.List;

import Administrador.model.entities.ClienteEndereco;

public interface ClienteEnderecoDao {
	void insert(Long cliente, ClienteEndereco obj);
	void update(Long cliente, ClienteEndereco obj);
	void delete(Long id);
	ClienteEndereco find(Long cliente, Long id);
	List<ClienteEndereco> findAll(Long cliente);
}
