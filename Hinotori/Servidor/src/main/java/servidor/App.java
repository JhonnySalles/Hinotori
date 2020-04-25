package servidor;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import servidor.entities.Imagem;
import servidor.entities.Produto;
import servidor.util.HibernateUtil;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) {

		// Cria objeto que receberá as configurações
		Configuration cfg = new Configuration();
		// Informe o arquivo XML que contém a configurações
		cfg.configure(App.class.getClassLoader().getResource("hibernate.cfg.xml"));
		
		// Cria uma fábrica de sessões.
		// Deve existir apenas uma instância na aplicação
		SessionFactory sf = cfg.buildSessionFactory();
		// Abre sessão com o Hibernate
		Session session = sf.openSession();
		// Cria uma transação
		Transaction tx = session.beginTransaction();
		// Cria objeto Aluno*/

		//salvarProdutoTeste();

	}

	private static void salvarProdutoTeste() {

		Produto prod = new Produto();
		prod.setDescricao("HB - Produto 1");

		Produto prod2 = new Produto();
		prod2.setDescricao("HB - Produto 2");

		Set<Imagem> img = new HashSet<Imagem>();

		Imagem teste = new Imagem();
		teste.setNome("teste img hibernate");

		Imagem teste2 = new Imagem();
		teste2.setNome("teste img hibernate");
		img.add(teste);
		img.add(teste2);
		prod2.setImagens(img);

		System.out.println(prod);

		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student objects
			session.save(prod);
			session.save(prod2);
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		
	}

}
