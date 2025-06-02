/**
 * ControlleurFenetreJeu.java                                02/06/2025
 * 
 * IUT de Rodez, 2024-2025, aucun copyright
 */
package iut.info1.sae201.controlleur;

import java.util.Optional;

import iut.info1.sae201.modele.Jeu;
import iut.info1.sae201.vue.EchangeurDeVue;
import iut.info1.sae201.vue.EnsembleDesVues;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

/**
 * Classe contrôleur associée à la fenêtre principale de jeu.
 * Gère les interactions utilisateur pendant une partie de Puissance 4.
 * Elle permet notamment :
 * <ul>
 *   <li>La gestion des clics sur les boutons de la grille</li>
 *   <li>Le lancement d'une nouvelle partie</li>
 *   <li>Le retour au menu</li>
 *   <li>La détection de victoire ou match nul</li>
 * </ul>
 * 
 * @author Thomas Bourgougnon
 * @author Nathael Dalle
 * @author Enzo Dumas
 * @author Toan Hery
 */
public class ControlleurFenetreJeu {

    private Jeu model;

    @FXML private GridPane gridPane;
    @FXML private Button btnRejouer;
    @FXML private Button btn_Menu;

    /**
     * Méthode d'initialisation automatique appelée après le chargement FXML.
     * Initialise le modèle et l'apparence des boutons de la grille.
     */
    @FXML
    public void initialize() {
        model = new Jeu();
        initialiserBoutons();
    }

    /**
     * Gère l’action du bouton "Menu", permettant de revenir à l’écran d’accueil.
     */
    @FXML
    private void handleMenu() {
        EchangeurDeVue.echangerAvec(EnsembleDesVues.VUE_MENU);
    }

    /**
     * Initialise tous les boutons de la grille avec une apparence neutre.
     */
    private void initialiserBoutons() {
        for (int i = 1; i <= Jeu.COLONNES; i++) {
            for (int j = 1; j <= Jeu.LIGNES; j++) {
                String boutonId = "btn_" + i + "_" + j;
                Button bouton = (Button) gridPane.lookup("#" + boutonId);
                if (bouton != null) {
                    bouton.setStyle("-fx-background-color: black; -fx-background-radius: 50;");
                }
            }
        }
    }

    /**
     * Gère l’action du bouton "Nouvelle Partie" en réinitialisant le modèle
     * et la grille si l’utilisateur confirme son choix.
     */
    @FXML
    private void handleNouvellePartie(ActionEvent event) {
        if (!confirmerAbandon()) {
            event.consume();
            return;
        }
        model.initialiserGrille();
        reactiverBoutons();
    }

    /**
     * Réactive et réinitialise les boutons de la grille pour une nouvelle partie.
     */
    private void reactiverBoutons() {
        for (int i = 1; i <= Jeu.COLONNES; i++) {
            for (int j = 1; j <= Jeu.LIGNES; j++) {
                String boutonId = "btn_" + i + "_" + j;
                Button bouton = (Button) gridPane.lookup("#" + boutonId);
                if (bouton != null) {
                    bouton.setDisable(false);
                    bouton.setStyle("-fx-background-color: black; -fx-background-radius: 50;");
                }
            }
        }
        if (btnRejouer != null) {
            btnRejouer.setDisable(false);
        }
    }

    /**
     * Gère le clic sur un bouton de la grille.
     * Effectue un tour de jeu et vérifie les conditions de victoire ou égalité.
     */
    @FXML
    public void handleButtonClick(ActionEvent event) {
        if (model.isPartieTerminee()) return;

        Button boutonClique = (Button) event.getSource();
        String id = boutonClique.getId();
        int colonne = extraireColonneDepuisId(id);

        int ligne = model.placerJeton(colonne);
        if (ligne == -1) {
            afficherMessage("Colonne pleine", "Cette colonne est déjà pleine. Choisissez-en une autre.");
            return;
        }

        mettreAJourBouton(ligne, colonne);

        if (model.verifierVictoire(ligne, colonne - 1)) {
            String gagnant = model.isRougeJoue() ? "Rouge" : "Jaune";
            afficherPopupVictoire(gagnant);
            model.setPartieTerminee(true);
            desactiverTousLesBoutons();
        } else if (model.estGrillePleine()) {
            afficherMessage("Match nul", "La grille est pleine, partie terminée !");
            model.setPartieTerminee(true);
            desactiverTousLesBoutons();
        } else {
            model.setRougeJoue(!model.isRougeJoue());
        }
    }

    /**
     * Extrait l’indice de colonne à partir de l’identifiant du bouton.
     *
     * @param buttonId identifiant du bouton (format : btn_colonne_ligne)
     * @return indice de la colonne
     */
    private int extraireColonneDepuisId(String buttonId) {
        String[] parts = buttonId.split("_");
        return Integer.parseInt(parts[1]);
    }

    /**
     * Met à jour l'apparence du bouton correspondant à la case jouée.
     *
     * @param ligne ligne où le jeton est tombé
     * @param colonne colonne choisie
     */
    private void mettreAJourBouton(int ligne, int colonne) {
        int ligneFXML = ligne + 1; // car FXML commence à 1
        String boutonId = "btn_" + colonne + "_" + ligneFXML;
        Button bouton = (Button) gridPane.lookup("#" + boutonId);

        if (bouton != null) {
            String couleur = model.isRougeJoue() ? "red" : "yellow";
            bouton.setStyle("-fx-background-color: " + couleur + "; -fx-background-radius: 50;");
        }
    }

    /**
     * Affiche une boîte de dialogue en cas de victoire.
     *
     * @param gagnant couleur du joueur gagnant
     */
    private void afficherPopupVictoire(String gagnant) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Victoire !");
        alert.setHeaderText("Le joueur " + gagnant + " a gagné !");
        alert.setContentText("Félicitations !");
        alert.showAndWait();
    }

    /**
     * Affiche une boîte de dialogue informative.
     *
     * @param titre titre de la boîte
     * @param message contenu à afficher
     */
    private void afficherMessage(String titre, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    /**
     * Désactive tous les boutons de la grille après la fin de partie.
     */
    private void desactiverTousLesBoutons() {
        for (int i = 1; i <= Jeu.COLONNES; i++) {
            for (int j = 1; j <= Jeu.LIGNES; j++) {
                String boutonId = "btn_" + i + "_" + j;
                Button bouton = (Button) gridPane.lookup("#" + boutonId);
                if (bouton != null) {
                    bouton.setDisable(true);
                }
            }
        }
    }

    /**
     * Affiche un message d'avertissement simple.
     *
     * @param message le contenu de l'avertissement
     */
    public void afficherAvertissement(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Avertissement");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Demande à l'utilisateur de confirmer l'abandon de la partie.
     *
     * @return {@code true} si l'utilisateur confirme, 
     *         {@code false} sinon
     */
    private boolean confirmerAbandon() {
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
}
