package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import alerts.Alertas;
import javafx.scene.control.ChoiceBox;

public class ConexaoMySQL {
	
	public static Boolean testaConexaoMySQL(String server, String port, String dataBase, 
			  								  String user, String psswd, ChoiceBox<String> checkBoxBase){
		  Connection connection = null;
		  Boolean conecta = false;
		  try {
			  
			  if (dataBase.equals("MySQL")) {
				  String driverName = "com.mysql.jdbc.Driver";  
				  Class.forName(driverName);
				  
				  String url = "jdbc:mysql://" + server + ":" + port; 
				  connection = DriverManager.getConnection(url, user, psswd);
				  
				  PreparedStatement pst = connection.prepareStatement("SELECT schema_name FROM information_schema.SCHEMATA "
				  													+ " WHERE schema_name NOT IN (?, ?, ?, ?)");
				  pst.setString( 1, "mysql");
				  pst.setString( 2, "performance_schema");
				  pst.setString( 3, "information_schema");
				  pst.setString( 4, "sys");
				  ResultSet rs = pst.executeQuery();
				  
				  if (checkBoxBase != null) {
					  while(rs.next())  {
						  checkBoxBase.getItems().add(rs.getString(1));
					  } 
					  checkBoxBase.setDisable(false);
					  checkBoxBase.getSelectionModel().select(0);
				  }
			  }
			  
			  if (connection != null) {
				  conecta = true;
			  } else {
				  conecta = false;
			  }
			  
			  connection.close();
			  
		  } catch (ClassNotFoundException e) {  //Driver não encontrado
	            System.out.println("O driver de conexão expecificado nao foi encontrado.");
	            e.printStackTrace();
	      } catch (SQLException e) {
	            System.out.println("Nao foi possivel conectar ao Banco de Dados.");
	      }
		  
		  return conecta;
	  }
}
