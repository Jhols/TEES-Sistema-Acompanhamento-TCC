package model;

import enums.Perfil;
import enums.SituacaoProjeto;

public class Projeto {
	
	private int id;
	private String titulo, descricao;
	private Professor professor;
	private SituacaoProjeto situacao;
	private int idProfessor;
	
	public Projeto() {
		// TODO Auto-generated constructor stub
		professor = (Professor) PessoaFactory.getPessoa(Perfil.PROFESSOR);
	}
	
	public int getIdProfessor() {
		return idProfessor;
	}

	public void setIdProfessor(int idProfessor) {
		this.idProfessor = idProfessor;
	}

	public Projeto(String titulo) {
		this();
		this.titulo = titulo;
		this.situacao = SituacaoProjeto.DISPONIVEL;
	}
	
	public Projeto(String titulo, SituacaoProjeto situacao) {
		this();
		this.titulo = titulo;
		this.situacao = situacao;
	}

	public Projeto(String titulo, Professor professor) {
		this(titulo);
		this.professor = professor;
	}
	
	public Projeto(String titulo, Professor professor, SituacaoProjeto situacao) {
		this(titulo, situacao);
		this.professor = professor;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public SituacaoProjeto getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoProjeto situacao) {
		this.situacao = situacao;
	}

	@Override
	public String toString() {
		return "Projeto [id=" + id + ", titulo=" + titulo + ", descricao=" + descricao + ", situacao=" + situacao
				+ ", idProfessor=" + idProfessor + "]";
	}
	
	
	
	
}
