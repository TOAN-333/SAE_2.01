/**
 * InitialisationPartie.java                05/06/2025
 * 
 * IUT de Rodez, 2024-2025, aucun copyright
 */
package iut.info1.sae201.modele;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Classe gérant l'initialisation d'une nouvelle partie de jeu.
 * 
 * Elle configure le modèle de jeu et le gestionnaire de chronomètres,
 * détermine quel joueur commence, et gère la logique de fin de partie
 * liée au dépassement de temps d'un joueur.
 * 
 * @author Toan Hery
 * @author Enzo Dumas
 * @author Nathael Dalle
 * @author Thomas Bourgougnon
 */
public class InitialisationPartie {
    private Jeu model; // Le modèle de jeu à initialiser
    private Chronos gestionnaireDeChronos; // Gestionnaire des chronomètres pour la partie

    /**
     * Constructeur initialisant la classe avec le modèle de jeu et le gestionnaire de chronos.
     * @param model Le modèle de jeu
     * @param chrono Le gestionnaire des chronomètres
     */
    public InitialisationPartie(Jeu model, Chronos chrono) {
        this.model = model;
        this.gestionnaireDeChronos = chrono;
    }

    /**
     * Démarre une nouvelle partie :
     * - Initialise la grille de jeu
     * - Définit quel joueur commence en fonction des paramètres
     * - Réinitialise et démarre les chronomètres
     * - Configure le listener déclenché quand un joueur dépasse son temps
     */
    public void start() {
        model.initialiserGrille();

        // Récupère le joueur qui doit commencer la partie
        String joueurCommence = ParametresPartie.getJoueurCommence();

        // Si le joueur qui commence est le joueur 2, alors c'est jaune qui joue en premier, sinon rouge
        if (joueurCommence != null && joueurCommence.equals(ParametresPartie.getJoueur2())) {
            model.setRougeJoue(false);
        } else {
            model.setRougeJoue(true);
        }

        // Réinitialise les chronomètres avant de démarrer la partie
        gestionnaireDeChronos.reset();

        // Définit le comportement quand un joueur dépasse son temps
        gestionnaireDeChronos.setTimeoutListener(joueur -> {
            // Le gagnant est l'autre joueur que celui dont le temps est écoulé
            String gagnant = (joueur == 1) ? ParametresPartie.getJoueur2() : ParametresPartie.getJoueur1();
            Platform.runLater(() -> {
                // Si la partie est déjà terminée, ne rien faire
                if (model.getPartieTerminee()) return;

                // Marque la partie comme terminée et arrête tous les chronos
                model.setPartieTerminee(true);
                gestionnaireDeChronos.arreterTousLesChronos();

                // Affiche une alerte pour informer que le temps est écoulé et afficher le gagnant
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Temps écoulé");
                alert.setHeaderText("Le temps du joueur " + joueur + " est écoulé !");
                alert.setContentText(gagnant + " a gagné la partie !");
                alert.showAndWait();
            });
        });

        // Démarre le chrono global de la partie
        gestionnaireDeChronos.demarrerChronoPartie();

        // Démarre le chrono du joueur qui commence avec un temps de 20 secondes (exemple)
        if (model.getRougeJoue()) {
            gestionnaireDeChronos.demarrerChronoJoueur1(20);
        } else {
            gestionnaireDeChronos.demarrerChronoJoueur2(20);
        }
    }

    /**
     * Retourne le pseudo du joueur 1.
     * @return Pseudo joueur 1
     */
    public String getPseudoJ1() {
        return ParametresPartie.getJoueur1();
    }  

    /**
     * Retourne le pseudo du joueur 2.
     * @return Pseudo joueur 2
     */
    public String getPseudoJ2() {
        return ParametresPartie.getJoueur2();
    }
}
