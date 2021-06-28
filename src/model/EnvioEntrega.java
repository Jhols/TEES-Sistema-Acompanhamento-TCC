package model;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import dao.AlunoDAO;
import dao.CalendarioDAO;

public class EnvioEntrega extends ArquivoAnexado {
	private int idEnvioEntrega;
	private int idEntrega;
	private int idAluno;
	private Date dataEnvio;
	
	private Entrega entrega;
	private Aluno aluno;
	
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
		entrega = CalendarioDAO.buscarEntregaPorId(getIdEntrega());
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
		aluno = AlunoDAO.getInstance().findById(idAluno);
	}
	
	
	
	public Entrega getEntrega() {
		return entrega;
	}
	public Aluno getAluno() {
		return aluno;
	}
	
	public String dataFormatada() {
		SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
		return dt.format(dataEnvio);
	}
	
	public boolean estaAtrasado() {
		return getDataEnvio().after(entrega.getDataPrazo());
	}
	public long calcularDiasDeAtraso() {
		TimeUnit timeUnit = TimeUnit.DAYS;
		long diffInMillies = getDataEnvio().getTime() - entrega.getDataPrazo().getTime() ;
		long dias = timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
		return dias;
	}
	
	
}

