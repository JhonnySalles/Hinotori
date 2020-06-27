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
import comum.model.enums.TipoProduto;
import comum.model.exceptions.ExcessaoBd;
import comum.model.messages.Mensagens;
import comum.model.mysql.DB;
import servidor.dao.ProdutoDao;
import servidor.entities.Imagem;
import servidor.entities.Produto;

public class ProdutoDaoJDBC implements ProdutoDao {

	final String INSERT = "INSERT INTO produtos (IdNcm, IdGrupo, Descricao, Observacao, CodigoBarras, Unidade,"
			+ " Marca, Peso, Volume, DataCadastro, DataUltimaAlteracao, Tipo, Situacao) VALUES "
			+ " (?, ?, ?,  ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	final String UPDATE = "UPDATE produtos SET IdNcm = ?, IdGrupo = ?, Descricao = ?, Observacao = ?, CodigoBarras = ?, "
			+ " Unidade = ?, Marca = ?, Peso = ?, Volume = ?, DataUltimaAlteracao = ?, Tipo = ?, Situacao = ? WHERE ID = ?";

	final String DELETE = "DELETE FROM produtos WHERE ID = ?";

	final String SELECT = "SELECT Id, IdNcm, IdGrupo, Descricao, Observacao, CodigoBarras, Unidade, Marca, Peso, Volume, "
			+ " DataCadastro, DataUltimaAlteracao, Tipo, Situacao FROM produtos WHERE ID = ?";

	final String SELECT_ALL = "SELECT Id, IdNcm, IdGrupo, Descricao, Observacao, CodigoBarras, Unidade, Marca, Peso, Volume, "
			+ " qtdVolume, DataCadastro, DataUltimaAlteracao, Tipo, Situacao FROM produtos";

	final String INSERT_IMAGEM = "INSERT INTO produtos_imagens (IdProduto, IdSequencial, Nome, Extenssao, Imagem, Tamanho) "
			+ " VALUES (?,(SELECT IFNULL(MAX(img.IdSequencial),0)+1 FROM produtos_imagens img WHERE img.IdProduto = ?),?,?,?,?)";

	final String UPDATE_IMAGEM = "UPDATE produtos_imagens SET Nome = ?, Extenssao = ?, Imagem = ?, "
			+ " Tamanho = ? WHERE IdProduto = ? AND IdSequencial = ?";

	final String SELECT_IMAGEM = "SELECT IdSequencial, Nome, Extenssao, Imagem, "
			+ " Tamanho FROM produtos_imagens WHERE IdProduto = ?";

	final String DELETE_IMAGEM = "DELETE FROM produtos_imagens WHERE IdUsuario = ? AND IdSequencial = ?";

	private Connection conexao;

	public ProdutoDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void insert(Produto obj) throws ExcessaoBd {
		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

			if (obj.getNcm() != null)
				st.setString(1, obj.getNcm().getNcm());
			else
				st.setString(1, "");

			st.setLong(2, obj.getIdGrupo());
			st.setString(3, obj.getDescricao());
			st.setString(4, obj.getObservacao());
			st.setString(5, obj.getCodigoBarras());
			st.setString(6, obj.getUnidade());
			st.setString(7, obj.getMarca());
			st.setDouble(8, obj.getPeso());
			st.setDouble(9, obj.getVolume());
			st.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
			st.setTimestamp(11, new Timestamp(System.currentTimeMillis()));

			switch ((TipoProduto) obj.getTipoProduto()) {
			case PRODUTOFINAL:
				st.setString(12, "PRODUTOFINAL");
				break;
			case PRODUZIDO:
				st.setString(12, "PRODUZIDO");
				break;
			case MATERIAPRIMA:
				st.setString(12, "MATERIAPRIMA");
				break;
			case SERVICO:
				st.setString(12, "SERVICO");
				break;
			default:
				st.setString(12, "");
			}

			st.setString(13, obj.getSituacao().toString());

			int rowsAffected = st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();

			if (rs.next()) {
				obj.setId(rs.getLong(1));
			}

			updateImagens(obj.getId(), obj.getImagens());

			if (rowsAffected < 1) {
				System.out.println("Erro ao salvar os dados. \n" + st.toString());
				throw new ExcessaoBd(Mensagens.BD_ERRO_INSERT);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(st.toString());
			throw new ExcessaoBd(Mensagens.BD_ERRO_INSERT);
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(Produto obj) throws ExcessaoBd {
		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(UPDATE, Statement.RETURN_GENERATED_KEYS);

			if (obj.getNcm() != null)
				st.setString(1, obj.getNcm().getNcm());
			else
				st.setString(1, "");

			st.setLong(2, obj.getIdGrupo());
			st.setString(3, obj.getDescricao());
			st.setString(4, obj.getObservacao());
			st.setString(5, obj.getCodigoBarras());
			st.setString(6, obj.getUnidade());
			st.setString(7, obj.getMarca());
			st.setDouble(8, obj.getPeso());
			st.setDouble(9, obj.getVolume());
			st.setTimestamp(10, new Timestamp(System.currentTimeMillis()));

			switch ((TipoProduto) obj.getTipoProduto()) {
			case PRODUTOFINAL:
				st.setString(11, "PRODUTOFINAL");
				break;
			case PRODUZIDO:
				st.setString(11, "PRODUZIDO");
				break;
			case MATERIAPRIMA:
				st.setString(11, "MATERIAPRIMA");
				break;
			case SERVICO:
				st.setString(11, "SERVICO");
				break;
			default:
				st.setString(11, "");
			}

			st.setString(12, obj.getSituacao().toString());
			st.setLong(13, obj.getId());

			st.executeUpdate();

			updateImagens(obj.getId(), obj.getImagens());

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(st.toString());
			throw new ExcessaoBd(Mensagens.BD_ERRO_UPDATE);
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
				System.out.println("Erro ao salvar os dados. \n" + st.toString());
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
	public Produto find(Long id, TamanhoImagem tamanho) throws ExcessaoBd {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conexao.prepareStatement(SELECT);
			st.setLong(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Produto obj = new Produto(rs.getLong("Id"), rs.getLong("IdGrupo"), rs.getString("Descricao"),
						rs.getString("Observacao"), rs.getString("CodigoBarras"), rs.getString("Unidade"),
						rs.getString("Marca"), rs.getDouble("Peso"), rs.getDouble("Volume"),
						rs.getTimestamp("DataCadastro"), rs.getTimestamp("DataUltimaAlteracao"),
						TipoProduto.valueOf(rs.getString("Tipo")), Situacao.valueOf(rs.getString("Situacao")));
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
	public List<Produto> findAll(TamanhoImagem tamanho) throws ExcessaoBd {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conexao.prepareStatement(SELECT_ALL);
			rs = st.executeQuery();

			List<Produto> list = new ArrayList<>();

			while (rs.next()) {
				Produto obj = new Produto(rs.getLong("Id"), rs.getLong("IdGrupo"), rs.getString("Descricao"),
						rs.getString("Observacao"), rs.getString("CodigoBarras"), rs.getString("Unidade"),
						rs.getString("Marca"), rs.getDouble("Peso"), rs.getDouble("Volume"),
						rs.getTimestamp("DataCadastro"), rs.getTimestamp("DataUltimaAlteracao"),
						TipoProduto.valueOf(rs.getString("Tipo")), Situacao.valueOf(rs.getString("Situacao")));
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
			DB.closeStatement(stImg);
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

			Set<Imagem> list = new HashSet<Imagem>();

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
			DB.closeStatement(stImg);
			DB.closeResultSet(rsImg);
		}
	}

}
