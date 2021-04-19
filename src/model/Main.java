package model;

import java.util.ArrayList;

import dao.AlunoDAO;
import dao.ProfessorDAO;
import enums.PerfilPessoa;
import enums.SituacaoProjeto;

public class Main {

	public static void main(String[] args) {
		
		var pessoas = AlunoDAO.pesquisarTodosAlunos();
		
		for (Pessoa pessoa : pessoas) {
			System.out.println("Nome: " + pessoa.getNome() + " | matricula: " + ((Aluno) pessoa).getMatricula());
		}
		
		ArrayList<Professor> professores = ProfessorDAO.pesquisarTodosProfessores();
		for (Professor p : professores) {
			System.out.println(p);
		}
		
	}

}
