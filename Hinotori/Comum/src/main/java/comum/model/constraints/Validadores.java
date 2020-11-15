package comum.model.constraints;

import java.util.regex.Pattern;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class Validadores {

	public static void setTextFieldNotEmpty(final JFXTextField textField) {
		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue) { // Usado para limpar o stilo para que pinte quando entra
					textField.setUnFocusColor(Color.BLACK);
				} else { // Após, na saida faz então a validacao.
					if (textField.textProperty().get().toString().isEmpty()) {
						textField.setUnFocusColor(Color.RED);
					} else {
						textField.setUnFocusColor(Color.BLACK);
					}
				}
			}
		});
	}

	public static void setTextFieldNotEmpty(final JFXPasswordField passwordField) {
		passwordField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue) { // Usado para limpar o stilo para que pinte quando entra
					passwordField.setUnFocusColor(Color.BLACK);
				} else { // Após, na saida faz então a validacao.
					if (passwordField.textProperty().get().toString().isEmpty()) {
						passwordField.setUnFocusColor(Color.RED);
					} else {
						passwordField.setUnFocusColor(Color.BLACK);
					}
				}
			}
		});
	}

	public static void setTextFieldNotEmptyGreen(final JFXTextField textField) {
		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue) { // Usado para limpar o stilo para que pinte quando entra
					textField.setUnFocusColor(Color.BLACK);
				} else { // Após, na saida faz então a validacao.
					if (textField.textProperty().get().toString().isEmpty()) {
						textField.setUnFocusColor(Color.BLACK);
					} else {
						textField.setUnFocusColor(Color.GREEN);
					}
				}
			}
		});
	}

	public static void setTextFieldChangeBlack(final JFXTextField textField) {
		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue)
					textField.setUnFocusColor(Color.BLACK);

				if (oldPropertyValue)
					textField.setUnFocusColor(Color.BLACK);
			}
		});
	}

	public static void setPasswordFieldChangeBlack(final JFXPasswordField Password) {
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

	public static void setTextFieldEmailExit(final JFXTextField textField) {

		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!validaEmail(textField.textProperty().get().toString())) {
					textField.setUnFocusColor(Color.RED);
				} else {
					textField.setUnFocusColor(Color.BLACK);
				}
			}
		});
	}

	public static void setTextFieldTelefoneExit(final JFXTextField textField) {
		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (!validaTelefone(textField.textProperty().get().toString())) {
					textField.setUnFocusColor(Color.RED);
				} else {
					textField.setUnFocusColor(Color.BLACK);
				}
			}
		});
	}

	/**
	 * <p>
	 * Função para fazer a validação de campos de cpf e cnpj ao sair, no qual irá
	 * pintar da cor desejada.
	 * </p>
	 * 
	 * @param textField   O textField na qual irá ser validado o seu conteúdo.
	 * @param Obrigatorio Será utilizada para habilitar ou desabilitar sua
	 *                    obrigatóriedade.
	 * @author Jhonny de Salles Noschang
	 */
	public static void setTextFieldCpnfCnpjExit(final JFXTextField textField) {
		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
					Boolean newPropertyValue) {
				if (newPropertyValue) {
					textField.setUnFocusColor(Color.BLACK);
					return;
				}

				if (!validaCpfCnpj(textField.getText()))
					textField.setUnFocusColor(Color.RED);
				else
					textField.setUnFocusColor(Color.BLACK);
			}
		});
	}

	public static Boolean validaCpfCnpj(final String texto) {
		if (texto.isEmpty()) {
			return true;
		} else {
			int lenght = texto.replaceAll("[^0-9]", "").length();
			Pattern p;
			if (lenght >= 12) {
				p = Pattern.compile("^[0-9]{2}[.][0-9]{3}[.][0-9]{3}[/][0-9]{4}[-][0-9]{2}"); // Cnpj

			} else {
				p = Pattern.compile("^[0-9]{3}[.][0-9]{3}[.][0-9]{3}[-][0-9]{2}"); // Cpf
			}

			if (p.matcher(texto).matches())
				return true;
			else
				return false;
		}
	}
	
	public static Boolean validaCep(final String texto) {
		if (texto.isEmpty()) {
			return true;
		} else {
			Pattern p = Pattern.compile("^\\d{5}[-]\\d{3}"); // Cep

			if (p.matcher(texto).matches())
				return true;
			else
				return false;
		}
	}

	public static Boolean validaEmail(final String texto) {
		if (texto.isEmpty()) {
			return true;
		} else {
			String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
			return texto.matches(regex);
		}
	}

	public static Boolean validaTelefone(final String texto) {
		if (texto.isEmpty()) {
			return true;
		} else {
			Pattern p = Pattern.compile("^.([1-9]{2}.) (?:[2-8]|9[1-9])[0-9]{3}\\-[0-9]{4}$"); // Telefone
			if (p.matcher(texto).matches())
				return true;
			else
				return false;
		}
	}

	public static Boolean validaTelefoneInternacional(final String texto) {
		if (texto.isEmpty()) {
			return true;
		} else {
			Pattern p = Pattern.compile("^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$"); // Telefone
			if (p.matcher(texto).matches())
				return true;
			else
				return false;
		}
	}

	// Funcao pater realiza validacao na forma de expressao.
	public static Boolean validaIp(final TextField txt) {
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
	public static void setTxtFieldPort(final TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> { // O obs e uma referencia para o controlador.
			if (newValue != null && (!newValue.matches("\\d*") || newValue.length() > 6)) { // A expressao serve para
																							// validar somente n�meros
				txt.setText(oldValue); // Caso seja digitado algo diferente ira receber o valor anterior, no caso nao
										// tera alteracao.
			} // d representa digito, * representa qualquer quantidade.
		});
	}
	
	public static Boolean validaEAN(final String codigoBarra) {
		boolean status = false;
        if (!codigoBarra.isEmpty()) {
            // Remove nom numeric characters
            String treatedEanStr = codigoBarra.replaceAll("[^\\d.]", "");
            // Remove leading zeros
            String shortenedEanStr = treatedEanStr.replaceFirst("^0+(?!$)", "");
            // Validate length
            if (shortenedEanStr.length() > 7 && shortenedEanStr.length() < 14) {
                int sum = 0;
                String[] digits = treatedEanStr.split("");
                for (int i = 0; i < (digits.length - 1); i++)
                    sum += Integer.parseInt(digits[i]) * ((i % 2 == 0) ? 1 : 3);
                
                int checksumDigit = (10 - (sum % 10)) % 10;
                status = (Integer.parseInt(digits[digits.length - 1]) == checksumDigit);
            }
        }
        return status;
	}

}
