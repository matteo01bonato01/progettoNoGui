package it.isa.progetto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Drop {
	static String url = "jdbc:db2://172.17.0.3:50000/SAMPLE:retrieveMessagesFromServerOnGetMessage=true;";
	static Connection con;
	static Statement stmt;
	

	void esegui() {
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");

		} catch(java.lang.ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: "); 
			System.err.println(e.getMessage());
		}
		
		
		try {
			con = DriverManager.getConnection(url, "db2inst1", "password");
			stmt = con.createStatement();
			
			stmt.executeUpdate("DROP TABLE ARTISTA;");
			stmt.executeUpdate("DROP TABLE PRENOTAZIONE;");
			stmt.executeUpdate("DROP TABLE LOCATION;");
			stmt.executeUpdate("DROP TABLE EVENTO;");
			stmt.executeUpdate("DROP TABLE UTENTEREGISTRATO; ");
        	
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
