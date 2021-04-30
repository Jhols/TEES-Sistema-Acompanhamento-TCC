package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
	
	private static AlunoDAO uniqueInstance = null;
	
	private AlunoDAO() {
		
	}
	
	public static synchronized AlunoDAO getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new AlunoDAO();
		return uniqueInstance;
	}
	
	//Consulta todos os alunos no banco de dados e os inclui numa lista a ser retornada.
	@SuppressWarnings("finally")
	public  ArrayList<Aluno> pesquisarTodosAlunos() {
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
			return alunos;			
		}
	}
	
	@SuppressWarnings("finally")
	public  Aluno pesquisarAlunoPorIdPessoa(int idPessoa) {
		Aluno aluno = null;
		ResultSet resultado = PessoaDAO.getInstance().selecionarPorPerfilEId(Perfil.ALUNO, idPessoa);
		
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
	public  Aluno pesquisarAlunoPorIdAluno(int idAluno) {
		
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

	//Pesquisa o aluno pela matrícula
	public Pessoa findByMatricula(String matricula) {
		Pessoa aluno = null;
		String sql = null;
		
		
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		sql = "SELECT * FROM "+ BancoTabela.ALUNO +" INNER JOIN "+ BancoTabela.PESSOA
				+" WHERE matricula = "+ matricula
				+" AND "+ BancoTabela.ALUNO+".id_pessoa = "+  BancoTabela.PESSOA+".id_pessoa;";
		
		ResultSet resultado = null;
		Statement stm = null;
		try {
			stm = conexao.createStatement();
			resultado = stm.executeQuery(sql);
			
			if (resultado.next()) {
				aluno = PessoaFactory.getPessoa(Perfil.ALUNO);
				
				aluno.setId(resultado.getInt(BancoTabela.ALUNO+ ".id_aluno"));
				aluno.setNome(resultado.getString(BancoTabela.PESSOA+ ".nome"));
				aluno.setEmail(resultado.getString(BancoTabela.PESSOA+".email"));
				aluno.setTelefone(resultado.getString(BancoTabela.PESSOA+".telefone"));;
				((Aluno) aluno).setMatricula(resultado.getString(BancoTabela.ALUNO+".matricula"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {resultado.close();}catch(SQLException e){e.printStackTrace();}
			try {stm.close();}catch(SQLException e){e.printStackTrace();}
			try {conexao.close();}catch(SQLException e){e.printStackTrace();}
		}
		
		return null;
	}
	
}
