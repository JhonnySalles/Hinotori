package util;

import javafx.scene.control.TextField;

/*		Constrants são limitadores	*/
public class Constraints {
	
	/*	Função para que seja digitado somente números	*/
	public static void setTextFieldInteger(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> { // O obs é uma referencia para o controlador.
			if(newValue != null && !newValue.matches("\\d*")) { // A expressão serve para validar somente números
				txt.setText(oldValue); //Caso seja digitado algo diferente irá receber o valor anterior, no caso não terá alteração.
			}								// d representa digito, * representa qualquer quantidade.
		});
	}
	
	/*	Função para que seja digitado apenas um limite de caracteres	*/
	public static void setTextFieldMaxLenght(TextField txt, int max) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue != null && newValue.length() > max) {
				txt.setText(oldValue);
			}
		});
	}
	
	/*	Função para que seja digitado apenas um limite de caracteres	*/
	public static void setTextFieldDouble(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> { 
			if(newValue != null && !newValue.matches("\\d*([\\.]\\d*)?")) { // A instrução testa o ponto e casas depois dele.
				txt.setText(oldValue);
			}
		});
	}
	
	
	/*	Função para que seja digitado id que seja número e tenha uma quantidade definida	*/
	public static void setTextFieldID(TextField txt,  int max) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue != null && (!newValue.matches("\\d*") || newValue.length() > max)) { // A expressão serve para validar somente números
				txt.setText(oldValue); //Caso seja digitado algo diferente irá receber o valor anterior, no caso não terá alteração.
			}
		});
	}
	
	/*	Função para que seja digitado ddd e telefone	*/
	public static void setTextFieldDDD(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue != null && (!newValue.matches("\\d*") || newValue.length() > 2)) { // A expressão serve para validar somente números
				txt.setText(oldValue); //Caso seja digitado algo diferente irá receber o valor anterior, no caso não terá alteração.
			}
		});
	}
	
	public static void setTextFieldFone(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue != null && (!newValue.matches("\\d*") || newValue.length() > 10)) { // A expressão serve para validar somente números
				txt.setText(oldValue); //Caso seja digitado algo diferente irá receber o valor anterior, no caso não terá alteração.
			}
		});
	}
	
	/*	Função para que seja digitado estado	*/
	public static void setTextFieldUF(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue != null && (newValue.matches("\\d*") || newValue.length() > 2)) { // A expressão serve para validar somente números
				txt.setText(oldValue); //Caso seja digitado algo diferente irá receber o valor anterior, no caso não terá alteração.
			}
		});
	}
}
