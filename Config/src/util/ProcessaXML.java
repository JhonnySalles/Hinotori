package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

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
		/*
		//Cria o objeto xstream
		XStream xStream = new XStream(new DomDriver());
		//informamos as tags que serao lidas
		//como foi feito no metodo gerarXml002
		xStream.alias("Contato", Conexao.class);
		xStream.aliasField("Endereco", Conexao.class, "endereco");
		xStream.aliasField("Telefones", Conexao.class, "telefones");
		xStream.alias("Telefone", Conexao.class);
		//cria um objeto Contato,
		//contendo os dados do xml
		Contato contato = (Contato) xStream.fromXML(reader);
		//Exibimos no console o resultado
		System.out.println(contato.toString());*/
		
		Conexao cn = new Conexao("teste", "000", "teste", "teste");
		return cn;
	}
	
	public void gravaXml() {
		
	}
	
	public void verificaXml(TelaConfiguracaoController cntr) {
		String caminho = new File("").getAbsolutePath();
		
		File f = new File(caminho + "\\..\\Servidor\\META-INF\\hibernate.cfg.xml");
		if(f.exists()) {
			System.out.println("Abrir");
			Conexao cn = leituraXml(f);
			cntr.setConfig(cn);
			
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
