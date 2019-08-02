package log;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SalvarLog {

	private static String getCaminho() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props.getProperty("log");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public void salvarLog(String tela, String tipo, String erro) {
		String caminho = getCaminho();

		if (caminho.isEmpty()) {
			caminho = "C:/";
		}
	}

}
