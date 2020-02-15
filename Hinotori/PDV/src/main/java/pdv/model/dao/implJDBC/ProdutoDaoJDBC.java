package pdv.model.dao.implJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import comum.model.enums.Padrao;
import comum.model.enums.Situacao;
import comum.model.enums.TamanhoImagem;
import comum.model.enums.TipoProduto;
import comum.model.exceptions.ExcessaoBd;
import comum.model.messages.Mensagens;
import comum.model.mysql.DB;
import pdv.model.dao.ProdutoDao;
import pdv.model.entities.Imagem;
import pdv.model.entities.Produto;

public class ProdutoDaoJDBC implements ProdutoDao {

	final String INSERT = "INSERT INTO produtos (Descricao, Observacao, CodigoBarras, Unidade,"
			+ " DataCadastro, Tipo, PesoBruto, PesoLiquido, Situacao) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

	final String UPDATE = "UPDATE produtos SET Descricao = ?, Observacao = ?, CodigoBarras = ?, Unidade = ?,"
			+ " Tipo = ?, PesoBruto = ?, PesoLiquido = ?, Situacao = ? WHERE ID = ?;";

	final String DELETE = "DELETE FROM produtos WHERE ID = ?;";

	final String SELECT_ALL = "SELECT ID, Descricao, Observacao, CodigoBarras, Unidade, DataCadastro,"
			+ " Tipo, PesoBruto, PesoLiquido, Situacao FROM produtos;";

	final String SELECT = "SELECT ID, Descricao, Observacao, CodigoBarras, Unidade, DataCadastro,"
			+ " Tipo, PesoBruto, PesoLiquido, Situacao FROM produtos WHERE ID = ?;";

	final String INSERT_IMAGEM = "INSERT INTO produtos_imagens (IdProduto, IdSequencial, Nome, Extenssao, Imagem, Tamanho, Padrao) "
			+ " VALUES (?,(SELECT IFNULL(MAX(img.IdSequencial),0)+1 FROM produtos_imagens img WHERE img.IdProduto = ?),?,?,?,?,?)";
	
	final String UPDATE_IMAGEM = "UPDATE produtos_imagens SET Nome = ?, Extenssao = ?, Imagem = ?, "
			+ " Tamanho = ?, Padrao = ? WHERE IdProduto = ? AND IdSequencial = ?;";
	
	final String SELECT_IMAGEM = "SELECT IdSequencial, Nome, Extenssao, Imagem, "
			+ " Tamanho, Padrao FROM produtos_imagens WHERE IdProduto = ?;";
	
	private Connection conexao;

	public ProdutoDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void insert(Produto obj) throws ExcessaoBd {
		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getDescricao());
			st.setString(2, obj.getObservacao());
			st.setString(3, obj.getCodigoBarras());
			st.setString(4, obj.getUnidade());
			st.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			
			switch ((TipoProduto) obj.getTipo()) {
			case PRODUTOFINAL:
				st.setString(6, "PRODUTOFINAL");
				break;
			case PRODUZIDO:
				st.setString(6, "PRODUZIDO");
				break;
			case MATERIAPRIMA:
				st.setString(6, "MATERIAPRIMA");
				break;
			case SERVICO:
				st.setString(6, "SERVICO");
				break;
			default:
				st.setString(6, "");
			}
			
			st.setDouble(7, obj.getPesoBruto());
			st.setDouble(8, obj.getPesoLiquido());
			st.setString(9, obj.getSituacao().toString());

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

			st.setString(1, obj.getDescricao());
			st.setString(2, obj.getObservacao());
			st.setString(3, obj.getCodigoBarras());
			st.setString(4, obj.getUnidade());
			
			switch ((TipoProduto) obj.getTipo()) {
			case PRODUTOFINAL:
				st.setString(5, "PRODUTOFINAL");
				break;
			case PRODUZIDO:
				st.setString(5, "PRODUZIDO");
				break;
			case MATERIAPRIMA:
				st.setString(5, "MATERIAPRIMA");
				break;
			case SERVICO:
				st.setString(5, "SERVICO");
				break;
			default:
				st.setString(5, "");
			}
			
			st.setDouble(6, obj.getPesoBruto());
			st.setDouble(7, obj.getPesoLiquido());
			st.setString(8, obj.getSituacao().toString());
			st.setLong(9, obj.getId());

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
				Produto obj = new Produto(rs.getLong("Id"), rs.getString("Descricao"), rs.getString("Observacao"),
						rs.getString("CodigoBarras"), rs.getString("Unidade"), rs.getDate("DataCadastro"),
						rs.getDouble("PesoBruto"), rs.getDouble("PesoLiquido"),
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
				Produto obj = new Produto(rs.getLong("Id"), rs.getString("Descricao"), rs.getString("Observacao"),
						rs.getString("CodigoBarras"), rs.getString("Unidade"), rs.getDate("DataCadastro"),
						rs.getDouble("PesoBruto"), rs.getDouble("PesoLiquido"),
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

}
