package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConnectionFactory {

	// Cria uma conexao com o banco de dados.
	@SuppressWarnings("finally")
	public static Connection getConnection() throws SQLException {
		Connection conexao = null;
		
		/*
		 String user = "postgres";
         String password = "0715";
         */
         //Class.forName("com.mysql.jdbc.Driver"); // Para quem for usar MySql
		
		//Credenciais do Heroku
		String host = "ec2-3-234-22-132.compute-1.amazonaws.com";
		String porta = "5432";
		String banco = "d5jo329bjo4mrh";
		String user = "omhgzdnlfuipun";
        String password = "1900287b3d049828026a8a18fadb569a79e0f373dd8cfe0fb1c994634d3c7217";
        
		try {
			Class.forName("org.postgresql.Driver");// Para quem for usar Postgres
	         //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetoIntegrador", user, password);// Para quem for usar MySql
	        /* 
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tees_acompanhamento_tcc",user, password);// Para quem for usar Postgres
	         System.out.println("Connection created: "+con);
	         */
			conexao = DriverManager.getConnection("jdbc:postgresql://"+host+":"+porta+"/"+banco, user, password);// Para quem for usar Postgres
	        System.out.println("Connection created: "+conexao);
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
			return conexao;
		}
	}
	
}