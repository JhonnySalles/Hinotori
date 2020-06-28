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
import comum.model.enums.TamanhoImagem;
import comum.model.enums.UsuarioNivel;
import comum.model.exceptions.ExcessaoBd;
import comum.model.messages.Mensagens;
import servidor.dao.UsuarioDao;
import servidor.entities.Imagem;
import servidor.entities.Usuario;
import servidor.util.DBConnection;

public class UsuarioDaoJDBC implements UsuarioDao {

	final String INSERT = "INSERT INTO usuarios (NomeSobrenome, Login,"
			+ " Senha, DataCadastro, DataUltimaAlteracao, Observacao, Nivel, Situacao) VALUES"
			+ " (?, ?, ?, ?, ?, ?, ?, ?)";

	final String UPDATE = "UPDATE usuarios SET NomeSobrenome = ?, Login = ?, Senha = ?, "
			+ " Observacao = ?, DataUltimaAlteracao = ?,  Nivel = ?, Situacao = ? WHERE id = ?";

	final String DELETE = "UPDATE Situacao = 'EXCLUIDO' WHERE id = ?";

	final String SELECT_ALL = "SELECT Id, NomeSobrenome, Login, DataCadastro, DataUltimaAlteracao, Observacao,"
			+ " Nivel, Situacao FROM usuarios WHERE Situacao <> 'EXCLUIDO' AND Id <> 0";

	final String SELECT = "SELECT Id, NomeSobrenome, Login, DataCadastro, DataUltimaAlteracao, Observacao, "
			+ " Nivel, Situacao FROM usuarios WHERE Id = ?";

	final String SELECT_LOGIN = "SELECT * FROM usuarios WHERE Login = ? AND SITUACAO = 'ATIVO' ";

	final String SELECT_LISTA_LOGIN = "SELECT Login FROM usuarios WHERE SITUACAO = 'ATIVO' ";

	final String SELECT_EXISTE_LOGIN = "SELECT * FROM usuarios WHERE Login = ? AND ID <> ?";

	final String INSERT_IMAGEM = "INSERT INTO usuarios_imagens (IdUsuario, IdSequencial, Nome, Extenssao, Imagem, Tamanho) "
			+ " VALUES (?,(SELECT IFNULL(MAX(img.IdSequencial),0)+1 FROM usuarios_imagens img WHERE img.IdUsuario = ?),?,?,?,?)";

	final String UPDATE_IMAGEM = "UPDATE usuarios_imagens SET Nome = ?, Extenssao = ?, Imagem = ?, "
			+ " Tamanho = ? WHERE IdUsuario = ? AND IdSequencial = ?";

	final String SELECT_IMAGEM = "SELECT IdSequencial, Nome, Extenssao, Imagem, "
			+ " Tamanho FROM usuarios_imagens WHERE IdUsuario = ?";

	final String DELETE_IMAGEM = "DELETE FROM usuarios_imagens WHERE IdUsuario = ? AND IdSequencial = ?";

	private Connection conexao;

	public UsuarioDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public Usuario insert(Usuario obj) throws ExcessaoBd {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNomeSobrenome());
			st.setString(2, obj.getLogin());
			st.setString(3, obj.getSenha());
			st.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			st.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			st.setString(6, obj.getObservacao());
			st.setString(7, obj.getNivel().toString());
			st.setString(8, obj.getSituacao().toString());

			int rowsAffected = st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();

			if (rs.next())
				obj.setId(rs.getLong(1));

			updateImagens(obj.getId(), obj.getImagens());

			if (rowsAffected < 1) {
				System.out.println("Erro ao salvar os dados.\n" + st.toString());
				throw new ExcessaoBd(Mensagens.BD_ERRO_INSERT);
			}

			return obj;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(st.toString());
			throw new ExcessaoBd(Mensagens.BD_ERRO_INSERT);
		} finally {
			DBConnection.closeStatement(st);
		}

	}

	@Override
	public Usuario update(Usuario obj) throws ExcessaoBd {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(UPDATE, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNomeSobrenome());
			st.setString(2, obj.getLogin());
			st.setString(3, obj.getSenha());
			st.setString(4, obj.getObservacao());
			st.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			st.setString(6, obj.getNivel().toString());
			st.setString(7, obj.getSituacao().toString());
			st.setLong(8, obj.getId());

			st.executeUpdate();
			updateImagens(obj.getId(), obj.getImagens());

			return obj;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(st.toString());
			throw new ExcessaoBd(Mensagens.BD_ERRO_UPDATE);
		} finally {
			DBConnection.closeStatement(st);
		}

	}

	@Override
	public Long delete(Long id) throws ExcessaoBd {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(DELETE, Statement.RETURN_GENERATED_KEYS);
			st.setLong(1, id);
			int rowsAffected = st.executeUpdate();

			if (rowsAffected < 1) {
				System.out.println("Erro ao salvar os dados.\n" + st.toString());
				throw new ExcessaoBd(Mensagens.BD_ERRO_DELETE);
			}

			return id;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(st.toString());
			throw new ExcessaoBd(Mensagens.BD_ERRO_DELETE);
		} finally {
			DBConnection.closeStatement(st);
		}
	}

	@Override
	public Usuario find(Long id, TamanhoImagem tamanho) throws ExcessaoBd {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conexao.prepareStatement(SELECT);
			st.setLong(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Usuario obj = new Usuario(rs.getLong("Id"), rs.getString("NomeSobrenome"),
						rs.getTimestamp("DataCadastro"), rs.getTimestamp("dataUltimaAlteracao"), rs.getString("Login"),
						rs.getString("Observacao"), UsuarioNivel.valueOf(rs.getString("Nivel")),
						Situacao.valueOf(rs.getString("Situacao")));
				obj.setImagens(selectImagens(rs.getLong("Id"), tamanho));
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
	public List<Usuario> findAll(TamanhoImagem tamanho) throws ExcessaoBd {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conexao.prepareStatement(SELECT_ALL);
			rs = st.executeQuery();

			List<Usuario> list = new ArrayList<>();

			while (rs.next()) {
				Usuario obj = new Usuario(rs.getLong("Id"), rs.getString("NomeSobrenome"),
						rs.getTimestamp("DataCadastro"), rs.getTimestamp("dataUltimaAlteracao"), rs.getString("Login"),
						rs.getString("Observacao"), UsuarioNivel.valueOf(rs.getString("Nivel")),
						Situacao.valueOf(rs.getString("Situacao")));
				obj.setImagens(selectImagens(rs.getLong("Id"), tamanho));
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

	private void updateImagens(Long id, Set<Imagem> lista) throws ExcessaoBd {
		if (lista == null || lista.size() == 0)
			return;

		PreparedStatement stImg = null;
		try {

			for (Imagem ls : lista) {
				if (ls.getExcluir()) {
					stImg = conexao.prepareStatement(DELETE, Statement.RETURN_GENERATED_KEYS);
					stImg.setLong(1, id);
					stImg.setLong(2, ls.getId());
					int rowsAffected = stImg.executeUpdate();

					if (rowsAffected < 1) {
						System.out.println("Erro ao excluir a imagem.\n" + stImg.toString());
						throw new ExcessaoBd(Mensagens.BD_ERRO_APAGAR_IMAGEM);
					}
				} else {
					if (ls.getId() != null && ls.getId() != 0) {
						stImg = conexao.prepareStatement(UPDATE_IMAGEM, Statement.RETURN_GENERATED_KEYS);

						stImg.setString(1, ls.getNome());
						stImg.setString(2, ls.getExtenssao());
						stImg.setBytes(3, ls.getImagem());

						switch ((TamanhoImagem) ls.getTamanho()) {
						case ORIGINAL:
							stImg.setString(4, "ORIGINAL");
							break;
						case MEDIA:
							stImg.setString(4, "MEDIA");
							break;
						case PEQUENA:
							stImg.setString(4, "PEQUENA");
							break;
						default:
							stImg.setString(4, "");
						}

						stImg.setLong(5, id);
						stImg.setLong(6, ls.getId());

						stImg.executeUpdate();

					} else {
						stImg = conexao.prepareStatement(INSERT_IMAGEM, Statement.RETURN_GENERATED_KEYS);
						stImg.setLong(1, id);
						stImg.setLong(2, id);
						stImg.setString(3, ls.getNome());
						stImg.setString(4, ls.getExtenssao());
						stImg.setBytes(5, ls.getImagem());

						switch ((TamanhoImagem) ls.getTamanho()) {
						case ORIGINAL:
							stImg.setString(6, "ORIGINAL");
							break;
						case MEDIA:
							stImg.setString(6, "MEDIA");
							break;
						case PEQUENA:
							stImg.setString(6, "PEQUENA");
							break;
						default:
							stImg.setString(6, "");
						}

						int rowsAffected = stImg.executeUpdate();

						if (rowsAffected < 1) {
							System.out.println("Erro ao salvar os imagem.\n" + stImg.toString());
							throw new ExcessaoBd(Mensagens.BD_ERRO_SALVAR_IMAGEM);
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(stImg.toString());
			throw new ExcessaoBd(Mensagens.BD_ERRO_SALVAR_IMAGEM);
		} finally {
			DBConnection.closeStatement(stImg);
		}
	}

	private Set<Imagem> selectImagens(Long id, TamanhoImagem tamanho) throws ExcessaoBd {
		if (tamanho == null || tamanho == TamanhoImagem.NENHUMA)
			return null;

		PreparedStatement stImg = null;
		ResultSet rsImg = null;
		try {
			String select = SELECT_IMAGEM;

			if (tamanho == TamanhoImagem.ORIGINAL || tamanho == TamanhoImagem.MEDIA
					|| tamanho == TamanhoImagem.PEQUENA) {
				switch ((TamanhoImagem) tamanho) {
				case ORIGINAL:
					select += " AND Tamanho = 'ORIGINAL'";
					break;
				case MEDIA:
					select += " AND Tamanho = 'MEDIA'";
					break;
				case PEQUENA:
					select += " AND Tamanho = 'PEQUENA'";
					break;
				default:
					select += " AND Tamanho = 'ORIGINAL'";
				}
			}

			stImg = conexao.prepareStatement(select);
			stImg.setLong(1, id);
			rsImg = stImg.executeQuery();

			Set<Imagem> list = new HashSet<>();

			while (rsImg.next()) {
				Imagem obj = new Imagem(rsImg.getLong("IdSequencial"), rsImg.getString("Nome"),
						rsImg.getString("Extenssao"), rsImg.getBytes("Imagem"),
						TamanhoImagem.valueOf(rsImg.getString("Tamanho")));

				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExcessaoBd(Mensagens.BD_ERRO_CARREGAR_IMAGEM);
		} finally {
			DBConnection.closeStatement(stImg);
			DBConnection.closeResultSet(rsImg);
		}
	}

	@Override
	public Boolean validaLogin(Long id, String login) throws ExcessaoBd {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conexao.prepareStatement(SELECT_EXISTE_LOGIN);
			st.setString(1, login);
			st.setLong(1, id);
			rs = st.executeQuery();
			if (rs.next())
				return true;
			else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExcessaoBd(Mensagens.BD_ERRO_USR_VALIDAR_LOGIN);
		} finally {
			DBConnection.closeStatement(st);
			DBConnection.closeResultSet(rs);
		}
	}

	@Override
	public Usuario find(String login) throws ExcessaoBd {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conexao.prepareStatement(SELECT_LOGIN);
			st.setString(1, login);
			rs = st.executeQuery();

			if (rs.next())
				return new Usuario(rs.getLong("Id"), rs.getString("NomeSobrenome"), rs.getTimestamp("DataCadastro"),
						rs.getTimestamp("dataUltimaAlteracao"), rs.getString("Login"), rs.getString("Observacao"),
						UsuarioNivel.valueOf(rs.getString("Nivel")), Situacao.valueOf(rs.getString("Situacao")));

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
	public List<String> findLogins() throws ExcessaoBd {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conexao.prepareStatement(SELECT_LISTA_LOGIN);
			rs = st.executeQuery();

			List<String> list = new ArrayList<>();

			while (rs.next())
				list.add(rs.getString("Login"));

			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExcessaoBd(Mensagens.BD_ERRO_SELECT_ALL);
		} finally {
			DBConnection.closeStatement(st);
			DBConnection.closeResultSet(rs);
		}
	}
}
