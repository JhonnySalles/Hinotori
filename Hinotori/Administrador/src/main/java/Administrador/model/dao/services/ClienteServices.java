package Administrador.model.dao.services;

import java.util.List;

import Administrador.model.dao.ClienteDao;
import Administrador.model.dao.DaoFactory;
import Administrador.model.entities.Cliente;

public class ClienteServices {
	
	private ClienteDao clienteDao = DaoFactory.createClienteDao();

	public void salvar(Cliente cliente) {
		if (cliente.getId() == 0)
			clienteDao.update(cliente);
		else
			clienteDao.insert(cliente);
	}

	public void deletar(Long id) {
		clienteDao.delete(id);
	};
	
	public Cliente pesquisar(Long id) {
		return clienteDao.find(id);
	};

	public List<Cliente> pesquisarTodos() {
		return clienteDao.findAll();
	};

}
