package comum.model.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import comum.model.config.ProcessaConfig;
import comum.model.entities.Conexao;

public class DB {

	private static Connection CONN = null;
	private static Conexao DADOS_CONEXAO;

	public static Connection getConnection() {
		if (CONN == null) {
			try {
				String driverName = "com.mysql.cj.jdbc.Driver";
				Class.forName(driverName);

				DADOS_CONEXAO = ProcessaConfig.getDadosConexao();
				Properties props = new Properties();
				props.setProperty("characterEncoding", DADOS_CONEXAO.getCharacterEncoding());
				props.setProperty("useUnicode", DADOS_CONEXAO.getUseUnicode() ? "True" : "False");
				props.setProperty("user", DADOS_CONEXAO.getUsuario());
				props.setProperty("password", DADOS_CONEXAO.getSenha());
				String url = "jdbc:mysql://" + DADOS_CONEXAO.getHost() + ":" + DADOS_CONEXAO.getPorta() + "/"
						+ DADOS_CONEXAO.getBase()
						+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
				CONN = DriverManager.getConnection(url, props);
			} catch (ClassNotFoundException e) { // Driver nao encontrado
				System.out.println("O driver de conexão expecificado nao foi encontrado.");
				e.printStackTrace();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return CONN;
	}

	public static void closeConnection() {
		if (CONN != null) {
			try {
				CONN.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
