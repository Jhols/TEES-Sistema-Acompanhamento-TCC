package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import enums.BancoTabela;
import enums.Perfil;

import model.Pessoa;
import model.PessoaFactory;
import util.ConnectionFactory;

public class PessoaDAO {
	
	private static PessoaDAO uniqueInstance; //Singleton
	
	private PessoaDAO() { }
	
	public static synchronized PessoaDAO getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new PessoaDAO();
		return uniqueInstance;
	}
	
	@SuppressWarnings("finally")
	public Pessoa findById(Perfil perfil, int idPessoa) {
		ResultSet resultado = null;
		String sql;
		Pessoa pessoa = null;
		
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		sql = "SELECT * FROM pessoa WHERE id_pessoa = " + idPessoa + ";";
		System.out.println(sql);
		
		Statement stm = null;
		try {
			stm = conexao.createStatement();
			resultado = stm.executeQuery(sql);
			if (resultado.next())
				pessoa = PessoaFactory.getPessoa(perfil,resultado);
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			try {resultado.close();}catch(SQLException e){e.printStackTrace();}
			try {stm.close();}catch(SQLException e){e.printStackTrace();}
			try {conexao.close();}catch(SQLException e){e.printStackTrace();}
		}
		return pessoa;
	}

	// Realiza e retorna uma consulta no banco de dados por uma pessoa que tenha um determinado perfil e determinado ID
	@SuppressWarnings("finally")
	public ResultSet selecionarPorPerfilEId(Perfil perfil, int idPessoa) {
		
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
		
		sql = "SELECT * FROM " + perfil + " INNER JOIN " + BancoTabela.PESSOA 
				+ " ON " + perfil +".id_pessoa = " +  BancoTabela.PESSOA + ".id_pessoa "
				+ " WHERE " + perfil+".id_"+perfil + " = " + id;
				
		
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
	
	
	//adiciona uma pessoa 
	public int addPessoa(Pessoa pessoa) {
		
		
		String sql;
		
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		sql = "INSERT INTO " + BancoTabela.PESSOA + "(nome,email,telefone) values (?,?,?)";
						

		 try {
	        	PreparedStatement  prepareStatement = conexao.prepareStatement(sql);
	        			
	            prepareStatement.setString(1, pessoa.getNome());
	            prepareStatement.setString(2, pessoa.getEmail());
	            prepareStatement.setString(3, pessoa.getTelefone());
	                        
	            prepareStatement.executeUpdate();
	            
	            sql="SELECT currval(pg_get_serial_sequence('pessoa','id_pessoa'))";
	            Statement stm = conexao.createStatement();
	            
	            ResultSet resultado = stm.executeQuery(sql);
	            resultado.next();
	            int id = resultado.getInt("currval");//pega o id da pessoa inserida
	            
	            return id;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		 return 0;
		
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