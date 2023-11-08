package it.isa.progetto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreaTabellaPrenotazione {
	static String url = "jdbc:db2://172.17.0.2:50000/SAMPLE:retrieveMessagesFromServerOnGetMessage=true;";
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
        	
        	stmt.executeUpdate("create table PRENOTAZIONE " +
        			"(ID integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1), " + /******* ATTENZIONE ALLA PARENTESII!!!! ****/
					"LOGIN_UR char(32) NOT NULL, " +
					"ID_EVENTO int NOT NULL, " +
					"foreign key(LOGIN_UR) references UTENTEREGISTRATO(LOG) on DELETE CASCADE on UPDATE NO ACTION, " +
					"foreign key(ID_EVENTO) references EVENTO(ID) on DELETE CASCADE on UPDATE NO ACTION, " +
					"PRIMARY KEY(ID));");
        	
		} catch(SQLException ex) {
			System.err.println("ERRORE CREATE PRENOTAZIONE: "+ex.getMessage());
		}
		
		/********** POPOLO PRENOTAZIONE ************/
		try {			
			
			stmt.executeUpdate("insert into PRENOTAZIONE(LOGIN_UR, ID_EVENTO) " + 
			     "values('matte78', '2');");

			stmt.executeUpdate("insert into PRENOTAZIONE(LOGIN_UR, ID_EVENTO) " + 
				     "values('matte78', '1');");
			
			stmt.executeUpdate("insert into PRENOTAZIONE(LOGIN_UR, ID_EVENTO) " + 
				     "values('vice11', '3');");
			
			stmt.executeUpdate("insert into PRENOTAZIONE(LOGIN_UR, ID_EVENTO) " + 
				     "values('marco78', '4');");
			
			stmt.executeUpdate("insert into PRENOTAZIONE(LOGIN_UR, ID_EVENTO) " + 
				     "values('giova55', '3');");
			
			stmt.executeUpdate("insert into PRENOTAZIONE(LOGIN_UR, ID_EVENTO) " + 
				     "values('giova55', '7');");
			
		} catch(SQLException ex) {
			System.err.println("ERRORE sugli INSERT into PRENOTAZIONE: "+ex.getMessage());
		}
		
			
	}//esegui
}
