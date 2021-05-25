package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import enums.BancoTabela;
import enums.Perfil;
import enums.SituacaoProjeto;
import model.Professor;
import model.Projeto;
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
			professor.setNome(resultado.getString("nome"));
			((Aluno) professor).setMatricula(resultado.getString("matricula"));
			professor.setEmail(resultado.getString("email"));
			professor.setTelefone(resultado.getString("telefone"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return (Professor) professor;
		}
	}

	// busca um professor a partir do seu id_pessoa
	@SuppressWarnings("finally")
	public Professor pesquisarProfessorPorIdPessoa(int idPessoa) {
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
				professor.setMatricula(resultado.getString("matricula"));
				
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

	public static void addProfessor(Professor professor) {
		
		String sql;
		
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		sql = "INSERT INTO " + BancoTabela.PROFESSOR + " (id_pessoa, matricula, tipo_prof, status_orientador) values (?, ?, ?, ? )";
		
		
        try {
        	PreparedStatement  prepareStatement = conexao.prepareStatement(sql);
        	
        	int id=PessoaDAO.addPessoa(professor);
        			
            prepareStatement.setInt(1, id);
            prepareStatement.setString(2, professor.getMatricula());
            prepareStatement.setInt(3, 3);
            prepareStatement.setInt(4, 0);
            
            prepareStatement.executeUpdate();
            
            sql = "INSERT INTO "+ BancoTabela.PERFIL_PESSOA + "(id_pessoa, id_perfil) values (?,?)";
            prepareStatement = conexao.prepareStatement(sql);
            
            prepareStatement.setInt(1, id);
            prepareStatement.setInt(2, 4);
            
            prepareStatement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	public void atualizar() {
		// TODO Auto-generated method stub
		
	}

	public boolean deletar() {
		// TODO Auto-generated method stub
		return false;
	}
}