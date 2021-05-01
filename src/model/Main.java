package model;

import java.sql.SQLException;
import java.util.ArrayList;

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
		
		var situacaoInscricao = SituacaoInscricao.ASSOCIADO;
		
		System.out.println(situacaoInscricao.toString().toLowerCase());
		System.out.println(BancoTabela.SITUACAO_ALUNO_PROJETO);
		
		var connection = ConnectionFactory.getConnection();
		var stm = connection.createStatement();
		//var result = stm.executeQuery("Select * from situacao_aluno_projeto where situacao_aluno_projeto.descricao = '"+situacaoInscricao.toString().toLowerCase()+"'");
		var result = stm.executeQuery("SELECT id_situacao_aluno_projeto FROM " + BancoTabela.SITUACAO_ALUNO_PROJETO 
						+ " WHERE " + BancoTabela.SITUACAO_ALUNO_PROJETO+".descricao = '"+ situacaoInscricao.toString().toLowerCase() +"' ");
		if (result.next()) {
			System.out.println(result.getInt("id_situacao_aluno_projeto"));
		}
		else {
			System.out.println("Nao encontrou");
		}
		
	}

}
