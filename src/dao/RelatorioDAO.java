package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import enums.BancoTabela;
import enums.Perfil;
import enums.SituacaoProjeto;
import model.Pessoa;
import model.Projeto;
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
			relatorio.setIdAutor(resultado.getInt("id_autor_pessoa"));
			relatorio.setIdDestinatario(resultado.getInt("id_destinatario_pessoa"));
			Pessoa p = PessoaDAO.getInstance().findById(Perfil.NENHHUM, resultado.getInt("id_destinatario_pessoa"));
			relatorio.setNomeDestinatario(p.getNome());
			relatorio.setData(resultado.getDate("data"));
			relatorio.setNomeAutor(resultado.getString("nome"));
			relatorio.setTexto(resultado.getString("texto"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return relatorio;
	}
	
	public boolean addRelatorio(Relatorio relatorio) {
		
		boolean sucesso = false;
		
		String sql;
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		sql = "INSERT INTO " + BancoTabela.RELATORIO + " (titulo, id_autor_pessoa, id_destinatario_pessoa, data, texto) values (?, ?, ?, ?, ? )";
		
		PreparedStatement prepareStatement = null;
        try {
        	prepareStatement = conexao.prepareStatement(sql);
        			
            prepareStatement.setString(1, relatorio.getTitulo());
            prepareStatement.setInt(2, relatorio.getIdAutor());
            prepareStatement.setInt(3, relatorio.getIdDestinatario());
            prepareStatement.setDate(4, new java.sql.Date(relatorio.getData().getTime()));
            prepareStatement.setString(5, relatorio.getTexto());
            
            prepareStatement.executeUpdate();
            sucesso = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
			try {prepareStatement.close();}catch(SQLException e){e.printStackTrace();}
			try {conexao.close();}catch(SQLException e){e.printStackTrace();}
		}
        
        return sucesso;
    }
	
}