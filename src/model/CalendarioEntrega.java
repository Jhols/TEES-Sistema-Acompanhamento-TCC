package model;

public class CalendarioEntrega {
	private int idCalendario, idTurma, idSemestre;
	private String titulo, descricao;
	
	public CalendarioEntrega() {
		super();
	}
	
	public CalendarioEntrega(int idCalendario, int idTurma, int idSemestre, String titulo, String descricao) {
		super();
		this.idCalendario = idCalendario;
		this.idTurma = idTurma;
		this.idSemestre = idSemestre;
		this.titulo = titulo;
		this.descricao = descricao;
	}
	
	public int getIdCalendario() {
		return idCalendario;
	}
	public void setIdCalendario(int idCalendario) {
		this.idCalendario = idCalendario;
	}
	public int getIdProfessor() {
		return idTurma;
	}
	public void setIdProfessor(int idTurma) {
		this.idTurma = idTurma;
	}
	public int getIdSemestre() {
		return idSemestre;
	}
	public void setIdSemestre(int idSemestre) {
		this.idSemestre = idSemestre;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
 
 