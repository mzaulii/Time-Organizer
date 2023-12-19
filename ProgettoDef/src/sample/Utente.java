package sample;

public class Utente {
    int id;
    String username;
    String telegramId;
    String pw;
    boolean tipo;


    public Utente(int id, String username, String telegramId, String pw, boolean tipo) {
        this.id=id;
        this.username=username;
        this.telegramId = telegramId;
        this.pw=pw;
        this.tipo=tipo;

    }

    public int getId(){
        return this.id;
    }
    public String getUsername(){
        return this.username;
    }
    public String getPw(){
        return this.pw;
    }
    public String getTelegramId(){
        return this.telegramId;
    }

}