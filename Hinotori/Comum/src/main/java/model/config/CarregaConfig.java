package model.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CarregaConfig {

	public static Properties carregaConfig() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			System.out.println("Não foi possível carregar o config.");
			e.printStackTrace();
		}
		return null;
	}

}
