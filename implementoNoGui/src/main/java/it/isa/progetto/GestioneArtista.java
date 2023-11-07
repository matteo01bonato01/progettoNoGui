package it.isa.progetto;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/*
class JTextFieldLimit extends PlainDocument {
	   private int limit;
	   JTextFieldLimit(int limit) {
	      super();
	      this.limit = limit;
	   }
	   JTextFieldLimit(int limit, boolean upper) {
	      super();
	      this.limit = limit;
	   }
	   public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
	      if (str == null)
	         return;
	      if ((getLength() + str.length()) <= limit) {
	         super.insertString(offset, str, attr);
	      }
	   }
	}
*/


public class GestioneArtista {

	//private static JFrame fListaEventi, frameMieiEventi, frModifica;
	//private static JPanel jpListaEventi, pannelloMieiEventi, panModifica;
	static String url = "jdbc:db2://172.20.0.3:55000/SAMPLE:retrieveMessagesFromServerOnGetMessage=true;";
	static Connection con;
	static Statement stmt;
	static JTextField nome_evento_prenotato, nome_evento_prenotabile;
	static JTextField jlogin, jnomeartista, jgiornoformazione, jmeseformazione, jannoformazione, jnumcomponenti, jgenere;
	static JPasswordField jpsw;
	

	/*********** TABELLA CON TUTTI GLI EVENTI **************************/
	void esegui(String user, String password) {

		//apro la finestra per profilo personale
		modificaProfilo(user);
		
		
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");

		} catch(java.lang.ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: "); 
			System.err.println(e.getMessage());
		}
		
		
		
		//Creo unJFrame per visualizzare la tabella
		/*fListaEventi = new JFrame("Eventi in programma");
		fListaEventi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jpListaEventi = new JPanel();
		JLabel l = new JLabel();
		
		//di default viene tutto grassetto; questo lo rimuove
		Font font = l.getFont();
		// unbold
		l.setFont(font.deriveFont(font.getStyle() & ~Font.BOLD));

		l.setHorizontalAlignment(SwingConstants.CENTER);
		l.setVerticalAlignment(SwingConstants.CENTER);
		
        l.setBackground(Color.GREEN);
        l.setOpaque(true);
		//aggiungo label al pannello Stadi
		l.add(jpListaEventi);
		//e lo aggiungo al frame
		fListaEventi.add(l, BorderLayout.CENTER);
		
		fListaEventi.setLocation(10, 150);
		fListaEventi.setSize(1100,400);
		fListaEventi.setVisible(true);
		
		//l.setText("");
		l.setText("<html><table border = 1><tr style='font-weight: bold'>" +
        		"<td>EVENTO</td><td>LOCATON</td><td>ARTISTA</td><td>DATA</td>" +
				"<td>PREZZO</td><td>DISPONIBILITA'</td></tr>");
        */
        
		try {
			con = DriverManager.getConnection(url, "db2inst1", "password");
			stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("select * from EVENTO;");
			while(rs.next()) {
				/*
				 * essendoci ambiguita nell'estrarre il campo nome,
				 * ho specificato la tabella da cui estrarre il NOME EVENTO
				 * si potrebbe aggiungere opzionalmente ORDER BY
				 
				String nome_evento = rs.getString("NOME_EVENTO");
				String nome_location = rs.getString("NOME_LOCATION");
				String nome_artista = rs.getString("NOME_ARTISTA");
				String data = rs.getString("DATA_EVENTO");
				String prezzo = rs.getString("PREZZO");
				String disponibilita = rs.getString("DISPONIBILITA");
				
				//Mostro risultati nella tabella
				l.setText(l.getText() + "<tr><td>"+ nome_evento + " </td><td>" + nome_location +
		        		"</td><td> " + nome_artista +" </td><td> " + data + "</td><td> " +
		        		prezzo + "</td><td>" + disponibilita + "</td></tr>");
		        */
			}
			
			//l.setText(l.getText() + "</table></html>");
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}//esegui
	
	
	
	
	
	
	
	
	
	
	

	/************* MODIFICA TUO PROFILO *****************/
	void modificaProfilo(String log) {
		
		/*
        frModifica = new JFrame("Modifica Profilo");
        frModifica.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panModifica = new JPanel(new GridLayout(0,2));
		*/
		
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");

		} catch(java.lang.ClassNotFoundException err) {
			System.err.print("ClassNotFoundException: "); 
			System.err.println(err.getMessage());
		}


		try {
			con = DriverManager.getConnection(url, "db2inst1", "password");
			stmt = con.createStatement();
        	ResultSet rsmodifica = stmt.executeQuery("select * from ARTISTA where LOG='" + log + "';");
        	
        	while(rsmodifica.next()) {
				jlogin = new JTextField(rsmodifica.getString("LOG").trim());
				jlogin.setEditable(false);
				jpsw = new JPasswordField(rsmodifica.getString("PASSW").trim());
				

				jnomeartista = new JTextField();
				jnomeartista.setDocument(new JTextFieldLimit(33));
				jnomeartista.setText(rsmodifica.getString("NOME_ARTISTA").trim());
				jnomeartista.setEditable(false);
				//approfitto del valore NOME_ARTISTA estratto per aprire la finestra con i miei EVENTI
				mieiEventi(jnomeartista.getText().trim());
				
				//data
				jgiornoformazione = new JTextField(rsmodifica.getString("DATA_FORMAZIONE").substring(8,10).trim(),2);
				jgiornoformazione.addKeyListener(new KeyAdapter() {
			         public void keyTyped(KeyEvent e) {
			             char c = e.getKeyChar();
			             if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
			                  e.consume();  // if it's not a number, ignore the event
			             }
			         }
			      });
				jmeseformazione = new JTextField(rsmodifica.getString("DATA_FORMAZIONE").substring(5,7).trim(),2);
				jmeseformazione.addKeyListener(new KeyAdapter() {
			         public void keyTyped(KeyEvent e) {
			             char c = e.getKeyChar();
			             if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
			                  e.consume();  // if it's not a number, ignore the event
			             }
			         }
			      });
				jannoformazione = new JTextField(rsmodifica.getString("DATA_FORMAZIONE").substring(0,4).trim(),4);
				jannoformazione.addKeyListener(new KeyAdapter() {
			         public void keyTyped(KeyEvent e) {
			             char c = e.getKeyChar();
			             if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
			                  e.consume();  // if it's not a number, ignore the event
			             }
			         }
			      });
				//fine_data
				
				
				jnumcomponenti = new JTextField(rsmodifica.getString("NUM_COMPONENTI").trim(),2);
				jnumcomponenti.addKeyListener(new KeyAdapter() {
			         public void keyTyped(KeyEvent e) {
			             char c = e.getKeyChar();
			             if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
			                  e.consume();  // if it's not a number, ignore the event
			             }
			         }
			      });
				
				
				jgenere = new JTextField();
				jgenere.setDocument(new JTextFieldLimit(25));
				jgenere.setText(rsmodifica.getString("GENERE").trim());
				
				/*
				JButton jmodifica = new JButton("SALVA MODIFICHE");
				
				
				
				
				panModifica.add(jlogin);
				panModifica.add(jpsw);
				panModifica.add(jnomeartista);
				panModifica.add(jgiornoformazione);
				panModifica.add(jmeseformazione);
				panModifica.add(jannoformazione);
				panModifica.add(jnumcomponenti);
				panModifica.add(jgenere);
				panModifica.add(jmodifica);
				*/
				//panModifica.setBackground(Color.YELLOW);
				/*
				frModifica.add(panModifica);
		
				frModifica.setLocation(690, 150);
				frModifica.setSize(430,200);
				frModifica.setVisible(true);
				
				jmodifica.addActionListener(new ActionListener(){ 
					public void actionPerformed(ActionEvent e){
						
						char[] p = jpsw.getPassword();
						String passwordInserita = new String(p);
						String data = jannoformazione.getText() + "-" + jmeseformazione.getText() + "-" + jgiornoformazione.getText();
						updateModProfilo(jlogin.getText(), passwordInserita, jnomeartista.getText(), data, jnumcomponenti.getText(), jgenere.getText());
						
						frModifica.setVisible(false);
			        }
			    });*/
			} //while
		}catch (SQLException e1) {
			System.out.println("Errore nella ricerca dell'utente per la modifica dei propri dati: ");
			e1.printStackTrace();
		}
	
	}//fine metodo profilo
	

	
	
	
	
	
	
	
	void updateModProfilo(String log, String passw, String nomeArtista, String dataFormazione, String numComponenti, String genere) {
		int numeroC = Integer.parseInt(numComponenti);

		
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");

		} catch(java.lang.ClassNotFoundException err) {
			System.err.print("ClassNotFoundException: "); 
			System.err.println(err.getMessage());
	   		System.out.println("mod fatta");
		}


		try {
			con = DriverManager.getConnection(url, "db2inst1", "password");
			stmt = con.createStatement();
        	stmt.executeUpdate("update ARTISTA"
        			+ " set PASSW='"+passw+"', DATA_FORMAZIONE='"+dataFormazione
        			+ "', NUM_COMPONENTI="+numeroC+", GENERE='"+genere+"' "
        			+ "  where LOG='" + log + "' and NOME_ARTISTA='"+nomeArtista+"';");
			
		} catch (SQLException e1) {
			System.out.println("Errore aggiornamento dati personali dell'utente: ");
			e1.printStackTrace();
		}
		
		modificaProfilo(log);
	}
	
	
	
	
	
	
	
	
	void mieiEventi(String nomeArtista) {

		//Creo unJFrame per visualizzare la tabella
		/*
		frameMieiEventi = new JFrame("I tuoi eventi in programma");
		frameMieiEventi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pannelloMieiEventi = new JPanel();
		JLabel l = new JLabel();
		
		//di default viene tutto grassetto; questo lo rimuove
		Font font = l.getFont();
		// unbold
		l.setFont(font.deriveFont(font.getStyle() & ~Font.BOLD));

		l.setHorizontalAlignment(SwingConstants.CENTER);
		l.setVerticalAlignment(SwingConstants.CENTER);
		
        l.setBackground(Color.PINK);
        l.setOpaque(true);
		//aggiungo label al pannello Stadi
		l.add(pannelloMieiEventi);
		//e lo aggiungo al frame
		frameMieiEventi.add(l, BorderLayout.CENTER);
		
		frameMieiEventi.setLocation(620, 360);
		frameMieiEventi.setSize(500,300);
		frameMieiEventi.setVisible(true);
		
		//l.setText("");
		l.setText("<html><table border = 1><tr style='font-weight: bold'>" +
        		"<td>ID</td><td>EVENTO</td><td>LOCATON</td><td>ARTISTA</td><td>DATA</td></tr>");
        */
        
		try {
			con = DriverManager.getConnection(url, "db2inst1", "password");
			stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("select * from EVENTO where NOME_ARTISTA='"+ nomeArtista +"';");
			while(rs.next()) {

				String id_evento = rs.getString("ID");
				String nome_evento = rs.getString("NOME_EVENTO");
				String nome_location = rs.getString("NOME_LOCATION");
				String nome_artista = rs.getString("NOME_ARTISTA");
				String data = rs.getString("DATA_EVENTO");
				
				//Mostro risultati nella tabella
				/*
		        l.setText(l.getText() + "<tr><td>"+ id_evento + " </td><td>"+ nome_evento + " </td><td>" + nome_location + 
		        		"</td><td> " + nome_artista +" </td><td> " + data + "</td></tr>");
		        */
			}
			
			//l.setText(l.getText() + "</table></html>");
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
