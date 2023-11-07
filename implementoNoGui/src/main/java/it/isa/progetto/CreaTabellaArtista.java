package it.isa.progetto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreaTabellaArtista {
	static String url = "jdbc:db2://172.20.0.3:50000/SAMPLE:retrieveMessagesFromServerOnGetMessage=true;";
	static Connection con;
	static Statement stmt, stmtcheck;
	
	
	public void esegui() {
		// CONNESSIONE A DB2
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");

		} catch(java.lang.ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: "); 
			System.err.println(e.getMessage());
		}
		
		
		/********** TABELLA: ARTISTA ************/
		try {
			con = DriverManager.getConnection(url, "db2inst1", "password");
        	stmt = con.createStatement();
        	
        	stmt.executeUpdate("create table ARTISTA " +
        			//"(ID integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1), " + /******* ATTENZIONE ALLA PARENTESII!!!! ****/
					"(LOG char(32) NOT NULL UNIQUE, " + 
					"PASSW char(18) NOT NULL, " +
					"NOME_ARTISTA char(33) NOT NULL PRIMARY KEY, " +
					"DATA_FORMAZIONE date NOT NULL, " +
					"NUM_COMPONENTI int NOT NULL, " +
					"GENERE char(15) NOT NULL ); ");
        	
		} catch(SQLException ex) {
			System.err.println("ERRORE CREATE ARTISTA: "+ex.getMessage());
		}
		
		/********** POPOLO ARTISTA ************/
		try {			
			
			stmt.executeUpdate("insert into ARTISTA(LOG, PASSW, NOME_ARTISTA, DATA_FORMAZIONE, NUM_COMPONENTI, GENERE) " + 
					"values('zucchero24', 'zucchero24', 'Zucchero Fornaciari', '1995-09-25', 1, 'pop-rock');");

			stmt.executeUpdate("insert into ARTISTA(LOG, PASSW, NOME_ARTISTA, DATA_FORMAZIONE, NUM_COMPONENTI, GENERE) " + 
				     "values('litfibaa', 'litfibaa', 'Litfiba', '1980-10-01', 5, 'rock');");
			
			stmt.executeUpdate("insert into ARTISTA(LOG, PASSW, NOME_ARTISTA, DATA_FORMAZIONE, NUM_COMPONENTI, GENERE) " + 
				     "values('21pilots', '21pilots', 'Twenty One Pilots', '2008-05-01', 2, 'tecno-pop');");
			
			stmt.executeUpdate("insert into ARTISTA(LOG, PASSW, NOME_ARTISTA, DATA_FORMAZIONE, NUM_COMPONENTI, GENERE) " + 
				     "values('pingu90', 'pingu90', 'Pinguini Tattici Nucleari', '2014-02-01', 6, 'pop');");
			
			stmt.executeUpdate("insert into ARTISTA(LOG, PASSW, NOME_ARTISTA, DATA_FORMAZIONE, NUM_COMPONENTI, GENERE) " + 
				     "values('pucci71', 'pucci71', 'Andrea Pucci', '2008-06-01', 2, 'commedia-teatro');");
			
			stmt.executeUpdate("insert into ARTISTA(LOG, PASSW, NOME_ARTISTA, DATA_FORMAZIONE, NUM_COMPONENTI, GENERE) " + 
				     "values('giorgia71', 'giorgia71', 'Giorgia', '1993-10-01', 1, 'pop-soul-r&b');");
			
			stmt.executeUpdate("insert into ARTISTA(LOG, PASSW, NOME_ARTISTA, DATA_FORMAZIONE, NUM_COMPONENTI, GENERE) " + 
				     "values('ligabue60', 'ligabue60', 'Luciano Riccardo Ligabue', '1987-01-01', 1, 'pop-rock');");
			
		} catch(SQLException ex) {
		System.err.println("ERRORE sugli INSERT into ARTISTA: "+ex.getMessage());
		}
			
	}//esegui
	
}
