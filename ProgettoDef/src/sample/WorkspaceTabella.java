package sample;

import javafx.beans.property.SimpleStringProperty;

public class WorkspaceTabella {
    int id;
    SimpleStringProperty workspace;
    SimpleStringProperty descrizioneW;
    int[] attivita;

    public WorkspaceTabella(int id, String workspace, String descrizioneW, int[] attivita) {
        this.id=id;
        this.workspace = new SimpleStringProperty(workspace);
        this.descrizioneW = new SimpleStringProperty(descrizioneW);
        this.attivita=attivita;
    }

    public WorkspaceTabella(Workspace workspace){
        /*
        questo metodo, utilizzando SimpleStringProperty invece che String, permette di andare ad inserire i dati
        nella Observable list presente in Administrator Controller --> tutto cio' per visualizzare i dati nella
        tabella
         */
        this.id = workspace.id;
        this.workspace = new SimpleStringProperty(workspace.workspace);
        this.descrizioneW = new SimpleStringProperty(workspace.workspace);
        this.attivita = workspace.attivita;
    }

    public int getId(){
        return this.id;
    }
    public String getWorkspace() { return workspace.get(); }

    public String getAttivita() {
        String elencoAttivita = "";
        for(int i = 0; i < attivita.length; i++){
            elencoAttivita += attivita[i] + " "; //questo ciclo --> perche' altrimenti non puo' stamparmi nulla essendo array
        }
        return elencoAttivita;
    }
}

