package Administrador.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import Administrador.model.dao.BairroDao;
import Administrador.model.entities.Bairro;
import log.SalvarLog;
import mysql.DB;

public class BairroDaoJDBC implements BairroDao {

	final String insert = "INSERT INTO bairros (Id_Cidade, Nome) VALUES (?, ?);";

	final String update = "UPDATE bairros SET Id_Cidade = ?, Nome = ? WHERE Id = ?;";

	final String delete = "DELETE FROM bairros WHERE Id = ?;";

	final String selectAll = "SELECT Id, Id_Cidade, Nome FROM bairros ";

	final String select = "SELECT Id, Id_Cidade, Nome FROM bairros WHERE Id = ?;";

	private Connection conexao;

	public BairroDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void insert(Bairro obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			st.setLong(1, obj.getId_Cidade());
			st.setString(2, obj.getNome());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected < 1) {
				System.out.println("Erro ao salvar os dados.");
				System.out.println(st.toString());
			}
			;
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

			st.setLong(1, obj.getId_Cidade());
			st.setString(2, obj.getNome());
			st.setLong(3, obj.getId());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected < 1) {
				System.out.println("Erro ao salvar os dados.");
				System.out.println(st.toString());
			}
			;
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
			;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(st.toString());
			SalvarLog.salvar(this.getClass(), "JDBC - DELETE", e.toString());
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
				Bairro obj = new Bairro(rs.getLong("Id"), rs.getLong("Id_Cidade"), rs.getString("Nome"));
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
		// TODO Auto-generated method stub
		return null;
	}

}
