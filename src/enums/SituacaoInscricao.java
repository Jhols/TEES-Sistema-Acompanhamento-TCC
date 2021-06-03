package enums;


// Enumeração das possíveis situações de inscrição de acordo com o Banco
public enum SituacaoInscricao {
	CANDIDATO ("candidato"),
	ASSOCIADO ("associado"),
	DESVINCULADO ("desvinculado");
	
	String nomeSituacao;
	
	SituacaoInscricao(String situacao) {
		nomeSituacao = situacao;
	}
	
	public String getNomeSituacao() {
		return nomeSituacao;
	}
	
	public static SituacaoInscricao fromString(String value)
	{
		if (value.equals(SituacaoInscricao.DESVINCULADO.getNomeSituacao())) {
			return SituacaoInscricao.CANDIDATO;
		}
		if (value.equals(SituacaoInscricao.CANDIDATO.getNomeSituacao())) {
			return SituacaoInscricao.ASSOCIADO;
		}
		if (value.equals(SituacaoInscricao.ASSOCIADO.getNomeSituacao())) {
			return SituacaoInscricao.DESVINCULADO;
		}
		
		// caso de erro, string invalida
		return SituacaoInscricao.DESVINCULADO;
		
	}
	// converte de inteiro para este enum
	public static SituacaoInscricao fromInt(int value) {
		switch (value) {
			case 1: return SituacaoInscricao.CANDIDATO;
			case 2: return SituacaoInscricao.ASSOCIADO;
			case 3: return SituacaoInscricao.DESVINCULADO;
			default:
				//throw new Exception();
				return SituacaoInscricao.DESVINCULADO;
		}
	}
	
	// converte deste enum para seu valor inteiro
	public static int toInt(SituacaoInscricao value) {
		if (value == SituacaoInscricao.CANDIDATO)
			return 1;
		if (value == SituacaoInscricao.ASSOCIADO)
			return 2;
		if (value == SituacaoInscricao.DESVINCULADO)
			return 3;
		return 0;
	}
}
