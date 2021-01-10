package servidor.dao;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import comum.model.config.ProcessaConfig;
import comum.model.entities.Configuracao;
import comum.model.exceptions.ExcessaoBd;

public class ManagerFactory {

	private static Properties CONFIG_BD;
	private static EntityManagerFactory EMF;

	public static EntityManager getEtityManager() {
		return EMF.createEntityManager();
	}

	/* Substitui a configuração do arquivo de persistencia pelo arquivo de config */
	private static Properties getConfigBD() throws ExcessaoBd {
		Configuracao dados_conexao = ProcessaConfig.getDadosConexao();
		
		if (dados_conexao == null || dados_conexao.getServer_host().isEmpty())
			throw new ExcessaoBd(
					"Arquivo de configuração do banco não encontrado ou caminho do servidor não informado.");

		Properties props = new Properties();
		props.setProperty("javax.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
		props.setProperty("javax.persistence.jdbc.user", dados_conexao.getServer_usuario());
		props.setProperty("javax.persistence.jdbc.password", dados_conexao.getServer_senha());

		String url = "jdbc:mysql://" + dados_conexao.getServer_host() + ":" + dados_conexao.getServer_porta() + "/"
				+ dados_conexao.getServer_base() + "?useTimezone=true&amp&serverTimezone=UTC"
				+ (dados_conexao.getUnicode_usar() ? "&useUnicode=true" : "");

		props.setProperty("javax.persistence.jdbc.url", url);

		/* Configurações específicas do Hibernate */
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
		props.setProperty("hibernate.hbm2ddl.auto", "update");
		props.setProperty("hibernate.show_sql", dados_conexao.getHibernate_mostrar_sql() ? "true" : "false");
		props.setProperty("hibernate.format_sql", "false");
		return props;
	}
	
	public static void closeConection() {
		EMF.close();
	}

	// O bloco static é chamado na inicialização para
	// criar os métodos staticos.
	static {
		try {
			CONFIG_BD = getConfigBD();
			EMF = Persistence.createEntityManagerFactory("PersistenciaServidor", CONFIG_BD);
		} catch (Exception e) {
			System.out.println("Erro ao iniciar o entity manager.\n" + e.getMessage());
			e.printStackTrace();
		}
	}

}
