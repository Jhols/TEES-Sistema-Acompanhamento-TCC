package model;

import java.util.Date;

public class Entrega {
	private int idEntrega, idCalendarioEntrega;
	private String titulo, instrucao;
	private Date dataPrazo;
	
	public Entrega() {
		super();
	}

	public Entrega(int idEntrega, int idCalendarioEntrega, String titulo, String instrucao, Date dataPrazo) {
		super();
		this.idEntrega = idEntrega;
		this.idCalendarioEntrega = idCalendarioEntrega;
		this.titulo = titulo;
		this.instrucao = instrucao;
		this.dataPrazo = dataPrazo;
	}

	public int getIdEntrega() {
		return idEntrega;
	}

	public void setIdEntrega(int idEntrega) {
		this.idEntrega = idEntrega;
	}

	public int getIdCalendarioEntrega() {
		return idCalendarioEntrega;
	}

	public void setIdCalendarioEntrega(int idCalendarioEntrega) {
		this.idCalendarioEntrega = idCalendarioEntrega;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getInstrucao() {
		return instrucao;
	}

	public void setInstrucao(String instrucao) {
		this.instrucao = instrucao;
	}

	public Date getDataPrazo() {
		return dataPrazo;
	}

	public void setDataPrazo(Date dataPrazo) {
		this.dataPrazo = dataPrazo;
	}
	
	
}
