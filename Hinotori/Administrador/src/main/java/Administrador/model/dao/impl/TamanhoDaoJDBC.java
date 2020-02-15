package administrador.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import administrador.model.dao.TamanhoDao;
import administrador.model.entities.Tamanho;
import comum.model.mysql.DB;

public class TamanhoDaoJDBC implements TamanhoDao {

	final String insert = "INSERT INTO tamanhos (Sigla, Descricao, Quantidade_Pedacos,"
			+ " Quantidade_Sabores, Situacao) VALUES(?, ?, ?, ?, ?);";

	final String update = "UPDATE tamanhos SET Sigla = ?, Descricao = ?, Quantidade_Pedacos = ?,"
			+ " Quantidade_Sabores = ?, Situacao = ? WHERE ID = ?;";

	final String delete = "DELETE FROM tamanhos WHERE ID = ?;";

	final String selectAll = "SELECT ID, Sigla, Descricao, Quantidade_Pedacos,"
			+ " Quantidade_Sabores, Situacao FROM tamanhos;";

	final String select = "SELECT ID, Sigla, Descricao, Quantidade_Pedacos,"
			+ " Quantidade_Sabores, Situacao FROM tamanhos WHERE ID = ?;";

	private Connection conexao;

	public TamanhoDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void insert(Tamanho obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getSigla());
			st.setString(2, obj.getDescricao());
			st.setInt(3, obj.getQtdPedacos());
			st.setInt(4, obj.getQtdSabores());
			//st.setString(5, obj.getSituacao().toString());

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
	public void update(Tamanho obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getSigla());
			st.setString(2, obj.getDescricao());
			st.setInt(3, obj.getQtdPedacos());
			st.setInt(4, obj.getQtdSabores());
			//st.setString(5, obj.getSituacao().toString());
			st.setLong(6, obj.getId());

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
	public Tamanho find(Long id) {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conexao.prepareStatement(select);
			st.setLong(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Tamanho obj = new Tamanho(rs.getLong("Id"), rs.getString("Sigla"), rs.getString("Descricao"),
						rs.getInt("Quantidade_Pedacos"), rs.getInt("Quantidade_Sabores")
					//	Situacao.valueOf(rs.getString("Situacao"))
						);
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
	public List<Tamanho> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conexao.prepareStatement(selectAll);
			rs = st.executeQuery();

			List<Tamanho> list = new ArrayList<>();

			while (rs.next()) {
				Tamanho obj = new Tamanho(rs.getLong("Id"), rs.getString("Sigla"), rs.getString("Descricao"),
						rs.getInt("Quantidade_Pedacos"), rs.getInt("Quantidade_Sabores")
						//Situacao.valueOf(rs.getString("Situacao"))
						);
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
