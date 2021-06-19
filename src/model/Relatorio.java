package model;

import java.util.Date;

public class Relatorio {
	int idRelatorio, autor, destinatario;
	String titulo, texto, nomeAutor;
	Date data;
	
	public Relatorio() {
		super();
	}
	
	public Relatorio(int idRelatorio) {
		super();
		this.idRelatorio = idRelatorio;
	}

	public Relatorio(int autor, int destinatario, String texto, Date data) {
		super();
		this.autor = autor;
		this.destinatario = destinatario;
		this.texto = texto;
		this.data = data;
	}

	public int getIdRelatorio() {
		return idRelatorio;
	}
	public void setIdRelatorio(int idRelatorio) {
		this.idRelatorio = idRelatorio;
	}
	public int getAutor() {
		return autor;
	}
	public void setAutor(int autor) {
		this.autor = autor;
	}
	public int getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(int destinatario) {
		this.destinatario = destinatario;
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
