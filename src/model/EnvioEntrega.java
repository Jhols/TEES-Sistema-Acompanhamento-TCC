package model;

import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class EnvioEntrega extends ArquivoAnexado {
	private int idEnvioEntrega;
	private int idEntrega;
	private int idAluno;
	private Date dataEnvio;
	
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
	public Date getDataEnvio() {
		return dataEnvio;
	}
	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}
	

	public int getIdAluno() {
		return idAluno;
	}
	public void setIdAluno(int idAluno) {
		this.idAluno = idAluno;
	}
	public String dataFormatada() {
		SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
		return dt.format(dataEnvio);
	}
	
}
