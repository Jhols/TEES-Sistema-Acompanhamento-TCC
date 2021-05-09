package model;
import enums.SituacaoInscricao;
import enums.SituacaoProjeto;

public class InscricaoProjeto {
	private int id;
	private Aluno aluno;
	private Projeto projeto;
	private SituacaoInscricao situacaoInscricao;
	int idSituacaoInscricao;
	
	public InscricaoProjeto() {
		aluno = new Aluno();
		projeto = new Projeto();
	} 

	public InscricaoProjeto(Aluno aluno, Projeto projeto) {
		this();
		this.aluno = aluno;
		this.projeto = projeto;
		this.situacaoInscricao = SituacaoInscricao.CANDIDATO;
	}
	
	public InscricaoProjeto(Aluno aluno, Projeto projeto, SituacaoInscricao situacaoInscricao) {
		this();
		this.aluno = aluno;
		this.projeto = projeto;
		this.situacaoInscricao = situacaoInscricao;
		if (this.situacaoInscricao == SituacaoInscricao.ASSOCIADO) {
			this.projeto.setSituacao(SituacaoProjeto.ATIVO);
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

	/*public Aluno getAluno() {
		return AlunoDAO.getInstance().pesquisarAlunoPorIdAluno(this.aluno.getId());
	}*/
	
	public int getIdAluno() {
		return this.aluno.getId();
	}
	public void setIdAluno(int idAluno) {
		this.aluno.setId(idAluno);
	}
	public int getIdProjeto() {
		return projeto.getId();
	}
	public void setIdProjeto(int idProjeto) {
		this.projeto.setId(idProjeto);
	}
	public int getIdSituacaoInscricao() {
		return idSituacaoInscricao;
	}
	public void setIdSituacaoInscricao(int idSituacaoInscricao) {
		this.idSituacaoInscricao = idSituacaoInscricao;
	}
	@Override
	public String toString() {
		return "InscricaoProjeto [idInscricao=" + id + ", idAluno=" + aluno.getId() + ", idProjeto=" + projeto.getId()
				+ ", idSituacaoInscricao=" + idSituacaoInscricao + "]";
	}
	
}