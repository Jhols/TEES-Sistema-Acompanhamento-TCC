package model;

import dao.AlunoDAO;

public class InscricaoProjeto {
	
	private int idInscricao;
	private int idAluno;
	private int idProjeto;
	private int idSituacaoInscricao;
	
	
	public Aluno getAluno() {
		return AlunoDAO.pesquisarAlunoPorIdAluno(idAluno);
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
	
	
	
	

}
