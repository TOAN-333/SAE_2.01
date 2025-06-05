package iut.info1.sae201.controlleur;

import java.io.IOException;

import iut.info1.sae201.modele.Fichier;
import iut.info1.sae201.modele.ParametresPartie;
import iut.info1.sae201.vue.EchangeurDeVue;
import iut.info1.sae201.vue.EnsembleDesVues;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ControlleurFenetreMenu {
	
    @FXML
    private void handleNouvellePartie() {
    	
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Configuration de la partie");
        dialog.setHeaderText("Choisissez les options de jeu");

        // Champs de texte pour les pseudos
        TextField tfJoueur1 = new TextField();
        tfJoueur1.setPromptText("Nom du joueur 1 (Rouge)");

        TextField tfJoueur2 = new TextField();
        tfJoueur2.setPromptText("Nom du joueur 2 (Jaune)");
        
        // Champs de texte pour les pseudos
        TextField choixJeu = new TextField();
        choixJeu.setPromptText("Entrez le nom d'un joueur");

        VBox content = new VBox(10);
        content.getChildren().addAll(
            new Label("Pseudo Joueur 1 :"), tfJoueur1,
            new Label("Pseudo Joueur 2 :"), tfJoueur2,
            new Label("Qui commence :"), choixJeu
        );

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                String joueur1 = tfJoueur1.getText().trim();
                String joueur2 = tfJoueur2.getText().trim();
                String choixJCommence = choixJeu.getText().trim();

                if (joueur1.isEmpty() || joueur2.isEmpty()) {
                    afficherAvertissement("Veuillez entrer les noms des deux joueurs.");
                    return null;
                }

                // Enregistrement dans les paramètres statiques
                ParametresPartie.setJoueur1(joueur1);
                ParametresPartie.setJoueur2(joueur2);
                ParametresPartie.setJoueurCommence(choixJCommence);
                
                EchangeurDeVue.echangerAvec(EnsembleDesVues.VUE_PUISSANCE4);
                
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void afficherAvertissement(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Avertissement");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
    private void handleImporterPartie() {
        Fichier fichier = new Fichier();
        try {
			fichier.importerPartie();
			EchangeurDeVue.echangerAvec(EnsembleDesVues.VUE_PUISSANCE4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


}
