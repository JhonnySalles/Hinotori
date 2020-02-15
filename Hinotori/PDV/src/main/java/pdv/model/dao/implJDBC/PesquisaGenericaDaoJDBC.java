package pdv.model.dao.implJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comum.model.mysql.DB;
import pdv.model.dao.PesquisaGenericaDao;
import pdv.model.entities.PesquisaGenerica;
import pdv.model.entities.PesquisaGenericaDados;

public class PesquisaGenericaDaoJDBC implements PesquisaGenericaDao {

	private Connection conexao;

	public PesquisaGenericaDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public PesquisaGenericaDados pesquisar(PesquisaGenerica pesquisa, String sql) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		List<List<Object>> dados = new ArrayList<>();
        List<String> colunasDescricao = new ArrayList<>();

		try {
			st = conexao.prepareStatement(sql);
			rs = st.executeQuery();

			int qtdColunas = rs.getMetaData().getColumnCount();

            for (int i = 1 ; i <= qtdColunas ; i++) {
            	colunasDescricao.add(rs.getMetaData().getColumnLabel(i));
            }

            while (rs.next()) {
                List<Object> row = new ArrayList<>();
                for (int i = 1 ; i <= qtdColunas ; i++) {
                    row.add(rs.getObject(i));
                }
                dados.add(row);
            }
            
            return new PesquisaGenericaDados(colunasDescricao, dados);
            
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		return null;
	}

}
