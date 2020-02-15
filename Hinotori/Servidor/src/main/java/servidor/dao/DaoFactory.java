package servidor.dao;

import comum.model.mysql.DB;
import servidor.dao.implementJDBC.BairroDaoJDBC;
import servidor.dao.implementJDBC.CidadeDaoJDBC;
import servidor.dao.implementJDBC.ClienteDaoJDBC;
import servidor.dao.implementJDBC.EmpresaDaoJDBC;
import servidor.dao.implementJDBC.EstadoDaoJDBC;
import servidor.dao.implementJDBC.PaisDaoJDBC;
import servidor.dao.implementJDBC.PesquisaGenericaDaoJDBC;
import servidor.dao.implementJDBC.ProdutoDaoJDBC;
import servidor.dao.implementJDBC.UsuarioDaoJDBC;

public class DaoFactory {

	public static PesquisaGenericaDao createPesquisaGenericaDao() {
		return new PesquisaGenericaDaoJDBC(DB.getConnection());
	}
	
	public static BairroDao createBairroDao() {
		return new BairroDaoJDBC(DB.getConnection());
	}

	public static CidadeDao createCidadeDao() {
		return new CidadeDaoJDBC(DB.getConnection());
	}

	public static ClienteDao createClienteDao() {
		return new ClienteDaoJDBC(DB.getConnection());
	}

	public static EmpresaDao createEmpresaDao() {
		return new EmpresaDaoJDBC(DB.getConnection());
	}

	public static EstadoDao createEstadoDao() {
		return new EstadoDaoJDBC(DB.getConnection());
	}

	public static PaisDao createPaisDao() {
		return new PaisDaoJDBC(DB.getConnection());
	}


	public static ProdutoDao createProdutoDao() {
		return new ProdutoDaoJDBC(DB.getConnection());
	}

	public static UsuarioDao createUsuarioDao() {
		return new UsuarioDaoJDBC(DB.getConnection());
	}

}
