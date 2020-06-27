package servidor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import servidor.test.Teste;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) {
		// Controlador entityManager = new Controlador();
		// Cria objeto que receberá as configurações
		Configuration cfg = new Configuration();
		// Informe o arquivo XML que contém a configurações
		cfg.configure(App.class.getClassLoader().getResource("hibernate.cfg.xml"));

		// Cria uma fábrica de sessões.
		// Deve existir apenas uma instância na aplicação
		SessionFactory sf = cfg.buildSessionFactory();
		// Abre sessão com o Hibernate
		Session session = sf.openSession();
		
		Teste iniciar = new Teste();
		iniciar.Iniciar();
	}

}
