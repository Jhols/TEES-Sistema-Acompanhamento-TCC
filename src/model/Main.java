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

	public static void main(String[] args) {

		Pessoa aluno = PessoaFactory.getPessoa(Perfil.ALUNO, null, "0715123");
		Projeto projeto = new Projeto("projeto roots");
		
		aluno = AlunoDAO.getInstance().findByMatricula(((Aluno) aluno).getMatricula());
		projeto = ProjetoDAO.getInstance().findByTitulo(projeto.getTitulo());
		
		InscricaoProjeto inscricao = new InscricaoProjeto(((Aluno) aluno), projeto);
		
	}
}
