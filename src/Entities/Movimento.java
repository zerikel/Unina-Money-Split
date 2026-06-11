package Entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class Movimento {
	private LocalDate data;
	private LocalTime ora;
	private String commento;
	private float importo;
	private Quota specificaQuota;
	private Partecipazione utenteMov;
	
	public Movimento (LocalDate data,LocalTime ora, String commento, float importo, Quota specificaQuota,Partecipazione utenteMov)
	{
		this.commento = commento;
		this.data = data;
		this.importo = importo;
		this.ora = ora;
		this.specificaQuota = specificaQuota;
		this.utenteMov = utenteMov;
	}
}
