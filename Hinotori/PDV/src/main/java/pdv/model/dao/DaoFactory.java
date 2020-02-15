package pdv.model.dao;

import comum.model.mysql.DB;
import pdv.model.dao.implJDBC.BairroDaoJDBC;
import pdv.model.dao.implJDBC.CidadeDaoJDBC;
import pdv.model.dao.implJDBC.ClienteDaoJDBC;
import pdv.model.dao.implJDBC.EmpresaDaoJDBC;
import pdv.model.dao.implJDBC.EstadoDaoJDBC;
import pdv.model.dao.implJDBC.PaisDaoJDBC;
import pdv.model.dao.implJDBC.PesquisaGenericaDaoJDBC;
import pdv.model.dao.implJDBC.ProdutoDaoJDBC;
import pdv.model.dao.implJDBC.UsuarioDaoJDBC;

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
