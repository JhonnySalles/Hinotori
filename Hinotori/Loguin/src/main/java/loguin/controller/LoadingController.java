package loguin.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXSpinner;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class LoadingController implements Initializable {

	@FXML
	private AnchorPane background;

	@FXML
	private ImageView logo;

	@FXML
	private Label lblLog;

	@FXML
	private JFXSpinner progress;

	@Override
	public synchronized void initialize(URL arg0, ResourceBundle arg1) {

	}

	public static URL getFxmlLocate() {
		return LoadingController.class.getResource("/loguin/view/Loading.fxml");
	}
}
