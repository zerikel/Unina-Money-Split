package Entities;

public class StoricoDebito {

	private float importo;
	private Quota quotaStoricoDebito;
	
	public StoricoDebito(float importo) {
		this.importo = importo;
	}
	
	public float getImporto() {
		return importo;
	}

	public void setImporto(float importo,Quota quota) {
		this.importo = importo;
		this.quotaStoricoDebito = quota;
	}

}
