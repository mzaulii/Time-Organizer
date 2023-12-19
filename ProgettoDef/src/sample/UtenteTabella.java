package sample;

import javafx.beans.property.SimpleStringProperty;

public class UtenteTabella {
    int id;
    SimpleStringProperty username;
    SimpleStringProperty telegramId;
    SimpleStringProperty pw;
    boolean tipo;


    public UtenteTabella(int id, String username, String telegramId, String pw, boolean tipo) {
        this.id=id;
        this.username=new SimpleStringProperty(username);
        this.telegramId= new SimpleStringProperty(telegramId);
        this.pw= new SimpleStringProperty(pw);
        this.tipo=tipo;

    }

    public UtenteTabella(Utente utente){
        /*
        questo metodo, utilizzando SimpleStringProperty invece che String, permette di andare ad inserire i dati
        nella Observable list presente in Administrator Controller --> tutto cio' per visualizzare i dati nella
        tabella
         */
        this.id = utente.id;
        this.username=new SimpleStringProperty(utente.username);
        this.telegramId= new SimpleStringProperty(utente.telegramId);
        this.pw= new SimpleStringProperty(utente.pw);
        this.tipo=utente.tipo;
    }

    public int getId(){
        return this.id;
    }
    public String getUsername(){
        return this.username.get();
    }
    public String getPw(){
        return this.pw.get();
    }
    public String getTelegramId(){
        return this.telegramId.get();
    }


}