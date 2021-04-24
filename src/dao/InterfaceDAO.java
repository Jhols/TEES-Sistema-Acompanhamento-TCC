package dao;

public interface InterfaceDAO { 
	
	//Método de leitura particular de cada classe
	//public ClasseDAOImpl consultar() ou pesquisar();
	
	public boolean incluir();
	
	public void atualizar();
	
	public boolean deletar();
	
}
