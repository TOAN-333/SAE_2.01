/**
 * GestionDialogues.java                05/06/2025
 * 
 * IUT de Rodez, 2024-2025, aucun copyright
 */
package iut.info1.sae201.modele;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

/**
 * La classe GestionDialogues centralise la gestion des fenêtres de dialogue (popups)
 * utilisées pendant la partie : confirmations, messages d'information, etc.
 * 
 * @author Toan Hery
 * @author Enzo Dumas
 * @author Nathael Dalle
 * @author Thomas Bourgougnon
 */
public class GestionDialogues {

    /**
     * Affiche une boîte de dialogue demandant à l'utilisateur s'il veut abandonner la partie.
     *
     * @return true si l'utilisateur confirme l'abandon, false sinon.
     */
    public static boolean confirmerAbandon() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Abandonner la partie ?");
        alert.setContentText("Êtes-vous sûr de vouloir quitter la partie en cours ?");

        ButtonType yesButton = new ButtonType("Oui", ButtonData.YES);
        ButtonType noButton = new ButtonType("Non", ButtonData.NO);
        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == yesButton;
    }

    /**
     * Affiche une boîte de dialogue annonçant la victoire d’un joueur.
     *
     * @param gagnant Le nom du joueur ayant gagné.
     */
    public static void afficherPopupVictoire(String gagnant) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Victoire !");
        alert.setHeaderText(gagnant + " a gagné(e) !");
        alert.setContentText("Félicitations !");
        alert.showAndWait();
    }

    /**
     * Affiche une boîte de dialogue avec un titre personnalisé et un message.
     *
     * @param titre   Le titre de la boîte de dialogue.
     * @param message Le message à afficher dans le corps de la boîte.
     */
    public static void afficherMessage(String titre, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
