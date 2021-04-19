package dao;

import java.util.ArrayList;

import enums.SituacaoProjeto;
import model.Projeto;

public class ProjetoDAO {

	// Consulta todos os projetos que estão no banco e retorna os que estão disponíveis para o aluno escolher se candidatar
		public ArrayList<Projeto> pesquisarProjetosDisponiveis() {
			
			ArrayList<Projeto> projetos = new ArrayList<>();
			
			Projeto p1 = new Projeto("teste");
			
			projetos.add(p1);
			
			/*//Mantém apenas os projetos cuja situação esteja disponível.
			for (Projeto projeto : projetos) {
				if (projeto.getSituacao() != SituacaoProjeto.DISPONIVEL) {
					projetos.remove(projeto);
				}
			}*/
			
			return projetos;
		}
	
}
