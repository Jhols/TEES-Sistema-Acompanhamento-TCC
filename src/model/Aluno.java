package model;

import java.util.ArrayList;

import enums.SituacaoProjeto;

public class Aluno extends Pessoa {
	private String matricula;

	public Aluno() {
		
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
	
	
	
}