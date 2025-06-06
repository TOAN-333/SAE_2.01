package iut.info1.sae201.modele;

import java.io.File;
import java.io.IOException;

import javafx.stage.Window;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

/**
 * Classe responsable de la gestion de la sauvegarde et de l'exportation
 * de la partie en cours. Elle utilise des dialogues de fichiers pour
 * permettre à l'utilisateur de choisir l'emplacement de sauvegarde ou
 * d'exportation, et affiche des messages d'information ou d'erreur
 * selon le résultat de l'opération.
 * 
 * @author Toan Hery
 * @author Enzo Dumas
 * @author Nathael Dalle
 * @author Thomas Bourgougnon
 */
public class GestionSauvegarde {

    private Jeu model;
    // Object controleur;  // pour appeler afficherMessage (optionnel)

    /**
     * @param model
     */
    public GestionSauvegarde(Jeu model) {
        this.model = model;
    }

    /**
     * Ouvre une boîte de dialogue pour sauvegarder la partie en cours dans un fichier texte.
     * @param parentWindow la fenêtre parente pour le dialogue de fichier
     */
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

    /**
     * Ouvre une boîte de dialogue pour exporter la partie en cours dans un fichier texte.
     * @param parentWindow la fenêtre parente pour le dialogue de fichier
     */
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

    /**
     * Affiche une boîte de dialogue d'information avec un titre et un message.
     * @param titre le titre de la fenêtre de dialogue
     * @param message le message à afficher
     */
    private void afficherMessage(String titre, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
