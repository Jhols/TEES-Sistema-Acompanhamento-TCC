package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import enums.BancoTabela;
import model.Semestre;
import util.ConnectionFactory;

public class SemestreDAO {
	
	private static SemestreDAO instance;
	public static SemestreDAO getInstance() {
		if (instance == null) {
			instance = new SemestreDAO();
		}
		return instance;
	}
	private SemestreDAO() {}
	
	
	public void addSemestre(Semestre semestre) {
		try {
			Connection con = ConnectionFactory.getConnection();
			
			String sql = "Insert into " + BancoTabela.SEMESTRE 
					+ "(semestre_atual, inicio_semestre, final_semestre) values (?, ?, ?)";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, semestre.getSemestreAtual());
			stm.setDate(2, semestre.getInicioSemestre());
			stm.setDate(3, semestre.getFinalSemestre());
			stm.executeUpdate();
			
			con.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Semestre getSemestreAtual() {
		Semestre semestre = null;
		try {
			Connection con = ConnectionFactory.getConnection();
			PreparedStatement stm;
			String sql = "SELECT * FROM semestre ORDER by semestre.id_semestre DESC LIMIT 1";
			stm = con.prepareStatement(sql);
			ResultSet resultado = stm.executeQuery();
			
			if (resultado.next())
			{
				semestre = new Semestre();
				semestre.setSemestreAtual(resultado.getString("semestre_atual"));
				semestre.setInicioSemestre(resultado.getDate("inicio_semestre"));
				semestre.setFinalSemestre(resultado.getDate("final_semestre"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return semestre;
	}
}
