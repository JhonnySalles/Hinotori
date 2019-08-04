package Administrador.model.dao;

import Administrador.model.dao.impl.ClienteDaoJDBC;
import model.mysql.DB;

public class DaoFactory {

	public static ClienteDao createClienteDao() {
		return new ClienteDaoJDBC(DB.getConnection());
	}
	
}
