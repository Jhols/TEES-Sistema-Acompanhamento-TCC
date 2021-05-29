package enums;

public enum SituacaoTurma {
	
	APROVADO ("aprovado"),
	REPROVADO ("reprovado"),
	CURSO ("curso");
	
	
	String nomeSituacaoTurma;
	
	SituacaoTurma(String situacao) {
		nomeSituacaoTurma = situacao;
	}
	
	public String getNomeSituacao() {
		return nomeSituacaoTurma;
	}
	public static SituacaoTurma fromString(String value)
	{
		if (value.equals(SituacaoTurma.APROVADO.getNomeSituacao())) {
			return SituacaoTurma.APROVADO;
		}
		if (value.equals(SituacaoTurma.REPROVADO.getNomeSituacao())) {
			return SituacaoTurma.REPROVADO;
		}
		if (value.equals(SituacaoTurma.CURSO.getNomeSituacao())) {
			return SituacaoTurma.CURSO;
		}
		
		return SituacaoTurma.REPROVADO;
		
	}
	// converte de inteiro para este enum
	public static SituacaoTurma fromInt(int value) {
		switch (value) {
			case 1: return SituacaoTurma.APROVADO;
			case 2: return SituacaoTurma.REPROVADO;
			case 3: return SituacaoTurma.CURSO;
			
			default:
				//throw new Exception();
				return SituacaoTurma.REPROVADO;
		}
	}
	
	// converte deste enum para seu valor inteiro
	public static int toInt(SituacaoTurma value) {
		if (value == SituacaoTurma.APROVADO)
			return 1;
		if (value == SituacaoTurma.REPROVADO)
			return 2;
		if (value == SituacaoTurma.CURSO)
			return 3;
		return 0;
	}
}
