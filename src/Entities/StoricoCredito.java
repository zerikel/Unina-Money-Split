package Entities;

public class StoricoCredito {
	
	private float importo;
	private Quota quotaStoricoCredito;
	
	public float getImporto() {
		return importo;
	}

	public void setImporto(float importo) {
		this.importo = importo;
	}

	public StoricoCredito(float importo,Quota quota) {
		this.importo = importo;
		this.quotaStoricoCredito = quota;
	}

}
