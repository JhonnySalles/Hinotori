package sistema.util;

import javafx.scene.control.TextField;

/*		Constrants s�o limitadores	*/
public class Constraints {
	
	/*	Fun��o para que seja digitado somente n�meros	*/
	public static void setTextFieldInteger(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> { // O obs � uma referencia para o controlador.
			if(newValue != null && !newValue.matches("\\d*")) { // A express�o serve para validar somente n�meros
				txt.setText(oldValue); //Caso seja digitado algo diferente ir� receber o valor anterior, no caso n�o ter� altera��o.
			}								// d representa digito, * representa qualquer quantidade.
		});
	}
	
	/*	Fun��o para que seja digitado apenas um limite de caracteres	*/
	public static void setTextFieldMaxLenght(TextField txt, int max) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue != null && newValue.length() > max) {
				txt.setText(oldValue);
			}
		});
	}
	
	/*	Fun��o para que seja digitado apenas um limite de caracteres	*/
	public static void setTextFieldDouble(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> { 
			if(newValue != null && !newValue.matches("\\d*([\\.]\\d*)?")) { // A instru��o testa o ponto e casas depois dele.
				txt.setText(oldValue);
			}
		});
	}
	
	
	/*	Fun��o para que seja digitado id que seja n�mero e tenha uma quantidade definida	*/
	public static void setTextFieldID(TextField txt,  int max) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue != null && (!newValue.matches("\\d*") || newValue.length() > max)) { // A express�o serve para validar somente n�meros
				txt.setText(oldValue); //Caso seja digitado algo diferente ir� receber o valor anterior, no caso n�o ter� altera��o.
			}
		});
	}
	
	/*	Fun��o para que seja digitado ddd e telefone	*/
	public static void setTextFieldDDD(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue != null && (!newValue.matches("\\d*") || newValue.length() > 2)) { // A express�o serve para validar somente n�meros
				txt.setText(oldValue); //Caso seja digitado algo diferente ir� receber o valor anterior, no caso n�o ter� altera��o.
			}
		});
	}
	
	public static void setTextFieldFone(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue != null && (!newValue.matches("\\d*") || newValue.length() > 10)) { // A express�o serve para validar somente n�meros
				txt.setText(oldValue); //Caso seja digitado algo diferente ir� receber o valor anterior, no caso n�o ter� altera��o.
			}
		});
	}
	
	/*	Fun��o para que seja digitado estado	*/
	public static void setTextFieldUF(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue != null && (newValue.matches("\\d*") || newValue.length() > 2)) { // A express�o serve para validar somente n�meros
				txt.setText(oldValue); //Caso seja digitado algo diferente ir� receber o valor anterior, no caso n�o ter� altera��o.
			}
		});
	}
}
