package it.isa.progetto;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;

public class PannelloLogin {

	static String url = "jdbc:db2://172.17.0.3:50000/SAMPLE:retrieveMessagesFromServerOnGetMessage=true;";
	static Connection con;
	static Statement stmt, stmtcheck;
	
	/*
	private JFrame frTabLogin, frTabConferma;
	private JPanel jpTabLogin;
	private JLabel jlRisultato;*/
	private JTextField user;
	private JPasswordField passw;
	
	
	public void show() {
		/******** PARTE CON INTERF. GRAFICA **********/
		//Creo un JFrame per login
		/*
		frTabLogin = new JFrame("Autenticazione");
		frTabLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frTabLogin.setResizable(false);
		*/
		/*************** CREO RIGA PER INSERIMENTO ******************/
		/*
		jpTabLogin = new JPanel();
		jpTabLogin.setAlignmentX((float) 0.6);
		*/
		
		//campi
		user = new JTextField("username",40);
		user.setHorizontalAlignment(JTextField.CENTER);
		passw = new JPasswordField("password",40);
		passw.setHorizontalAlignment(JTextField.CENTER);
		JButton jbinvia = new JButton("ACCEDI");
		jbinvia.setHorizontalAlignment(JTextField.CENTER);
		
		
        
		/*
		jpTabLogin.add(user);
		jpTabLogin.add(passw);
		jpTabLogin.add(jbinvia);
		
		jpTabLogin.setBackground(Color.YELLOW);
		frTabLogin.add(jpTabLogin);
		frTabLogin.setLocation(250, 190);
		frTabLogin.setSize(350,300);
		frTabLogin.setVisible(true);
		*/
		jbinvia.addActionListener(new ActionListener(){  
			public void actionPerformed(ActionEvent e){
				
				 /*Fai il check login se almeno l'utente ha scritto l'username
				 * In altre parole: attiva tutto se hai scritto almeno la passw
				 * */
				//System.out.println("Cosa ricevo: " + user.getText().trim()+"\\");
				
					//frTabLogin.setVisible(false);
					/* SPOSTATO IN CHECK-LOGIN
					//Creo un JFrame per Swing di conferma
					frTabConferma = new JFrame("Conferma");
					frTabConferma.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					//frTabConferma.setResizable(false);
					
					jlRisultato = new JLabel();
					jlRisultato.setOpaque(true);		//questi due assieme, senno non colorano lo sfondo
					jlRisultato.setBackground(Color.BLACK);//e questo
					
					
					jlRisultato.setForeground(Color.ORANGE);
					jlRisultato.setHorizontalAlignment((int)0.6);
					

					frTabConferma.add(jlRisultato);
					frTabConferma.setLocation(700, 460);
					frTabConferma.setSize(400,170);
					frTabConferma.setVisible(true);
					*/
					
					checkLogin(user, passw);
					
					//import javax.swing.Timer; CHIUDO FINESTRA DOPO 3 SECONDI
					/*spostato in checkLogin
					 Timer timer = new Timer(3000, new ActionListener(){
					    public void actionPerformed(ActionEvent evt) {
					    	frTabConferma.dispose();
					    }
					});
					timer.setRepeats(false);
					timer.start();
					*/
					
				//}//IF
				
	        }  
	    });
		
	}//show
	
	
	

	public void checkLogin(JTextField userN, JPasswordField passw) {

		String nomeAutenticato = userN.getText();
//		System.out.println("USER CONVERTITO DA JTEXTFIELD: " + nomeAutenticato);
		
		char[] p = passw.getPassword();
		String passwordInserita = new String(p);
//		System.out.println("PASSWORD CONVERTITA DA JTEXTFIELD: " + passwordInserita);
		
		//Creo un JFrame per Swing di conferma
		/*
		frTabConferma = new JFrame("Conferma");
		frTabConferma.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		*/
		//frTabConferma.setResizable(false);
		
		/*
		jlRisultato = new JLabel();
		jlRisultato.setOpaque(true);		//questi due assieme, senno non colorano lo sfondo
		jlRisultato.setBackground(Color.BLACK);//e questo
		
		
		jlRisultato.setForeground(Color.ORANGE);
		jlRisultato.setHorizontalAlignment((int)0.6);
		

		frTabConferma.add(jlRisultato);
		frTabConferma.setLocation(700, 460);
		frTabConferma.setSize(400,170);
		frTabConferma.setVisible(true);
		*/
		
		
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
        	
        	//cerco tra tutti gli utenti
        	ResultSet resultSetUser = stmt.executeQuery("select * from UTENTEREGISTRATO "
        			+ "where LOG='" + nomeAutenticato +"' "
        			+ "and PASSW='" + passwordInserita + "';");
        	
        		
	        	if(resultSetUser.next()) {
	        		String log = resultSetUser.getString("LOG").trim();
	        		String chiave = resultSetUser.getString("PASSW").trim();
	        		
	        		/*
	        		jlRisultato.setForeground(Color.GREEN);
					jlRisultato.setHorizontalAlignment((int)0.6);
	        		jlRisultato.setText("<html><table><td><tr>User: " + log + 
	        				" </tr><tr>Password: " + chiave + "</tr>" +
	        				"</tr><tr>Autorizzazione: Risulti un utente registrato !!</tr></td></table></html>");
	        		*/
	        		GestioneUtente gu = new GestioneUtente();
	        		gu.esegui(log, chiave);
	        	}
	        	
	        	else {
	        		//cerco tra tutti gli artisti
	        		ResultSet resultSetArtist = stmt.executeQuery("select * from ARTISTA "
	            			+ "where LOG='" + nomeAutenticato +"' "
	            			+ "and PASSW='" + passwordInserita + "';");
	        		
	        		if(resultSetArtist.next()) {
		        		String log = resultSetArtist.getString("LOG").trim();
		        		String chiave = resultSetArtist.getString("PASSW").trim();
		        		
		        		/*
		        		jlRisultato.setForeground(Color.GREEN);
						jlRisultato.setHorizontalAlignment((int)0.6);
		        		jlRisultato.setText("<html><table><td><tr>User: " + log + 
		        				" </tr><tr>Password: " + chiave + "</tr>" +
		        				"</tr><tr>Autorizzazione: Risulti un artista !!</tr></td></table></html>");
		        		*/
		        		
		        		GestioneArtista ga = new GestioneArtista();
		        		ga.esegui(log, chiave);
		        	}
	        		else {
	        			//jlRisultato.setText("<html>Non sei stato autenticato.<br>Devi riprovare</html>");
	        			App.main(null);
	        		}
	        			
	        	}
	        }catch (SQLException e1) {
			System.out.println("Errore nella ricerca utente sul DB: ");
			e1.printStackTrace();
		}
		
		
		
		/*
		Timer timer = new Timer(3000, new ActionListener(){
		    public void actionPerformed(ActionEvent evt) {
		    	frTabConferma.dispose();
		    }
		});
		timer.setRepeats(false);
		timer.start();*/
	}//checklogin

}