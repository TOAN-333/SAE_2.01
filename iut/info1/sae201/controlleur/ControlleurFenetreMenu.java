package iut.info1.sae201.controlleur;

import iut.info1.sae201.vue.EchangeurDeVue;
import iut.info1.sae201.vue.EnsembleDesVues;
import javafx.fxml.FXML;

public class ControlleurFenetreMenu {

    @FXML
    private void handleNouvellePartie() {
        EchangeurDeVue.echangerAvec(EnsembleDesVues.VUE_PUISSANCE4);
    }
}
