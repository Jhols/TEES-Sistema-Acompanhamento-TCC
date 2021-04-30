package model;

import enums.Perfil;

public class Aluno extends Pessoa {
	private String matricula;

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

	@Override
	public String toString() {
		return "Aluno [matricula=" + matricula + ", Pessoa=" + super.toString() + "]";
	}
	
	
	
	
}