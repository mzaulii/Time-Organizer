package sample;

import javafx.beans.property.SimpleStringProperty;

public class AttivitaTabella {

    int id;
    SimpleStringProperty attivita;
    SimpleStringProperty luogo;
    SimpleStringProperty descrizioneA;
    int[] preferenze;

    public AttivitaTabella(int id, String attivita, String luogo, String descrizioneA, int[] preferenze) {
        this.id=id;
        this.attivita=new SimpleStringProperty(attivita);
        this.luogo= new SimpleStringProperty(luogo);
        this.descrizioneA = new SimpleStringProperty(descrizioneA);
        this.preferenze=preferenze;
    }

    public AttivitaTabella(Attivita attivita){
        /*
        questo metodo, utilizzando SimpleStringProperty invece che String, permette di andare ad inserire i dati
        nella Observable list presente in Administrator Controller --> tutto cio' per visualizzare i dati nella
        tabella
         */
        this.id = attivita.id;
        this.attivita=new SimpleStringProperty(attivita.attivita);
        this.luogo= new SimpleStringProperty(attivita.luogo);
        this.descrizioneA = new SimpleStringProperty(attivita.descrizioneA);
        this.preferenze= attivita.preferenze;
    }

    public int getId(){
        return this.id;
    }
    public String getAttivita() { return attivita.get(); }
    public String getLuogo() { return luogo.get(); }
}
