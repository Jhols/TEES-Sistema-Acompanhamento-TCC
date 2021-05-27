package model;

import enums.Perfil;

public class Aluno extends Pessoa {
	private String matricula;
	private int idAluno;
	private StatusAlunoTCC statusAlunoTCC;
	enum StatusAlunoTCC{
		ACEITO,
		NENHUM,
		CANDIDATO,
		REJEITADO;
	
		public static StatusAlunoTCC fromInt(int value) {
			switch (value) {
			case 0: return StatusAlunoTCC.ACEITO;
			case 1: return StatusAlunoTCC.NENHUM;
			case 2: return StatusAlunoTCC.CANDIDATO;
			case 3: return StatusAlunoTCC.REJEITADO;
			
			default:
				//throw new Exception();
				return StatusAlunoTCC.NENHUM;
			}
		}
	}
	
	public static int toInt(StatusAlunoTCC value) {
		if (value == StatusAlunoTCC.ACEITO)
			return 0;
		if (value == StatusAlunoTCC.NENHUM)
			return 1;
		if (value == StatusAlunoTCC.CANDIDATO)
			return 2;
		if (value == StatusAlunoTCC.REJEITADO)
			return 3;
		return 5;
	}
	
	public boolean isAluno() {
		return statusAlunoTCC == StatusAlunoTCC.ACEITO;
	}
	public StatusAlunoTCC getStatusAlunoTCC() {
		return statusAlunoTCC;
	}

	public void setstatusAlunoTCC(StatusAlunoTCC statusAlunoTCC) {
		this.statusAlunoTCC = statusAlunoTCC;
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