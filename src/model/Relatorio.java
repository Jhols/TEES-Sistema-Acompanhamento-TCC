package model;

import java.util.Date;

public class Relatorio {
	int idRelatorio, idAutor, idDestinatario;
	String titulo, texto, nomeAutor, nomeDestinatario;
	Date data;
	
	public Relatorio() {
		super();
	}
	
	public Relatorio(int idRelatorio) {
		super();
		this.idRelatorio = idRelatorio;
	}
	
	public Relatorio(String titulo, int idAutor, int idDestinatario, String texto) {
		super();
		this.titulo = titulo;
		this.idAutor = idAutor;
		this.idDestinatario = idDestinatario;
		this.texto = texto;
	}

	public Relatorio(String titulo, int idAutor, int idDestinatario, String texto, Date data) {
		this(titulo, idAutor, idDestinatario, texto);
		this.data = data;
	}

	public int getIdRelatorio() {
		return idRelatorio;
	}
	public void setIdRelatorio(int idRelatorio) {
		this.idRelatorio = idRelatorio;
	}
	public int getIdAutor() {
		return idAutor;
	}
	public void setIdAutor(int autor) {
		this.idAutor = autor;
	}
	public int getIdDestinatario() {
		return idDestinatario;
	}
	public void setIdDestinatario(int destinatario) {
		this.idDestinatario = destinatario;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getNomeAutor() {
		return nomeAutor;
	}

	public void setNomeAutor(String nomeAutor) {
		this.nomeAutor = nomeAutor;
	}
	
	public String getNomeDestinatario() {
		return nomeDestinatario;
	}

	public void setNomeDestinatario(String nomeDestinatario) {
		this.nomeDestinatario = nomeDestinatario;
	}
	
}
