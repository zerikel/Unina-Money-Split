package Entities;

import java.time.LocalDate;

public class SpesaPersonale extends Spesa{
	
	public SpesaPersonale (float importoTotale,String descrizione, LocalDate data, Gruppo SGruppo, Partecipazione pagatore)
	{
		super (importoTotale,descrizione, data, SGruppo, pagatore);
	}
}
