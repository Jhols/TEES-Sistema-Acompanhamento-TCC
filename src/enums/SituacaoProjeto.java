package enums;

//Enumeração das possíveis situações de projeto de acordo com o Banco
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
	
	// converte de inteiro para este enum
	public static SituacaoProjeto fromInt(int value) {
		switch (value) {
			case 1: return SituacaoProjeto.EXCLUIDO;
			case 2: return SituacaoProjeto.DISPONIVEL;
			case 3: return SituacaoProjeto.ATIVO;
			case 4: return SituacaoProjeto.DESATIVADO;
			default:
				//throw new Exception();
				return SituacaoProjeto.EXCLUIDO;
		}
	}
	
	// converte deste enum para seu valor inteiro
	public static int toInt(SituacaoProjeto value) {
		if (value == SituacaoProjeto.EXCLUIDO)
			return 1;
		if (value == SituacaoProjeto.DISPONIVEL)
			return 2;
		if (value == SituacaoProjeto.ATIVO)
			return 3;
		if (value == SituacaoProjeto.DESATIVADO)
			return 4;
		return 0;
	}
}