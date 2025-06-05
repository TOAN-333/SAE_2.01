package iut.info1.sae201.modele;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class GestionInterface {

    private GridPane gridPane;
    private Label labelJoueurActuel;

    public GestionInterface(GridPane gridPane, Label labelJoueurActuel) {
        this.gridPane = gridPane;
        this.labelJoueurActuel = labelJoueurActuel;
    }

    /**
     * Initialise tous les boutons à l'état "vide" (fond noir, boutons activés).
     * Indices en 1-based, car fx:id sont "btn_colonne_ligne".
     */
    public void initialiserBoutons() {
        for (int col = 1; col <= Jeu.COLONNES; col++) {
            for (int ligne = 1; ligne <= Jeu.LIGNES; ligne++) {
                String boutonId = "btn_" + col + "_" + ligne;
                Button bouton = (Button) gridPane.lookup("#" + boutonId);
                if (bouton != null) {
                    bouton.setStyle("-fx-background-color: black; -fx-background-radius: 50;");
                    bouton.setDisable(false);
                }
            }
        }
    }

    /**
     * Met à jour l'affichage de la grille complète à partir d'un tableau de String.
     * Chaque case contient "R", "J" ou autre chose (vide).
     * Indices dans la grille sont 0-based, les boutons en 1-based.
     */
    public void afficherGrille(String[][] grille) {
        System.out.println("Affichage grille commencé");
        for (int ligne = 0; ligne < grille.length; ligne++) {
            for (int colonne = 0; colonne < grille[ligne].length; colonne++) {
                String symbole = grille[ligne][colonne];
                int ligne1Based = ligne + 1;
                int colonne1Based = colonne + 1;

                if ("R".equals(symbole)) {
                    mettreAJourBouton(colonne1Based, ligne1Based, true);
                    System.out.println("rouge à btn_" + colonne1Based + "_" + ligne1Based);
                } else if ("J".equals(symbole)) {
                    mettreAJourBouton(colonne1Based, ligne1Based, false);
                    System.out.println("jaune à btn_" + colonne1Based + "_" + ligne1Based);
                } else {
                    mettreAJourBoutonVide(colonne1Based, ligne1Based);
                    System.out.println("vide à btn_" + colonne1Based + "_" + ligne1Based);
                }
            }
        }
        System.out.println("Affichage grille terminé");
    }

    /**
     * Met à jour un bouton en couleur rouge ou jaune selon le joueur.
     * Indices 1-based.
     */
    public void mettreAJourBouton(int colonne1Based, int ligne1Based, boolean isRouge) {
        String boutonId = "btn_" + colonne1Based + "_" + ligne1Based;
        Button bouton = (Button) gridPane.lookup("#" + boutonId);
        if (bouton != null) {
            String couleur = isRouge ? "red" : "yellow";
            bouton.setStyle("-fx-background-color: " + couleur + "; -fx-background-radius: 50;");
            System.out.println("[LOG] bouton " + boutonId + " coloré en " + couleur);
        } else {
            System.out.println("Bouton introuvable : " + boutonId);
        }
    }


    /**
     * Met à jour un bouton pour le rendre "vide" (fond noir).
     * Indices 1-based.
     */
    private void mettreAJourBoutonVide(int colonne1Based, int ligne1Based) {
        String boutonId = "btn_" + colonne1Based + "_" + ligne1Based;
        Button bouton = (Button) gridPane.lookup("#" + boutonId);
        if (bouton != null) {
            bouton.setStyle("-fx-background-color: black; -fx-background-radius: 50;");
        } else {
            System.out.println("Bouton vide introuvable : " + boutonId);
        }
    }

    /**
     * Met à jour le label affichant le nom du joueur actuel.
     */
    public void mettreAJourNomJoueur(String nomJoueur) {
        if (labelJoueurActuel != null) {
            labelJoueurActuel.setText("À toi de jouer : " + nomJoueur);
        }
    }

    /**
     * Désactive tous les boutons (fin de partie, par exemple).
     */
    public void desactiverTousLesBoutons() {
        for (int col = 1; col <= Jeu.COLONNES; col++) {
            for (int ligne = 1; ligne <= Jeu.LIGNES; ligne++) {
                String boutonId = "btn_" + col + "_" + ligne;
                Button bouton = (Button) gridPane.lookup("#" + boutonId);
                if (bouton != null) {
                    bouton.setDisable(true);
                }
            }
        }
    }
}
