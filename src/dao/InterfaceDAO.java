package dao;

public interface InterfaceDAO { 
	
	//M�todo de leitura particular de cada classe
	//public ClasseDAOImpl consultar() ou pesquisar();
	
	public boolean incluir();
	
	public void atualizar();
	
	public boolean deletar();
	
}
