package servidor.dao.implementJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import comum.model.enums.Situacao;
import comum.model.enums.TipoCliente;
import comum.model.enums.TipoContato;
import comum.model.enums.TipoEndereco;
import comum.model.enums.TipoPessoa;
import comum.model.exceptions.ExcessaoBd;
import comum.model.messages.Mensagens;
import servidor.dao.ClienteDao;
import servidor.dao.services.BairroServices;
import servidor.entities.Cliente;
import servidor.entities.Contato;
import servidor.entities.Endereco;
import servidor.util.DBConnection;

public class ClienteDaoJDBC implements ClienteDao {

	final String INSERT = "INSERT INTO clientes (NomeSobrenome, "
			+ " Cpf, Cnpj, DataCadastro, DataUltimaAlteracao, Observacao, Tipo, "
			+ " Enquadramento, Situacao ) VALUES (?,?,?,?,?,?,?,?,?);";

	final String UPDATE = "UPDATE clientes SET NomeSobrenome = ?, "
			+ " Cpf = ?, Cnpj = ?, Observacao = ?, DataUltimaAlteracao = ?, Tipo = ?, "
			+ " Enquadramento = ?, Situacao = ? WHERE ID = ?";

	final String DELETE = "UPDATE clientes SET Situacao = 'Excluído' WHERE ID = ?";

	final String SELECT_ALL = "SELECT ID, NomeSobrenome, Cpf, Cnpj, DataCadastro, DataUltimaAlteracao, "
			+ " Observacao, Tipo, Enquadramento, Situacao FROM clientes WHERE Situacao <> 'Excluído'";

	final String SELECT = "SELECT ID, NomeSobrenome, Cpf, Cnpj, DataCadastro, DataUltimaAlteracao, "
			+ "  Observacao, Tipo, Enquadramento, Situacao FROM clientes WHERE ID = ?";

	final String SELECT_ENDERECO = "SELECT IdSequencial, IdBairro, Endereco, Numero, CEP, "
			+ " Complemento, Observacao, DataCadastro, Tipo, Situacao, Padrao FROM clientes_enderecos "
			+ "WHERE IdCliente = ? AND Situacao <> 'Excluído' ";

	final String INSERT_ENDERECO = "INSERT INTO clientes_enderecos (IdCliente, IdSequencial, IdBairro, Endereco, "
			+ " Numero, CEP, Complemento, Observacao, DataCadastro, Tipo, Situacao, Padrao) VALUES (?,"
			+ " (SELECT IFNULL(MAX(cliEnd.IdSequencial),0)+1 FROM clientes_enderecos cliEnd WHERE cliEnd.IdCliente = ?),"
			+ " ?,?,?,?,?,?,?,?,?,?)";

	final String UPDATE_ENDERECO = "UPDATE clientes_enderecos SET IdBairro = ?,"
			+ " Endereco = ?, Numero = ?, CEP = ?, Complemento = ?, Observacao = ?, DataCadastro = ?,"
			+ " Tipo = ?, Situacao = ?, Padrao = ? WHERE IdCliente = ? AND IdSequencial = ?";

	final String SELECT_CONTATO = "SELECT IdSequencial, Nome, Telefone, Celular, Email, Observacao, Tipo, "
			+ " Situacao, Padrao FROM clientes_contatos WHERE IdCliente = ? AND Situacao <> 'Excluído' ";

	final String INSERT_CONTATO = "INSERT INTO clientes_contatos (IdCliente, IdSequencial, "
			+ " Nome, Telefone, Celular, Email, Observacao, DataCadastro, Tipo, Situacao, Padrao) VALUES (?,"
			+ " (SELECT IFNULL(MAX(cliCont.IdSequencial),0)+1 FROM clientes_contatos cliCont WHERE cliCont.IdCliente = ?),"
			+ " ?,?,?,?,?,??,,?,?)";

	final String UPDATE_CONTATO = "UPDATE clientes_contatos SET Nome = ?, Telefone = ?, Celular = ?, Email = ?, "
			+ " Observacao = ?, DataCadastro = ?, Tipo = ?, Situacao = ?, Padrao = ? WHERE IdCliente = ? AND IdSequencial = ?";

	private Connection conexao;
	private BairroServices bairroService;

	public ClienteDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	private void setBairroServices(BairroServices bairroService) {
		this.bairroService = bairroService;
	}

	@Override
	public void insert(Cliente obj) throws ExcessaoBd {

		PreparedStatement st = null;

		try {
			st = conexao.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNomeSobrenome());
			st.setString(2, obj.getCpf());
			st.setString(3, obj.getCnpj());
			st.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			st.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			st.setString(6, obj.getObservacao());
			st.setString(7, obj.getTipoPessoa().toString());
			st.setString(8, obj.getTipoCliente().toString());
			st.setString(9, obj.getSituacao().toString());

			int rowsAffected = st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();

			if (rs.next()) {
				obj.setId(rs.getLong(1));
			}

			updateContatos(obj.getId(), obj.getContatos());
			updateEnderecos(obj.getId(), obj.getEnderecos());

			if (rowsAffected < 1) {
				System.out.println("Erro ao salvar os dados.\n" + st.toString());
				throw new ExcessaoBd(Mensagens.BD_ERRO_INSERT);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(st.toString());
			throw new ExcessaoBd(Mensagens.BD_ERRO_INSERT);
		} finally {
			DBConnection.closeStatement(st);
		}
	}

	@Override
	public void update(Cliente obj) throws ExcessaoBd {

		PreparedStatement st = null;

		try {
			st = conexao.prepareStatement(UPDATE, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNomeSobrenome());
			st.setString(2, obj.getCpf());
			st.setString(3, obj.getCnpj());
			st.setString(4, obj.getObservacao());
			st.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			st.setString(6, obj.getTipoPessoa().toString());
			st.setString(7, obj.getTipoCliente().toString());
			st.setString(8, obj.getSituacao().toString());
			st.setLong(9, obj.getId());

			st.executeUpdate();

			updateContatos(obj.getId(), obj.getContatos());
			updateEnderecos(obj.getId(), obj.getEnderecos());

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(st.toString());
			throw new ExcessaoBd(Mensagens.BD_ERRO_UPDATE);
		} finally {
			DBConnection.closeStatement(st);
		}
	}

	@Override
	public void delete(Long id) throws ExcessaoBd {

		PreparedStatement st = null;

		try {
			st = conexao.prepareStatement(DELETE, Statement.RETURN_GENERATED_KEYS);

			st.setLong(1, id);

			int rowsAffected = st.executeUpdate();

			if (rowsAffected < 1) {
				System.out.println("Erro ao deletar os dados.\n" + st.toString());
				throw new ExcessaoBd(Mensagens.BD_ERRO_DELETE);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(st.toString());
			throw new ExcessaoBd(Mensagens.BD_ERRO_DELETE);
		} finally {
			DBConnection.closeStatement(st);
		}
	}

	@Override
	public Cliente find(Long id) throws ExcessaoBd {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conexao.prepareStatement(SELECT);
			st.setLong(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Cliente obj = new Cliente(rs.getLong("Id"), rs.getString("NomeSobrenome"),
						rs.getTimestamp("DataCadastro"), rs.getTimestamp("DataUltimaAlteracao"), rs.getString("Cpf"),
						rs.getString("Cnpj"), rs.getString("Observacao"), TipoPessoa.valueOf(rs.getString("Tipo")),
						TipoCliente.valueOf(rs.getString("Enquadramento")), Situacao.valueOf(rs.getString("Situacao")));

				obj.setContatos(selectContato(rs.getLong("Id")));
				obj.setEnderecos(selectEndereco(rs.getLong("Id")));

				return obj;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExcessaoBd(Mensagens.BD_ERRO_SELECT);
		} finally {
			DBConnection.closeStatement(st);
			DBConnection.closeResultSet(rs);
		}
		return null;
	}

	@Override
	public List<Cliente> findAll() throws ExcessaoBd {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conexao.prepareStatement(SELECT_ALL);
			rs = st.executeQuery();

			List<Cliente> list = new ArrayList<>();

			while (rs.next()) {

				Cliente obj = new Cliente(rs.getLong("Id"), rs.getString("NomeSobrenome"),
						rs.getTimestamp("DataCadastro"), rs.getTimestamp("DataUltimaAlteracao"), rs.getString("Cpf"),
						rs.getString("Cnpj"), rs.getString("Observacao"), TipoPessoa.valueOf(rs.getString("Tipo")),
						TipoCliente.valueOf(rs.getString("Enquadramento")), Situacao.valueOf(rs.getString("Situacao")));

				obj.setContatos(selectContato(rs.getLong("Id")));
				obj.setEnderecos(selectEndereco(rs.getLong("Id")));

				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExcessaoBd(Mensagens.BD_ERRO_SELECT_ALL);
		} finally {
			DBConnection.closeStatement(st);
			DBConnection.closeResultSet(rs);
		}
	}

	private void updateEnderecos(Long id, Set<Endereco> lista) throws ExcessaoBd {
		if (lista == null || lista.size() == 0)
			return;

		PreparedStatement stEnd = null;
		try {
			for (Endereco ls : lista) {
				if (ls.getId() != null && ls.getId() != 0) {
					stEnd = conexao.prepareStatement(UPDATE_ENDERECO, Statement.RETURN_GENERATED_KEYS);

					if (ls.getBairro() == null || ls.getBairro().getId() == 0)
						stEnd.setNull(1, 0);
					else
						stEnd.setLong(1, ls.getBairro().getId());

					stEnd.setString(2, ls.getEndereco());
					stEnd.setString(3, ls.getNumero());
					stEnd.setString(4, ls.getCep());
					stEnd.setString(5, ls.getComplemento());
					stEnd.setString(6, ls.getObservacao());
					stEnd.setTimestamp(7, ls.getDataCadastro());
					stEnd.setString(8, ls.getTipoEndereco().toString());
					stEnd.setString(9, ls.getSituacao().toString());
					stEnd.setBoolean(10, ls.isPadrao());
					stEnd.setLong(11, id);
					stEnd.setLong(12, ls.getId());

					stEnd.executeUpdate();

				} else {
					stEnd = conexao.prepareStatement(INSERT_ENDERECO, Statement.RETURN_GENERATED_KEYS);

					stEnd.setLong(1, id);
					stEnd.setLong(2, id);

					if (ls.getBairro() == null || ls.getBairro().getId() == 0)
						stEnd.setNull(3, 0);
					else
						stEnd.setLong(3, ls.getBairro().getId());

					stEnd.setString(4, ls.getEndereco());
					stEnd.setString(5, ls.getNumero());
					stEnd.setString(6, ls.getCep());
					stEnd.setString(7, ls.getComplemento());
					stEnd.setString(8, ls.getObservacao());
					stEnd.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
					stEnd.setString(10, ls.getTipoEndereco().toString());
					stEnd.setString(11, ls.getSituacao().toString());
					stEnd.setBoolean(12, ls.isPadrao());

					int rowsAffected = stEnd.executeUpdate();

					if (rowsAffected < 1) {
						System.out.println("Erro ao salvar os endereços.\n" + stEnd.toString());
						throw new ExcessaoBd(Mensagens.BD_ERRO_SALVAR_ENDERECO);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(stEnd.toString());
			throw new ExcessaoBd(Mensagens.BD_ERRO_SALVAR_ENDERECO);
		} finally {
			DBConnection.closeStatement(stEnd);
		}
	}

	private Set<Endereco> selectEndereco(Long id) throws ExcessaoBd {
		PreparedStatement stEnd = null;
		ResultSet rsEnd = null;
		try {
			stEnd = conexao.prepareStatement(SELECT_ENDERECO);
			stEnd.setLong(1, id);
			rsEnd = stEnd.executeQuery();

			Set<Endereco> list = new HashSet<>();

			if (bairroService == null) {
				setBairroServices(new BairroServices());
			}

			while (rsEnd.next()) {

				Endereco endereco = new Endereco(rsEnd.getLong("IdSequencial"),
						bairroService.pesquisar(rsEnd.getLong("IdBairro")), rsEnd.getString("Endereco"),
						rsEnd.getString("Numero"), rsEnd.getString("CEP"), rsEnd.getString("Complemento"),
						rsEnd.getString("Observacao"), rsEnd.getTimestamp("DataCadastro"),
						TipoEndereco.valueOf(rsEnd.getString("Tipo")), Situacao.valueOf(rsEnd.getString("Situacao")),
						rsEnd.getBoolean("Padrao"));

				list.add(endereco);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExcessaoBd(Mensagens.BD_ERRO_CARREGAR_ENDERECO);
		} finally {
			DBConnection.closeStatement(stEnd);
			DBConnection.closeResultSet(rsEnd);
		}
	}

	private void updateContatos(Long id, Set<Contato> lista) throws ExcessaoBd {
		if (lista == null || lista.size() == 0)
			return;

		PreparedStatement stCont = null;
		try {
			for (Contato ls : lista) {
				if (ls.getId() != null && ls.getId() != 0) {
					stCont = conexao.prepareStatement(UPDATE_CONTATO, Statement.RETURN_GENERATED_KEYS);

					stCont.setString(1, ls.getNomeSobrenome());
					stCont.setString(2, ls.getTelefone());
					stCont.setString(3, ls.getCelular());
					stCont.setString(4, ls.getEmail());
					stCont.setString(5, ls.getObservacao());
					stCont.setTimestamp(6, ls.getDataCadastro());
					stCont.setString(7, ls.getTipoContato().toString());
					stCont.setString(8, ls.getSituacao().toString());
					stCont.setBoolean(9, ls.isPadrao());
					stCont.setLong(10, id);
					stCont.setLong(11, ls.getId());

					stCont.executeUpdate();

				} else {
					stCont = conexao.prepareStatement(INSERT_CONTATO, Statement.RETURN_GENERATED_KEYS);

					stCont.setLong(1, id);
					stCont.setLong(2, id);
					stCont.setString(3, ls.getNomeSobrenome());
					stCont.setString(4, ls.getTelefone());
					stCont.setString(5, ls.getCelular());
					stCont.setString(6, ls.getEmail());
					stCont.setString(7, ls.getObservacao());
					stCont.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
					stCont.setString(9, ls.getTipoContato().toString());
					stCont.setString(10, ls.getSituacao().toString());
					stCont.setBoolean(11, ls.isPadrao());

					int rowsAffected = stCont.executeUpdate();

					if (rowsAffected < 1) {
						System.out.println("Erro ao salvar os contatos.\n" + stCont.toString());
						throw new ExcessaoBd(Mensagens.BD_ERRO_SALVAR_CONTATO);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(stCont.toString());
			throw new ExcessaoBd(Mensagens.BD_ERRO_SALVAR_CONTATO);
		} finally {
			DBConnection.closeStatement(stCont);
		}
	}

	private Set<Contato> selectContato(Long id) throws ExcessaoBd {
		PreparedStatement stCont = null;
		ResultSet rsCont = null;
		try {
			stCont = conexao.prepareStatement(SELECT_CONTATO);
			stCont.setLong(1, id);
			rsCont = stCont.executeQuery();

			Set<Contato> list = new HashSet<>();

			while (rsCont.next()) {

				Contato endereco = new Contato(rsCont.getLong("IdSequencial"), rsCont.getString("Nome"),
						rsCont.getString("Telefone"), rsCont.getString("Celular"), rsCont.getString("Email"),
						rsCont.getString("Observacao"), rsCont.getTimestamp("DataCadastro"),
						TipoContato.valueOf(rsCont.getString("Tipo")), Situacao.valueOf(rsCont.getString("Situacao")),
						rsCont.getBoolean("Padrao"));

				list.add(endereco);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExcessaoBd(Mensagens.BD_ERRO_CARREGAR_CONTATO);
		} finally {
			DBConnection.closeStatement(stCont);
			DBConnection.closeResultSet(rsCont);
		}
	}
}