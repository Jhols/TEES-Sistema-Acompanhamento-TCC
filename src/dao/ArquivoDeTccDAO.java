package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import enums.BancoTabela;
import model.Arquivo;
import model.ArquivoDeTcc;
import util.ConnectionFactory;

public class ArquivoDeTccDAO {

	public static void addArquivo(ArquivoDeTcc arquivo) {
		
		String sql;
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
			
			
			sql = "INSERT INTO arquivo_tcc(id_turma,anexo, content_type, nome) values (?, ?, ?, ?)";
			
			
	       
	        	PreparedStatement  prepareStatement = conexao.prepareStatement(sql);
	        			
	            prepareStatement.setInt(1, arquivo.getId_turma());
	            prepareStatement.setBinaryStream(2, arquivo.getAnexo());
	            prepareStatement.setString(3, arquivo.getContentType());
	            prepareStatement.setString(4, arquivo.getFileName());
	            prepareStatement.executeUpdate();
	
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}
	
	public static ArrayList<ArquivoDeTcc> procurarAnexosPorTurma(int idTurma) {
		ArrayList<ArquivoDeTcc> arquivos = new ArrayList<ArquivoDeTcc>();
		try {
			Connection con = ConnectionFactory.getConnection();
			String sql = "SELECT * from arquivo_tcc where arquivo_tcc.id_turma = ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, idTurma);
			ResultSet result = stm.executeQuery();
			while (result.next()) {
				ArquivoDeTcc arquivo = new ArquivoDeTcc();
				popularArquivo(arquivo, result);
				arquivos.add(arquivo);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return arquivos;
	}
	
	private static void popularArquivo(ArquivoDeTcc arquivo, ResultSet result) throws SQLException {
		arquivo.setId_arquivo(result.getInt("id_arquivo"));
		arquivo.setId_turma(result.getInt("id_turma"));
		arquivo.setFileName(result.getString("nome"));
		arquivo.setContentType(result.getString("content_type"));
		arquivo.setAnexo(result.getBinaryStream("anexo"));
	}
	
	public static ArquivoDeTcc procurarArquivoPorId(int idArquivo) {
		try {
			Connection con = ConnectionFactory.getConnection();
			String sql = "Select * from arquivo_tcc where id_arquivo = ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, idArquivo);
			ResultSet result = stm.executeQuery();
			if (result.next()) {
				ArquivoDeTcc arquivo = new ArquivoDeTcc();
				popularArquivo(arquivo, result);
				return arquivo;
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
