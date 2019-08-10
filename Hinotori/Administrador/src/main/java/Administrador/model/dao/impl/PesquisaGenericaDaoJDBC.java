package Administrador.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Administrador.model.dao.PesquisaGenericaDao;
import Administrador.model.entities.PesquisaGenerica;
import Administrador.model.entities.PesquisaGenericaDados;
import model.log.ManipulaLog;
import model.mysql.DB;

public class PesquisaGenericaDaoJDBC implements PesquisaGenericaDao {

	private Connection conexao;

	public PesquisaGenericaDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public List<PesquisaGenericaDados> pesquisar(PesquisaGenerica pesquisa, String sql) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conexao.prepareStatement(sql);
			rs = st.executeQuery();

			List<PesquisaGenericaDados> list = new ArrayList<>();

			while (rs.next()) {
				PesquisaGenericaDados obj = new PesquisaGenericaDados(rs.getString(pesquisa.getCampoID()),
						rs.getString(pesquisa.getCampoDescricao()));
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			ManipulaLog.salvar(this.getClass(), "JDBC - FIND ALL", st.toString(), e.toString());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		return null;
	}

}
