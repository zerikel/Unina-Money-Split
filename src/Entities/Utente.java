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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
