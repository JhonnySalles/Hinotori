package model.arquivo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Arquivos {

	final static String cabecalho = "TELA								| TIPO								| SQL								| ERRO";

	public static String leitor(String path) throws IOException {

		String linha = "";
		try (BufferedReader buffRead = new BufferedReader(new FileReader(path))) {
			linha = buffRead.readLine();
			return linha;
		}

	}

	public static void escritor(String path, String texto) throws IOException {

		File file = new File(path);
		String linha = "";
		if (!file.exists()) {
			file.createNewFile();
			linha = cabecalho;
		}

		try (BufferedWriter buffWrite = new BufferedWriter(new FileWriter(file.getAbsoluteFile(), true))) {

			if (!linha.isEmpty()) {
				buffWrite.write(linha);
				buffWrite.newLine();
			}

			buffWrite.write(texto);
			buffWrite.newLine();

		} catch (IOException e) {
			e.getMessage();
		}

	}
	
	public static String criaCaminhoPadraoLog() {
		try {
            File diretorio = new File("C:\\Logs");
            diretorio.mkdir();
            return diretorio.getPath();
        } catch (Exception ex) {
        	ex.printStackTrace();
        }		
		return "";		
	}

}
