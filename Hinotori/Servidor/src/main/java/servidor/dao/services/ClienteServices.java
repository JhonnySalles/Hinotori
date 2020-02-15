package servidor.dao.services;

import java.util.List;

import comum.model.exceptions.ExcessaoBd;
import servidor.dao.ClienteDao;
import servidor.dao.DaoFactory;
import servidor.entities.Cliente;

public class ClienteServices {

	private ClienteDao clienteDao = DaoFactory.createClienteDao();

	public void salvar(Cliente cliente) throws ExcessaoBd {
		if (cliente.getId() != null && cliente.getId() != 0)
			clienteDao.update(cliente);
		else
			clienteDao.insert(cliente);
	}

	public void deletar(Long id) throws ExcessaoBd {
		clienteDao.delete(id);
	};

	public Cliente pesquisar(Long id) throws ExcessaoBd {
		return clienteDao.find(id);
	};

	public List<Cliente> pesquisarTodos() throws ExcessaoBd {
		return clienteDao.findAll();
	};

}
