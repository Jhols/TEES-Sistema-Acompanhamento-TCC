package model;

import java.util.ArrayList;

import enums.Perfil;


public class Professor extends Pessoa {
	enum StatusOrientador{
		CANDIDATO,
		REJEITADO,
		ACEITO,
		NENHUM;//USADO QUANDO O ADM FIZER O CADASTRO DO PROF TCC
		
		public static StatusOrientador fromInt(int value) {
			switch (value) {
			case 0: return StatusOrientador.CANDIDATO;
			case 1: return StatusOrientador.REJEITADO;
			case 2: return StatusOrientador.ACEITO;
			case 3: return StatusOrientador.NENHUM;
			
			default:
				//throw new Exception();
				return StatusOrientador.NENHUM;
			}
		}
		
	}
	enum Tipo {
		PROFESSOR_TCC,
		PROFESSOR_ORIENTADOR,
		PROFESSOR_TCC_E_ORIENTADOR,
		PROFESSOR;
		
		public static Tipo fromInt(int value) {
			switch (value) {
			case 0: return Tipo.PROFESSOR_TCC;
			case 1: return Tipo.PROFESSOR_ORIENTADOR;
			case 2: return Tipo.PROFESSOR_TCC_E_ORIENTADOR;
			case 3: return Tipo.PROFESSOR;
			default:
				//throw new Exception();
				return Tipo.PROFESSOR_TCC;
			}
		}
	}
	
	public Professor() {
		this.setPerfil(Perfil.PROFESSOR);
	}
	
	String matricula;
	ArrayList<Projeto> projetos = new ArrayList<>();
	
	Tipo tipo;
	int idProfessor;
	
	public int getIdProfessor() {
		return idProfessor;
	}

	public void setIdProfessor(int idProfessor) {
		this.idProfessor = idProfessor;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public void addProjeto(Projeto projeto) {
		this.projetos.add(projeto);
	}

	public ArrayList<Projeto> getAllProjetos() {
		return projetos;
		
	}
	
	public Projeto getProjeto(int indice) {
		return projetos.get(indice);
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public boolean isOrientador() {
		return tipo == Tipo.PROFESSOR_ORIENTADOR || tipo == Tipo.PROFESSOR_TCC_E_ORIENTADOR;
	}
	
	@Override
	public String toString() {
		return "Professor [matricula=" + matricula + ", projetos=" + projetos + ", tipo=" + tipo + ", Pessoa="
				+ super.toString() + "]";
	}
	


	
	// TODO: CalendarioDeEntregas calendarioDeEntregas
	
	
}
