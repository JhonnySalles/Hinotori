package servidor.persistencia;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import comum.model.config.ProcessaConfig;
import comum.model.entities.Configuracao;

public class Controlador {
	private EntityManagerFactory emf;

	private static Persistence persistence;
	private static Properties configbd = getConfigBD();

	/* Substitui a configuração do arquivo de persistencia pelo arquivo de config */
	private static Properties getConfigBD() {
		Configuracao dados_conexao = ProcessaConfig.getDadosConexao();

		if (dados_conexao == null || dados_conexao.getServer_host().isEmpty())
			return null;

		Properties props = new Properties();
		props.setProperty("javax.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
		props.setProperty("javax.persistence.jdbc.user", dados_conexao.getServer_usuario());
		props.setProperty("javax.persistence.jdbc.password", dados_conexao.getServer_senha());

		String url = "jdbc:mysql://" + dados_conexao.getServer_host() + ":" + dados_conexao.getServer_porta() + "/"
				+ dados_conexao.getServer_base() + "?useTimezone=true&amp;serverTimezone=UTC"
				+ (dados_conexao.getUnicode_usar() ? "&useUnicode=true" : "");

		props.setProperty("javax.persistence.jdbc.url", url);

		/* Configurações específicas do Hibernate */
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
		props.setProperty("hibernate.hbm2ddl.auto", "update");
		props.setProperty("hibernate.show_sql", dados_conexao.getHibernate_mostrar_sql() ? "true" : "false");
		props.setProperty("hibernate.format_sql", "false");
		return props;
	}

	public static Persistence getInstance() {
		if (persistence == null)
			persistence = new Persistence();

		return persistence;
	}

	public EntityManager createEntityManager() {
		return emf.createEntityManager();
	}

	public Controlador() {
		if (configbd != null)
			emf = Persistence.createEntityManagerFactory("PersistenciaServidor", configbd);
		else
			emf = Persistence.createEntityManagerFactory("PersistenciaServidor");
	}

	public static void closeAll() {
		if (persistence != null) {
			((EntityManager) persistence).close();
		}
	}

	private void close() {
		emf.close();
	}
}
