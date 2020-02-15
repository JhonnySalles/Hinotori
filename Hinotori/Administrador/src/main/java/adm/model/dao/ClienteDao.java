package administrador.model.dao;

import java.util.List;

import administrador.model.entities.Cliente;
import comum.model.exceptions.ExcessaoBd;

public interface ClienteDao {

	void insert(Cliente obj) throws ExcessaoBd;

	void update(Cliente obj) throws ExcessaoBd;

	void delete(Long id) throws ExcessaoBd;

	Cliente find(Long id) throws ExcessaoBd;

	List<Cliente> findAll() throws ExcessaoBd;

}
