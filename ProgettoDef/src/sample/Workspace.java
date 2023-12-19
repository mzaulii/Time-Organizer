package sample;

public class Workspace {
    int id;
    String workspace;
    String descrizioneW;
    int[] attivita;

    public Workspace(int id, String workspace, String descrizioneW, int[] attivita) {
        this.id=id;
        this.workspace=workspace;
        this.descrizioneW = descrizioneW;
        this.attivita=attivita;
    }

    public int getId(){
        return this.id;
    }

}
