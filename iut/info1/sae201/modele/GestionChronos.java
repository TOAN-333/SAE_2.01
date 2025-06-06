/**
 * GestionChronos.java                05/06/2025
 * 
 * IUT de Rodez, 2024-2025, aucun copyright
 */
package iut.info1.sae201.modele;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * La classe GestionChronos permet de gérer l'affichage et le contrôle
 * des différents chronomètres d'une partie : chronomètre général,
 * chronomètre du joueur 1 et du joueur 2.
 * <p>
 * 		Elle utilise la classe Chronos pour effectuer les calculs de temps,
 * 		et JavaFX pour afficher les durées dans des labels.
 * </p>
 * 
 * @author Toan Hery
 * @author Enzo Dumas
 * @author Nathael Dalle
 * @author Thomas Bourgougnon
 */
public class GestionChronos {

    private Chronos gestionnaireDeChronos = new Chronos(); // Gère les données de temps
    private Timeline timelineChronos; // Rafraîchissement des affichages toutes les secondes

    private Label labelChronoPartie;
    private Label labelChronoJoueur1;
    private Label labelChronoJoueur2;

    /**
     * Constructeur : lie les labels de l'interface aux chronomètres.
     *
     * @param labelChronoPartie  Label affichant le temps total de la partie
     * @param labelChronoJoueur1 Label affichant le temps du joueur 1
     * @param labelChronoJoueur2 Label affichant le temps du joueur 2
     */
    public GestionChronos(Label labelChronoPartie, Label labelChronoJoueur1, Label labelChronoJoueur2) {
        this.labelChronoPartie = labelChronoPartie;
        this.labelChronoJoueur1 = labelChronoJoueur1;
        this.labelChronoJoueur2 = labelChronoJoueur2;
    }

    /**
     * Lance un rafraîchissement automatique de l’affichage des chronomètres
     * toutes les secondes.
     */
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

    /**
     * Arrête la mise à jour automatique des chronomètres à l'écran.
     */
    public void arreterMiseAJourChronos() {
        if (timelineChronos != null) {
            timelineChronos.stop();
        }
    }

    /**
     * Réinitialise tous les chronomètres à zéro.
     */
    public void reset() {
        gestionnaireDeChronos.reset();
    }

    /**
     * Démarre le chronomètre global de la partie.
     */
    public void demarrerChronoPartie() {
        gestionnaireDeChronos.demarrerChronoPartie();
    }

    /**
     * Démarre le chronomètre du joueur 1 avec une durée de départ de 20 secondes.
     */
    public void demarrerChronoJoueur1() {
        gestionnaireDeChronos.demarrerChronoJoueur1(20);
    }

    /**
     * Démarre le chronomètre du joueur 2 avec une durée de départ de 20 secondes.
     */
    public void demarrerChronoJoueur2() {
        gestionnaireDeChronos.demarrerChronoJoueur2(20);
    }

    /**
     * Arrête le chronomètre du joueur 1 tout en mettant à jour son temps restant.
     */
    public void arreterChronoJoueur1() {
        gestionnaireDeChronos.setTempsJoueur1(gestionnaireDeChronos.getTempsJoueur1());
        gestionnaireDeChronos.demarrerChronoJoueur1(20);
        gestionnaireDeChronos.arreterChronoJoueur1();
    }

    /**
     * Arrête le chronomètre du joueur 2 tout en mettant à jour son temps restant.
     */
    public void arreterChronoJoueur2() {
        gestionnaireDeChronos.setTempsJoueur2(gestionnaireDeChronos.getTempsJoueur2());
        gestionnaireDeChronos.demarrerChronoJoueur2(20);
        gestionnaireDeChronos.arreterChronoJoueur2();
    }

    /**
     * Arrête tous les chronomètres de la partie (partie, joueur1, joueur2).
     */
    public void arreterTousLesChronos() {
        gestionnaireDeChronos.arreterTousLesChronos();
    }

    /**
     * Compare les temps des deux joueurs et redémarre le chronomètre
     * du joueur ayant le temps le plus faible.
     */
    public void CompareChronos() {
        if (gestionnaireDeChronos.getTempsJoueur1() > gestionnaireDeChronos.getTempsJoueur2()) {
            gestionnaireDeChronos.demarrerChronoJoueur2(gestionnaireDeChronos.getTempsJoueur2());
        } else {
            gestionnaireDeChronos.demarrerChronoJoueur1(gestionnaireDeChronos.getTempsJoueur1());
        }
    }
}
