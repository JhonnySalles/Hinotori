package comum.model.constraints;

import java.util.regex.Pattern;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;

/**
 * <p>
 * Classe que contem métodos estáticos para validações de campos field.
 * </p>
 * 
 * <p>
 * Por padrão esta classe possui diversos regex de validação e as validações dos
 * campos como <b>JFXTextField</b> e <b>JFXPasswordField</b> irá utilizar a
 * função já embutida <b>Validato</b>, onde pode-se utilizar essas funções aqui
 * dentro ou então criar um validador próprio que implementa o
 * <b>IFXValidatableControl </b>.
 * </p>
 * 
 * 
 * @author Jhonny de Salles Noschang
 */
public class Validadores {
	private static final String ERROR = "error";

	public static final String REGEX_APENAS_NUMEROS = "\\d*";
	public static final String REGEX_EMAIL = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	public static final String REGEX_CNPJ = "^[0-9]{2}[.][0-9]{3}[.][0-9]{3}[/][0-9]{4}[-][0-9]{2}";
	public static final String REGEX_CPF = "^[0-9]{3}[.][0-9]{3}[.][0-9]{3}[-][0-9]{2}";
	public static final String REGEX_CEP = "^\\d{5}[-]\\d{3}";
	public static final String REGEX_TELEFONE = "^.([1-9]{2}.) (?:[2-8]|9[1-9])[0-9]{3}\\-[0-9]{4}$";
	public static final String REGEX_TELEFONE_INTERNACIONAL = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";
	public static final String REGEX_ENDERECO_IP = "^" + "localhost" + "|"
			+ "(([a-zA-Z]|[a-zA-Z][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z]|[A-Za-z][A-Za-z0-9\\-]*[A-Za-z0-9])" // Dominio
			+ "|" + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}$";

	public static void setTextFieldRegexValidate(final JFXTextField textField, String regex, String menssagem) {
		RegexValidator valid = new RegexValidator();

		valid.setRegexPattern(regex);
		valid.setMessage(menssagem);

		FontIcon warnIcon = new FontIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE);
		warnIcon.getStyleClass().add(ERROR);
		valid.setIcon(warnIcon);

		textField.setValidators(valid);
		textField.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
				textField.validate();
		});
	}

	public static void setTextFieldEmailValidate(final JFXTextField textField) {
		setTextFieldRegexValidate(textField, REGEX_EMAIL, "Email informado inválido!");
	}

	public static void setTextFieldTelefoneValidate(final JFXTextField textField) {
		setTextFieldRegexValidate(textField, REGEX_TELEFONE, "Telefone informado inválido!");
	}

	public static void setTextFieldCNPJValidate(final JFXTextField textField) {
		setTextFieldRegexValidate(textField, REGEX_CNPJ, "CNPJ informado inválido!");
	}

	public static void setTextFieldCPFValidate(final JFXTextField textField) {
		setTextFieldRegexValidate(textField, REGEX_CPF, "CPF informado inválido!");
	}

	public static void setTextFieldNumberValidate(final JFXTextField textField) {
		setTextFieldRegexValidate(textField, REGEX_APENAS_NUMEROS, "Somente números são aceito!");
	}

	public static void setTextFieldNotEmpty(final JFXTextField textField) {
		RequiredFieldValidator validator = new RequiredFieldValidator();
		validator.setMessage("Campo obrigatório!");

		FontIcon warnIcon = new FontIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE);
		warnIcon.getStyleClass().add(ERROR);
		validator.setIcon(warnIcon);

		textField.getValidators().add(validator);
		textField.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
				textField.validate();
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

	public static Boolean validaCpf(final String texto) {
		if (texto.isEmpty() || texto.replaceAll("[^0-9]", "").length() > 12)
			return false;

		return Pattern.compile(REGEX_CPF).matcher(texto).matches();
	}

	public static Boolean validaCnpj(final String texto) {
		if (texto.isEmpty())
			return true;

		return Pattern.compile(REGEX_CNPJ).matcher(texto).matches();
	}

	public static Boolean validaCep(final String texto) {
		if (texto.isEmpty())
			return true;

		return Pattern.compile(REGEX_CEP).matcher(texto).matches();
	}

	public static Boolean validaEmail(final String texto) {
		if (texto.isEmpty())
			return true;

		return texto.matches(REGEX_EMAIL);
	}

	public static Boolean validaTelefone(final String texto) {
		if (texto.isEmpty())
			return true;

		return Pattern.compile(REGEX_TELEFONE).matcher(texto).matches();
	}

	public static Boolean validaTelefoneInternacional(final String texto) {
		if (texto.isEmpty())
			return true;

		return Pattern.compile(REGEX_TELEFONE_INTERNACIONAL).matcher(texto).matches();
	}

	// Funcao pater realiza validacao na forma de expressao.
	public static Boolean validaIp(final String texto) {
		return Pattern.compile(REGEX_ENDERECO_IP).matcher(texto).matches(); // Ip
	}

	// Funcao pater realiza validacao na forma de expressao.
	public static Boolean validaPorta(final String texto) {
		return Pattern.compile(REGEX_APENAS_NUMEROS).matcher(texto).matches(); // Ip
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
