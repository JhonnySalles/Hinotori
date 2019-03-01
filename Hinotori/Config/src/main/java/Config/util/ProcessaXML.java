package Config.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import Config.alerts.Alertas;
import Config.entitis.Conexao;
import Config.gui.TelaConfiguracaoController;
import model.encode.Encryption;

public class ProcessaXML {
	
	public static Conexao dadosConexao = new Conexao();
	public final static File f = new File(new File("C:\\Teste") + "\\conexao.properties");
	
	public static void leituraConfig(File arquivo) {
		Properties prop = new Properties();
		
		try {
			// Carrega o arquivo proprierts para ser manipulado
			prop.load(new FileInputStream(arquivo));

			// Carrega as informa��es para a entidade 
			dadosConexao.setDatabase(prop.getProperty("prop.server.database"));
			dadosConexao.setHost(prop.getProperty("prop.server.host"));
			dadosConexao.setPorta(prop.getProperty("prop.server.port"));
			dadosConexao.setBase(prop.getProperty("prop.server.base"));
			dadosConexao.setUsuario(prop.getProperty("prop.server.login"));
			dadosConexao.setSenha(Encryption.decodifica(prop.getProperty("prop.server.password")));
			
		} catch (FileNotFoundException e) {
			System.out.println("Não foi possivel ler o arquivo de config, verifique o caminho: " + f.toString());
			e.printStackTrace();
			return;
		} catch (IOException e) {
			System.out.println("Erro ao carregar o arquivo de config.");
			e.printStackTrace();
			return;
		}
	}
	
	public static void gravaConfig() {
		Boolean resultado = false;
		Properties prop = new Properties();
		
		try {
			// Carrega o arquivo proprierts para ser manipulado
			prop.load(new FileInputStream(f));
			
			// Carrega as informa��es para a entidade 
			prop.setProperty("prop.server.database", dadosConexao.getDatabase());
			prop.setProperty("prop.server.host", dadosConexao.getHost());
			prop.setProperty("prop.server.port", dadosConexao.getPorta());
			prop.setProperty("prop.server.base", dadosConexao.getBase());
			prop.setProperty("prop.server.login", dadosConexao.getUsuario());
			prop.setProperty("prop.server.password", Encryption.codifica(dadosConexao.getSenha()));
			
			FileOutputStream arquivoOut = new FileOutputStream(f);
			prop.store(arquivoOut, null);
			
			resultado = true;
		} catch (FileNotFoundException e) {
			System.out.println("Não foi possivel ler o config, verifique o caminho: " + f.toString());
			e.printStackTrace();
			resultado = false;
		} catch (IOException e) {
			System.out.println("Erro ao salvar o arquivo de configuraçãoo.");
			e.printStackTrace();
			resultado = false;
		}
		
		if (resultado) {
			Alertas.Concluido("Salvar", "Arquivo de configuração salvo com sucesso.");
			System.exit(0);
		} else {
			Alertas.Erro("Erro", "Não foi possivel salvar o arquivo de configuração.");
		}
	}
	
	public void verificaConfig(TelaConfiguracaoController cntr) {
		if(f.exists()) {
			ProcessaXML.leituraConfig(f);
			cntr.setConfig(dadosConexao);
			cntr.validaCampos();
		} else {
			ProcessaXML.criaConfig(f);
		}	
	}
	
	public static void criaConfig(File arquivo) {
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
