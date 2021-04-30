package model;

import dao.AlunoDAO;
import enums.SituacaoInscricao;

public class InscricaoProjeto {
	
	private int idInscricao;
	private int idAluno;
	private int idProjeto;
	private int idSituacaoInscricao;
	
	//A ser discutido:
	private Aluno aluno = null;
	private Projeto projeto = null;
	private SituacaoInscricao situacaoInscricao = null;
	
	public InscricaoProjeto() {
		aluno = new Aluno();
		projeto = new Projeto();
		situacaoInscricao = SituacaoInscricao.CANDIDATO;
	}

	public InscricaoProjeto(Aluno aluno, Projeto projeto) {
		this();
		this.aluno = aluno;
		this.projeto = projeto;
	}

	//A ser discutido:
	public Aluno getAluno() {
		return AlunoDAO.getInstance().pesquisarAlunoPorIdAluno(idAluno);
	}
	
	public int getIdInscricao() {
		return idInscricao;
	}
	public void setIdInscricao(int idInscricao) {
		this.idInscricao = idInscricao;
	}
	public int getIdAluno() {
		return idAluno;
	}
	public void setIdAluno(int idAluno) {
		this.idAluno = idAluno;
	}
	public int getIdProjeto() {
		return idProjeto;
	}
	public void setIdProjeto(int idProjeto) {
		this.idProjeto = idProjeto;
	}
	public int getIdSituacaoInscricao() {
		return idSituacaoInscricao;
	}
	public void setIdSituacaoInscricao(int idSituacaoInscricao) {
		this.idSituacaoInscricao = idSituacaoInscricao;
	}
	@Override
	public String toString() {
		return "InscricaoProjeto [idInscricao=" + idInscricao + ", idAluno=" + idAluno + ", idProjeto=" + idProjeto
				+ ", idSituacaoInscricao=" + idSituacaoInscricao + "]";
	}

	public Projeto getProjeto() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public SituacaoInscricao getSituacaoInscricao() {
		return situacaoInscricao;
	}

	public void setSituacaoInscricao(SituacaoInscricao situacaoInscricao) {
		this.situacaoInscricao = situacaoInscricao;
	}
	
	

}
