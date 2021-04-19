package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import enums.BancoTabela;
import enums.PerfilPessoa;
import model.Professor;
import model.PessoaFactory;

public class ProfessorDAO {

	@SuppressWarnings("finally")
	public static ArrayList<Professor> pesquisarTodosProfessores() {
		ArrayList<Professor> professores = new ArrayList<>();
		ResultSet resultado = PessoaDAO.selecionarPorPerfil(BancoTabela.PROFESSOR);
		
		try {
			while(resultado.next()) {
				Professor professor = ((Professor) PessoaFactory.getPessoa(PerfilPessoa.PROFESSOR, resultado));
				professor.setMatricula(resultado.getString(BancoTabela.PROFESSOR + ".matricula"));
				
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
