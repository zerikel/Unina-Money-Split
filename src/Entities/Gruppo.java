package Entities;

import java.util.*;

public class Gruppo {
	private String nome;
	private Date dataCreazione;
	private Utente CreatoreGruppo;
	private ArrayList<Partecipazione> membriGruppo = new ArrayList<Partecipazione>();
	
	public Gruppo (String nome, Date dataCreazione, Utente CreatoreGruppo) {
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

}
