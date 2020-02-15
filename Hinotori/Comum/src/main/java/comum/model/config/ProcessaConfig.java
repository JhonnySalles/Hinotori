package comum.model.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import comum.model.encode.Encryption;
import comum.model.entities.Conexao;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ProcessaConfig {
	public static Conexao dadosConexao = new Conexao();
	public final static File f = new File(Config.verificaConfig());

	public static void leituraConfig(File arquivo) {
		Properties prop = new Properties();

		if (prop.isEmpty()) {
			CriaConfig cria = new CriaConfig();
			cria.criaConfig(arquivo);
		}

		try {
			// Carrega o arquivo proprierts para ser manipulado
			prop.load(new FileInputStream(arquivo));

			// Carrega as informacoes para a entidade
			dadosConexao.setDatabase(prop.getProperty("prop.server.database"));
			dadosConexao.setHost(prop.getProperty("prop.server.host"));
			dadosConexao.setPorta(prop.getProperty("prop.server.port"));
			dadosConexao.setBase(prop.getProperty("prop.server.base"));
			dadosConexao.setUsuario(prop.getProperty("prop.server.login"));
			dadosConexao.setSenha(Encryption.decodifica(prop.getProperty("prop.server.password")));
			dadosConexao.setDatabase(prop.getProperty("prop.server.database"));
			dadosConexao.setCaminhoLog(prop.getProperty("prop.caminho.log"));
			dadosConexao.setMostraErro(prop.getProperty("prop.log.mostrar") == "1" ? true : false);
			dadosConexao.setCharacterEncoding(prop.getProperty("prop.unicode.encode"));
			dadosConexao.setUseUnicode(prop.getProperty("prop.unicode.usa") == "1" ? true : false);
		} catch (FileNotFoundException e) {
			System.out.println("Não foi possivel ler o arquivo de config, verifique o caminho: " + f.toString());
			e.printStackTrace();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText(
					"Não foi possivel ler o arquivo de configuração,  verifique o caminho: " + f.toString());
			alert.showAndWait();
		} catch (IOException e) {
			System.out.println("Erro ao carregar o arquivo de config.");
			e.printStackTrace();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Não foi possivel ler o arquivo de configuração.");
			alert.showAndWait();
		}
	}

	public static void gravaConfig() {
		Properties prop = new Properties();

		try {
			// Carrega o arquivo proprierts para ser manipulado
			prop.load(new FileInputStream(f));

			// Carrega as informacoes para a entidade
			prop.setProperty("prop.server.database", dadosConexao.getDatabase());
			prop.setProperty("prop.server.host", dadosConexao.getHost());
			prop.setProperty("prop.server.port", dadosConexao.getPorta());
			prop.setProperty("prop.server.base", dadosConexao.getBase());
			prop.setProperty("prop.server.login", dadosConexao.getUsuario());
			prop.setProperty("prop.server.password", Encryption.codifica(dadosConexao.getSenha()));
			prop.setProperty("prop.server.database", dadosConexao.getDatabase());
			prop.setProperty("prop.caminho.log", dadosConexao.getCaminhoLog());
			prop.setProperty("prop.log.mostrar", dadosConexao.getMostraErro() ? "1" : "0");
			prop.setProperty("prop.unicode.usa", dadosConexao.getUseUnicode() ? "1" : "0");
			prop.setProperty("prop.unicode.encode", dadosConexao.getCharacterEncoding());

			FileOutputStream arquivoOut = new FileOutputStream(f);
			prop.store(arquivoOut, null);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Erro");
			alert.setHeaderText("Arquivo de configuração salvo com sucesso.");
			alert.showAndWait();
		} catch (FileNotFoundException e) {
			System.out.println("Não foi possivel salvar o arquivo de config, verifique o caminho: " + f.toString());
			e.printStackTrace();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText(
					"Não foi possivel salvar o arquivo de configuração, verifique o caminho: " + f.toString());
			alert.showAndWait();
		} catch (IOException e) {
			System.out.println("Não foi possivel salvar o arquivo de configuração.");
			e.printStackTrace();

			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Não foi possivel salvar o arquivo de configuração.");
			alert.showAndWait();
		}
	}

	public static Conexao getDadosConexao() {
		ProcessaConfig.leituraConfig(f);
		return dadosConexao;
	}

	public static String getCaminhoLog() {
		ProcessaConfig.leituraConfig(f);
		return dadosConexao.getCaminhoLog();
	}

}
