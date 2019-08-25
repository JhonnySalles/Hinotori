package model.utils;

import java.io.File;

public class Utils {

	public static String getFileExtension(File file) {
	    String name = file.getName();
	    int lastIndexOf = name.lastIndexOf(".");
	    if (lastIndexOf == -1) {
	        return ""; // empty extension
	    }
	    return name.substring(lastIndexOf);
	}
	
	public static String removeMascaras(String texto) {
		return texto.replaceAll("[.-/()]", "");
	}
	
}
