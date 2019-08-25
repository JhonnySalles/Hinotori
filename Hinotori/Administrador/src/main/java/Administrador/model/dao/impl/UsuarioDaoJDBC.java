package Administrador.model.dao.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import Administrador.model.dao.UsuarioDao;
import Administrador.model.entities.Usuario;
import model.encode.DecodeHash;
import model.enums.Situacao;
import model.enums.UsuarioNivel;
import model.log.ManipulaLog;
import model.mysql.DB;

public class UsuarioDaoJDBC implements UsuarioDao {

	final String insert = "INSERT INTO usuarios (Id_Empresa, Login, Nome,"
			+ " Sobrenome, Senha, Email, Data_Cadastro, Observacao, Ddd_Telefone, Telefone,"
			+ " Ddd_Celular, Celular, Imagem, Nivel, Situacao) VALUES"
			+ " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	final String update = "UPDATE usuarios SET Id_Empresa = ?, Nome = ?, Sobrenome = ?, Senha = ?,"
			+ " Email = ?, Observacao = ?, Ddd_Telefone = ?, Telefone = ?, Ddd_Celular = ?,"
			+ " Celular = ?, Imagem = ?, Nivel = ?, Situacao = ? WHERE Login = ?;";

	final String delete = "UPDATE Situacao = \"EXCLUIDO\" WHERE Login ?;";

	final String selectAll = "SELECT Login, Id_Empresa, Nome, Sobrenome, Email, Data_Cadastro, Observacao,"
			+ " Ddd_Telefone, Telefone, Ddd_Celular, Celular, Imagem, Nivel, Situacao FROM usuarios WHERE Situacao <> \"EXCLUIDO\";";

	final String select = "SELECT Login, Id_Empresa, Nome, Sobrenome, Email, Data_Cadastro, Observacao, Ddd_Telefone,"
			+ " Telefone, Ddd_Celular, Celular, Imagem, Nivel, Situacao FROM usuarios WHERE Login = ?;";

	private Connection conexao;

	public UsuarioDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void insert(Usuario obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			st.setLong(1, obj.getIdEmpresa());
			st.setString(2, obj.getLogin());
			st.setString(3, obj.getNome());
			st.setString(4, obj.getSobreNome());
			st.setString(5, DecodeHash.DecodePassword(obj.getSenha()));
			st.setString(6, obj.getEmail());
			st.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			st.setString(8, obj.getObservacao());
			st.setString(9, obj.getDddTelefone());
			st.setString(10, obj.getTelefone());
			st.setString(11, obj.getDddCelular());
			st.setString(12, obj.getCelular());
			st.setBytes(13, obj.getImagem());
			st.setString(14, obj.getNivel().toString());
			st.setString(15, obj.getSituacao().toString());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected < 1) {
				System.out.println("Erro ao salvar os dados.");
				System.out.println(st.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(st.toString());
			ManipulaLog.salvar(this.getClass(), "JDBC - INSERT", st.toString(), e.toString());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			ManipulaLog.salvar(this.getClass(), "PASSWORD", st.toString(), e.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			ManipulaLog.salvar(this.getClass(), "PASSWORD", st.toString(), e.toString());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(Usuario obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);

			st.setLong(1, obj.getIdEmpresa());
			st.setString(2, obj.getNome());
			st.setString(3, obj.getSobreNome());
			st.setString(4, DecodeHash.DecodePassword(obj.getSenha()));
			st.setString(5, obj.getEmail());
			st.setString(6, obj.getObservacao());
			st.setString(7, obj.getDddTelefone());
			st.setString(8, obj.getTelefone());
			st.setString(9, obj.getDddCelular());
			st.setString(10, obj.getCelular());
			st.setBytes(11, obj.getImagem());
			st.setString(12, obj.getNivel().toString());
			st.setString(13, obj.getSituacao().toString());
			st.setString(14, obj.getLogin());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected < 1) {
				System.out.println("Erro ao salvar os dados.");
				System.out.println(st.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(st.toString());
			ManipulaLog.salvar(this.getClass(), "JDBC - UPDATE", st.toString(), e.toString());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			ManipulaLog.salvar(this.getClass(), "PASSWORD", st.toString(), e.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			ManipulaLog.salvar(this.getClass(), "PASSWORD", st.toString(), e.toString());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void delete(String login) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(delete, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, login);
			int rowsAffected = st.executeUpdate();

			if (rowsAffected < 1) {
				System.out.println("Erro ao salvar os dados.");
				System.out.println(st.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(st.toString());
			ManipulaLog.salvar(this.getClass(), "JDBC - DELETE", st.toString(), e.toString());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Usuario find(String login) {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conexao.prepareStatement(select);
			st.setString(1, login);
			rs = st.executeQuery();
			if (rs.next()) {

				Usuario obj = new Usuario(rs.getString("Nome"), rs.getString("Sobrenome"), rs.getString("Ddd_Telefone"),
						rs.getString("Telefone"), rs.getString("Ddd_Celular"), rs.getString("Celular"),
						rs.getString("Email"), rs.getDate("Data_Cadastro"), rs.getLong("Id_Empresa"),
						rs.getString("Login"), "", rs.getString("Observacao"), rs.getBytes("Imagem"),
						UsuarioNivel.valueOf(rs.getString("Nivel")), Situacao.valueOf(rs.getString("Situacao")));

				return obj;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ManipulaLog.salvar(this.getClass(), "JDBC - FIND", st.toString(), e.toString());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		return null;
	}

	@Override
	public List<Usuario> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conexao.prepareStatement(selectAll);
			rs = st.executeQuery();

			List<Usuario> list = new ArrayList<>();

			while (rs.next()) {

				Usuario obj = new Usuario(rs.getString("Nome"), rs.getString("Sobrenome"), rs.getString("Ddd_Telefone"),
						rs.getString("Telefone"), rs.getString("Ddd_Celular"), rs.getString("Celular"),
						rs.getString("Email"), rs.getDate("Data_Cadastro"), rs.getLong("Id_Empresa"),
						rs.getString("Login"), "", rs.getString("Observacao"), rs.getBytes("Imagem"),
						UsuarioNivel.valueOf(rs.getString("Nivel")), Situacao.valueOf(rs.getString("Situacao")));
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			ManipulaLog.salvar(this.getClass(), "JDBC - FIND ALL", st.toString(), e.toString());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		return null;
	}

	@Override
	public Boolean validaLogin(String login) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conexao.prepareStatement(select);
			st.setString(1, login);
			rs = st.executeQuery();
			if (rs.next())
				return true;
			else 
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			ManipulaLog.salvar(this.getClass(), "JDBC - VALIDA LOGIN", st.toString(), e.toString());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		return false;
	}
}
