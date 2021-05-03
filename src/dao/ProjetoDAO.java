package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;

import enums.BancoTabela;
import enums.SituacaoProjeto;
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

	// pesquisa todos projetos de um professor (especificado pelo id_professor) cuja situação do projeto seja igual ao parametro 'situacao' passado
	// retorna um ArrayList vazio caso o professor não tenho projetos com a situção determinada
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
	
	// Popula os campos de projeto a partir do resultado de uma consulta sql
	private static void popularProjeto(Projeto projeto, ResultSet resultado) throws SQLException {
		projeto.setId(resultado.getInt(BancoTabela.PROJETO+".id_projeto"));
		projeto.setTitulo(resultado.getString(BancoTabela.PROJETO+".titulo"));
		projeto.setDescricao(resultado.getString(BancoTabela.PROJETO+".descricao"));
		projeto.setIdProfessor(resultado.getInt(BancoTabela.PROJETO+".id_professor"));
		System.out.println("ID DO PROFESSOR DO PROJETO = " +projeto.getIdProfessor());
		projeto.setProfessor(ProfessorDAO.pesquisarPorIdProfessor(projeto.getIdProfessor()));
		System.out.println("PROFESSOR DO PROJETO = " +projeto.getProfessor());
		projeto.setSituacao(SituacaoProjeto.fromInt(resultado.getInt(BancoTabela.PROJETO+".id_situacao")));
	}
	
	// Consulta todos os projetos que estao no banco e retorna os que estao disponiveis para o aluno escolher se candidatar
	@SuppressWarnings("finally")
	public ArrayList<Projeto> pesquisarProjetosDisponiveis() {
			
		ArrayList<Projeto> projetos = new ArrayList<>();
		ResultSet resultado = null;
		String sql;
		SituacaoProjeto situacao = SituacaoProjeto.DISPONIVEL;
		
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		sql = "SELECT * FROM " + BancoTabela.PROJETO 
				+ " WHERE " + BancoTabela.PROJETO+".id_situacao = "
						+ "(SELECT id_situacao_projeto FROM " + BancoTabela.SITUACAO_PROJETO
						+ " WHERE descricao = '"+ situacao +"')";  
		
		
		try {
			Statement stm = conexao.createStatement();
			resultado = stm.executeQuery(sql);
			
			while(resultado.next()) {
				Projeto projeto = new Projeto();
				projeto.setId(resultado.getInt(BancoTabela.PROJETO + ".id_projeto"));
				projeto.setTitulo(resultado.getString(BancoTabela.PROJETO + ".titulo"));
				projeto.setDescricao(resultado.getString(BancoTabela.PROJETO + ".descricao"));
				projeto.setProfessor(ProfessorDAO.getInstance().findById(resultado.getInt(BancoTabela.PROJETO+".id_professor")));
				projeto.setSituacao(situacao);
				
				projetos.add(projeto);
			}
			stm.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			try {resultado.close();}catch(SQLException e){e.printStackTrace();}
			try {conexao.close();}catch(SQLException e){e.printStackTrace();}
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
	// pesquisa um projeto pelo seu id
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
	
	// atualiza para 'ativo' a situação do projeto especificado pelo id
	public void atualizarParaAtivo(int idProjeto) {
		
		try {
				var connection = ConnectionFactory.getConnection();
				String sql;
				sql = "Update " + BancoTabela.PROJETO + " set id_situacao = 3 where id_projeto = ? ";
				
				System.out.print(sql);
				PreparedStatement stm =  connection.prepareStatement(sql);
				stm.setInt(1, idProjeto);
				stm.executeUpdate();
						
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	}
	
	// atualiza o banco com o projeto passado
	// usa o id do projeto para encontrar a ocorrencia no banco
	public void atualizar(Projeto projeto) {
		
		try {
				var connection = ConnectionFactory.getConnection();
				String sql;
				sql = "Update " + BancoTabela.PROJETO + " set id_situacao = ?"
						+ ", titulo = ? "
						+ ", descricao = ? "
						+ " where id_projeto = ? ";
				
				System.out.print(sql);
				PreparedStatement stm =  connection.prepareStatement(sql);
				stm.setInt(1, SituacaoProjeto.toInt(projeto.getSituacao()));
				stm.setString(2, projeto.getTitulo());
				stm.setString(3, projeto.getDescricao());
				stm.setInt(4, projeto.getId());
				stm.executeUpdate();
						
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	}
	
	


	public boolean deletar() {
		// TODO Auto-generated method stub
		return false;
	}
		
		
}