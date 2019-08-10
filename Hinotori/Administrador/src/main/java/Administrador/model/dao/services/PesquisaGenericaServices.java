package Administrador.model.dao.services;

import java.util.List;

import Administrador.model.dao.DaoFactory;
import Administrador.model.dao.PesquisaGenericaDao;
import Administrador.model.entities.PesquisaGenerica;
import Administrador.model.entities.PesquisaGenericaDados;

public class PesquisaGenericaServices {

	private String sql;
	private PesquisaGenericaDao pesquisaDao = DaoFactory.createPesquisaGenericaDao();

	public List<PesquisaGenericaDados> pesquisar(PesquisaGenerica pesquisa) {
		sql = "SELECT " + pesquisa.getSelect() + " FROM " + pesquisa.getTabela() + " " + pesquisa.getJoins()
				+ " WHERE 1 > 0 " + pesquisa.getWhere() + " " + pesquisa.getGroupOrder();

		return pesquisaDao.pesquisar(pesquisa, sql);
	};

}
