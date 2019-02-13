package application;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JLabel;

import Alertas.Alertas;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class TelaConfiguracaoController implements Initializable {
	
	@FXML
	AnchorPane background;
	
	@FXML
	Button btnExit;
	
	@FXML
	Button btnTestarConexao;
	
	@FXML
	Button btnConfirmar;
	
	@FXML
	Button btnCancelar;
	
	@FXML
	ChoiceBox chBoxBase;
	
	@FXML
	TextField txtIP;
	
	@FXML
	TextField txtPorta;
	
	@FXML
	TextField txtUsuario;

	@FXML
	PasswordField pswSenha;
	
	@FXML
	Pane pnImgAviso;
	
	@FXML
	Label lblAviso;
	
	@FXML
	ImageView imgViewConexao;
	
	@FXML
	public void onBtExitAction() {
		System.exit(0);
	}
	
	@FXML
	public void onBtnTesteClick() {
		lblAviso.setText("");
		lblAviso.setVisible(false);
		pnImgAviso.setVisible(false);
		
		imgViewConexao.setImage(new Image(getClass().getResourceAsStream("../images/config/bntDataBase_48.png")));
		imgViewConexao.setFitWidth(48);
		imgViewConexao.setFitHeight(48);
		
		if (validaCampos() && Constraints.validaIp(txtIP)) {
			String ip = txtIP.getText();
			String port = txtPorta.getText();
			String user = txtUsuario.getText();
			String psword = pswSenha.getText();
			
			if (ConexaoMySQL.testaConexxaoMySQL(ip, port, "MySQL", user, psword)) {
				lblAviso.setText("Conectado com sucesso!");
				lblAviso.setVisible(true);
				imgViewConexao.setImage(new Image(getClass().getResourceAsStream("../images/config/bntDataConectado_48.png")));
				imgViewConexao.setFitWidth(48);
				imgViewConexao.setFitHeight(48);
			} else {
				
				ImageView img = new ImageView(new Image(getClass().getResourceAsStream("../images/config/bntSinalizaMarcada_48.png")));
				img.setFitWidth(20);
				img.setFitHeight(20);
				
				pnImgAviso.getChildren().add(img);
				pnImgAviso.setVisible(true);
				lblAviso.setText("Não foi possivel conectar ao banco, verifique os dados de conexão!");
				lblAviso.setVisible(true);
				
				imgViewConexao.setImage(new Image(getClass().getResourceAsStream("../images/config/bntDataSemConexao_48.png")));
				imgViewConexao.setFitWidth(48);
				imgViewConexao.setFitHeight(48);
			}
		}
	}
	
	@FXML
	public void onBtnCancelarClick() {
		if (Alertas.Confirmacao("Sair", "", "")) {
			System.exit(0);
		}
	}
	
	@FXML
	public void onBtnConfirmarClick() {
		
	}
	
	@FXML
	public void onTxtPortaEventAction() {
		Constraints.setTxtFieldPort(txtIP);
	}
	
	public void onTxtIPExitAction() {
		if (txtIP.textProperty().get().toString().isEmpty()) {
			txtIP.setStyle("");
		} else {
			if (Constraints.validaIp(txtIP)) {
				txtIP.setStyle("-fx-border-color: green;");
			} else {
				txtIP.setStyle("-fx-border-color: red;");
			}
		}
	}
	
	
	public Boolean validaCampos() {
		Boolean result = false;
		
		if (!txtIP.getText().isEmpty() 		&& !txtPorta.getText().isEmpty() &&
		    !txtUsuario.getText().isEmpty() && !pswSenha.getText().isEmpty()) {
			result = true;
		} else {
			if (txtIP.getText().isEmpty()) {
				txtIP.setStyle("-fx-border-color: red;");
			}
			
			if (txtPorta.getText().isEmpty()) {
				txtPorta.setStyle("-fx-border-color: red;");
			}
			
			if (txtUsuario.getText().isEmpty()) {
				txtUsuario.setStyle("-fx-border-color: red;");
			}
			
			if (pswSenha.getText().isEmpty()) {
				pswSenha.setStyle("-fx-border-color: red;");
			}	
		}	
		return result;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Constraints.setTxtFieldPort(txtPorta);
		
		txtIP.focusedProperty().addListener(new ChangeListener<Boolean>(){
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
		        if (!newPropertyValue) {
		        	 onTxtIPExitAction();
		        } else {
		        	txtIP.setStyle("");
		        }
		    }
		});
		
		txtPorta.focusedProperty().addListener(new ChangeListener<Boolean>(){
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
		        if (txtPorta.textProperty().get().toString().isEmpty()) {
		        	txtPorta.setStyle("");
		        } else {
		        	txtPorta.setStyle("-fx-border-color: green;");
		        }
		    }
		});
		
		txtUsuario.focusedProperty().addListener(new ChangeListener<Boolean>(){
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
		    	if (txtUsuario.textProperty().get().toString().isEmpty()) {
		    		txtUsuario.setStyle("");
		        } else {
		        	txtUsuario.setStyle("-fx-border-color: green;");
		        }
		    }
		});
		
		pswSenha.focusedProperty().addListener(new ChangeListener<Boolean>(){
		    @Override
		    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue){
		        if (pswSenha.textProperty().get().toString().isEmpty()) {
		        	pswSenha.setStyle("");
		        } else {
		        	pswSenha.setStyle("-fx-border-color: green;");
		        }
		    }
		});
	}

}
