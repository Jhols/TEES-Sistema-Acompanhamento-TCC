package enums;

public enum BancoTabela {
	NONE(""),
	PESSOA ("pessoa"),
	ALUNO ("aluno"),
	PROFESSOR ("professor"),
	PROJETO ("projeto"),
	LOGIN("login"),
	SITUACAO_PROJETO ("situacao_projeto"),
	PERFIL_PESSOA ("perfil_pessoa"),
	INSCRICAO_PROJETO("inscricao_aluno_projeto");
	
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
