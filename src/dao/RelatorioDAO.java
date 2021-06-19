package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import enums.BancoTabela;
import model.Relatorio;
import util.ConnectionFactory;

public class RelatorioDAO {

	private static RelatorioDAO uniqueInstance; //Singleton
	
	public RelatorioDAO() { }
	
	public static synchronized RelatorioDAO getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new RelatorioDAO();
		return uniqueInstance;
	}
	
	public Relatorio findRelatorioById(int id) {
		Relatorio relatorio = null;
		
		Connection conexao = null;
		ResultSet resultado = null;
		PreparedStatement stm = null;
		
		String sql;
		
		try {
			conexao = ConnectionFactory.getConnection();
			
			sql = "SELECT id_relatorio, titulo, id_autor_pessoa, id_destinatario_pessoa, data, texto, nome"
				+ " FROM " + BancoTabela.RELATORIO
				+ " INNER JOIN " + BancoTabela.PESSOA + " ON id_autor_pessoa = id_pessoa"
				+ " WHERE id_relatorio = ?;";
			
			stm = conexao.prepareStatement(sql);
			stm.setInt(1, id);
			resultado = stm.executeQuery();
			
			if (resultado.next()) {
				relatorio = new Relatorio();
				relatorio = popularRelatorio(resultado);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		finally {
			try {resultado.close();}catch(SQLException e){e.printStackTrace();}
			try {stm.close();}catch(SQLException e){e.printStackTrace();}
			try {conexao.close();}catch(SQLException e){e.printStackTrace();}
		}
				
		return relatorio;
	}
	
	public ArrayList<Relatorio> findAllRelatoriosByAutor(int idPessoa) {
		ArrayList<Relatorio> relatorios = null;
		
		Connection conexao = null;
		ResultSet resultado = null;
		PreparedStatement stm = null;
		
		String sql;
		
		try {
			conexao = ConnectionFactory.getConnection();
			
			sql = "SELECT id_relatorio, titulo, id_autor_pessoa, id_destinatario_pessoa, data, texto, nome"
				+ " FROM " + BancoTabela.RELATORIO
				+ " INNER JOIN " + BancoTabela.PESSOA + " ON id_autor_pessoa = id_pessoa"
				+ " WHERE id_autor_pessoa = ?;";
			
			stm = conexao.prepareStatement(sql);
			stm.setInt(1, idPessoa);
			resultado = stm.executeQuery();
			
			relatorios = new ArrayList<Relatorio>();
			
			while (resultado.next()) {
				Relatorio relatorio = new Relatorio();
				relatorio = popularRelatorio(resultado);
				
				relatorios.add(relatorio);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		finally {
			try {resultado.close();}catch(SQLException e){e.printStackTrace();}
			try {stm.close();}catch(SQLException e){e.printStackTrace();}
			try {conexao.close();}catch(SQLException e){e.printStackTrace();}
		}
				
		return relatorios;
	}
	
	public ArrayList<Relatorio> findAllRelatoriosByDestinatario(int idPessoa) {
		ArrayList<Relatorio> relatorios = null;
		
		Connection conexao = null;
		ResultSet resultado = null;
		PreparedStatement stm = null;
		
		String sql;
		
		try {
			conexao = ConnectionFactory.getConnection();
			
			sql = "SELECT id_relatorio, titulo, id_autor_pessoa, id_destinatario_pessoa, data, texto, nome"
				+ " FROM " + BancoTabela.RELATORIO
				+ " INNER JOIN " + BancoTabela.PESSOA + " ON id_autor_pessoa = id_pessoa"
				+ " WHERE id_destinatario_pessoa = ?;";
			
			stm = conexao.prepareStatement(sql);
			stm.setInt(1, idPessoa);
			resultado = stm.executeQuery();
			
			relatorios = new ArrayList<Relatorio>();
			
			while (resultado.next()) {
				Relatorio relatorio = new Relatorio();
				relatorio = popularRelatorio(resultado);
				
				relatorios.add(relatorio);
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		finally {
			try {resultado.close();}catch(SQLException e){e.printStackTrace();}
			try {stm.close();}catch(SQLException e){e.printStackTrace();}
			try {conexao.close();}catch(SQLException e){e.printStackTrace();}
		}
				
		return relatorios;
	}
	
	protected Relatorio popularRelatorio(ResultSet resultado) {
		Relatorio relatorio = new Relatorio();
		try {
			relatorio.setIdRelatorio(resultado.getInt("id_relatorio"));
			relatorio.setTitulo(resultado.getString("titulo"));
			relatorio.setAutor(resultado.getInt("id_autor_pessoa"));
			relatorio.setDestinatario(resultado.getInt("id_destinatario_pessoa"));
			relatorio.setData(resultado.getDate("data"));
			relatorio.setNomeAutor(resultado.getString("nome"));
			relatorio.setTexto(resultado.getString("texto"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return relatorio;
	}
	
}