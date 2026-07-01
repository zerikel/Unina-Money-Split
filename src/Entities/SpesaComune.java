package Entities;

import java.time.LocalDate;
import java.util.ArrayList;

public class SpesaComune extends Spesa{
	private ArrayList<Quota> listaQuote;
	
	public SpesaComune (int idSpesa,float importoTotale,String descrizione, LocalDate data, Gruppo SGruppo, Partecipazione pagatore)
	{
		super (idSpesa, importoTotale,descrizione, data, SGruppo, pagatore);
		listaQuote = new ArrayList<>();
	}
}
