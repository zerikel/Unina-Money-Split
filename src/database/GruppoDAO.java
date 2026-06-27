package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import Entities.Gruppo;
import Entities.Utente;

public class GruppoDAO {
	public List<Gruppo> getGruppiByUtente (String emailUtente)
	{
		List<Gruppo> listaTmp = new ArrayList<Gruppo>();
		Connection conn = DBConnection.getConnection();
		
		String query = "SELECT g.nome,g.datacreazione,g.emailcreatore,g.idgruppo "
								+ "FROM gruppo AS g "
								+ "JOIN partecipazione AS p ON g.idgruppo = p.idgruppo "
								+ "WHERE p.emailutente = ?";
		try 
		{
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, emailUtente);

			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next())
			{
				String nomeGruppo = rs.getString("nome");
				Date dataCreazione = rs.getDate("datacreazione");
				String emailCreatore = rs.getString("emailcreatore");
				int idGruppoDB = rs.getInt("idgruppo");
				
				Utente utenteCreatore = new Utente(emailCreatore, "", "", "");
				
				Gruppo gruppo = new Gruppo(idGruppoDB,nomeGruppo,dataCreazione,utenteCreatore);
				listaTmp.add(gruppo);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
	return listaTmp;
	}
	
	public int creaNuovoGruppo (String nome,String emailCreatore)
	{
		Connection conn = DBConnection.getConnection();
		int idGenerato = -1;
		try 
		{
			String query = "INSERT INTO gruppo (nome, emailcreatore) VALUES (?,?)";
			PreparedStatement pstmt = conn.prepareStatement(query,java.sql.Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, nome);
			pstmt.setString(2, emailCreatore);
			pstmt.executeUpdate();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next())
			{
				idGenerato = rs.getInt(1);
			}			
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return idGenerato;
	}
	
	public boolean isUtentePartecipante(String email, int idGruppo) {
	    Connection conn = DBConnection.getConnection();
	    String query = "SELECT 1 FROM PARTECIPAZIONE WHERE emailutente = ? AND idgruppo = ?";
	    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
	        pstmt.setString(1, email);
	        pstmt.setInt(2, idGruppo);
	        ResultSet rs = pstmt.executeQuery();
	        return rs.next(); // Ritornerà true se trova almeno un elemento nel risultato
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
}
