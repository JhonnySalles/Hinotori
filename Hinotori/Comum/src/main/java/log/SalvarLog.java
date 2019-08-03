package log;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import arquivo.Arquivos;

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

	public static void salvar(Class<?> tela, String tipo, String erro) {
		String caminho = getCaminho();

		if (caminho.isEmpty()) {
			caminho = "C:/LogErro.txt";
		}

		String texto = tela.getName().toString() + " | " + tipo + " | " + erro;

		try {
			Arquivos.escritor(caminho, texto);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
