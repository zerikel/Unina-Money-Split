package Entities;

import java.util.ArrayList;

public class Partecipazione {
	
	private float saldoCorrente;
	private Utente myUtente;
	private Gruppo myGruppo;
	private ArrayList<StoricoCredito> storicoCredito = new ArrayList<StoricoCredito>();	
	private ArrayList<StoricoDebito> storicoDebito = new ArrayList<StoricoDebito>();	
	
	public Partecipazione(Utente myUtente, Gruppo myGruppo) {
		this.saldoCorrente = 0;
		if(myUtente != null)
			this.myUtente = myUtente;
		else
			throw new IllegalArgumentException("L'utente inserito deve essere valido.");
		if(myGruppo != null)
			this.myGruppo = myGruppo;
		else
			throw new IllegalArgumentException("Il gruppo inserito deve essere valido.");
		
	}
	
	public void addCredito(StoricoCredito newCredito) {
		if(newCredito != null) 
		this.storicoCredito.add(newCredito);
		else
			throw new IllegalArgumentException("Credito non valido.");
	}
	
	public void addDebito(StoricoDebito newDebito) {
		if(newDebito != null) 
		this.storicoDebito.add(newDebito);
		else
			throw new IllegalArgumentException("Debito non valido.");
	}
	
	public float calcolaSaldoCorrente() {
		float sommaCredito = 0;
		float sommaDebito = 0;
		
		for(StoricoCredito s: storicoCredito) {
			sommaCredito += s.getImporto();
		}
		
		for(StoricoDebito s: storicoDebito) {
			sommaDebito += s.getImporto();
		}
		
		return this.saldoCorrente = sommaCredito - sommaDebito;
	}
	
	public float getSaldoCorrente() { return this.saldoCorrente; }
    public Utente getMyUtente() { return this.myUtente; }
    public Gruppo getMyGruppo() { return this.myGruppo; }
	
	
}
