package Entities;

public class Quota {
	private float importo;
    private Partecipazione debitore;
    private Spesa spesaRiferimento;  

    public Quota(float importo, Partecipazione debitore, Spesa spesaRiferimento) {
        this.importo = importo;
        this.debitore = debitore;
        this.spesaRiferimento = spesaRiferimento;
    }

    public float getImporto() { return importo; }
    public Partecipazione getDebitore() { return debitore; }
    public Spesa getSpesaRiferimento() { return spesaRiferimento; }
}