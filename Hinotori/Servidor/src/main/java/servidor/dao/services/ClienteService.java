package servidor.dao.services;

import servidor.dao.ClienteDao;
import servidor.entities.Cliente;

public class ClienteService {

	private ClienteDao service = new ClienteDao();

	public ClienteDao getService() {
		return service;
	}

	public Cliente existeCPF(String cpf) {
		return service.existeCPF(cpf);
	}

	public Cliente existeCNPJ(String cnpj) {
		return service.existeCNPJ(cnpj);
	};

}
