package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Entities.Utente;

public class UtenteDAO {
    
    public Utente getUtenteByEmail(String email) {
        Utente utenteTrovato = null;
        Connection conn = DBConnection.getConnection();
        
        String query = "SELECT * FROM utente WHERE email = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                utenteTrovato = new Utente(rs.getString("email"), rs.getString("password"), rs.getString("cognome"), rs.getString("nome"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utenteTrovato;
    }
}