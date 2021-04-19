package model;

import java.util.ArrayList;

import Banco.AlunoDAO;
import enums.PerfilPessoa;
import enums.SituacaoProjeto;

public class Main {

	public static void main(String[] args) {
				
		ArrayList<Pessoa> pessoas = new ArrayList();
		
		pessoas = AlunoDAO.pesquisarTodosAlunos();
		
		for (Pessoa pessoa : pessoas) {
			System.out.println("Nome: " + pessoa.getNome() + " | matricula: " + ((Aluno) pessoa).getMatricula());
		}
		
	}

}
