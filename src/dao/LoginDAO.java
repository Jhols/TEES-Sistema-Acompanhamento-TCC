package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import enums.BancoTabela;
import enums.Perfil;

import model.Pessoa;

import util.ConnectionFactory;

public class LoginDAO {

	private static LoginDAO uniqueInstance; //Singleton
	
	public LoginDAO() { }
	
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
						pessoa = PessoaDAO.getInstance().findById(perfil, idPessoa);
						break;
					case NENHHUM:
						break;
					case PROFESSOR:
						pessoa = ProfessorDAO.getInstance().pesquisarProfessorPorIdPessoa(idPessoa);
						break;
					case SECRETARIO:
						pessoa = PessoaDAO.getInstance().findById(perfil, idPessoa);
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
	
	//Procura no banco se o nome de usuario ja existe
	public int existeLogin(String login) {
		int existe = 0; //Inteiro que assume 3 valores: 0- Nenhum texto, 1- Não existe, 2- Existe usuario
		int idLogin = 0; //ID do usuario encontrado no banco.

		String sql = null;
		ResultSet resultado = null;
		Connection conexao = null;
		Statement stm = null;
		
		try {
			if (!login.equals("")) { //Se houver texto em login, ele fara' a consulta ao banco
				conexao = ConnectionFactory.getConnection();
				sql = "SELECT id_login FROM login where login.login = '" + login + "';";
				stm = conexao.createStatement();
				resultado = stm.executeQuery(sql);
				if(resultado.next()) {
					//Consulta no banco se o usuario ja existe e retorna seu id
					idLogin = resultado.getInt("id_login");
				}
				else {
					System.out.println("Erro: não foi possivel encontrar o login desejado");
					idLogin = 0;
				}
				if (idLogin == 0) { //Se o nome de usuário não foi encontrado, deixa disponivel para cadastro 
					existe = 1;
				}
				else { //Senao, nao sera' possivel efetuar o cadastro com o nome de usuario informado. 
					existe = 2;
				}
			}
			else { //Se nao houver texto, nao ha' o que ser feito.
				existe = 0;
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
			if (resultado != null) {try {resultado.close();}catch(SQLException e){e.printStackTrace();}}
			if (stm != null) {try {stm.close();}catch(SQLException e){e.printStackTrace();}}
			if (conexao != null) {try {conexao.close();}catch(SQLException e){e.printStackTrace();}}
		}
		
		return existe;
	}
	
	public boolean addLogin(int id, String usuario, String senha) {
		boolean sucesso = false;
		String sql;
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		sql = "INSERT INTO " + BancoTabela.LOGIN + " (pessoa_id, login, senha) values (?, ?, ? )";
		
		PreparedStatement prepareStatement = null;
        try {
        	conexao.setAutoCommit(false);
        	prepareStatement = conexao.prepareStatement(sql);
        			
            prepareStatement.setInt(1, id);
            prepareStatement.setString(2, usuario);
            prepareStatement.setString(3, senha);
            
            prepareStatement.executeUpdate();
            
            conexao.commit();
            sucesso = true;
        } catch (SQLException e) {
            e.printStackTrace();
            try { conexao.rollback(); } catch (SQLException e1) { e1.printStackTrace();}
        }
        finally {
        	try {prepareStatement.close();} catch (SQLException e) {e.printStackTrace();}
        	try {conexao.close();} catch (SQLException e) {e.printStackTrace();}
        }
        return sucesso;
	}
	
}