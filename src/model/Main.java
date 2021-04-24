package model;

import java.util.ArrayList;

import dao.AlunoDAOImpl;
import dao.ProfessorDAOImpl;
import dao.ProjetoDAOImpl;
import enums.BancoTabela;
import enums.PerfilPessoa;
import enums.SituacaoInscricao;
import enums.SituacaoProjeto;

public class Main {

	public static void main(String[] args) {
		
		Pessoa a1 = PessoaFactory.getPessoa(PerfilPessoa.ALUNO);
		a1.setId(3);
		System.out.println("ID: " + a1.getId());
		a1 = AlunoDAOImpl.getInstance().findById(a1.getId());
		System.out.println("ID: " + a1.getId());
		System.out.println(a1.getNome() + " | " + ((Aluno) a1).getMatricula());
		
		Projeto projeto = new Projeto();
		projeto.setId(2);
		projeto = ProjetoDAOImpl.getInstance().findById(projeto.getId());
		
		InscricaoProjeto inscricao = new InscricaoProjeto();
		
		inscricao.setAluno((Aluno) a1);
		inscricao.setProjeto(projeto);
		inscricao.setSituacaoInscricao(SituacaoInscricao.CANDIDATO);
		System.out.println("Projeto: " + inscricao.getProjeto().getTitulo());
		System.out.println("Situação: " + inscricao.getProjeto().getSituacao());
		//antes de ser associado
		((Aluno) a1).setProjeto(projeto);
		//depois de ser associado
		System.out.println("Situação: " + inscricao.getProjeto().getSituacao());
	}

}
