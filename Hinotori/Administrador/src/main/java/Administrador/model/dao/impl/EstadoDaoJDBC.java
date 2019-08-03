package Administrador.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import Administrador.model.dao.EstadoDao;
import Administrador.model.entities.Estado;
import mysql.DB;

public class EstadoDaoJDBC implements EstadoDao {

	final String insert = "INSERT INTO estados (Nome, Sigla, CodigoIBGE, Id_Pais) VALUES (?, ?, ?, ?);";

	final String update = "UPDATE estados SET Nome = ?, Sigla = ?, CodigoIBGE = ?, Id_Pais = ? WHERE Id = ?;";

	final String delete = "DELETE FROM estados WHERE ID = ?;";

	final String selectAll = "SELECT Id, Nome, Sigla, CodigoIBGE, Id_Pais FROM estados;";

	final String select = "SELECT Id, Nome, Sigla, CodigoIBGE, Id_Pais FROM estados WHERE ID = ?;";

	private Connection conexao;

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
			st.setLong(4, obj.getIdPais());

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
	public void update(Estado obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNome());
			st.setString(2, obj.getSigla());
			st.setInt(3, obj.getCodigoIBGE());
			st.setLong(4, obj.getIdPais());
			st.setLong(5, obj.getId());

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
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Estado find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Estado> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
