package iut.info1.sae201;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.ButtonBar;
import java.io.IOException;
import java.util.Optional;

import iut.info1.sae201.Model;

public class Controller {
	
    private Model model;

    @FXML
    private GridPane gridPane;
    
    @FXML
    private Button btnRejouer;
    
    @FXML
    private Button btn_Menu;

    @FXML
    public void initialize() {
        model = new Model();
        initialiserBoutons();
    }

    private void initialiserBoutons() {
        for (int i = 1; i <= Model.COLONNES; i++) {
            for (int j = 1; j <= Model.LIGNES; j++) {
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
        for (int i = 1; i <= Model.COLONNES; i++) {
            for (int j = 1; j <= Model.LIGNES; j++) {
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
        for (int i = 1; i <= Model.COLONNES; i++) {
            for (int j = 1; j <= Model.LIGNES; j++) {
                String boutonId = "btn_" + i + "_" + j;
                Button bouton = (Button) gridPane.lookup("#" + boutonId);
                if (bouton != null) {
                    bouton.setDisable(true);
                }
            }
        }
    }

    @FXML
    public void handleParametre(ActionEvent event) {
        System.out.println("Paramètres cliqués");
        afficherAvertissement("Action impossible dans l'état actuel !");
    }

    @FXML
    private void handleMenu(ActionEvent event) {
        if (!confirmerAbandon()) {
            System.out.println("Annulation - La partie continue");
            event.consume();
            return;
        }

        try {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Menu.fxml"));
            Parent root = loader.load();
            
            stage.setScene(new Scene(root));
            stage.sizeToScene();
            stage.centerOnScreen();
            
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(AlertType.ERROR, "Erreur de chargement").show();
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