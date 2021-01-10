package comum.model.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import comum.model.alerts.Alertas;
import comum.model.encode.Encryption;
import comum.model.entities.Configuracao;
import comum.model.enums.DataBase;
import comum.model.enums.Tema;
import comum.model.enums.TipoLancamento;
import javafx.scene.control.Alert.AlertType;

public class ProcessaConfig {

	private final static Logger LOGGER = Logger.getLogger(ProcessaConfig.class.getName());

	public static Configuracao dadosConexao = new Configuracao();
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
			dadosConexao.setServer_database(DataBase.valueOf(prop.getProperty("prop.server.database")));
			dadosConexao.setServer_host(prop.getProperty("prop.server.host"));
			dadosConexao.setServer_porta(prop.getProperty("prop.server.port"));
			dadosConexao.setServer_base(prop.getProperty("prop.server.base"));
			dadosConexao.setServer_usuario(prop.getProperty("prop.server.login"));
			dadosConexao.setServer_senha(Encryption.decodifica(prop.getProperty("prop.server.password")));

			dadosConexao.setUnicode_encode(prop.getProperty("prop.unicode.encode"));
			dadosConexao.setUnicode_usar(prop.getProperty("prop.unicode.usar") == "1" ? true : false);

			dadosConexao.setLog_caminho(prop.getProperty("prop.log.caminho"));
			dadosConexao.setLog_mostrar(prop.getProperty("prop.log.mostrar") == "1" ? true : false);

			dadosConexao.setHibernate_mostrar_sql(prop.getProperty("prop.hibernate.mostrarSql") == "1" ? true : false);

			dadosConexao.setSistema_tema(Tema.valueOf(prop.getProperty("prop.sistema.tema")));
			dadosConexao.setSistema_tipo(TipoLancamento.valueOf(prop.getProperty("prop.sistema.tipo")));

			validaConfig();

		} catch (FileNotFoundException e) {
			Alertas.Alerta(AlertType.ERROR, "Erro",
					"Não foi possivel ler o arquivo de configuração,  verifique o caminho: " + f.toString());
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "{Erro ao carregar o arquivo config: " + f.toString() + "}", e);
		} catch (IOException e) {
			Alertas.Alerta(AlertType.ERROR, "Erro", "Não foi possivel ler o arquivo de configuração.");
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "{Erro ao carregar o arquivo config: " + f.toString() + "}", e);
		}
	}

	private static void validaConfig() {

		if (dadosConexao.getServer_host().isEmpty())
			Alertas.Alerta(AlertType.WARNING, "Aviso",
					"Arquivo de configuração com informações faltando, não localizado ip do servidor.");

		if (dadosConexao.getServer_porta().isEmpty())
			Alertas.Alerta(AlertType.WARNING, "Aviso",
					"Arquivo de configuração com informações faltando, não localizado a porta do servidor.");

		if (dadosConexao.getServer_base().isEmpty())
			Alertas.Alerta(AlertType.WARNING, "Aviso",
					"Arquivo de configuração com informações faltando, não localizado a base de dados.");

		if (dadosConexao.getServer_usuario().isEmpty() || dadosConexao.getServer_senha().isEmpty())
			Alertas.Alerta(AlertType.WARNING, "Aviso",
					"Arquivo de configuração com informações faltando, não usuário ou senha de acesso.");

		if (dadosConexao.getUnicode_encode().isEmpty()) {
			dadosConexao.setUnicode_encode("UTF-8");
			dadosConexao.setUnicode_usar(true);
		}

	}

	public static void gravaConfig() {
		Properties prop = new Properties();

		try {
			// Carrega o arquivo proprierts para ser manipulado
			prop.load(new FileInputStream(f));

			// Carrega as informacoes para a entidade
			prop.setProperty("prop.server.database", dadosConexao.getServer_database().toString());
			prop.setProperty("prop.server.host", dadosConexao.getServer_host());
			prop.setProperty("prop.server.port", dadosConexao.getServer_porta());
			prop.setProperty("prop.server.base", dadosConexao.getServer_base());
			prop.setProperty("prop.server.login", dadosConexao.getServer_usuario());
			prop.setProperty("prop.server.password", Encryption.codifica(dadosConexao.getServer_senha()));

			prop.setProperty("prop.unicode.usar", dadosConexao.getUnicode_usar() ? "1" : "0");
			prop.setProperty("prop.unicode.encode", dadosConexao.getUnicode_encode());

			prop.setProperty("prop.log.caminho", dadosConexao.getLog_caminho());
			prop.setProperty("prop.log.mostrar", dadosConexao.getLog_mostrar() ? "1" : "0");

			prop.setProperty("prop.sistema.tema", dadosConexao.getSistema_tema().toString());
			prop.setProperty("prop.sistema.tipo", dadosConexao.getSistema_tipo().toString());

			FileOutputStream arquivoOut = new FileOutputStream(f);
			prop.store(arquivoOut, null);

			Alertas.Concluido("Arquivo salvo", "Arquivo de configuração salvo com sucesso.");
		} catch (FileNotFoundException e) {
			Alertas.Alerta(AlertType.ERROR, "Erro",
					"Não foi possivel salvar o arquivo de configuração, verifique o caminho: " + f.toString());
			e.printStackTrace();
		} catch (IOException e) {
			Alertas.Alerta(AlertType.ERROR, "Erro", "Não foi possivel salvar o arquivo de configuração.");
			e.printStackTrace();
		}
	}

	public static Configuracao getDadosConexao() {
		ProcessaConfig.leituraConfig(f);
		return dadosConexao;
	}

	public static String getCaminhoLog() {
		ProcessaConfig.leituraConfig(f);
		return dadosConexao.getLog_caminho();
	}

}
