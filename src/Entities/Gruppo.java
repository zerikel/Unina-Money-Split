package Entities;

import java.util.*;

public class Gruppo {
	private String nome;
	private Date dataCreazione;
	private Utente CreatoreGruppo;
	private int idGruppo;
	private ArrayList<Partecipazione> membriGruppo = new ArrayList<Partecipazione>();
	
	public Gruppo (int idGruppo ,String nome, Date dataCreazione, Utente CreatoreGruppo) {
		this.idGruppo = idGruppo;
		if(nome != null)
		this.nome = nome;
		else
			 throw new IllegalArgumentException("Il nome inserito deve essere valido.");
		if(dataCreazione != null)
			this.dataCreazione = dataCreazione;
		else
			throw new IllegalArgumentException("La data inserita deve essere valida.");
		if(CreatoreGruppo != null)
			this.CreatoreGruppo = CreatoreGruppo;
		else
			throw new IllegalArgumentException("Il gruppo inserito deve essere valido.");
		
			Partecipazione Creatore = new Partecipazione(CreatoreGruppo, this);
			membriGruppo.add(Creatore);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Utente getCreatoreGruppo() {
		return CreatoreGruppo;
	}

	public void setCreatoreGruppo(Utente creatoreGruppo) {
		CreatoreGruppo = creatoreGruppo;
	}

	public ArrayList<Partecipazione> getMembriGruppo() {
		return membriGruppo;
	}

	public void setMembriGruppo(ArrayList<Partecipazione> membriGruppo) {
		this.membriGruppo = membriGruppo;
	}

	public int getIdGruppo() {
		return idGruppo;
	}

	public void setIdGruppo(int idGruppo) {
		this.idGruppo = idGruppo;
	}

}
