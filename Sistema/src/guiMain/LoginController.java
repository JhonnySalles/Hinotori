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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.encode.DecodeHash;

public class LoginController implements Initializable {
	
	public static String usuario;
	public static Stage loginStage;
	
	
	@FXML
	private Label lblAviso;
	@FXML
	private ImageView imgViewAviso;
	
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
		pswFieldPassword.setStyle("-fx-border-color: transparent; " + 
 				  "-fx-border-width: 0; ");
	}
	
	@FXML
	public void onPasswordEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if(e.getCode().toString().equals("ENTER"))
	    {
			ValidaLogin();
	    }
	}
	
	@FXML
	public void onUserEnter(KeyEvent e) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		if (e.getCode().toString().equals("ENTER")) {
			pswFieldPassword.requestFocus();
		}
	}
	
	@FXML
	public void onBtLoginAction() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		ValidaLogin();
	}
	
	public void initialize(URL url, ResourceBundle rb) {

		/*
		//Gera lista de usuario
		List<String> lista = DBUtil.DBGetUsers();	
		obsList = FXCollections.observableArrayList(lista);
		cbBoxUsuario.getItems().addAll(obsList); // Transfere a lista para o combobox
		cbBoxUsuario.requestFocus(); // Foco principal no combo box */
		
	}
	
	public void ValidaLogin() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String user = "";
		
		// Caso seja vazio, acreditasse que irá logar como nosso usuario.
		if (cbBoxUsuario.getValue() == null){
			user = "SHIYOKEN";
		} else {
			user = cbBoxUsuario.getSelectionModel().getSelectedItem().toString();
		}
		
		String password = pswFieldPassword.getText();
		
		
		if (DecodeHash.CompPassword(user, password)) {
			
			
		} else {
			
			if (cbBoxUsuario.getValue() == null){
				cbBoxUsuario.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
				cbBoxUsuario.requestFocus();
			} else {
				// Deixa o campo de senha em vermelho.
				pswFieldPassword.setStyle("-fx-background-color: red,linear-gradient(to bottom, derive(red,60%) 5%,derive(red,90%) 40%);");
				pswFieldPassword.requestFocus(); // Seta o foco no campo
				pswFieldPassword.selectAll();    // seleciona todo o texto.
			}		
		}
		
	
	}
}
