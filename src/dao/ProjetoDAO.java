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
import model.Professor;
import model.Projeto;
import util.ConnectionFactory;


public class ProjetoDAO {
	
	private static ProjetoDAO uniqueInstance; //Singleton
	
	private ProjetoDAO() { }
	
	public static synchronized ProjetoDAO getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new ProjetoDAO();
		return uniqueInstance;
	}
	
	@SuppressWarnings("finally")
	public Projeto findById(int id) {
		Projeto projeto = new Projeto();
		ResultSet resultado = null;
		String sql;
		
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		sql = "SELECT * FROM " + BancoTabela.PROJETO + " INNER JOIN " + BancoTabela.SITUACAO_PROJETO + 
				" WHERE " + BancoTabela.PROJETO+".id_"+BancoTabela.PROJETO+ " = " + id + 
				" AND " + BancoTabela.PROJETO +".id_situacao = " +  BancoTabela.SITUACAO_PROJETO + ".id_situacao_projeto;";
		
		try {
            Statement stm = conexao.createStatement();
            resultado = stm.executeQuery(sql);
            
            resultado.next();
			projeto.setId(id);
			projeto.setTitulo(resultado.getString(BancoTabela.PROJETO+".titulo"));
			projeto.setDescricao(resultado.getString(BancoTabela.PROJETO+".descricao"));
			projeto.getProfessor().setId(resultado.getInt(BancoTabela.PROJETO+".id_professor"));
			projeto.setProfessor(ProfessorDAO.getInstance().findById(projeto.getProfessor().getId()));
			projeto.setSituacao(SituacaoProjeto.valueOf(resultado.getString(BancoTabela.SITUACAO_PROJETO+".descricao").toUpperCase()));
            
			stm.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {resultado.close();}catch(SQLException e){e.printStackTrace();}
			try {conexao.close();}catch(SQLException e){e.printStackTrace();}
			return projeto;
		}
	}

	
	public  ArrayList<Projeto> pesquisarProjetosPorProfessorESituacao(int idProfessor, SituacaoProjeto situacao) {
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
	
	private  void popularProjeto(Projeto projeto, ResultSet resultado) throws SQLException {
		projeto.setId(resultado.getInt(BancoTabela.PROJETO+".id_projeto"));
		projeto.setTitulo(resultado.getString(BancoTabela.PROJETO+".titulo"));
		projeto.setDescricao(resultado.getString(BancoTabela.PROJETO+".descricao"));
		projeto.setIdProfessor(resultado.getInt(BancoTabela.PROJETO+".id_professor"));
		projeto.setSituacao(SituacaoProjeto.fromInt(resultado.getInt(BancoTabela.PROJETO+".id_situacao")));
	}
	
	// Consulta todos os projetos que est�o no banco e retorna os que est�o dispon�veis para o Projeto escolher se candidatar
	@SuppressWarnings("finally")
	public ArrayList<Projeto> pesquisarProjetosDisponiveis() {
			
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
				projeto.setProfessor(ProfessorDAO.getInstance().findById(resultado.getInt(BancoTabela.PROJETO+".id_professor")));
				String situacao = resultado.getString(BancoTabela.SITUACAO_PROJETO + ".descricao");
				projeto.setSituacao(SituacaoProjeto.valueOf(situacao.toUpperCase()));
				
				projetos.add(projeto);
			}
			stm.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {resultado.close();}catch(SQLException e){e.printStackTrace();}
			try {conexao.close();}catch(SQLException e){e.printStackTrace();}
			return projetos;			
		}
	}

	public void addProjeto(Projeto projeto) {
			
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
	public Projeto findByTitulo(String titulo) {
		Projeto projeto = new Projeto();
		ResultSet resultado = null;
		String sql;
		
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		sql = "SELECT * FROM " + BancoTabela.PROJETO + " INNER JOIN " + BancoTabela.SITUACAO_PROJETO +
				" WHERE " + BancoTabela.PROJETO+".titulo = '" + titulo + "' AND " + BancoTabela.PROJETO+".id_situacao = " + BancoTabela.SITUACAO_PROJETO+".id_situacao_projeto;";
		
		try {
            Statement stm = conexao.createStatement();
            resultado = stm.executeQuery(sql);
            
            resultado.next();
			projeto.setId(resultado.getInt(BancoTabela.PROJETO+".id_"+BancoTabela.PROJETO.toString().toLowerCase()));
			projeto.setTitulo(resultado.getString(BancoTabela.PROJETO+".titulo"));
			projeto.setDescricao(resultado.getString(BancoTabela.PROJETO+".descricao"));
			projeto.getProfessor().setId(resultado.getInt(BancoTabela.PROJETO+".id_professor"));
			projeto.setProfessor(ProfessorDAO.getInstance().findById(projeto.getProfessor().getId()));
			projeto.setSituacao(SituacaoProjeto.valueOf(resultado.getString(BancoTabela.SITUACAO_PROJETO+".descricao").toUpperCase()));
            
			stm.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {resultado.close();}catch(SQLException e){e.printStackTrace();}
			try {conexao.close();}catch(SQLException e){e.printStackTrace();}
			return projeto;
		}
	}
	
	public Professor findProfessorById(int idProjeto) {
		Professor professor = new Professor();
		
		
		
		return professor;
	}
	
	public boolean incluir(Projeto projeto) {
		// TODO Auto-generated method stub
		String sql;
		try {
			Connection conexao = ConnectionFactory.getConnection();
			
			Statement stm = conexao.createStatement();
			
			//sql = "INSERT INTO " 
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	public void atualizar() {
		// TODO Auto-generated method stub
		
	}

	public boolean deletar() {
		// TODO Auto-generated method stub
		return false;
	}
		
		
}
