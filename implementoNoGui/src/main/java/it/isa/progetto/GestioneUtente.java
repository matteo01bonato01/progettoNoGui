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



public class GestioneUtente {
	
	//private static JFrame fListaEventi, framePrenotami, frModifica;
	//private static JPanel jpListaEventi, pannelloPrenotami, panModifica;
	static String url = "jdbc:db2://172.17.0.2:50000/SAMPLE:retrieveMessagesFromServerOnGetMessage=true;";
	static Connection con;
	static Statement stmt;
	static JTextField nome_evento_prenotato, nome_evento_prenotabile;
	static JTextField jlogin, jnome, jcognome, jemail, jgiornonascita, jmesenascita, jannonascita;
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
		/*
		fListaEventi = new JFrame("Eventi in programma");
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
			con = DriverManager.getConnection(url,"db2inst1","password");
			stmt = con.createStatement();
			
			ResultSet rs = stmt.executeQuery("select * from EVENTO;");
			while(rs.next()) {
				/*
				 * essendoci ambiguita nell'estrarre il campo nome,
				 * ho specificato la tabella da cui estrarre il NOME EVENTO
				 * si potrebbe aggiungere opzionalmente ORDER BY
				 */
				String nome_evento = rs.getString("NOME_EVENTO");
				String nome_location = rs.getString("NOME_LOCATION");
				String nome_artista = rs.getString("NOME_ARTISTA");
				String data = rs.getString("DATA_EVENTO");
				String prezzo = rs.getString("PREZZO");
				String disponibilita = rs.getString("DISPONIBILITA");
				
				//Mostro risultati nella tabella
				/*
		        l.setText(l.getText() + "<tr><td>"+ nome_evento + " </td><td>" + nome_location + 
		        		"</td><td> " + nome_artista +" </td><td> " + data + "</td><td> " + prezzo + 
		        		" € </td><td>" + disponibilita + "</td></tr>");
        		*/
			}
			
			//l.setText(l.getText() + "</table></html>");
			
			
			//richiamo metodo per inserimento e cancellazione prenotazioni
			prenota_cancella(user, password);
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.err.println("Errore su Gestione utente: estrazione colonne eventi da mostrare: ");
			e1.printStackTrace();
		}
		
	}//esegui
	
	
	
	
	
	
	
	
	
	
	
	

	/*********** TABELLA PER PRENOTARSI/CANCELLARSI AGLI/DAGLI EVENTI **************************/
	void prenota_cancella(final String user, final String password) {
		
		//Creo JFrame per effettuare mie prenotazioni
        /*framePrenotami = new JFrame("Lista per cancellazione/iscrizione dagli/agli eventi");
        framePrenotami.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pannelloPrenotami = new JPanel(new GridLayout(0, 1));
        
		
		//CREO MENU A TENDINA PER SELEZIONARE GLI EVENTI A CUI SONO ISCRITTO
		JMenuBar jmb = new JMenuBar();
		JMenu menu = new JMenu("Seleziona evento: ");
		nome_evento_prenotato = new JTextField("",26);
		nome_evento_prenotato.setEditable(false);
		jmb.add(menu);
		JMenuItem jmi;
		*/
		
		try {
			con = DriverManager.getConnection(url, "db2inst1", "password");
        	stmt = con.createStatement();
        	
        	stmt.execute("create or replace view VIEW" + user + "SUBSCRIBED "
        			+ "AS select P.ID, P.ID_EVENTO, E.NOME_EVENTO, L.NOME_LOCATION "
        			+ "from LOCATION as L, EVENTO as E, PRENOTAZIONE as P, UTENTEREGISTRATO as UR "
					+ "where L.NOME_LOCATION=E.NOME_LOCATION and E.ID=P.ID_EVENTO and P.LOGIN_UR=UR.LOG "
					+ "and UR.LOG = '" + user + "';");
        	
			ResultSet rs = stmt.executeQuery("select ID, ID_EVENTO, NOME_EVENTO, NOME_LOCATION from VIEW" + user + "SUBSCRIBED;");
			/*
			jmi = new JMenuItem("Svuota campo");
			menu.add(jmi);
			
			
			while(rs.next()) {
				jmi = new JMenuItem(rs.getString("ID").trim() + ". (" + rs.getString("ID_EVENTO") +") " 
						+ rs.getString("NOME_EVENTO").trim() + " -/- " + rs.getString("NOME_LOCATION").trim());
		        menu.add(jmi);		
			}
			*/
		} catch(SQLException ex) {
			System.err.println("Errore su Gestione utente: estrazione colonne eventi sul metodo prenota_cancella: ");
			System.err.println("SQLException: " + ex.getMessage());
		}
		
		/*
		for(int i=0; i < menu.getItemCount(); i++) {
			if(i==0) {
				menu.getItem(i).addActionListener(new ActionListener(){  
					public void actionPerformed(ActionEvent e){  
						nome_evento_prenotato.setText("");
					}
				});
			}else {
				final String s = menu.getItem(i).getText();
				menu.getItem(i).addActionListener(new ActionListener(){  
					public void actionPerformed(ActionEvent e){  
						nome_evento_prenotato.setText(s);
					}
				});
			}
		}
		*/
		
		JButton elimina_prenotazione = new JButton("ELIMINA PRENOTAZIONE");
		elimina_prenotazione.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){
				
				try {
					String estrai = nome_evento_prenotato.getText();
					if(estrai != null){
						
						//eliminazione
						String[] num = estrai.split(". ");
						int idPrenotazione = Integer.parseInt(num[0]);
						
						String id_eevento = estrai.substring(estrai.indexOf("(")+1, estrai.indexOf(")"));
						int id_evento = Integer.parseInt(id_eevento);
						/*se tutto è andato a buon fine,
						 * allora avviene l'effettiva
						 * eliminazione della prenotazione
						 * */
						if(idPrenotazione > 0) {
							//framePrenotami.setVisible(false);
							delEvento(idPrenotazione, id_evento, user, password);

							System.out.println("idEvento a cui mi sto cancellando: "+id_evento);
						}
					} 
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					
	        }  
	    });
		
		/*
		pannelloPrenotami.add(jmb, "center");
		pannelloPrenotami.add(nome_evento_prenotato, "center");
		pannelloPrenotami.add(elimina_prenotazione);
		*/
		
		
		
		
		
		
		
		JMenuBar jmb2 = new JMenuBar();
		JMenu menu2 = new JMenu("Seleziona evento: ");
		nome_evento_prenotabile = new JTextField("",26);
		nome_evento_prenotabile.setEditable(false);
		jmb2.add(menu2);
		JMenuItem jmi2;
		
		try {
			con = DriverManager.getConnection(url, "db2inst1", "password");
        	stmt = con.createStatement();
        	
        	/*
        	 * 5 ore per farlo funzionare: devo escludere le tuple in cui compare il login
        	 * di tale user, e escludere l'id dell'evento (prelevabile dalla precedente View)
        	 * senno compaiono le possibili altre prenotazioni degli altri
        	 */
        	stmt.execute("create or replace view VIEW" + user + "PRENOTABLE "
        			+ "AS select E.ID, E.NOME_EVENTO, E.NOME_LOCATION, E.DISPONIBILITA "
        			+ "from LOCATION as L, EVENTO as E "
					//+ "where L.NOME_LOCATION=E.NOME_LOCATION and E.ID = P.ID_EVENTO and P.LOGIN_UR=UR.LOG "
					+ "where E.NOME_LOCATION=L.NOME_LOCATION and E.DISPONIBILITA>0 "
					+ "and E.ID NOT IN (select ID_EVENTO from VIEW" + user + "SUBSCRIBED);");
        	
			ResultSet rs2 = stmt.executeQuery("select * from VIEW" + user + "PRENOTABLE;");
			/*
			jmi2 = new JMenuItem("Svuota campo");
			menu2.add(jmi2);
			
			while(rs2.next()) {
				jmi2 = new JMenuItem(rs2.getString("ID").trim() + ". " + rs2.getString("NOME_EVENTO").trim()
						+ " -/- " + rs2.getString("NOME_LOCATION").trim() + " (" + rs2.getString("DISPONIBILITA") + ")");
		        menu2.add(jmi2);
			}
			*/
		} catch(SQLException ex) {
			System.err.println("SQLException on ResultSet2: " + ex.getMessage());
		}
		
		/*
		for(int i=0; i < menu2.getItemCount(); i++) {
			if(i==0) {
				menu2.getItem(i).addActionListener(new ActionListener(){  
					public void actionPerformed(ActionEvent e){  
						nome_evento_prenotabile.setText("");
					}
				});
			}else {
				final String s2 = menu2.getItem(i).getText();
				menu2.getItem(i).addActionListener(new ActionListener(){  
					public void actionPerformed(ActionEvent e){  
						nome_evento_prenotabile.setText(s2);
					}
				});
			}
		}*/
		
		JButton aggiungi_prenotazione = new JButton("AGGIUNGI PRENOTAZIONE");
		aggiungi_prenotazione.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){
				
				try {
					String estrai = nome_evento_prenotabile.getText();
					
					if(estrai != null) {
						String[] num = estrai.split(". ");
						int idEvento = Integer.parseInt(num[0]);

						/*
						 * se tutto è andato a buon fine,
						 * allora effettuo il vero inserimento
						 */
						if(idEvento >0) {
							//framePrenotami.setVisible(false);
							addEvento(idEvento, user, password);
							System.out.println("idEvento a cui mi sto prenotando: "+idEvento);
						}
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        }  
	    });
		
		/*
		pannelloPrenotami.add(jmb2, "center");
		pannelloPrenotami.add(nome_evento_prenotabile, "center");
		pannelloPrenotami.add(aggiungi_prenotazione);
		*/
		
		/* aggiungo i panel al frame */
		/*
		pannelloPrenotami.setBackground(Color.RED);
		framePrenotami.add(pannelloPrenotami);
		framePrenotami.setLocation(650, 150);
		framePrenotami.setSize(300,280);
		framePrenotami.setVisible(true);
		*/
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	void delEvento(int id_prenotazione, int id_evento, String login, String passw) {
		try {
			con = DriverManager.getConnection(url, "db2inst1", "password");
	    	stmt = con.createStatement();
	    	
	    	stmt.execute("delete from PRENOTAZIONE where ID=" + id_prenotazione + ";");
	    	
			//System.out.println(id_prenotazione);
		} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.err.println("Errore cancellazione Prenotazione: ");
				e1.printStackTrace();
		}
		
		try {
			con = DriverManager.getConnection(url, "db2inst1", "password");
	    	stmt = con.createStatement();
	    	
	    	stmt.execute("update EVENTO set DISPONIBILITA = DISPONIBILITA + 1 where ID=" + id_evento + ";");
	    	
			//System.out.println(id_prenotazione);
		} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.err.println("Errore aggiornamento disponibilita dell'evento su delEvento: ");
				e1.printStackTrace();
		}
		
    	prenota_cancella(login, passw);
	}
	
	
	
	

	void addEvento(int id_evento, String login, String passw) {
		try {
			con = DriverManager.getConnection(url, "db2inst1", "password");
	    	stmt = con.createStatement();
	    	
	    	stmt.execute("insert into PRENOTAZIONE(LOGIN_UR, ID_EVENTO) values('" + login + "', '" + id_evento + "');");
	
	    	//System.out.println(id_evento);
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			System.err.print("ERRORE su add PRENOTAZIONE in addEvento");
			ex.printStackTrace();
		}
		
		try {
			con = DriverManager.getConnection(url, "db2inst1", "password");
	    	stmt = con.createStatement();
	    	
	    	stmt.execute("update EVENTO set DISPONIBILITA = DISPONIBILITA - 1 where ID=" + id_evento + ";");
	    	
			//System.out.println(id_prenotazione);
		} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.err.println("Errore aggiornamento disponibilita dell'evento in addEvento: ");
				e1.printStackTrace();
		}
		
    	prenota_cancella(login, passw);
    	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/************* MODIFICA TUO PROFILO *****************/
	void modificaProfilo(String log) {
		/*
        frModifica = new JFrame("Modifica Profilo");
        frModifica.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panModifica = new JPanel(new GridLayout(0,3));
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
        	//stmt.execute("select * from UTENTEREGISTRATO where LOG='" + log + "';");
        	ResultSet rsmodifica = stmt.executeQuery("select * from UTENTEREGISTRATO where LOG='" + log + "';");
        	
        	while(rsmodifica.next()) {
        		/*rsmodifica.getString("NOME");
        		rsmodifica.getString("COGNOME");
        		rsmodifica.getString("DATA_NASCITA");
        		rsmodifica.getString("EMAIL");
        		 */
			

				jlogin = new JTextField(rsmodifica.getString("LOG").trim());
				jlogin.setEditable(false);
	
				
				jpsw = new JPasswordField(rsmodifica.getString("PASSW").trim());
				

				jnome = new JTextField();
				jnome.setEditable(false);
				jnome.setText(rsmodifica.getString("NOME").trim());
	
				jcognome = new JTextField();
				jcognome.setDocument(new JTextFieldLimit(11));
				jcognome.setText(rsmodifica.getString("COGNOME").trim());
				
				
				jgiornonascita = new JTextField(rsmodifica.getString("DATA_NASCITA").substring(8,10).trim(),2);
				jgiornonascita.addKeyListener(new KeyAdapter() {
			         public void keyTyped(KeyEvent e) {
			             char c = e.getKeyChar();
			             if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
			                  e.consume();  // if it's not a number, ignore the event
			             }
			         }
			      });
				jmesenascita = new JTextField(rsmodifica.getString("DATA_NASCITA").substring(5,7).trim(),2);
				jmesenascita.addKeyListener(new KeyAdapter() {
			         public void keyTyped(KeyEvent e) {
			             char c = e.getKeyChar();
			             if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
			                  e.consume();  // if it's not a number, ignore the event
			             }
			         }
			      });
				jannonascita = new JTextField(rsmodifica.getString("DATA_NASCITA").substring(0,4).trim(),4);
				jannonascita.addKeyListener(new KeyAdapter() {
			         public void keyTyped(KeyEvent e) {
			             char c = e.getKeyChar();
			             if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
			                  e.consume();  // if it's not a number, ignore the event
			             }
			         }
			      });
				
				
				
				jemail = new JTextField();
				jemail.setDocument(new JTextFieldLimit(25));
				jemail.setText(rsmodifica.getString("EMAIL").trim());
				

				JButton jmodifica = new JButton("SALVA MODIFICHE");
				
				
				//jvuoto solo per allineare le colonne sfasate a causa della data di nascita
				JTextField jvuoto = new JTextField();
				jvuoto.setEditable(false);
				JTextField jvuoto1 = new JTextField();
				jvuoto1.setEditable(false);
				JTextField jvuoto2 = new JTextField();
				jvuoto2.setEditable(false);
				JTextField jvuoto3 = new JTextField();
				jvuoto3.setEditable(false);
				JTextField jvuoto4 = new JTextField();
				jvuoto4.setEditable(false);
				
				
				/*
				panModifica.add(jlogin);
				panModifica.add(jpsw);
				panModifica.add(jvuoto);
				panModifica.add(jnome);
				panModifica.add(jcognome);
				panModifica.add(jvuoto1);
				panModifica.add(jgiornonascita);
				panModifica.add(jmesenascita);
				panModifica.add(jannonascita);
				panModifica.add(jemail);
				panModifica.add(jvuoto2);
				panModifica.add(jvuoto3);
				panModifica.add(jvuoto4);
				panModifica.add(jmodifica);
				//panModifica.setBackground(Color.YELLOW);
				frModifica.add(panModifica);
		
				frModifica.setLocation(980, 150);
				frModifica.setSize(430,200);
				frModifica.setVisible(true);
				*/
				
				jmodifica.addActionListener(new ActionListener(){ 
					public void actionPerformed(ActionEvent e){
						
						char[] p = jpsw.getPassword();
						String passwordInserita = new String(p);
						String data = jannonascita.getText() + "-" + jmesenascita.getText() + "-" + jgiornonascita.getText();
						updateModProfilo(jlogin.getText(), passwordInserita, jnome.getText(), jcognome.getText(), data, jemail.getText());
						
						//frModifica.setVisible(false);
			        }
			    });
			} //while
		}catch (SQLException e1) {
			System.out.println("Errore nella ricerca dell'utente per la modifica dei propri dati: ");
			e1.printStackTrace();
		}
	}//fine metodo profilo
	
	
	
	void updateModProfilo(String log, String passw, String nome, String cognome, String data_nascita, String email) {
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
        	stmt.executeUpdate("update UTENTEREGISTRATO"
        			+ " set PASSW='"+passw+"', NOME='"+nome+"', COGNOME='"+cognome
        			+ "', DATA_NASCITA='"+ data_nascita +"', EMAIL='"+email+"'"
        			+ "  where LOG='" + log + "';");
			
		} catch (SQLException e1) {
			System.out.println("Errore aggiornamento dati personali dell'utente: ");
			e1.printStackTrace();
		}
		
		modificaProfilo(log);
	}

}
