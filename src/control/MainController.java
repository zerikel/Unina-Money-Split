package control;

import database.UtenteDAO;
import Entities.Utente;
import boundaries.Dashboard;

public class MainController {
    
    private static MainController instance = null;
    
    private UtenteDAO utenteDAO;
    private Utente utenteLoggato;
    
    private Dashboard dashboard = new Dashboard();
    private MainController() {
        this.utenteDAO = new UtenteDAO();
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
        	dashboard.setVisible(true);
        	
        	return true;
        }
        
        return false;
    }
}