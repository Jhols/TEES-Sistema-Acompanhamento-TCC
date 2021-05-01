package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;

import enums.BancoTabela;
import enums.SituacaoProjeto;
import model.Aluno;
import model.Projeto;
import util.ConnectionFactory;


public class ProjetoDAO {

	
	public static ArrayList<Projeto> pesquisarProjetosPorProfessorESituacao(int idProfessor, SituacaoProjeto situacao) {
		ArrayList<Projeto> projetos = new ArrayList<Projeto>();
		
		
		try {
			Connection con = ConnectionFactory.getConnection();
			String sql = "Select * from " + BancoTabela.PROJETO 
					+ " where " + BancoTabela.PROJETO +".id_professor = ? and "
					+ BancoTabela.PROJETO + ".id_situacao = ?";
			
			PreparedStatement stm =  con.prepareStatement(sql);
			stm.setInt(1, idProfessor);
			stm.setInt(2, SituacaoProjeto.toInt(situacao));
			ResultSet resultado = stm.executeQuery();
			
			while (resultado.next()) {
				Projeto projeto = new Projeto();
				popularProjeto(projeto, resultado);
				projetos.add(projeto);
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return projetos;
	}
	
	private static void popularProjeto(Projeto projeto, ResultSet resultado) throws SQLException {
		projeto.setId(resultado.getInt(BancoTabela.PROJETO+".id_projeto"));
		projeto.setTitulo(resultado.getString(BancoTabela.PROJETO+".titulo"));
		projeto.setDescricao(resultado.getString(BancoTabela.PROJETO+".descricao"));
		projeto.setIdProfessor(resultado.getInt(BancoTabela.PROJETO+".id_professor"));
		projeto.setSituacao(SituacaoProjeto.fromInt(resultado.getInt(BancoTabela.PROJETO+".id_situacao")));
	}
	
	// Consulta todos os projetos que estão no banco e retorna os que estão disponíveis para o Projeto escolher se candidatar
	@SuppressWarnings("finally")
	public static ArrayList<Projeto> pesquisarProjetosDisponiveis() {
			
		ArrayList<Projeto> projetos = new ArrayList<>();
		ResultSet resultado = null;
		String sql;
		
		
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		sql = "SELECT * FROM " + BancoTabela.PROJETO + " INNER JOIN " + BancoTabela.SITUACAO_PROJETO + 
				" WHERE " + BancoTabela.SITUACAO_PROJETO + ".descricao = 'disponivel'";
		
		
		try {
			Statement stm = conexao.createStatement();
			resultado = stm.executeQuery(sql);
			
			while(resultado.next()) {
				Projeto projeto = new Projeto();
				projeto.setId(resultado.getInt(BancoTabela.PROJETO + ".id_projeto"));
				projeto.setTitulo(resultado.getString(BancoTabela.PROJETO + ".titulo"));
				projeto.setDescricao(resultado.getString(BancoTabela.PROJETO + ".descricao"));
				
				String situacao = resultado.getString(BancoTabela.SITUACAO_PROJETO + ".descricao");
				projeto.setSituacao(SituacaoProjeto.valueOf(situacao.toUpperCase()));
				
				projetos.add(projeto);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			return projetos;			
		}
	}
	
	public static void addProjeto(Projeto projeto) {
		
		String sql;
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		sql = "INSERT INTO " + BancoTabela.PROJETO + " (titulo,descricao,id_professor,id_situacao) values (?, ?, ?, ? )";
		
		
        try {
        	PreparedStatement  prepareStatement = conexao.prepareStatement(sql);
        			
            prepareStatement.setString(1, projeto.getTitulo());
            prepareStatement.setString(2, projeto.getDescricao());
            prepareStatement.setInt(3, projeto.getIdProfessor());
            prepareStatement.setInt(4, SituacaoProjeto.toInt(projeto.getSituacao()));
            
            prepareStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	@SuppressWarnings("finally")
	public static Projeto pesquisarProjetoPorIdProjeto(int idProjeto) {
		
		Projeto projeto = null;
		
		try {
			var connection = ConnectionFactory.getConnection();
			String sql = "Select * from " + BancoTabela.PROJETO
					+ " where " + BancoTabela.PROJETO +".id_projeto = ? ";
			
			PreparedStatement stm =  connection.prepareStatement(sql);
			stm.setInt(1, idProjeto);
			var resultado = stm.executeQuery();
			
			if (resultado.next()) {
				projeto = new Projeto();
				popularProjeto(projeto, resultado);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return projeto;			
		}
		
	}
		
		
}
