package dao;

import java.util.ArrayList;

import enums.SituacaoProjeto;
import model.Projeto;

public class ProjetoDAO {

	// Consulta todos os projetos que est�o no banco e retorna os que est�o dispon�veis para o aluno escolher se candidatar
		public ArrayList<Projeto> pesquisarProjetosDisponiveis() {
			
			ArrayList<Projeto> projetos = new ArrayList<>();
			
			Projeto p1 = new Projeto("teste");
			
			projetos.add(p1);
			
			/*//Mant�m apenas os projetos cuja situa��o esteja dispon�vel.
			for (Projeto projeto : projetos) {
				if (projeto.getSituacao() != SituacaoProjeto.DISPONIVEL) {
					projetos.remove(projeto);
				}
			}*/
			
			return projetos;
		}
	
}
