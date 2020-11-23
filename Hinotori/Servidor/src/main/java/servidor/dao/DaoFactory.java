package servidor.dao;

import servidor.dao.implementJDBC.BairroDaoJDBC;
import servidor.dao.implementJDBC.CidadeDaoJDBC;
import servidor.dao.implementJDBC.EmpresaDaoJDBC;
import servidor.dao.implementJDBC.EstadoDaoJDBC;
import servidor.dao.implementJDBC.PaisDaoJDBC;
import servidor.dao.implementJDBC.PesquisaGenericaDaoJDBC;
import servidor.dao.implementJDBC.ProdutoDaoJDBC;
import servidor.dao.implementJDBC.UsuarioDaoJDBC;
import servidor.util.DBConnection;

public class DaoFactory {

	public static PesquisaGenericaDao createPesquisaGenericaDao() {
		return new PesquisaGenericaDaoJDBC(DBConnection.getConnection());
	}
	
	public static BairroDao createBairroDao() {
		return new BairroDaoJDBC(DBConnection.getConnection());
	}

	public static CidadeDao createCidadeDao() {
		return new CidadeDaoJDBC(DBConnection.getConnection());
	}

	public static EmpresaDao createEmpresaDao() {
		return new EmpresaDaoJDBC(DBConnection.getConnection());
	}

	public static EstadoDao createEstadoDao() {
		return new EstadoDaoJDBC(DBConnection.getConnection());
	}

	public static PaisDao createPaisDao() {
		return new PaisDaoJDBC(DBConnection.getConnection());
	}


	public static ProdutoDao createProdutoDao() {
		return new ProdutoDaoJDBC(DBConnection.getConnection());
	}

	public static UsuarioDao createUsuarioDao() {
		return new UsuarioDaoJDBC(DBConnection.getConnection());
	}
	
}
