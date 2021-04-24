package model;

import enums.SituacaoInscricao;

public class InscricaoProjeto {
	private int id;
	private Aluno aluno;
	private Projeto projeto;
	private SituacaoInscricao situacaoInscricao;
	
	public InscricaoProjeto() {
		
	}

	public InscricaoProjeto(Aluno aluno, Projeto projeto) {
		this.aluno = aluno;
		this.projeto = projeto;
		this.situacaoInscricao = SituacaoInscricao.CANDIDATO;
	}
	
	public InscricaoProjeto(Aluno aluno, Projeto projeto, SituacaoInscricao situacaoInscricao) {
		this.aluno = aluno;
		this.projeto = projeto;
		this.situacaoInscricao = situacaoInscricao;
		if (this.situacaoInscricao == SituacaoInscricao.ASSOCIADO) {
			this.aluno.setProjeto(this.projeto);
		}
	}
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}
	
	public SituacaoInscricao getSituacaoInscricao() {
		return situacaoInscricao;
	}

	public void setSituacaoInscricao(SituacaoInscricao situacaoInscricao) {
		this.situacaoInscricao = situacaoInscricao;
	}

	
	
}
