package Administrador.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import Administrador.model.dao.ClienteDao;
import Administrador.model.entities.Cliente;
import mysql.DB;

public class ClienteDaoJDBC implements ClienteDao {

	final String insert = "INSERT INTO clientes (ID, ID_Bairro, Nome, Sobrenome,"
			+ " DDD_Telefone, Telefone, DDD_Celular, Celular, Email, Data_Cadastro, "
			+ " Ultima_Alteracao, Observacao, Tipo, Situacao ) " + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?');";

	final String update = "UPDATE clientes SET ID_Bairro = ?, Nome = ?,"
			+ " Sobrenome = ?, DDD_Telefone = ?, Telefone = ?, DDD_Celular = ?,"
			+ " Celular = ?, Email = ?, Ultima_Alteracao = ?,"
			+ " Observacao = ?, Tipo = ?, Situacao = ? WHERE ID = ?;";

	final String delete = "DELETE FROM clientes WHERE ID = ?;";

	final String selectAll = "SELECT ID, ID_Bairro, Nome, Sobrenome, DDD_Telefone,"
			+ " Telefone, DDD_Celular, Celular, Email, Data_Cadastro, Ultima_Alteracao,"
			+ " Observacao, Tipo, Situacao FROM clientes;";

	final String select = "SELECT ID, ID_Bairro, Nome, Sobrenome, DDD_Telefone,"
			+ " Telefone, DDD_Celular, Celular, Email, Data_Cadastro, Ultima_Alteracao,"
			+ " Observacao, Tipo, Situacao FROM clientes WHERE ID = ?;";

	private Connection conexao;

	public ClienteDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void insert(Cliente obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			st.setLong(1, obj.getId());
			st.setLong(2, obj.getIdBairro());
			st.setString(3, obj.getNome());
			st.setString(4, obj.getSobreNome());
			st.setString(5, obj.getDddTelefone());
			st.setString(6, obj.getTelefone());
			st.setString(7, obj.getDddCelular());
			st.setString(8, obj.getCelular());
			st.setString(9, obj.getEmail());
			st.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
			st.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
			st.setString(12, obj.getObservacao());
			st.setString(13, obj.getTipo().toString());
			st.setString(14, obj.getSituacao().toString());

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
	public void update(Cliente obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);

			st.setLong(1, obj.getIdBairro());
			st.setString(2, obj.getNome());
			st.setString(3, obj.getSobreNome());
			st.setString(4, obj.getDddTelefone());
			st.setString(5, obj.getTelefone());
			st.setString(6, obj.getDddCelular());
			st.setString(7, obj.getCelular());
			st.setString(8, obj.getEmail());
			st.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
			st.setString(10, obj.getObservacao());
			st.setString(11, obj.getTipo().toString());
			st.setString(12, obj.getSituacao().toString());
			st.setLong(13, obj.getId());

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
	public Cliente find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
