package administrador.model.dao.services;

import administrador.model.dao.DaoFactory;
import administrador.model.dao.PesquisaGenericaDao;
import administrador.model.entities.PesquisaGenerica;
import administrador.model.entities.PesquisaGenericaDados;

public class PesquisaGenericaServices {

	private String sql;
	private PesquisaGenericaDao pesquisaDao = DaoFactory.createPesquisaGenericaDao();

	public PesquisaGenericaDados pesquisar(PesquisaGenerica pesquisa) {
		sql = "SELECT " + pesquisa.getSelect() + " FROM " + pesquisa.getTabela() + " " + pesquisa.getJoins()
				+ " WHERE 1 > 0 " + pesquisa.getWhere() + " " + pesquisa.getGroupOrder();

		return pesquisaDao.pesquisar(pesquisa, sql);
	};

}
