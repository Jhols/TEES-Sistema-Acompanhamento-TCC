package model;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Entrega {
	int idEntrega;
	int idCalendarioEntrega;
	String titulo;
	String instrucao;
	Date dataPrazo;
	

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
	
	public String dataFormatada() {
		SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
		return dt.format(dataPrazo);
	}
	
}
