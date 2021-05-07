package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import enums.BancoTabela;
import enums.Perfil;
import util.ConnectionFactory;

public class PessoaDAO {
	
	private static PessoaDAO uniqueInstance; //Singleton
	
	private PessoaDAO() { }
	
	public static synchronized PessoaDAO getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new PessoaDAO();
		return uniqueInstance;
	}

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
		System.out.println(sql);
		
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
	public ResultSet selecionarPorPerfil(BancoTabela perfil) {
		
		ResultSet resultado = null;
		String sql;
		
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		sql = "SELECT * FROM " + perfil + " inner join " + BancoTabela.PESSOA + 
				" WHERE " + perfil +".id_pessoa = " +  BancoTabela.PESSOA + ".id_pessoa;";
		
		try {
            Statement stm = conexao.createStatement();
            resultado = stm.executeQuery(sql);
            stm.close();
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			try {resultado.close();}catch(SQLException e){e.printStackTrace();}
			try {conexao.close();}catch(SQLException e){e.printStackTrace();}
			return resultado;
		}
		
	}
	
	//Consulta e retorna uma pessoa de determinado id com base em seu perfil.
	//OBS: O ID � o que faz parte da classe filha e n�o da classe Pessoa. 
	@SuppressWarnings("finally")
	public ResultSet selecionarPorPerfil(BancoTabela perfil, int id) {
		
		ResultSet resultado = null;
		String sql;
		
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		sql = "SELECT * FROM " + perfil + " INNER JOIN " + BancoTabela.PESSOA + 
				" WHERE " + perfil+".id_"+perfil + " = " + id + 
				" AND " + perfil +".id_pessoa = " +  BancoTabela.PESSOA + ".id_pessoa;";
		
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

	public boolean incluir() {
		// TODO Auto-generated method stub
		return false;
	}

	public void atualizar() {
		// TODO Auto-generated method stub
		
	}

	public boolean deletar() {
		// TODO Auto-generated method stub
		return false;
	}
	
}