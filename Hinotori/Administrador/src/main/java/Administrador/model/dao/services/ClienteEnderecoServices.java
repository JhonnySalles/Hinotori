package Administrador.model.dao.services;

import java.util.List;

import Administrador.model.dao.ClienteEnderecoDao;
import Administrador.model.dao.DaoFactory;
import Administrador.model.entities.ClienteEndereco;

public class ClienteEnderecoServices {

	private ClienteEnderecoDao clienteEnderecoDao = DaoFactory.createClienteEnderecoDao();

	public void salvar(Long cliente, ClienteEndereco endereco) {
		if (endereco.getId() == 0)
			clienteEnderecoDao.update(cliente, endereco);
		else
			clienteEnderecoDao.insert(cliente, endereco);
	}

	public void deletar(Long id) {
		clienteEnderecoDao.delete(id);
	};

	public ClienteEndereco pesquisar(Long cliente, Long id) {
		return clienteEnderecoDao.find(cliente, id);
	};

	public List<ClienteEndereco> pesquisarTodos(Long cliente) {
		return clienteEnderecoDao.findAll(cliente);
	};
}
