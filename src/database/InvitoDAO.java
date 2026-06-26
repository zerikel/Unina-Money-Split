package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import Entities.Invito;
import Entities.Gruppo;
import Entities.Utente;

public class InvitoDAO {
	public List<Invito> getInvitiSospesiByUtente(String emailUtente)
	{
		List<Invito> listaTmp = new ArrayList<Invito>();
		Connection conn = DBConnection.getConnection();
		
		String query = "SELECT i.statoinvito,g.idgruppo,g.nome,g.datacreazione,g.emailcreatore "
								+ "FROM invito AS i "
								+ "JOIN gruppo AS g on i.idgruppo = g.idgruppo "
								+ "WHERE i.emailutente = ? AND i.statoinvito = 'InAttesa'";
		try {
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, emailUtente);
		
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next())
		{
			String stato = rs.getString("statoinvito");
			String nomeGruppo = rs.getString("nome");
			Date dataCreazione = rs.getDate("datacreazione");
			String emailCreatore = rs.getString("emailcreatore");
			int idGruppoDB = rs.getInt("idgruppo");
			
			Utente utenteCreatore = new Utente(emailCreatore, "", "", "");
			Gruppo gruppo = new Gruppo(idGruppoDB,nomeGruppo,dataCreazione,utenteCreatore);
			Utente utenteInvitato = new Utente(emailUtente,"","","");
			
			Invito invitato = new Invito(stato, utenteInvitato, gruppo);
			listaTmp.add(invitato);
		}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return listaTmp;
	}

	public boolean rispondiInvito(String emailUtente,int idGruppo,boolean Stato)
	{
		Connection conn = DBConnection.getConnection();
		boolean successo = false;
		
		try 
		{
			String nuovoStato = Stato ? "Accettato" : "Rifiutato";
			
			String query = "UPDATE invito set statoinvito = ? "
								+ "WHERE emailutente = ? AND idgruppo = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, nuovoStato);
			pstmt.setString(2, emailUtente);
			pstmt.setInt(3, idGruppo);
			
			pstmt.executeUpdate();
			successo = true;
		}catch (SQLException e) {
			e.printStackTrace();
		}
	return successo;
	}
}
