package model;

import java.util.ArrayList;

import dao.AlunoDAO;
import dao.ProfessorDAO;
import dao.ProjetoDAO;
import enums.BancoTabela;
import enums.Perfil;
import enums.SituacaoProjeto;

public class Main {

	public static void main(String[] args) {
		
		Pessoa prof1 = PessoaFactory.getPessoa(Perfil.PROFESSOR, "Amorim", "0158532");
		Pessoa alun1 = PessoaFactory.getPessoa(Perfil.ALUNO, "Joao", "071234567");
		ArrayList<Projeto> projetos = new ArrayList<>();
		
		projetos = ProjetoDAO.pesquisarProjetosDisponiveis();
		
		System.out.println("Projetos Disponiveis:");
		for (Projeto projeto : projetos) {
			System.out.println("  - " + projeto.getTitulo());
		}
		
		System.out.println("\n\nOutros:");
		Projeto proj = new Projeto();
		proj = projetos.get(1);
		//Projeto proj = new Projeto("Controle de fluido no ambiente com arduino");
		System.out.println("Situacao antes de ser atribuida: " + proj.getSituacao());
		
		((Professor) prof1).addProjeto(proj);
		((Aluno) alun1).setProjeto(proj);
		
		System.out.println("\nProfessor: " + prof1.getNome() + " | Titulo Projeto: " + ((Professor) prof1).getProjeto(0).getTitulo());
		System.out.println("Aluno: " + alun1.getNome() + " | Titulo Projeto: " + ((Aluno) alun1).getProjeto().getTitulo());
		System.out.println("Descricao projeto: " + ((Aluno) alun1).getProjeto().getDescricao());
		System.out.println("Situacao Projeto agora: " + proj.getSituacao());
		
	}

}
