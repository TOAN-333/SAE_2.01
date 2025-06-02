package iut.info1.sae201.controlleur;

//import application.modele.Parametres;
import iut.info1.sae201.vue.EchangeurDeVue;
import iut.info1.sae201.vue.EnsembleDesVues;
import javafx.fxml.FXML;

public class ControlleurFenetreMenu {

    @FXML
    private void handleNouvellePartie() {
        EchangeurDeVue.echangerAvec(EnsembleDesVues.VUE_PUISSANCE4);
    }

    @FXML
    private void handleParametre() {
        EchangeurDeVue.echangerAvec(EnsembleDesVues.VUE_PARAMETRE);
    }
  
    @FXML
    private void initialize() {
    	//Parametres parametres = new Parametres("joueur 1","joueur 2","red","yellow","blue");
    }
}
