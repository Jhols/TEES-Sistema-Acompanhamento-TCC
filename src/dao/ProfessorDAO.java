package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
	
	public ProfessorDAO() { }
	
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
			professor.setId(resultado.getInt("id_pessoa"));
			((Professor)professor).setIdProfessor(idProfessor);
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
		ResultSet resultado = PessoaDAO.getInstance().selecionarPorPerfilEId(Perfil.PROFESSOR, idPessoa);
		
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
	
	public static void mudarStatusOrientador(int idProfessor) {
		
		
		try {
			Connection connection = ConnectionFactory.getConnection();
			
			String sql = "UPDATE " +BancoTabela.PROFESSOR+" SET status_orientador = 0"+
					" WHERE id_professor = " + idProfessor;
			PreparedStatement stm = connection.prepareStatement(sql);
			stm.executeQuery();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int incluirProfessor(Professor professor) {
		int id = 0;
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
        	
        	id=PessoaDAO.getInstance().addPessoa(professor);
        			
            prepareStatement.setInt(1, id);
            prepareStatement.setString(2, professor.getMatricula());
            prepareStatement.setInt(3, 0);
            prepareStatement.setInt(4, 3);
            
            prepareStatement.executeUpdate();
            
            sql = "INSERT INTO "+ BancoTabela.PERFIL_PESSOA + "(id_pessoa, id_perfil) values (?,?)";
            prepareStatement = conexao.prepareStatement(sql);
            
            prepareStatement.setInt(1, id);
            prepareStatement.setInt(2, 4);
            
            prepareStatement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
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
        	
        	int id=PessoaDAO.getInstance().addPessoa(professor);
        			
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

	public static void atualizaProfessorOrientador(Professor  professor) {
		
		String sql;
		
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	
		sql = "UPDATE  " + BancoTabela.PESSOA + " SET  nome=?, email=?, telefone=? WHERE " + 
				 BancoTabela.PESSOA + ".id_pessoa = ?";
		
        try {
        	PreparedStatement  prepareStatement = conexao.prepareStatement(sql);
        	
        	int id_pessoa= professor.getId();
        	System.out.println("PROFESSOR ATULIZADO");
        	System.out.println(professor);
        	
            prepareStatement.setString(1, professor.getNome());
            prepareStatement.setString(2, professor.getEmail());
            prepareStatement.setString(3, professor.getTelefone());
            prepareStatement.setInt(4, id_pessoa);
            prepareStatement.executeUpdate();
            
            System.out.println(professor.getNome()+"chegou aqui!");
            sql = "UPDATE " + BancoTabela.PROFESSOR + " SET matricula=? WHERE " +
   				 BancoTabela.PROFESSOR + ".id_pessoa = ?";
            
   			prepareStatement = conexao.prepareStatement(sql);
   			prepareStatement.setString(1, professor.getMatricula());
   			prepareStatement.setInt(2, id_pessoa);
            prepareStatement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	@SuppressWarnings("finally")
	public ArrayList<Professor> pesquisarCandidatosOrientador() {
		ArrayList<Professor> professores = new ArrayList<>();
		ResultSet resultado = null;
		String sql;
		try {
			Connection con = ConnectionFactory.getConnection();
			sql = " SELECT * FROM "+BancoTabela.PROFESSOR 
					+ " INNER JOIN "
					+ BancoTabela.PESSOA + " ON " + BancoTabela.PESSOA+".id_pessoa = " + BancoTabela.PROFESSOR + ".id_pessoa"
					+ " WHERE " + BancoTabela.PROFESSOR + ".status_orientador = 0";
			
			var stm = con.prepareStatement(sql);
			resultado = stm.executeQuery();
			while (resultado.next()) {
				Professor professor = ((Professor) PessoaFactory.getPessoa(Perfil.PROFESSOR, resultado));
				professores.add(professor);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {resultado.close(); }catch(SQLException e){e.printStackTrace();}
			return professores;			
		}
	}
	public ArrayList<Professor> pesquisarProfessoresTCC() {
		ArrayList<Professor> professores = new ArrayList<Professor>();
		
		try {
			Connection conexao = ConnectionFactory.getConnection();
			String sql = "SELECT * FROM " + BancoTabela.PROFESSOR + " WHERE " + 
					 BancoTabela.PROFESSOR + ".tipo_prof IN (0, 2)";
			var stm = conexao.createStatement();
			ResultSet resultado = stm.executeQuery(sql);
			while (resultado.next()) {
				var id_pessoa = resultado.getInt("id_pessoa");
				Professor professor = pesquisarProfessorPorIdPessoa(id_pessoa);
				professores.add(professor);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return professores;
		
	}
	
	
	public void alterarStatusCandidatoOrientador(Professor professor, String acao, String tipoAntigo) {
		Connection conexao = null;
		PreparedStatement stm = null;
		String sql = "";
		try {
			conexao = ConnectionFactory.getConnection();
			
			// Inicio da Transacao
			conexao.setAutoCommit(false);
			
			sql = " UPDATE " + BancoTabela.PROFESSOR 
					+ " SET "
					+ " status_orientador = " + Professor.toInt(professor.getStatusOrientador()) + ","
					+ " tipo_prof = " + Professor.toInt(professor.getTipo())
					+ " WHERE " + BancoTabela.PROFESSOR + ".id_professor = " + professor.getIdProfessor();
			stm = conexao.prepareStatement(sql);
			stm.executeUpdate();
			
			if (acao.equals("aceitar_candidatura") && tipoAntigo.equals("PROFESSOR")) {
				LoginDAO.getInstance().addLogin(professor.getId(), professor.getEmail() , "1234");	
			}
			
			// Fim da transacao
			conexao.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			try { conexao.rollback(); } catch (SQLException e1) { e1.printStackTrace(); } ;
		}  finally {  
			try { stm.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { conexao.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
	}
}