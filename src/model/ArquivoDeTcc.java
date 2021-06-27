package model;


public class ArquivoDeTcc extends ArquivoAnexado {
	private int id_arquivo;
	private int id_turma;
	
	public ArquivoDeTcc() {
		
	}
	public int getId_arquivo() {
		return id_arquivo;
	}
	public void setId_arquivo(int id_arquivo) {
		this.id_arquivo = id_arquivo;
	}
	public int getId_turma() {
		return id_turma;
	}
	public void setId_turma(int id_turma) {
		this.id_turma = id_turma;
	}
	
}
