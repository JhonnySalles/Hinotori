package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import model.config.CarregaConfig;

public class ConexaoMysql {

	private static String server = "";
	private static String port = "";
	private static String dataBase = "";
	private static String user = "";
	private static String psswd = "";

	public static void getDadosConexao() {
		Properties props = CarregaConfig.carregaConfig();
		server = props.getProperty("server");
		port = props.getProperty("port");
		dataBase = props.getProperty("dataBase");
		user = props.getProperty("user");
		psswd = props.getProperty("password");
	}

	public static String testaConexaoMySQL() {
		getDadosConexao();
		Connection connection = null;
		String conecta = "";
		try {

			String driverName = "com.mysql.cj.jdbc.Driver";
			Class.forName(driverName);

			String url = "jdbc:mysql://" + server + ":" + port + "/" + dataBase
					+ "?useTimezone=true&serverTimezone=UTC";
			connection = DriverManager.getConnection(url, user, psswd);

			if (connection != null) {
				conecta = dataBase;
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

}
