package Administrador.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import Administrador.model.dao.TamanhoDao;
import Administrador.model.entities.Tamanho;
import mysql.DB;

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
	public void update(Tamanho obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getSigla());
			st.setString(2, obj.getDescricao());
			st.setInt(3, obj.getQtdPedacos());
			st.setInt(4, obj.getQtdSabores());
			st.setString(5, obj.getSituacao().toString());
			st.setLong(6, obj.getId());

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
	public Tamanho find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tamanho> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
