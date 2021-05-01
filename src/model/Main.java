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
		
		Pessoa prof1 = PessoaFactory.getPessoa(Perfil.PROFESSOR, "Amorim", "0158532");
		Pessoa alun1 = PessoaFactory.getPessoa(Perfil.ALUNO, "Joao", "071234567");
		ArrayList<Projeto> projetos = new ArrayList<>();
		
		aluno = AlunoDAO.getInstance().findByMatricula(((Aluno) aluno).getMatricula());
		projeto = ProjetoDAO.getInstance().findByTitulo(projeto.getTitulo());
		
		InscricaoProjeto inscricao = new InscricaoProjeto(((Aluno) aluno), projeto);
		
		/*Boolean b = InscricaoProjetoDAOImpl.getInstance().incluir(inscricao);
		
		System.out.println(b.toString());*/
		
		Boolean b = InscricaoProjetoDAO.getInstance().deletar(inscricao);
		
		System.out.println(b.toString());
		
	}

}
