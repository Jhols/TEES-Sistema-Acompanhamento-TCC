package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConnectionFactory {

	// Cria uma conexão com o banco de dados.
	@SuppressWarnings("finally")
	public static Connection getConnection() throws SQLException {
		Connection con = null;
		
		 String user = "postgres";
         String password = "0715";
         //Class.forName("com.mysql.jdbc.Driver"); // Para quem for usar MySql
		try {
			Class.forName("org.postgresql.Driver");// Para quem for usar Postgres
	         //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetoIntegrador", user, password);// Para quem for usar MySql
	         con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tees_acompanhamento_tcc",user, password);// Para quem for usar Postgres
	         System.out.println("Connection created: "+con);
		}
		catch(SQLException e) {
			System.out.println("ERRO SQL: " + e.getMessage());
			e.printStackTrace();
		}
		catch(Exception e) {
			System.out.println("ERRO FATAL: " + e.getMessage());
			e.printStackTrace();
		}
		catch (Throwable e) {
			System.out.println("ERRO FATAL: " + e.getMessage());
			e.printStackTrace();
		}
		finally {
			return con;
		}
	}
	
}
