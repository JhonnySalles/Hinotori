package application;

import java.io.File;

public class ProcessaXML {

	
	public void leituraXml() {
		
		
	}
	
	public void gravaXml() {
		
	}
	
	public void verificaXml() {
		String path = new File("").getAbsolutePath();
		File f = new File(path + "\\src\\META-INF\\hibernate.cfg.xml");
		
		if(f.exists()) {
			System.out.println(path+"/src/META-INF/hibernate.cfg.xml");
		}
		
		
	}
	
	public void criaXml() {
		
	}
	
	
}
