package it.isa.progetto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit test for simple App.
 */
@TestInstance(Lifecycle.PER_CLASS)
public class AppTest {
	static String url = "jdbc:db2://172.20.0.3:50000/SAMPLE:retrieveMessagesFromServerOnGetMessage=true;";
	static Connection con;
	static Statement stmt, stmtcheck;	

	@Test
	public void testUtenteRegistrato() {
		System.out.println("Test sul numero di utenti inseriti all'avvio. Attesi 5");
		
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
			
			ResultSet rs = stmt.executeQuery("select count(LOG) as TOTAL from UTENTEREGISTRATO;");
			
			while(rs.next()) {
				int risultato = Integer.parseInt(rs.getString("TOTAL"));
				System.out.println("Sta matchando le " + rs.getString("TOTAL") + " tuple di utenti ritornate...");
				assertEquals(5,risultato);
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	@Test
	public void testArtista() {
		
		System.out.println("Test sul numero di artisti inseriti all'avvio. Attesi 7");
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
			
			ResultSet rs = stmt.executeQuery("select count(LOG) as TOTAL from ARTISTA;");
			
			while(rs.next()) {
				int risultato = Integer.parseInt(rs.getString("TOTAL"));
				System.out.println("Sta matchando le " + rs.getString("TOTAL") + " tuple di artista ritornate...");
				assertEquals(7, risultato);
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	

	
	
	
	

	@Test
	public void testCheckLoginUser() {
		
		String userTest = "giova55";
		System.out.println("Test sul login di: " + userTest + ", " + userTest);
		
		PannelloLogin pl = new PannelloLogin();
		pl.checkLogin(new JTextField(userTest), new JPasswordField(userTest));
		
		
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
					+ "where LOG='" + userTest + "' and PASSW='" + userTest + "';");
			
			while(rs.next()) {
				String risultatoQuery = rs.getString("LOG").trim();
				assertEquals(userTest, risultatoQuery);
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	
	@Test
	public void testCheckLoginArtista() {
		
		String artist="21pilots";
		System.out.println("Test sul login di: " + artist + ", " + artist);
		
		PannelloLogin pl = new PannelloLogin();
		pl.checkLogin(new JTextField(artist), new JPasswordField(artist));
		
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
					+ "where LOG='" + artist + "' and PASSW='" + artist + "';");
			
			while(rs.next()) {
				String risultatoQuery = rs.getString("LOG").trim();
				assertEquals(artist,risultatoQuery);
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	

	@Test
	public void testAutenticazioneFallita() {
		
		String logUnk="beppe";
		System.out.println("Test sull'autenticazione fallita di " + logUnk);
		
		
		PannelloLogin pl = new PannelloLogin();
		pl.checkLogin(new JTextField(logUnk), new JPasswordField(logUnk));
		
		
		//doppia verifica
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
					+ "where LOG='" + logUnk + "' and PASSW='" + logUnk + "';");

			boolean risultato = rs.next();
			assertFalse(risultato);
			assertEquals(risultato, false);
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			con = DriverManager.getConnection(url, "db2inst1", "password");
			stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("select * from ARTISTA "
					+ "where LOG='" + logUnk + "' and PASSW='" + logUnk + "';");
			
			assertFalse(rs.next());
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	

	@BeforeAll
	public void execBeforeAll() {
		System.out.println("Iniziamo con i test !");
		
		System.out.println("Testo App e PannelloLogin");
		App app = new App();
		app.main(null);
	}
	
	@AfterAll
	public void execAfterAll() {
		System.out.println("\nHo finito.");
	}
	@BeforeEach
	public void execBeforeEach() {
		System.out.println("\n");
	}
	
	/*
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }*/
}
