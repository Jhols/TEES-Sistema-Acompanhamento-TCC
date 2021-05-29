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
		

		try {
			
			Connection conexao = ConnectionFactory.getConnection();
			sql = "SELECT * FROM login  where login.login = '" + login + "' and login.senha = '" + senha + "'";
			System.out.println(sql);
			System.out.println("1");
            Statement stm = conexao.createStatement();
            System.out.println("2");
            resultado = stm.executeQuery(sql);
            System.out.println("3");
            if(resultado.next()) {
            	System.out.println("4");
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
						pessoa = PessoaDAO.getInstance().findById(perfil, idPessoa);
						break;
					case ALUNO:
						pessoa = AlunoDAO.getInstance().pesquisarAlunoPorIdPessoa(idPessoa);
						break;
					case COORDENADOR:
						break;
					case NENHHUM:
						break;
					case PROFESSOR:
						pessoa = ProfessorDAO.getInstance().pesquisarProfessorPorIdPessoa(idPessoa);
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
            else {
            	System.out.println("Erro: não foi possivel encontrar o login desejado");
            }
			
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		catch (Exception e) {
			System.out.println("ERROR: "+e.getMessage());
			e.printStackTrace();
		}
		finally {
			System.out.println("return "+pessoa);
			return pessoa;
		}
		
	}
}