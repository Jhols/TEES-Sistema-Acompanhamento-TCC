package model;

import java.util.ArrayList;

public class Professor extends Pessoa {
	
	String matricula;
	
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	ArrayList<Integer> projetos;
	
	// TODO: CalendarioDeEntregas calendarioDeEntregas
	
	public String toString() {
		return "Professor: matricula " + matricula + "; " + super.toString();
	}
}
