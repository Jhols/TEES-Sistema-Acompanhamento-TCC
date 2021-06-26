package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import enums.BancoTabela;
import model.Arquivo;
import util.ConnectionFactory;

public class ArquivoDAO {

		public static void addArquivo(Arquivo arquivo) {
				
			String sql;
			Connection conexao = null;
			try {
				conexao = ConnectionFactory.getConnection();
				
				
				sql = "INSERT INTO " + BancoTabela.ARQUIVO + " (id_projeto,anexo, content_type, nome) values (?, ?, ?, ?)";
				
				
		       
		        	PreparedStatement  prepareStatement = conexao.prepareStatement(sql);
		        			
		            prepareStatement.setInt(1, arquivo.getId_projeto());
		            prepareStatement.setBinaryStream(2, arquivo.getAnexo());
		            prepareStatement.setString(3, arquivo.getContentType());
		            prepareStatement.setString(4, arquivo.getFileName());
		            prepareStatement.executeUpdate();
		
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		
		public static ArrayList<Arquivo> procurarAnexosPorProjeto(int idProjeto) {
			ArrayList<Arquivo> arquivos = new ArrayList<Arquivo>();
			try {
				Connection con = ConnectionFactory.getConnection();
				String sql = "SELECT * from " + BancoTabela.ARQUIVO + " where " + BancoTabela.ARQUIVO + ".id_projeto = ?";
				PreparedStatement stm = con.prepareStatement(sql);
				stm.setInt(1, idProjeto);
				ResultSet result = stm.executeQuery();
				while (result.next()) {
					Arquivo arquivo = new Arquivo();
					popularArquivo(arquivo, result);
					arquivos.add(arquivo);
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			return arquivos;
		}
		
		public static Arquivo procuarArquivoPorId(int idArquivo) {
			try {
				Connection con = ConnectionFactory.getConnection();
				String sql = "Select * from " + BancoTabela.ARQUIVO + " where id_arquivo = ?";
				PreparedStatement stm = con.prepareStatement(sql);
				stm.setInt(1, idArquivo);
				ResultSet result = stm.executeQuery();
				if (result.next()) {
					Arquivo arquivo = new Arquivo();
					popularArquivo(arquivo, result);
					return arquivo;
				}
				
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		private static void popularArquivo(Arquivo arquivo, ResultSet result) throws SQLException {
			arquivo.setIdArquivo(result.getInt("id_arquivo"));
			arquivo.setId_projeto(result.getInt("id_projeto"));
			arquivo.setFileName(result.getString("nome"));
			arquivo.setContentType(result.getString("content_type"));
			arquivo.setAnexo(result.getBinaryStream("anexo"));
		}
}
