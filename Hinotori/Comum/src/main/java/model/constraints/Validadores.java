package model.constraints;

import java.util.regex.Pattern;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class Validadores {

	public static void setTextFieldNotEmptyGreen(JFXTextField TextField) {
		TextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue) { // Usado para limpar o stilo para que pinte quando entra
					TextField.setUnFocusColor(Color.BLACK);
				} else { // Após, na saida faz então a validacao.
					if (TextField.textProperty().get().toString().isEmpty()) {
						TextField.setUnFocusColor(Color.BLACK);
					} else {
						TextField.setUnFocusColor(Color.GREEN);
					}
				}
			}
		});
	}

	public static void setTextFieldChangeBlack(JFXTextField TextField) {
		TextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue)
					TextField.setUnFocusColor(Color.BLACK);

				if (oldPropertyValue)
					TextField.setUnFocusColor(Color.BLACK);

			}
		});
	}

	public static void setPasswordFieldChangeBlack(JFXPasswordField Password) {
		Password.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue)
					Password.setUnFocusColor(Color.BLACK);

				if (oldPropertyValue)
					Password.setUnFocusColor(Color.BLACK);

			}
		});
	}

	public static void setTextFielEmailExitColor(JFXTextField TextField) {

		TextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {

				if (!validaEmail(TextField)) {
					TextField.setUnFocusColor(Color.RED);
				} else {
					TextField.setUnFocusColor(Color.BLACK);
				}
			}
		});
	}

	public static void setTextFielTelefoneExitColor(JFXTextField TextField) {

		TextField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!validaTelefone(TextField)) {
					TextField.setUnFocusColor(Color.RED);
				} else {
					TextField.setUnFocusColor(Color.BLACK);
				}
			}
		});
	}

	public static Boolean validaEmail(JFXTextField TextField) {
		if (TextField.getText().isEmpty()) {
			return true;
		} else {
			String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
			return TextField.getText().matches(regex);
		}
	}

	public static Boolean validaTelefone(JFXTextField TextField) {
		if (TextField.getText().isEmpty()) {
			return true;
		} else {
			Pattern p = Pattern.compile("^\\([1-9]{2}\\)(?:[2-8]|9[1-9])[0-9]{3}\\-[0-9]{4}$"); // Telefone
			if (p.matcher(TextField.getText()).matches())
				return true;
			else
				return false;
		}
	}

	// Funcao pater realiza validacao na forma de expressao.
	public static Boolean validaIp(TextField txt) {
		Pattern p = Pattern.compile("^"
				+ "(([a-zA-Z]|[a-zA-Z][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z]|[A-Za-z][A-Za-z0-9\\-]*[A-Za-z0-9])" // Dominio
				+ "|" + "localhost" // localhost
				+ "|" + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}$"); // Ip
		if (p.matcher(txt.textProperty().get().toString()).matches()) {
			return true;
		} else
			return false;
	}

	/* Funcao para que seja validada a porta */
	public static void setTxtFieldPort(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> { // O obs e uma referencia para o controlador.
			if (newValue != null && (!newValue.matches("\\d*") || newValue.length() > 6)) { // A expressao serve para
																							// validar somente n�meros
				txt.setText(oldValue); // Caso seja digitado algo diferente ira receber o valor anterior, no caso nao
										// tera alteracao.
			} // d representa digito, * representa qualquer quantidade.
		});
	}

	/* Funcao para que seja digitado apenas um limite de caracteres */
	public static void setTextFieldMaxLenght(TextField txt, int max) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if (newValue != null && newValue.length() > max) {
				txt.setText(oldValue);
			}
		});
	}

}
