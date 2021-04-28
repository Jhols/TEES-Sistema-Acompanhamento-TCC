package model;

import enums.Perfil;

public class Aluno extends Pessoa {
	private String matricula;
	private	Projeto projeto;

	public Aluno() {
		this.setPerfil(Perfil.ALUNO);
	}
	
	public Aluno (String matricula) {
		this.matricula = matricula;
	}
	
	public Aluno(String nome, String matricula) {
		super(nome);
		this.matricula = matricula;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
		this.projeto.setAluno(this);
	}

	@Override
	public String toString() {
		return "Aluno [matricula=" + matricula + ", projeto=" + projeto + ", Pessoa=" + super.toString() + "]";
	}
	
	
	
	
}