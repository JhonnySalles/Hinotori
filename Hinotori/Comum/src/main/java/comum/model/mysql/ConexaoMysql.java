package comum.model.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import comum.model.animation.BDAnimacao;
import comum.model.config.ProcessaConfig;
import comum.model.entities.Conexao;
import javafx.concurrent.Task;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ConexaoMysql {

	public final static Image BANCO = new Image(
			BDAnimacao.class.getResourceAsStream("/comum/resources/imagens/bd/icoDataBase_48.png"));
	public final static Image CONECTANDO = new Image(
			BDAnimacao.class.getResourceAsStream("/comum/resources/imagens/bd/icoDataEspera_48.png"));
	public final static Image SEM_CONEXAO = new Image(
			BDAnimacao.class.getResourceAsStream("/comum/resources/imagens/bd/icoDataSemConexao_48.png"));
	public final static Image COM_CONEXAO = new Image(
			BDAnimacao.class.getResourceAsStream("/comum/resources/imagens/bd/icoDataConectado_48.png"));

	final public static double IMG_BANCO_WIDTH = 30;
	final public static double IMG_BANCO_HEIGHT = 30;

	public static Conexao DADOS_CONEXAO;

	/**
	 * <p>Método testar a conexão, método pode travar até que consiga testar.</p>
	 * 
	 * @return Retorna uma <b>String</b> contendo o nome do host ou vazio, caso não consiga conectar.
	 * @author Jhonny de Salles Noschang
	 */
	public static String testaConexaoMySQL() {
		DADOS_CONEXAO = ProcessaConfig.getDadosConexao();
		Connection connection = null;
		String conecta = "";
		try {

			String driverName = "com.mysql.cj.jdbc.Driver";
			Class.forName(driverName);

			String url = "jdbc:mysql://" + DADOS_CONEXAO.getHost() + ":" + DADOS_CONEXAO.getPorta() + "/"
					+ DADOS_CONEXAO.getBase() + "?useTimezone=true&serverTimezone=UTC";
			connection = DriverManager.getConnection(url, DADOS_CONEXAO.getUsuario(), DADOS_CONEXAO.getSenha());

			if (connection != null) {
				conecta = DADOS_CONEXAO.getHost();
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
	 * <p>Método utilizado para fazer o teste de conexão e animar uma imagem, tooltip opcional.</p>
	 * <p>A função aplica método de task para que seja executado sem travar a aplicação.</p>
	 * 
	 * @param imgBd    referência da imagem utilizada para a animação.
	 * @param tootBd   referência ao tooltip da imagem ou botão que irá informar o banco ao conecatar e uma mensagem de erro caso não consiga.
	 * Mensagem: "Não foi possível conectar na base, verifique a configuração ou clique aqui para realizar nova tentativa."
	 * @return Retorna a <b>Classe Conexao</b>
	 * @author Jhonny de Salles Noschang
	 */
	public static Conexao testaConexaoMySQL(ImageView imgBd, Tooltip tootBd) {
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

					String url = "jdbc:mysql://" + DADOS_CONEXAO.getHost() + ":" + DADOS_CONEXAO.getPorta() + "/"
							+ DADOS_CONEXAO.getBase() + "?useTimezone=true&serverTimezone=UTC";
					connection = DriverManager.getConnection(url, DADOS_CONEXAO.getUsuario(), DADOS_CONEXAO.getSenha());

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
						tootBd.setText(DADOS_CONEXAO.getBase());
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

}
