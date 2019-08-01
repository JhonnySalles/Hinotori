package Administrador.model.dao;

import Administrador.model.dao.impl.ClienteDaoJDBC;

public class DaoFactory {

	public static ClienteDao createClienteDao() {
		return new ClienteDaoJDBC();
	}
	
}
