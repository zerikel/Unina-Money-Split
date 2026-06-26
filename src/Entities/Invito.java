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

	public String getStatoInvito() {
		return statoInvito;
	}

	public void setStatoInvito(String statoInvito) {
		this.statoInvito = statoInvito;
	}

	public Utente getUtenteInvitato() {
		return utenteInvitato;
	}

	public void setUtenteInvitato(Utente utenteInvitato) {
		this.utenteInvitato = utenteInvitato;
	}

	public Gruppo getInvitoGruppo() {
		return invitoGruppo;
	}

	public void setInvitoGruppo(Gruppo invitoGruppo) {
		this.invitoGruppo = invitoGruppo;
	}
}
