package dao;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import enums.BancoTabela;
import enums.Perfil;
import enums.SituacaoInscricao;
import model.Aluno;
import model.InscricaoProjeto;
import model.Pessoa;
import model.PessoaFactory;

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
			aluno.setId(resultado.getInt("id_pessoa"));
			aluno.setNome(resultado.getString("nome"));
			((Aluno) aluno).setMatricula(resultado.getString("matricula"));
			((Aluno) aluno).setIdAluno(resultado.getInt("id_aluno"));
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
				// a fun��o getPessoa deve preencher todos os dados de aluno a partir do resultado do SQL
				
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
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return aluno;			
		}
		
		
		
	}
	
	//pesquisa todos os alunos cujo status seja candidato a tcc
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
			connection.close();
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
			connection.close();
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
            	System.out.println("ID DA TURMA �" +turma_id);
			
            }
            sql = "INSERT INTO " + BancoTabela.TURMA_ALUNO + " (turma_id,aluno_id,pontuacao,situacao_id) values (?, ?, ?, ? )";
            
            
            	stm =  connection.prepareStatement(sql);
            			
            	stm.setInt(1, turma_id);
            	stm.setInt(2, aluno.getIdAluno());
            	stm.setInt(3, 0);
            	stm.setInt(4, 3);//em curso
                
            	stm.executeUpdate();
            	
			stm.close();
			connection.close();
            }catch (SQLException e) {
            	e.printStackTrace();
		} 
	}
	
	public static ArrayList<Aluno> pesquisaAlunosDaTurmaDeCadaProfessor(int idprofessor) {
		ArrayList<Integer> listaIdAluno = new ArrayList<Integer>();				
		String sql;
		ArrayList<Aluno> listaAlunos = new ArrayList<Aluno>();
		AlunoDAO aluno = new AlunoDAO();
				
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
            	turma_id=resultado.getInt("turma_id");//pega om id da turma da professor 
            	System.out.println("ID DA TURMA E'" +turma_id);
			
            }
            sql = "SELECT aluno_id 	FROM " + BancoTabela.TURMA_ALUNO + " WHERE turma_id=?";
           
            	stm =  connection.prepareStatement(sql);
            			
            	stm.setInt(1, turma_id);
            	
            	
            	
            	resultado=stm.executeQuery();
            	while(resultado.next()) {//preeher o array com os ids de alunos encontrados
            		listaIdAluno.add(resultado.getInt("aluno_id"));
            	}
            	
            	for(int i=0; i < listaIdAluno.size(); i++) {
            		listaAlunos.add(aluno.pesquisarAlunoPorIdAluno(listaIdAluno.get(i)));
            	}
            
            connection.close();
			
            }catch (SQLException e) {
            	e.printStackTrace();
		}
		return listaAlunos; 
	}
	
	public boolean addAluno(Aluno aluno) {
		Connection con = null;
		
		try {
			
			int idPessoa = PessoaDAO.getInstance().addPessoa(aluno);
			
			con = ConnectionFactory.getConnection();
			
			String sql = "Insert into " +BancoTabela.ALUNO +
					" (id_pessoa, matricula, status_aluno_tcc) values (?, ?, ?)";
			
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, idPessoa);
			stm.setString(2, aluno.getMatricula());
			stm.setInt(3, Aluno.toInt(aluno.getStatusAlunoTCC()));
			
			int rowsAffected = stm.executeUpdate(); 
			// retorna qtde de linhas que foi alterada pelo sql
			// idealmente 1, pois foi inserido 01 linha
			if (rowsAffected == 0) {
				return false;
			}
			
			sql = "Insert into " + BancoTabela.PERFIL_PESSOA +
					"(id_pessoa, id_perfil) values (?, ?)";
			
			stm = con.prepareStatement(sql);
			stm.setInt(1, idPessoa);
			stm.setInt(2, Perfil.ALUNO.getValue());
			rowsAffected = stm.executeUpdate();
			if (rowsAffected == 0) {
				return false;
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	public static void atualizaAluno(Aluno  aluno) {
		
		String sql;
		
		try {
	
		sql = "UPDATE  " + BancoTabela.PESSOA + " SET  nome=?, email=?, telefone=? WHERE " + 
				 BancoTabela.PESSOA + ".id_pessoa = ?";
			
			var con = ConnectionFactory.getConnection();
        	PreparedStatement  prepareStatement = con.prepareStatement(sql);
        	
        	int id_pessoa= aluno.getId();
        	
        	
            prepareStatement.setString(1, aluno.getNome());
            prepareStatement.setString(2, aluno.getEmail());
            prepareStatement.setString(3, aluno.getTelefone());
            prepareStatement.setInt(4, id_pessoa);
            prepareStatement.executeUpdate();
            
           
            sql = "UPDATE " + BancoTabela.ALUNO + " SET matricula=? WHERE " +
   				 BancoTabela.ALUNO + ".id_pessoa = ?";
            
   			prepareStatement = con.prepareStatement(sql);
   			prepareStatement.setString(1, aluno.getMatricula());
   			prepareStatement.setInt(2, id_pessoa);
            prepareStatement.executeUpdate();
            
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	// Procura no banco um aluno de um projeto em que esteja associado
	public Aluno pesquisarAlunoAssociadoAoProjeto(int idProjeto) {
		Aluno aluno = null;
		
		ResultSet resultado = null;
		String sql;
		
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sql = "SELECT * FROM "+ BancoTabela.INSCRICAO_ALUNO_PROJETO
			+ " WHERE id_projeto = "+ idProjeto
			+ " AND id_situacao_aluno_projeto = " + SituacaoInscricao.toInt(SituacaoInscricao.ASSOCIADO) +";";
		
		Statement stm = null;
		try {
			stm = conexao.createStatement();
			resultado = stm.executeQuery(sql);
			
			if (resultado.next()) {
				aluno = new Aluno();
				
				aluno = AlunoDAO.getInstance().findById(resultado.getInt("id_aluno"));
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {resultado.close();}catch(SQLException e){e.printStackTrace();}
			try {stm.close();}catch(SQLException e){e.printStackTrace();}
			try {conexao.close();}catch(SQLException e){e.printStackTrace();}
		}
		
		return aluno;
	}

	
}