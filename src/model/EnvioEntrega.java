package model;

import java.util.Date;

public class EnvioEntrega {
	private int idEnvioEntrega, idEntrega, idAluno;
	private String conteudo;
	private Date dataEnvio;
	
	public EnvioEntrega() {
		super();
	}

	public EnvioEntrega(int idEnvioEntrega, int idEntrega, int idAluno, String conteudo, Date dataEnvio) {
		super();
		this.idEnvioEntrega = idEnvioEntrega;
		this.idEntrega = idEntrega;
		this.idAluno = idAluno;
		this.conteudo = conteudo;
		this.dataEnvio = dataEnvio;
	}

	public int getIdEnvioEntrega() {
		return idEnvioEntrega;
	}

	public void setIdEnvioEntrega(int idEnvioEntrega) {
		this.idEnvioEntrega = idEnvioEntrega;
	}

	public int getIdEntrega() {
		return idEntrega;
	}

	public void setIdEntrega(int idEntrega) {
		this.idEntrega = idEntrega;
	}

	public int getIdAluno() {
		return idAluno;
	}

	public void setIdAluno(int idAluno) {
		this.idAluno = idAluno;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public Date getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}
		
}
