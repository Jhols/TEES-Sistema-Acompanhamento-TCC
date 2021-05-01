package model;

import java.util.ArrayList;

import dao.AlunoDAO;
import dao.InscricaoProjetoDAO;
import dao.ProfessorDAO;
import dao.ProjetoDAO;
import enums.BancoTabela;
import enums.Perfil;
import enums.SituacaoInscricao;
import enums.SituacaoProjeto;

public class Main {

	public static void main(String[] args) {
		
		ArrayList<Projeto> projetos = new ArrayList<Projeto>();
		projetos = ProjetoDAO.getInstance().pesquisarProjetosDisponiveis();
		
		Pessoa aluno = PessoaFactory.getPessoa(Perfil.ALUNO);
		
		((Aluno) aluno).setMatricula("0715456");
		aluno = AlunoDAO.getInstance().findByMatricula(((Aluno)aluno).getMatricula());
		
		InscricaoProjeto inscricao = new InscricaoProjeto();
		
		inscricao = InscricaoProjetoDAO.getInstance().pesquisarProjetoAssociado((Aluno) aluno);
		System.out.println();
		
		
	}
}
