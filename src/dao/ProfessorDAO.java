package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import enums.BancoTabela;
import enums.Perfil;
import model.Professor;
import model.PessoaFactory;

public class ProfessorDAO {
	
	@SuppressWarnings("finally")
	public static Professor pesquisarProfessorPorIdPessoa(int idPessoa) {
		Professor professor = null;
		ResultSet resultado = PessoaDAO.selecionarPorPerfilEId(Perfil.PROFESSOR, idPessoa);
		
		try {
			if (resultado.next()) {
				professor = ((Professor) PessoaFactory.getPessoa(Perfil.PROFESSOR, resultado));
				// TODO: selecionar projetos do BD para preencher a lista de projetos do professor
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return professor;			
		}
		
	}

	@SuppressWarnings("finally")
	public static ArrayList<Professor> pesquisarTodosProfessores() {
		ArrayList<Professor> professores = new ArrayList<>();
		ResultSet resultado = PessoaDAO.selecionarPorPerfil(BancoTabela.PROFESSOR);
		
		try {
			while(resultado.next()) {
				Professor professor = ((Professor) PessoaFactory.getPessoa(Perfil.PROFESSOR, resultado));
				// TODO: selecionar projetos do BD para preencher a lista de projetos do professor
				
				professores.add(professor);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return professores;			
		}
		
	}
}
