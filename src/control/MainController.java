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

    public List<Partecipazione> getSaldiGruppo(Gruppo gruppoAttuale) {
        if (this.utenteLoggato == null || gruppoAttuale == null) { 
            return new ArrayList<>(); 
        }
        return gruppoDettagliDAO.getPartecipazioniByGruppo(gruppoAttuale);
    }

    public List<Spesa> getSpeseGruppo(Gruppo gruppoAttuale) {
        if (this.utenteLoggato == null || gruppoAttuale == null) { 
            return new ArrayList<>(); 
        }
        return gruppoDettagliDAO.getSpeseByGruppo(gruppoAttuale);
    }

    public boolean registraSpesa(float importo, String descrizione, LocalDate data, String tipoSpesa, Gruppo gruppoAttuale) {
        if (this.utenteLoggato == null || gruppoAttuale == null) { 
            return false; 
        }
        Partecipazione pagatore = new Partecipazione(this.utenteLoggato, gruppoAttuale);
        
        Spesa nuovaSpesa;
        if ("COMUNE".equals(tipoSpesa)) {
            nuovaSpesa = new SpesaComune(importo, descrizione, data, gruppoAttuale, pagatore);
        } else {
            nuovaSpesa = new SpesaPersonale(importo, descrizione, data, gruppoAttuale, pagatore);
        }

        return spesaDAO.inserisciSpesa(nuovaSpesa);
    }
}