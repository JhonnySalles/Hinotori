package Administrador.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import Administrador.model.dao.EmpresaDao;
import Administrador.model.entities.Empresa;
import mysql.DB;

public class EmpresaDaoJDBC implements EmpresaDao {

	final String insert = "INSERT INTO empresas ( ID_Bairro, Nome_Fantasia,"
			+ " Razao_Social, CNPJ, Endereco, Numero, CEP, Complemento,"
			+ " Data_Cadastro, Situacao ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

	final String update = "UPDATE empresas SET ID_Bairro = ?, Nome_Fantasia = ?,"
			+ " Razao_Social = ?, CNPJ = ?, Endereco = ?, Numero = ?, CEP = ?,"
			+ " Complemento = ?, Situacao = ? WHERE ID = ?;";

	final String delete = "DELETE FROM empresas WHERE ID = ?;";

	final String selectAll = "SELECT ID, ID_Bairro, Nome_Fantasia, Razao_Social, CNPJ,"
			+ " Endereco, Numero, CEP, Complemento, Data_Cadastro, Situacao FROM empresas";

	final String select = "SELECT ID, ID_Bairro, Nome_Fantasia, Razao_Social, CNPJ, Endereco,"
			+ " Numero, CEP, Complemento, Data_Cadastro, Situacao FROM empresas WHERE ID = ?;";

	private Connection conexao;

	public EmpresaDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void insert(Empresa obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			st.setLong(1, obj.getIdBairro());
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
			;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(st.toString());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(Empresa obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);

			st.setLong(1, obj.getIdBairro());
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
	public Empresa find(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Empresa> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
