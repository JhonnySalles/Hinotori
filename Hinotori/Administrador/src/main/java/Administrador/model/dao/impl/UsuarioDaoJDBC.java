package Administrador.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import Administrador.model.dao.UsuarioDao;
import Administrador.model.entities.Usuario;
import mysql.DB;

public class UsuarioDaoJDBC implements UsuarioDao {

	final String insert = "INSERT INTO usuarios (Id_Bairro, Login, Nome,"
			+ " Sobrenome, Senha, Email, Data_Cadastro, Observacao, Ddd_Telefone, Telefone,"
			+ " Ddd_Celular, Celular, Imagem, Nivel, Situacao) VALUES"
			+ " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	final String update = "UPDATE usuarios SET Id_Bairro = ?, Nome = ?, Sobrenome = ?, Senha = ?,"
			+ " Email = ?, Observacao = ?, Ddd_Telefone = ?, Telefone = ?, Ddd_Celular = ?,"
			+ " Celular = ?, Imagem = ?, Nivel = ?, Situacao = ? WHERE Login = ?;";

	final String delete = "DELETE FROM usuarios WHERE ID = ?;";

	final String selectAll = "SELECT Login, Id_Bairro, Nome, Sobrenome, Senha, Email, Data_Cadastro, Observacao,"
			+ " Ddd_Telefone, Telefone, Ddd_Celular, Celular, Imagem, Nivel, Situacao FROM usuarios;";

	final String select = "SELECT Login, Id_Bairro, Nome, Sobrenome, Senha, Email, Data_Cadastro, Observacao,"
			+ " Ddd_Telefone, Telefone, Ddd_Celular, Celular, Imagem, Nivel, Situacao FROM usuarios WHERE ID = ?;";

	private Connection conexao;

	public UsuarioDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void insert(Usuario obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			st.setLong(1, obj.getIdBairro());
			st.setString(2, obj.getLogin());
			st.setString(3, obj.getNome());
			st.setString(4, obj.getSobreNome());
			st.setString(5, obj.getSenha());
			st.setString(6, obj.getEmail());
			st.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			st.setString(8, obj.getObservacao());
			st.setString(9, obj.getDddTelefone());
			st.setString(10, obj.getTelefone());
			st.setString(11, obj.getDddCelular());
			st.setString(12, obj.getCelular());

			// st.setString(13, obj.getImagem());

			st.setString(14, obj.getNivel().toString());
			st.setString(15, obj.getSituacao().toString());

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
	public void update(Usuario obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			st.setLong(1, obj.getIdBairro());
			st.setString(2, obj.getNome());
			st.setString(3, obj.getSobreNome());
			st.setString(4, obj.getSenha());
			st.setString(5, obj.getEmail());
			st.setString(6, obj.getObservacao());
			st.setString(7, obj.getDddTelefone());
			st.setString(8, obj.getTelefone());
			st.setString(9, obj.getDddCelular());
			st.setString(10, obj.getCelular());

			// st.setString(11, obj.getImagem());

			st.setString(12, obj.getNivel().toString());
			st.setString(13, obj.getSituacao().toString());
			st.setString(14, obj.getLogin());

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
	public Usuario find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
