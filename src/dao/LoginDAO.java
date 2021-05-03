package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import enums.Perfil;
import model.Pessoa;
import util.ConnectionFactory;

public class LoginDAO {

	private static LoginDAO uniqueInstance; //Singleton
	
	private LoginDAO() { }
	
	public static synchronized LoginDAO getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new LoginDAO();
		return uniqueInstance;
	}
	
	// pesquisa Pessoa no banco a partir de sua informacao de login (login e senha)
	// preenche os dados dos objetos filhos (aluno, professor, etc) de acordo com o perfil encontrado
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
            	System.out.println("id encontrado " + idPessoa);
            	sql = "SELECT * FROM perfil_pessoa where id_pessoa = " + idPessoa;
            	//System.out.println(sql);
            	stm = conexao.createStatement();
                resultado = stm.executeQuery(sql);
                
                if(resultado.next()) {
                	var perfil = Perfil.fromId(resultado.getInt("id_perfil"));
                	System.out.println("Perfil = "+perfil);
                	switch (perfil) {
					case ADMINISTRADOR:
						break;
					case ALUNO:
						pessoa = AlunoDAO.pesquisarAlunoPorIdPessoa(idPessoa);
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
                else {
                	System.out.println("Erro: perfil n�o encontrado para pessoa id = "+idPessoa);
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