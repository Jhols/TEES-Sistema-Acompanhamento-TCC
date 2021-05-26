package model;

import java.util.ArrayList;

import enums.Perfil;
import enums.SituacaoProjeto;


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
	
	public static int toInt(StatusOrientador value) {
		if (value == StatusOrientador.CANDIDATO)
			return 0;
		if (value == StatusOrientador.REJEITADO)
			return 1;
		if (value == StatusOrientador.ACEITO)
			return 2;
		if (value == StatusOrientador.NENHUM)
			return 3;
		return 5;
	}
	
	public static int toInt(Tipo value) {
		if (value == Tipo.PROFESSOR_TCC)
			return 0;
		if (value == Tipo.PROFESSOR_ORIENTADOR)
			return 1;
		if (value == Tipo.PROFESSOR_TCC_E_ORIENTADOR)
			return 2;
		if (value == Tipo.PROFESSOR)
			return 3;
		return 5;
	}
	
	public Professor() {
		this.setPerfil(Perfil.PROFESSOR);
	}
	
	private String matricula;
	private ArrayList<Projeto> projetos = new ArrayList<>();
	
	private Tipo tipo;
	private StatusOrientador statusOrientador;
	private int idProfessor;
	
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
	public StatusOrientador getStatusOrientador() {
		return statusOrientador;
	}

	public void setStatusOrientador(StatusOrientador statusOrientador) {
		this.statusOrientador = statusOrientador;
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
