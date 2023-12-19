package sample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import java.time.LocalDate;

public class UserController {

    public TextField vecchioNomeUser;
    public TextField vecchioTelegramIdUser;
    public TextField vecchiaPasswordUser;
    public TextField nuovoTelegramIdUser;
    public Button modificaUserButton;
    public Button verificaUserButton;
    public TextField nuovaPasswordUser;
    public TextField nuovoNomeUtenteUser;
    public Label verificaUMessageLabel;
    public Label modificaUMessageLabel;
    public FontAwesomeIcon emojiUserUser;
    public FontAwesomeIcon emojiTelegramUser;
    public FontAwesomeIcon emojiLockUser;
    public Button bottoneHelp;
    public AnchorPane anchorPaneUserHelp;
    public AnchorPane anchorPaneUserTelegram;
    public Button bottoneTelegram;
    public Button contactUsUserButton;
    public AnchorPane anchorPaneMessage;
    @FXML
    private VBox leftUserVBox;
    @FXML
    private Button bottoneAzioneUtente;
    @FXML
    private Button bottoneSettings;
    @FXML
    private Button userCloseButtonOnAction;
    @FXML
    private Button logoutButtonUser;
    @FXML
    private AnchorPane anchorPaneUserSettings;
    @FXML
    private AnchorPane anchorPaneUserCalendario;
    @FXML
    private ListView<String> listworkspaceUser;
    @FXML
    private ListView<String> listAttivitaUser;
    @FXML
    private TextArea textAreaPrefernzeUser;
    @FXML
    private AnchorPane userTitlePane;
    @FXML
    private Label userTitleLabel;
    @FXML
    private Label userMessageLabel;


    Stage stage;
    public boolean esistente = false;
    public boolean trovato = false; // per verificare se i campi che sto modificando in "Settings" sono già occupati o meno
    public int idUfficialeUserModifica; //variabile generica per prendere nome utente
    AdministratorController adminController = new AdministratorController();

    //--------------------------------CONTROLLO ID UTENTE GENERICO --------------------------------------

    public int controlloIdUtente() throws FileNotFoundException {
        int id = -1; //inizializzo con valore sicuramente sbagliato essendo negativo
        FileReader readerU = new FileReader("Utenti.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Utente[] utenteFromJson = gson.fromJson(readerU, Utente[].class);

        for(int i = 0; i < utenteFromJson.length; i++){
            if(LoginSceneController.nomeUtente.equals(utenteFromJson[i].username)){

                id = utenteFromJson[i].id;
            }
        }
        return id;
    }

    //--------------------------------CONTROLLO USERNAME GENERICO --------------------------------------
    public boolean controlloUsernameUser(String username, Utente[] personFromJson ){
        boolean trovato = false;
        for(int i=0; i<personFromJson.length; i++){
            if(username.equals(personFromJson[i].username)){
                trovato = true;
            }
        }
        return trovato;
    }

    //--------------------------------CONTROLLO TELEGRAM ID GENERICO --------------------------------------
    public boolean controlloTelegramIdUser(String telegramId, Utente[] personFromJson){
        boolean telegram = false;
        for(int i = 0; i < personFromJson.length; i++){
            if(telegramId.equals(personFromJson[i].telegramId)){
                telegram = true;
                break;
            }
        }
        return telegram;
    }

    //--------------------------------CONTROLLO PASSWORD GENERICO --------------------------------------
    public boolean controlloPasswordUser(String passwordUser, Utente[] personFromJson){
        boolean password = false;
        for(int i = 0; i < personFromJson.length; i++){
            if(passwordUser.equals(personFromJson[i].pw)){
                password = true;
            }
        }
        return password;
    }

    //---------------------------CAMBIO SCENA TRA I PANNELLI DELLA VBOX----------------------------------
    @FXML
    private void handleClicks(ActionEvent event) throws FileNotFoundException {
        if(event.getSource() == bottoneAzioneUtente){
            //Utente

            userTitleLabel.setText("Calendario");
            anchorPaneUserCalendario.toFront(); //pone in primo piano il pannello indicato

            listworkspaceUser.getItems().clear(); //pulisce tutto quello usato in precedenza
            userMessageLabel.setText("In questa sezione potrai esprimere le preferenze in base alle attivita' proposte");

            //Inserisco dati nella tabella
            FileReader reader = new FileReader("Workspace.json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            try{
                Workspace[] workspaceFromJson = gson.fromJson(reader, Workspace[].class);
                for(int i=0; i< workspaceFromJson.length; i++){
                    listworkspaceUser.getItems().add(workspaceFromJson[i].workspace);
                }
            }catch (Exception e){
                System.err.println(e.getMessage());
            }

            //Legge il file preferenze e stampa le preferenze gia' espresse nell'area indicata
            FileReader readerP = new FileReader("Preferenze.json");
            try{
                Preferenze[] preferenzeFromJson = gson.fromJson(readerP, Preferenze[].class);
                for(int i=0; i< preferenzeFromJson.length; i++){
                    if(controlloIdUtente() == preferenzeFromJson[i].idUtente){

                        //Time:
                        LocalDateTime dataOdierna = LocalDateTime.now();
                        DateTimeFormatter formatoCorretto = DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm:ss");
                        String dataFinale = dataOdierna.format(formatoCorretto);

                        textAreaPrefernzeUser.setText(preferenzeFromJson[i].preferenza);
                        textAreaPrefernzeUser.appendText(" \n");
                        textAreaPrefernzeUser.appendText("\"`-._,-'\"`-._,-' \"`-._,-'\"`-._,-' \n \n"); //semplice stacco per le nuove preferenze
                        textAreaPrefernzeUser.appendText(" Giorno: " + dataFinale + "\n");

                    }
                }
            }catch (Exception e){
                System.err.println(e.getMessage());
            }

        } else if(event.getSource() == bottoneTelegram){
            //Telegram

            userTitleLabel.setText("Telegram");
            anchorPaneUserTelegram.toFront();

            userMessageLabel.setText("In questa sezione potrai aprire direttamente il bot telegram sull'app o su telegram web");

        } else if(event.getSource() == bottoneSettings){
            //Settings
            if(adminController.attivato){
                bottoneSettings.disableProperty();

            } else {
                userTitleLabel.setText("Impostazioni");
                anchorPaneUserSettings.toFront();

                modificaUMessageLabel.setText("");
                verificaUMessageLabel.setText("");
                vecchioNomeUser.clear();
                vecchioTelegramIdUser.clear();
                vecchiaPasswordUser.clear();
                nuovoTelegramIdUser.clear();
                nuovaPasswordUser.clear();
                nuovoNomeUtenteUser.clear();

                listworkspaceUser.getItems().clear(); //evita di stampare la lista workspace ogni volta che si entra nuovamente nella scheda utente

                userMessageLabel.setText("In questa sezione si ha la possibilita' di gestire le proprie impostazioni. \n" +
                        "Inserendo i dati nella colonna di sinistra ti permettera' di verificarli premendo il pulsante verifica \n" +
                        "Premendo il pulsante TELEGRAM avrai la possibilita' di aprire direttamente telegram web e utilizzare TIME ORGANIZER");

            }

        } else if(event.getSource() == bottoneHelp){
            //Help

            userTitleLabel.setText("Help");
            anchorPaneUserHelp.toFront();

            userMessageLabel.setText("Si prega l'utente di non modficare l'oggetto della mail!");

        }
    }

    //---------------------------AREA DI STAMPA NELLE LISTVIEW----------------------------------
    public void selectWorkspace(MouseEvent mouseEvent) throws FileNotFoundException {
        FileReader readerW = new FileReader("Workspace.json"); //readerW --> reader per i workspace
        FileReader readerA = new FileReader("Attivita.json"); //readerA --> reader per le attivita
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Workspace[] workspaceFromJson = gson.fromJson(readerW, Workspace[].class);
        for(int i=0; i< workspaceFromJson.length; i++){
            /*
                //System.out.println(listworkspaceUser.getFocusModel().getFocusedItem() + " "); --> per controllo
                // System.out.println(listworkspaceUser.getSelectionModel().getSelectedItems()); --> per controllo
                //System.out.println(workspaceFromJson[i].workspace + " "); --> per controllo
            */
            if(listworkspaceUser.getFocusModel().getFocusedItem().equals(workspaceFromJson[i].workspace)){
                listAttivitaUser.getItems().clear();
                try{
                    Attivita[] attivitaFromJson = gson.fromJson(readerA, Attivita[].class);
                    for(int j=0; j < workspaceFromJson[i].attivita.length; j++){

                        listAttivitaUser.getItems().add(attivitaFromJson[workspaceFromJson[i].attivita[j]].attivita);
                    }
                }catch (Exception e){
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    //---------------------------STAMPA DEI WORKSPACE IN PREFERENZE UTENTE----------------------------------
    public void preferenzeUserWorkspace(MouseEvent mouseEvent) {

        String prefWorkspace = listworkspaceUser.getFocusModel().getFocusedItem();
        textAreaPrefernzeUser.appendText(prefWorkspace + "\n");

    }

    public void prefernzeUserAttivita(MouseEvent mouseEvent) {

        String prefAttivita = listAttivitaUser.getFocusModel().getFocusedItem();
        textAreaPrefernzeUser.appendText(" > " + prefAttivita + "\n");
        textAreaPrefernzeUser.appendText("GIORNO: \n");
        textAreaPrefernzeUser.appendText("DURATA: \n");
        textAreaPrefernzeUser.appendText("NOTE: \n");

    }

    //---------------------------SALVATAGGIO DELLE PREFERENZE----------------------------------
    public void salvaPrefernze(ActionEvent mouseEvent) throws IOException{
        if(adminController.attivato){
            userMessageLabel.setText("Il bot telgram e' attivo. Non e' possibile salvare le preferenze fino a quando non verra' disattivato.");

        } else {
            FileReader readerU = new FileReader("Utenti.json");
            FileReader readerP = new FileReader("Preferenze.json");

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            Utente[] utenteFromJson = gson.fromJson(readerU, Utente[].class);
            Preferenze[] preferenzeFromJson = gson.fromJson(readerP, Preferenze[].class);

            boolean trovato = false; //--> usato per controllo id
            //System.out.println("Prova prima for");
            int idUtente = controlloIdUtente();
            for(int j = 0; j < preferenzeFromJson.length; j++){
                if( idUtente == preferenzeFromJson[j].idUtente){
                    userMessageLabel.setText("Salvataggio completato");
                    System.out.println("Controllo avvenuto correttamente, salvataggio ...");

                    trovato = true;

                    preferenzeFromJson[j].preferenza = textAreaPrefernzeUser.getText();

                    FileWriter newWriter =new FileWriter("Preferenze.json");
                    gson.toJson(preferenzeFromJson, newWriter);
                    newWriter.close();

                    break;

                }
            }

            if(trovato == false){
                //Se non trova l'utente nelle preferenze, scrive un nuovo file
                Preferenze preferenza = new Preferenze(idUtente, textAreaPrefernzeUser.getText());

                preferenzeFromJson = aggiungiElemento(preferenzeFromJson, preferenza);
                FileWriter newWriter =new FileWriter("Preferenze.json");
                gson.toJson(preferenzeFromJson, newWriter);
                newWriter.close();

            }
        }

    }

    //---------------------------AGGIUNTA DELLE PREFERENZE NEL FILE DELLE PREFERENZE----------------------------------
    public Preferenze[] aggiungiElemento(Preferenze[] preferenze, Preferenze nuovoOggetto){
        Preferenze[] preferenzeTemporanee = new Preferenze[preferenze.length+1];
        for(int i = 0; i < preferenze.length; i++){
            preferenzeTemporanee[i] = preferenze[i];
        }
        preferenzeTemporanee[preferenze.length] = nuovoOggetto;
        return preferenzeTemporanee;
    }

    //---------------------------------------------MODIFICA DATI UTENTE-------------------------------------------------

    public void verificaDatiUtente(ActionEvent event) throws IOException {

        modificaUMessageLabel.setText("");

        FileReader reader = new FileReader("Utenti.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Utente[] personFromJson = gson.fromJson(reader, Utente[].class);

        if(event.getSource() == verificaUserButton){ // BOTTONE VERIFICA
            for(int i = 0; i < personFromJson.length; i++){
                if(controlloIdUtente() == personFromJson[i].id){
                    idUfficialeUserModifica = controlloIdUtente();
                    //System.out.println(idUfficialeUserModifica + " ");
                }
            }

            try{
                //controlliamo se l'utente esiste già
                if(vecchioNomeUser.getText().isBlank() == true){
                    verificaUMessageLabel.setText("Username mancante");
                } else {
                    if(vecchiaPasswordUser.getText().isBlank() == true){
                        verificaUMessageLabel.setText("Password mancante");
                    }else if(vecchioNomeUser.getText().isBlank() == false && vecchiaPasswordUser.getText().isBlank() == false){
                        if(controlloUsernameUser(vecchioNomeUser.getText(), personFromJson) && controlloTelegramIdUser(vecchioTelegramIdUser.getText(), personFromJson) && controlloPasswordUser(vecchiaPasswordUser.getText(), personFromJson)){
                            esistente = true;
                        }
                    }
                }

                if(esistente==false){
                    //controllo per l'esistenza dell'utente
                    verificaUMessageLabel.setText("Dati mancanti o errati");
                } else if(esistente) {
                    for(int i = 0; i < personFromJson.length; i++){

                        nuovoNomeUtenteUser.setText(personFromJson[idUfficialeUserModifica].username);
                        nuovoTelegramIdUser.setText(personFromJson[idUfficialeUserModifica].telegramId);
                        nuovaPasswordUser.setText(personFromJson[idUfficialeUserModifica].pw);

                        /*
                        vecchioNomeUser.setEditable(false);
                        vecchioTelegramIdUser.setEditable(false);
                        vecchiaPasswordUser.setEditable(false);
                         */
                    }

                    verificaUMessageLabel.setText("L'utente e' corretto");
                }

            }catch (Exception e){
                verificaUMessageLabel.setText("File non trovato. ");
                e.printStackTrace();
                e.getCause();
            }
        }

    }

    public void modificaDatiUtente(ActionEvent event) throws IOException{
        //fase di registrazione dei nuovi dati
        boolean campoVuoto = false;
        boolean telegramEsistente = false;
        boolean idEsistente = false;
        FileReader reader = new FileReader("Utenti.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Utente[] personFromJson = gson.fromJson(reader, Utente[].class);

        //fase di registrazione dei nuovi dati
        modificaUMessageLabel.setText("");

        if (esistente) {
            Type type = new TypeToken<List<Utente>>() {
            }.getType();
            FileReader newReader = new FileReader("Utenti.json");
            List<Utente> utenteList = gson.fromJson(newReader, type);
            newReader.close();
            if (null == utenteList) {
                utenteList = new ArrayList<>();
            }

            for (int i = 0; i < utenteList.size(); i++) { // CONTROLLO ESISTENZA USERNAME

                if (nuovoNomeUtenteUser.getText().equals(utenteList.get(i).username)) {//controlliamo se l'utente esiste gia

                    if (!utenteList.get(i).username.equals(vecchioNomeUser.getText()) && utenteList.get(i).username.equals(nuovoNomeUtenteUser.getText())) {
                        //controllo se username rimane invariato ed e' diverso dagli altri
                        modificaUMessageLabel.setText("L'username e' gia' in uso");
                        trovato = true;
                        idEsistente=true;
                        break;
                    } else {
                        trovato=false;
                        idEsistente=false;
                    }
                }
                if (nuovoNomeUtenteUser.getText().isBlank() == true) {
                    modificaUMessageLabel.setText("Username mancante");
                    campoVuoto = true;
                    break;
                } else if (nuovaPasswordUser.getText().isBlank() == true) {
                    modificaUMessageLabel.setText("Password mancante");
                    campoVuoto = true;
                    break;
                } else {
                    campoVuoto = false;
                }

            }

            for (int i = 0; i < utenteList.size(); i++) {
                if (trovato == false && campoVuoto == false && idEsistente==false) {
                    if (idUfficialeUserModifica == utenteList.get(i).id) {
                        utenteList.get(i).username = nuovoNomeUtenteUser.getText();
                        //utenteList.get(i).telegramId = nuovoTelegramIdUser.getText();
                        utenteList.get(i).pw = nuovaPasswordUser.getText();
                        modificaUMessageLabel.setText("Modifica Effettuata");
                        System.out.println("1");
                    }
                } else {
                    modificaUMessageLabel.setText("Modifica non Effettuata");
                    System.out.println("2");
                }
                FileWriter newWriter = new FileWriter("Utenti.json");
                gson.toJson(utenteList, newWriter);
                newWriter.close();
            }


            for (int i = 0; i < utenteList.size(); i++) { // CONTROLLO ESISTENZA ID TELEGRAM

                if(trovato==false){
                    if (nuovoTelegramIdUser.getText().equals((String)utenteList.get(i).telegramId)) {
                        if (!nuovoTelegramIdUser.getText().equals("") && !utenteList.get(i).telegramId.equals(vecchioTelegramIdUser.getText())){ //se id telegram non rimane invariato
                            if(nuovoTelegramIdUser.getText().equals(utenteList.get(i).telegramId) && utenteList.indexOf(vecchioNomeUser)!=i){
                                modificaUMessageLabel.setText("Telegram Id gia' in uso");
                                telegramEsistente=true;
                                break;
                            } else {
                                telegramEsistente=false;
                            }
                        } else if (nuovoTelegramIdUser.getText().isBlank() == true) {
                            modificaUMessageLabel.setText("Utente senza telegram");
                            telegramEsistente=false; //
                            break;
                        } else {
                            telegramEsistente=false;
                        }
                    }

                    if (nuovoNomeUtenteUser.getText().isBlank() == true) {
                        modificaUMessageLabel.setText("Username mancante");
                        campoVuoto = true;
                        break;
                    } else if (nuovaPasswordUser.getText().isBlank() == true) {
                        modificaUMessageLabel.setText("Password mancante");
                        campoVuoto = true;
                        break;
                    } else {
                        campoVuoto = false;
                    }
                }
            }

            for (int i = 0; i < utenteList.size(); i++) {
                if (trovato == false && campoVuoto==false && telegramEsistente==false && idEsistente==false) {
                    if (idUfficialeUserModifica == utenteList.get(i).id) {
                        utenteList.get(i).username = nuovoNomeUtenteUser.getText();
                        utenteList.get(i).telegramId = nuovoTelegramIdUser.getText();
                        utenteList.get(i).pw = nuovaPasswordUser.getText();
                        modificaUMessageLabel.setText("Modifica Effettuata");
                        System.out.println("3");
                    }
                } else {
                    modificaUMessageLabel.setText("Modifica non Effettuata");
                    System.out.println("4");
                }

                FileWriter newWriter = new FileWriter("Utenti.json");
                gson.toJson(utenteList, newWriter);
                newWriter.close();
            }
        }
    }


    //---------------------------------------------APERTURA TELEGRAM WEB-------------------------------------------------
    public void openTelegram() {
        try {
            Desktop.getDesktop().browse(new URL("https://t.me/MNZR_bot").toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //---------------------------------------------CONTATTACI-------------------------------------------------
    public void contactUs(ActionEvent event) throws IOException{

        Desktop desktop = Desktop.getDesktop();
        String message = "mailto:nicola.ricci13@studio.unibo.it?subject=Time%20Organizer%20Help" +
                "&cc=martina.zauli@studio.unibo.it";
        URI uri = URI.create(message);
        desktop.mail(uri);

    }

    //---------------------------------------------LOGUT APPLICATIVO-------------------------------------------------
    public void logoutButtonOnAction(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("sample.fxml");
    }

    //---------------------------------------------CHIUSURA APPLICATIVO -------------------------------------------------
    public void close(javafx.event.ActionEvent actionEvent) {
        //messaggio di allerta prima della chiusura
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("!Stai per chiudere il programma!");
        alert.setContentText("Vuoi salvare prima di chiudere il programma?");

        if(alert.showAndWait().get() == ButtonType.OK){
            //se voglio uscire
            //fase di chiusura finale
            stage = (Stage) leftUserVBox.getScene().getWindow();
            System.out.println(" ");
            System.out.println("Hai chiuso correttamente il programma e hai eseguito il logout");
            System.out.println(" ");
            stage.close();
        }
    }


}