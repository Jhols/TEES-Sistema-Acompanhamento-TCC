package model;

import enums.Perfil;

public class Administrador extends Pessoa {

	public Administrador() {
		super();
		this.setPerfil(Perfil.ADMINISTRADOR);
	}

}
