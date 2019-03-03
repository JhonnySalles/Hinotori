package model.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class CriaConfig {
	
	public void criaConfig(File arquivo) {
		Properties prop = new Properties();
		
		prop.setProperty("prop.server.database", "base");
		prop.setProperty("prop.server.host", "localhost");
		prop.setProperty("prop.server.port", "3306");
		prop.setProperty("prop.server.base", "mysql");
		prop.setProperty("prop.server.login", "admin");
		prop.setProperty("prop.server.password", "HmbiDggIHlM\\=");

		try {
			FileOutputStream arquivoOut = new FileOutputStream(arquivo);
			prop.store(arquivoOut, null);
		} catch (IOException e) {
			System.out.println("Não foi possivel salvar o arquivo de configuração em: " + arquivo.toString());
			e.printStackTrace();
		}		
	}

}
