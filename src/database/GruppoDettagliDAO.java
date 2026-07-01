package database;

import Entities.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GruppoDettagliDAO {
	
    public List<Spesa> getSpeseByGruppo(Gruppo gruppo) {
        List<Spesa> spese = new ArrayList<>();
        
        Connection conn = DBConnection.getConnection();
        
        String query = "SELECT S.idspesa, S.ImportoTotale, S.Descrizione, S.Data, S.TipoSpesa, " +
                       "U.Email, U.Nome, U.Cognome " +
                       "FROM SPESA S " +
                       "JOIN UTENTE U ON S.EmailPagatore = U.Email " +
                       "WHERE S.IDGruppo = ? " +
                       "ORDER BY S.Data ASC";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, gruppo.getIdGruppo());
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    float importo = rs.getFloat("ImportoTotale");
                    String descrizione = rs.getString("Descrizione");
                    LocalDate data = rs.getDate("Data").toLocalDate(); 
                    String tipoSpesa = rs.getString("TipoSpesa");
                    
                    Utente utentePagatore = new Utente(
                        rs.getString("Email"),
                        "",
                        rs.getString("Cognome"),
                        rs.getString("Nome")
                    );
                    
                    Partecipazione pagatore = new Partecipazione(utentePagatore, gruppo);
                    
                    Spesa s;
                    if ("COMUNE".equalsIgnoreCase(tipoSpesa)) {
                        s = new SpesaComune(rs.getInt("idspesa"),importo, descrizione, data, gruppo, pagatore);
                    } else {
                        s = new SpesaPersonale(rs.getInt("idspesa"),importo, descrizione, data, gruppo, pagatore);
                    }
                    
                    spese.add(s);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return spese;
    }

    public List<Partecipazione> getPartecipazioniByGruppo(Gruppo gruppo) {
        List<Partecipazione> partecipazioni = new ArrayList<>();
        
        Connection conn = DBConnection.getConnection();
        
        String queryMembri = "SELECT P.EmailUtente, U.Nome, U.Cognome " +
                             "FROM PARTECIPAZIONE P " +
                             "JOIN UTENTE U ON P.EmailUtente = U.Email " +
                             "WHERE P.IDGruppo = ?";
                             
        try (PreparedStatement stmtMembri = conn.prepareStatement(queryMembri)) {
            stmtMembri.setInt(1, gruppo.getIdGruppo());
            
            try (ResultSet rsMembri = stmtMembri.executeQuery()) {
                while (rsMembri.next()) {
                    Utente utente = new Utente(
                        rsMembri.getString("EmailUtente"),
                        "", 
                        rsMembri.getString("Cognome"),
                        rsMembri.getString("Nome")
                    );
                    
                    Partecipazione p = new Partecipazione(utente, gruppo);
                    caricaStoricoCrediti(p, gruppo.getIdGruppo(), conn);
                    caricaStoricoDebiti(p, gruppo.getIdGruppo(), conn);
                    
                    partecipazioni.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return partecipazioni;
    }

    private void caricaStoricoCrediti(Partecipazione p, int idGruppo, Connection conn) {
        String query = "SELECT Importo FROM STORICOCREDITO WHERE EmailUtente = ? AND IDGruppo = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, p.getMyUtente().getEmail());
            stmt.setInt(2, idGruppo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    p.addCredito(new StoricoCredito(rs.getFloat("Importo")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void caricaStoricoDebiti(Partecipazione p, int idGruppo, Connection conn) {
        String query = "SELECT Importo FROM STORICODEBITO WHERE EmailUtente = ? AND IDGruppo = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, p.getMyUtente().getEmail());
            stmt.setInt(2, idGruppo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    p.addDebito(new StoricoDebito(rs.getFloat("Importo")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}