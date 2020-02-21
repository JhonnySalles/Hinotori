package servidor;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import servidor.entities.Imagem;
import servidor.entities.Produto;

/**
 * Hello world!
 *
 */
public class App {
	
    public static void main( String[] args ) {
    	
    	//Cria objeto que receberá as configurações
    	 Configuration cfg = new Configuration();
    	 //Informe o arquivo XML que contém a configurações
    	 
    	cfg.configure(App.class.getClassLoader().getResource("META-INF/hibernate.cfg.xml"));
    	 
    	 //Cria uma fábrica de sessões.
    	 //Deve existir apenas uma instância na aplicação
    	 SessionFactory sf = cfg.buildSessionFactory();
    	 // Abre sessão com o Hibernate
    	 Session session = sf.openSession();
    	 //Cria uma transação
    	 Transaction tx = session.beginTransaction();
    	 // Cria objeto Aluno
    	
    	
    	
       //Controlador com = new Controlador();
    	
    	/*Produto prod = new Produto();
    	prod.setDescricao("hibernate 1");
    	
    	Produto prod2 = new Produto();
    	prod2.setDescricao("hibernate 2");
    	Set<Imagem> img = new HashSet<Imagem>();
    	Imagem teste = new Imagem();
    	teste.setNome("teste hibernate");
    	Imagem teste2 = new Imagem();
    	teste2.setNome("teste hibernate");
    	img.add(teste);
    	img.add(teste2);
    	prod2.setImagens(img);

    	
    	
    	session.save(prod);
    	session.save(prod2);
    	tx.commit();*/
    }
    
    
    
}
