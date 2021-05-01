package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import enums.BancoTabela;
import model.InscricaoProjeto;
import util.ConnectionFactory;

public class InscricaoProjetoDAO {
	
	
	public static ArrayList<InscricaoProjeto> pesquisarInscricoesDeCandidatoParaProjeto(int idProjeto) {
		ArrayList<InscricaoProjeto> inscricoes = new ArrayList<InscricaoProjeto>();
		
		try {
			Connection con = ConnectionFactory.getConnection();
			String sql = "Select * from " + BancoTabela.INSCRICAO_PROJETO.getNomeTabela()
					+ " where " + BancoTabela.INSCRICAO_PROJETO.getNomeTabela() +".id_projeto = ? and id_situacao_aluno_projeto = 1";
			
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
	
	
	public static ArrayList<InscricaoProjeto> pesquisarInscricoesPorAluno(int idAluno) {
		ArrayList<InscricaoProjeto> inscricoes = new ArrayList<InscricaoProjeto>();
		
		try {
			Connection con = ConnectionFactory.getConnection();
			String sql = "Select * from " + BancoTabela.INSCRICAO_PROJETO.getNomeTabela()
					+ " where " + BancoTabela.INSCRICAO_PROJETO.getNomeTabela() +".id_aluno = ? and id_situacao_aluno_projeto = 2";
			
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

}
