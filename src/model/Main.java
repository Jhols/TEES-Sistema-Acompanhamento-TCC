package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.postgresql.util.PSQLException;

import dao.AlunoDAO;
import dao.InscricaoProjetoDAO;
import dao.ProfessorDAO;
import dao.ProjetoDAO;
import enums.BancoTabela;
import enums.Perfil;
import enums.SituacaoInscricao;
import enums.SituacaoProjeto;
import util.ConnectionFactory;

public class Main {

	public static void main(String[] args) throws SQLException {
		
		//desagra();
		
		/*
		Pessoa aluno = PessoaFactory.getPessoa(Perfil.ALUNO, null, "0715123");
		Projeto projeto = new Projeto("projeto roots");
		
		aluno = AlunoDAO.getInstance().findByMatricula(((Aluno) aluno).getMatricula());
		projeto = ProjetoDAO.getInstance().findByTitulo(projeto.getTitulo());
		
		InscricaoProjeto inscricao = new InscricaoProjeto(((Aluno) aluno), projeto);
		
		*/
		
		Pessoa aluno = PessoaFactory.getPessoa(Perfil.ALUNO, null, "0715789");
		
		aluno = AlunoDAO.getInstance().findByMatricula(((Aluno)aluno).getMatricula());
		
		System.out.println("oi, eu sou " + ((Professor)aluno).getMatricula());
		
		
		/*String sql = "INSERT INTO " + BancoTabela.INSCRICAO_ALUNO_PROJETO + " (id_aluno, id_projeto, id_situacao_aluno_projeto) "
				+ "VALUES (3, 2, 3)";
		for (int i = 1; i < 25; i++) {
			try {
				con.createStatement().execute(sql);
			}
			catch (PSQLException e) {
				System.out.println(e.getMessage());
			}
		}
		
		
		
	}
	
	public static void desagra() {
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
		/*
		sql = "SELECT * FROM "+ BancoTabela.INSCRICAO_ALUNO_PROJETO +" \n"
				+ " INNER JOIN "+ BancoTabela.SITUACAO_ALUNO_PROJETO +" \n"
				+ "  ON " + BancoTabela.INSCRICAO_ALUNO_PROJETO+".id_situacao_aluno_projeto = " + BancoTabela.SITUACAO_ALUNO_PROJETO+".id_situacao_aluno_projeto"+" \n"
				
				+ " INNER JOIN " + BancoTabela.PROJETO +" \n"
				+ "  ON " + BancoTabela.INSCRICAO_ALUNO_PROJETO+".id_projeto = "+ BancoTabela.PROJETO+".id_projeto"+" \n"
				+ " WHERE " + BancoTabela.INSCRICAO_ALUNO_PROJETO + ".id_aluno=" + aluno.getId() + ";";
		*/
		/*sql  = 
				 "SELECT * FROM INSCRICAO_ALUNO_PROJETO "
				+ " INNER JOIN SITUACAO_ALUNO_PROJETO "
				+ "  ON INSCRICAO_ALUNO_PROJETO.id_situacao_aluno_projeto = SITUACAO_ALUNO_PROJETO.id_situacao_aluno_projeto "
				+ " INNER JOIN PROJETO "
				+ "  ON INSCRICAO_ALUNO_PROJETO.id_projeto = PROJETO.id_projeto "
				+ " WHERE INSCRICAO_ALUNO_PROJETO.id_aluno = 3;";
		
		System.out.println(sql);
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
			
			try {stm.close();}catch(SQLException e){e.printStackTrace();}
		}
		
		System.out.println("Resultados: ");
		for (InscricaoProjeto in : inscricoes) {
			System.out.println(in);
		}*/
		
	}
}
