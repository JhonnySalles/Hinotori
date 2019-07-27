package br.com.jisho.Teste;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class App {
	
	public void getPage(URL url, File file) throws IOException {
        BufferedReader in =
                new BufferedReader(new InputStreamReader(url.openStream()));

        BufferedWriter out = new BufferedWriter(new FileWriter(file));

        String inputLine;

        while ((inputLine = in.readLine()) != null) {
		
            // Imprime página no console
            System.out.println(inputLine);
			
            // Grava pagina no arquivo
            out.write(inputLine);
            out.newLine();
        }

        in.close();
        out.flush();
        out.close();
    }

    public static void main(String[] args) {
        /*URL url = null;
        File file = new File("D:\\Projeto App\\page.html");
        try {	
            url = new URL("https://jisho.org/search/%E7%8C%AB%20%23kanji");
            new App().getPage(url, file);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    	
    	List<String> supplierNames = Arrays.asList();
    	List<String> radicais = new ArrayList();
		Document doc;
		try {
			
			
			
			for (String ls : supplierNames) {
				
				System.out.println(ls);
				doc = Jsoup.connect("https://jisho.org/search/" + URLEncoder.encode( ls, "UTF-8")+ "%20%23kanji").get();

				
				Elements content = doc.getElementsByClass("radicals");	
				Elements variant = doc.getElementsByClass("variants");
				System.out.println(content.text() + " " + variant.text());
				radicais.add(ls + " " + content.text() + "                                                      " + variant.text());
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BufferedWriter out;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
				    new FileOutputStream("D:\\Projeto App\\test.txt"), "UTF-8"));
			
			try {

				for (String ls : radicais) {
					out.write(ls);
					out.newLine();
				}
			    
			} finally {
			    out.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
