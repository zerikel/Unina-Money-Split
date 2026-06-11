package Entities;

public class Invito {
	private String statoInvito;
	private Utente utenteInvitato;
	private Gruppo invitoGruppo;
	
	public Invito (String statoInvito, Utente utenteInvitato, Gruppo invitoGruppo)
	{
		this.statoInvito = statoInvito;
		this.invitoGruppo = invitoGruppo;
		this.utenteInvitato = utenteInvitato;
	}
}
