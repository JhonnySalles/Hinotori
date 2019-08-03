package Administrador.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import Administrador.model.dao.PaisDao;
import Administrador.model.entities.Pais;
import mysql.DB;

public class PaisDaoJDBC implements PaisDao {

	final String insert = "INSERT INTO paises (Nome) VALUES (?);";

	final String update = "UPDATE paises SET Nome = ? WHERE Id = ?;";

	final String delete = "DELETE FROM paises WHERE ID = ?;";

	final String selectAll = "SELECT Id, Nome FROM paises;";

	final String select = "SELECT Id, Nome FROM paises WHERE ID = ?;";

	private Connection conexao;

	public PaisDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void insert(Pais obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNome());

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
	public void update(Pais obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNome());
			st.setLong(2, obj.getId());

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
	public Pais find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pais> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
