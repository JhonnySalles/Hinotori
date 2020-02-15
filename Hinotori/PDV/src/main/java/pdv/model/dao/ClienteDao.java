package pdv.model.dao;

import java.util.List;

import comum.model.exceptions.ExcessaoBd;
import pdv.model.entities.Cliente;

public interface ClienteDao {

	void insert(Cliente obj) throws ExcessaoBd;

	void update(Cliente obj) throws ExcessaoBd;

	void delete(Long id) throws ExcessaoBd;

	Cliente find(Long id) throws ExcessaoBd;

	List<Cliente> findAll() throws ExcessaoBd;

}
