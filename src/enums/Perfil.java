package enums;

public enum Perfil{
	NENHHUM(0),
	ADMINISTRADOR(1),
	COORDENADOR(2),
	SECRETARIO(3),
	PROFESSOR(4),
	ALUNO(5);
	
	private final int value;
	private Perfil(int value) {
		this.value=value;
	}
	public int getValue()
	{
		return this.value;
	}
	public static Perfil fromId(int id) {
		switch (id) {
			case 1:
				return Perfil.ADMINISTRADOR;
			case 2:
				return Perfil.COORDENADOR;
			case 3:
				return Perfil.SECRETARIO;
			case 4:
				return Perfil.PROFESSOR;
			case 5:
				return Perfil.ALUNO;
			default:
				return Perfil.NENHHUM;
		}
	}
	public static BancoTabela getTabela(Perfil perfil) {
		switch (perfil) {
		case ADMINISTRADOR:
			//return BancoTabela.ADMINISTRADOR;
			break;
		case ALUNO:
			return BancoTabela.ALUNO;
		case COORDENADOR:
			//return BancoTabela.COORDENADOR;
			break;
		case NENHHUM:
			break;
		case PROFESSOR:
			return BancoTabela.PROFESSOR;
		case SECRETARIO:
			//return BancoTabela.SECRETARIO;
			break;
		default:
			break;
		}
		return BancoTabela.NONE;
	}
}
