package model;

import java.sql.SQLException;

import enums.BancoTabela;
import enums.PerfilPessoa;

public abstract class PessoaFactory {
	
	//Cria a instância de uma pessoa a partir de seu perfil. 
	public static Pessoa getPessoa(PerfilPessoa perfil) {
		if (perfil == PerfilPessoa.ALUNO)
			return new Aluno();
		if (perfil == PerfilPessoa.PROFESSOR)
			return new Professor();
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
	
	
	/// Cria um objeto pessoa do perfil especificado e preenche os dados base de pessoa do resultado
	public static Pessoa getPessoa(PerfilPessoa perfil, java.sql.ResultSet resultado) throws NumberFormatException, SQLException {
		Pessoa pessoa = getPessoa(perfil);
		pessoa.setId(Integer.parseInt(resultado.getString(BancoTabela.PESSOA + ".id_pessoa")));
		pessoa.setNome(resultado.getString(BancoTabela.PESSOA + ".nome"));
		pessoa.setEmail(resultado.getString(BancoTabela.PESSOA + ".email"));
		pessoa.setTelefone(resultado.getString(BancoTabela.PESSOA + ".telefone"));
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
