package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
	
	//Procura por uma inscricao pelo seu id
	public InscricaoProjeto findById() {
		return null;
	}
	
	//Pesquisa por todas as inscricoes de um aluno
	public ArrayList<InscricaoProjeto> findAllByAluno(Aluno aluno) {
		ArrayList<InscricaoProjeto> inscricoes = new ArrayList<>();
		InscricaoProjeto inscricao = null;
		String sql;
		ResultSet resultado = null;
		
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		sql = "SELECT * FROM "+ BancoTabela.INSCRICAO_ALUNO_PROJETO +" \n"
				+ " INNER JOIN "+ BancoTabela.SITUACAO_ALUNO_PROJETO +" \n"
				+ "  ON " + BancoTabela.INSCRICAO_ALUNO_PROJETO+".id_situacao_aluno_projeto = " + BancoTabela.SITUACAO_ALUNO_PROJETO+".id_situacao_aluno_projeto"+" \n"
				
				+ " INNER JOIN " + BancoTabela.PROJETO +" \n"
				+ "  ON " + BancoTabela.INSCRICAO_ALUNO_PROJETO+".id_projeto = "+ BancoTabela.PROJETO+".id_projeto"+" \n"
				+ " WHERE " + BancoTabela.INSCRICAO_ALUNO_PROJETO + ".id_aluno=" + aluno.getId() + ";";
		
		
		//System.out.println(sql);
		Statement stm = null;
		try {
			stm = conexao.createStatement();
			resultado = stm.executeQuery(sql);
			
			while (resultado.next()) {	//Caso encontre algum resultado na consulta, atribui os dados a inscricao a ser retornada
				inscricao = new InscricaoProjeto();
				
				inscricao.setId(resultado.getInt("id_inscricao_aluno_projeto"));
				inscricao.getAluno().setId(resultado.getInt("id_aluno"));
				inscricao.getProjeto().setId(resultado.getInt("id_projeto"));
				inscricao.setSituacaoInscricao(SituacaoInscricao.valueOf(resultado.getString("descricao").toUpperCase()));
				
				inscricao.setAluno(AlunoDAO.getInstance().findById(inscricao.getAluno().getId()));
				inscricao.setProjeto(ProjetoDAO.getInstance().findById(inscricao.getProjeto().getId()));
				
				inscricoes.add(inscricao); //Insere a inscricao na lista de inscricoes do aluno
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			
		}
		System.out.println("Resultados de findAllByAluno(): ");
		for (InscricaoProjeto in : inscricoes) {
			System.out.println(in);
		}
		return inscricoes;
	}
	
	//Procura por uma inscricao especifica de um aluno em relacao a um projeto
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
		
		sql = "SELECT * FROM " + BancoTabela.INSCRICAO_ALUNO_PROJETO + " INNER JOIN " + BancoTabela.SITUACAO_ALUNO_PROJETO
				+ " ON "+ BancoTabela.INSCRICAO_ALUNO_PROJETO+".id_situacao_aluno_projeto = "+ BancoTabela.SITUACAO_ALUNO_PROJETO+".id_situacao_aluno_projeto "
				+ " WHERE id_aluno = " + aluno.getId() + " AND id_projeto = " + projeto.getId();
		System.out.println(sql);
		Statement stm = null;
		try {
			stm = conexao.createStatement();
			resultado = stm.executeQuery(sql);
			System.out.println("execute sql");
			
			if (resultado.next()) {	//Caso encontre algum resultado na consulta, atribui os dados � inscri��o a ser retornada
				inscricao = new InscricaoProjeto();
				
				inscricao.setId(resultado.getInt("id_inscricao_aluno_projeto"));
				inscricao.getAluno().setId(resultado.getInt("id_aluno"));
				inscricao.getProjeto().setId(resultado.getInt("id_projeto"));
				inscricao.setSituacaoInscricao(SituacaoInscricao.valueOf(resultado.getString("descricao").toUpperCase()));
				
				inscricao.setAluno(AlunoDAO.getInstance().findById(inscricao.getAluno().getId()));
				
				inscricao.setProjeto(ProjetoDAO.getInstance().findById(inscricao.getProjeto().getId()));
				System.out.println("Inscricao encontrada: AlunoId: " + inscricao.getAluno().getId() + " | ProjetoId: " + inscricao.getProjeto().getId());
			}
			else {
				System.out.println("Nao foi encontrada a inscricao procurada");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {stm.close();}catch(SQLException e){e.printStackTrace();}
		}
		
		return inscricao;
	}
	
	
	// busca no banco uma inscricao cuja situação seja 'associado'
	public InscricaoProjeto pesquisarProjetoAssociado(Aluno aluno) {
		InscricaoProjeto inscricao = null;
		ResultSet resultado = null;
		String sql;
		
		Connection conexao = null;
		try {
			conexao = ConnectionFactory.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sql = "SELECT * FROM "+ BancoTabela.INSCRICAO_ALUNO_PROJETO +" INNER JOIN "+ BancoTabela.SITUACAO_ALUNO_PROJETO 
				+ " ON "+ BancoTabela.INSCRICAO_ALUNO_PROJETO+".id_situacao_aluno_projeto = "+ BancoTabela.SITUACAO_ALUNO_PROJETO+".id_situacao_aluno_projeto"
				+ " WHERE id_aluno = "+ aluno.getId() 
				+ " AND "+ BancoTabela.SITUACAO_ALUNO_PROJETO+".descricao = '"+ SituacaoInscricao.ASSOCIADO.toString().toLowerCase() +"';";
		
		Statement stm = null;
		try {
			stm = conexao.createStatement();
			resultado = stm.executeQuery(sql);
			
			if (resultado.next()) {
				inscricao = new InscricaoProjeto();
				
				inscricao.setId(resultado.getInt("id_inscricao_aluno_projeto"));
				inscricao.setSituacaoInscricao(SituacaoInscricao.valueOf(resultado.getString("descricao").toUpperCase()));
				// PRECISA SEMPRE PREENCHER OS OBJECTOS DENTRO DO OBJETO SENAO LÀ FORA DA ERRO
				inscricao.setAluno(AlunoDAO.getInstance().findById(inscricao.getAluno().getId()));
				inscricao.setProjeto(ProjetoDAO.getInstance().findById(resultado.getInt("id_projeto")));
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {stm.close();}catch(SQLException e){e.printStackTrace();}
		}
		return inscricao;
	}
	
	//Pesquisa por todas as inscricoes cadastradas
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
		
		sql = "SELECT * FROM " + BancoTabela.INSCRICAO_ALUNO_PROJETO + " INNER JOIN " + BancoTabela.SITUACAO_ALUNO_PROJETO +
				" ON " + BancoTabela.INSCRICAO_ALUNO_PROJETO + ".id_situacao_aluno_projeto = " + BancoTabela.SITUACAO_ALUNO_PROJETO + ".id_situacao_aluno_projeto;";
		
		Statement stm = null;
		try {
			stm = conexao.createStatement();
			resultado = stm.executeQuery(sql);
			
			while (resultado.next()) {
				InscricaoProjeto inscricao = new InscricaoProjeto();
				
				inscricao.setId(resultado.getInt("id_inscricao_aluno_projeto"));
				inscricao.getAluno().setId(resultado.getInt("id_aluno"));
				inscricao.getProjeto().setId(resultado.getInt("id_projeto"));
				inscricao.setSituacaoInscricao(SituacaoInscricao.valueOf(resultado.getString("descricao").toUpperCase()));
				
				inscricao.setAluno(AlunoDAO.getInstance().findById(inscricao.getAluno().getId()));
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {resultado.close();}catch(SQLException e){e.printStackTrace();}
			try {stm.close();}catch(SQLException e){e.printStackTrace();}
		}
		
		return inscricoes;
	}
		
	
	
	//Inclui uma incricao no banco de dados
	public boolean incluir(InscricaoProjeto inscricao) {
		String sql = null;
		int respostaInsert = 0;
		
		Connection conexao = null;
		Statement stm = null;
		try {
			conexao = ConnectionFactory.getConnection();
			
			stm = conexao.createStatement();
			
			//Se a inscricao nao existir no banco, cria uma nova
			if (findByAlunoAndProjeto(inscricao.getAluno(), inscricao.getProjeto()) == null) {
				sql = "INSERT INTO " + BancoTabela.INSCRICAO_ALUNO_PROJETO + " (id_aluno, id_projeto, id_situacao_aluno_projeto) "
						+ "VALUES ("
						+ inscricao.getAluno().getId() +", "
						+ inscricao.getProjeto().getId() +", "
						+ "(SELECT id_situacao_aluno_projeto FROM "+ BancoTabela.SITUACAO_ALUNO_PROJETO 
						+" WHERE "+ BancoTabela.SITUACAO_ALUNO_PROJETO+".descricao = '" + inscricao.getSituacaoInscricao().toString().toLowerCase() + "'));";
				
				respostaInsert = stm.executeUpdate(sql);
			}
			//Senao, atualiza a situacao da inscricao encontrada para 'candidato'
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
	
	//Atualiza a situacao de uma inscricao
	public boolean atualizar(InscricaoProjeto inscricao, SituacaoInscricao situacaoInscricao) {
		String sql;
		int respostaUpdate = 0;

		Connection conexao = null;
		Statement stm = null;
		try {
			conexao = ConnectionFactory.getConnection();
			
			stm = conexao.createStatement();
			
			sql = "UPDATE " + BancoTabela.INSCRICAO_ALUNO_PROJETO
				+ " SET id_situacao_aluno_projeto = "
					+ "(SELECT id_situacao_aluno_projeto FROM " + BancoTabela.SITUACAO_ALUNO_PROJETO 
						+ " WHERE " + BancoTabela.SITUACAO_ALUNO_PROJETO+".descricao = '"+ situacaoInscricao.toString().toLowerCase() +"') "
				+ " WHERE id_aluno = " + inscricao.getAluno().getIdAluno() + " AND id_projeto = " + inscricao.getProjeto().getId() + ";";
			
			System.out.println("Update SQL= "+sql);
			respostaUpdate = stm.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			inscricao.setSituacaoInscricao(situacaoInscricao); //Atualiza a situacao da inscricao em caso de sucesso. 
			try {stm.close();}catch(SQLException e){e.printStackTrace();}
			try {conexao.close();}catch(SQLException e){e.printStackTrace();}
		}
		return respostaUpdate > 0 ? true : false;
	}

	//Deletar uma inscricao: atualiza a situacao da inscricao para 'desvinculado'.
	public Boolean deletar(InscricaoProjeto inscricao) {
		SituacaoInscricao situacao = SituacaoInscricao.DESVINCULADO;
		boolean respostaDelete = false;
		
		respostaDelete = atualizar(inscricao, situacao);
		
		return respostaDelete;
	}

	//Nome a ser discutido
	//Pesquisa por inscricoes de um projeto que tenha determinada situacao (Nesta funcao: 'candidato')
	public ArrayList<InscricaoProjeto> pesquisarInscricoesDeCandidatoParaProjeto(Projeto projeto) {
		ArrayList<InscricaoProjeto> inscricoes = new ArrayList<InscricaoProjeto>();
		
		try {
			Connection con = ConnectionFactory.getConnection();
			String sql = "Select * from " + BancoTabela.INSCRICAO_ALUNO_PROJETO.getNomeTabela()
					+ " where " + BancoTabela.INSCRICAO_ALUNO_PROJETO.getNomeTabela() +".id_projeto = ? and id_situacao_aluno_projeto = 1";
			
			PreparedStatement stm =  con.prepareStatement(sql);
			stm.setInt(1, projeto.getId());
			ResultSet resultado = stm.executeQuery();
			
			while (resultado.next()) {
				InscricaoProjeto inscricao = new InscricaoProjeto();
				popularInscricao(inscricao, resultado);
				inscricao.setProjeto(projeto);
				inscricoes.add(inscricao);
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return inscricoes;
	}
	
	// busca no banco todas as inscrições de determinado projeto que estejam com determinada situação
	public ArrayList<InscricaoProjeto> pesquisarInscricoesParaProjeto(Projeto projeto, SituacaoInscricao situacaoInscricao) {
		ArrayList<InscricaoProjeto> inscricoes = new ArrayList<InscricaoProjeto>();
		
		try {
			Connection con = ConnectionFactory.getConnection();
			String sql = "Select * from " + BancoTabela.INSCRICAO_ALUNO_PROJETO.getNomeTabela()
					+ " where " + BancoTabela.INSCRICAO_ALUNO_PROJETO.getNomeTabela() +".id_projeto = ? and id_situacao_aluno_projeto = "
					+ "(Select id_situacao_aluno_projeto from "+ BancoTabela.SITUACAO_ALUNO_PROJETO.getNomeTabela() 
					+ " where "+BancoTabela.SITUACAO_ALUNO_PROJETO.getNomeTabela()+".descricao = ?)";
			System.out.println(sql);
			PreparedStatement stm =  con.prepareStatement(sql);
			stm.setInt(1, projeto.getId());
			stm.setString(2, situacaoInscricao.toString().toLowerCase());
			ResultSet resultado = stm.executeQuery();
			
			while (resultado.next()) {
				InscricaoProjeto inscricao = new InscricaoProjeto();
				popularInscricao(inscricao, resultado);
				inscricao.setProjeto(projeto);
				inscricoes.add(inscricao);
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return inscricoes;
	}

	
	// preenche os dados do objeto inscricao a partir de um resultado SQL
	public void popularInscricao(InscricaoProjeto inscricao,  ResultSet resultado) throws SQLException {
		inscricao.setIdInscricao(resultado.getInt("id_inscricao_aluno_projeto"));
		inscricao.setAluno(AlunoDAO.pesquisarAlunoPorIdAluno(resultado.getInt("id_aluno")));
		inscricao.setProjeto(ProjetoDAO.pesquisarProjetoPorIdProjeto(resultado.getInt("id_projeto")));
		inscricao.setIdSituacaoInscricao(resultado.getInt("id_situacao_aluno_projeto"));
		
	}
	
	//Nome a ser discutido
	//Pesquisa por inscricoes de um aluno que possuam uma determinada situacao (nesta funcao: 'associado')
	public ArrayList<InscricaoProjeto> pesquisarInscricoesPorAluno(int idAluno) {
	ArrayList<InscricaoProjeto> inscricoes = new ArrayList<InscricaoProjeto>();
	
	try {
		Connection con = ConnectionFactory.getConnection();
		String sql = "Select * from " + BancoTabela.INSCRICAO_ALUNO_PROJETO.getNomeTabela()
				+ " where " + BancoTabela.INSCRICAO_ALUNO_PROJETO.getNomeTabela() +".id_aluno = ? and id_situacao_aluno_projeto = 2";
		
		PreparedStatement stm =  con.prepareStatement(sql);
		stm.setInt(1, idAluno);
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
	
	//Nome a ser discutido
	//Pesquisa por inscricoes de um aluno que possuam uma determinada situacao 
	public ArrayList<InscricaoProjeto> pesquisarInscricoesPorAluno(int idAluno, SituacaoInscricao situacaoInscricao) {
		ArrayList<InscricaoProjeto> inscricoes = new ArrayList<InscricaoProjeto>();
		
		try {
			Connection con = ConnectionFactory.getConnection();
			String sql = "Select * from " + BancoTabela.INSCRICAO_ALUNO_PROJETO.getNomeTabela()
					+ " where " + BancoTabela.INSCRICAO_ALUNO_PROJETO.getNomeTabela() +".id_aluno = ? and"
					+ " id_situacao_aluno_projeto = " 
					+ "(SELECT id_situacao_aluno_projeto FROM " + BancoTabela.SITUACAO_ALUNO_PROJETO 
					+ " WHERE " + BancoTabela.SITUACAO_ALUNO_PROJETO+".descricao = ?) ";
			
			PreparedStatement stm =  con.prepareStatement(sql);
			stm.setInt(1, idAluno);
			stm.setString(2, situacaoInscricao.toString().toLowerCase());
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
	
	// busca uma inscricao a partir do seu id_inscricao
	public InscricaoProjeto pesquisarInscricaoPorId(int idInscricao) {
		InscricaoProjeto inscricao = null;
		
		
		try {
			Connection con = ConnectionFactory.getConnection();
			String sql = "Select * from " + BancoTabela.INSCRICAO_ALUNO_PROJETO.getNomeTabela()
			+ " where "+BancoTabela.INSCRICAO_ALUNO_PROJETO.getNomeTabela()+".id_inscricao_aluno_projeto = ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setInt(1, idInscricao);
			ResultSet resultado = stm.executeQuery();
			if (resultado.next()) {
				inscricao = new InscricaoProjeto();
				popularInscricao(inscricao, resultado);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return inscricao;
	}
	
	
	//feito por @carol, por favor n�o modificar a func�o abaixo
	// busca uma inscricao que contenha o aluno e o projeto especificado
		public InscricaoProjeto pesquisarAlunoNoProjeto(Aluno aluno, Projeto projeto) {
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
				+ " WHERE id_aluno = " + aluno.getIdAluno() + " AND id_projeto = " + projeto.getId() + ";";
		
		Statement stm = null;
		try {
			stm = conexao.createStatement();
			resultado = stm.executeQuery(sql);
			
			if (resultado.next()) {	//Caso encontre algum resultado na consulta, atribui os dados � inscri��o a ser retornada
				inscricao = new InscricaoProjeto();
				inscricao.setAluno(aluno);
				inscricao.setProjeto(projeto);
			}
			else {
				System.out.println("Nao foi encontrada a inscricao procurada");
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
	

}