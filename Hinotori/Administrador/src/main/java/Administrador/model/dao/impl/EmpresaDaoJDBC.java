package Administrador.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import Administrador.model.dao.EmpresaDao;
import Administrador.model.dao.services.BairroServices;
import Administrador.model.entities.Empresa;
import model.enums.Situacao;
import model.log.ManipulaLog;
import model.mysql.DB;

public class EmpresaDaoJDBC implements EmpresaDao {

	final String insert = "INSERT INTO empresas (IdBairro, NomeFantasia,"
			+ " RazaoSocial, CNPJ, Endereco, Numero, CEP, Complemento,"
			+ " DataCadastro, Situacao ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	final String update = "UPDATE empresas SET IdBairro = ?, NomeFantasia = ?,"
			+ " RazaoSocial = ?, CNPJ = ?, Endereco = ?, Numero = ?, CEP = ?,"
			+ " Complemento = ?, Situacao = ? WHERE ID = ?;";

	final String delete = "DELETE FROM empresas WHERE ID = ?;";

	final String selectAll = "SELECT ID, IdBairro, NomeFantasia, RazaoSocial, CNPJ,"
			+ " Endereco, Numero, CEP, Complemento, DataCadastro, Situacao FROM empresas";

	final String select = "SELECT ID, IdBairro, NomeFantasia, RazaoSocial, CNPJ, Endereco,"
			+ " Numero, CEP, Complemento, DataCadastro, Situacao FROM empresas WHERE ID = ?;";

	private Connection conexao;
	private BairroServices bairroService;

	public EmpresaDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void insert(Empresa obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			st.setLong(1, obj.getBairro().getId());
			st.setString(2, obj.getNomeFantasia());
			st.setString(3, obj.getRazaoSocial());
			st.setString(4, obj.getCnpj());
			st.setString(5, obj.getEndereco());
			st.setString(6, obj.getNumero());
			st.setString(7, obj.getCep());
			st.setString(8, obj.getComplemento());
			st.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
			st.setString(10, obj.getSituacao().toString());

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
	public void update(Empresa obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);

			st.setLong(1, obj.getBairro().getId());
			st.setString(2, obj.getNomeFantasia());
			st.setString(3, obj.getRazaoSocial());
			st.setString(4, obj.getCnpj());
			st.setString(5, obj.getEndereco());
			st.setString(6, obj.getNumero());
			st.setString(7, obj.getCep());
			st.setString(8, obj.getComplemento());
			st.setString(9, obj.getSituacao().toString());
			st.setLong(10, obj.getId());

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
	public Empresa find(Long id) {

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
				Empresa obj = new Empresa(rs.getLong("Id"), rs.getString("NomeFantasia"), rs.getString("RazaoSocial"),
						rs.getString("CNPJ"), rs.getString("Endereco"), rs.getString("Numero"), rs.getString("CEP"),
						rs.getString("Complemento"), rs.getTimestamp("DataCadastro"),
						Situacao.valueOf(rs.getString("Situacao")), bairroService.pesquisar(rs.getLong("IdBairro")));
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
	public List<Empresa> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conexao.prepareStatement(selectAll);
			rs = st.executeQuery();

			List<Empresa> list = new ArrayList<>();
			if (bairroService == null) {
				setBairroServices(new BairroServices());
			}

			while (rs.next()) {
				Empresa obj = new Empresa(rs.getLong("Id"), rs.getString("NomeFantasia"), rs.getString("RazaoSocial"),
						rs.getString("CNPJ"), rs.getString("Endereco"), rs.getString("Numero"), rs.getString("CEP"),
						rs.getString("Complemento"), rs.getTimestamp("DataCadastro"),
						Situacao.valueOf(rs.getString("Situacao")), bairroService.pesquisar(rs.getLong("IdBairro")));
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
