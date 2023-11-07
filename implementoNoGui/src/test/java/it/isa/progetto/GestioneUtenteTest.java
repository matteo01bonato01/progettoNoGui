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

public class GestioneUtenteTest {

	static String url = "jdbc:db2://172.20.0.3:55000/SAMPLE:retrieveMessagesFromServerOnGetMessage=true;";
	static Connection con;
	static Statement stmt, stmtcheck;	

	
	@Test
	public void testUpdateProfilo() {
		System.out.println("\nTest sull'update profilo utente\n");
		
		String logp = "giova55", nome = "Giova", cognome="Bellot", nascita = "2020-07-21", email = "email@email.com";
		
		GestioneUtente gu = new GestioneUtente();
		gu.updateModProfilo(logp, logp, nome, cognome, nascita, email);

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
			
			ResultSet rs = stmt.executeQuery("select * from UTENTEREGISTRATO "
					+"where LOG='" + logp + "';");
			
			while(rs.next()) {
				String nomeRitornato = rs.getString("NOME").trim();
				String cognomeRitornato = rs.getString("COGNOME").trim();
				String dataNascitaRitornata = rs.getString("DATA_NASCITA").trim();
				String emailRitornata = rs.getString("EMAIL").trim();
				
				
				//assertEquals(dataNascitaRitornata, nascita);
				assertArrayEquals(new String[] {nomeRitornato, cognomeRitornato, dataNascitaRitornata, emailRitornata},
						new String[] {nome, cognome, nascita, email});
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	

	@Test
	public void testAddEvento() {
		System.out.println("\nTest sul prenota evento\n");
		
		int id = 3;
		String log = "admin", pass = "admin";
		
		
		GestioneUtente gu = new GestioneUtente();
		gu.addEvento(id, log, pass);

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
			
			ResultSet rs = stmt.executeQuery("select * from PRENOTAZIONE "
					+"where ID_EVENTO='" + id + "' and LOGIN_UR='" + log + "';");
			
			while(rs.next()) {
				int id_risultato = Integer.parseInt(rs.getString("ID_EVENTO").trim());
				String log_risultato = rs.getString("LOGIN_UR").trim();
				
				assertEquals(id, id_risultato);
				assertEquals(log, log_risultato);
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
		
		
		
	

	@Test
	public void testEventiEconomici() {
		System.out.println("\nTest sugli eventi all'Unipol Arena con prezzo inferiore ai 50 euro,"
				+ " di genere rock e con l'utente 'matteo78'.\n");
		
		try {
			con = DriverManager.getConnection(url, "db2inst1", "password");
			stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("select A.*, E.*, P.*, U.* "
					+ "from EVENTO as E, PRENOTAZIONE as P, UTENTEREGISTRATO as U, ARTISTA as A "
					+ "where E.ID=P.ID_EVENTO and P.LOGIN_UR=U.LOG and A.NOME_ARTISTA=E.NOME_ARTISTA and U.NOME='matteo78' "
					+ "and E.PREZZO < 50 and A.GENERE='rock';");
			
			while(rs.next()) {
				assertEquals("matteo78", rs.getString("NOME").trim());
			}
			
		} catch (SQLException e1) {
			System.err.println("Errore su query complessa");
			e1.printStackTrace();
		}
		
	}
	
		

	@Test
	public void testDelEvento() {
		System.out.println("\nTest sul rimuove evento\n");
		
		int id = 3, evento = 2;
		String log = "admin", pass = "admin";
		
		
		GestioneUtente gu = new GestioneUtente();
		gu.delEvento(id, evento, log, pass);

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
			
			ResultSet rs = stmt.executeQuery("select * from PRENOTAZIONE "
					+"where ID_EVENTO='" + id + "' and LOGIN_UR='" + log + "';");
			
			assertFalse(rs.next());
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	
	

	@Test
	public void testJTextFieldLimit () throws BadLocationException {
		System.out.println("\nTest sul JTextFieldLimit...\n");
		
		JTextFieldLimit jtl = new JTextFieldLimit(2, true);
		jtl = new JTextFieldLimit(2, false);
		
		AttributeSet a = null;
		jtl.insertString(0, null, a);
		jtl.insertString(0, "giacomo", a);
		jtl.insertString(0, "giacomoooooooooooo", a);
	}
}
