package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import enums.BancoTabela;
import enums.Perfil;
import model.Aluno;
import model.PessoaFactory;
import model.Professor;
import util.ConnectionFactory;

public class AlunoDAO {
	
	//Consulta todos os alunos no banco de dados e os inclui numa lista a ser retornada.
	@SuppressWarnings("finally")
	public static ArrayList<Aluno> pesquisarTodosAlunos() {
		ArrayList<Aluno> alunos = new ArrayList<>();
		ResultSet resultado = PessoaDAO.selecionarPorPerfil(BancoTabela.ALUNO); //Realiza a consulta no banco
		
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
