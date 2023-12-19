package sample;

import java.io.*;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Scanner;

//NOME BOT: Time Organizer
public class BotMN extends TelegramLongPollingBot {
    boolean trovato = false; //se = true vuol dire che l'utente è presente nel sistema
    boolean amministratore=false; // per memorizzare se l'utente è amministratore o no
    boolean attivitaTrovata = false;
    int attivita[] = {}; // per memorizzare id attività del workspace selezionato
    String stato = "wait";
    String pw="";
    String listaWorkspace="";
    String listaAttivita="";
    String listaPreferenze="";
    String workspaceSelezionato=""; // per memorizzare il workspace una volta selezionato
    String attivitaSelezionata=""; // per memorizzare l'attività una volta selezionata

    AdministratorController admin = new AdministratorController(); // per capire se bot è attivo o disattivo

    @Override
    public String getBotUsername() {
        return "MNZR_bot";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            //System.out.println("1 messaggio arrivato."); //per verificare che il bot funzioni e parta
            String msg = update.getMessage().getText();
            String user_first_name = update.getMessage().getChat().getFirstName();
            String user_last_name = update.getMessage().getChat().getLastName();
            String user_username = update.getMessage().getChat().getUserName();

            System.out.println(user_first_name + " " + user_last_name + " (" + user_username + ") says: " + msg); //per verificare se è partito il Bot
            String chatId = update.getMessage().getChatId().toString();
            SendMessage sendMessage = new SendMessage();

// --> bot ATTIVATO
            if (admin.attivato == true) {

                //LETTURA FILE JSON
                FileReader readerUtenti = null;
                FileReader readerWorkspace = null;
                FileReader readerAttivita = null;
                FileReader readerPreferenze=null;
                try {
                    readerUtenti = new FileReader("Utenti.json");
                    readerWorkspace = new FileReader("Workspace.json");
                    readerAttivita = new FileReader("Attivita.json");
                    readerPreferenze = new FileReader("Preferenze.json");
                } catch (FileNotFoundException e) {
                    System.out.println("Problemi con lettura file.");
                    e.printStackTrace();
                }
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Utente[] personFromJson = gson.fromJson(readerUtenti, Utente[].class);
                Workspace[] workspaceFromJson = gson.fromJson(readerWorkspace, Workspace[].class);
                Attivita[] attivitaFromJson = gson.fromJson(readerAttivita, Attivita[].class);
                Preferenze[] preferenzeFromJson = gson.fromJson(readerPreferenze, Preferenze[].class);


                // COMANDI BOT TELEGRAM
                if (msg.equals("/start")) { //avvia la conversazione con il bot e mostra l'elenco di comandi possibili per questo Bot
                    sendMessage.setText("Ciao @" + user_username + "! \uD83D\uDCBB\n\n" +
                            "Accedi al sistema invocando il comando /login:");

                } else if (msg.equals("/login")) {
                    attivitaSelezionata="";
                    workspaceSelezionato="";
                    for (int i = 0; i < personFromJson.length; i++) {
                        if (user_username.equals(personFromJson[i].telegramId)) {
                            if(personFromJson[i].tipo==true){ // L'AMMINISTRATORE NON PUO' USUFRUIRE DEL SERVIZIO
                                amministratore=true;
                                break;
                            } else { // utente normale
                                trovato = true;
                                pw = personFromJson[i].pw;
                                amministratore = false;
                                break;
                            }
                        }
                    }
                    if (trovato == false && amministratore==false) {
                        sendMessage.setText("Non sei registrato nel sistema ⛔\n\n" +
                                "Per usufruire del bot, crea un account utente normale effettuando la registrazione direttamente dall'applicativo.");
                        System.out.println("L'utente " + user_username + " non è registrato nel sistema.");
                    } else if(amministratore==true){
                        sendMessage.setText("Sei un amministratore ⛔\n\n"+
                                "Non potrai usufruire del bot.");
                    } else {
                        stato = "inserimento password";
                        sendMessage.setText("Sei un utente registrato nel nostro sistema.\n\nInserisci la password: \uD83D\uDD11");
                        System.out.println("Utente " + user_username + " presente nel sistema."); //per verifica
                    }

// --> VERIFICA UTENTE
                } else if (stato.equals("inserimento password")) {
                    if (msg.equals(pw)) {
                        stato = "workspace"; //utente ha effettuato l'accesso, il prossimo comando che può utilizzare è /workspace per selezionarne uno
                        System.out.println("Autenticazione riuscita \uD83D\uDD13"); //verifica
                        sendMessage.setText("Bentornato nel nostro servizio! \uD83D\uDDD3️ \n\n" +
                                "Invoca il comando /workspace per visualizzare l’elenco dei tuoi workspace e per selezionare l'attività su cui lavorare: ");

                    } else if (msg.equals("/workspace") || msg.equals("/leggipreferenze") || msg.equals("/aggiungipreferenze")) {
                        sendMessage.setText("Ancora non hai effettuato l'accesso \uD83D\uDD10\n\n" +
                                "Inserisci la password: \uD83D\uDD11");
                    } else {
                        sendMessage.setText("Password dell'utente " + user_username + " non valida ❌\n\n"
                                + "Inserisci la password corretta: ");
                        System.out.println("Password dell'utente " + user_username + " non valida.");
                    }

// --> WORKSPACE
                } else if (msg.equals("/workspace") && stato.equals("workspace")) { // per visualizzare elenco workspace
                    attivitaSelezionata="";
                    attivitaTrovata=false; //azzeriamo altrimenti se inserisco un'altra attività, il bot crede di averla già trovata
                    stato = "attivita";
                    listaWorkspace = "I tuoi workspace: \uD83D\uDCBC \uD83D\uDCDA\n\n";
                    for (int i = 0; i < workspaceFromJson.length; i++) {
                        //scorro lista workspace e uso una variabile d'appoggio per inserire i nomi dei workspace per poi stamparli
                        listaWorkspace = listaWorkspace + "   - " + workspaceFromJson[i].workspace + " \n";
                    }
                    listaWorkspace = listaWorkspace + "\nScrivi il workspace del quale vuoi visualizzare le attività:";
                    sendMessage.setText(listaWorkspace);
                    stato = "attivita";

                    if (msg.equals("/logout")) {
                        attivitaSelezionata="";
                        workspaceSelezionato="";
                        stato = "logout";
                        sendMessage.setText("Hai effettuato il log out da Time Organizer \uD83D\uDD12");
                    }

// --> ATTIVITA
                } else if (stato.equals("attivita")) { // per visualizzare elenco attività di un relativo workspace
                    listaAttivita = "Le tue attività per '" + msg + "': \uD83D\uDD0D\n\n";
                    boolean workspaceTrovato = false;

                    for (int i = 0; i < workspaceFromJson.length; i++) { //ciclo per verificare che il workspace sia presente nell'elenco
                        if (msg.equalsIgnoreCase(workspaceFromJson[i].workspace)) {
                            attivita = new int[workspaceFromJson[i].attivita.length];
                            workspaceTrovato = true; //il workspace inserito è tra l'elenco
                            workspaceSelezionato = workspaceFromJson[i].workspace;
                            for (int j = 0; j < workspaceFromJson[i].attivita.length; j++) {
                                attivita[j] = workspaceFromJson[i].attivita[j]; //memorizza gli id attivita che riguardano il workspace selezionato
                            }
                            break;
                        }
                    }

                    if (workspaceTrovato == true) {
                        for (int i = 0; i < attivita.length; i++) {
                            //scorro lista attivita e uso una variabile d'appoggio per inserire i nomi delle attività per poi stamparle
                            listaAttivita = listaAttivita + "   - " + attivitaFromJson[attivita[i]].attivita + " \n";
                        }
                        listaAttivita = listaAttivita + "\nScrivi l'attività sulla quale vuoi esprimere le preferenze, \n" +
                                "oppure utilizza nuovamente il comando /workspace per selezionarne un altro: ";
                        sendMessage.setText(listaAttivita);
                        stato = "preferenze"; // se non lo mettessi e sbagliassi a scrivere nome attivitò, mi stamperebbe il messaggio d'errore dei workspace anzichè quello delle attività

                    } else {
                        sendMessage.setText("Il workspace inserito non è tra il tuo elenco ❌\n\n" +
                                "Inserisci un workspace che rientri nell'elenco sopra stampato:");
                    }

                    if (msg.equals("/workspace")) { //ristampa lista workspace se l'utente ripete il comando /workspace anzichè scrivere un workspace
                        attivitaSelezionata=""; //azzero la variabile se (dopo aver utilizzato i comandi per le preferenze) richiamo il metodo /workspace
                        attivitaTrovata=false; //azzeriamo altrimenti se inserisco un'altra attività, il bot crede di averla già trovata
                        stato = "attivita";
                        listaWorkspace = "I tuoi workspace: \uD83D\uDCBC \uD83D\uDCDA\n\n";
                        for (int i = 0; i < workspaceFromJson.length; i++) {
                            //scorro lista workspace e uso una variabile d'appoggio per inserire i nomi dei workspace per poi stamparli
                            listaWorkspace = listaWorkspace + "   - " + workspaceFromJson[i].workspace + " \n";
                        }
                        listaWorkspace = listaWorkspace + "\nScrivi il workspace del quale vuoi visualizzare le attività:";
                        sendMessage.setText(listaWorkspace);
                    } else if (msg.equals("/leggipreferenze") || msg.equals("/aggiungipreferenze")) {
                        sendMessage.setText("Ancora non hai selezionato nessun workspace \uD83D\uDEAB️\n\n" +
                                "Inserisci un workspace che rientri nell'elenco sopra stampato: ");
                    }

                    if (msg.equals("/logout")) {
                        attivitaSelezionata="";
                        workspaceSelezionato="";
                        stato = "logout";
                        sendMessage.setText("Hai effettuato il log out da Time Organizer \uD83D\uDD12");
                    }

// --> PREFERENZE
                } else if (stato.equals("preferenze")) { // entra dopo aver selezionato un workspace
                    if (msg.equals("/workspace")) {
                        attivitaSelezionata=""; //azzero la variabile se (dopo aver utilizzato i comandi per le preferenze) richiamo il metodo /workspace
                        attivitaTrovata=false; //azzeriamo altrimenti se inserisco un'altra attività, il bot crede di averla già trovata
                        stato = "attivita";
                        listaWorkspace = "I tuoi workspace: \uD83D\uDCBC \uD83D\uDCDA\n\n";
                        for (int i = 0; i < workspaceFromJson.length; i++) {
                            //scorro lista workspace e uso una variabile d'appoggio per inserire i nomi dei workspace per poi stamparli
                            listaWorkspace = listaWorkspace + "   - " + workspaceFromJson[i].workspace + " \n";
                        }
                        listaWorkspace = listaWorkspace + "\nScrivi il workspace del quale vuoi visualizzare le attività:";
                        sendMessage.setText(listaWorkspace);
                    } else {
                        for (int i = 0; i < attivitaFromJson.length; i++) {
                            if (msg.equalsIgnoreCase(attivitaFromJson[i].attivita)) {
                                for (int j = 0; j < attivita.length; j++) { // scorro array in cui ho messo gli id delle attività che riguardano il workspace selezionato
                                    if(attivitaFromJson[i].id==attivita[j]){
                                        stato = "preferenze"; // richiede di inserire una corretta attività, se ne è stata inserita una inseistente
                                        attivitaTrovata = true;
                                        attivitaSelezionata = attivitaFromJson[i].attivita; // memorizzo l'attività selezionata
                                        sendMessage.setText("Hai selezionato l'attività '" + attivitaFromJson[i].attivita + "' \uD83D\uDD0D \n\n" +
                                                "Invoca uno dei seguenti comandi: \n" +
                                                "   - /leggipreferenze per visualizzare le preferenze di tutti i tuoi workspace e relative attività \n" +
                                                "   - /aggiungipreferenze per inserire una nuova preferenza per l'attività '"+attivitaSelezionata+"' \n\n\n"+
                                                "Puoi invocare il comando /workspace per selezionarne un altro: ");
                                        break;
                                    }
                                }
                            }
                            if(attivitaTrovata==true){
                                break;
                            }
                        }


// --> LEGGI PREFERENZE
                        if (msg.equals("/leggipreferenze") && attivitaTrovata==true && stato.equals("preferenze")) {
                            listaPreferenze = "Le tue preferenze sono: ✏️\n\n";

                            for (int i = 0; i < personFromJson.length; i++) {
                                if (user_username.equals(personFromJson[i].telegramId)) {
                                    for (int j = 0; j < preferenzeFromJson.length; j++) {
                                        if(personFromJson[i].id==preferenzeFromJson[j].idUtente){
                                            listaPreferenze = listaPreferenze + preferenzeFromJson[j].preferenza + " \n\nPuoi invocare uno dei seguenti comandi: \n"+
                                                    "   - /workspace per selezionarne un altro \n"+
                                                    "   - /leggipreferenze per visualizzare le preferenze di tutti i tuoi workspace e relative attività \n" +
                                                    "   - /aggiungipreferenze per inserire una nuova preferenza per l'attività '"+attivitaSelezionata+"' \n\n";
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                            sendMessage.setText(listaPreferenze);

                            // --> AGGIUNGI PREFERENZE
                        } else if(msg.equals("/aggiungipreferenze") && attivitaTrovata==true && stato.equals("preferenze")) {
                            sendMessage.setText("Inserisci una preferenza per l'attività '" + attivitaSelezionata + "' ✏️\n\n" +
                                    "Puoi invocare uno dei seguenti comandi: \n"+
                                    "   - /workspace per selezionarne un altro \n"+
                                    "   - /leggipreferenze per visualizzare le preferenze di tutti i tuoi workspace e relative attività \n" +
                                    "   - /aggiungipreferenze per inserire una nuova preferenza per l'attività '"+attivitaSelezionata+"' \n\n");
                            stato = "aggiungi";

                            // se ancora non è stata inserita un'attività corretta e si usano i comandi /leggipreferenze e /aggiungipreferenze
                        } else if (msg.equals("/leggipreferenze") && attivitaTrovata==false) {
                            attivitaSelezionata="";
                            sendMessage.setText("Ancora non hai selezionato nessun attività \uD83D\uDEAB️\n\n" +
                                    "Inserisci un'attività che rientri nell'elenco sopra stampato:");
                        } else if(msg.equals("/aggiungipreferenze") && attivitaTrovata==false){
                            attivitaSelezionata="";
                            sendMessage.setText("Ancora non hai selezionato nessun attività \uD83D\uDEAB️\n\n" +
                                    "Inserisci un'attività che rientri nell'elenco sopra stampato:");
                        } else if (attivitaTrovata == false ) {
                            attivitaSelezionata="";
                            sendMessage.setText("L'attività inserita non è tra il tuo elenco ❌\n\n" +
                                    "Inserisci un'attività che rientri nell'elenco sopra stampato:");
                        }

                        if (msg.equals("/logout")) {
                            attivitaSelezionata="";
                            workspaceSelezionato="";
                            stato = "logout";
                            sendMessage.setText("Hai effettuato il log out da Time Organizer \uD83D\uDD12");
                        }
                    }


// --> AGGIUNGI PREFERENZE
                } else if (stato.equals("aggiungi")) {
                    if (msg.equals("/leggipreferenze")) {
                        stato = "preferenze";

                        listaPreferenze = "Le tue preferenze sono: ✏️\n\n";
                        for (int i = 0; i < personFromJson.length; i++) {
                            if (user_username.equals(personFromJson[i].telegramId)) {
                                for (int j = 0; j < preferenzeFromJson.length; j++) {
                                    if (personFromJson[i].id == preferenzeFromJson[j].idUtente) {
                                        listaPreferenze = listaPreferenze + preferenzeFromJson[j].preferenza + " \n\nPuoi invocare uno dei seguenti comandi: \n" +
                                                "   - /workspace per selezionarne un altro \n" +
                                                "   - /leggipreferenze per visualizzare le preferenze di tutti i tuoi workspace e relative attività \n" +
                                                "   - /aggiungipreferenze per inserire una nuova preferenza per l'attività '" + attivitaSelezionata + "' \n\n";
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        sendMessage.setText(listaPreferenze);

                    } else if (msg.equals("/aggiungipreferenze")) {
                        sendMessage.setText("Inserisci una preferenza per l'attività '" + attivitaSelezionata + "' ✏️\n\n" +
                                "Puoi invocare uno dei seguenti comandi: \n" +
                                "   - /workspace per selezionarne un altro \n" +
                                "   - /leggipreferenze per visualizzare le preferenze di tutti i tuoi workspace e relative attività \n" +
                                "   - /aggiungipreferenze per inserire una nuova preferenza per l'attività '" + attivitaSelezionata + "' \n\n");
                        stato = "aggiungi";
                    } else if (msg.equals("/workspace")) {
                        attivitaSelezionata = ""; //azzero la variabile se (dopo aver utilizzato i comandi per le preferenze) richiamo il metodo /workspace
                        attivitaTrovata = false; //azzeriamo altrimenti se inserisco un'altra attività, il bot crede di averla già trovata
                        stato = "attivita";
                        listaWorkspace = "I tuoi workspace: \uD83D\uDCBC \uD83D\uDCDA\n\n";
                        for (int i = 0; i < workspaceFromJson.length; i++) {
                            //scorro lista workspace e uso una variabile d'appoggio per inserire i nomi dei workspace per poi stamparli
                            listaWorkspace = listaWorkspace + "   - " + workspaceFromJson[i].workspace + " \n";
                        }
                        listaWorkspace = listaWorkspace + "\nScrivi il workspace del quale vuoi visualizzare le attività:";
                        sendMessage.setText(listaWorkspace);

                    } else if(msg.equals("/logout")){
                        attivitaSelezionata="";
                        workspaceSelezionato="";
                        stato = "logout";
                        sendMessage.setText("Hai effettuato il log out da Time Organizer \uD83D\uDD12");
                    } else {
                        //System.out.println("WORKSPACE: "+workspaceSelezionato+", ATTIVITA: "+attivitaSelezionata); // per verificare le selezioni dell'utente
                        listaPreferenze = "Le tue preferenze sono state aggiornate: ✅✏️\n\n";

                        // passo dal workspace selezionato e dall'attività selezionata per andare a memorizzare la preferenza espressa
                        for (int i = 0; i < workspaceFromJson.length; i++) {
                            if(workspaceSelezionato.equalsIgnoreCase(workspaceFromJson[i].workspace)){ // entro nel workspace selezionato

                                for (int j = 0; j < attivitaFromJson.length; j++) {
                                    if (attivitaSelezionata.equalsIgnoreCase(attivitaFromJson[j].attivita)) { // entro nell'attività selezionata

                                        for (int k = 0; k < personFromJson.length; k++) {
                                            if (user_username.equals(personFromJson[k].telegramId)) { // entro nell'utente giusto verificando idTelegram di chi sta scrivendo al bot

                                                for (int z = 0; z < preferenzeFromJson.length; z++) {
                                                    if(personFromJson[k].id==preferenzeFromJson[z].idUtente){ // prendo l'id dell'utente per usarlo nel file Preferenze.json

                                                        preferenzeFromJson[z].preferenza = preferenzeFromJson[z].preferenza + workspaceSelezionato + "\n" + " > " + attivitaSelezionata + " " + msg +" \n";
                                                        listaPreferenze = listaPreferenze + preferenzeFromJson[z].preferenza + " \n\nPuoi invocare uno dei seguenti comandi: \n"+
                                                                "   - /workspace per selezionarne un altro \n"+
                                                                "   - /leggipreferenze per visualizzare le preferenze di tutti i tuoi workspace e relative attività \n" +
                                                                "   - /aggiungipreferenze per inserire una nuova preferenza per l'attività '"+attivitaSelezionata+"' \n\n";
                                                        sendMessage.setText(listaPreferenze);
                                                        stato = "preferenze"; // per poter riutilizzare gli altri comandi

                                                        // salvataggio nuova preferenza
                                                        FileWriter newWriter = null;
                                                        try {
                                                            newWriter = new FileWriter("Preferenze.json");
                                                            gson.toJson(preferenzeFromJson, newWriter);
                                                            newWriter.close();
                                                        } catch (IOException e) {
                                                            System.out.println("Salvataggio nuova preferenza da bot non riuscito.");
                                                            e.printStackTrace();
                                                        }

                                                        break;
                                                    }
                                                }
                                                break;
                                            }
                                        }
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }



// --> LOGOUT
                } else if (msg.equals("/logout") && !stato.equals("wait")) {
                    attivitaSelezionata="";
                    workspaceSelezionato="";
                    stato = "logout";
                    sendMessage.setText("Hai effettuato il log out da Time Organizer \uD83D\uDD12");

                    // --> COMANDI ERRATI O INUTILIZZABILI IN UN DETERMINATO MOMENTO
                } else {
                    // entra se è stato invocato un comando che non è nella lista
                    // oppure se lo stato in cui l'utente si trova non permette di invocare quel comando poichè prima necessita l'invocazione di un altro comando
                    // ESEMPIO: se si vuole visualizzare l'elenco dei workspace, prima bisogna effettuare l'accesso
                    if (stato.equals("wait")) {
                        sendMessage.setText("Ancora non hai effettuato l'accesso \uD83D\uDD10\n\n" +
                                "Utilizza il comando /login per accedere al sistema:");
                    } else if (stato.equals("inserimento password")) {
                        sendMessage.setText("Ancora non hai effettuato l'accesso \uD83D\uDD10\n\n" +
                                "Per accedere ai tuoi workspace, inserisci la password:");
                    } else if (stato.equals("workspace")) { //hai effettuato l'accesso ma ancora non hai invocato il comando /workspace
                        sendMessage.setText("Ancora non hai selezionato nessun workspace \uD83D\uDEAB️\n\n" +
                                "Invoca il comando /workspace per visualizzare l’elenco dei tuoi workspace e per selezionare l'attività su cui lavorare: ");
                    } else if (stato.equals("logout")) {
                        sendMessage.setText("Ancora non hai effettuato l'accesso \uD83D\uDD10\n\n" +
                                "Utilizza il comando /login per accedere al sistema:");
                    } else {
                        sendMessage.setText("Comando non valido.");
                        System.out.println("Comando non valido.");
                    }
                }

                sendMessage.setChatId(chatId); // bot invia messaggio alla chat che ha quell'id
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    System.out.println("Problemi con comandi del bot.");
                    System.err.println(e.getMessage());
                }


// --> bot DISATTIVATO
            } else if (admin.attivato == false) {
                if (msg.isEmpty()==false) { // se non è vuoto, quindi se sono stati inviati messaggi
                    sendMessage.setText("⛔ BOT DISATTIVATO ⛔\n\n" +
                            "Non potrà essere utilizzato fino a quando l'amministratore non lo riattiverà.");
                    System.out.println("Il bot è stato disattivato.");
                }

                sendMessage.setChatId(chatId);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    System.out.println("Problemi con invio dei messaggi al bot.");
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    @Override
    public String getBotToken () {
        return "1740009076:AAFCDA-HqR3lNSJckAkLUnI-En_X6Awwn9g";
    }

}