package control;

import database.GruppoDAO;
import database.InvitoDAO;
import database.SaldaDebitoDAO;
import database.UtenteDAO;
import database.GruppoDettagliDAO;
import database.SpesaDAO;          

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Entities.Gruppo;
import Entities.Invito;
import Entities.Partecipazione;
import Entities.Quota;
import Entities.Spesa;
import Entities.SpesaComune;
import Entities.SpesaPersonale;
import Entities.Utente;
import boundaries.Dashboard;
import java.time.format.DateTimeFormatter;

public class MainController {
    
    private static MainController instance = null;
    
    private UtenteDAO utenteDAO;
    private Utente utenteLoggato;
    private GruppoDAO gruppoDAO;
    private InvitoDAO invitoDAO;
    private GruppoDettagliDAO gruppoDettagliDAO;
    private SpesaDAO spesaDAO;
    private Gruppo gruppoAttuale;
    private SaldaDebitoDAO saldaDebitoDAO;
    
    private MainController() {
        this.utenteDAO = new UtenteDAO();
        this.gruppoDAO = new GruppoDAO();
        this.invitoDAO = new InvitoDAO();
        this.gruppoDettagliDAO = new GruppoDettagliDAO(); 
        this.spesaDAO = new SpesaDAO();
        this.saldaDebitoDAO = new SaldaDebitoDAO();
    }
    
    public static MainController getInstance() {
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }
    
    public boolean effettuaLogin(String email, String passwordInserita) {
        if (email == null || email.trim().isEmpty() || passwordInserita == null || passwordInserita.trim().isEmpty()) {
            return false; 
        }
        Utente utenteDB = utenteDAO.getUtenteByEmail(email);
        
        if (utenteDB != null && utenteDB.getPassword().equals(passwordInserita)) {
            this.utenteLoggato = utenteDB;
            Dashboard dashboard = new Dashboard();
            dashboard.setVisible(true); 
            return true;
        }
        return false;
    }
    
    public List<Gruppo> getGruppiUtente() {
        if (this.utenteLoggato == null) {return new ArrayList<>();}
        return gruppoDAO.getGruppiByUtente(this.utenteLoggato.getEmail());
    }
    
    public List<Invito> getInvitiUtente() {
        if (this.utenteLoggato == null) {return new ArrayList<>();}
        return invitoDAO.getInvitiSospesiByUtente(this.utenteLoggato.getEmail());
    }
    
    public boolean gestisciRispostaInvito(int idGruppo, boolean stato) {
        if (this.utenteLoggato == null) {
            return false;
        }
        return invitoDAO.rispondiInvito(this.utenteLoggato.getEmail(), idGruppo, stato);
    }

    public Utente getUtenteLoggato() {
        return utenteLoggato;
    }

    public List<Partecipazione> getSaldiGruppo() {
        if (this.utenteLoggato == null || this.gruppoAttuale == null) { 
            return new ArrayList<>(); 
        }
        return gruppoDettagliDAO.getPartecipazioniByGruppo(this.gruppoAttuale);
    }

    public List<Spesa> getSpeseGruppo() {
        if (this.utenteLoggato == null || this.gruppoAttuale == null) { 
            return new ArrayList<>(); 
        }
        return gruppoDettagliDAO.getSpeseByGruppo(this.gruppoAttuale);
    }

    public boolean registraSpesa(float importo, String descrizione, java.time.LocalDate data, String tipoSpesa) {
        if (this.utenteLoggato == null || this.gruppoAttuale == null) { 
            return false; 
        }
        Partecipazione pagatore = new Partecipazione(this.utenteLoggato, this.gruppoAttuale);
        
        String emailPagatore = this.utenteLoggato.getEmail();
        int idGruppo = this.gruppoAttuale.getIdGruppo();

        java.sql.Date dataSQL = java.sql.Date.valueOf(data);
        return spesaDAO.inserisciSpesa(importo,descrizione,dataSQL,emailPagatore,idGruppo,tipoSpesa);
    }
    
    /*public void impostaGruppoAttuale (int idGruppoSelezionato)
    {
    	for (Gruppo g:getGruppiUtente())
    	{
    		if (g.getIdGruppo() == idGruppoSelezionato)
    		{
    			this.gruppoAttuale = g;
    			break;
    		}
    	}
    }*/
    
    public String getNomeGruppoAttuale ()
    {
    	return this.gruppoAttuale != null ? this.gruppoAttuale.getNome() :"";
    }
    
    public boolean creaGruppo (String nomeGruppo, List<String> emailInvitati)
    {
    	if (this.utenteLoggato == null || nomeGruppo == null || nomeGruppo.trim().isEmpty())
    	{
    		return false;
    	}
    	
    	for (String email: emailInvitati)
    	{
    		if (utenteDAO.getUtenteByEmail(email) == null)
    		{
    			System.out.println("l'utente "+email+" non esiste.");
    			return false;
    		}
    	}
    	
    	for (String email :emailInvitati)
    	{
    		if (email.equals(this.utenteLoggato.getEmail()))
    		{
    			System.out.println("Errore: Non puoi invitare te stesso.");
                return false;
    		}
    	}
    	int idGruppoNuovo = gruppoDAO.creaNuovoGruppo(nomeGruppo, this.utenteLoggato.getEmail());
    	
    	if (idGruppoNuovo != -1)
    	{
    		for(String email: emailInvitati)
    		{
    			invitoDAO.inviaInvito(email, idGruppoNuovo);
    		}
    		return true;
    	}
    return false;
    }
    
    public boolean inviaInviti (List<String> emailInvitati)
    {
    	if (this.gruppoAttuale == null || emailInvitati == null || emailInvitati.isEmpty())
    	{
    		return false;
    	}
    	
    	int idGruppo = this.gruppoAttuale.getIdGruppo();
    	boolean successo = false;
    	
    	for (String email : emailInvitati)
    	{
    		if (utenteDAO.getUtenteByEmail(email) == null) {
                System.out.println("Salto: " + email + " non è registrato.");
                continue;
            }
    		
    		if (gruppoDAO.isUtentePartecipante(email, idGruppo)) {
                System.out.println("Salto: " + email + " fa già parte del gruppo.");
                continue;
            }
    		
    		if (invitoDAO.esisteInvito(email, idGruppo)) {
                System.out.println("Salto: " + email + " ha già un invito pendente.");
                continue;
            }
    		
    		invitoDAO.inviaInvito(email, idGruppo);
    		successo = true;
    	}
    	return successo;
    }
    
    public List<String> getNomiGruppiUtente()
    {
    	List<String> nomi= new ArrayList<String>();
    	for (Gruppo g:getGruppiUtente())
    	{
    		nomi.add(g.getNome());
    	}
    	return nomi;
    }
    
    public void impostaGruppoDaIndice(int index)
    {
    	List<Gruppo> tuttiGruppi = getGruppiUtente();
    	if (index >= 0 && index < tuttiGruppi.size())
    	{
    		this.gruppoAttuale = tuttiGruppi.get(index);
    	}
    }
    
    public List<String> getQuoteAperteTesto() {
        List<Quota> quote = saldaDebitoDAO.getQuoteAperte(this.utenteLoggato.getEmail(), this.gruppoAttuale);
        List<String> testiUI = new ArrayList<>();
        
        DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM");
        
        for (Quota q : quote) {
            String dataFormattata = q.getSpesaRiferimento().getData().format(formatterData);
            String importoFormattato = String.format("%.2f", q.getImporto()).replace(",", "."); 
            String nomeCreditore = q.getSpesaRiferimento().getPagatore().getMyUtente().getNome();
            String descrizione = q.getSpesaRiferimento().getDescrizione();
            
            testiUI.add(dataFormattata + " - Quota di " + importoFormattato + "€ a " + nomeCreditore + " per " + descrizione);
        }
        return testiUI;
    }

    public float getImportoQuota(int index) {
        List<Quota> quote = saldaDebitoDAO.getQuoteAperte(this.utenteLoggato.getEmail(), this.gruppoAttuale);
        if (index >= 0 && index < quote.size()) {
            return quote.get(index).getImporto();
        }
        return 0;
    }


    public boolean registraRimborso(int indexSelezionato, float importoPagato) {
        List<Quota> quote = saldaDebitoDAO.getQuoteAperte(this.utenteLoggato.getEmail(), this.gruppoAttuale);
        if (indexSelezionato < 0 || indexSelezionato >= quote.size()) return false;
        
        Quota quotaDaSaldare = quote.get(indexSelezionato);
        
        int idSpesa = quotaDaSaldare.getSpesaRiferimento().getIdSpesa(); 
        String emailCreditore = quotaDaSaldare.getSpesaRiferimento().getPagatore().getMyUtente().getEmail();
        
        return saldaDebitoDAO.salvaRimborso(idSpesa, this.utenteLoggato.getEmail(), importoPagato, emailCreditore, this.gruppoAttuale.getIdGruppo());
    }
    public int getReportNumeroSpese() {
        if (this.gruppoAttuale == null) return 0;
        return gruppoDettagliDAO.getSpeseByGruppo(this.gruppoAttuale).size();
    }

    public float getReportImportoTotale() {
        if (this.gruppoAttuale == null) return 0f;
        List<Spesa> spese = gruppoDettagliDAO.getSpeseByGruppo(this.gruppoAttuale);
        float totale = 0;
        for (Spesa s : spese) {
            totale += s.getImportoTotale();
        }
        return totale;
    }

    public double[] getReportPercentualiSpesa() {
        if (this.gruppoAttuale == null) return new double[]{0.0, 0.0};
        List<Spesa> spese = gruppoDettagliDAO.getSpeseByGruppo(this.gruppoAttuale);
        if (spese.isEmpty()) return new double[]{0.0, 0.0};

        float totale = 0;
        float totaleComuni = 0;
        for (Spesa s : spese) {
            totale += s.getImportoTotale();
            if (s instanceof SpesaComune) {
                totaleComuni += s.getImportoTotale();
            }
        }
        
        if (totale == 0) return new double[]{0.0, 0.0};
        
        double percComuni = (totaleComuni / totale) * 100.0;
        double percPersonali = 100.0 - percComuni;
        return new double[]{percComuni, percPersonali};
    }

    public String getReportTabellaTesto() {
        if (this.gruppoAttuale == null) return "";
        
        List<Partecipazione> saldi = gruppoDettagliDAO.getPartecipazioniByGruppo(this.gruppoAttuale);
        List<Spesa> spese = gruppoDettagliDAO.getSpeseByGruppo(this.gruppoAttuale);
        
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-15s | %-14s | %s\n", "Partecipante", "Importo Speso", "Saldo Finale Corrente"));
        sb.append("-----------------------------------------------------------------\n");
        
        for (Partecipazione p : saldi) {
            String nome = p.getMyUtente().getNome();
            String email = p.getMyUtente().getEmail();
            
            float speso = 0;
            for (Spesa s : spese) {
                if (s.getPagatore().getMyUtente().getEmail().equals(email)) {
                    speso += s.getImportoTotale();
                }
            }
            
            float saldo = p.calcolaSaldoCorrente();
            String saldoStr;
            if (saldo > 0) saldoStr = String.format("+ %.2f € (Credito)", saldo);
            else if (saldo < 0) saldoStr = String.format("%.2f € (Debito)", saldo);
            else saldoStr = "0.00 € (Pari)";
            
            sb.append(String.format("%-15s | %6.2f €       | %s\n", nome, speso, saldoStr));
        }
        
        return sb.toString();
    }
}