package enums;

public enum BancoTabela {
	LOGIN("login"),
	PESSOA ("pessoa"),
	ALUNO ("aluno"),
	PROFESSOR ("professor"),
	PROJETO ("projeto"),
	SITUACAO_PROJETO ("situacao_projeto"),
	INSCRICAO_ALUNO_PROJETO ("inscricao_aluno_projeto"),
	SITUACAO_ALUNO_PROJETO ("situacao_aluno_projeto");
	
	private String nomeTabela;
	
	BancoTabela(String nomeTabela) {
		this.nomeTabela = nomeTabela;
	}

	public String getNomeTabela() {
		return this.nomeTabela;
	}
	
	/*public BancoTabela getTabela(String nomeTabela) {
		return this;
	}*/
	
}
