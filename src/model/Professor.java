package model;

import java.util.ArrayList;

public class Professor extends Pessoa {
	
	String matricula;
	ArrayList<Projeto> projetos = new ArrayList<>();
	
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	public void addProjeto(Projeto projeto) {
		this.projetos.add(projeto);
	}

	public ArrayList<Projeto> getAllProjetos() {
		return projetos;
		
	}
	
	public Projeto getProjeto(int indice) {
		return projetos.get(indice);
	}
	
	// TODO: CalendarioDeEntregas calendarioDeEntregas
	
	public String toString() {
		return "Professor: matricula " + matricula + "; " + super.toString();
	}
}
