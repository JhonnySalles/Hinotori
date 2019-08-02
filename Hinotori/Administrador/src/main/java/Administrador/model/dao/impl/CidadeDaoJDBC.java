package Administrador.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import Administrador.model.dao.CidadeDao;
import Administrador.model.entities.Cidade;
import mysql.DB;

public class CidadeDaoJDBC implements CidadeDao {

	final String insert = "INSERT INTO cidades (Id, Id_Estado, Nome, Ddd, Situacao) VALUES (?, ?, ?, ?, ?);";

	final String update = "UPDATE cidades SET Id_Estado = ?, Nome = ?, Ddd = ?, Situacao = ? WHERE Id = ?;";

	final String delete = "DELETE FROM cidades WHERE Id = ?;";

	final String selectAll = "SELECT Id, Id_Estado, Nome, Ddd, Situacao FROM cidades;";

	final String select = "SELECT Id, Id_Estado, Nome, Ddd, Situacao FROM cidades WHERE ID = ?;";

	private Connection conexao;

	public CidadeDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void insert(Cidade obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			st.setLong(1, obj.getId());
			st.setLong(2, obj.getIdEstado());
			st.setString(3, obj.getNome());
			st.setString(4, obj.getDdd());
			st.setString(5, obj.getSituacao().toString());

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
	public void update(Cidade obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);

			st.setLong(1, obj.getIdEstado());
			st.setString(2, obj.getNome());
			st.setString(3, obj.getDdd());
			st.setString(4, obj.getSituacao().toString());
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
	public Cidade find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cidade> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
