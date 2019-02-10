package guiMain;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	
	public static String usuario;
	public static Stage loginStage;
	
	@FXML
	private PasswordField pswFieldPassword;
	@FXML
	private Button btnExit;
	@FXML
	private Button btnLogin;
	
	@FXML
	private ComboBox<String> cbBoxUsuario;
	private ObservableList<String> obsList;
	
	@FXML
	public void onBtExitAction() {
		System.exit(0);
	}
	
	@FXML
	public void onDrLoginExited() {
	
	}
	
	@FXML
	public void onPasswordEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
	
	}
	
	@FXML
	public void onUserEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException{
	
	}
	
	@FXML
	public void onBtLoginAction() throws NoSuchAlgorithmException, UnsupportedEncodingException {


	}
	
	public void initialize(URL url, ResourceBundle rb) {

	}
	
	public void ValidaLogin() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
	
	}
}
