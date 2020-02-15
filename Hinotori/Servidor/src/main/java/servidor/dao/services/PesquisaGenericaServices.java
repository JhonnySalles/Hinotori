package servidor.dao.services;

import servidor.dao.DaoFactory;
import servidor.dao.PesquisaGenericaDao;
import servidor.entities.PesquisaGenerica;
import servidor.entities.PesquisaGenericaDados;

public class PesquisaGenericaServices {

	private String sql;
	private PesquisaGenericaDao pesquisaDao = DaoFactory.createPesquisaGenericaDao();

	public PesquisaGenericaDados pesquisar(PesquisaGenerica pesquisa) {
		sql = "SELECT " + pesquisa.getSelect() + " FROM " + pesquisa.getTabela() + " " + pesquisa.getJoins()
				+ " WHERE 1 > 0 " + pesquisa.getWhere() + " " + pesquisa.getGroupOrder();

		return pesquisaDao.pesquisar(pesquisa, sql);
	};

}
