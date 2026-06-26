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
}
