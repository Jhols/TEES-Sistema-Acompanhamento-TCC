package model;

import java.io.InputStream;

public class ArquivoDeTcc {
	private int id_arquivo;
	private int id_turma;
	private InputStream anexo;
	private String fileName;
	private String contentType;
	
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
	public InputStream getAnexo() {
		return anexo;
	}
	public void setAnexo(InputStream anexo) {
		this.anexo = anexo;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	
}
