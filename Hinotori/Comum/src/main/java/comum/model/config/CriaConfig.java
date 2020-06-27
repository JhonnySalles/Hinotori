package comum.model.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CriaConfig {

	public void criaConfig(File arquivo) {
		Properties prop = new Properties();

		prop.setProperty("prop.server.host", "localhost");
		prop.setProperty("prop.server.port", "3306");
		prop.setProperty("prop.server.base", "servidorteste");
		prop.setProperty("prop.server.login", "admin");
		prop.setProperty("prop.server.password", "crg+SNfHXEMF7MkmtK4CdA==");
		prop.setProperty("prop.server.database", "MySQL");
		prop.setProperty("prop.unicode.usar", "1");
		prop.setProperty("prop.unicode.encode", "UTF-8");
		prop.setProperty("prop.log.caminho", "C:\\Log");
		prop.setProperty("prop.log.mostrar", "0");
		prop.setProperty("prop.hibernate.mostrar_sql", "1");
		prop.setProperty("prop.sistema.tipo", "HOMOLOGACAO");
		prop.setProperty("prop.sistema.tema", "WHITE");
		
		try {
			FileOutputStream arquivoOut = new FileOutputStream(arquivo);
			prop.store(arquivoOut, null);
		} catch (IOException e) {
			System.out.println("Não foi possivel salvar o arquivo de configuração em: " + arquivo.toString());
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Não foi possivel salvar o arquivo de configuração.");
			alert.showAndWait();
		}
	}

}
