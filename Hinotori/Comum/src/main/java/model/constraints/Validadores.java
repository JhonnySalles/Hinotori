package model.constraints;

import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

}
