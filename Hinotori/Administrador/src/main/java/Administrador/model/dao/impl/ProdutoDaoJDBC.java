package Administrador.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import Administrador.model.dao.ProdutoDao;
import Administrador.model.entities.Produto;
import model.enums.Situacao;
import model.enums.TipoProduto;
import model.log.ManipulaLog;
import model.mysql.DB;

public class ProdutoDaoJDBC implements ProdutoDao {

	final String insert = "INSERT INTO produtos (Descricao, Observacao, Codigo_Barras, Unidade,"
			+ " Data_Cadastro, Tipo, Peso_Bruto, Peso_Liquido, Situacao) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

	final String update = "UPDATE produtos SET Descricao = ?, Observacao = ?, Codigo_Barras = ?, Unidade = ?,"
			+ " Tipo = ?, Peso_Bruto = ?, Peso_Liquido = ?, Situacao = ? WHERE ID = ?;";

	final String delete = "DELETE FROM produtos WHERE ID = ?;";

	final String selectAll = "SELECT ID, Descricao, Observacao, Codigo_Barras, Unidade, Data_Cadastro,"
			+ " Tipo, Peso_Bruto, Peso_Liquido, Situacao FROM produtos;";

	final String select = "SELECT ID, Descricao, Observacao, Codigo_Barras, Unidade, Data_Cadastro,"
			+ " Tipo, Peso_Bruto, Peso_Liquido, Situacao FROM produtos WHERE ID = ?;";

	private Connection conexao;

	public ProdutoDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void insert(Produto obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getDescricao());
			st.setString(2, obj.getObservacao());
			st.setString(3, obj.getCodigoBarras());
			st.setString(4, obj.getUnidade());
			st.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			st.setString(6, obj.getTipo().toString());
			st.setDouble(7, obj.getPesoBruto());
			st.setDouble(8, obj.getPesoLiquido());
			st.setString(9, obj.getSituacao().toString());

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
	public void update(Produto obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getDescricao());
			st.setString(2, obj.getObservacao());
			st.setString(3, obj.getCodigoBarras());
			st.setString(4, obj.getUnidade());
			st.setString(5, obj.getTipo().toString());
			st.setDouble(6, obj.getPesoBruto());
			st.setDouble(7, obj.getPesoLiquido());
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
	public Produto find(Long id) {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conexao.prepareStatement(select);
			st.setLong(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Produto obj = new Produto(rs.getLong("Id"), rs.getString("Descricao"), rs.getString("Observacao"),
						rs.getString("Codigo_Barras"), rs.getString("Unidade"), rs.getDate("Data_Cadastro"),
						rs.getDouble("Peso_Bruto"), rs.getDouble("Peso_Liquido"),
						TipoProduto.valueOf(rs.getString("Tipo")), Situacao.valueOf(rs.getString("Situacao")));
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
	public List<Produto> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conexao.prepareStatement(selectAll);
			rs = st.executeQuery();

			List<Produto> list = new ArrayList<>();

			while (rs.next()) {
				Produto obj = new Produto(rs.getLong("Id"), rs.getString("Descricao"), rs.getString("Observacao"),
						rs.getString("Codigo_Barras"), rs.getString("Unidade"), rs.getDate("Data_Cadastro"),
						rs.getDouble("Peso_Bruto"), rs.getDouble("Peso_Liquido"),
						TipoProduto.valueOf(rs.getString("Tipo")), Situacao.valueOf(rs.getString("Situacao")));
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

}
