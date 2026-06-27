package database;

import Entities.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SpesaDAO {

    public boolean inserisciSpesa(Spesa spesa) {
        Connection conn = DBConnection.getConnection();
        
        String query = "INSERT INTO SPESA (ImportoTotale, Descrizione, Data, EmailPagatore, IDGruppo, TipoSpesa) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setFloat(1, spesa.getImportoTotale());
            stmt.setString(2, spesa.getDescrizione());
            stmt.setDate(3, java.sql.Date.valueOf(spesa.getData())); 
            
            stmt.setString(4, spesa.getPagatore().getMyUtente().getEmail()); 
            stmt.setInt(5, spesa.getSGruppo().getIdGruppo());
            
            if (spesa instanceof SpesaComune) {
                stmt.setString(6, "COMUNE");
            } else {
                stmt.setString(6, "PERSONALE");
            }
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}