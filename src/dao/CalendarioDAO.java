package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import enums.BancoTabela;
import model.Entrega;
import util.ConnectionFactory;

public class CalendarioDAO {
	private CalendarioDAO() {}
	
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
	
	
	private static void popularEntrega(Entrega entrega, ResultSet result) throws SQLException {
		entrega.setIdEntrega(result.getInt("id_entrega"));
		entrega.setIdCalendarioEntrega(result.getInt("id_calendario_entrega"));
		entrega.setTitulo(result.getString("titulo"));
		entrega.setInstrucao(result.getString("instrucao"));
		entrega.setDataPrazo(result.getDate("data_prazo"));
	}
}
