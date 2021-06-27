package model;

import java.io.InputStream;

public abstract class ArquivoAnexado {
	private InputStream anexo;
	private String fileName;
	private String contentType;
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


