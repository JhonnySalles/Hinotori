package administrador.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import administrador.model.dao.SaborDao;
import administrador.model.entities.Sabor;
import comum.model.enums.Situacao;
import comum.model.mysql.DB;

public class SaborDaoJDBC implements SaborDao {

	final String insert = "INSERT INTO sabores (Descricao, Observacao, Situacao) VALUES(?, ?, ?);";

	final String update = "UPDATE sabores SET Descricao = ?, Observacao = ?, Situacao = ? WHERE ID = ?;";

	final String delete = "DELETE FROM sabores WHERE ID = ?;";

	final String selectAll = "SELECT ID, Descricao, Observacao, Situacao FROM sabores;";

	final String select = "SELECT ID, Descricao, Observacao, Situacao FROM sabores WHERE ID = ?;";

	private Connection conexao;

	public SaborDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void insert(Sabor obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getDescricao());
			st.setString(2, obj.getObservacao());
			//st.setString(3, obj.getSituacao().toString());

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
	public void update(Sabor obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getDescricao());
			st.setString(2, obj.getObservacao());
			//st.setString(3, obj.getSituacao().toString());
			st.setLong(4, obj.getId());

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
	public Sabor find(Long id) {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conexao.prepareStatement(select);
			st.setLong(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Sabor obj = new Sabor(rs.getLong("Id"), rs.getString("Descricao"), rs.getString("Observacao"),
						Situacao.valueOf(rs.getString("Situacao")));
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
	public List<Sabor> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conexao.prepareStatement(selectAll);
			rs = st.executeQuery();

			List<Sabor> list = new ArrayList<>();

			while (rs.next()) {
				Sabor obj = new Sabor(rs.getLong("Id"), rs.getString("Descricao"), rs.getString("Observacao"),
						Situacao.valueOf(rs.getString("Situacao")));
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

}
