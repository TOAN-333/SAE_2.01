package iut.info1.sae201.controlleur;

import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import iut.info1.sae201.modele.Jeu;
import iut.info1.sae201.vue.EchangeurDeVue;
import iut.info1.sae201.vue.EnsembleDesVues;

public class ControlleurFenetreJeu {

    @FXML private GridPane gridPane;
    @FXML private Button btn_Rejouer;
    @FXML private Button btn_Menu;
    @FXML private Label labelChronoRouge;
    @FXML private Label labelChronoJaune;
    @FXML private Label labelTempsTotal;

    private Jeu model;

    private int tempsTotal = 0;

    private Timeline timeline;

    // Chronos joueurs
    private int tempsRouge = 20;
    private int tempsJaune = 20;

    @FXML
    public void initialize() {
        model = new Jeu();
        initialiserBoutons();
        initialiserChronos();
        demarrerTimers();
        mettreAJourIndicateurJoueur();
    }

    private void initialiserBoutons() {
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
    }

    private void initialiserChronos() {
        tempsRouge = 20;
        tempsJaune = 20;
        tempsTotal = 0;

        labelChronoRouge.setText(formatterTemps(tempsRouge));
        labelChronoJaune.setText(formatterTemps(tempsJaune));
        labelTempsTotal.setText(formatterTemps(tempsTotal));
    }

    private String formatterTemps(int secondes) {
        int min = secondes / 60;
        int sec = secondes % 60;
        return String.format("%02d:%02d", min, sec);
    }

    private void demarrerTimers() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            tempsTotal++;
            labelTempsTotal.setText(formatterTemps(tempsTotal));

            if (model.isRougeJoue()) {
                if (tempsRouge > 0) tempsRouge--;
                labelChronoRouge.setText(formatterTemps(tempsRouge));

                if (tempsRouge == 0) {
                    afficherMessage("Temps écoulé", "Le joueur Rouge a dépassé son temps !");
                    model.setPartieTerminee(true);
                    desactiverTousLesBoutons();
                    stopTimers();
                }
            } else {
                if (tempsJaune > 0) tempsJaune--;
                labelChronoJaune.setText(formatterTemps(tempsJaune));

                if (tempsJaune == 0) {
                    afficherMessage("Temps écoulé", "Le joueur Jaune a dépassé son temps !");
                    model.setPartieTerminee(true);
                    desactiverTousLesBoutons();
                    stopTimers();
                }
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void stopTimers() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    @FXML
    private void handleNouvellePartie(ActionEvent event) {
        if (!confirmerAbandon()) {
            event.consume();
            return;
        }
        model.initialiserGrille();
        initialiserBoutons();
        reactiverBoutons();
        initialiserChronos();
        demarrerTimers();
        model.setPartieTerminee(false);
        mettreAJourIndicateurJoueur();
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
        if (btn_Rejouer != null) {
        	btn_Rejouer.setDisable(false);
        }
    }

    @FXML
    public void handleButtonClick(ActionEvent event) {
        if (model.isPartieTerminee()) return;

        Button boutonClique = (Button) event.getSource();
        String id = boutonClique.getId();
        int colonne = extraireColonneDepuisId(id);

        int ligne = model.placerJeton(colonne - 1);  // Corrigé : colonne commence à 0 dans model

        if (ligne == -1) {
            return;
        }

        mettreAJourBouton(ligne, colonne);

        if (model.verifierVictoire(ligne, colonne - 1)) {
            String gagnant = model.isRougeJoue() ? "Rouge" : "Jaune";
            afficherPopupVictoire(gagnant);
            model.setPartieTerminee(true);
            desactiverTousLesBoutons();
            stopTimers();
            return;
        }

        if (model.estGrillePleine()) {
            afficherMessage("Match nul", "La grille est pleine, partie terminée !");
            model.setPartieTerminee(true);
            desactiverTousLesBoutons();
            stopTimers();
            return;
        }

        // Changer de joueur
        model.setRougeJoue(!model.isRougeJoue());

        // Reset temps joueur actif, les chronos doivent repartir
        if (model.isRougeJoue()) {
            tempsRouge = 20;
        } else {
            tempsJaune = 20;
        }

        mettreAJourIndicateurJoueur();
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

    private void mettreAJourIndicateurJoueur() {
        if (model.isRougeJoue()) {
            labelChronoRouge.setStyle("-fx-font-weight: bold; -fx-text-fill: red;");
            labelChronoJaune.setStyle("-fx-font-weight: normal; -fx-text-fill: black;");
        } else {
            labelChronoJaune.setStyle("-fx-font-weight: bold; -fx-text-fill: yellow;");
            labelChronoRouge.setStyle("-fx-font-weight: normal; -fx-text-fill: black;");
        }
    }

    private void afficherPopupVictoire(String gagnant) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Victoire !");
        alert.setHeaderText("Le joueur " + gagnant + " a gagné !");
        alert.setContentText("Félicitations !");
        alert.showAndWait();
    }

    private void afficherMessage(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
        if (btn_Rejouer != null) {
        	btn_Rejouer.setDisable(true);
        }
    }

    public void afficherAvertissement(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
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

    @FXML
    private void handleMenu(ActionEvent event) {
    	
    	if (!confirmerAbandon()) {
            event.consume();
            return;
        }
    	
		model.initialiserGrille();
	    initialiserBoutons();
	    reactiverBoutons();
	    initialiserChronos();
	    demarrerTimers();
	    model.setPartieTerminee(false);
	    mettreAJourIndicateurJoueur();
         
        EchangeurDeVue.echangerAvec(EnsembleDesVues.VUE_MENU);
        stopTimers();
    }
}
