package model;

import enums.Perfil;




public class Aluno extends Pessoa {
	private String matricula;
	private int idAluno;
	private StatusAlunoTCC statusAlunoTCC;
	public enum StatusAlunoTCC{
		E_ALUNO_TCC,
		NAO_E_ALUNO_TCC;
	
		public static StatusAlunoTCC fromInt(int value) {
			switch (value) {
			case 0: return StatusAlunoTCC.E_ALUNO_TCC;
			case 1: return StatusAlunoTCC.NAO_E_ALUNO_TCC;
			
			
			default:
				//throw new Exception();
				return StatusAlunoTCC.NAO_E_ALUNO_TCC;
			}
		}
	}
	
	public boolean isAluno() {
		return statusAlunoTCC == StatusAlunoTCC.E_ALUNO_TCC;
	}
	public StatusAlunoTCC getStatusAlunoTCC() {
		return statusAlunoTCC;
	}

	public void setstatusAlunoTCC(StatusAlunoTCC statusAlunoTCC) {
		this.statusAlunoTCC = statusAlunoTCC;
	}
	
	public void setstatusAlunoTCC(int statusAlunoTCC) {
		this.statusAlunoTCC = StatusAlunoTCC.fromInt(statusAlunoTCC);
	}
	
	public int getIdAluno() {
		return idAluno;
	}

	public void setIdAluno(int idAluno) {
		this.idAluno = idAluno;
	}

	public Aluno() {
		this.setPerfil(Perfil.ALUNO);
	}
	
	public Aluno (String matricula) {
		this.matricula = matricula;
	}
	
	public Aluno(String nome, String matricula) {
		super(nome);
		this.matricula = matricula;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	@Override
	public String toString() {
		return "Aluno [matricula=" + matricula + ", idAluno=" + idAluno + ", Pessoa=" + super.toString() + "]";
	}


	
}