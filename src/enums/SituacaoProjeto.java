package enums;

public enum SituacaoProjeto {
	EXCLUIDO ("excluido"),
	DISPONIVEL ("disponivel"),
	ATIVO ("ativo"),
	DESATIVADO ("desativado");
	
	String nomeSituacao;
	
	SituacaoProjeto(String situacao) {
		nomeSituacao = situacao;
	}
	
	public String getNomeSituacao() {
		return nomeSituacao;
	}
	
}