package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import enums.Perfil;

public abstract class PessoaFactory {
	
	//Cria a instância de uma pessoa a partir de seu perfil. 
	public static Pessoa getPessoa(Perfil  perfil) {
		if (perfil == Perfil.ALUNO)
			return new Aluno();
		if (perfil == Perfil.PROFESSOR)
			return new Professor();
		/*if (tipo.equals("secretario"))
			return new Aluno(nome, matricula);
		else if (tipo.equals("coordenador"))
			return new Professor(nome, matricula);*/
		return null;
	}
	
	public static Pessoa getPessoa(Perfil tipo, String nome) {
		Pessoa pessoa = getPessoa(tipo);
		pessoa.setNome(nome);
		return pessoa;
	}
	public static Pessoa getPessoa(Perfil perfil, String nome, String matricula) {
		Pessoa pessoa = getPessoa(perfil, nome);
		if (perfil == Perfil.ALUNO)
			((Aluno) pessoa).setMatricula(matricula);
		/*else if (perfil == PerfilPessoa.PROFESSOR)
			((Professor) pessoa).setMatricula(matricula);*/
		return pessoa;
	}
	
	/// Cria um objeto pessoa do perfil especificado e preenche os dados do resultado
	public static Pessoa getPessoa(Perfil perfil, ResultSet resultado) throws NumberFormatException, SQLException {
		Pessoa pessoa = getPessoa(perfil);
		popularDadosPessoa(pessoa, resultado);
		return pessoa;
	}
	
	/// popula um objeto Pessoa a partir do resultado de um select 
	/// tambem leva em consideração o perfil 
	private static Pessoa popularDadosPessoa(Pessoa pessoa, ResultSet resultado) throws SQLException {
		System.out.println("ODISGRACA 5");
		pessoa.setId(resultado.getInt("id_pessoa"));
		pessoa.setNome(resultado.getString("nome"));
		pessoa.setEmail(resultado.getString("email"));
		pessoa.setTelefone(resultado.getString("telefone"));
		// de acordo com o perfil da pessoa chama a função adequada
		switch (pessoa.getPerfil()) {
		case ADMINISTRADOR:
			break;
		case ALUNO:
			popularDadosAluno((Aluno)pessoa, resultado);
			break;
		case COORDENADOR:
			break;
		case NENHHUM:
			break;
		case PROFESSOR:
			popularDadosProfessor((Professor)pessoa, resultado);
			break;
		case SECRETARIO:
			break;
		default:
			break;
		
		}
		return pessoa;
	}
	
	/// popula um objeto Professor a partir do resultado de um select 
	private static void popularDadosProfessor(Professor professor, ResultSet resultado) throws SQLException {
		professor.setMatricula(resultado.getString("matricula"));
		var tipo_prof = resultado.getInt("tipo_prof");
		var status_orientador = resultado.getInt("status_orientador");
		professor.setIdProfessor(resultado.getInt("id_professor"));
		professor.setTipo(Professor.Tipo.fromInt(tipo_prof));
		professor.setStatusOrientador(Professor.StatusOrientador.fromInt(status_orientador));
	}
	
	/// popula um objeto Aluno a partir do resultado de um select 
	private static void popularDadosAluno(Aluno aluno, ResultSet resultado) throws SQLException {
		aluno.setMatricula(resultado.getString("matricula"));
		aluno.setIdAluno(resultado.getInt("id_aluno"));
		var statusAlunoTCC=resultado.getInt("status_aluno_tcc");
		aluno.setstatusAlunoTCC(Aluno.StatusAlunoTCC.fromInt(statusAlunoTCC));
	}
}
