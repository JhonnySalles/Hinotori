package pdv.model.dao.implJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comum.model.mysql.DB;
import pdv.model.dao.EstadoDao;
import pdv.model.dao.services.PaisServices;
import pdv.model.entities.Estado;

public class EstadoDaoJDBC implements EstadoDao {

	final String insert = "INSERT INTO estados (Nome, Sigla, CodigoIBGE, IdPais) VALUES (?, ?, ?, ?);";

	final String update = "UPDATE estados SET Nome = ?, Sigla = ?, CodigoIBGE = ?, IdPais = ? WHERE Id = ?;";

	final String delete = "DELETE FROM estados WHERE ID = ?;";

	final String selectAll = "SELECT Id, Nome, Sigla, CodigoIBGE, IdPais FROM estados;";

	final String select = "SELECT Id, Nome, Sigla, CodigoIBGE, IdPais FROM estados WHERE ID = ?;";

	private Connection conexao;
	private PaisServices paisService;

	public EstadoDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void insert(Estado obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNome());
			st.setString(2, obj.getSigla());
			st.setInt(3, obj.getCodigoIBGE());
			st.setLong(4, obj.getPais().getId());

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
	public void update(Estado obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNome());
			st.setString(2, obj.getSigla());
			st.setInt(3, obj.getCodigoIBGE());
			st.setLong(4, obj.getPais().getId());
			st.setLong(5, obj.getId());

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
	public Estado find(Long id) {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conexao.prepareStatement(select);
			st.setLong(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				if (paisService == null) {
					setPaisServices(new PaisServices());
				}

				Estado obj = new Estado(rs.getLong("Id"), rs.getString("Nome"), rs.getString("Sigla"),
						rs.getInt("CodigoIBGE"), paisService.pesquisar(rs.getLong("IdPais")));
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
	public List<Estado> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conexao.prepareStatement(selectAll);
			rs = st.executeQuery();

			List<Estado> list = new ArrayList<>();

			if (paisService == null) {
				setPaisServices(new PaisServices());
			}

			while (rs.next()) {
				Estado obj = new Estado(rs.getLong("Id"), rs.getString("Nome"), rs.getString("Sigla"),
						rs.getInt("CodigoIBGE"), paisService.pesquisar(rs.getLong("IdPais")));
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

	private void setPaisServices(PaisServices paisService) {
		this.paisService = paisService;
	}

}
