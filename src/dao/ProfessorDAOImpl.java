package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import enums.BancoTabela;
import enums.PerfilPessoa;
import model.Professor;
import model.PessoaFactory;

public class ProfessorDAOImpl implements InterfaceDAO {
	
	private static ProfessorDAOImpl uniqueInstance; //Singleton
	
	private ProfessorDAOImpl() { }
	
	public static synchronized ProfessorDAOImpl getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new ProfessorDAOImpl();
		return uniqueInstance;
	}
	
	@SuppressWarnings("finally")
	public ArrayList<Professor> pesquisarTodosProfessores() {
		ArrayList<Professor> professores = new ArrayList<>();
		ResultSet resultado = PessoaDAOImpl.getInstance().selecionarPorPerfil(BancoTabela.PROFESSOR);
		
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

	@Override
	public boolean incluir() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void atualizar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean deletar() {
		// TODO Auto-generated method stub
		return false;
	}
}
