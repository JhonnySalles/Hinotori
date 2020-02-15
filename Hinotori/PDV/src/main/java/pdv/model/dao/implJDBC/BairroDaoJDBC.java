package pdv.model.dao.implJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comum.model.mysql.DB;
import pdv.model.dao.BairroDao;
import pdv.model.dao.services.CidadeServices;
import pdv.model.entities.Bairro;

public class BairroDaoJDBC implements BairroDao {

	final String insert = "INSERT INTO bairros (IdCidade, Nome) VALUES (?, ?);";

	final String update = "UPDATE bairros SET IdCidade = ?, Nome = ? WHERE Id = ?;";

	final String delete = "DELETE FROM bairros WHERE Id = ?;";

	final String selectAll = "SELECT Id, IdCidade, Nome FROM bairros ";

	final String select = "SELECT Id, IdCidade, Nome FROM bairros WHERE Id = ?;";

	private Connection conexao;
	private CidadeServices cidadeService;

	public BairroDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void insert(Bairro obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			st.setLong(1, obj.getCidade().getId());
			st.setString(2, obj.getNome());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected < 1) {
				System.out.println("Erro ao salvar os dados.");
				System.out.println(st.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(st.toString());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Bairro obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);

			st.setLong(1, obj.getCidade().getId());
			st.setString(2, obj.getNome());
			st.setLong(3, obj.getId());

			st.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(st.toString());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void delete(Long id) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(delete, Statement.RETURN_GENERATED_KEYS);

			st.setLong(1, id);

			int rowsAffected = st.executeUpdate();

			if (rowsAffected < 1) {
				System.out.println("Erro ao deletar os dados.");
				System.out.println(st.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(st.toString());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Bairro find(Long id) {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conexao.prepareStatement(select);
			st.setLong(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				if (cidadeService == null) {
					setCidadeServices(new CidadeServices());
				}
				Bairro obj = new Bairro(rs.getLong("Id"), rs.getString("Nome"),
						cidadeService.pesquisar(rs.getLong("IdCidade")));
				return obj;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		return null;
	}

	@Override
	public List<Bairro> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conexao.prepareStatement(selectAll);
			rs = st.executeQuery();

			List<Bairro> list = new ArrayList<>();

			if (cidadeService == null) {
				setCidadeServices(new CidadeServices());
			}

			while (rs.next()) {
				Bairro obj = new Bairro(rs.getLong("Id"), rs.getString("Nome"),
						cidadeService.pesquisar(rs.getLong("IdCidade")));
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		return null;
	}

	private void setCidadeServices(CidadeServices cidadeService) {
		this.cidadeService = cidadeService;
	}

}
