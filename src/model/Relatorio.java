package model;

import java.util.Date;

public class Relatorio {
	int idRelatorio, idAutor, idDestinatario;
	String titulo, texto, nomeAutor;
	Date data;
	
	public Relatorio() {
		super();
	}
	
	public Relatorio(int idRelatorio) {
		super();
		this.idRelatorio = idRelatorio;
	}
	
	public Relatorio(String titulo, int autor, int destinatario, String texto) {
		super();
		this.titulo = titulo;
		this.idAutor = autor;
		this.idDestinatario = destinatario;
		this.texto = texto;
	}

	public Relatorio(String titulo, int autor, int destinatario, String texto, Date data) {
		this(titulo, autor, destinatario, texto);
		this.data = data;
	}

	public int getIdRelatorio() {
		return idRelatorio;
	}
	public void setIdRelatorio(int idRelatorio) {
		this.idRelatorio = idRelatorio;
	}
	public int getAutor() {
		return idAutor;
	}
	public void setAutor(int autor) {
		this.idAutor = autor;
	}
	public int getDestinatario() {
		return idDestinatario;
	}
	public void setDestinatario(int destinatario) {
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
	
}
