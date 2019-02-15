package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL {
	
	  public ConexaoMySQL(){
		  
	  }
 
	  public static Boolean testaConexxaoMySQL(String server, String port, String base,
			  								    String user, String psswd){
		  Connection connection = null;
		  Boolean conecta = false;
		  try {
			  String driverName = "com.mysql.jdbc.Driver";  
			  Class.forName(driverName);
			  
			  String url = "jdbc:mysql://" + server + ":" + port + "/" + base;
			  
			  connection = DriverManager.getConnection(url, user, psswd);
			  

			  if (connection != null) {
				  conecta = true;
			  } else {
				  conecta = false;
			  }
			  
			  connection.close();
			  
		  }  catch (ClassNotFoundException e) {  //Driver não encontrado
	            System.out.println("O driver de conexão expecificado nao foi encontrado.");
	      } catch (SQLException e) {
	            System.out.println("Nao foi possivel conectar ao Banco de Dados.");
	      }
		  
		  return conecta;
	  }
}
