package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import enums.BancoTabela;
import enums.SituacaoProjeto;
import model.Professor;
import model.Projeto;
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
}
