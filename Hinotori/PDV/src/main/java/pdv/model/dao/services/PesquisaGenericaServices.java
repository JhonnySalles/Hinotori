package pdv.model.dao.services;

import pdv.model.dao.DaoFactory;
import pdv.model.dao.PesquisaGenericaDao;
import pdv.model.entities.PesquisaGenerica;
import pdv.model.entities.PesquisaGenericaDados;

public class PesquisaGenericaServices {

	private String sql;
	private PesquisaGenericaDao pesquisaDao = DaoFactory.createPesquisaGenericaDao();

	public PesquisaGenericaDados pesquisar(PesquisaGenerica pesquisa) {
		sql = "SELECT " + pesquisa.getSelect() + " FROM " + pesquisa.getTabela() + " " + pesquisa.getJoins()
				+ " WHERE 1 > 0 " + pesquisa.getWhere() + " " + pesquisa.getGroupOrder();

		return pesquisaDao.pesquisar(pesquisa, sql);
	};

}
