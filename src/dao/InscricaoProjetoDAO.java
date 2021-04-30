package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import enums.BancoTabela;
import enums.SituacaoInscricao;
import model.Aluno;
import model.InscricaoProjeto;
import model.Projeto;
import util.ConnectionFactory;

public class InscricaoProjetoDAO {
	
	private static InscricaoProjetoDAO uniqueInstance; //Singleton
	
	private InscricaoProjetoDAO() { }
	
	public static InscricaoProjetoDAO getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new InscricaoProjetoDAO();
		return uniqueInstance;
	}
	
	public InscricaoProjeto findById() {
		return null;
	}
	
	public InscricaoProjeto findByAlunoAndProjeto(Aluno aluno, Projeto projeto) {
		InscricaoProjeto inscricao = null;
		String sql;
		ResultSet resultado = null;
		
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		sql = "SELECT * FROM " + BancoTabela.INSCRICAO_ALUNO_PROJETO
				+ " WHERE id_aluno = " + aluno.getId() + " AND id_projeto = " + projeto.getId() + ";";
		
		Statement stm = null;
		try {
			stm = conexao.createStatement();
			resultado = stm.executeQuery(sql);
			
			if (resultado.next()) {	//Caso encontre algum resultado na consulta, atribui os dados � inscri��o a ser retornada
				inscricao = new InscricaoProjeto();
				
				inscricao.getAluno().setId(resultado.getInt(BancoTabela.INSCRICAO_ALUNO_PROJETO + ".id_aluno"));
				inscricao.getProjeto().setId(resultado.getInt(BancoTabela.INSCRICAO_ALUNO_PROJETO + ".id_projeto"));
				inscricao.setSituacaoInscricao(SituacaoInscricao.valueOf(resultado.getString(BancoTabela.SITUACAO_ALUNO_PROJETO + ".descricao").toUpperCase()));
				
				inscricao.setAluno(AlunoDAO.getInstance().findById(inscricao.getAluno().getId()));
			}
			else {
				System.out.println("N�o foi encontrada a inscri��o procurada");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {resultado.close();}catch(SQLException e){e.printStackTrace();}
			try {stm.close();}catch(SQLException e){e.printStackTrace();}
			try {conexao.close();}catch(SQLException e){e.printStackTrace();}
		}
		
		return inscricao;
	}
	
	public ArrayList<InscricaoProjeto> pesquisarTodasInscricoes() {
		
		ArrayList<InscricaoProjeto> inscricoes = new ArrayList<>();		
		ResultSet resultado = null;
		String sql;
		
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sql = "SELECT * FROM " + BancoTabela.INSCRICAO_ALUNO_PROJETO + " INNER JOIN " + BancoTabela.SITUACAO_PROJETO +
				" WHERE " + BancoTabela.INSCRICAO_ALUNO_PROJETO + ".id_situacao_aluno_projeto = " + BancoTabela.SITUACAO_ALUNO_PROJETO + ".id_situacao_aluno_projeto;";
		
		Statement stm = null;
		try {
			stm = conexao.createStatement();
			resultado = stm.executeQuery(sql);
			
			while (resultado.next()) {
				InscricaoProjeto inscricao = new InscricaoProjeto();
				
				inscricao.getAluno().setId(resultado.getInt(BancoTabela.INSCRICAO_ALUNO_PROJETO + ".id_aluno"));
				inscricao.getProjeto().setId(resultado.getInt(BancoTabela.INSCRICAO_ALUNO_PROJETO + ".id_projeto"));
				inscricao.setSituacaoInscricao(SituacaoInscricao.valueOf(resultado.getString(BancoTabela.SITUACAO_ALUNO_PROJETO + ".descricao").toUpperCase()));
				
				inscricao.setAluno(AlunoDAO.getInstance().findById(inscricao.getAluno().getId()));
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {resultado.close();}catch(SQLException e){e.printStackTrace();}
			try {stm.close();}catch(SQLException e){e.printStackTrace();}
			try {conexao.close();}catch(SQLException e){e.printStackTrace();}
		}
		
		return inscricoes;
	}
	
	public boolean incluir(InscricaoProjeto inscricao) {
		String sql = null;
		int respostaInsert = 0;
		
		Connection conexao = null;
		Statement stm = null;
		try {
			conexao = ConnectionFactory.getConnection();
			
			stm = conexao.createStatement();
			
			//Se a inscri��o n�o existir no banco, cria uma nova
			if (findByAlunoAndProjeto(inscricao.getAluno(), inscricao.getProjeto()) == null) {
				sql = "INSERT INTO " + BancoTabela.INSCRICAO_ALUNO_PROJETO + " (id_aluno, id_projeto, id_situacao_aluno_projeto) "
						+ "VALUES ("
						+ inscricao.getAluno().getId() +", "
						+ inscricao.getProjeto().getId() +", "
						+ "(SELECT id_situacao_aluno_projeto FROM "+ BancoTabela.SITUACAO_ALUNO_PROJETO 
						+" WHERE "+ BancoTabela.SITUACAO_ALUNO_PROJETO+".descricao = '" + inscricao.getSituacaoInscricao().toString().toLowerCase() + "'));";
				
				respostaInsert = stm.executeUpdate(sql);
			}
			//Sen�o, atualiza a situa��o da inscri��o encontrada para 'candidato'
			else {
				SituacaoInscricao situacao = SituacaoInscricao.CANDIDATO;
				respostaInsert = atualizar(inscricao, situacao) ? 1 : 0;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {stm.close();}catch(SQLException e){e.printStackTrace();}
			try {conexao.close();}catch(SQLException e){e.printStackTrace();}
		}
		return respostaInsert > 0 ? true : false;
	}
	
	public boolean atualizar(InscricaoProjeto inscricao, SituacaoInscricao situacaoInscricao) {
		String sql;
		int respostaUpdate = 0;

		Connection conexao = null;
		Statement stm = null;
		try {
			conexao = ConnectionFactory.getConnection();
			
			stm = conexao.createStatement();
			
			sql = "UPDATE " + BancoTabela.INSCRICAO_ALUNO_PROJETO
				+ " SET " + BancoTabela.INSCRICAO_ALUNO_PROJETO+".id_situacao_aluno_projeto = "
					+ "(SELECT id_situacao_aluno_projeto FROM " + BancoTabela.SITUACAO_ALUNO_PROJETO 
						+ " WHERE " + BancoTabela.SITUACAO_ALUNO_PROJETO+".descricao = '"+ situacaoInscricao.toString().toLowerCase() +"') "
				+ " WHERE id_aluno = " + inscricao.getAluno().getId() + " AND id_projeto = " + inscricao.getProjeto().getId() + ";";
			
			respostaUpdate = stm.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			inscricao.setSituacaoInscricao(situacaoInscricao); //Atualiza a situa��o da inscri��o em caso de sucesso. 
			try {stm.close();}catch(SQLException e){e.printStackTrace();}
			try {conexao.close();}catch(SQLException e){e.printStackTrace();}
		}
		return respostaUpdate > 0 ? true : false;
	}

	public Boolean deletar(InscricaoProjeto inscricao) {
		SituacaoInscricao situacao = SituacaoInscricao.DESVINCULADO;
		boolean respostaDelete = false;
		
		respostaDelete = atualizar(inscricao, situacao);
		
		return respostaDelete;
	}

	public static ArrayList<InscricaoProjeto> pesquisarInscricoesParaProjeto(int idProjeto) {
		ArrayList<InscricaoProjeto> inscricoes = new ArrayList<InscricaoProjeto>();
		
		try {
			Connection con = ConnectionFactory.getConnection();
			String sql = "Select * from " + BancoTabela.INSCRICAO_PROJETO.getNomeTabela()
					+ " where " + BancoTabela.INSCRICAO_PROJETO.getNomeTabela() +".id_projeto = ? ";
			
			PreparedStatement stm =  con.prepareStatement(sql);
			stm.setInt(1, idProjeto);
			ResultSet resultado = stm.executeQuery();
			
			while (resultado.next()) {
				InscricaoProjeto inscricao = new InscricaoProjeto();
				popularInscricao(inscricao, resultado);
				inscricoes.add(inscricao);
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return inscricoes;
	}
	
	public static void popularInscricao(InscricaoProjeto inscricao,  ResultSet resultado) throws SQLException {
		inscricao.setIdInscricao(resultado.getInt("id_inscricao_aluno_projeto"));
		inscricao.setIdAluno(resultado.getInt("id_aluno"));
		inscricao.setIdProjeto(resultado.getInt("id_projeto"));
		inscricao.setIdSituacaoInscricao(resultado.getInt("id_situacao_aluno_projeto"));
	}

}
