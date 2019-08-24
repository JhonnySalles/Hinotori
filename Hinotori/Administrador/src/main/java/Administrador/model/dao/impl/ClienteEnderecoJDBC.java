package Administrador.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Administrador.model.dao.ClienteEnderecoDao;
import Administrador.model.dao.services.BairroServices;
import Administrador.model.entities.ClienteEndereco;
import model.enums.Situacao;
import model.log.ManipulaLog;
import model.mysql.DB;

public class ClienteEnderecoJDBC implements ClienteEnderecoDao {

	final String insert = "INSERT INTO Clientes_Enderecos (IdCliente, IdBairro, Endereco,"
			+ " Numero, CEP, Complemento, Observacao, Situacao) VALUES (?,?,?,?,?,?,?,?');";

	final String update = "UPDATE Clientes_Enderecos SET IdCliente = ?, IdBairro = ?, "
			+ " Endereco = ?, Numero = ?, CEP = ?, Observacao = ?, Situacao = ? WHERE ID = ?;";

	final String delete = "DELETE FROM Clientes_Enderecos WHERE ID = ?;";

	final String selectAll = "SELECT ID, IdCliente, IdBairro, Endereco, Numero, CEP, "
			+ " Complemento, Observacao, Situacao FROM Clientes_Enderecos;";

	final String select = "SELECT ID, IdCliente, IdBairro, Endereco, Numero, CEP, Complemento, "
			+ " Observacao, Situacao FROM Clientes_Enderecos WHERE ID = ?;";

	private Connection conexao;
	private BairroServices bairroService;

	public ClienteEnderecoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void insert(Long cliente, ClienteEndereco obj) {
		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			st.setLong(1, cliente);
			st.setLong(2, obj.getBairro().getId());
			st.setString(3, obj.getEndereco());
			st.setString(4, obj.getNumero());
			st.setString(5, obj.getCep());
			st.setString(6, obj.getComplemento());
			st.setString(7, obj.getObservacao());
			st.setString(8, obj.getSituacao().toString());

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
	public void update(Long cliente, ClienteEndereco obj) {
		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);

			st.setLong(1, cliente);
			st.setLong(2, obj.getBairro().getId());
			st.setString(3, obj.getEndereco());
			st.setString(4, obj.getNumero());
			st.setString(5, obj.getCep());
			st.setString(6, obj.getComplemento());
			st.setString(7, obj.getObservacao());
			st.setString(8, obj.getSituacao().toString());
			st.setLong(9, obj.getId());

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
	public ClienteEndereco find(Long cliente, Long id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conexao.prepareStatement(select);
			st.setLong(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				if (bairroService == null) {
					setBairroServices(new BairroServices());
				}
				ClienteEndereco obj = new ClienteEndereco(rs.getLong("Id"), rs.getString("endereco"),
						rs.getString("numero"), rs.getString("cep"), rs.getString("complemento"),
						rs.getString("observacao"), Situacao.valueOf(rs.getString("Situacao")),
						bairroService.pesquisar(rs.getLong("IdBairro")));
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
	public List<ClienteEndereco> findAll(Long cliente) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conexao.prepareStatement(selectAll);
			rs = st.executeQuery();

			List<ClienteEndereco> list = new ArrayList<>();
			if (bairroService == null) {
				setBairroServices(new BairroServices());
			}

			while (rs.next()) {
				ClienteEndereco obj = new ClienteEndereco(rs.getLong("Id"), rs.getString("endereco"),
						rs.getString("numero"), rs.getString("cep"), rs.getString("complemento"),
						rs.getString("observacao"), Situacao.valueOf(rs.getString("Situacao")),
						bairroService.pesquisar(rs.getLong("IdBairro")));
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

	private void setBairroServices(BairroServices bairroService) {
		this.bairroService = bairroService;
	}

}
