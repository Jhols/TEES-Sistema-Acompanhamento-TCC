package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import enums.BancoTabela;
import model.Login;
import util.ConnectionFactory;

public class LoginDAO {

	@SuppressWarnings("finally")
	public static ResultSet pesquisaLogin(String login, String senha) {
		ResultSet resultado = null;
		String sql;
		
		//conectarBanco();
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		sql = "SELECT * FROM login  where login.login = '" + login + "' and login.senha = '" + senha + "'";
		System.out.println(sql);
		
		try {
            Statement stm = conexao.createStatement();
            resultado = stm.executeQuery(sql);
            if(resultado.next()) {
            	int idPessoa=resultado.getInt("pessoa_id");
            	System.out.print(idPessoa);
            }
            
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			return resultado;
		}
		
	}
}
