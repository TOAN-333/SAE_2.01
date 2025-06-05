package iut.info1.sae201.modele;

import java.io.File;
import java.io.IOException;

import javafx.stage.Window;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

public class GestionSauvegarde {

    private Jeu model;
    // Object controleur;  // pour appeler afficherMessage (optionnel)

    public GestionSauvegarde(Jeu model) {
        this.model = model;
    }

    public void sauvegarderPartie(Window parentWindow) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer la partie");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers texte (*.txt)", "*.txt"));
        File fichier = fileChooser.showSaveDialog(parentWindow);

        if (fichier != null) {
            try {
                Fichier.sauvegarderPartie(model, fichier.getAbsolutePath());
                afficherMessage("Succès", "Partie sauvegardée !");
            } catch (IOException e) {
                afficherMessage("Erreur", "Impossible de sauvegarder la partie : " + e.getMessage());
            }
        }
    }

    public void exporterPartie(Window parentWindow) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter la partie");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers texte (*.txt)", "*.txt"));
        File fichier = fileChooser.showSaveDialog(parentWindow);

        if (fichier != null) {
            try {
                Fichier.exporterPartie(model, fichier);
                afficherMessage("Exportation réussie", "La partie a été exportée avec succès.");
            } catch (IOException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Impossible d’exporter la partie.");
                alert.setContentText("Détails : " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    private void afficherMessage(String titre, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
