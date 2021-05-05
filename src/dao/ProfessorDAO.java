package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import enums.BancoTabela;
import enums.Perfil;
import model.Professor;
import util.ConnectionFactory;
import model.Aluno;
import model.Pessoa;
import model.PessoaFactory;

public class ProfessorDAO {
	
	private static ProfessorDAO uniqueInstance; //Singleton
	
	private ProfessorDAO() { }
	
	public static synchronized ProfessorDAO getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new ProfessorDAO();
		return uniqueInstance;
	}
	
	@SuppressWarnings("finally")
	public Professor findById(int idProfessor) {
		Pessoa professor = PessoaFactory.getPessoa(Perfil.PROFESSOR);
		
		ResultSet resultado = PessoaDAO.getInstance().selecionarPorPerfil(BancoTabela.PROFESSOR, idProfessor);
		
		try {
			resultado.next();
			professor.setNome(resultado.getString(BancoTabela.PESSOA+".nome"));
			((Aluno) professor).setMatricula(resultado.getString(BancoTabela.PROFESSOR+".matricula"));
			professor.setEmail(resultado.getString(BancoTabela.PESSOA+".email"));
			professor.setTelefone(resultado.getString(BancoTabela.PESSOA+".telefone"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {resultado.close();}catch(SQLException e){e.printStackTrace();}
			return (Professor) professor;
		}
	}

	// busca um professor a partir do seu id_pessoa
	@SuppressWarnings("finally")
	public static Professor pesquisarProfessorPorIdPessoa(int idPessoa) {
		Professor professor = null;
		ResultSet resultado = PessoaDAO.selecionarPorPerfilEId(Perfil.PROFESSOR, idPessoa);
		
		try {
			if (resultado.next()) {
				professor = ((Professor) PessoaFactory.getPessoa(Perfil.PROFESSOR, resultado));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return professor;			
		}
		
	}
	
	// busca a lista de todos os professores no banco
	@SuppressWarnings("finally")
	public ArrayList<Professor> pesquisarTodosProfessores() {
		ArrayList<Professor> professores = new ArrayList<>();
		ResultSet resultado = PessoaDAO.getInstance().selecionarPorPerfil(BancoTabela.PROFESSOR);
		
		try {
			while(resultado.next()) {
				Professor professor = ((Professor) PessoaFactory.getPessoa(Perfil.PROFESSOR, resultado));
				professor.setMatricula(resultado.getString(BancoTabela.PROFESSOR + ".matricula"));
				
				// TODO: selecionar projetos do BD para preencher a lista de projetos do professor
				
				professores.add(professor);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {resultado.close();}catch(SQLException e){e.printStackTrace();}
			return professores;			
		}
		
	}
	
	// busca um professor a partir de seu id_professor
	public static Professor pesquisarPorIdProfessor(int idProfessor) {
		Professor professor = null;
		
		try {
			Connection con = ConnectionFactory.getConnection();
			String sql = "Select * from "+BancoTabela.PROFESSOR + " inner join "
					+BancoTabela.PESSOA + " on "+BancoTabela.PESSOA+".id_pessoa = "+BancoTabela.PROFESSOR+".id_pessoa"
					+ " where " + BancoTabela.PROFESSOR + ".id_professor = ?";
			
			var stm = con.prepareStatement(sql);
			stm.setInt(1, idProfessor);
			var resultado = stm.executeQuery();
			if (resultado.next()) {
				professor = ((Professor) PessoaFactory.getPessoa(Perfil.PROFESSOR, resultado));
				
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return professor;
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