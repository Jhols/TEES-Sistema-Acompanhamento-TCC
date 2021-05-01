package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class ConnectionFactory {

	// Cria uma conexão com o banco de dados.
	@SuppressWarnings("finally")
	public static Connection getConnection() throws SQLException {
		Connection con = null;
		
		String porta = "3306";
		String schema = "tees_acompanhamento_tcc";
		String servidor = "jdbc:mysql://localhost:" + porta + "/" + schema;
		String usuario = "root";
		String senha = "123456";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(servidor, usuario, senha);
		}
		catch(SQLException e) {
			System.out.println("ERRO FATAL: " + e.getMessage());
		}
		finally {
			return con;
		}
	}
	
}
