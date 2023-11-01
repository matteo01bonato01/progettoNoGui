package it.isa.progetto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreaTabellaEvento {
	static String url = "jdbc:db2://172.17.0.3:50000/SAMPLE:retrieveMessagesFromServerOnGetMessage=true;";
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
        	
        	stmt.executeUpdate("create table EVENTO " +
        			"(ID integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1), " + /******* ATTENZIONE ALLA PARENTESII!!!! ****/
					"NOME_EVENTO char(50) NOT NULL, " +
					"NOME_LOCATION char(33), " +
					"NOME_ARTISTA char(33), " +
					"DATA_EVENTO date NOT NULL, " +
					"PREZZO double NOT NULL, " +
					"DISPONIBILITA int NOT NULL, " +
					"foreign key(NOME_ARTISTA) references ARTISTA(NOME_ARTISTA) on DELETE SET NULL on UPDATE NO ACTION, " +
					"foreign key(NOME_LOCATION) references LOCATION(NOME_LOCATION) on DELETE SET NULL on UPDATE NO ACTION, " +
					"PRIMARY KEY(ID));");
        	
		} catch(SQLException ex) {
			System.err.println("ERRORE su CREATE EVENTO: "+ex.getMessage());
		}
		
		/********** POPOLO EVENTI ************/
		try {			
			
			stmt.executeUpdate("insert into EVENTO(NOME_EVENTO, NOME_LOCATION, NOME_ARTISTA, DATA_EVENTO, PREZZO, DISPONIBILITA) " + 
					 "values('Zucchero in Festa', 'Castello Carrarese', 'Zucchero Fornaciari', '2022-06-05', 79, 5000);");

			stmt.executeUpdate("insert into EVENTO(NOME_EVENTO, NOME_LOCATION, NOME_ARTISTA, DATA_EVENTO, PREZZO, DISPONIBILITA) " + 
				     "values('Pinguini Indoor', 'Unipol Arena', 'Pinguini Tattici Nucleari', '2024-04-27', 81, 9000);");

			stmt.executeUpdate("insert into EVENTO(NOME_EVENTO, NOME_LOCATION, NOME_ARTISTA, DATA_EVENTO, PREZZO, DISPONIBILITA) " + 
				     "values('Trench Tour', 'Unipol Arena', 'Twenty One Pilots', '2019-02-06', 60, 9000);");

			stmt.executeUpdate("insert into EVENTO(NOME_EVENTO, NOME_LOCATION, NOME_ARTISTA, DATA_EVENTO, PREZZO, DISPONIBILITA) " + 
				     "values('Pucci Show', 'Castello Carrarese', 'Andrea Pucci', '2022-06-08', 39, 7000);");

			stmt.executeUpdate("insert into EVENTO(NOME_EVENTO, NOME_LOCATION, NOME_ARTISTA, DATA_EVENTO, PREZZO, DISPONIBILITA) " + 
				     "values('Ferrara Summer Festival - Giorgia', 'Piazza Trento - Trieste', 'Giorgia', '2022-06-08', 31, 7000);");
			
			stmt.executeUpdate("insert into EVENTO(NOME_EVENTO, NOME_LOCATION, NOME_ARTISTA, DATA_EVENTO, PREZZO, DISPONIBILITA) " + 
				     "values('Campovolo Ligabue', 'RCF Arena', 'Luciano Riccardo Ligabue', '2022-06-04', 89, 100000);");
			
			stmt.executeUpdate("insert into EVENTO(NOME_EVENTO, NOME_LOCATION, NOME_ARTISTA, DATA_EVENTO, PREZZO, DISPONIBILITA) " + 
				     "values('L''ultimo girone', 'Piazza Trento - Trieste', 'Litfiba', '2022-07-16', 39, 7000);");
			
		} catch(SQLException ex) {
		System.err.println("ERRORE sugli INSERT into EVENTO: "+ex.getMessage());
		}
	}//esegui
}
