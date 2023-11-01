package it.isa.progetto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class GestioneArtistaTest {
	static String url = "jdbc:db2://172.17.0.3:50000/SAMPLE:retrieveMessagesFromServerOnGetMessage=true;";
	static Connection con;
	static Statement stmt, stmtcheck;

	GestioneArtista ga = new GestioneArtista();
	
	@Test
	public void testUpdateArtista() {
		System.out.println("\nTest sull'update profilo artista 21 pilots\n");
		
		
		String loga = "21pilots", nome_gruppo = "Twenty One Pilots", dataFormazione="1991-12-23", numComponenti = "4", genere = "funko-pop";
		
		ga.updateModProfilo(loga, loga, nome_gruppo, dataFormazione, numComponenti, genere);

		// CONNESSIONE A DB2
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");

		} catch(java.lang.ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: "); 
			System.err.println(e.getMessage());
		}
				
		try {
			con = DriverManager.getConnection(url, "db2inst1", "password");
			stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("select * from ARTISTA "
					+"where LOG='" + loga + "' and NOME_ARTISTA='" + nome_gruppo + "';");
			
			while(rs.next()) {
				String nomeRitornato = rs.getString("NOME_ARTISTA").trim();
				String dataFormazioneRitornata = rs.getString("DATA_FORMAZIONE").trim();
				String numComponentiRitornato = rs.getString("NUM_COMPONENTI").trim();
				String genereRitornato = rs.getString("GENERE").trim();
				
				
				assertArrayEquals(new String[] {nomeRitornato, dataFormazioneRitornata, numComponentiRitornato, genereRitornato},
						new String[] {nome_gruppo, dataFormazione, numComponenti, genere});
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
}
