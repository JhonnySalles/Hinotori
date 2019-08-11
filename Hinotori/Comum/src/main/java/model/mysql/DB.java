package model.mysql;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import model.encode.Encryption;

public class DB {

	private static Connection conn = null;

	public static Connection getConnection() {
		if (conn == null) {
			try {
				String driverName = "com.mysql.cj.jdbc.Driver";
				Class.forName(driverName);
				
				Properties props = loadProperties();
				props.setProperty("characterEncoding", "UTF-8");
				props.setProperty("useUnicode", "true");
				props.setProperty("user", props.getProperty("prop.server.login"));
				props.setProperty("password", Encryption.decodifica(props.getProperty("prop.server.password")));
				String url = "jdbc:mysql://" + props.getProperty("prop.server.host") + ":" + props.getProperty("prop.server.port") + "/"
						+ props.getProperty("prop.server.base")
						+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
				conn = DriverManager.getConnection(url, props);
			} catch (ClassNotFoundException e) { // Driver n�o encontrado
				System.out.println("O driver de conexão expecificado nao foi encontrado.");
				e.printStackTrace();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}

	public static void closeConnection() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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
