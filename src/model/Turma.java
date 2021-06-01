package model;

public class Turma {
	private int id;
	private String nome, semestre;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSemestre() {
		return semestre;
	}
	public void setSemestre(String semestre) {
		this.semestre = semestre;
	}
	public Turma(int id, String nome, String semestre) {
		super();
		this.id = id;
		this.nome = nome;
		this.semestre = semestre;
	}
	
	public Turma() {}
	@Override
	public String toString() {
		return "Turma [id=" + id + ", nome=" + nome + ", semestre=" + semestre + "]";
	}
	
	
}
