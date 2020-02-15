package administrador.model.dao;

import administrador.model.dao.impl.BairroDaoJDBC;
import administrador.model.dao.impl.CidadeDaoJDBC;
import administrador.model.dao.impl.ClienteDaoJDBC;
import administrador.model.dao.impl.EmpresaDaoJDBC;
import administrador.model.dao.impl.EstadoDaoJDBC;
import administrador.model.dao.impl.PaisDaoJDBC;
import administrador.model.dao.impl.PesquisaGenericaDaoJDBC;
import administrador.model.dao.impl.ProdutoDaoJDBC;
import administrador.model.dao.impl.SaborDaoJDBC;
import administrador.model.dao.impl.TamanhoDaoJDBC;
import administrador.model.dao.impl.UsuarioDaoJDBC;
import comum.model.mysql.DB;

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
