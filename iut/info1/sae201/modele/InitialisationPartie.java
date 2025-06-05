package iut.info1.sae201.modele;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class InitialisationPartie {
    private Jeu model;
    private Chronos gestionnaireDeChronos;

    public InitialisationPartie(Jeu model, Chronos chrono) {
        this.model = model;
        this.gestionnaireDeChronos = chrono;
    }

    public void start() {
        model.initialiserGrille();
        String joueurCommence = ParametresPartie.getJoueurCommence();

        if (joueurCommence != null && joueurCommence.equals(ParametresPartie.getJoueur2())) {
            model.setRougeJoue(false);
        } else {
            model.setRougeJoue(true);
        }

        gestionnaireDeChronos.reset();

        gestionnaireDeChronos.setTimeoutListener(joueur -> {
            String gagnant = (joueur == 1) ? ParametresPartie.getJoueur2() : ParametresPartie.getJoueur1();
            Platform.runLater(() -> {
                if (model.isPartieTerminee()) return;
                model.setPartieTerminee(true);
                gestionnaireDeChronos.arreterTousLesChronos();

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Temps écoulé");
                alert.setHeaderText("Le temps du joueur " + joueur + " est écoulé !");
                alert.setContentText(gagnant + " a gagné la partie !");
                alert.showAndWait();
            });
        });

        gestionnaireDeChronos.demarrerChronoPartie();
        if (model.isRougeJoue()) {
            gestionnaireDeChronos.demarrerChronoJoueur1(20);
        } else {
            gestionnaireDeChronos.demarrerChronoJoueur2(20);
        }
    }

    public String getPseudoJ1() {
        return ParametresPartie.getJoueur1();
    }

    public String getPseudoJ2() {
        return ParametresPartie.getJoueur2();
    }
}
