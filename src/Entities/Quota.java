package Entities;

public class Quota {
	private float importo;
	private Partecipazione utenteQuota;
	
	public Quota(float importo, Partecipazione utenteQuota)
	{
		this.importo = importo;
		this.utenteQuota = utenteQuota;
	}
}
