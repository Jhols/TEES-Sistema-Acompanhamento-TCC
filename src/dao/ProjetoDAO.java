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
				" ON " + BancoTabela.PROJETO+".id_"+BancoTabela.PROJETO+ " = " + id + 
				" WHERE " + BancoTabela.PROJETO +".id_situacao = " +  BancoTabela.SITUACAO_PROJETO + ".id_situacao_projeto;";
		
		try {
            Statement stm = conexao.createStatement();
            resultado = stm.executeQuery(sql);
            
            resultado.next();
			projeto.setId(id);
			projeto.setTitulo(resultado.getString("titulo"));
			projeto.setDescricao(resultado.getString("descricao"));
			projeto.getProfessor().setId(resultado.getInt("id_professor"));
			projeto.setProfessor(ProfessorDAO.getInstance().findById(projeto.getProfessor().getId()));
			projeto.setSituacao(SituacaoProjeto.fromString(resultado.getString("descricao").toLowerCase()));
            
			stm.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		} finally {
			return projeto;
		}
	}
	
	public static ArrayList<Projeto> pesquisarProjetosDisponiveisEAtivos() {
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
		
		int disponivel = SituacaoProjeto.toInt(SituacaoProjeto.DISPONIVEL);
		int ativo = SituacaoProjeto.toInt(SituacaoProjeto.ATIVO);;
		
		sql = "SELECT * FROM " + BancoTabela.PROJETO + " WHERE id_situacao = "+ disponivel +" OR id_situacao = " + ativo +";";
		
		//System.out.println(sql);
		Statement stm = null;
		try {
			stm = conexao.createStatement();
			resultado = stm.executeQuery(sql);
			
			while(resultado.next()) {
				Projeto projeto = new Projeto();
				projeto.setId(resultado.getInt("id_projeto"));
				projeto.setTitulo(resultado.getString("titulo"));
				projeto.setDescricao(resultado.getString("descricao"));
				projeto.setProfessor(ProfessorDAO.getInstance().findById(resultado.getInt("id_professor")));
				projeto.setSituacao(SituacaoProjeto.fromInt(resultado.getInt("id_situacao")));
				
				projetos.add(projeto);
			}
			stm.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			System.out.println("resultado de findAll() em ProjetoDAO");
			for (Projeto projeto : projetos) {
				System.out.println(projeto);
			}
			try {resultado.close();}catch(SQLException e){e.printStackTrace();}
			try {stm.close();}catch(SQLException e){e.printStackTrace();}
			try {conexao.close();}catch(SQLException e){e.printStackTrace();}
		}
		return projetos;			
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
		projeto.setId(resultado.getInt("id_projeto"));
		projeto.setTitulo(resultado.getString("titulo"));
		projeto.setDescricao(resultado.getString("descricao"));
		projeto.setIdProfessor(resultado.getInt("id_professor"));
		System.out.println("ID DO PROFESSOR DO PROJETO = " +projeto.getIdProfessor());
		projeto.setProfessor(ProfessorDAO.pesquisarPorIdProfessor(projeto.getIdProfessor()));
		System.out.println("PROFESSOR DO PROJETO = " +projeto.getProfessor());
		projeto.setSituacao(SituacaoProjeto.fromInt(resultado.getInt("id_situacao")));
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
						+ " WHERE descricao = '"+ situacao.toString().toLowerCase() +"')";  
		
		//System.out.println(sql);
		try {
			Statement stm = conexao.createStatement();
			resultado = stm.executeQuery(sql);
			
			while(resultado.next()) {
				Projeto projeto = new Projeto();
				projeto.setId(resultado.getInt("id_projeto"));
				projeto.setTitulo(resultado.getString("titulo"));
				projeto.setDescricao(resultado.getString("descricao"));
				projeto.setProfessor(ProfessorDAO.getInstance().findById(resultado.getInt("id_professor")));
				projeto.setSituacao(situacao);
				
				projetos.add(projeto);
			}
			stm.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			System.out.println("resultado de pesquisarProjetosDisponiveis()");
			for (Projeto projeto : projetos) {
				System.out.println(projeto);
			}
				
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
		
		sql = "SELECT * FROM " + BancoTabela.PROJETO + " INNER JOIN " + BancoTabela.SITUACAO_PROJETO
				+ " ON " + BancoTabela.PROJETO+".id_situacao = " + BancoTabela.SITUACAO_PROJETO+".id_situacao_projeto"
				+ " WHERE " + BancoTabela.PROJETO+".titulo = '" + titulo +"'";
				
		
		try {
            Statement stm = conexao.createStatement();
            resultado = stm.executeQuery(sql);
            
            resultado.next();
			projeto.setId(resultado.getInt("id_"+BancoTabela.PROJETO.toString().toLowerCase()));
			projeto.setTitulo(resultado.getString("titulo"));
			projeto.setDescricao(resultado.getString("descricao"));
			projeto.getProfessor().setId(resultado.getInt("id_professor"));
			projeto.setProfessor(ProfessorDAO.getInstance().findById(projeto.getProfessor().getId()));
			projeto.setSituacao(SituacaoProjeto.valueOf(resultado.getString("descricao").toUpperCase()));
            
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
				sql = "UPDATE " + BancoTabela.PROJETO 
						+ " SET id_situacao = " + SituacaoProjeto.toInt(projeto.getSituacao())
						+ ", titulo = '" + projeto.getTitulo() + "'"
						+ ", descricao = '" + projeto.getDescricao() + "'"
						+ " WHERE id_projeto = " + projeto.getId();
				PreparedStatement stm =  connection.prepareStatement(sql);
				System.out.print(sql);
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
	
	public ArrayList<Projeto> buscarProjetosPorIdProfessor(int idProfessor) {
		ArrayList<Projeto> listaProjetos = new ArrayList<Projeto>();
		
		try {
			Connection connection = ConnectionFactory.getConnection();
			String sql = " SELECT * FROM " + BancoTabela.PROJETO 
					+ " INNER JOIN " + BancoTabela.SITUACAO_PROJETO + " ON " + BancoTabela.PROJETO + ".id_situacao = " + BancoTabela.SITUACAO_PROJETO + ".id_situacao_projeto"
					+ " WHERE 1=1 "
					+ " AND " + BancoTabela.PROJETO + ".id_professor = " + idProfessor + " ";
			PreparedStatement stm = connection.prepareStatement(sql);
			ResultSet resultado = stm.executeQuery();
			while (resultado.next()) {
				Projeto projeto = new Projeto();
				popularProjeto(projeto, resultado);
				listaProjetos.add(projeto);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return listaProjetos;
	}
	
			
		
}