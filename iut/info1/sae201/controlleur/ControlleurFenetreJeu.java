/* Controleur de la vue permettant de faire une addition 05/23
 *
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
 * Classe contrôleur associée à la vue permettant de faire une addition.
 * Pour l'instant, la vue est dotée d'un seul bouton :
 * - retour => permet de modifier la vue, afin de revenir à celle de
 * la fenêtre principale
 * @author C. Servières
 * @version 1.0
 *
 */
public class ControlleurFenetreJeu {
	 @FXML
	 private void handleMenu() {
		 // retour à la vue principale
		 EchangeurDeVue.echangerAvec(EnsembleDesVues.VUE_MENU);
	 }
	 
	 private Jeu model;

    @FXML
    private GridPane gridPane;
    
    @FXML
    private Button btnRejouer;
    
    @FXML
    private Button btn_Menu;


    @FXML
    public void initialize() {
    	
        model = new Jeu();
        initialiserBoutons();
    }

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

    @FXML
    private void handleNouvellePartie(ActionEvent event) {
        if (!confirmerAbandon()) {
            System.out.println("Annulation - La partie continue");
            event.consume();
            return;
        }
        model.initialiserGrille();
        reactiverBoutons();
    }

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

    @FXML
    public void handleButtonClick(ActionEvent event) {
        if (model.isPartieTerminee()) return;

        Button boutonClique = (Button) event.getSource();
        String id = boutonClique.getId();
        int colonne = extraireColonneDepuisId(id);

        int ligne = model.placerJeton(colonne);
        if (ligne == -1) {
            // afficherMessage("Colonne pleine", "Cette colonne est déjà pleine. Choisissez-en une autre.");
            return;
        }

        mettreAJourBouton(ligne, colonne);

        if (model.verifierVictoire(ligne, colonne - 1)) {
            String gagnant = model.isRougeJoue() ? "Rouge" : "Jaune";
            afficherPopupVictoire(gagnant);
            model.setPartieTerminee(true);
            desactiverTousLesBoutons();
            return;
        }

        if (model.estGrillePleine()) {
            afficherMessage("Match nul", "La grille est pleine, partie terminée !");
            model.setPartieTerminee(true);
            desactiverTousLesBoutons();
            return;
        }

        model.setRougeJoue(!model.isRougeJoue());
    }

    private int extraireColonneDepuisId(String buttonId) {
        String[] parts = buttonId.split("_");
        return Integer.parseInt(parts[1]);
    }

    private void mettreAJourBouton(int ligne, int colonne) {
        int ligneFXML = ligne + 1;
        String boutonId = "btn_" + colonne + "_" + ligneFXML;
        Button bouton = (Button) gridPane.lookup("#" + boutonId);

        if (bouton != null) {
            String couleur = model.isRougeJoue() ? "red" : "yellow";
            bouton.setStyle("-fx-background-color: " + couleur + "; -fx-background-radius: 50;");
        }
    }

    private void afficherPopupVictoire(String gagnant) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Victoire !");
        alert.setHeaderText("Le joueur " + gagnant + " a gagné !");
        alert.setContentText("Félicitations !");
        alert.showAndWait();
    }

    private void afficherMessage(String titre, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

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

    public void afficherAvertissement(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        
        alert.setTitle("Avertissement");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

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