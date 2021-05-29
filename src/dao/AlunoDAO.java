package dao;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import enums.BancoTabela;
import enums.Perfil;
import enums.SituacaoProjeto;
import model.Aluno;
import model.Pessoa;
import model.PessoaFactory;
import model.Professor;
import util.ConnectionFactory;

public class AlunoDAO {
	
	private static AlunoDAO uniqueInstance; //Singleton
	
	public AlunoDAO() { }
	
	public static synchronized AlunoDAO getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new AlunoDAO();
		return uniqueInstance;
	}
	
	@SuppressWarnings("finally")
	public Aluno findById(int idAluno) {
		Pessoa aluno = PessoaFactory.getPessoa(Perfil.ALUNO);
		
		ResultSet resultado = PessoaDAO.getInstance().selecionarPorPerfil(BancoTabela.ALUNO, idAluno);
		
		try {
			resultado.next();
			aluno.setId(idAluno);
			aluno.setNome(resultado.getString("nome"));
			((Aluno) aluno).setMatricula(resultado.getString("matricula"));
			aluno.setEmail(resultado.getString("email"));
			aluno.setTelefone(resultado.getString("telefone"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
		
		sql = "SELECT * FROM " + BancoTabela.ALUNO + " INNER JOIN " + BancoTabela.PESSOA  
				+ " ON " + BancoTabela.ALUNO +".id_pessoa = "+ BancoTabela.PESSOA + ".id_pessoa "
				+ " WHERE "+BancoTabela.ALUNO+".matricula = '" + matricula + "'";
		
		try {
            Statement stm = conexao.createStatement();
            resultado = stm.executeQuery(sql);
            
            resultado.next();
			aluno.setId(resultado.getInt("id_"+BancoTabela.ALUNO.toString().toLowerCase()));
			((Aluno)aluno).setIdAluno(resultado.getInt("id_"+BancoTabela.ALUNO.toString().toLowerCase()));
			aluno.setNome(resultado.getString("nome"));
			((Aluno) aluno).setMatricula(resultado.getString("matricula"));
			aluno.setEmail(resultado.getString("email"));
			aluno.setTelefone(resultado.getString("telefone"));
			
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
	
	// Consulta todos os alunos no banco de dados e os inclui numa lista a ser retornada.
	@SuppressWarnings("finally")
	public ArrayList<Aluno> pesquisarTodosAlunos() {
		ArrayList<Aluno> alunos = new ArrayList<>();
		
		ResultSet resultado = PessoaDAO.getInstance().selecionarPorPerfil(BancoTabela.ALUNO); //Realiza a consulta no banco
		
		try {
			while(resultado.next()) { //Atribui os valores encontrados em uma lista de objetos de alunos
				Aluno aluno = ((Aluno) PessoaFactory.getPessoa(Perfil.ALUNO, resultado));
				aluno.setMatricula(resultado.getString("matricula"));
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

		
	// busca um aluno a partir do seu id_pessoa
	@SuppressWarnings("finally")
	public Aluno pesquisarAlunoPorIdPessoa(int idPessoa) {
		Aluno aluno = null;
		ResultSet resultado = PessoaDAO.getInstance().selecionarPorPerfilEId(Perfil.ALUNO, idPessoa);
		
		try {
			if (resultado.next()) {
				// a função getPessoa deve preencher todos os dados de aluno a partir do resultado do SQL
				
				aluno = ((Aluno) PessoaFactory.getPessoa(Perfil.ALUNO, resultado));
			}
			else {
				System.out.println("Não encontrou aluno com id_pessoa="+idPessoa);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return aluno;			
		}
	}
	

	// busca um aluno a partir do seu id_aluno
	@SuppressWarnings("finally")
	public Aluno pesquisarAlunoPorIdAluno(int idAluno) {
		
		Aluno aluno = null;
		
		try {
			var connection = ConnectionFactory.getConnection();
			
			// Primeiro busca o id_pessoa a partir desse id_aluno
			String sql = "Select * from " + BancoTabela.ALUNO 
					+ " where " + BancoTabela.ALUNO +".id_aluno = ? ";
			
			PreparedStatement stm =  connection.prepareStatement(sql);
			stm.setInt(1, idAluno);
			var resultado = stm.executeQuery();
			
			if (resultado.next()) {
				// usa o id_pessoa encontrado para retornar a pessoa
				int idPessoa = resultado.getInt("id_pessoa");
				aluno = pesquisarAlunoPorIdPessoa(idPessoa);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return aluno;			
		}
		
		
		
	}
	
	public  ArrayList<Aluno> pesquisaStatusAlunoTccCandidato() {
		ArrayList<Aluno> alunos = new ArrayList<Aluno>();
		
		
		try {
			Connection connection = ConnectionFactory.getConnection();
			String sql = "Select * from " + BancoTabela.ALUNO 
					+ " where status_aluno_tcc=2";//candidato
			
			PreparedStatement stm = connection.prepareStatement(sql);
			
			ResultSet resultado = stm.executeQuery();
			
			while (resultado.next()) {
				Aluno aluno = new Aluno();
				popularDadosAluno(aluno,resultado);
				
				alunos.add(aluno);
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return alunos;
	}
	
	public void popularDadosAluno(Aluno aluno, ResultSet resultado) throws SQLException {
		aluno.setMatricula(resultado.getString("matricula"));
		aluno.setIdAluno(resultado.getInt("id_aluno"));
		var statusAlunoTCC=resultado.getInt("status_aluno_tcc");
		aluno.setstatusAlunoTCC(Aluno.fromInt(statusAlunoTCC));
		aluno.setId(resultado.getInt("id_pessoa"));
		
		int idPessoa = resultado.getInt("id_pessoa");
		Aluno alunoPessoa = pesquisarAlunoPorIdPessoa(idPessoa);
		
		aluno.setNome(alunoPessoa.getNome());
		aluno.setEmail(alunoPessoa.getEmail());
		aluno.setTelefone(alunoPessoa.getTelefone());
		aluno.setMatricula(alunoPessoa.getMatricula());
		
		
	}
	
	public static void atualizaStatusAlunoParaRejeitado(int idAluno) {
		
		
		try {
			Connection connection = ConnectionFactory.getConnection();
			
			String sql = "UPDATE " +BancoTabela.ALUNO+" SET status_aluno_tcc = 3"+
					" WHERE id_aluno = " + idAluno;
			PreparedStatement stm = connection.prepareStatement(sql);
			stm.executeUpdate();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void atualizaStatusAlunoParaAceito(int idAluno) {
		
		
		try {
			Connection connection = ConnectionFactory.getConnection();
			
			String sql = "UPDATE " +BancoTabela.ALUNO+" SET status_aluno_tcc = 0"+
					" WHERE id_aluno = " + idAluno;
			PreparedStatement stm = connection.prepareStatement(sql);
			stm.executeUpdate();
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void adicionaAlunoNaTurmaAssociadaAProfessor(int  idAluno, int idprofessor) {
		AlunoDAO alunoDAO= new AlunoDAO();
		Aluno aluno = alunoDAO.pesquisarAlunoPorIdAluno(idAluno);
				
		String sql;
		
				
		try {
			var connection = ConnectionFactory.getConnection();
			
			
			sql = "SELECT "+ BancoTabela.TURMA_PROFESSOR+".turma_id FROM " + BancoTabela.PROFESSOR + " INNER JOIN " + BancoTabela.TURMA_PROFESSOR  
					+ " ON " + BancoTabela.TURMA_PROFESSOR+".professor_id = "+ BancoTabela.PROFESSOR + ".id_professor "
					+ " AND "+BancoTabela.PROFESSOR+".id_professor=?";
			
			PreparedStatement stm =  connection.prepareStatement(sql);
			stm.setInt(1, idprofessor);
			var resultado = stm.executeQuery();
			int turma_id=0;
            
            if (resultado.next()){
            	turma_id=resultado.getInt("turma_id");
            	System.out.println("ID DA TURMA É" +turma_id);
			
            }
            sql = "INSERT INTO " + BancoTabela.TURMA_ALUNO + " (turma_id,aluno_id,pontuacao,situacao_id) values (?, ?, ?, ? )";
            
            
            	stm =  connection.prepareStatement(sql);
            			
            	stm.setInt(1, turma_id);
            	stm.setInt(2, aluno.getIdAluno());
            	stm.setInt(3, 0);
            	stm.setInt(4, 3);//em curso
                
            	stm.executeUpdate();
            	
			stm.close();
            }catch (SQLException e) {
            	e.printStackTrace();
		} 
	}
	
	
	
	
	
}