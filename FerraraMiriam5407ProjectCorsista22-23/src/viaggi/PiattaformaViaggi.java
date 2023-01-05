package viaggi;

import java.util.*;
import java.sql.*;
import java.sql.Date;
import java.time.Year;

public class PiattaformaViaggi {
private static Connection con;
	
	public PiattaformaViaggi() {
	try{	//Caricamento del driver
		Class.forName("com.mysql.cj.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/piattaformaViaggi"
				   + "?useUnicode=true&useJDBCCompliantTimezoneShift=true"
				   + "&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String username = "root"; String pwd = "12345678";
		//Apertura della connessione
		con = DriverManager.getConnection(url, username, pwd);
		System.out.println("Connessione Accettata.\nCARICAMENTO IN CORSO...");
		}//fine try 
	catch (Exception e){ 
		System.out.println("Connessione Fallita"); 
		}//fine catch
			}//fine costruttore 

	public void closeConnessione() throws SQLException{
		con.close(); 
		}

	
	//[1]Registrazione di un cliente
	public void registraCliente1(){
		Scanner in = new Scanner(System.in);
			System.out.println("\nRegistrazione di un cliente.");
			System.out.println("Inserisci tutti i campi: ");
			//codice_fiscale, nome, cognome, via, cap, citta, data_nascita
			String datiAnagrafici = "INSERT INTO dati_Anagrafici VALUE (?, ?, ?, ?, ?, ?, ?);";
			//id_Tessera, periodo_Validita, premium, sconto, data_Scadenza
			String tesseraP = "INSERT INTO tessera(periodo_Validita, premium, data_Scadenza) VALUE (?, ?, ?);";
			//num_Prenotazioni, email, num_Telefono, code_Fiscale, code_Tessera
			String cliente = "INSERT INTO cliente(num_Prenotazioni, email, num_Telefono, code_Fiscale, code_Tessera) VALUE (?, ?, ?, ?, ?);";
			String cercaidTessera = "SELECT MAX(id_Tessera) FROM tessera;";
			
		try {
				//Esecuzione dell'interrogazione
				Statement query1 = con.createStatement();

				PreparedStatement ps = con.prepareStatement(datiAnagrafici);
				PreparedStatement ps1 = con.prepareStatement(tesseraP);
				PreparedStatement ps2 = con.prepareStatement(cercaidTessera);
				PreparedStatement ps3 = con.prepareStatement(cliente);
				
				ResultSet risultato = query1.executeQuery(cercaidTessera);
				
				
				System.out.println("CODICE FISCALE: ");
				String codice_fiscale = in.nextLine();
				ps.setString(1, codice_fiscale);
				
				System.out.println("NOME: ");
				String nome = in.nextLine();
				ps.setString(2, nome);
				
				System.out.println("COGNOME: ");
				String cognome = in.nextLine();
				ps.setString(3, cognome);

				System.out.println("VIA: ");
				String via = in.nextLine();
				ps.setString(4, via);
				System.out.println("CAP: ");	
				String cap = in.nextLine();
				ps.setString(5, cap);
				System.out.println("CITTA: ");
				String citta = in.nextLine();
				ps.setString(6,citta);
				
				
				System.out.println("DATA DI NASCITA (yyyy-mm-dd): ");
				String date = in.nextLine();
				Date data_nascita = Date.valueOf(date);
				ps.setDate(7, data_nascita);
			    
				    
				int num_Prenotazioni= 0;
				ps3.setInt(1, num_Prenotazioni);
				System.out.println("EMAIL: ");
				String  email = in.nextLine();
				ps3.setString(2, email);
				
				System.out.println("NUMERO TELEFONO: ");
				String  num_Telefono = in.nextLine();
				ps3.setString(3, num_Telefono);
				
				ps3.setString(4, codice_fiscale);
				
				System.out.println("PERIODO VALIDITA TESSERA (yyyy-mm-dd): ");
				String periodo= in.nextLine();  
				Date periodo_Validita =  Date.valueOf(periodo);
				ps1.setDate(1, periodo_Validita);
				
				String premium= "NO";  
				ps1.setString(2, premium);
				
				
				System.out.println("DATA SCADENZA TESSERA (yyyy-mm-dd): ");
				String scadenza= in.next();
				Date data_Scadenza = Date.valueOf(scadenza);
				ps1.setDate(3, data_Scadenza);
		
				
				while(risultato.next()){
					int idT = risultato.getInt(1);
					ps3.setInt(5, idT+1);
				}
		
				ps.execute();
				ps1.execute();
				ps2.execute();
				ps3.execute();
				
				
				System.out.println("\nCliente REGISTRATO");
				Statement query2 = con.createStatement();
				ResultSet risultato1 = query2.executeQuery("SELECT * FROM dati_Anagrafici;");
				System.out.println("\ncodice_fiscale\tnome\tcognome\tvia\tcap\tcitta\tdata_nascita");
				while(risultato1.next()){
					System.out.println(risultato1.getString(1) + "\t" + risultato1.getString(2) + "\t" + risultato1.getString(3) + "\t" + risultato1.getString(4) + "\t" + risultato1.getString(5) + "\t" + risultato1.getString(6) + "\t\t" + risultato1.getDate(7));
					}/*
				Statement query3 = con.createStatement();
				ResultSet risultato2 = query3.executeQuery("SELECT * FROM dati_Anagrafici;");
				System.out.println("\nid_Cliente\tnum_Prenotazioni\temail\tnum_Telefono\tcode_Fiscale\tcode_Tessera\tcode_Prenotazione");
				while(risultato2.next()){
					System.out.println(risultato2.getInt(1)+ "\t" + risultato2.getString(2) + "\t" + risultato2.getString(3) + "\t" + risultato2.getString(4) + "\t" + risultato2.getInt(5));
					}
				//inserisci la tessera
				
				System.out.println("\nQuery Utilizzate per l'inserimento di un nuovo cliente:"
						+ "\nINSERT INTO dati_Anagrafici VALUE (?, ?, ?, ?, ?, ?, ?)"
						+ "\nINSERT INTO cliente VALUE (?, ?, ?, ?, ?);\n");
			*/
		}
				
			catch (Exception e){
				System.out.println("ERRORE nell'interrogazione: " + e.getMessage());
				e.printStackTrace();
				} //catch		
			}//fine metodo registraCliente
	
	
	//[2]Registrazione di una struttura ricettiva
	public void registraStrutturaRicettiva2(){
		//aggiungi i punti dinteresse
		
	Scanner in = new Scanner(System.in);
	System.out.println("\nRegistrazione di una struttura ricettiva.");
	System.out.println("Inserisci tutti i campi: ");
	
	String inserisciSRicettiva = "INSERT INTO struttura_Ricettiva VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	
	
try {
	Statement query2 = con.createStatement();

	PreparedStatement ps = con.prepareStatement(inserisciSRicettiva);
		
	System.out.println("Nome Struttura: ");
    String nomeStruttura = in.nextLine();
    ps.setString(1, nomeStruttura);

    System.out.println("Descrizione: ");
    String descrizione = in.nextLine();
    ps.setString(2, descrizione);

    System.out.println("Indirizzo: ");
    String indirizzo = in.nextLine();
    ps.setString(3, indirizzo);

    System.out.println("Numero Telefono: ");
    String telefono = in.nextLine();
    ps.setString(4, telefono);

    System.out.println("Anno di iscrizione della struttura: ");
    String anno = in.nextLine();
	Year annoIscrizione = Year.parse(anno);
	ps.setInt(5, annoIscrizione.getValue());
	
    System.out.println("Tipologia Struttura: ");
    String tipologia = in.nextLine();
    ps.setString(8, tipologia);
	
    if(!(tipologia.equalsIgnoreCase("hotel")) && !(tipologia.equalsIgnoreCase("appartamento")) && !(tipologia.equalsIgnoreCase("ostello"))){
    	System.out.println("\nMi dispiace ma la tipologia di struttura non presente");
    	System.exit(0);
    }
    if(tipologia.equalsIgnoreCase("hotel")){
    
    System.out.println("Numero massimo di ospiti della struttura: ");
    int numeroOspiti = in.nextInt();
    ps.setInt(6, numeroOspiti);
  

    System.out.println("Tipologia di stanze del Hotel: ");
    String stanza = in.next();
    ps.setString(7, stanza);
    }else{
    	 ps.setInt(6, 0);
    	 ps.setString(7, null);
    }
    
    System.out.println("Prezzo: ");
    Float prezzo = in.nextFloat();
    ps.setFloat(9, prezzo);
    
    if(tipologia.equalsIgnoreCase("appartamento")){
    	
    System.out.println("Vani: ");
    int vani = in.nextInt();
    ps.setInt(10, vani);
	
    System.out.println("metri_Quadri: ");
    String metri_Quadri = in.next();
    ps.setString(11, metri_Quadri);
    }else{
   	 ps.setInt(10, 0);
   	 ps.setString(11, null);
   }
  
    
    ps.execute();
		System.out.println("\nStruttura Ricettiva REGISTRATO");
		
	
	}catch (Exception e){
		System.out.println("ERRORE nell'interrogazione: " + e.getMessage());
		e.printStackTrace();
		} //catch		
	}//fine metodo registraStrutturaRicettiva
	
	
	//[3]Prenotazione di una struttura ricettiva da parte di un cliente
	public void prenotazioneStrutturaRicettiva3(){
		try{
			Scanner in = new Scanner(System.in);
			System.out.println("\nPrenotazione di una struttura ricettiva da parte di un cliente.");
			
			String cercaTuttiClienti = "SELECT id_Cliente, code_Fiscale FROM cliente;";
		
			//Esecuzione dell'interrogazione
			Statement query3 = con.createStatement();
			ResultSet risultato = query3.executeQuery(cercaTuttiClienti);
			
		
			System.out.println("\nLista Clienti: ");
			System.out.println("\nid_Cliente\tcode_Fiscale\t");
			while(risultato.next()){
			System.out.println(risultato.getInt(1)+ "\t\t" + risultato.getString(2));
			}
			
			
			System.out.println("\nInserisci l'id del cliente: ");
			int id_Cliente = in.nextInt(); 
			
			String cercaPrenotazione = "INSERT INTO prenotazione (id_Cliente, note, data_Check_in, data_Check_out, prezzo_Totale, pagamento) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(cercaPrenotazione);
			
			ps.setInt(1, id_Cliente);
			
			System.out.println("Note: ");
			in.nextLine();
		    String note = in.nextLine();
		    
		    ps.setString(2, note);
		    
		    System.out.println("Data Check-in: ");
		    String data_in = in.next();
		    Date data_Check_in = Date.valueOf(data_in);
		    ps.setDate(3, data_Check_in);
		    
		    System.out.println("Data Check-out: ");
		    String data_out = in.next();
		    Date data_Check_out = Date.valueOf(data_out);
		    ps.setDate(4, data_Check_out);

		    System.out.println("Pagamento: ");
		    String pagamento = in.next();
		    ps.setString(5, pagamento);
		    
		    
		    //nome_Struttura, descrizione, indirizzo, telefono, anno_Iscrizione, numero_ospiti, stanza, tipologia, prezzo, vani, metri_Quadri
		    String cercaTuttiSRicettive = "SELECT * FROM struttura_Ricettiva;";
			
		    ResultSet risultato1 = query3.executeQuery(cercaTuttiSRicettive);
		    String cercaPrezzoSRicettive = "SELECT prezzo_Totale FROM struttura_Ricettiva;";
		    ResultSet risultato2 = query3.executeQuery(cercaPrezzoSRicettive);
			
		    //calcolato 
		    String tipologia = risultato1.getString("tipologia");
		    int numOspiti = risultato1.getInt("numero_ospiti");
		    String stanza = risultato1.getString("stanza");
		    float prezzoS  = risultato1.getFloat("prezzo");
		    float prezzoTotale = risultato2.getFloat("prezzo_Totale");
		    
		    if(!(tipologia.equalsIgnoreCase("Hotel")) && !(tipologia.equalsIgnoreCase("Appartamento")) && !(tipologia.equalsIgnoreCase("Ostello"))){
		    	System.out.println("\nMi dispiace ma la tipologia di struttura non presente");
		    	System.exit(0);
		    }
		    
		    else if((tipologia.equalsIgnoreCase("Appartamento")) && (tipologia.equalsIgnoreCase("Ostello"))){
		    	
		    	if(tipologia.equalsIgnoreCase("Hotel")){
		    	if(stanza.equalsIgnoreCase("Camera Singola")){
		    		float numO = Float.valueOf(numOspiti);
		    		prezzoTotale = prezzoS*numOspiti;
			    }	
		    	if(stanza.equalsIgnoreCase("Camera Matrimoniale")){
		    		float numO = Float.valueOf(numOspiti);
				    prezzoTotale = (prezzoS*numOspiti)+20;
			    }
		    	if(stanza.equalsIgnoreCase("Camera Quadrupla")){
		    		float numO = Float.valueOf(numOspiti);
				    prezzoTotale = (prezzoS*numOspiti)+40;
				    
			    }
		    	ps.setFloat(5, prezzoTotale);
		     }	
	    }
		   
		    System.out.println("Prezzo Totale: "  + prezzoTotale);
			
		    System.out.println("\nLista Strutture Ricettive: ");
			System.out.println("\nnome_Struttura\t\tdescrizione\t\tindirizzo\t\ttelefono\t\tanno_Iscrizione\t\tnumero_ospiti"
					+ "\t\tstanza\t\ttipologia\t\tprezzo\t\tvani\t\tmetri_Quadri");
			
			while(risultato1.next()){
			System.out.println(risultato1.getString(1)+ "\t\t" + risultato1.getString(2) + "\t\t" + risultato1.getString(3) + "\t\t" + risultato1.getString(4)
			+ "\t\t" + risultato1.getInt(5) + "\t\t" + risultato1.getInt(6) + "\t\t" + risultato1.getString(7) + "\t\t" + risultato1.getString(8) + "\t\t" + risultato1.getFloat(9)
			+ "\t\t" + risultato1.getInt(10) + "\t\t" + risultato1.getString(11));
			}
			
			System.out.println("\nInserisci la struttara Ricettiva che vuoi prenotare: ");
			String nomeSR = "";	
			in.nextLine();
			nomeSR = in.nextLine();
		
			
			String cercaidPrenotazione = "SELECT MAX(id_Prenotazione) FROM prenotazione;";
			ResultSet risultato3 = query3.executeQuery(cercaidPrenotazione);
			
			int idP = 0;
			while(risultato3.next()){
				idP = risultato3.getInt(1)+1;
			}
			
			 
			 String inserimentoInteressare = "INSERT INTO interessare (id_Prenotazione_interessare, id_Struttura_interessare) VALUES (?, ?)";
			 PreparedStatement ps1 = con.prepareStatement(inserimentoInteressare);
			 ps1.setInt(1, idP);
			 ps1.setString(2, nomeSR);
			 
			 int risultato4 =  query3.executeUpdate("UPDATE cliente SET num_Prenotazioni = num_Prenotazioni+1 WHERE id_Cliente = "+id_Cliente+";");	
	        	System.out.println("Operazione eseguita con SUCCESSO");
	        	
	        	ps.execute();
	        	ps1.execute();
		}
		
		catch (Exception e){
			System.out.println("ERRORE nell'interrogazione: " + e.getMessage());
			e.printStackTrace();
			} //catch		
		}//fine metodo prenotazioneStrutturaRicettiva
	
	//[4]Attribuzione ad un cliente di una tessera con fidelizzazione premium
	public void attribuzioneTesseraPremiumCliente4(){
		try{
		
		Scanner in = new Scanner(System.in);
		System.out.println("\nAttribuzione ad un cliente di una tessera con fidelizzazione premium.");
		
		String cercaTuttiClienti = "SELECT DISTINCT cliente.id_Cliente, cliente.code_Fiscale, cliente.code_Tessera, tessera.premium, tessera.sconto FROM cliente "
								+ "INNER JOIN tessera ON cliente.code_Tessera = tessera.id_Tessera WHERE tessera.premium = 'NO';";
	
		//Esecuzione dell'interrogazione
		Statement query4 = con.createStatement();
		ResultSet risultato = query4.executeQuery(cercaTuttiClienti);
		
		
		if(risultato.isBeforeFirst()){
		System.out.println("\nLista Clienti: ");
		System.out.println("\nid_Cliente\tcode_Fiscale\t	code_Tessera\t	Premium\t	Sconto");
		while(risultato.next()){
		System.out.println(risultato.getInt(1)+ "	\t" + risultato.getString(2) + "	\t" + risultato.getInt(3)+ "	\t" + risultato.getString(4)+ "	\t" + risultato.getFloat(5));
		}
		
		
		System.out.println("\nInserisci l'id del cliente: ");
		int id_Cliente = in.nextInt(); 
		String verificaPremium ="SELECT * FROM cliente INNER JOIN tessera ON cliente.code_Tessera = tessera.id_Tessera WHERE cliente.id_Cliente = '"+id_Cliente+"';";
		
		ResultSet rs = query4.executeQuery(verificaPremium);

	      //Estrazione dei dati dal risultato
	      if (rs.next()) {
	        // Verifica se la tessera ha l'attributo premium impostato a "no"
	        if (rs.getString("premium").equals("NO")) {
	        	System.out.println("Inserisci sconto: ");
	        	Float sconto= in.nextFloat();
	        	int aggiornaTessera = query4.executeUpdate("UPDATE tessera SET premium = 'SI', sconto = '"+sconto+"' WHERE id_Tessera = (SELECT code_Tessera FROM cliente WHERE id_Cliente = '"+id_Cliente+"');");	
	        	System.out.println("Operazione eseguita con SUCCESSO");
	        } else {
	       System.out.println("Ci dispiace controlla se l'id_cliente è corretto o se la tessera è già premium.");
	        } 
	      }  
		}else {
		       System.out.println("\nTutti i clienti sono in possesso della tessera premium");
		       }
		  
		}catch (Exception e){
			System.out.println("ERRORE nell'interrogazione: " + e.getMessage());
			e.printStackTrace();
			} //catch		
		}//fine metodo attribuzioneTesseraPremiumCliente

	
	//[5]Visualizzazione di tutte le strutture ricettive per città
	public void VisualizzazioneTutteSRicettivePerCitta5(){
		try{
			
			Scanner in = new Scanner(System.in);
			System.out.println("\nVisualizzazione di tutte le strutture ricettive per città.");
			Statement query5 = con.createStatement();
			
			String cercaTutteSRicettive = "SELECT sr.nome_Struttura, sr.descrizione, sr.indirizzo, sr.telefono, sr.anno_Iscrizione, sr.numero_ospiti, sr.stanza, sr.tipologia, sr.prezzo, sr.vani, sr.metri_Quadri "
				    + "FROM struttura_Ricettiva sr "
				    + "INNER JOIN punti_Interesse pi ON sr.nome_Struttura = pi.nome "
				    + "WHERE pi.citta = ?";
			
			
			PreparedStatement ps = con.prepareStatement(cercaTutteSRicettive);

			System.out.println("Inserisci la citta: ");
			String citta = in.nextLine();
			ps.setString(1, citta);

			ps.execute();

			ResultSet risultato = ps.executeQuery();
			
			System.out.println("\nLista Strutture Ricettive con "+citta+": ");
			System.out.println("\nnome_Struttura\tdescrizione\t\tindirizzo\t\t\ttelefono\t\tanno_Iscrizione\t\tnumero_ospiti"
			    + "\t\tstanza\t\ttipologia\t\tprezzo\t\tvani\t\tmetri_Quadri");
			while(risultato.next()){
			    System.out.println(risultato.getString(1)+ "\t" + risultato.getString(2) + "\t\t\t" + risultato.getString(3)+ "\t\t" + risultato.getString(4)+
			        "\t\t" + risultato.getInt(5) +"\t\t" + risultato.getInt(6) +"\t\t" + risultato.getString(7) +"\t\t" + risultato.getString(8) +
					"	\t" + risultato.getFloat(9) +"	\t" +  risultato.getInt(10) +"	\t" + risultato.getString(11));
			}
		    
			}catch (Exception e){
				System.out.println("ERRORE nell'interrogazione: " + e.getMessage());
				e.printStackTrace();
				} //catch		
			}//fine metodo VisualizzazioneTutteSRicettivePerCitta

	
	
	//[6]Visualizzazione di tutte le strutture ricettive disponibili in un periodo di tempo specificato
	public void VisualizzazioneTutteSRicettivePerPeriodo6(){
		System.out.println("\nVisualizzazione di tutte le strutture ricettive disponibili in un periodo di tempo specificato");
		try{
		Statement query5 = con.createStatement();
		
		
		}catch (Exception e){
			System.out.println("ERRORE nell'interrogazione: " + e.getMessage());
			e.printStackTrace();
			} //catch		
		}//fine metodo VisualizzazioneTutteSRicettivePerPeriodo
	
	
	//[7]Visualizzazione di tutte le strutture ricettive disponibili in un periodo di tempo specificato il cui prezzo a notte non superi i 50€
	public void VisualizzazioneTutteSRicettivePerPezzo7(){
		
	}
	
	
	//[8]Visualizzazione del numero di prenotazioni effettuate da tutti i clienti nell’ultimo mese
	public void VisualizzazionePrenotazioniMese8(){
		
	}
	
	
	//[9]Visualizzazione dei migliori 10 clienti premium che abbiano effettuato il maggior numero di prenotazioni nelle diverse strutture ricettive
	public void VisualizzazioneMiglioCliente9(){
		
	}
	
	
	//[10]Visualizzazione degli ostelli per i quali non è stato mai registrata una prenotazione di più di 7 giorni
	public void VisualizzazioneOstelli10(){
		
	}
	
	
	//[11]Visualizzazione delle strutture ricettive che hanno una distanza di 10km specifica da un punto di interesse
	public void VisualizzazioneSRConDistanza11(){
		
	}
	
	
	//[12]Visualizzazione della somma degli incassi ottenuti dalle strutture ricettive registrate sulla piattaforma
	public void VisualizzazioneIncassi12(){
		
	}
	
	
	//[13]Stampa dei dati dei clienti che hanno prenotato solo appartamenti e ostelli
	public void StampaPrenotazioniOstelliAppartamenti13(){
		
	}
	
	
	//[14]Stampa di un report che mostri i dati delle agenzie di viaggio compreso il numero totale di prenotazioni effettuate
	public void StampaDatiAgenziaViaggio14(){
		
	}
	
	
	//[15]Stampa di un report che mostri i dati delle strutture ricettive per una specifica città e che hanno ricevuto meno di 3 prenotazioni
	public void StampaDatiSRicettiva15(){
		
	}
	
	
	//[16]Stampa di un report che mostri i dati delle prenotazioni che ancora non sono state effettuate ed il costo di ognuna di esse
	public void StampaDatiPrenotazioni16(){
	
	}

	public void opMenu() throws SQLException{
			//Menu Operazioni
			int scelta = 0;
			while(scelta != 16){
			System.out.println("\n<<Menu Operazioni>>");
			System.out.println("[1]Registrazione di un cliente.");
			System.out.println("[2]Registrazione di una struttura ricettiva.");
			System.out.println("[3]Prenotazione di una struttura ricettiva da parte di un cliente.");
			System.out.println("[4]Attribuzione ad un cliente di una tessera con fidelizzazione premium.");
			System.out.println("[5]Visualizzazione di tutte le strutture ricettive per città.");
			System.out.println("[6]Visualizzazione di tutte le strutture ricettive disponibili in un periodo di tempo specificato.");
			System.out.println("[7]Visualizzazione di tutte le strutture ricettive disponibili in un periodo di tempo specificato il cui prezzo a notte non superi i 50€.");
			System.out.println("[8]Visualizzazione del numero di prenotazioni effettuate da tutti i clienti nell’ultimo mese.");
			System.out.println("[9]Visualizzazione dei migliori 10 clienti premium che abbiano effettuato il maggior numero di prenotazioni nelle diverse strutture ricettive.");
			System.out.println("[10]Visualizzazione degli ostelli per i quali non è stato mai registrata una prenotazione di più di 7 giorni.");
			System.out.println("[11]Visualizzazione delle strutture ricettive che hanno una distanza di 10km specifica da un punto di interesse.");
			System.out.println("[12]Visualizzazione della somma degli incassi ottenuti dalle strutture ricettive registrate sulla piattaforma.");
			System.out.println("[13]Stampa dei dati dei clienti che hanno prenotato solo appartamenti e ostelli.");
			System.out.println("[14]Stampa di un report che mostri i dati delle agenzie di viaggio compreso il numero totale di prenotazioni effettuate.");
			System.out.println("[15]Stampa di un report che mostri i dati delle strutture ricettive per una specifica città e che hanno ricevuto meno di 3 prenotazioni.");
			System.out.println("[16]Stampa di un report che mostri i dati delle prenotazioni che ancora non sono state effettuate ed il costo di ognuna di esse. ");
			System.out.println("[17]Esci dal Menu delle Operazioni. Termina Connessione.");
			System.out.println("SCEGLI il numero dell'operazione desiderata: ");
			Scanner in = new Scanner(System.in);
			scelta = in.nextInt();
			
			
			switch (scelta) {
			case 1:{  System.out.println("Hai selezionato l'operazione-> " + "[1]Registrazione di un cliente.");this.registraCliente1(); break;}
			case 2:{  System.out.println("Hai selezionato l'operazione-> " + "[2]Registrazione di una struttura ricettiva."); this.registraStrutturaRicettiva2(); break;}
			case 3:{  System.out.println("Hai selezionato l'operazione-> " + "[3]Prenotazione di una struttura ricettiva da parte di un cliente."); this.prenotazioneStrutturaRicettiva3(); break;}
			case 4:{  System.out.println("Hai selezionato l'operazione-> " + "[4]Attribuzione ad un cliente di una tessera con fidelizzazione premium."); this.attribuzioneTesseraPremiumCliente4(); break;}
			case 5:{  System.out.println("Hai selezionato l'operazione-> " + "[5]Visualizzazione di tutte le strutture ricettive per città."); this.VisualizzazioneTutteSRicettivePerCitta5(); break;}
			case 6:{  System.out.println("Hai selezionato l'operazione-> " + "[6]Visualizzazione di tutte le strutture ricettive disponibili in un periodo di tempo specificato."); this.VisualizzazioneTutteSRicettivePerPeriodo6(); break;}
			case 7:{  System.out.println("Hai selezionato l'operazione-> " + "[7]Visualizzazione di tutte le strutture ricettive disponibili in un periodo di tempo specificato il cui prezzo a notte non superi i 50€."); this.VisualizzazioneTutteSRicettivePerPezzo7(); break;}
			case 8:{  System.out.println("Hai selezionato l'operazione-> " + "[8]Visualizzazione del numero di prenotazioni effettuate da tutti i clienti nell’ultimo mese."); this.VisualizzazionePrenotazioniMese8(); break;}
			case 9:{  System.out.println("Hai selezionato l'operazione-> " + "[9]Visualizzazione dei migliori 10 clienti premium che abbiano effettuato il maggior numero di prenotazioni nelle diverse strutture ricettive."); this.VisualizzazioneMiglioCliente9(); break;}
			case 10:{ System.out.println("Hai selezionato l'operazione-> " + "[10]Visualizzazione degli ostelli per i quali non è stato mai registrata una prenotazione di più di 7 giorni."); this.VisualizzazioneOstelli10(); break;}
			case 11:{ System.out.println("Hai selezionato l'operazione-> " + "[11]Visualizzazione delle strutture ricettive che hanno una distanza di 10km specifica da un punto di interesse."); this.VisualizzazioneSRConDistanza11(); break;}
			case 12:{ System.out.println("Hai selezionato l'operazione-> " + "[12]Visualizzazione della somma degli incassi ottenuti dalle strutture ricettive registrate sulla piattaforma."); this.VisualizzazioneIncassi12(); break;}
			case 13:{ System.out.println("Hai selezionato l'operazione-> " + "[13]Stampa dei dati dei clienti che hanno prenotato solo appartamenti e ostelli."); this.StampaPrenotazioniOstelliAppartamenti13(); break;}
			case 14:{ System.out.println("Hai selezionato l'operazione-> " + "[14]Stampa di un report che mostri i dati delle agenzie di viaggio compreso il numero totale di prenotazioni effettuate."); this.StampaDatiAgenziaViaggio14(); break;}
			case 15:{ System.out.println("Hai selezionato l'operazione-> " + "[15]Stampa di un report che mostri i dati delle strutture ricettive per una specifica città e che hanno ricevuto meno di 3 prenotazioni.");this.StampaDatiSRicettiva15(); break;}
			case 16:{ System.out.println("Hai selezionato l'operazione-> " + "[16]Stampa di un report che mostri i dati delle prenotazioni che ancora non sono state effettuate ed il costo di ognuna di esse."); this.StampaDatiPrenotazioni16(); break;}
			case 17:{ System.out.println("Hai selezionato l'operazione-> " + "[17]Menu delle Operazioni Terminato.\nConnessione Terminata-->"); this.closeConnessione(); break;}
			default:{ System.out.println("\nERRORE: La scelta dell'operazione " + scelta + " selezionata non è presente. RIPROVA..." ); break; }
				}// fine switch		
			} //fine while
		}//fine OpMenu
}
/*[1]Registrazione di un cliente
[2]Registrazione di una struttura ricettiva
[3]Prenotazione di una struttura ricettiva da parte di un cliente
[4]Attribuzione ad un cliente di una tessera con fidelizzazione premium
[5]Visualizzazione di tutte le strutture ricettive per città
[6]Visualizzazione di tutte le strutture ricettive disponibili in un periodo di tempo specificato
[7]Visualizzazione di tutte le strutture ricettive disponibili in un periodo di tempo specificato il cui prezzo a notte non superi i 50€
[8]Visualizzazione del numero di prenotazioni effettuate da tutti i clienti nell’ultimo mese
[9]Visualizzazione dei migliori 10 clienti premium che abbiano effettuato il maggior numero di prenotazioni nelle diverse strutture ricettive
[10]Visualizzazione degli ostelli per i quali non è stato mai registrata una prenotazione di più di 7 giorni
[11]Visualizzazione delle strutture ricettive che hanno una distanza di 10km specifica da un punto di interesse
[12]Visualizzazione della somma degli incassi ottenuti dalle strutture ricettive registrate sulla piattaforma
[13]Stampa dei dati dei clienti che hanno prenotato solo appartamenti e ostelli
[14]Stampa di un report che mostri i dati delle agenzie di viaggio compreso il numero totale di prenotazioni effettuate
[15]Stampa di un report che mostri i dati delle strutture ricettive per una specifica città e che hanno ricevuto meno di 3 prenotazioni
[16]Stampa di un report che mostri i dati delle prenotazioni che ancora non sono state effettuate ed il costo di ognuna di esse*/
