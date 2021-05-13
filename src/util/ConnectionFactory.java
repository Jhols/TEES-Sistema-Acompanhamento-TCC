package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConnectionFactory {
	
	private static Connection conexao;
	
	// Cria uma conexao com o banco de dados.
	@SuppressWarnings("finally")
	public static Connection getConnection() throws SQLException {
		/*
		if (conexao != null) {
			return conexao;
		}
		Connection conexao = null;
		
		//Conexao pela variavel de ambiente dentro do Heroku
		String dbUrl = System.getenv("JDBC_DATABASE_URL");
	    return DriverManager.getConnection(dbUrl);
		*/
		
		
		//Credenciais do Heroku
		
		String host = "ec2-34-206-8-52.compute-1.amazonaws.com";
		String porta = "5432";
		String banco = "dffootpr6lpl0b";
		String user = "ihprytmqpknust";
        String password = "25312cd238db5bd42777dbc6c4de373c4255eea1f564e643b21e6a1b18b62fde";
        
		/*
		String host = "localhost";
		String porta = "5432";
		String banco = "postgres";
		String user = "postgres";
        String password = "123456";
		*/
        //Class.forName("com.mysql.jdbc.Driver"); // Para quem for usar MySql
		
	    try {
			Class.forName("org.postgresql.Driver");// Para quem for usar Postgres
	         //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetoIntegrador", user, password);// Para quem for usar MySql
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