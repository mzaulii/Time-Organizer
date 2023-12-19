package sample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RegisterController {
    @FXML
    private AnchorPane upperPaneRegister;
    @FXML
    private TextField registerUsernameField;
    @FXML
    private TextField registerTelegramUsernameField;
    @FXML
    private PasswordField registerPasswordField;
    @FXML
    private Label registerMessageLabel;
    @FXML
    private Button closeButton;

    Main m = new Main();
    Stage stage;


    //---------------------------------AZIONE DEL BOTTONE BACK---------------------------------------
    public void backButtonOnAction(ActionEvent event) throws IOException{
        m.changeScene("sample.fxml");
    }

    //CONTROLLO DELLO USERNAME
    public boolean controlloUsername(String username, Utente[] personFromJson ){
        boolean trovato = false;
        for(int i=0; i<personFromJson.length; i++){
            if(username.equals(personFromJson[i].username)){
                trovato = true;
            }
        }
        return trovato;
    }

    //CONTROLLO TELEGRAM ID
    public boolean controlloTelegramId(String telegramId, Utente[] personFromJson){
        boolean telegram = false;
        for(int i = 0; i < personFromJson.length; i++){

           if(telegramId.equals(personFromJson[i].telegramId)){
                telegram = true;
                break;
           }
        }
        return telegram;
    }

    //---------------------------------AZIONE DEL BOTTONE REGISTER---------------------------------------
    public void registerButtonOnAction(ActionEvent actionEvent) throws IOException {
        FileReader reader = new FileReader("Utenti.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try{
            Utente[] personFromJson = gson.fromJson(reader, Utente[].class);
            boolean esistente=true;
            if(controlloUsername(registerUsernameField.getText(), personFromJson)){ //controlliamo se l'utente esiste già
                registerMessageLabel.setText("L'username "+registerUsernameField.getText()+" e' gia' in uso");
                esistente=true;
            } else if(controlloTelegramId(registerTelegramUsernameField.getText(), personFromJson)){
                registerMessageLabel.setText("L'Id Telegram di "+registerUsernameField.getText()+" e' gia' in uso");
                esistente=true;
            } else if(registerUsernameField.getText().isBlank() == true){
                registerMessageLabel.setText("Username mancante");
                esistente=true;
            }  else {
                if(registerPasswordField.getText().isBlank() == true){
                    registerMessageLabel.setText("Password mancante");
                    esistente=true;
                }else if(registerUsernameField.getText().isBlank() == false && registerPasswordField.getText().isBlank() == false){
                    esistente=false;
                    registerMessageLabel.setText("L'utente "+registerUsernameField.getText()+" e' stato inserito correttamente.");
                    m.changeScene("User.fxml");
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
                personList.add(new Utente(id, registerUsernameField.getText(), registerTelegramUsernameField.getText(), registerPasswordField.getText(), false));
                LoginSceneController.nomeUtente = registerUsernameField.getText(); //applico un nuovo nome alla variabile nomeUtente
                FileWriter newWriter =new FileWriter("Utenti.json");
                gson.toJson(personList, newWriter);
                newWriter.close();
                registerMessageLabel.setText("L'utente "+registerUsernameField.getText()+" e' stato inserito correttamente");
            }

        }catch (Exception e){
            registerMessageLabel.setText("File non trovato. ");
            e.printStackTrace();
            e.getCause();
        }
    }

    //-------------------------------CHIUSURA DELLA FINESTRA DELL'APPLICATIVO------------------------------
    public void close(javafx.event.ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("!Stai per chiudere il programma!");
        alert.setContentText("Vuoi salvare prima di chiudere il programma?");

        if(alert.showAndWait().get() == ButtonType.OK){
            stage = (Stage) upperPaneRegister.getScene().getWindow();
            System.out.println(" ");
            System.out.println("Hai chiuso correttamente il programma e hai eseguito il logout");
            System.out.println(" ");
            stage.close();
        }
    }

}