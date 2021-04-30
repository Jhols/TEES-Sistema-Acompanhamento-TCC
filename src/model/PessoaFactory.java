package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import enums.BancoTabela;
import enums.Perfil;

public abstract class PessoaFactory {
	
	//Cria a instância de uma pessoa a partir de seu perfil. 
	public static Pessoa getPessoa(Perfil perfil) {
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
		pessoa.setId(Integer.parseInt(resultado.getString(BancoTabela.PESSOA + ".id_pessoa")));
		pessoa.setNome(resultado.getString(BancoTabela.PESSOA + ".nome"));
		pessoa.setEmail(resultado.getString(BancoTabela.PESSOA + ".email"));
		pessoa.setTelefone(resultado.getString(BancoTabela.PESSOA + ".telefone"));
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
		professor.setMatricula(resultado.getString(BancoTabela.PROFESSOR + ".matricula"));
		var tipo_prof = resultado.getInt(BancoTabela.PROFESSOR + ".tipo_prof");
		professor.setIdProfessor(resultado.getInt(BancoTabela.PROFESSOR + ".id_professor"));
		professor.setTipo(Professor.Tipo.fromInt(tipo_prof));
	}
	
	
	private static void popularDadosAluno(Aluno aluno, ResultSet resulato) throws SQLException {
		/// TODO
	}
}
