package model;

import enums.PerfilPessoa;

public class PessoaFactory {
	
	public static Pessoa getPessoa(PerfilPessoa perfil) {
		if (perfil == PerfilPessoa.ALUNO)
			return new Aluno();
		/*if (tipo.equals("secretario"))
			return new Aluno(nome, matricula);
		else if (tipo.equals("coordenador"))
			return new Professor(nome, matricula);*/
		return null;
	}
	
	public static Pessoa getPessoa(PerfilPessoa tipo, String nome) {
		Pessoa pessoa = getPessoa(tipo);
		pessoa.setNome(nome);
		return pessoa;
	}

	public static Pessoa getPessoa(PerfilPessoa perfil, String nome, String matricula) {
		Pessoa pessoa = getPessoa(perfil, nome);
		if (perfil == PerfilPessoa.ALUNO)
			((Aluno) pessoa).setMatricula(matricula);
		/*else if (perfil == PerfilPessoa.PROFESSOR)
			((Professor) pessoa).setMatricula(matricula);*/
		return pessoa;
	}
	
	/*public static Pessoa getPessoa(PerfilPessoa tipo, String nome, String email) {
		Pessoa pessoa = getPessoa(tipo);
		pessoa.setNome(nome);
		return pessoa;
	}
	
	public static Pessoa getPessoa(PerfilPessoa tipo, String nome, String email, String telefone) {
		Pessoa pessoa = getPessoa(tipo);
		pessoa.setNome(nome);
		return pessoa;
	}*/
	
}
