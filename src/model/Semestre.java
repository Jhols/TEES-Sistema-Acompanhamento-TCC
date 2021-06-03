package model;

import java.sql.Date;

public class Semestre {
	private String semestreAtual;
	private int idSemestre;
	private Date inicioSemestre;
	private Date finalSemestre;
	
	public String getSemestreAtual() {
		return semestreAtual;
	}
	public void setSemestreAtual(String semestreAtual) {
		this.semestreAtual = semestreAtual;
	}
	public int getIdSemestre() {
		return idSemestre;
	}
	public void setIdSemestre(int idSemestre) {
		this.idSemestre = idSemestre;
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
	

	public void setInicioSemestre(java.util.Date inicioSemestre) {
		this.inicioSemestre = new Date(inicioSemestre.getTime());
	}
	public void setFinalSemestre(java.util.Date finalSemestre) {
		this.finalSemestre = new Date(finalSemestre.getTime());
	}
	
}
