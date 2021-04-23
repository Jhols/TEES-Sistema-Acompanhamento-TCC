package model;

import enums.SituacaoProjeto;

public class Projeto {
	
	private int id;
	private String titulo, descricao;
	private Aluno aluno;
	private SituacaoProjeto situacao;
	
	
	public Projeto(String titulo) {
		this.titulo = titulo;
		this.situacao = SituacaoProjeto.DISPONIVEL;
	}
	
	public Projeto(String titulo, SituacaoProjeto situacao) {
		this.titulo = titulo;
		this.situacao = situacao;
	}

	public Projeto() {
		// TODO Auto-generated constructor stub
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

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
		this.situacao = SituacaoProjeto.ATIVO;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public SituacaoProjeto getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoProjeto situacao) {
		this.situacao = situacao;
	}
	
	
	
}
