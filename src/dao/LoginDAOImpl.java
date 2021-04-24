package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import enums.BancoTabela;
import model.Login;
import model.Pessoa;
import util.ConnectionFactory;

public class LoginDAOImpl implements InterfaceDAO {

	private static LoginDAOImpl uniqueInstance; //Singleton
	
	private LoginDAOImpl() { }
	
	public static synchronized LoginDAOImpl getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new LoginDAOImpl();
		return uniqueInstance;
	}
	
	@SuppressWarnings("finally")
	public String  pesquisaLogin(String login, String senha) {
		ResultSet resultado = null;
		String sql;
		String nome = null;
		
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
            	System.out.println(idPessoa);
            	sql= "SELECT * FROM pessoa where pessoa.id_pessoa = "+idPessoa;
            	System.out.println(sql);
            	stm = conexao.createStatement();
                resultado = stm.executeQuery(sql);
                if(resultado.next()) {
                	System.out.println("Achou linha");
                	nome=resultado.getString("nome");
                	
                }
            	
            }
            
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			return nome;
		}
		
	}

	@Override
	public boolean incluir() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void atualizar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean deletar() {
		// TODO Auto-generated method stub
		return false;
	}
}
