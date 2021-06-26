package model;

import java.io.InputStream;

public class Arquivo {
	private int idArquivo;
	private int id_projeto;
	private InputStream anexo;
	private String fileName;
	private String contentType;
	
	public int getId_projeto() {
		return id_projeto;
	}
	public void setId_projeto(int id_projeto) {
		this.id_projeto = id_projeto;
	}
	public int getIdArquivo() {
		return idArquivo;
	}
	public void setIdArquivo(int idArquivo) {
		this.idArquivo = idArquivo;
	}
	public InputStream getAnexo() {
		return anexo;
	}
	public void setAnexo(InputStream anexo) {
		this.anexo = anexo;
	}
	
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Arquivo(int idArquivo, InputStream anexo) {
		super();
		this.idArquivo = idArquivo;
		this.anexo = anexo;
	}
	
	public Arquivo() {
		
	}
	@Override
	public String toString() {
		return "Arquivo [idArquivo=" + idArquivo + ", id_projeto=" + id_projeto + ", fileName=" + fileName
				+ ", contentType=" + contentType + "]";
	}
	
	
	
	

}
