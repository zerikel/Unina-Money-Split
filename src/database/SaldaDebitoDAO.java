package database;

import Entities.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SaldaDebitoDAO {

    public List<Quota> getQuoteAperte(String emailDebitore, Gruppo gruppo) {
        List<Quota> lista = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        
        String query = "SELECT Q.idspesa, Q.importo, S.descrizione, S.data, S.emailPagatore, U.nome, U.cognome " +
                "FROM quota Q " +
                "JOIN spesa S ON Q.idspesa = S.idspesa " +
                "JOIN utente U ON S.emailpagatore = U.email " +
                "WHERE Q.emailutente = ? AND S.idgruppo = ? " +
                "AND S.emailpagatore != ? " + 
                "AND NOT EXISTS (SELECT 1 FROM movimento M WHERE M.idspesaquota = Q.idspesa AND M.emailquotautente = Q.emailutente)";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, emailDebitore);
            stmt.setInt(2, gruppo.getIdGruppo());
            stmt.setString(3, emailDebitore);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Utente utenteCreditore = new Utente(rs.getString("emailpagatore"), "", rs.getString("cognome"), rs.getString("nome"));
                Partecipazione pagatore = new Partecipazione(utenteCreditore, gruppo);
                Spesa spesaRif = new SpesaComune(rs.getInt("idspesa"),rs.getFloat("importo"), rs.getString("descrizione"), rs.getDate("data").toLocalDate(), gruppo, pagatore);
                
                Partecipazione debitore = new Partecipazione(new Utente(emailDebitore, "", "", ""), gruppo);
                
                lista.add(new Quota(rs.getFloat("importo"), debitore, spesaRif));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public boolean salvaRimborso(int idSpesa, String emailDebitore, float importo, String emailCreditore, int idGruppo) {
        Connection conn = DBConnection.getConnection();
        try {
        	conn.setAutoCommit(false);
        	
            String qCred = "INSERT INTO STORICOCREDITO (Importo, EmailUtente, IDGruppo, IdSpesaCredito, EmailDebitore) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(qCred)) {
                stmt.setFloat(1, importo); 
                stmt.setString(2, emailDebitore);
                stmt.setInt(3, idGruppo);
                stmt.setInt(4, idSpesa);
                stmt.setString(5, emailCreditore); 
                stmt.executeUpdate();
            }

      
            String qDeb = "INSERT INTO STORICODEBITO (Importo, EmailUtente, IDGruppo, IdSpesaDebito, EmailCreditore) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(qDeb)) {
                stmt.setFloat(1, importo); 
                stmt.setString(2, emailCreditore); 
                stmt.setInt(3, idGruppo);
                stmt.setInt(4, idSpesa);
                stmt.setString(5, emailDebitore); 
                stmt.executeUpdate();
            }

            String qMov = "INSERT INTO movimento (data,ora,commento,importo,idspesaquota,emailquotautente) values (?,?,?,?,?,?)";
            try (PreparedStatement stmt = conn.prepareStatement(qMov))
            {
            	stmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            	stmt.setTime(2, java.sql.Time.valueOf(LocalTime.now()));
            	stmt.setString(3, "");
            	stmt.setFloat(4, importo);
            	stmt.setInt(5, idSpesa);
            	stmt.setString(6, emailDebitore);
            	stmt.executeUpdate();
            }
            
            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}