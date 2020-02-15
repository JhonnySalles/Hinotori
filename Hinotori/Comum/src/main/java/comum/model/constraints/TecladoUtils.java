package comum.model.constraints;

import comum.model.utils.Utils;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class TecladoUtils {

	public static void onEnterConfigureTab(TextField textField) {
		textField.setOnKeyPressed(e -> {
			if (e.getCode().toString().equals("ENTER"))
				Utils.clickTab();
		});
	}

	public static <T> void onEnterConfigureTab(ComboBox<T> comboBox) {
		comboBox.setOnKeyPressed(e -> {
			if (e.getCode().toString().equals("ENTER"))
				Utils.clickTab();
		});
	}

}
