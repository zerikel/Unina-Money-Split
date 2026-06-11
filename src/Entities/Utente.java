package Entities;

public class Utente {
	private String email;
	private String password;
	private String cognome;
	private String nome;
	
	public Utente (String email, String password, String cognome, String nome)
	{
		this.cognome = cognome;
		this.email = email;
		this.nome = nome;
		this.password = password;
	}
}
