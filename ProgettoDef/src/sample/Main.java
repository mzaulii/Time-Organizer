package sample;

import java.io.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent; //mouse editor
import javafx.stage.StageStyle; //stage style
/*
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
*/


public class Main extends Application {

    private double xOffset = 0; //dimensioni della finestra
    private double yOffset = 0;
    private static Stage stg;

    @Override
    public void start(Stage primaryStage) throws Exception{

        stg = primaryStage; //prima schermata

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml")); //upload schermata iniziale

        primaryStage.setOnCloseRequest(event -> {
            //forza la chiusura della pagina andando a richiamare il metodo close
            event.consume();
            close(primaryStage); //chiusura effettiva
        });

        Scene scene = new Scene(root); //scena

        primaryStage.initStyle(StageStyle.DECORATED.UNDECORATED); //blocco
        root.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show(); //mostra stage
    }

    //----------------------------------CHIUSURA DELL'APPLICATIVO---------------------------------------
    public void close(Stage stage) {
        //messaggio di allerta prima della chiusura
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("!Stai per chiudere il programma!");
        alert.setContentText("Vuoi salvare prima di chiudere il programma?");

        if(alert.showAndWait().get() == ButtonType.OK){
           //chiusura finale
            System.out.println(" ");
            System.out.println("Hai chiuso correttamente il programma e hai eseguito il logout");
            System.out.println(" ");
            stage.close();
        }
    }

    //--------------------------------------CAMBIO DI SCENA-------------------------------------------
    public void changeScene(String fxml) throws IOException{
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
        System.out.println("Scena cambiata correttamente");
    }

    public static void main(String[] args){
        launch(args);
    }
}
