package Entities;

public class StoricoDebito {

	private float importo;
	private Quota quotaStoricoDebito;
	
	public StoricoDebito(float importo,Quota quota) {
		this.importo = importo;
		this.quotaStoricoDebito = quota;
	}
	
	public float getImporto() {
		return importo;
	}

	public void setImporto(float importo) {
		this.importo = importo;
	}

}
