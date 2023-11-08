package it.isa.progetto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreaTabellaLocation {
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
		
		
		/********** TABELLA: LOCATION  ************/
		try {
			con = DriverManager.getConnection(url, "db2inst1", "password");
			stmt = con.createStatement();
        	stmt.executeUpdate("create table LOCATION " +
        			//"(ID int not null GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1), " +
					"(NOME_LOCATION char(32) NOT NULL PRIMARY KEY, " +
					"INDIRIZZO char(32) NOT NULL, " +
					"CITTA char(33) NOT NULL, " +
					"CAPIENZA int NOT NULL );");
					//"PRIMARY KEY(ID));");
        	
		} catch(SQLException ex) {
			System.err.println("ERRORE CREATE LOCATION: "+ex.getMessage());
		}
		/********** POPOLO LOCATIONS ************/
		try {
			stmt.executeUpdate("insert into LOCATION(NOME_LOCATION, INDIRIZZO, CITTA, CAPIENZA) " + 
				     "values('Castello Carrarese', 'Via Guido Negri, 9', 'Este', 7000);");
				
				stmt.executeUpdate("insert into LOCATION(NOME_LOCATION, INDIRIZZO, CITTA, CAPIENZA) " + 
				     "values('San Siro', 'Piazzale Angelo Moratti, 20151', 'Milano', 50000);");

				stmt.executeUpdate("insert into LOCATION(NOME_LOCATION, INDIRIZZO, CITTA, CAPIENZA) " + 
				     "values('Unipol Arena', 'Via Gino Cervi, 2', 'Casalecchio di Reno', 9000);");

				stmt.executeUpdate("insert into LOCATION(NOME_LOCATION, INDIRIZZO, CITTA, CAPIENZA) " + 
					     "values('RCF Arena', 'Via dell''Aeronautica, 42122', 'Reggio Emilia', 100000);");

				stmt.executeUpdate("insert into LOCATION(NOME_LOCATION, INDIRIZZO, CITTA, CAPIENZA) " + 
					     "values('Piazza Trento - Trieste', 'Piazza Trento - Trieste', 'Ferrara', 7000);");
				
		} catch(SQLException ex) {
		System.err.println("ERRORE INSERT into LOCATION: "+ex.getMessage());
		}
		
	
	}//esegui
	
}
