/**
 * GestionPartie.java                05/06/2025
 * 
 * IUT de Rodez, 2024-2025, aucun copyright
 */
package iut.info1.sae201.modele;

import java.util.Optional;

import iut.info1.sae201.vue.EchangeurDeVue;
import iut.info1.sae201.vue.EnsembleDesVues;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
/**
 * Classe gèrant la logique principale d'une partie de jeu.
 * Elle manipule le modèle de jeu (grille, état de la partie), 
 * met à jour l'interface graphique (boutons, labels), 
 * et contrôle la gestion du temps via des chronomètres.
 * 
 * @author Toan Hery
 * @author Enzo Dumas
 * @author Nathael Dalle
 * @author Thomas Bourgougnon
 */

public class GestionPartie {

    private Jeu model;
    private GridPane gridPane;
    private Button btnRejouer;
    private Label labelJoueurActuel;
    private GestionChronos gestionChronos;

    // Constructeur avec les éléments nécessaires à la gestion de la partie
    /**
     * @param model
     * @param gridPane
     * @param btnRejouer
     * @param labelJoueurActuel
     * @param gestionChronos
     */
    public GestionPartie(Jeu model, GridPane gridPane, Button btnRejouer, Label labelJoueurActuel, GestionChronos gestionChronos) {
        this.model = model;
        this.gridPane = gridPane;
        this.btnRejouer = btnRejouer;
        this.labelJoueurActuel = labelJoueurActuel;
        this.gestionChronos = gestionChronos;
    }

    /**
     * Initialise tous les boutons de la grille à l'état initial (fond noir, activés)
     * et met à jour le label indiquant le joueur qui commence.
     */
    public void initialiserBoutons() {
        labelJoueurActuel.setText("À toi de jouer : " + ParametresPartie.getJoueur1());
        for (int i = 1; i <= Jeu.COLONNES; i++) {
            for (int j = 1; j <= Jeu.LIGNES; j++) {
                String boutonId = "btn_" + i + "_" + j;
                Button bouton = (Button) gridPane.lookup("#" + boutonId);
                if (bouton != null) {
                    bouton.setStyle("-fx-background-color: black; -fx-background-radius: 50;");
                    bouton.setDisable(false);
                }
            }
        }
        if (btnRejouer != null) btnRejouer.setDisable(false);
    }

    /**
     * Démarre une nouvelle partie : initialise la grille, remet les états à zéro,
     * démarre les chronos et définit le joueur qui commence.
     */
    public void demarrerNouvellePartie() {
        model.initialiserGrille();
        model.setPartieTerminee(false);
        initialiserBoutons();
        gestionChronos.reset();

        gestionChronos.demarrerChronoPartie();

        String joueurCommence = ParametresPartie.getJoueurCommence();
        if (joueurCommence != null && joueurCommence.equals(ParametresPartie.getJoueur2())) {
            model.setRougeJoue(false);
            gestionChronos.demarrerChronoJoueur2();
        } else {
            model.setRougeJoue(true);
            gestionChronos.demarrerChronoJoueur1();
        }

        mettreAJourNomJoueur();
        gestionChronos.demarrerMiseAJourChronos();
    }

    /**
     * Retourne au menu principal en réinitialisant la grille et arrêtant les chronos.
     */
    public void retourMenu() {
        model.initialiserGrille();
        initialiserBoutons();
        gestionChronos.reset();
        gestionChronos.arreterMiseAJourChronos();
        EchangeurDeVue.echangerAvec(EnsembleDesVues.VUE_MENU);
    }

    /**
     * Gère un coup joué à partir du bouton cliqué :
     * place le jeton, vérifie victoire ou égalité, met à jour l'affichage,
     * gère le changement de joueur et la gestion des chronos.
     */
    public void gererCoup(Button boutonClique) {
        if (model.getPartieTerminee()) return;

        String id = boutonClique.getId();
        int colonne = extraireColonneDepuisId(id);
        int ligne = model.placerJeton(colonne);
        if (ligne == -1) return;

        mettreAJourBouton(ligne, colonne);

        if (model.verifierVictoire(ligne, colonne - 1)) {
            String gagnant = model.getRougeJoue() ? ParametresPartie.getJoueur1() : ParametresPartie.getJoueur2();
            afficherPopupVictoire(gagnant);
            terminerPartie();
            return;
        }

        if (model.estGrillePleine()) {
            afficherMessage("Match nul", "La grille est pleine, partie terminée !");
            terminerPartie();
            return;
        }

        // Gestion des chronos lors du changement de joueur
        if (model.getRougeJoue()) {
            gestionChronos.arreterChronoJoueur1();
            gestionChronos.demarrerChronoJoueur2();
        } else {
            gestionChronos.arreterChronoJoueur2();
            gestionChronos.demarrerChronoJoueur1();
        }

        model.setRougeJoue(!model.getRougeJoue());
        mettreAJourNomJoueur();
    }

    /**
     * Extrait le numéro de colonne à partir de l'id du bouton (format : btn_col_ligne).
     */
    private int extraireColonneDepuisId(String buttonId) {
        String[] parts = buttonId.split("_");
        return Integer.parseInt(parts[1]);
    }

    /**
     * Met à jour la couleur du bouton correspondant à la position du jeton joué.
     */
    private void mettreAJourBouton(int ligne, int colonne) {
        int ligneFXML = ligne + 1;
        String boutonId = "btn_" + colonne + "_" + ligneFXML;
        Button bouton = (Button) gridPane.lookup("#" + boutonId);

        if (bouton != null) {
            String couleur = model.getRougeJoue() ? "red" : "yellow";
            bouton.setStyle("-fx-background-color: " + couleur + "; -fx-background-radius: 50;");
        }
    }

    /**
     * Met à jour le label affichant le nom du joueur actuel.
     */
    private void mettreAJourNomJoueur() {
        if (labelJoueurActuel != null) {
            String nom = model.getRougeJoue() ? ParametresPartie.getJoueur1() : ParametresPartie.getJoueur2();
            labelJoueurActuel.setText("À toi de jouer : " + nom);
        }
    }

    /**
     * Affiche une fenêtre popup annonçant la victoire d'un joueur.
     */
    private void afficherPopupVictoire(String gagnant) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Victoire !");
        alert.setHeaderText(gagnant + " a gagné(e) !");
        alert.setContentText("Félicitations !");
        alert.showAndWait();
    }

    /**
     * Affiche une fenêtre popup avec un message d'information.
     */
    private void afficherMessage(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    /**
     * Désactive tous les boutons de la grille ainsi que le bouton "Rejouer".
     */
    public void desactiverTousLesBoutons() {
        for (int i = 1; i <= Jeu.COLONNES; i++) {
            for (int j = 1; j <= Jeu.LIGNES; j++) {
                String boutonId = "btn_" + i + "_" + j;
                Button bouton = (Button) gridPane.lookup("#" + boutonId);
                if (bouton != null) bouton.setDisable(true);
            }
        }
        if (btnRejouer != null) btnRejouer.setDisable(true);
    }

    /**
     * Affiche un dialogue de confirmation pour l'abandon de la partie.
     * @return true si l'utilisateur confirme, false sinon.
     */
    public boolean confirmerAbandon() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Abandonner la partie ?");
        alert.setContentText("Êtes-vous sûr de vouloir quitter la partie en cours ?");
        ButtonType yesButton = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("Non", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == yesButton;
    }

    /**
     * Affiche un dialogue de confirmation pour l'exportation de la partie.
     * @return true si l'utilisateur confirme, false sinon.
     */
    public boolean confirmerExporter() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Exporter cette parti");
        alert.setContentText("Êtes-vous sûr de vouloir exporter la partie en cours ?");
        ButtonType yesButton = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("Non", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == yesButton;
    }

    /**
     * Termine la partie : bloque toute interaction et arrête les chronos.
     */
    private void terminerPartie() {
        model.setPartieTerminee(true);
        gestionChronos.arreterTousLesChronos();
        gestionChronos.arreterMiseAJourChronos();
        desactiverTousLesBoutons();
    }
}
