package model;

import enums.Perfil;

public abstract class Pessoa {
	private int id;
	private String nome, email, telefone;
	private Perfil perfil;
	
	public Perfil getPerfil() {
		return perfil;
	}

	protected void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Pessoa() {
		
	}
	
	public Pessoa(String nome) {
		this.nome = nome;
	}
	
	public Pessoa(String nome, String email, String telefone) {
		this(nome);
		this.email = email;
		this.telefone = telefone;
	}
	
	public void incluirBanco() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public String toString() {
		return "nome: "+nome+"; email: "+ email + "; telefone: "+ telefone;
	}
}
