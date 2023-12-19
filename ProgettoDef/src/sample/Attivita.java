package sample;

public class Attivita {

    int id;
    String attivita;
    String luogo;
    String descrizioneA;
    int[] preferenze;

    public Attivita(int id, String attivita, String luogo, String descrizioneA, int[] preferenze) {
        this.id=id;
        this.attivita=attivita;
        this.luogo=luogo;
        this.descrizioneA = descrizioneA;
        this.preferenze=preferenze;
    }

    public int getId(){
        return this.id;
    }

}
