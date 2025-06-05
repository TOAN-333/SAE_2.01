package iut.info1.sae201.modele;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class GestionChronos {

    private Chronos gestionnaireDeChronos = new Chronos();
    private Timeline timelineChronos;

    private Label labelChronoPartie;
    private Label labelChronoJoueur1;
    private Label labelChronoJoueur2;

    public GestionChronos(Label labelChronoPartie, Label labelChronoJoueur1, Label labelChronoJoueur2) {
        this.labelChronoPartie = labelChronoPartie;
        this.labelChronoJoueur1 = labelChronoJoueur1;
        this.labelChronoJoueur2 = labelChronoJoueur2;
    }

    public void demarrerMiseAJourChronos() {
        timelineChronos = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (labelChronoPartie != null)
                labelChronoPartie.setText(gestionnaireDeChronos.getTempsFormatPartie());
            if (labelChronoJoueur1 != null)
                labelChronoJoueur1.setText(gestionnaireDeChronos.getTempsFormatJoueur1());
            if (labelChronoJoueur2 != null)
                labelChronoJoueur2.setText(gestionnaireDeChronos.getTempsFormatJoueur2());
        }));
        timelineChronos.setCycleCount(Timeline.INDEFINITE);
        timelineChronos.play();
    }

    public void arreterMiseAJourChronos() {
        if (timelineChronos != null) {
            timelineChronos.stop();
        }
    }

    public void reset() {
        gestionnaireDeChronos.reset();
    }

    public void demarrerChronoPartie() {
        gestionnaireDeChronos.demarrerChronoPartie();
    }

    public void demarrerChronoJoueur1() {
        gestionnaireDeChronos.demarrerChronoJoueur1(20);
    }

    public void demarrerChronoJoueur2() {
        gestionnaireDeChronos.demarrerChronoJoueur2(20);
    }

    public void arreterChronoJoueur1() {
    	gestionnaireDeChronos.setTempsJoueur1(gestionnaireDeChronos.getTempsJoueur1());
    	gestionnaireDeChronos.demarrerChronoJoueur1(20);
        gestionnaireDeChronos.arreterChronoJoueur1();
    }

    public void arreterChronoJoueur2() {
    	gestionnaireDeChronos.setTempsJoueur2(gestionnaireDeChronos.getTempsJoueur2());
    	gestionnaireDeChronos.demarrerChronoJoueur2(20);
        gestionnaireDeChronos.arreterChronoJoueur2();
    }

    public void arreterTousLesChronos() {
        gestionnaireDeChronos.arreterTousLesChronos();
    }
    
    public void CompareChronos() {
		if(gestionnaireDeChronos.getTempsJoueur1() > gestionnaireDeChronos.getTempsJoueur2()) {
			gestionnaireDeChronos.demarrerChronoJoueur2(gestionnaireDeChronos.getTempsJoueur2());
		} else {
			gestionnaireDeChronos.demarrerChronoJoueur1(gestionnaireDeChronos.getTempsJoueur1());
		}
    }
    
}
