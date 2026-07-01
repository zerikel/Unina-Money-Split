package database;

import Entities.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class SpesaDAO {

    public boolean inserisciSpesa(float importo, String descrizione, java.sql.Date data,String emailPagatore, int idGruppo,String tipoSpesa) {
        Connection conn = DBConnection.getConnection();
        
        String query = "INSERT INTO SPESA (ImportoTotale, Descrizione, Data, EmailPagatore, IDGruppo, TipoSpesa) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setFloat(1, importo);
            stmt.setString(2, descrizione);
            stmt.setDate(3, data); 
            
            stmt.setString(4, emailPagatore); 
            stmt.setInt(5, idGruppo);
            
            stmt.setString(6, tipoSpesa);
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}