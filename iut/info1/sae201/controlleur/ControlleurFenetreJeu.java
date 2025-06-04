package iut.info1.sae201.controlleur;

import java.util.Optional;

import iut.info1.sae201.modele.GestionnaireDeChronos;
import iut.info1.sae201.modele.Jeu;
import iut.info1.sae201.modele.ParametresPartie;
import iut.info1.sae201.vue.EchangeurDeVue;
import iut.info1.sae201.vue.EnsembleDesVues;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class ControlleurFenetreJeu {

    private Jeu model;
    private GestionnaireDeChronos gestionnaireDeChronos = new GestionnaireDeChronos();
    private Timeline timelineChronos;

    @FXML private GridPane gridPane;
    @FXML private Button btnRejouer;
    @FXML private Button btn_Menu;
    @FXML private Label labelJoueurActuel;

    // Labels pour les chronos affichés
    @FXML private Label labelChronoPartie;
    @FXML private Label labelChronoJoueur1;
    @FXML private Label labelChronoJoueur2;
    @FXML private Label pseudoJ1;
    @FXML private Label pseudoJ2;

    @FXML
    public void initialize() {
        model = new Jeu();

        String joueurCommence = ParametresPartie.getJoueurCommence();
        if (joueurCommence != null && joueurCommence.equals(ParametresPartie.getJoueur2())) {
            model.setRougeJoue(false); // Jaune commence
        } else {
            model.setRougeJoue(true); // Rouge commence (par défaut)
        }

        pseudoJ1.setText(ParametresPartie.getJoueur1());
        pseudoJ2.setText(ParametresPartie.getJoueur2());
        
        initialiserBoutons();
        mettreAJourNomJoueur();
        gestionnaireDeChronos.reset();

        // Set le listener pour gérer fin partie quand chrono = 0
        gestionnaireDeChronos.setTimeoutListener(joueur -> {
            // Joueur 1 = 1, Joueur 2 = 2
            String gagnant = (joueur == 1) ? ParametresPartie.getJoueur2() : ParametresPartie.getJoueur1();

            // Exécuter dans le thread JavaFX
            javafx.application.Platform.runLater(() -> {
                if (model.isPartieTerminee()) return;

                model.setPartieTerminee(true);
                gestionnaireDeChronos.arreterTousLesChronos();
                arreterMiseAJourChronos();
                desactiverTousLesBoutons();

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Temps écoulé");
                alert.setHeaderText("Le temps du joueur " + joueur + " est écoulé !");
                alert.setContentText(gagnant + " a gagné la partie !");
                alert.showAndWait();
            });
        });

        gestionnaireDeChronos.demarrerChronoPartie();
        if (model.isRougeJoue()) {
            gestionnaireDeChronos.demarrerChronoJoueur1();
        } else {
            gestionnaireDeChronos.demarrerChronoJoueur2();
        }

        demarrerMiseAJourChronos();
    }

    private void demarrerMiseAJourChronos() {
        timelineChronos = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (labelChronoPartie != null)
                labelChronoPartie.setText(gestionnaireDeChronos.getTempsFormatPartie());
            if (labelChronoJoueur1 != null)
                labelChronoJoueur1.setText(gestionnaireDeChronos.getTempsFormatJoueur1());
            if (labelChronoJoueur2 != null)
                labelChronoJoueur2.setText(gestionnaireDeChronos.getTempsFormatJoueur2());
        }));
        timelineChronos.setCycleCount(Timeline.INDEFINITE);
        timelineChronos.play();
    }

    private void arreterMiseAJourChronos() {
        if (timelineChronos != null) {
            timelineChronos.stop();
        }
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
        if (btnRejouer != null) btnRejouer.setDisable(false); // réactiver bouton rejouer si désactivé avant
    }

    @FXML
    private void handleNouvellePartie(ActionEvent event) {
        if (!confirmerAbandon()) {
            event.consume();
            return;
        }

        model.initialiserGrille();
        model.setPartieTerminee(false);
        initialiserBoutons();
        gestionnaireDeChronos.reset();

        gestionnaireDeChronos.demarrerChronoPartie();

        String joueurCommence = ParametresPartie.getJoueurCommence();
        if (joueurCommence != null && joueurCommence.equals(ParametresPartie.getJoueur2())) {
            model.setRougeJoue(false);
            gestionnaireDeChronos.demarrerChronoJoueur2();
        } else {
            model.setRougeJoue(true);
            gestionnaireDeChronos.demarrerChronoJoueur1();
        }

        mettreAJourNomJoueur();

        demarrerMiseAJourChronos();
    }

    @FXML
    private void handleMenu(ActionEvent event) {
        if (!confirmerAbandon()) {
            event.consume();
            return;
        }
        
        model.initialiserGrille();
        initialiserBoutons();

        gestionnaireDeChronos.reset();
        arreterMiseAJourChronos();

        EchangeurDeVue.echangerAvec(EnsembleDesVues.VUE_MENU);
    }

    @FXML
    public void handleButtonClick(ActionEvent event) {
        if (model.isPartieTerminee()) return;

        Button boutonClique = (Button) event.getSource();
        String id = boutonClique.getId();
        int colonne = extraireColonneDepuisId(id);

        int ligne = model.placerJeton(colonne);
        if (ligne == -1) return;

        mettreAJourBouton(ligne, colonne);

        if (model.verifierVictoire(ligne, colonne - 1)) {
            String gagnant = model.isRougeJoue() ? ParametresPartie.getJoueur1() : ParametresPartie.getJoueur2();
            afficherPopupVictoire(gagnant);
            model.setPartieTerminee(true);
            gestionnaireDeChronos.arreterTousLesChronos();
            arreterMiseAJourChronos();
            desactiverTousLesBoutons();
            return;
        }

        if (model.estGrillePleine()) {
            afficherMessage("Match nul", "La grille est pleine, partie terminée !");
            model.setPartieTerminee(true);
            gestionnaireDeChronos.arreterTousLesChronos();
            arreterMiseAJourChronos();
            desactiverTousLesBoutons();
            return;
        }

        // Changement de joueur + gestion des chronos
        if (model.isRougeJoue()) {
            gestionnaireDeChronos.arreterChronoJoueur1();
            gestionnaireDeChronos.demarrerChronoJoueur2();
        } else {
            gestionnaireDeChronos.arreterChronoJoueur2();
            gestionnaireDeChronos.demarrerChronoJoueur1();
        }

        model.setRougeJoue(!model.isRougeJoue());
        mettreAJourNomJoueur();
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

    private void mettreAJourNomJoueur() {
        if (labelJoueurActuel != null) {
            String nom = model.isRougeJoue() ? ParametresPartie.getJoueur1() : ParametresPartie.getJoueur2();
            labelJoueurActuel.setText("À toi de jouer : " + nom);
        }
    }

    private void afficherPopupVictoire(String gagnant) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Victoire !");
        alert.setHeaderText(gagnant + " a gagné(e) !");
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
                if (bouton != null) bouton.setDisable(true);
            }
        }
        if (btnRejouer != null) btnRejouer.setDisable(true);
    }

    private boolean confirmerAbandon() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
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
