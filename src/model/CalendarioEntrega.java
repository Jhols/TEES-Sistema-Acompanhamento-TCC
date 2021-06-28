package model;

public class CalendarioEntrega {
	private int idCalendario, idTurma, idSemestre;
	private String descricao;
	
	public CalendarioEntrega() {
		super();
	}
	
	public CalendarioEntrega(int idTurma) {
		super();
		this.idTurma = idTurma;
	}
	
	public CalendarioEntrega(int idCalendario, int idTurma, int idSemestre, String descricao) {
		super();
		this.idCalendario = idCalendario;
		this.idTurma = idTurma;
		this.idSemestre = idSemestre;
		this.descricao = descricao;
	}
	
	public int getIdCalendario() {
		return idCalendario;
	}
	public void setIdCalendario(int idCalendario) {
		this.idCalendario = idCalendario;
	}
	public int getIdTurma() {
		return idTurma;
	}
	public void setIdTurma(int idTurma) {
		this.idTurma = idTurma;
	}
	public int getIdSemestre() {
		return idSemestre;
	}
	public void setIdSemestre(int idSemestre) {
		this.idSemestre = idSemestre;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
 
 