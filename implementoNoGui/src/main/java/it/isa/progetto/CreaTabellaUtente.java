package it.isa.progetto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreaTabellaUtente {
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
		
		
		/********** TABELLA: UTENTIREGISTRATI ************/
		try {
			con = DriverManager.getConnection(url, "db2inst1", "password");
        	stmt = con.createStatement();
			
        	stmt.executeUpdate("create table UTENTEREGISTRATO " +
        			//"(ID integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1), " + /******* ATTENZIONE ALLA PARENTESII!!!! ****/
					"(LOG char(32) NOT NULL, " +
					"PASSW char(18) NOT NULL, " +
					"NOME char(18) NOT NULL, " +
					"COGNOME char(18), " +
					"DATA_NASCITA date NOT NULL, " +
					"EMAIL char(30), " +
					"PRIMARY KEY(LOG));");
        	
		} catch(SQLException ex) {
			System.err.println("ERRORE CREATE UTENTEREGISTRATO: "+ex.getMessage());
		}
		
		/********** POPOLO UTENTIREGISTRATI ************/
		try {
			con = DriverManager.getConnection(url, "db2inst1", "password");
        	stmt = con.createStatement();
			
			stmt.executeUpdate("insert into UTENTEREGISTRATO(LOG, PASSW, NOME, COGNOME, DATA_NASCITA, EMAIL) " + 
			     "values('admin', 'admin', 'Amministratore', 'cognome', '1990-01-01', 'admin@ticketone.it');");
			
			
			/*
			stmt.executeUpdate("insert into UTENTEREGISTRATO " + 
			     "values('zucchero24', 'zucchero24', 'Zucchero', 'Fornaciari', '1995-09-25', 'zucchero24@inbox.lv');");

			stmt.executeUpdate("insert into UTENTIREGISTRATI " + 
				     "values('litfibaa', 'litfibaa', 'Litfiba', '', '1980-10-01', 'litfiba80@inbox.lv');");
			
			stmt.executeUpdate("insert into UTENTIREGISTRATI " + 
				     "values('21pilots', '21pilots', 'Twenty One', 'Pilots', '2008-05-01', '21pilots@inbox.lv');");
			
			stmt.executeUpdate("insert into UTENTIREGISTRATI " + 
				     "values('pingu90', 'pingu90', 'Pinguini', 'Tattici Nucleari', '2014-02-01', 'ptnn@inbox.lv');");
			*/
			
			
			stmt.executeUpdate("insert into UTENTEREGISTRATO(LOG, PASSW, NOME, COGNOME, DATA_NASCITA, EMAIL) " + 
			     "values('matte78', 'matte78', 'Matteo', 'Bonato', '1997-11-23', 'mate78@inbox.lv');");

			stmt.executeUpdate("insert into UTENTEREGISTRATO(LOG, PASSW, NOME, COGNOME, DATA_NASCITA, EMAIL) " + 
			     "values('vice11', 'vice11', 'Vincenzo', 'Ornano', '2003-01-21', 'vice11@inbox.lv');");

			stmt.executeUpdate("insert into UTENTEREGISTRATO(LOG, PASSW, NOME, COGNOME, DATA_NASCITA, EMAIL) " + 
			     "values('marco78', 'marco78', 'Massimo', 'Circonese', '1992-01-14', 'marco78@inbox.lv');");

			stmt.executeUpdate("insert into UTENTEREGISTRATO(LOG, PASSW, NOME, COGNOME, DATA_NASCITA, EMAIL) " + 
			     "values('giova55', 'giova55', 'Giovanni', 'Bernardi', '2000-12-13', 'giova55@inbox.lv');");
			
		} catch(SQLException ex) {
		System.err.println("ERRORE sugli INSERT into UTENTEREGISTRATO: "+ex.getMessage());
		}
		
	}//esegui
	
}
