package database;

import Entities.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SaldaDebitoDAO {

    public List<Quota> getQuoteAperte(String emailDebitore, Gruppo gruppo) {
        List<Quota> lista = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        
        String query = "SELECT Q.IDSpesa, Q.Importo, S.Descrizione, S.Data, S.EmailPagatore, U.Nome, U.Cognome " +
                       "FROM QUOTA Q " +
                       "JOIN SPESA S ON Q.IDSpesa = S.IDSpesa " +
                       "JOIN UTENTE U ON S.EmailPagatore = U.Email " +
                       "WHERE Q.EmailUtente = ? AND S.IDGruppo = ? " +
                       "AND NOT EXISTS (SELECT 1 FROM MOVIMENTO M WHERE M.IDSpesaQuota = Q.IDSpesa AND M.EmailQuotaUtente = Q.EmailUtente)";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, emailDebitore);
            stmt.setInt(2, gruppo.getIdGruppo());
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Utente utenteCreditore = new Utente(rs.getString("EmailPagatore"), "", rs.getString("Cognome"), rs.getString("Nome"));
                Partecipazione pagatore = new Partecipazione(utenteCreditore, gruppo);
                Spesa spesaRif = new SpesaComune(rs.getFloat("Importo"), rs.getString("Descrizione"), rs.getDate("Data").toLocalDate(), gruppo, pagatore);
                
                Partecipazione debitore = new Partecipazione(new Utente(emailDebitore, "", "", ""), gruppo);
                
                lista.add(new Quota(rs.getFloat("Importo"), debitore, spesaRif));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public boolean salvaRimborso(int idSpesa, String emailDebitore, float importo, String emailCreditore, int idGruppo) {
        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            
            String qMov = "INSERT INTO MOVIMENTO (Data, Ora, Commento, Importo, IDSpesaQuota, EmailQuotaUtente) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(qMov)) {
                stmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
                stmt.setTime(2, java.sql.Time.valueOf(java.time.LocalTime.now()));
                stmt.setString(3, "Rimborso debito"); 
                stmt.setFloat(4, importo);
                stmt.setInt(5, idSpesa); 
                stmt.setString(6, emailDebitore);
                stmt.executeUpdate();
            }

            String qCred = "INSERT INTO STORICOCREDITO (Importo, EmailUtente, IDGruppo) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(qCred)) {
                stmt.setFloat(1, importo); 
                stmt.setString(2, emailDebitore); 
                stmt.setInt(3, idGruppo);
                stmt.executeUpdate();
            }

            String qDeb = "INSERT INTO STORICODEBITO (Importo, EmailUtente, IDGruppo) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(qDeb)) {
                stmt.setFloat(1, importo); 
                stmt.setString(2, emailCreditore); 
                stmt.setInt(3, idGruppo);
                stmt.executeUpdate();
            }

            conn.commit(); 
            return true;
        } catch (SQLException e) {
            try { conn.rollback(); } catch (Exception ex) {}
            return false;
        } finally {
            try { conn.setAutoCommit(true); } catch (Exception e) {}
        }
    }
}