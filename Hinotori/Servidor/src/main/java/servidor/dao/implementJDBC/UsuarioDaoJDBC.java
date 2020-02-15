package servidor.dao.implementJDBC;

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

import comum.model.encode.DecodeHash;
import comum.model.enums.Padrao;
import comum.model.enums.Situacao;
import comum.model.enums.TamanhoImagem;
import comum.model.enums.UsuarioNivel;
import comum.model.exceptions.ExcessaoBd;
import comum.model.messages.Mensagens;
import comum.model.mysql.DB;
import servidor.dao.UsuarioDao;
import servidor.entities.Imagem;
import servidor.entities.Usuario;

public class UsuarioDaoJDBC implements UsuarioDao {

	final String INSERT = "INSERT INTO usuarios (NomeSobrenome, Login,"
			+ " Senha, DataCadastro, Observacao, Nivel, Situacao) VALUES" + " (?, ?, ?, ?, ?, ?, ?);";

	final String UPDATE = "UPDATE usuarios SET NomeSobrenome = ?, Login = ?, Senha = ?, "
			+ " Observacao = ?,  Nivel = ?, Situacao = ? WHERE id = ?;";

	final String DELETE = "UPDATE Situacao = 'EXCLUIDO' WHERE id = ?;";

	final String SELECT_ALL = "SELECT Id, NomeSobrenome, Login, DataCadastro, Observacao,"
			+ " Nivel, Situacao FROM usuarios WHERE Situacao <> 'EXCLUIDO';";

	final String SELECT = "SELECT Id, NomeSobrenome, Login, DataCadastro, Observacao, "
			+ " Nivel, Situacao FROM usuarios WHERE Id = ?;";

	final String SELECT_LOGIN = "SELECT * FROM usuarios WHERE Login = ?;";

	final String INSERT_IMAGEM = "INSERT INTO usuarios_imagens (IdUsuario, IdSequencial, Nome, Extenssao, Imagem, Tamanho, Padrao) "
			+ " VALUES (?,(SELECT IFNULL(MAX(img.IdSequencial),0)+1 FROM usuarios_imagens img WHERE img.IdUsuario = ?),?,?,?,?,?)";
	
	final String UPDATE_IMAGEM = "UPDATE usuarios_imagens SET Nome = ?, Extenssao = ?, Imagem = ?, "
			+ " Tamanho = ?, Padrao = ? WHERE IdUsuario = ? AND IdSequencial = ?;";
	
	final String SELECT_IMAGEM = "SELECT IdSequencial, Nome, Extenssao, Imagem, "
			+ " Tamanho, Padrao FROM usuarios_imagens WHERE IdUsuario = ?;";

	private Connection conexao;

	public UsuarioDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void insert(Usuario obj) throws ExcessaoBd {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNomeSobrenome());
			st.setString(2, obj.getLogin());
			st.setString(3, DecodeHash.DecodePassword(obj.getSenha()));
			st.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			st.setString(5, obj.getObservacao());
			st.setString(6, obj.getNivel().toString());
			st.setString(7, obj.getSituacao().toString());

			int rowsAffected = st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();

			if (rs.next()) {
				obj.setId(rs.getLong(1));
			}

			updateImagens(obj.getId(), obj.getImagens());

			if (rowsAffected < 1) {
				System.out.println("Erro ao salvar os dados.\n" + st.toString());
				throw new ExcessaoBd(Mensagens.BD_ERRO_INSERT);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(st.toString());
			throw new ExcessaoBd(Mensagens.BD_ERRO_INSERT);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new ExcessaoBd(Mensagens.USR_ERRO_SENHA);
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(Usuario obj) throws ExcessaoBd {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(UPDATE, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNomeSobrenome());
			st.setString(2, obj.getLogin());
			st.setString(3, DecodeHash.DecodePassword(obj.getSenha()));
			st.setString(4, obj.getObservacao());
			st.setString(5, obj.getNivel().toString());
			st.setString(6, obj.getSituacao().toString());
			st.setLong(7, obj.getId());

			st.executeUpdate();

			updateImagens(obj.getId(), obj.getImagens());

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(st.toString());
			throw new ExcessaoBd(Mensagens.BD_ERRO_UPDATE);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new ExcessaoBd(Mensagens.USR_ERRO_SENHA);
		} finally {
			DB.closeStatement(st);
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
				System.out.println("Erro ao salvar os dados.\n" + st.toString());
				throw new ExcessaoBd(Mensagens.BD_ERRO_DELETE);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(st.toString());
			throw new ExcessaoBd(Mensagens.BD_ERRO_DELETE);
		} finally {
			DB.closeStatement(st);
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
						rs.getTimestamp("DataCadastro"), rs.getString("Login"), rs.getString("Observacao"),
						UsuarioNivel.valueOf(rs.getString("Nivel")), Situacao.valueOf(rs.getString("Situacao")));
				obj.setImagens(selectImagens(rs.getLong("Id"), tamanho));
				return obj;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExcessaoBd(Mensagens.BD_ERRO_SELECT);
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
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
						rs.getTimestamp("DataCadastro"), rs.getString("Login"), rs.getString("Observacao"),
						UsuarioNivel.valueOf(rs.getString("Nivel")), Situacao.valueOf(rs.getString("Situacao")));
				obj.setImagens(selectImagens(rs.getLong("Id"), tamanho));
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExcessaoBd(Mensagens.BD_ERRO_SELECT_ALL);
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private void updateImagens(Long id, List<Imagem> lista) throws ExcessaoBd {
		if (lista == null || lista.size() == 0)
			return;

		PreparedStatement stImg = null;
		try {

			for (Imagem ls : lista) {
				if (ls.getIdSequencial() != null && ls.getIdSequencial() != 0) {
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

					stImg.setString(5, ls.getPadrao().toString());
					stImg.setLong(6, id);
					stImg.setLong(7, ls.getIdSequencial());

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

					stImg.setString(7, ls.getPadrao().toString());

					int rowsAffected = stImg.executeUpdate();

					if (rowsAffected < 1) {
						System.out.println("Erro ao salvar os imagem.\n" + stImg.toString());
						throw new ExcessaoBd(Mensagens.BD_ERRO_SALVAR_IMAGEM);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(stImg.toString());
			throw new ExcessaoBd(Mensagens.BD_ERRO_SALVAR_IMAGEM);
		} finally {
			DB.closeStatement(stImg);
		}
	}

	private List<Imagem> selectImagens(Long id, TamanhoImagem tamanho) throws ExcessaoBd {
		if (tamanho == null || tamanho == TamanhoImagem.NENHUMA)
			return null;

		PreparedStatement stImg = null;
		ResultSet rsImg = null;
		try {
			String select = SELECT_IMAGEM;

			if (tamanho == TamanhoImagem.ORIGINAL || tamanho == TamanhoImagem.MEDIA || tamanho == TamanhoImagem.PEQUENA)
				select += " AND Tamanho = \"" + tamanho.toString() + "\"";

			stImg = conexao.prepareStatement(select);
			stImg.setLong(1, id);
			rsImg = stImg.executeQuery();

			List<Imagem> list = new ArrayList<>();

			while (rsImg.next()) {
				Imagem obj = new Imagem(rsImg.getLong("IdSequencial"), rsImg.getString("Nome"),
						rsImg.getString("Extenssao"), rsImg.getBytes("Imagem"),
						Padrao.valueOf(rsImg.getString("Padrao")), TamanhoImagem.valueOf(rsImg.getString("Tamanho")));

				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExcessaoBd(Mensagens.BD_ERRO_CARREGAR_IMAGEM);
		} finally {
			DB.closeStatement(stImg);
			DB.closeResultSet(rsImg);
		}
	}

	@Override
	public Boolean validaLogin(String login) throws ExcessaoBd {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conexao.prepareStatement(SELECT_LOGIN);
			st.setString(1, login);
			rs = st.executeQuery();
			if (rs.next())
				return true;
			else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExcessaoBd(Mensagens.USR_ERRO_AO_VALIDAR_LOGIN);
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
}
