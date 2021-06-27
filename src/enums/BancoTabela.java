package enums;


// Enumeracao dos nomes das tabelas de acordo com o Banco
public enum BancoTabela {
	NONE (""),
	PESSOA ("pessoa"),
	ALUNO ("aluno"),
	PROFESSOR ("professor"),
	PROJETO ("projeto"),
	LOGIN ("login"),
	SITUACAO_PROJETO ("situacao_projeto"),
	PERFIL_PESSOA ("perfil_pessoa"),
	INSCRICAO_ALUNO_PROJETO ("inscricao_aluno_projeto"),
	SITUACAO_ALUNO_PROJETO ("situacao_aluno_projeto"),
	TURMA_PROFESSOR("turma_professor"),
	TURMA_ALUNO("turma_aluno"),
	SITUACAO_TURMA("situacao_turma"),
	TURMA("turma"),
	SEMESTRE("semestre"),
	RELATORIO("relatorio"),
	ARQUIVO("arquivo"),
	ARQUIVOTCC("arquivo_tcc"),
	ENTREGA("entrega"),
	CALENDARIO_ENTREGA("calendario_entrega"),
	ENVIO_ENTREGA("envio_entrega");
	
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