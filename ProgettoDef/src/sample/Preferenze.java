package sample;

public class Preferenze {
    int idUtente;
    String preferenza;

    public Preferenze(int idUtente, String preferenza) {
        this.idUtente =idUtente;
        this.preferenza=preferenza;
    }

    public int getIdUtente(){
        return this.idUtente;
    }
}
