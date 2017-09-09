package pe.edu.upc.homeassistant;

/**
 * Created by paul.cabrera on 08/09/2017.
 */

public class Competencia {

    private String competencia;
    private Boolean state;

    public Competencia(String com){
        this.competencia = com;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
