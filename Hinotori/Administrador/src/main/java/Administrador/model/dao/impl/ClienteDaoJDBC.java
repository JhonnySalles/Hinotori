package Administrador.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import Administrador.model.dao.ClienteDao;
import Administrador.model.dao.services.ClienteEnderecoServices;
import Administrador.model.entities.Cliente;
import model.enums.Situacao;
import model.enums.UsuarioNivel;
import model.log.ManipulaLog;
import model.mysql.DB;

public class ClienteDaoJDBC implements ClienteDao {

	final String insert = "INSERT INTO clientes (Nome, Sobrenome, dddTelefone,"
			+ " Telefone, dddCelular, Celular, Email, DataCadastro, UltimaAlteracao, "
			+ " Observacao, Tipo, Situacao ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?');";

	final String update = "UPDATE clientes SET Nome = ?, Sobrenome = ?, "
			+ " dddTelefone = ?, Telefone = ?, dddCelular = ?," + " Celular = ?, Email = ?, UltimaAlteracao = ?,"
			+ " Observacao = ?, Tipo = ?, Situacao = ? WHERE ID = ?;";

	final String delete = "DELETE FROM clientes WHERE ID = ?;";

	final String selectAll = "SELECT ID, Nome, Sobrenome, dddTelefone,"
			+ " Telefone, dddCelular, Celular, Email, DataCadastro, UltimaAlteracao,"
			+ " Observacao, Tipo, Situacao FROM clientes;";

	final String select = "SELECT ID, Nome, Sobrenome, dddTelefone,"
			+ " Telefone, dddCelular, Celular, Email, DataCadastro, UltimaAlteracao,"
			+ " Observacao, Tipo, Situacao FROM clientes WHERE ID = ?;";

	private Connection conexao;
	private ClienteEnderecoServices clienteEnderecoService;

	public ClienteDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void insert(Cliente obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNome());
			st.setString(2, obj.getSobreNome());
			st.setString(3, obj.getDddTelefone());
			st.setString(4, obj.getTelefone());
			st.setString(5, obj.getDddCelular());
			st.setString(6, obj.getCelular());
			st.setString(7, obj.getEmail());
			st.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
			st.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
			st.setString(10, obj.getObservacao());
			st.setString(11, obj.getTipo().toString());
			st.setString(12, obj.getSituacao().toString());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected < 1) {
				System.out.println("Erro ao salvar os dados.");
				System.out.println(st.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(st.toString());
			ManipulaLog.salvar(this.getClass(), "JDBC - INSERT", st.toString(), e.toString());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Cliente obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNome());
			st.setString(2, obj.getSobreNome());
			st.setString(3, obj.getDddTelefone());
			st.setString(4, obj.getTelefone());
			st.setString(5, obj.getDddCelular());
			st.setString(6, obj.getCelular());
			st.setString(7, obj.getEmail());
			st.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
			st.setString(9, obj.getObservacao());
			st.setString(10, obj.getTipo().toString());
			st.setString(11, obj.getSituacao().toString());
			st.setLong(12, obj.getId());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected < 1) {
				System.out.println("Erro ao salvar os dados.");
				System.out.println(st.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(st.toString());
			ManipulaLog.salvar(this.getClass(), "JDBC - UPDATE", st.toString(), e.toString());
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
				System.out.println("Erro ao deletar os dados.");
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
	public Cliente find(Long id) {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conexao.prepareStatement(select);
			st.setLong(1, id);
			rs = st.executeQuery();
			if (rs.next()) {

				if (clienteEnderecoService == null) {
					setClienteEnderecoServices(new ClienteEnderecoServices());
				}

				Cliente obj = new Cliente(rs.getLong("Id"), rs.getString("Nome"), rs.getString("Sobrenome"),
						rs.getString("dddTelefone"), rs.getString("Telefone"), rs.getString("dddCelular"),
						rs.getString("Celular"), rs.getString("Email"), rs.getString("cpfCnpj"),
						rs.getString("Observacao"), UsuarioNivel.valueOf(rs.getString("Tipo")),
						Situacao.valueOf(rs.getString("Situacao")),
						clienteEnderecoService.pesquisarTodos(rs.getLong("Id")));
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
	public List<Cliente> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conexao.prepareStatement(selectAll);
			rs = st.executeQuery();

			List<Cliente> list = new ArrayList<>();

			if (clienteEnderecoService == null) {
				setClienteEnderecoServices(new ClienteEnderecoServices());
			}

			while (rs.next()) {
				Cliente obj = new Cliente(rs.getLong("Id"), rs.getString("Nome"), rs.getString("Sobrenome"),
						rs.getString("dddTelefone"), rs.getString("Telefone"), rs.getString("dddCelular"),
						rs.getString("Celular"), rs.getString("Email"), rs.getString("cpfCnpj"),
						rs.getString("Observacao"), UsuarioNivel.valueOf(rs.getString("Tipo")),
						Situacao.valueOf(rs.getString("Situacao")),
						clienteEnderecoService.pesquisarTodos(rs.getLong("Id")));
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

	private void setClienteEnderecoServices(ClienteEnderecoServices clienteEnderecoService) {
		this.clienteEnderecoService = clienteEnderecoService;
	}

}
