package control;

import database.GruppoDAO;
import database.InvitoDAO;
import database.UtenteDAO;
import database.GruppoDettagliDAO;
import database.SpesaDAO;          

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Entities.Gruppo;
import Entities.Invito;
import Entities.Partecipazione;
import Entities.Spesa;
import Entities.SpesaComune;
import Entities.SpesaPersonale;
import Entities.Utente;
import boundaries.Dashboard;

public class MainController {
    
    private static MainController instance = null;
    
    private UtenteDAO utenteDAO;
    private Utente utenteLoggato;
    private GruppoDAO gruppoDAO;
    private InvitoDAO invitoDAO;
    private GruppoDettagliDAO gruppoDettagliDAO;
    private SpesaDAO spesaDAO;
    private Gruppo gruppoAttuale;
    
    private MainController() {
        this.utenteDAO = new UtenteDAO();
        this.gruppoDAO = new GruppoDAO();
        this.invitoDAO = new InvitoDAO();
        this.gruppoDettagliDAO = new GruppoDettagliDAO(); 
        this.spesaDAO = new SpesaDAO(); 
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

    public boolean registraSpesa(float importo, String descrizione, LocalDate data, String tipoSpesa) {
        if (this.utenteLoggato == null || this.gruppoAttuale == null) { 
            return false; 
        }
        Partecipazione pagatore = new Partecipazione(this.utenteLoggato, this.gruppoAttuale);
        
        Spesa nuovaSpesa;
        if ("COMUNE".equals(tipoSpesa)) {
            nuovaSpesa = new SpesaComune(importo, descrizione, data, this.gruppoAttuale, pagatore);
        } else {
            nuovaSpesa = new SpesaPersonale(importo, descrizione, data, this.gruppoAttuale, pagatore);
        }

        return spesaDAO.inserisciSpesa(nuovaSpesa);
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
                continue; //va al prossimo ciclo
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
}