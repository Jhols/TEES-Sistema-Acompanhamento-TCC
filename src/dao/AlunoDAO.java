package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import enums.BancoTabela;
import enums.PerfilPessoa;
import model.Aluno;
import model.PessoaFactory;

public class AlunoDAO {
	
	@SuppressWarnings("finally")
	public static ArrayList<Aluno> pesquisarTodosAlunos() {
		ArrayList<Aluno> alunos = new ArrayList<>();
		ResultSet resultado = PessoaDAO.selecionarPorPerfil(BancoTabela.ALUNO);
		
		try {
			while(resultado.next()) {
				Aluno aluno = ((Aluno) PessoaFactory.getPessoa(PerfilPessoa.ALUNO, resultado));
				aluno.setMatricula(resultado.getString(BancoTabela.ALUNO + ".matricula"));
				alunos.add(aluno);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return alunos;			
		}
		
	}
	
	
}
