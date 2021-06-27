package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import enums.BancoTabela;
import enums.SituacaoTurma;
import model.Aluno;
import model.Professor;
import model.Semestre;
import model.Turma;
import util.ConnectionFactory;

public class TurmaDAO {
	
	private static TurmaDAO instance = null;
	public static TurmaDAO getInstance() {
		if (instance == null) {
			instance = new TurmaDAO();
		}
		return instance;
	}
	
	// Adiciona uma turma ao banco, retorna o ID da turma criada
	public int addTurma(Turma turma) {
			
		String sql;
		
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		sql = "INSERT INTO " + BancoTabela.TURMA + " (nome, semestre) values (?, ? )";
		
		
		try {
			PreparedStatement  prepareStatement = conexao.prepareStatement(sql);
			
			prepareStatement.setString(1, turma.getNome().toUpperCase());
			prepareStatement.setString(2, turma.getSemestre());
			
			prepareStatement.executeUpdate();
			
			sql = "SELECT currval(pg_get_serial_sequence('"+BancoTabela.TURMA+"','turma_id'))";
			
			Statement stm = conexao.createStatement();
			ResultSet resultado = stm.executeQuery(sql);
			resultado.next();
			return resultado.getInt("currval");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public  ArrayList<Turma> pesquisarTurmas() {
		ArrayList<Turma> turmas = new ArrayList<Turma>();
		
		
		try {
			Connection con = ConnectionFactory.getConnection();
			String sql = "Select * from " + BancoTabela.TURMA;
			
			PreparedStatement stm =  con.prepareStatement(sql);
			ResultSet resultado = stm.executeQuery();
			
			while (resultado.next()) {
				Turma turma = new Turma();
				popularTurma(turma, resultado);
				turmas.add(turma);
			}
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return turmas;
	}
	
	public Turma pesquisarTurmaPorId(int idTurma) {
		Turma turma = null;
		
		try {
			Connection con = ConnectionFactory.getConnection();
			String sql = "Select * from " + BancoTabela.TURMA + " where " + BancoTabela.TURMA +".turma_id = ?";
			
			PreparedStatement stm =  con.prepareStatement(sql);
			stm.setInt(1, idTurma);
			ResultSet resultado = stm.executeQuery();
			
			if (resultado.next()) {
				turma = new Turma();
				popularTurma(turma, resultado);
			}
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return turma;
	}
	
	public void atualizaTurma(Turma turma) {
		try {
			Connection con = ConnectionFactory.getConnection();
			String sql = "Update " + BancoTabela.TURMA + 
					" set " + 
					"nome = ?, " +
					"semestre = ? " +
					" where " + BancoTabela.TURMA +".turma_id = ?";
			
			PreparedStatement stm =  con.prepareStatement(sql);
			stm.setString(1, turma.getNome());
			stm.setString(2, turma.getSemestre());
			stm.setInt(3, turma.getId());
			stm.executeUpdate();
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Professor> pesquisarProfessoresVinculados(int idTurma) {
		ArrayList<Professor> professores = new ArrayList<Professor>();
		
		try {
			Connection con = ConnectionFactory.getConnection();
			String sql = "Select * from " + BancoTabela.TURMA_PROFESSOR + 
					" where " + BancoTabela.TURMA_PROFESSOR +".turma_id = ?";
			
			PreparedStatement stm =  con.prepareStatement(sql);
			stm.setInt(1, idTurma);
			ResultSet resultado = stm.executeQuery();
			
			while (resultado.next()) {
				Professor professor = ProfessorDAO.pesquisarPorIdProfessor(resultado.getInt("professor_id"));
				professores.add(professor);
			}
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return professores;
	}
	
	public void vincularProfessor(int idTurma, int idProfessor) {
		try {
			Connection con = ConnectionFactory.getConnection();
			String sql = "Insert into " + BancoTabela.TURMA_PROFESSOR + 
					" (turma_id, professor_id) values (?, ?) ";
			
			PreparedStatement stm =  con.prepareStatement(sql);
			stm.setInt(1, idTurma);
			stm.setInt(2, idProfessor);
			stm.executeUpdate();
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void desvincularProfessor(int idTurma, int idProfessor) {
		try {
			Connection con = ConnectionFactory.getConnection();
			String sql = "Delete from " + BancoTabela.TURMA_PROFESSOR + 
					" where turma_id=? AND professor_id=? ";
			
			PreparedStatement stm =  con.prepareStatement(sql);
			stm.setInt(1, idTurma);
			stm.setInt(2, idProfessor);
			stm.executeUpdate();
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void popularTurma(Turma turma, ResultSet resultado) throws SQLException {
		turma.setId(resultado.getInt("turma_id"));
		turma.setNome(resultado.getString("nome"));
		turma.setSemestre(resultado.getString("semestre"));
		
	}
	
	public ArrayList<Turma> pesquisarTurmaDoSemestreAtualDeCadaProfessor(Professor professor ) {
		ArrayList<Turma> turmas = new ArrayList<Turma>();
		Semestre semestreAtual = SemestreDAO.getInstance().getSemestreAtual();
		if (semestreAtual == null) {
			return turmas;
		}
		try {
			Connection con = ConnectionFactory.getConnection();
			
			String sql = "Select * from " + BancoTabela.TURMA_PROFESSOR
					+" inner join "+ BancoTabela.TURMA + " on "+ BancoTabela.TURMA+".turma_id = " +
					BancoTabela.TURMA_PROFESSOR+".turma_id"
					+ " where " + BancoTabela.TURMA +".semestre = ? and "
					+ BancoTabela.TURMA_PROFESSOR + ".professor_id = ?";
			
			PreparedStatement stm =  con.prepareStatement(sql);
			
			stm.setString(1, semestreAtual.getSemestreAtual());
			stm.setInt(2, professor.getIdProfessor());
			ResultSet resultado = stm.executeQuery();
			
			while (resultado.next()) {
				Turma turma = new Turma();
				popularTurma(turma, resultado);
				turmas.add(turma);
			}
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return turmas;
	}
	
	public void vincularAluno(int idTurma, int idAluno) {
		try {
			Connection con = ConnectionFactory.getConnection();
			String sql = "Insert into " + BancoTabela.TURMA_ALUNO + 
					" (turma_id, aluno_id, situacao_id) values (?, ?, ?) ";
			
			PreparedStatement stm =  con.prepareStatement(sql);
			stm.setInt(1, idTurma);
			stm.setInt(2, idAluno);
			stm.setInt(3, SituacaoTurma.toInt(SituacaoTurma.CURSO));
			stm.executeUpdate();
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Aluno> pesquisarAlunosPorTurma(int idTurma) {
		ArrayList<Aluno> alunos = new ArrayList<Aluno>();
		try {
			String sql = "Select * from " + BancoTabela.TURMA_ALUNO +
					" where " + BancoTabela.TURMA_ALUNO + ".turma_id = ?";
			Connection con = ConnectionFactory.getConnection();
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, idTurma);
			ResultSet resultado = stm.executeQuery();
			while (resultado.next()) {
				int idAluno = resultado.getInt("aluno_id");
				Aluno aluno = AlunoDAO.getInstance().pesquisarAlunoPorIdAluno(idAluno);
				alunos.add(aluno);
			}
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return alunos;
	}
	
	
	public int pesquisarTurmaDoAluno(int idAluno) {
		int idTurma=-1;
		try {
			String sql = "Select turma_id from turma_aluno inner join aluno \r\n"
					+ "			on aluno_id = id_aluno where id_aluno=?";
			
			Connection con = ConnectionFactory.getConnection();
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, idAluno);
			ResultSet resultado = stm.executeQuery();
			while (resultado.next()) {
			 idTurma = resultado.getInt("turma_id");
			}
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return idTurma;
	}
	public void excluirTurma(int idTurma) {
		try {
			Connection con = ConnectionFactory.getConnection();
			String sql = "Delete  from " + BancoTabela.TURMA + " where " + BancoTabela.TURMA +".turma_id = ?";
			
			PreparedStatement stm =  con.prepareStatement(sql);
			stm.setInt(1, idTurma);
			stm.executeUpdate();
			
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}


