package model.constraints;

import javafx.scene.control.TextField;

/*		Constrants sao limitadores	*/
public class Limitadores {
	
	/*	Funcao para que seja digitado somente numeros	*/
	public static void setTextFieldInteger(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> { // O obs � uma referencia para o controlador.
			if(newValue != null && !newValue.matches("\\d*")) { // A expressao serve para validar somente numeros
				txt.setText(oldValue); //Caso seja digitado algo diferente ir� receber o valor anterior, no caso nao tera alteracao.
			}								// d representa digito, * representa qualquer quantidade.
		});
	}
	
	/*	Funcao para que seja digitado apenas um limite de caracteres	*/
	public static void setTextFieldMaxLenght(TextField txt, int max) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue != null && newValue.length() > max) {
				txt.setText(oldValue);
			}
		});
	}
	
	/*	Funcao para que seja digitado apenas um limite de caracteres	*/
	public static void setTextFieldDouble(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> { 
			if(newValue != null && !newValue.matches("\\d*([\\.]\\d*)?")) { // A instrucao testa o ponto e casas depois dele.
				txt.setText(oldValue);
			}
		});
	}
	
	
	/*	Funcao para que seja digitado id que seja numero e tenha uma quantidade definida	*/
	public static void setTextFieldID(TextField txt,  int max) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue != null && (!newValue.matches("\\d*") || newValue.length() > max)) { // A expressao serve para validar somente numeros
				txt.setText(oldValue); //Caso seja digitado algo diferente ir� receber o valor anterior, no caso nao tera alteracao.
			}
		});
	}
	
	/*	Funcao para que seja digitado ddd e telefone	*/
	public static void setTextFieldDDD(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue != null && (!newValue.matches("\\d*") || newValue.length() > 2)) { // A expressao serve para validar somente numeros
				txt.setText(oldValue); //Caso seja digitado algo diferente ir� receber o valor anterior, no caso nao tera alteracao.
			}
		});
	}
	
	public static void setTextFieldFone(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue != null && (!newValue.matches("\\d*") || newValue.length() > 10)) { // A expressao serve para validar somente numeros
				txt.setText(oldValue); //Caso seja digitado algo diferente ir� receber o valor anterior, no caso nao tera alteracao.
			}
		});
	}
	
	public static void setTextFieldCep(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue != null && (!newValue.matches("\\d*") || newValue.length() > 8)) { // A expressao serve para validar somente numeros
				txt.setText(oldValue); //Caso seja digitado algo diferente ira receber o valor anterior, no caso nao tera alteracao.
			}
		});
	}
	
	/*	Funcao para que seja digitado estado	*/
	public static void setTextFieldUF(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue != null && (newValue.matches("\\d*") || newValue.length() > 2)) { // A expressao serve para validar somente numeros
				txt.setText(oldValue); //Caso seja digitado algo diferente ira receber o valor anterior, no caso nao tera alteracao.
			}
		});
	}
}
