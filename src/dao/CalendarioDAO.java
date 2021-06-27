package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import enums.BancoTabela;
import enums.SituacaoProjeto;
import model.CalendarioEntrega;
import model.Entrega;
import util.ConnectionFactory;

public class CalendarioDAO {
	
	private static CalendarioDAO uniqueInstance; //Singleton
	
	public CalendarioDAO() { }
	
	public static synchronized CalendarioDAO getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new CalendarioDAO();
		return uniqueInstance;
	}
		
	public CalendarioEntrega findById(int id) {
		CalendarioEntrega cronograma = null;
		
		ResultSet resultado = null;
		String sql;
		
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		sql = "SELECT * FROM " + BancoTabela.CALENDARIO_ENTREGA 
			+ " WHERE id_calendario_entrega = " + id;
		
		Statement stm = null;
		try {
            stm = conexao.createStatement();
            resultado = stm.executeQuery(sql);
            
            resultado.next();
            cronograma = new CalendarioEntrega();
            popularCalendario(cronograma, resultado);
            
		}
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {resultado.close();}catch(SQLException e){e.printStackTrace();}
			try {stm.close();}catch(SQLException e){e.printStackTrace();}
			try {conexao.close();}catch(SQLException e){e.printStackTrace();}
		}
		
		return cronograma;
	}
	
	public CalendarioEntrega findByIdTurma(int idTurma) {
		CalendarioEntrega cronograma = null;
		
		ResultSet resultado = null;
		String sql;
		
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		sql = "SELECT * FROM " + BancoTabela.CALENDARIO_ENTREGA 
			+ " WHERE id_turma = " + idTurma;
		
		Statement stm = null;
		try {
            stm = conexao.createStatement();
            resultado = stm.executeQuery(sql);
            
            resultado.next();
            cronograma = new CalendarioEntrega();
            popularCalendario(cronograma, resultado);
            
		}
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {resultado.close();}catch(SQLException e){e.printStackTrace();}
			try {stm.close();}catch(SQLException e){e.printStackTrace();}
			try {conexao.close();}catch(SQLException e){e.printStackTrace();}
		}
		
		return cronograma;
	}
	
	public static ArrayList<Entrega> buscarEntregasPorTurma(int idTurma) {
		ArrayList<Entrega> entregas = new ArrayList<Entrega>();
		try {
			Connection con = ConnectionFactory.getConnection();
			String sql = "Select * from " + BancoTabela.TURMA
					+ " inner join " + BancoTabela.CALENDARIO_ENTREGA
						+ " on " + BancoTabela.TURMA + ".turma_id=" + BancoTabela.CALENDARIO_ENTREGA + ".id_turma "
					+ " inner join " + BancoTabela.ENTREGA
						+ " on " + BancoTabela.CALENDARIO_ENTREGA + ".id_calendario_entrega=" + BancoTabela.ENTREGA+".id_calendario_entrega"
					+ " where " + BancoTabela.TURMA + ".turma_id = ?"
					+ " order by " + BancoTabela.ENTREGA + ".data_prazo";
			System.out.println(sql);
			System.out.println(idTurma);
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, idTurma);
			ResultSet result = stm.executeQuery();
			while (result.next()) {
				Entrega entrega = new Entrega();
				popularEntrega(entrega, result);
				entregas.add(entrega);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return entregas;
	}
	
	private void popularCalendario(CalendarioEntrega cronograma, ResultSet resultado) throws SQLException {
		cronograma.setIdCalendario(resultado.getInt("id_calendario_entrega"));
        cronograma.setIdTurma(resultado.getInt("id_turma"));
        cronograma.setDescricao(resultado.getString("descricao"));
        cronograma.setIdSemestre(resultado.getInt("id_semestre"));
	}
	
	private static void popularEntrega(Entrega entrega, ResultSet result) throws SQLException {
		entrega.setIdEntrega(result.getInt("id_entrega"));
		entrega.setIdCalendarioEntrega(result.getInt("id_calendario_entrega"));
		entrega.setTitulo(result.getString("titulo"));
		entrega.setInstrucao(result.getString("instrucao"));
		entrega.setDataPrazo(result.getDate("data_prazo"));
	}
	
	public void inserirEntrega(Entrega entrega) {
		String sql;
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		sql = "INSERT INTO " + BancoTabela.ENTREGA + " (id_calendario_entrega, titulo, instrucao, data_prazo) VALUES ( ?, ?, ?, ? )";
		
		PreparedStatement stm = null;
        try {
        	stm = conexao.prepareStatement(sql);
        			
            stm.setInt(1, entrega.getIdCalendarioEntrega());
            stm.setString(2, entrega.getTitulo());
            stm.setString(3, entrega.getInstrucao());
            stm.setDate(4, new java.sql.Date(entrega.getDataPrazo().getTime()));
            
            stm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
			try {stm.close();}catch(SQLException e){e.printStackTrace();}
			try {conexao.close();}catch(SQLException e){e.printStackTrace();}
        }
	}
}
