package servidor.dao.implementJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comum.model.enums.Situacao;
import servidor.dao.CidadeDao;
import servidor.dao.services.EstadoServices;
import servidor.entities.Cidade;
import servidor.util.DBConnection;

public class CidadeDaoJDBC implements CidadeDao {

	final String insert = "INSERT INTO cidades (IdEstado, Nome, Ddd, Situacao) VALUES (?, ?, ?, ?);";

	final String update = "UPDATE cidades SET IdEstado = ?, Nome = ?, Ddd = ?, Situacao = ? WHERE Id = ?;";

	final String delete = "DELETE FROM cidades WHERE Id = ?;";

	final String selectAll = "SELECT Id, IdEstado, Nome, Ddd, Situacao FROM cidades;";

	final String select = "SELECT Id, IdEstado, Nome, Ddd, Situacao FROM cidades WHERE ID = ?;";

	private Connection conexao;
	private EstadoServices estadoService;

	public CidadeDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void insert(Cidade obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			st.setLong(1, obj.getEstado().getId());
			st.setString(2, obj.getNome());
			st.setString(3, obj.getDdd());
			st.setString(4, obj.getSituacao().toString());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected < 1) {
				System.out.println("Erro ao salvar os dados.");
				System.out.println(st.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(st.toString());
		} finally {
			DBConnection.closeStatement(st);
		}
	}

	@Override
	public void update(Cidade obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);

			st.setLong(1, obj.getEstado().getId());
			st.setString(2, obj.getNome());
			st.setString(3, obj.getDdd());
			st.setString(4, obj.getSituacao().toString());
			st.setLong(5, obj.getId());

			st.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(st.toString());
		} finally {
			DBConnection.closeStatement(st);
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
			DBConnection.closeStatement(st);
		}
	}

	@Override
	public Cidade find(Long id) {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conexao.prepareStatement(select);
			st.setLong(1, id);
			rs = st.executeQuery();
			if (rs.next()) {

				if (estadoService == null) {
					setEstadoServices(new EstadoServices());
				}

				Cidade obj = new Cidade(rs.getLong("Id"), rs.getString("Nome"), rs.getString("Ddd"),
						Situacao.valueOf(rs.getString("Situacao")), estadoService.pesquisar(rs.getLong("IdEstado")));
				return obj;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(st);
			DBConnection.closeResultSet(rs);
		}
		return null;
	}

	@Override
	public List<Cidade> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conexao.prepareStatement(selectAll);
			rs = st.executeQuery();

			List<Cidade> list = new ArrayList<>();

			if (estadoService == null) {
				setEstadoServices(new EstadoServices());
			}

			while (rs.next()) {
				Cidade obj = new Cidade(rs.getLong("Id"), rs.getString("Nome"), rs.getString("Ddd"),
						Situacao.valueOf(rs.getString("Situacao")), estadoService.pesquisar(rs.getLong("IdEstado")));
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(st);
			DBConnection.closeResultSet(rs);
		}
		return null;
	}

	private void setEstadoServices(EstadoServices estadoService) {
		this.estadoService = estadoService;
	}

}
