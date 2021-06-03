package model;

import java.sql.Date;

public class Turma {
	private int id;
	private String  semestre;
	private Date inicioSemestre, finalSemestre;
	private String nome;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSemestre() {
		return semestre;
	}
	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}
	public Date getInicioSemestre() {
		return inicioSemestre;
	}
	public void setInicioSemestre(Date inicioSemestre) {
		this.inicioSemestre = inicioSemestre;
	}
	public Date getFinalSemestre() {
		return finalSemestre;
	}
	public void setFinalSemestre(Date finalSemestre) {
		this.finalSemestre = finalSemestre;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
	
	
}
