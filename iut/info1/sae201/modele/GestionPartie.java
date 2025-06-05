package iut.info1.sae201.modele;

import java.util.Optional;

import iut.info1.sae201.vue.EchangeurDeVue;
import iut.info1.sae201.vue.EnsembleDesVues;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class GestionPartie {

    private Jeu model;
    private GridPane gridPane;
    private Button btnRejouer;
    private Label labelJoueurActuel;
    private GestionChronos gestionChronos;

    public GestionPartie(Jeu model, GridPane gridPane, Button btnRejouer, Label labelJoueurActuel, GestionChronos gestionChronos) {
        this.model = model;
        this.gridPane = gridPane;
        this.btnRejouer = btnRejouer;
        this.labelJoueurActuel = labelJoueurActuel;
        this.gestionChronos = gestionChronos;
    }

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

    public void retourMenu() {
        model.initialiserGrille();
        initialiserBoutons();
        gestionChronos.reset();
        gestionChronos.arreterMiseAJourChronos();
        EchangeurDeVue.echangerAvec(EnsembleDesVues.VUE_MENU);
    }

    public void gererCoup(Button boutonClique) {
        if (model.isPartieTerminee()) return;

        String id = boutonClique.getId();
        int colonne = extraireColonneDepuisId(id);
        int ligne = model.placerJeton(colonne);
        if (ligne == -1) return;

        mettreAJourBouton(ligne, colonne);

        if (model.verifierVictoire(ligne, colonne - 1)) {
            String gagnant = model.isRougeJoue() ? ParametresPartie.getJoueur1() : ParametresPartie.getJoueur2();
            afficherPopupVictoire(gagnant);
            terminerPartie();
            return;
        }

        if (model.estGrillePleine()) {
            afficherMessage("Match nul", "La grille est pleine, partie terminée !");
            terminerPartie();
            return;
        }

        // Changement de joueur + gestion des chronos
        if (model.isRougeJoue()) {
            gestionChronos.arreterChronoJoueur1();
            gestionChronos.demarrerChronoJoueur2();
        } else {
            gestionChronos.arreterChronoJoueur2();
            gestionChronos.demarrerChronoJoueur1();
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
                if (bouton != null) bouton.setDisable(true);
            }
        }
        if (btnRejouer != null) btnRejouer.setDisable(true);
    }

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

    private void terminerPartie() {
        model.setPartieTerminee(true);
        gestionChronos.arreterTousLesChronos();
        gestionChronos.arreterMiseAJourChronos();
        desactiverTousLesBoutons();
    }
}
