package model.log;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import model.arquivo.Arquivos;

public class ManipulaLog {

	private static String getCaminho() {

		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props.getProperty("prop.caminho.log");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void salvar(Class<?> tela, String tipo, String sql, String erro) {
		String caminho = getCaminho();

		if ((caminho == null) || caminho.isEmpty()) {
			caminho = Arquivos.criaCaminhoPadraoLog();
		}

		String texto = tela.getName().toString() + " | " + tipo + "|" + sql + " | " + erro;

		try {
			Arquivos.escritor(caminho, texto);
		} catch (IOException e) {
			System.out.println("Não foi possivel salvar o Log.");
			e.printStackTrace();
		}
	}

}
