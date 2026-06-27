package Entities;

import java.time.LocalDate;
import java.util.*;

public abstract class Spesa {
	protected float importoTotale;
	protected String descrizione;
	protected LocalDate data;
	protected Gruppo SGruppo;
	protected Partecipazione pagatore;
	
	public Spesa (float importoTotale,String descrizione, LocalDate data, Gruppo SGruppo, Partecipazione pagatore)
	{
		this.data = data;
		this.descrizione = descrizione;
		this.importoTotale = importoTotale;
		this.pagatore = pagatore;
		this.SGruppo = SGruppo;
	}
	
	public float getImportoTotale() { return importoTotale; }
    public String getDescrizione() { return descrizione; }
    public LocalDate getData() { return data; }
    public Gruppo getSGruppo() { return SGruppo; }
    public Partecipazione getPagatore() { return pagatore; }
}
