package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import enums.BancoTabela;
import model.EnvioEntrega;
import util.ConnectionFactory;

public class EnvioEntregaDAO {
	private EnvioEntregaDAO() {}
	
	public static EnvioEntrega buscarEnvioEntregaPorAluno(int idEntrega, int idAluno) {
		try {
			Connection con = ConnectionFactory.getConnection();
			String sql = "Select * from " + BancoTabela.ENVIO_ENTREGA 
					+ " where id_entrega=? AND id_aluno=?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, idEntrega);
			stm.setInt(2, idAluno);
			ResultSet result = stm.executeQuery();
			if (result.next()) {
				EnvioEntrega envio = new EnvioEntrega();
				popularEnvioEntrega(envio, result);
				return envio;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static EnvioEntrega buscarEnvioEntregaPorId(int idEnvioEntrega) {
		try {
			Connection con = ConnectionFactory.getConnection();
			String sql = "Select * from " + BancoTabela.ENVIO_ENTREGA 
					+ " where id_envio_entrega=? ";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, idEnvioEntrega);
			ResultSet result = stm.executeQuery();
			if (result.next()) {
				EnvioEntrega envio = new EnvioEntrega();
				popularEnvioEntrega(envio, result);
				return envio;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void addEnvioEntrega(EnvioEntrega envio) {
		try {
			Connection con = ConnectionFactory.getConnection();
			String sql = "Insert into " + BancoTabela.ENVIO_ENTREGA 
					+ " (id_entrega, id_aluno, data_envio, anexo, content_type, nome)"
					+ " VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, envio.getIdEntrega());
			stm.setInt(2, envio.getIdAluno());
			stm.setDate(3, envio.getDataEnvio());
			stm.setBinaryStream(4, envio.getAnexo());
			stm.setString(5, envio.getContentType());
			stm.setString(6, envio.getFileName());
			
			stm.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void removeEnvioEntrega(int idEnvioEntrega) {
		try {
			Connection con = ConnectionFactory.getConnection();
			String sql = "Delete from " + BancoTabela.ENVIO_ENTREGA 
					+ " where " + BancoTabela.ENVIO_ENTREGA + ".id_envio_entrega = ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, idEnvioEntrega);
			
			stm.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void popularEnvioEntrega(EnvioEntrega envio, ResultSet result) throws SQLException {
		envio.setIdEnvioEntrega(result.getInt("id_envio_entrega"));
		envio.setIdEntrega(result.getInt("id_entrega"));
		envio.setIdAluno(result.getInt("id_aluno"));
		envio.setDataEnvio(result.getDate("data_envio"));
		envio.setContentType(result.getString("content_type"));
		envio.setFileName(result.getString("nome"));
		envio.setAnexo(result.getBinaryStream("anexo"));
	}
	
	
}
