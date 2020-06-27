package servidor.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import comum.model.config.ProcessaConfig;
import comum.model.entities.Configuracao;

public class DBConnection {
	
	private static Connection CONN = null;
	private static Configuracao DADOS_CONEXAO;

	public static Connection getConnection() {
		if (CONN == null) {
			try {
				String driverName = "com.mysql.cj.jdbc.Driver";
				Class.forName(driverName);

				DADOS_CONEXAO = ProcessaConfig.getDadosConexao();
				Properties props = new Properties();
				props.setProperty("characterEncoding", DADOS_CONEXAO.getUnicode_encode());
				props.setProperty("useUnicode", DADOS_CONEXAO.getUnicode_usar() ? "True" : "False");
				props.setProperty("user", DADOS_CONEXAO.getServer_usuario());
				props.setProperty("password", DADOS_CONEXAO.getServer_senha());
				String url = "jdbc:mysql://" + DADOS_CONEXAO.getServer_host() + ":" + DADOS_CONEXAO.getServer_porta()
						+ "/" + DADOS_CONEXAO.getServer_base()
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
