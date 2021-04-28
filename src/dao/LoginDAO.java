package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import enums.Perfil;
import model.Pessoa;
import util.ConnectionFactory;

public class LoginDAO {

	@SuppressWarnings("finally")
	public static Pessoa pesquisaPessoa(String login, String senha) {
		ResultSet resultado = null;
		String sql;
		Pessoa pessoa = null;
		
		//conectarBanco();
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		sql = "SELECT * FROM login  where login.login = '" + login + "' and login.senha = '" + senha + "'";
		
		try {
            Statement stm = conexao.createStatement();
            resultado = stm.executeQuery(sql);
            if(resultado.next()) {
            	int idPessoa = resultado.getInt("pessoa_id");
            	//System.out.println("id encontrado " + idPessoa);
            	sql = "SELECT * FROM perfil_pessoa where id_pessoa = " + idPessoa;
            	//System.out.println(sql);
            	stm = conexao.createStatement();
                resultado = stm.executeQuery(sql);
                
                if(resultado.next()) {
                	var perfil = Perfil.fromId(resultado.getInt("id_perfil"));
                	switch (perfil) {
					case ADMINISTRADOR:
						break;
					case ALUNO:
						//pessoa = AlunoDAO.pesquisarTodosAlunos()
						break;
					case COORDENADOR:
						break;
					case NENHHUM:
						break;
					case PROFESSOR:
						pessoa = ProfessorDAO.pesquisarProfessorPorIdPessoa(idPessoa);
						break;
					case SECRETARIO:
						break;
					default:
						break;
                	
                	} 
                	
                }
            	
            }
            
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
			return pessoa;
		}
		
	}
}
