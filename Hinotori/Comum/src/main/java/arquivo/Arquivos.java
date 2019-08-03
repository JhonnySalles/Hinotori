package arquivo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Arquivos {

	public static String leitor(String path) throws IOException {

		BufferedReader buffRead = new BufferedReader(new FileReader(path));
		String linha = "";
		try {
			linha = buffRead.readLine();
			return linha;
		} finally {
			buffRead.close();
		}
	}

	public static void escritor(String path, String texto) throws IOException {

		File file = new File(path);
		if (!file.exists()) {
			file.createNewFile();
			texto = "TELA								| TIPO								| ERRO  \r\n";
		}

		BufferedWriter buffWrite = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
		Scanner in = new Scanner(System.in);
		try {
			String linha = "";
			linha = in.nextLine();
			buffWrite.append(linha + texto);
		} finally {
			buffWrite.close();
			in.close();
		}
	}

}
