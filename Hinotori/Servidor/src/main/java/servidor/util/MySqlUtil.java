package servidor.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import comum.model.config.ProcessaConfig;
import comum.model.entities.Configuracao;
import javafx.concurrent.Task;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import servidor.animation.BDAnimacao;

public class MySqlUtil {

	public final static Image BANCO = new Image(
			MySqlUtil.class.getResourceAsStream("/servidor/resources/imagens/icoDataBase_48.png"));
	public final static Image CONECTANDO = new Image(
			MySqlUtil.class.getResourceAsStream("/servidor/resources/imagens/icoDataEspera_48.png"));
	public final static Image SEM_CONEXAO = new Image(
			MySqlUtil.class.getResourceAsStream("/servidor/resources/imagens/icoDataSemConexao_48.png"));
	public final static Image COM_CONEXAO = new Image(
			MySqlUtil.class.getResourceAsStream("/servidor/resources/imagens/icoDataConectado_48.png"));

	final public static double IMG_BANCO_WIDTH = 30;
	final public static double IMG_BANCO_HEIGHT = 30;

	public static Configuracao DADOS_CONEXAO;

	/**
	 * <p>
	 * Método testar a conexão, método pode travar até que consiga testar.
	 * </p>
	 * 
	 * @return Retorna uma <b>String</b> contendo o nome do host ou vazio, caso não
	 *         consiga conectar.
	 * @author Jhonny de Salles Noschang
	 */
	public static String testaConexaoMySQL() {
		DADOS_CONEXAO = ProcessaConfig.getDadosConexao();
		Connection connection = null;
		String conecta = "";
		try {

			String driverName = "com.mysql.cj.jdbc.Driver";
			Class.forName(driverName);

			String url = "jdbc:mysql://" + DADOS_CONEXAO.getServer_host() + ":" + DADOS_CONEXAO.getServer_porta() + "/"
					+ DADOS_CONEXAO.getServer_base() + "?useTimezone=true&serverTimezone=UTC";
			connection = DriverManager.getConnection(url, DADOS_CONEXAO.getServer_usuario(),
					DADOS_CONEXAO.getServer_senha());

			if (connection != null) {
				conecta = DADOS_CONEXAO.getServer_host();
			}

			connection.close();

		} catch (ClassNotFoundException e) { // Driver n�o encontrado
			System.out.println("O driver de conexão expecificado nao foi encontrado.");
			e.printStackTrace();

		} catch (SQLException e) {
			System.out.println("Nao foi possivel conectar ao Banco de Dados.");
			e.printStackTrace();

		}
		return conecta;
	}

	/**
	 * <p>
	 * Método utilizado para fazer o teste de conexão e animar uma imagem, tooltip
	 * opcional.
	 * </p>
	 * <p>
	 * A função aplica método de task para que seja executado sem travar a
	 * aplicação.
	 * </p>
	 * 
	 * @param imgBd  referência da imagem utilizada para a animação.
	 * @param tootBd referência ao tooltip da imagem ou botão que irá informar o
	 *               banco ao conecatar e uma mensagem de erro caso não consiga.
	 *               Mensagem: "Não foi possível conectar na base, verifique a
	 *               configuração ou clique aqui para realizar nova tentativa."
	 * @return Retorna a <b>Classe Conexao</b>
	 * @author Jhonny de Salles Noschang
	 */
	public static Configuracao testaConexaoMySQL(ImageView imgBd, Tooltip tootBd) {
		DADOS_CONEXAO = ProcessaConfig.getDadosConexao();
		BDAnimacao.inicia(imgBd);
		// Criacao da thread para que esteja validando a conexao e nao trave a tela.
		Task<Boolean> verificaConexao = new Task<Boolean>() {
			@Override
			protected Boolean call() throws Exception {
				DADOS_CONEXAO = ProcessaConfig.getDadosConexao();
				Connection connection = null;
				Boolean conecta = false;
				try {

					String driverName = "com.mysql.cj.jdbc.Driver";
					Class.forName(driverName);

					String url = "jdbc:mysql://" + DADOS_CONEXAO.getServer_host() + ":"
							+ DADOS_CONEXAO.getServer_porta() + "/" + DADOS_CONEXAO.getServer_base()
							+ "?useTimezone=true&serverTimezone=UTC";
					connection = DriverManager.getConnection(url, DADOS_CONEXAO.getServer_usuario(),
							DADOS_CONEXAO.getServer_senha());

					if (connection != null) {
						conecta = true;
					}

					connection.close();
				} catch (ClassNotFoundException e) { // Driver n�o encontrado
					System.out.println("O driver de conexão expecificado nao foi encontrado.");
					e.printStackTrace();
				} catch (SQLException e) {
					System.out.println("Nao foi possivel conectar ao Banco de Dados.");
					e.printStackTrace();
				}

				return conecta;
			}

			@Override
			protected void succeeded() {
				BDAnimacao.TIMELINE.stop();
				Boolean conectado = getValue();

				if (conectado) {
					imgBd.setImage(COM_CONEXAO);

					if (tootBd != null)
						tootBd.setText(DADOS_CONEXAO.getServer_base());
					imgBd.setFitWidth(IMG_BANCO_WIDTH);
					imgBd.setFitHeight(IMG_BANCO_HEIGHT);
				} else {
					imgBd.setImage(SEM_CONEXAO);
					if (tootBd != null)
						tootBd.setText(
								"Não foi possível conectar na base, verifique a configuração ou clique aqui para realizar nova tentativa.");
					imgBd.setFitWidth(IMG_BANCO_WIDTH);
					imgBd.setFitHeight(IMG_BANCO_HEIGHT);
				}
			}
		};
		Thread t = new Thread(verificaConexao);
		t.setDaemon(true);
		t.start();
		return DADOS_CONEXAO;
	}

	final static String SELECT_USER = "SELECT Login FROM usuarios WHERE Situacao <> 'EXCLUIDO' AND id <> 0;";

	/**
	 * <p>
	 * Função para listar e retornar todos os usuarios.
	 * </p>
	 * 
	 * @return Retorna uma <b>Lista de String</b> contendo todos os usuários
	 *         cadastrados.
	 * @author Jhonny de Salles Noschang
	 */
	public static List<String> getDBUsuarios() {
		Connection conexao = DBConnection.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conexao.prepareStatement(SELECT_USER);
			rs = st.executeQuery();
			List<String> lista = new ArrayList<>();

			while (rs.next()) {
				lista.add(rs.getString("Login"));
			}

			return lista;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(st);
			DBConnection.closeResultSet(rs);
		}
		return null;
	}

	final static String SELECT_PSW = "SELECT senha FROM usuarios WHERE Login = ?;";

	public static String getDBPassword(String user) {
		Connection conexao = DBConnection.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conexao.prepareStatement(SELECT_PSW);
			st.setString(1, user);
			rs = st.executeQuery();

			if (rs.next()) {
				return rs.getString("senha");
			}

			return "";
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeStatement(st);
			DBConnection.closeResultSet(rs);
		}
		return "";
	}
}
