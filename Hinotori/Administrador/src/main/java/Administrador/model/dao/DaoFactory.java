package Administrador.model.dao;

import Administrador.model.dao.impl.BairroDaoJDBC;
import Administrador.model.dao.impl.CidadeDaoJDBC;
import Administrador.model.dao.impl.ClienteDaoJDBC;
import Administrador.model.dao.impl.ClienteEnderecoJDBC;
import Administrador.model.dao.impl.EmpresaDaoJDBC;
import Administrador.model.dao.impl.EstadoDaoJDBC;
import Administrador.model.dao.impl.PaisDaoJDBC;
import Administrador.model.dao.impl.PesquisaGenericaDaoJDBC;
import Administrador.model.dao.impl.ProdutoDaoJDBC;
import Administrador.model.dao.impl.SaborDaoJDBC;
import Administrador.model.dao.impl.TamanhoDaoJDBC;
import Administrador.model.dao.impl.UsuarioDaoJDBC;
import model.mysql.DB;

public class DaoFactory {

	public static BairroDao createBairroDao() {
		return new BairroDaoJDBC(DB.getConnection());
	}

	public static CidadeDao createCidadeDao() {
		return new CidadeDaoJDBC(DB.getConnection());
	}

	public static ClienteDao createClienteDao() {
		return new ClienteDaoJDBC(DB.getConnection());
	}
	
	public static ClienteEnderecoDao createClienteEnderecoDao() {
		return new ClienteEnderecoJDBC(DB.getConnection());
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

	public static PesquisaGenericaDao createPesquisaGenericaDao() {
		return new PesquisaGenericaDaoJDBC(DB.getConnection());
	}

	public static ProdutoDao createProdutoDao() {
		return new ProdutoDaoJDBC(DB.getConnection());
	}

	public static SaborDao createSaborDao() {
		return new SaborDaoJDBC(DB.getConnection());
	}

	public static TamanhoDao createTamanhoDao() {
		return new TamanhoDaoJDBC(DB.getConnection());
	}

	public static UsuarioDao createUsuarioDao() {
		return new UsuarioDaoJDBC(DB.getConnection());
	}

}
