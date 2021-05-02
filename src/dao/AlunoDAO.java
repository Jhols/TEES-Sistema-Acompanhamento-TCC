package dao;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import enums.BancoTabela;
import enums.Perfil;
import model.Aluno;
import model.Pessoa;
import model.PessoaFactory;
import model.Professor;
import util.ConnectionFactory;

public class AlunoDAO {
	
	private static AlunoDAO uniqueInstance; //Singleton
	
	private AlunoDAO() { }
	
	public static synchronized AlunoDAO getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new AlunoDAO();
		return uniqueInstance;
	}
	
	@SuppressWarnings("finally")
	public Aluno findById(int id) {
		Pessoa aluno = PessoaFactory.getPessoa(Perfil.ALUNO);
		
		ResultSet resultado = PessoaDAO.getInstance().selecionarPorPerfil(BancoTabela.ALUNO, id);
		
		try {
			resultado.next();
			aluno.setId(id);
			aluno.setNome(resultado.getString(BancoTabela.PESSOA+".nome"));
			((Aluno) aluno).setMatricula(resultado.getString(BancoTabela.ALUNO+".matricula"));
			aluno.setEmail(resultado.getString(BancoTabela.PESSOA+".email"));
			aluno.setTelefone(resultado.getString(BancoTabela.PESSOA+".telefone"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {resultado.close();}catch(SQLException e){e.printStackTrace();}
			return (Aluno) aluno;
		}
		
	}
	
	@SuppressWarnings("finally")
	public Aluno findByMatricula(String matricula) {
		Pessoa aluno = PessoaFactory.getPessoa(Perfil.ALUNO);
		
		ResultSet resultado = null;
		String sql;
		
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		sql = "SELECT * FROM " + BancoTabela.ALUNO + " INNER JOIN " + BancoTabela.PESSOA +
				" WHERE "+BancoTabela.ALUNO+".matricula = '" + matricula + "'" + 
				" AND "+BancoTabela.ALUNO+".id_pessoa = "+BancoTabela.PESSOA +".id_pessoa;";
		
		try {
            Statement stm = conexao.createStatement();
            resultado = stm.executeQuery(sql);
            
            resultado.next();
			aluno.setId(resultado.getInt(BancoTabela.ALUNO+".id_"+BancoTabela.ALUNO.toString().toLowerCase()));
			((Aluno)aluno).setIdAluno(resultado.getInt(BancoTabela.ALUNO+".id_"+BancoTabela.ALUNO.toString().toLowerCase()));
			aluno.setNome(resultado.getString(BancoTabela.PESSOA+".nome"));
			((Aluno) aluno).setMatricula(resultado.getString(BancoTabela.ALUNO+".matricula"));
			aluno.setEmail(resultado.getString(BancoTabela.PESSOA+".email"));
			aluno.setTelefone(resultado.getString(BancoTabela.PESSOA+".telefone"));
			
			System.out.println("Aluno encontrado por matricula: id: " + aluno.getId() + " " + ((Aluno)aluno).toString());
			stm.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {resultado.close();}catch(SQLException e){e.printStackTrace();}
			try {conexao.close();}catch(SQLException e){e.printStackTrace();}
			return (Aluno) aluno;
		}
		
	}
	
	//Consulta todos os alunos no banco de dados e os inclui numa lista a ser retornada.
	@SuppressWarnings("finally")
	public ArrayList<Aluno> pesquisarTodosAlunos() {
		ArrayList<Aluno> alunos = new ArrayList<>();
		
		ResultSet resultado = PessoaDAO.getInstance().selecionarPorPerfil(BancoTabela.ALUNO); //Realiza a consulta no banco
		
		try {
			while(resultado.next()) { //Atribui os valores encontrados em uma lista de objetos de alunos
				Aluno aluno = ((Aluno) PessoaFactory.getPessoa(Perfil.ALUNO, resultado));
				aluno.setMatricula(resultado.getString(BancoTabela.ALUNO + ".matricula"));
				alunos.add(aluno);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {resultado.close();}catch(SQLException e){e.printStackTrace();} //Fecha o resultado da conexao
			return alunos;			
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
	
	@SuppressWarnings("finally")
	public static Aluno pesquisarAlunoPorIdPessoa(int idPessoa) {
		Aluno aluno = null;
		ResultSet resultado = PessoaDAO.selecionarPorPerfilEId(Perfil.ALUNO, idPessoa);
		
		try {
			if (resultado.next()) {
				aluno = ((Aluno) PessoaFactory.getPessoa(Perfil.ALUNO, resultado));
				// TODO: selecionar projetos do BD para preencher a lista de projetos do professor
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return aluno;			
		}
	}
	

	@SuppressWarnings("finally")
	public static Aluno pesquisarAlunoPorIdAluno(int idAluno) {
		
		Aluno aluno = null;
		
		try {
			var connection = ConnectionFactory.getConnection();
			String sql = "Select * from " + BancoTabela.ALUNO 
					+ " where " + BancoTabela.ALUNO +".id_aluno = ? ";
			
			PreparedStatement stm =  connection.prepareStatement(sql);
			stm.setInt(1, idAluno);
			var resultado = stm.executeQuery();
			
			if (resultado.next()) {
				int idPessoa = resultado.getInt(BancoTabela.ALUNO+".id_pessoa");
				aluno = pesquisarAlunoPorIdPessoa(idPessoa);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return aluno;			
		}
		
	}
	
}
