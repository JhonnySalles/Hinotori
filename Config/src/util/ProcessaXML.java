package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


import alerts.Alertas;
import entitis.Conexao;
import gui.TelaConfiguracaoController;

public class ProcessaXML {

	
	public Conexao leituraXml(File arquivo) {
		
		// Irá utilizar o XStream para realizar a leitura do config.
		FileReader reader = null;
		try {
			//carrega o arquivo XML para um objeto reader
			reader = new FileReader(arquivo);
		} catch (FileNotFoundException e) {
			System.out.println("Não foi possivel ler o xml do config.");
			e.printStackTrace();
		}
		
		Properties prop = new Properties();
	    prop.setProperty("hibernate.connection.driver_class", "oracle.jdbc.driver.OracleDriver");
	    prop.setProperty("hibernate.connection.url", "jdbc:mysql://localhost...");
	    prop.setProperty("hibernate.connection.username", "root");
	    prop.setProperty("hibernate.connection.password", "123");
	    prop.setProperty("dialect", "org.hibernate.dialect.OracleDialect");

	    SessionFactory sessionFactory = new Configuration()
	            .addPackage("pacote onde estao as entidades")
	                .addProperties(prop)
	                .addAnnotatedClass(Conexao.class)
	                .buildSessionFactory();
		
		Conexao cn = new Conexao("teste", "000", "teste", "teste");
		return cn;
	}
	
	public static void gravaXml(File URL, String user, String password, String driver) {
		
		System.out.println("teste");
		Configuration config = new Configuration();
		System.out.println("teste");
		config.configure(URL.toString());
		System.out.println("teste");

		config.setProperty("javax.persistence.jdbc.user", "update" );
		System.out.println("teste");
		config.setProperty("javax.persistence.jdbc.password", "defgh629154" ); 
		
		/*Properties prop = new Properties();
	    prop.setProperty("hibernate.connection.driver_class", "oracle.jdbc.driver.OracleDriver");
	    prop.setProperty("hibernate.connection.url", "jdbc:mysql://localhost...");
	    prop.setProperty("hibernate.connection.username", "root");
	    prop.setProperty("hibernate.connection.password", "123");
	    prop.setProperty("dialect", "org.hibernate.dialect.OracleDialect");

	    SessionFactory sessionFactory = new Configuration()
	            .addPackage("pacote onde estao as entidades")
	                .addProperties(prop)
	                .addAnnotatedClass(Conexao.class)
	                .buildSessionFactory();*/
	    
	   // return sessionFactory;
		
	}
	
	public void verificaXml(TelaConfiguracaoController cntr) {
		String caminho = new File("").getAbsolutePath();
		
		File f = new File(caminho + "\\..\\Servidor\\META-INF\\hibernate.cfg.xml");
		if(f.exists()) {
			ProcessaXML.gravaXml(f, null, null, null);
			//Conexao cn = leituraXml(f);
			//cntr.setConfig(cn);
			
		} else {
			System.out.println("Não encontrado o arquivo xml de configuração, verifique o caminho: \n"+
							   caminho + "\\..\\Servidor\\META-INF\\hibernate.cfg.xml");
			
			Alertas.Erro("Erro ao abrir arquivo de configuração",
						 "Não foi possivel encontrar arquivo xml de configuração.", 
						 "Verifique a pasta META-INF no local de instalação.");
		}	
	}
	
	public void criaXml() {
		
	}
	
	
}
