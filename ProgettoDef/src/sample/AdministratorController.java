package sample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdministratorController {
    @FXML
    public AnchorPane anchorAggiungiAttivita;
    @FXML
    public AnchorPane anchorAggiungiWorkspace;
    @FXML
    public AnchorPane anchorAggiungiUser;
    @FXML
    public Button rimuoviUserButton;
    @FXML
    public Button aggiungiWorkspaceButtontoPage;
    @FXML
    public Button aggiungiAttivitaButtonToPage;
    @FXML
    public Label userAddMessageLabel;
    public TextField addUserUsernameField;
    public TextField addUserTelegramIdField;
    public PasswordField addUserPasswordField;
    public TextField workspaceAggiungiWorkspace;
    public Label workspaceAddMessageLabel;
    public TextField idAttivitaAggiungiWorkspace;
    public TextArea testoAggiungiWorkspace;
    public TextField attivitaAddNomeAttivita;
    public TextField attivitaAddLuogo;
    public TextArea attivitaAddDescrizione;
    public Label attivitaAddMessageLabel;
    public Button modificaAttivitatoPane;
    public AnchorPane anchorModificaAttivita;
    public AnchorPane anchorModificaWorkspace;
    public TextField workspaceModificaWorkspace;
    public Button modificaWorkspaceButton;
    public TextField luogoModificaWorkspace;
    public TextArea descrizioneModificaWorkspace;
    public Label modificaWorkspaceMessageLabel;
    public TextField idModificaWorkspace;
    public Button okButtonModificaWorkspace;
    public Button modificaWorkspacetoPane;
    public TextField IdAttivitaModificaWorkspace;
    public TextField attivitaModificaAttivita;
    public Button modificaAttivitaButton;
    public TextField luogoModificaAttivita;
    public Label attivitaModificaMessageLabel;
    public TextArea descrizioneModificaAttivita;
    public TextField idUfficialeAttivita;
    public Button modificaAttivitaOKButton;
    public AnchorPane anchorModificaUser;
    public TextField modificaNomeUser;
    public Button modificaUserButton;
    public TextField modificaTelegramIdUser;
    public Label modificaUserMessageLabel;
    public TextField modificaIdUser;
    public Button modificaUserOKButton1;
    public TextField modificaUserPassword;
    public Button modifcaUserToPane;
    public Button refreshButtonWorkspace;
    public Button refreshButtonUser;
    public Button refreshButtonAttivita;
    public Button bottonePreferenze;
    public AnchorPane anchorPanePreferenze;
    public TextField idUtentePreferenze;
    public TextArea textAreaPreferenze;
    public Button idUtenteOkButton;
    public Button modificaPreferenzeToPane;
    public AnchorPane anchorPaneModificaPreferenze;
    public Button modificaPreferenze;
    public TextField idUtenteModificaPreferenze;
    public TextArea textAreaModificaPreferenze;
    public Button idUtenteModificaOkButton1;
    public Label modificaPreferenzeMessageLabel;
    public Button addFileAttivita;
    public Button addFileUser;
    public Button addFilePreferenze;
    public Button addFileWorkspace;
    public Button downloadFileUser;
    public Button downloadFileAttivita;
    public Button dowloadFileWorkspace;
    @FXML
    private VBox leftAdminVBox;
    @FXML
    private Button bottoneUser;
    @FXML
    private Button bottoneAttivita;
    @FXML
    private Button bottoneWorkspace;
    @FXML
    private Button bottoneSettings;
    @FXML
    private Button adminCloseButtonOnAction;
    @FXML
    private Button logoutButtonAdmin;
    @FXML
    private AnchorPane anchorGestoreAggiungiUser;
    @FXML
    private TextField usernameUserTextField;
    @FXML
    private PasswordField passwordUserPasswordField;
    @FXML
    private Button aggiungiUserButtonPage;
    @FXML
    private Button aggiungiUserButtonToPage;
    @FXML
    private AnchorPane anchorGestoreAggiungiAttivita;
    @FXML
    private TextField usernameAttivitaTextField;
    @FXML
    private PasswordField passwordAttivitaPasswordField;
    @FXML
    private Button aggiungiAttivitaButton;
    @FXML
    private GridPane paneAttivita;
    @FXML
    private TableView<AttivitaTabella> attivitaTableView;
    @FXML
    private TableColumn<Attivita, Integer> columnAttivitaId;
    @FXML
    private TableColumn<Attivita, String> columnNomeAttivita;
    @FXML
    private TableColumn<Attivita, String> columnLuogo;
    @FXML
    private GridPane paneWorkspace;
    @FXML
    private TableView<WorkspaceTabella> workspaceTableView;
    @FXML
    private TableColumn<Workspace, Integer> columnWorkspaceId;
    @FXML
    private TableColumn<Workspace, String> columnWorkspace;
    @FXML
    private TableColumn<Workspace, String> columnAttivita;
    @FXML
    private AnchorPane anchorPaneSettings;
    @FXML
    private Label botTextLabel;
    @FXML
    private Button telegramOffButton;
    @FXML
    private Button telegramOnButton;
    @FXML
    private AnchorPane anchorWorkspaceAggiungiAttivita;
    @FXML
    private TextField usernameWorkspaceTextField1;
    @FXML
    private PasswordField passwordWorkspacePasswordField1;
    @FXML
    private Button aggiungiWorkspaceButton;
    @FXML
    private GridPane paneUser;
    @FXML
    private TableView<UtenteTabella> userTableView;
    @FXML
    private TableColumn<Utente, Integer> columnUserId;
    @FXML
    private TableColumn<Utente, String> columnUsername;
    @FXML
    private TableColumn<Utente, String> columnTelegramId;
    @FXML
    private TableColumn<Utente, String> columnPassword;
    @FXML
    private AnchorPane adminTitlePane;
    @FXML
    private Label adminTitleLabel;
    @FXML
    private Label adminMessageLabel;

    Stage stage;
    public static boolean attivato; // se bot è online oppure offline
    int idUtenteModifica; // utile per la modifica degli utente e tenere memorizzato l'utente che si sta modificando
    int idWorkspaceModifica; // stessa cosa per workspace
    int idAttivitaModifica; // stessa cosa per attività


    //-------------------------------------DISATTIVA TELEGRAM--------------------------------------
    public void telegramOff(ActionEvent event)  throws IOException{
        this.attivato = false;
        ApiContextInitializer.init();
        TelegramBotsApi api = new TelegramBotsApi();
        try {
            api.registerBot(new BotMN());
            System.out.println("MNZR_bot Offline.");
            botTextLabel.setText("MNZR_bot Offline."); //per capire se partito
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------ATTIVA TELEGRAM--------------------------------------
    public void telegramOn(ActionEvent event) throws IOException {
        this.attivato = true;
        ApiContextInitializer.init();
        TelegramBotsApi api = new TelegramBotsApi();
        try {
            api.registerBot(new BotMN());
            System.out.println("MNZR_bot Online.");
            botTextLabel.setText("MNZR_bot Online."); //per capire se partito
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------GESTORE PANNELLI PRINCIPALI--------------------------------------
    @FXML
    private void handleClicks(ActionEvent event) throws FileNotFoundException {
        if(event.getSource() == bottoneUser){
            //gestione degli USER
            adminTitleLabel.setText("User"); //setta il titolo nell'apposito label in alto a sinistra
            paneUser.toFront(); //pone in primo piano il pannello indicato(paneUser in questo caso)

            adminMessageLabel.setText("In questa sezione si ha la possibilita' di gestire gli utenti.");

            //inserimento dei dati nella tabella inerente agli USER
            FileReader reader = new FileReader("Utenti.json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            Utente[] personFromJson = gson.fromJson(reader, Utente[].class);
            LinkedList<UtenteTabella> personTabellaFromJson = new LinkedList<>();
            for(int i = 0; i < personFromJson.length; i++){
                personTabellaFromJson.add(new UtenteTabella(personFromJson[i]));
            }
            ObservableList<UtenteTabella> list = FXCollections.observableArrayList(
                    personTabellaFromJson
                    /*
                    Dal sito di Oracle --> usano ObservableList per gestire le tableView e tableColumn
                    quindi tramite le classi Utnete...Workspace Prova ho convertito i dati e le variabili
                    al fine di essere lette nelle ObservableList
                     */
                    /*
                    new UtenteProva(4, "nome", "cognome", "1234", false), //prova aggiunta utenti manuale nella tabella
                    new UtenteProva(5, "pippo", "pluto", "12ciccio", false)
                     */
            );
            userTableView.setItems(list); //settaggio elementi nella tabella
            columnUserId.setCellValueFactory( new PropertyValueFactory<Utente, Integer>("id")); //settaggio elementi nelle colonne
            columnUsername.setCellValueFactory(new PropertyValueFactory<Utente, String>("username"));
            columnTelegramId.setCellValueFactory(new PropertyValueFactory<Utente, String>("telegramId"));
            columnPassword.setCellValueFactory(new PropertyValueFactory<Utente, String>("pw"));

        } else if(event.getSource() == bottoneWorkspace){
            //gestione dei workspace
            adminTitleLabel.setText("Workspace");

            paneWorkspace.toFront();

            adminMessageLabel.setText("In questa sezione si ha la possibilita' di gestire i workspace.");

            //inserimento dati nella tabella attivita
            FileReader reader = new FileReader("Workspace.json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            Workspace[] workspaceFromJson = gson.fromJson(reader, Workspace[].class);
            LinkedList<WorkspaceTabella> workspaceProvaFromJson = new LinkedList<>();
            for(int i = 0; i < workspaceFromJson.length; i++){
                workspaceProvaFromJson.add(new WorkspaceTabella(workspaceFromJson[i]));
            }
            ObservableList<WorkspaceTabella> list = FXCollections.observableArrayList(
                    workspaceProvaFromJson

            );
            workspaceTableView.setItems(list);
            columnWorkspaceId.setCellValueFactory( new PropertyValueFactory<Workspace, Integer>("id"));
            columnWorkspace.setCellValueFactory(new PropertyValueFactory<Workspace, String>("workspace"));
            columnAttivita.setCellValueFactory(new PropertyValueFactory<Workspace, String>("attivita"));

        } else if(event.getSource() == bottoneAttivita){
            //gestione delle attivita
            adminTitleLabel.setText("Attivita'");
            paneAttivita.toFront(); //pannello attivita di fronte

            adminMessageLabel.setText("In questa sezione si ha la possibilita' di gestire le singole attivita.");

            //inserimento dati nella tabella attivita
            FileReader reader = new FileReader("Attivita.json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            Attivita[] activityFromJson = gson.fromJson(reader, Attivita[].class);
            LinkedList<AttivitaTabella> activityProvaFromJson = new LinkedList<>();
            for(int i = 0; i < activityFromJson.length; i++){
                activityProvaFromJson.add(new AttivitaTabella(activityFromJson[i]));
            }
            ObservableList<AttivitaTabella> list = FXCollections.observableArrayList(
                    activityProvaFromJson
            );
            attivitaTableView.setItems(list);
            columnAttivitaId.setCellValueFactory( new PropertyValueFactory<Attivita, Integer>("id"));
            columnNomeAttivita.setCellValueFactory(new PropertyValueFactory<Attivita, String>("attivita"));
            columnLuogo.setCellValueFactory(new PropertyValueFactory<Attivita, String>("luogo"));

        } else if(event.getSource() == bottonePreferenze){
            adminTitleLabel.setText("Preferenze");
            anchorPanePreferenze.toFront(); //pannello attivita di fronte

            adminMessageLabel.setText("In questa sezione si ha la possibilita' di visualizzare le preferenze dell'utente selezionato. \n Per visualizzare le preferenze dell'utente, inserire il suo id nell'apposito spazio dedicato, poi premere il bottone OK");



        } else if(event.getSource() == bottoneSettings){
            //gestione impostazioni
            adminTitleLabel.setText("Settings");
            anchorPaneSettings.toFront();

            adminMessageLabel.setText("In questa sezione si ha la possibilita' di attivare il bot di telegram.");
        }
    }

    //-------------------------------------GENRICO STRINGA TO VETTORE--------------------------------------
    //Lo uso quando in una Text Box devo prendere una parte di testo e inserirlo in un file json
    public int[] stringToVect(String string){
        String[] stringV = string.split(" ");//string.split --> spazio tra gli elementi inseriti, scriverli per forza cosi sempre !!!
        int[] intV = new int[stringV.length];
        for(int i = 0; i < intV.length; i++){
            try{
                intV[i] = Integer.parseInt(stringV[i]);
            } catch(NumberFormatException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return intV;
    }

    //-------------------------------------GENRICO STRINGA TO INTERO--------------------------------------
    //Lo uso quando in una Text Box devo prendere una parte di testo e inserirlo in un file json
    public int stringToInt(String string){
        int intV = -1;
        try{
            intV = Integer.parseInt(string);
        } catch(NumberFormatException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        return intV;
    }

    //-------------------------------------GENRICO INTERO TO STRING--------------------------------------
    //Lo uso quando in una Text Box devo prendere una parte di testo dal file json e inserirlo nella text box
    public String intToString(int[] intV){
        String stringV = "";
        for(int i = 0; i < intV.length; i++){
            if(i ==  intV.length-1){
                stringV += intV[i];
            } else {
                stringV += intV[i] + " ";
            }
        }
        return stringV;
    }

    //--------------------------------STAMPA DESCRIZIONE DEI WORKSPACE--------------------------------------
    public void descrizioneWorkspace(MouseEvent mouseEvent) throws FileNotFoundException {
        FileReader reader = new FileReader("Workspace.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Workspace[] workspaceFromJson = gson.fromJson(reader, Workspace[].class);

        int idWorkspace = workspaceTableView.getFocusModel().getFocusedItem().id;
        //System.out.println(idWorkspace + " "); //controllo per vedere cosa seleziona

        for(int i = 0; i < workspaceFromJson.length; i++){
            if(idWorkspace == workspaceFromJson[i].id){
                adminMessageLabel.setText(workspaceFromJson[i].descrizioneW + " ");
            }
        }
    }

    //--------------------------------STAMPA DESCRIZIONE DEI WORKSPACE--------------------------------------
    public void descrizioneAttivita(MouseEvent mouseEvent) throws FileNotFoundException{
        FileReader reader = new FileReader("Attivita.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Attivita[] attivitaFromJson = gson.fromJson(reader, Attivita[].class);

        int idAttivita = attivitaTableView.getFocusModel().getFocusedItem().id;
        //System.out.println(idAttivita + " "); //controllo per vedere cosa seleziona

        for(int i = 0; i < attivitaFromJson.length; i++){
            if(idAttivita == attivitaFromJson[i].id){
                adminMessageLabel.setText(attivitaFromJson[i].luogo + "\n" + attivitaFromJson[i].descrizioneA);
            }
        }
    }

    //--------------------------------STAMPA DESCRIZIONE PREFERENZE--------------------------------------
    public void selezionaPreferenze(ActionEvent event) throws FileNotFoundException{
        if(event.getSource() == idUtenteOkButton){
            FileReader reader = new FileReader("Preferenze.json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            Preferenze[] preferenzeFromJson = gson.fromJson(reader, Preferenze[].class);

            int idFromText = stringToInt(idUtentePreferenze.getText()); //conversione da string a intero, box e' stringa e ho bisogno di intero

            for(int i = 0; i < preferenzeFromJson.length; i++){
                if(idFromText == preferenzeFromJson[i].idUtente){
                    textAreaPreferenze.setText(preferenzeFromJson[i].preferenza);
                    textAreaPreferenze.setEditable(false); //disabilita qualsiasi azione dell'utente nel testo

                }
            }
        }
    }

    //--------------------------------------- AREA DI AGGIUNTA DATI--------------------------------------

    //----------------------CONTROLLO GENERICO DELLO USERNAME: controllo esistenza dello username-----------------
    public boolean controlloUsernameAdd(String username, Utente[] personFromJson ){
        boolean trovato = false;
        for(int i=0; i<personFromJson.length; i++){
            if(username.equals(personFromJson[i].username)){
                trovato = true;
            }
        }
        return trovato;
    }

    //--------------------CONTROLLO GENERICO ID-TELEGRAM: controllo esistenza telegramId-----------------------------
    public boolean controlloTelegramIdAdd(String telegramId, Utente[] personFromJson){
        boolean telegram = false;
        for(int i = 0; i < personFromJson.length; i++){
            if(telegramId.equals("")){
                telegram = false;
            } else if(telegramId.equals(personFromJson[i].telegramId)){
                telegram = true;
            }
        }
        return telegram;
    }

    //-------------------------AGGIUNGI UTENTE: fase di aggiunta dell'utente------------------------------
    public void aggiungiUtenteUserPane(ActionEvent event) throws IOException{
        if(event.getSource() == aggiungiUserButtonToPage) {
            //System.out.println("Controllo aggiungiUserButtonToPage");

            adminTitleLabel.setText("USER ADD");
            addUserUsernameField.clear();
            addUserTelegramIdField.clear();
            addUserPasswordField.clear();
            anchorAggiungiUser.toFront();
            userAddMessageLabel.setText("");


            adminMessageLabel.setText("Area di aggiunta dell'utente. \n" + "Se non disponi del suo telegramId scrivi //, l'utente non potra' usufruire del bot" );

        }
        if(event.getSource() == aggiungiUserButtonPage){

            FileReader reader = new FileReader("Utenti.json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            try{
                Utente[] personFromJson = gson.fromJson(reader, Utente[].class);
                boolean esistente = true;
                //controlliamo se l'utente esiste già
                if(controlloUsernameAdd(addUserUsernameField.getText(), personFromJson)){
                    userAddMessageLabel.setText("L'username "+addUserUsernameField.getText()+" e' gia' in uso");
                    esistente=true;
                } else if(controlloTelegramIdAdd(addUserTelegramIdField.getText(), personFromJson)){
                    userAddMessageLabel.setText("L'Id Telegram di "+addUserUsernameField.getText()+" e' gia' in uso");
                    esistente=true;
                } else if(addUserUsernameField.getText().isBlank() == true){
                    userAddMessageLabel.setText("Username mancante");
                    esistente=true;
                } else {
                    if(addUserPasswordField.getText().isBlank() == true){
                        userAddMessageLabel.setText("Password mancante");
                        esistente=true;
                    }else if(addUserUsernameField.getText().isBlank() == false /*&& addUserTelegramIdField.getText().isBlank() == false*/ && addUserPasswordField.getText().isBlank() == false){
                        esistente=false;
                        userAddMessageLabel.setText("L'utente "+addUserUsernameField.getText()+" e' stato inserito.");
                        paneUser.toFront(); //Cambio schermata
                    }
                }

                //SE NON ESISTE, ANDIAMO A CREARE L'UTENTE PER POI REGISTRARLO
                if(esistente==false){
                    Type type = new TypeToken<List<Utente>>(){}.getType();
                    FileReader newReader =new FileReader("Utenti.json");
                    List<Utente> personList =gson.fromJson(newReader, type);
                    newReader.close();
                    if(null==personList) {
                        personList=new ArrayList<>();
                    }

                    int id=0; //con questa variabile controlliamo tutti gli id già in uso ed assegnamo il primo id libero al nuovo utente
                    for(int i=0; i<personFromJson.length; i++){
                        if(personFromJson[i].getId()==id){
                            id++;
                        }else{
                            break;
                        }
                    }
                    personList.add(new Utente(id, addUserUsernameField.getText(), addUserTelegramIdField.getText(), addUserPasswordField.getText(), false));
                    FileWriter newWriter =new FileWriter("Utenti.json");
                    gson.toJson(personList, newWriter);
                    newWriter.close();
                    userAddMessageLabel.setText("L'utente "+addUserUsernameField.getText()+" e' stato inserito correttamente");
                }

            }catch (Exception e){
                userAddMessageLabel.setText("File non trovato. ");
                e.printStackTrace();
                e.getCause();
            }
        }
    }

    //-------------------------AGGIUNGI WORKSPACE: fase di aggiunta dei workspace------------------------------

    //------------------CONTROLLO WORKSPACE: fase di controllo esistenza nome workspace----------------
    public boolean controlloWorkspaceAdd(String workspace, Workspace[] workspaceFromJson ){
        boolean trovatoW = false;
        for(int i=0; i<workspaceFromJson.length; i++){
            if(workspace.equals(workspaceFromJson[i].workspace)){
                trovatoW = true;
            }
        }
        return trovatoW;
    }

    //--------------------------------AGGIUNGI WORKSPACE: fase di aggiunta workspace---------------------
    public void aggiungiWorkspace(ActionEvent event) throws FileNotFoundException{
        if(event.getSource() == aggiungiWorkspaceButtontoPage){
            System.out.println("Controllo aggiungiWorkspaceButtontoPage");

            adminTitleLabel.setText("WORKSPACE ADD");
            workspaceAggiungiWorkspace.clear();
            idAttivitaAggiungiWorkspace.clear();
            testoAggiungiWorkspace.clear();
            anchorAggiungiWorkspace.toFront();
            workspaceAddMessageLabel.setText("");

            adminMessageLabel.setText("Area di aggiunta dei workspace. \n" + "Scrivi solamente gli id attivita' esistenti e con gli spazi (Es. 1 2 3 4)" );
        }

        if(event.getSource() == aggiungiWorkspaceButton){

            FileReader reader = new FileReader("Workspace.json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            try{
                Workspace[] workspaceFromJson = gson.fromJson(reader, Workspace[].class);
                boolean esistente = true;
                //controlliamo se l'utente esiste già
                if(controlloWorkspaceAdd(workspaceAggiungiWorkspace.getText(), workspaceFromJson)){
                    workspaceAddMessageLabel.setText("Il nome workspace "+workspaceAggiungiWorkspace.getText()+" e' gia' in uso");
                    esistente=true;
                } else if(workspaceAggiungiWorkspace.getText().isBlank() == true){
                    workspaceAddMessageLabel.setText("Workspace mancante");
                    esistente=true;
                } else if(idAttivitaAggiungiWorkspace.getText().isBlank() == true){
                    workspaceAddMessageLabel.setText("Id attivita' mancante");
                    esistente=true;
                } else if(testoAggiungiWorkspace.getText().isBlank() == true){
                    workspaceAddMessageLabel.setText("Breve Descrizione mancante ");
                    esistente=true;
                }
                else {
                    if(workspaceAggiungiWorkspace.getText().isBlank() == false && idAttivitaAggiungiWorkspace.getText().isBlank() == false && testoAggiungiWorkspace.getText().isBlank() == false){
                        esistente=false;
                        workspaceAddMessageLabel.setText("Workspace "+workspaceAggiungiWorkspace.getText()+" inserito correttamente.");
                        paneWorkspace.toFront(); //Cambio schermata
                    }
                }

                //SE NON ESISTE, ANDIAMO A CREARE il WORKSPACE PER POI REGISTRARLO
                if(esistente==false){
                    Type type = new TypeToken<List<Workspace>>(){}.getType();
                    FileReader newReader =new FileReader("Workspace.json");
                    List<Workspace> workspaceList =gson.fromJson(newReader, type);
                    newReader.close();
                    if(null==workspaceList) {
                        workspaceList=new ArrayList<>();
                    }

                    int id=0; //con questa variabile controlliamo tutti gli id già in uso ed assegnamo il primo id libero al nuovo workspace
                    for(int i=0; i<workspaceFromJson.length; i++){
                        if(workspaceFromJson[i].getId()==id){
                            id++;
                        }else{
                            break;
                        }
                    }

                    workspaceList.add(new Workspace(id, workspaceAggiungiWorkspace.getText(), testoAggiungiWorkspace.getText(), stringToVect(idAttivitaAggiungiWorkspace.getText())));
                    FileWriter newWriter =new FileWriter("Workspace.json");
                    gson.toJson(workspaceList, newWriter);
                    newWriter.close();
                    workspaceAddMessageLabel.setText("Workspace "+workspaceAggiungiWorkspace.getText()+" inserito correttamente");
                }

            }catch (Exception e){
                workspaceAddMessageLabel.setText("File non trovato. ");
                e.printStackTrace();
                e.getCause();
            }
        }

    }

    //-------------------------AGGIUNGI ATTIVITA: fase di aggiunta dei workspace------------------------------

    //---------CONTROLLO ATTIVITA: fase di controllo se nome dell'attivita' e' esistente oppure no ------
    public boolean controlloAttivitaAdd(String attivita, Attivita[] attivitaFromJson ){
        boolean trovatoA = false;
        for(int i=0; i<attivitaFromJson.length; i++){
            if(attivita.equals(attivitaFromJson[i].attivita)){
                trovatoA = true;
            }
        }
        return trovatoA;
    }

    //AGGIUNGI ATTIVITA: fase di aggiunta dell'attivita tramite bottone dedicato nella pagina apposita
    public void aggiungiAttivita(ActionEvent event) throws FileNotFoundException {
        if (event.getSource() == aggiungiAttivitaButtonToPage) {
            System.out.println("Controllo aggiungiAttivitaButtonToPage");

            adminTitleLabel.setText("ATTIVITA' ADD");
            attivitaAddNomeAttivita.clear();
            attivitaAddLuogo.clear();
            attivitaAddDescrizione.clear();
            anchorAggiungiAttivita.toFront();
            attivitaAddMessageLabel.setText("");


            adminMessageLabel.setText("Area di aggiunta delle attivita'. \n" + "Scrivi solamente luoghi attendibili" );
        }

        if (event.getSource() == aggiungiAttivitaButton) {

            FileReader reader = new FileReader("Attivita.json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            try {
                Attivita[] attivitaFromJson = gson.fromJson(reader, Attivita[].class);
                boolean esistente = true;
                //controlliamo se l'attivoita' esiste già
                if (controlloAttivitaAdd(attivitaAddNomeAttivita.getText(), attivitaFromJson)) {
                    attivitaAddMessageLabel.setText("Il nome dell'attivita' " + attivitaAddNomeAttivita.getText() + " e' gia' in uso");
                    esistente = true;
                } else if (attivitaAddNomeAttivita.getText().isBlank() == true) {
                    attivitaAddMessageLabel.setText("Nome attivita' mancante");
                    esistente = true;
                } else if (attivitaAddLuogo.getText().isBlank() == true) {
                    attivitaAddMessageLabel.setText(" Luogo mancante");
                    esistente = true;
                } else if (attivitaAddDescrizione.getText().isBlank() == true) {
                    attivitaAddMessageLabel.setText("Breve Descrizione mancante ");
                    esistente = true;
                } else {
                    if (attivitaAddNomeAttivita.getText().isBlank() == false && attivitaAddLuogo.getText().isBlank() == false && attivitaAddDescrizione.getText().isBlank() == false) {
                        esistente = false;
                        attivitaAddMessageLabel.setText("Attivita' " + attivitaAddNomeAttivita.getText() + " inserita correttamente.");
                        paneAttivita.toFront(); //Cambio schermata
                    }
                }

                //SE NON ESISTE, ANDIAMO A CREARE L'ATTIVITA' PER POI REGISTRARLA
                if (esistente == false) {
                    Type type = new TypeToken<List<Attivita>>() {
                    }.getType();
                    FileReader newReader = new FileReader("Attivita.json");
                    List<Attivita> attivitaList = gson.fromJson(newReader, type);
                    newReader.close();
                    if (null == attivitaList) {
                        attivitaList = new ArrayList<>();
                    }

                    int id = 0; //con questa variabile controlliamo tutti gli id già in uso ed assegnamo il primo id libero alla nuova attivita'
                    for (int i = 0; i < attivitaFromJson.length; i++) {
                        if (attivitaFromJson[i].getId() == id) {
                            id++;
                        } else {
                            break;
                        }
                    }

                    attivitaList.add(new Attivita(id, attivitaAddNomeAttivita.getText(), attivitaAddLuogo.getText(), attivitaAddDescrizione.getText(), stringToVect(idAttivitaAggiungiWorkspace.getText())));
                    FileWriter newWriter = new FileWriter("Attivita.json");
                    gson.toJson(attivitaList, newWriter);
                    newWriter.close();
                    attivitaAddMessageLabel.setText("Attivita' " + attivitaAddNomeAttivita.getText() + " inserita correttamente.");
                }

            } catch (Exception e) {
                attivitaAddMessageLabel.setText("File non trovato. ");
                e.printStackTrace();
                e.getCause();
            }
        }
    }

    //---------------------------------------- AREA DI MODIFICA DATI------------------------------------------------
    //----------------------------------------AREA DI MODIIFCA USER ----------------------------------------
    public int idUfficialeUser;
    public void modificaUser(ActionEvent event) throws IOException {
        //passaggio da elenco user a pagina modifica
        if (event.getSource() == modifcaUserToPane) {

            adminTitleLabel.setText("USER MODIFICA");
            modificaNomeUser.clear();
            modificaTelegramIdUser.clear();
            modificaUserPassword.clear();
            anchorModificaUser.toFront();
            modificaUserMessageLabel.setText("");


            adminMessageLabel.setText("Area di modifica dell'Utente '. \n" + "Inserisci l'ID dell'utente che si vuole modificare, poi premi OK affianco. \n" +
                    "Appariranno le voci attualmente in uso, possono essere modificate e poi sovrascritte" );
        }

        //controllo dell'id per corrispondere i dati all'id
        if(event.getSource() == modificaUserOKButton1){

            FileReader reader = new FileReader("Utenti.json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Utente[] utenteFromJson = gson.fromJson(reader, Utente[].class);

            idUtenteModifica = stringToInt(modificaIdUser.getText());

            boolean trovato = false;
            for(int i = 0; i < utenteFromJson.length; i++){
                if(idUtenteModifica == (utenteFromJson[i].id)){
                    modificaUserMessageLabel.setText("Id inserito correttamente");

                    trovato = true;

                    idUfficialeUser = utenteFromJson[i].id;

                    modificaNomeUser.setText(utenteFromJson[i].username);
                    modificaTelegramIdUser.setText(utenteFromJson[i].telegramId);
                    modificaUserPassword.setText(utenteFromJson[i].pw);

                    adminMessageLabel.setText("Scrivere le voci che si vogliono modificare, le altre non toccarle per evitare ulteriori errori" );
                }
            }

            if(trovato == false){
                modificaUserMessageLabel.setText("Scrivi l'id correttamente");
            }
        }

        //modifica e sovra scrittura dei nuovi dati
        if(event.getSource() == modificaUserButton){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type type = new TypeToken<List<Utente>>(){}.getType();
            FileReader newReader =new FileReader("Utenti.json");
            List<Utente> utenteList =gson.fromJson(newReader, type);
            newReader.close();
            if(null==utenteList) {
                utenteList=new ArrayList<>();
            }

            boolean trovato = false;
            for(int i = 0; i < utenteList.size(); i++){ //ciclo per controllare esistenza dell'username
                if(modificaNomeUser.getText().equals(utenteList.get(i).username) && i!=idUtenteModifica){
                    modificaUserMessageLabel.setText("L'Username e' gia' in uso");
                    trovato = true;
                    break;
                }

                if(modificaTelegramIdUser.getText().equals((utenteList.get(i).telegramId)) && i!=idUtenteModifica && !modificaTelegramIdUser.getText().equals("")){
                    modificaUserMessageLabel.setText("L'Id Telegram e' gia' in uso");
                    trovato = true;
                    break;
                }
            }


            for(int i = 0; i < utenteList.size(); i++){ //ciclo per controllare esistenza dell'id telegram
                if(trovato == false){
                    if(idUfficialeUser == utenteList.get(i).id){
                        utenteList.get(i).username =  modificaNomeUser.getText();
                        utenteList.get(i).telegramId = modificaTelegramIdUser.getText();
                        utenteList.get(i).pw = modificaUserPassword.getText();
                        System.out.println("User modificato correttamente");

                        FileWriter newWriter =new FileWriter("Utenti.json");
                        gson.toJson(utenteList, newWriter);
                        newWriter.close();
                        modificaUserMessageLabel.setText("Utente "+modificaNomeUser.getText()+" modificato correttamente");
                        adminTitleLabel.setText("USER");
                        paneUser.toFront();
                        break;
                    }
                } else {
                    modificaUserMessageLabel.setText("Dati gia' esistenti. Riprova.");
                }
            }


        }
    }

    //----------------------------------------AREA DI MODIIFCA WORKSPACE ----------------------------------------
    public int idUfficialeWorkspace;
    public void modificaWorkspace(ActionEvent event) throws IOException {
        //passaggio da elenco workspace a pagina modifica
        if (event.getSource() == modificaWorkspacetoPane) {

            adminTitleLabel.setText("WORKSPACE MODIFICA"); //set del titolo
            workspaceModificaWorkspace.clear(); //pulizia dei campi da eventuali dati precedenti
            IdAttivitaModificaWorkspace.clear();
            descrizioneModificaWorkspace.clear();
            modificaWorkspaceMessageLabel.setText("");

            //il campo del messaggio di avviso ho preferito lasciarlo.
            anchorModificaWorkspace.toFront(); //pannello modifica to fronte

            adminMessageLabel.setText("Area di modifica dei workspace'. \n" + "Inserisci l'ID del workspace che si vuole modificare, poi premi OK affianco. \n" +
                    "Appariranno le voci attualmente in uso, possono essere modificate e poi sovrascritte" );
        }

        //controllo dell'id per estrapolare i dati inerenti all'id
        if(event.getSource() == okButtonModificaWorkspace){
            //controllo che avviene quando si preme il bottone OK

            FileReader reader = new FileReader("Workspace.json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Workspace[] workspaceFromJson = gson.fromJson(reader, Workspace[].class);

            idWorkspaceModifica = stringToInt(idModificaWorkspace.getText()); //intero per il controllo dell'id nel primo if

            boolean trovato = false;
            for(int i = 0; i < workspaceFromJson.length; i++){
                if(idWorkspaceModifica == (workspaceFromJson[i].id)){
                    modificaWorkspaceMessageLabel.setText("Id inserito correttamente"); //semplice messaggio di verifica

                    trovato = true;

                    idUfficialeWorkspace = workspaceFromJson[i].id; //settaggio dell'id ufficiale da json alla variabile dichiarata in alto fuori dal metodo

                    workspaceModificaWorkspace.setText(workspaceFromJson[i].workspace);//impostazione dei dati precedenti nelle singole caselle
                    IdAttivitaModificaWorkspace.setText(intToString(workspaceFromJson[i].attivita));
                    descrizioneModificaWorkspace.setText(workspaceFromJson[i].descrizioneW);

                    adminMessageLabel.setText("Scrivere le voci che si vogliono modificare \n" + " Le altre si prega di non modificarle ale fine di evitare ulteriori errori" );
                }
            }

            if(trovato == false){
                modificaWorkspaceMessageLabel.setText("Scrivi un id valido"); //messaggio in caso di errore
            }
        }

        //modifica e sovra scrittura dei nuovi dati
        if(event.getSource() == modificaWorkspaceButton){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type type = new TypeToken<List<Workspace>>(){}.getType();
            FileReader newReader =new FileReader("Workspace.json");
            List<Workspace> workspaceList =gson.fromJson(newReader, type);
            newReader.close();
            if(null==workspaceList) {
                workspaceList=new ArrayList<>();
            }

            //scorrimento della lista dei workspace
            boolean trovato = false;
            for(int i = 0; i < workspaceList.size(); i++){
                if(workspaceModificaWorkspace.getText().equals(workspaceList.get(i).workspace) && i!=idWorkspaceModifica){
                    modificaUserMessageLabel.setText("Workspace gia' in uso");
                    trovato = true;
                    break;
                }
            }

            for(int i = 0; i < workspaceList.size(); i++){
                if(trovato == false){
                    if(idUfficialeWorkspace == workspaceList.get(i).id){
                        workspaceList.get(i).workspace =  workspaceModificaWorkspace.getText(); //sovra scrittura dei nuovi dati negli appositi spazi nel file json
                        workspaceList.get(i).descrizioneW = descrizioneModificaWorkspace.getText();
                        workspaceList.get(i).attivita = stringToVect(IdAttivitaModificaWorkspace.getText());
                        System.out.println("Workspace modificato correttamente");
                        FileWriter newWriter =new FileWriter("Workspace.json");
                        gson.toJson(workspaceList, newWriter);
                        newWriter.close();
                        workspaceAddMessageLabel.setText("Workspace "+workspaceModificaWorkspace.getText()+" modificato correttamente");
                        adminTitleLabel.setText("WORKSPACE");
                        paneWorkspace.toFront();
                        break;
                    }
                } else {
                    modificaWorkspaceMessageLabel.setText("Dati gia' esistenti. Riprova.");
                }
            }

        }
    }

    //----------------------------------------AREA DI MODIIFCA ATTIVITA ----------------------------------------
    public int idUfficialeA;
    public void modificaAttivita(ActionEvent event) throws IOException {
        //passaggio da elenco attivita a pagina modifica
        if (event.getSource() == modificaAttivitatoPane) {


            adminTitleLabel.setText("ATTIVITA' MODIFICA");
            attivitaModificaAttivita.clear();
            descrizioneModificaAttivita.clear();
            luogoModificaAttivita.clear();
            anchorModificaAttivita.toFront();
            attivitaModificaMessageLabel.setText("");


            adminMessageLabel.setText("Area di modifica delle attivita'. \n" + "Inserisci l'ID dell'attivita' che si vuole modificare, poi premi OK affianco. \n" +
                    "Appariranno le voci attualmente in uso, possono essere modificate e poi sovrascritte" );
        }

        //controllo dell'id per estrapolare i dati inerenti all'id
        if(event.getSource() == modificaAttivitaOKButton){

            FileReader reader = new FileReader("Attivita.json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Attivita[] attivitaFromJson = gson.fromJson(reader, Attivita[].class);

            idAttivitaModifica = stringToInt(idUfficialeAttivita.getText()); //id fittizio e temporaneo solamente per il controllo della condizione

            boolean trovato = false;
            for(int i = 0; i < attivitaFromJson.length; i++){
                if(idAttivitaModifica == (attivitaFromJson[i].id)){
                    attivitaModificaMessageLabel.setText("Id inserito correttamente");

                    trovato = true;

                    idUfficialeA = attivitaFromJson[i].id; //settaggio dell'id ufficiale da json alla variabile dichiarata in alto fuori dal metodo

                    attivitaModificaAttivita.setText(attivitaFromJson[i].attivita);
                    luogoModificaAttivita.setText(attivitaFromJson[i].luogo);
                    descrizioneModificaAttivita.setText(attivitaFromJson[i].descrizioneA);

                    adminMessageLabel.setText("Scrivere le voci che si vogliono modificare, le altre non toccarle per evitare ulteriori errori" );
                }
            }

            if(trovato == false){
                attivitaModificaMessageLabel.setText("Scrivi un id valido");
            }
        }

        //modifica e sovra scrittura dei nuovi dati
        if(event.getSource() == modificaAttivitaButton){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type type = new TypeToken<List<Attivita>>(){}.getType();
            FileReader newReader =new FileReader("Attivita.json");
            List<Attivita> attivitaList =gson.fromJson(newReader, type);
            newReader.close();
            if(null==attivitaList) {
                attivitaList=new ArrayList<>();
            }

            boolean trovato = false;
            for(int i = 0; i < attivitaList.size(); i++){
                if(attivitaModificaAttivita.getText().equals(attivitaList.get(i).attivita) && i!=idAttivitaModifica){
                    modificaUserMessageLabel.setText("Attivita' gia' in uso");
                    trovato = true;
                    break;
                }
            }

            for(int i = 0; i < attivitaList.size(); i++){
                if(trovato == false){
                    if(idUfficialeA == attivitaList.get(i).id){
                        attivitaList.get(i).attivita =  attivitaModificaAttivita.getText();
                        attivitaList.get(i).luogo = luogoModificaAttivita.getText();
                        attivitaList.get(i).descrizioneA = descrizioneModificaAttivita.getText();
                        System.out.println("Attivita' modificata correttamente");
                        FileWriter newWriter =new FileWriter("Attivita.json");
                        gson.toJson(attivitaList, newWriter);
                        newWriter.close();
                        attivitaModificaMessageLabel.setText("Attivita "+attivitaModificaAttivita.getText()+" modificata correttamente");
                        adminTitleLabel.setText("ATTIVITA'");
                        paneAttivita.toFront();
                        break;
                    }
                } else {
                    attivitaModificaMessageLabel.setText("Dati gia' esistenti. Riprova.");
                }
            }
        }
    }

    //----------------------------------------AREA DI MODIIFCA PREFERENZE ----------------------------------------
    public int idUfficialeP;
    public void modificaPreferenze(ActionEvent event) throws IOException{
        if (event.getSource() == modificaPreferenzeToPane) {

            adminTitleLabel.setText("PREFERENZE MODIFICA");
            idUtenteModificaPreferenze.clear();
            textAreaModificaPreferenze.clear();
            anchorPaneModificaPreferenze.toFront();
            modificaPreferenzeMessageLabel.setText("");

            adminMessageLabel.setText("Area di modifica delle Preferenze '. \n" + "Inserisci l'ID dell'utente del quale si vogliono modificare le preferenze, poi premi OK affianco. \n" +
                    "Appariranno le preferenze attualmente in uso, possono essere modificate e poi sovrascritte" );
        }

        //controllo dell'id per corrispondere i dati all'id
        if(event.getSource() == idUtenteModificaOkButton1){

            FileReader reader = new FileReader("Preferenze.json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Preferenze[] preferenzeFromJson = gson.fromJson(reader, Preferenze[].class);

            int idUtente = stringToInt(idUtenteModificaPreferenze.getText()); //avviene gia il cambio da string a intero tramite il metodo apposito

            boolean trovato = false;
            for(int i = 0; i < preferenzeFromJson.length; i++){
                if(idUtente == (preferenzeFromJson[i].idUtente)){
                    modificaPreferenzeMessageLabel.setText("Id inserito correttamente");

                    trovato = true;

                    idUfficialeP = preferenzeFromJson[i].idUtente;

                    textAreaModificaPreferenze.setText(preferenzeFromJson[i].preferenza);

                    modificaPreferenzeMessageLabel.setText("Testo inserito correttamente");
                    adminMessageLabel.setText("Scrivere le voci che si vogliono modificare, le altre non toccarle per evitare ulteriori errori" );
                }
            }

            if(trovato == false){
                modificaPreferenzeMessageLabel.setText("Scrivi un id esistente");
            }
        }

        //modifica e sovra scrittura dei nuovi dati
        if(event.getSource() == modificaPreferenze){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Type type = new TypeToken<List<Preferenze>>(){}.getType();
            FileReader newReader =new FileReader("Preferenze.json");
            List<Preferenze> preferenzeList =gson.fromJson(newReader, type);
            newReader.close();
            if(null==preferenzeList) {
                preferenzeList=new ArrayList<>();
            }

            for(int i = 0; i < preferenzeList.size(); i++){
                if(idUfficialeP == preferenzeList.get(i).idUtente){
                    preferenzeList.get(i).preferenza =  textAreaModificaPreferenze.getText();

                    break;
                }
            }

            FileWriter newWriter =new FileWriter("Preferenze.json");
            gson.toJson(preferenzeList, newWriter);
            newWriter.close();
            modificaUserPassword.setText("Preferenza "+idUtenteModificaPreferenze.getText()+" modificata correttamente");
            adminTitleLabel.setText("PREFERENZE");
            anchorPanePreferenze.toFront();
        }
    }


    //------------------------------------------REFRESH BUTTON----------------------------------------------------
    //refresh button and reload USER
    public void refreshUser(ActionEvent event) throws FileNotFoundException {
        if(event.getSource() == refreshButtonUser) {
            //gestione degli username
            adminTitleLabel.setText("User"); //setta il titolo nell'apposito label in alto a sinistra
            paneUser.toFront(); //pone in primo piano il pannello indicato(paneUser in questo caso)

            adminMessageLabel.setText("In questa sezione si ha la possibilita' di gestire gli utenti.");

            //inserimento dei dati nella tabella inerente agli USER
            FileReader reader = new FileReader("Utenti.json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            Utente[] personFromJson = gson.fromJson(reader, Utente[].class);
            LinkedList<UtenteTabella> personTabellaFromJson = new LinkedList<>();
            for (int i = 0; i < personFromJson.length; i++) {
                personTabellaFromJson.add(new UtenteTabella(personFromJson[i]));
            }
            ObservableList<UtenteTabella> list = FXCollections.observableArrayList(
                    personTabellaFromJson
                    /*
                    Dal sito di Oracle --> usano ObservableList per gestire le tableView e tableColumn
                    quindi tramite le classei Utnete...Workspace Prova ho convertito i dati e le variabili
                    al fine di essere lette nelle ObservableList
                     */
            );
            userTableView.setItems(list); //settaggio elementi nella tabella
            columnUserId.setCellValueFactory(new PropertyValueFactory<Utente, Integer>("id")); //settaggio elementi nelle colonne
            columnUsername.setCellValueFactory(new PropertyValueFactory<Utente, String>("username"));
            columnTelegramId.setCellValueFactory(new PropertyValueFactory<Utente, String>("telegramId"));
            columnPassword.setCellValueFactory(new PropertyValueFactory<Utente, String>("pw"));
        }
    }

    //refresh button and reload ATTIVITA
    public void refreshAttivita(ActionEvent event) throws FileNotFoundException {
        if(event.getSource() == refreshButtonAttivita){
            //gestione delle attivita
            adminTitleLabel.setText("Attivita'");
            paneAttivita.toFront(); //pannello attivita di fronte

            adminMessageLabel.setText("In questa sezione si ha la possibilita' di gestire le singole attivita.");

            //inserimento dati nella tabella attivita
            FileReader reader = new FileReader("Attivita.json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            Attivita[] activityFromJson = gson.fromJson(reader, Attivita[].class);
            LinkedList<AttivitaTabella> activityProvaFromJson = new LinkedList<>();
            for(int i = 0; i < activityFromJson.length; i++){
                activityProvaFromJson.add(new AttivitaTabella(activityFromJson[i]));
            }
            ObservableList<AttivitaTabella> list = FXCollections.observableArrayList(
                    activityProvaFromJson
            );
            attivitaTableView.setItems(list);
            columnAttivitaId.setCellValueFactory( new PropertyValueFactory<Attivita, Integer>("id"));
            columnNomeAttivita.setCellValueFactory(new PropertyValueFactory<Attivita, String>("attivita"));
            columnLuogo.setCellValueFactory(new PropertyValueFactory<Attivita, String>("luogo"));

        }
    }

    //refresh button and reload WORKSPACE
    public void refreshWorkspace(ActionEvent event) throws IOException {
        if(event.getSource() == refreshButtonWorkspace){
            //gestione dei workspace
            adminTitleLabel.setText("Workspace");

            paneWorkspace.toFront();

            adminMessageLabel.setText("In questa sezione si ha la possibilita' di gestire i workspace.");

            //inserimento dati nella tabella attivita
            FileReader reader = new FileReader("Workspace.json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            Workspace[] workspaceFromJson = gson.fromJson(reader, Workspace[].class);
            LinkedList<WorkspaceTabella> workspaceProvaFromJson = new LinkedList<>();
            for(int i = 0; i < workspaceFromJson.length; i++){
                workspaceProvaFromJson.add(new WorkspaceTabella(workspaceFromJson[i]));
            }
            ObservableList<WorkspaceTabella> list = FXCollections.observableArrayList(
                    workspaceProvaFromJson

            );
            workspaceTableView.setItems(list);
            columnWorkspaceId.setCellValueFactory( new PropertyValueFactory<Workspace, Integer>("id"));
            columnWorkspace.setCellValueFactory(new PropertyValueFactory<Workspace, String>("workspace"));
            columnAttivita.setCellValueFactory(new PropertyValueFactory<Workspace, String>("attivita"));

        }
    }


    //---------------------------------------LOGOUT DALL'APPLICATIVO-------------------------------------------
    public void logoutButtonOnAction(ActionEvent event) throws IOException{
        Main m = new Main();
        m.changeScene("sample.fxml"); //ritorno da schermata admin a schermata login
    }

    //---------------------------------CHIUSURA DALL'APPLICATIVO---------------------------------------
    public void close(javafx.event.ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("!Stai per chiudere il programma!");
        alert.setContentText("Vuoi salvare prima di chiudere il programma?");

        if(alert.showAndWait().get() == ButtonType.OK){
            stage = (Stage) leftAdminVBox.getScene().getWindow();
            System.out.println(" ");
            System.out.println("Hai chiuso correttamente il programma e hai eseguito il logout");
            System.out.println(" ");
            stage.close();
        }
    }



}