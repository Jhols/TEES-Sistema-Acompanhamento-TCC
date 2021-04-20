package enums;

public enum BancoTabela {
	PESSOA ("pessoa"),
	ALUNO ("aluno"),
	PROFESSOR ("professor"),
	PROJETO ("projeto"),
	LOGIN("login");
	
	private String nomeTabela;
	
	BancoTabela(String nomeTabela) {
		this.nomeTabela = nomeTabela;
	}

	public String getNomeTabela() {
		return this.nomeTabela;
	}
	
}
