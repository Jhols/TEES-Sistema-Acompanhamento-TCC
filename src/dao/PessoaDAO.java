package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import enums.BancoTabela;
import enums.Perfil;
import util.ConnectionFactory;

public class PessoaDAO {
	
	// Realiza e retorna uma consulta no banco de dados por uma pessoa que tenha um determinado perfil e determinado ID
		@SuppressWarnings("finally")
		public static ResultSet selecionarPorPerfilEId(Perfil perfil, int idPessoa) {
			
			ResultSet resultado = null;
			String sql;
			
			Connection conexao = null;
			try {
				conexao = ConnectionFactory.getConnection();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			sql = "SELECT * FROM " + Perfil.getTabela(perfil) + " inner join " + BancoTabela.PESSOA + 
					" ON " + Perfil.getTabela(perfil) +".id_pessoa = " +  BancoTabela.PESSOA + ".id_pessoa" +
					" WHERE " + Perfil.getTabela(perfil) + ".id_pessoa = " + idPessoa;
			
			try {
	            Statement stm = conexao.createStatement();
	            resultado = stm.executeQuery(sql);
			}
			catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			finally {
				return resultado;
			}
			
		}
		
	
	// Realiza e retorna uma consulta no banco de dados por uma pessoa que tenha um determinado perfil
	@SuppressWarnings("finally")
	public static ResultSet selecionarPorPerfil(BancoTabela tabela) {
		
		ResultSet resultado = null;
		String sql;
		
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		sql = "SELECT * FROM " + tabela + " inner join " + BancoTabela.PESSOA + 
				" ON " + tabela +".id_pessoa = " +  BancoTabela.PESSOA + ".id_pessoa;";
		
		try {
            Statement stm = conexao.createStatement();
            resultado = stm.executeQuery(sql);
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			return resultado;
		}
		
	}
	
}
