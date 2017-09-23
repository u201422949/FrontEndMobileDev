package pe.edu.upc.homeassistant.model;

public class Skill {

    private String competencia;
    private Boolean state;

    public Skill(String com){
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
