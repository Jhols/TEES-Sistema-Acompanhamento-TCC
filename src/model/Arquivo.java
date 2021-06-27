package model;

import java.io.InputStream;

public class Arquivo extends ArquivoAnexado {
	private int idArquivo;
	private int id_projeto;
	
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
	public Arquivo(int idArquivo, InputStream anexo) {
		super();
		this.idArquivo = idArquivo;
		setAnexo(anexo);
	}
	
	public Arquivo() {
		
	}
	@Override
	public String toString() {
		return "Arquivo [idArquivo=" + idArquivo + ", id_projeto=" + id_projeto + ", fileName=" + getFileName()
				+ ", contentType=" + getContentType() + "]";
	}
	
	
	
	

}
