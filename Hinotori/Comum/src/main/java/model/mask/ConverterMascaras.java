package model.mask;

public class ConverterMascaras {

	public static String formataCNPJ(String texto) {
		texto = texto.replaceAll("[^0-9]", "");
		texto = texto.replaceFirst("(\\d{2})(\\d)", "$1.$2");
		texto = texto.replaceFirst("(\\d{3})(\\d)", "$1.$2");
		texto = texto.replaceFirst("(\\d{3})(\\d)", "$1/$2");
		texto = texto.replaceFirst("(\\d{4})(\\d)", "$1-$2");
		return texto;		
	}
	
	public static String formataCPF(String texto) {
		texto = texto.replaceAll("[^0-9]", "");
		texto = texto.replaceFirst("(\\d{3})(\\d)", "$1.$2");
		texto = texto.replaceFirst("(\\d{3})(\\d)", "$1.$2");
		texto = texto.replaceFirst("(\\d{3})(\\d)", "$1-$2");
		return texto;		
	}
	
}
