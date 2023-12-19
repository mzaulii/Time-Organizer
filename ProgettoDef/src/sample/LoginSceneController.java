package sample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*; //bottoni e testo
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.FileReader;
import java.io.IOException;

/*
import javafx.fxml.Initializable;
import java.io.FileNotFoundException;
import java.util.Scanner;
 */

public class LoginSceneController {

    @FXML
    private AnchorPane upperPaneLogin;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    Stage stage;
    public static String nomeUtente;
    Main m = new Main(); //Oggetto Cambio della schermata


    //---------------------------------AZIONE DEL BOTTONE LOGIN---------------------------------------
    public void loginButtonOnAction(javafx.event.ActionEvent event) throws IOException {

        FileReader reader = new FileReader("Utenti.json"); //reader file json
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try{ //Controllo dati utente
            Utente[] personFromJson = gson.fromJson(reader, Utente[].class);
            for(int i=0; i<personFromJson.length; i++){
                if(usernameField.getText().equals(personFromJson[i].username)){
                    if(passwordField.getText().equals(personFromJson[i].pw)){
                        loginMessageLabel.setText("Ciao "+personFromJson[i].username+", hai effettuato l'accesso!");

                        if(personFromJson[i].tipo == true){ //CAMBIO SCENA
                            m.changeScene("Administrator.fxml");
                        } else if(personFromJson[i].tipo == false){
                            m.changeScene("User.fxml");
                            nomeUtente = usernameField.getText();
                        } else {
                            loginMessageLabel.setText("Nessun tipo assegnato all'utente");
                        }
                        break;

                    } else if(passwordField.getText().isBlank() == true){
                        loginMessageLabel.setText("Password mancante");
                    } else {
                        loginMessageLabel.setText("Password non valida");
                    }
                    break;
                } else if(usernameField.getText().isBlank() == true){
                    loginMessageLabel.setText("Username mancante");
                } else {
                    loginMessageLabel.setText("Username non valido.");
                }
            }
        }catch (Exception e){
            loginMessageLabel.setText("File non trovato. ");
            e.printStackTrace();
            e.getCause();
        }
    }

    //-------------------------------CHIUSURA DELLA FINESTRA DELL'APPLICATIVO------------------------------
    public void close(javafx.event.ActionEvent actionEvent) {
        //messaggio di allerta prima della chiusura
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("!Stai per chiudere il programma!");
        alert.setContentText("Vuoi salvare prima di chiudere il programma?");

        if(alert.showAndWait().get() == ButtonType.OK){
            stage = (Stage) upperPaneLogin.getScene().getWindow();
            System.out.println(" ");
            System.out.println("Hai chiuso correttamente il programma e hai eseguito il logout");
            System.out.println(" ");
            stage.close();
        }
    }

    //---------------------------------AZIONE DEL BOTTONE REGISTER---------------------------------------
    public void registerPaneButtonOnAction(ActionEvent actionEvent) throws IOException{
        m.changeScene("Register.fxml");
    }
}