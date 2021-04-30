package model;

import java.util.ArrayList;

import dao.AlunoDAO;
import dao.InscricaoProjetoDAO;
import dao.ProfessorDAO;
import dao.ProjetoDAO;
import enums.BancoTabela;
import enums.PerfilPessoa;
import enums.SituacaoInscricao;
import enums.SituacaoProjeto;

public class Main {

	public static void main(String[] args) {
		
		Pessoa aluno = PessoaFactory.getPessoa(PerfilPessoa.ALUNO, null, "0715123");
		Projeto projeto = new Projeto("projeto roots");
		
		aluno = AlunoDAO.getInstance().findByMatricula(((Aluno) aluno).getMatricula());
		projeto = ProjetoDAO.getInstance().findByTitulo(projeto.getTitulo());
		
		InscricaoProjeto inscricao = new InscricaoProjeto(((Aluno) aluno), projeto);
		
		/*Boolean b = InscricaoProjetoDAOImpl.getInstance().incluir(inscricao);
		
		System.out.println(b.toString());*/
		
		Boolean b = InscricaoProjetoDAO.getInstance().deletar(inscricao);
		
		System.out.println(b.toString());
		
	}

}
