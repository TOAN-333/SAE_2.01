/**
 * GestionInterface.java                05/06/2025
 * 
 * IUT de Rodez, 2024-2025, aucun copyright
 */
package iut.info1.sae201.modele;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * Classe responsable de la gestion de l'interface utilisateur pour la grille de jeu.
 * Permet d'afficher la grille, de gérer les événements de survol,
 * et de mettre à jour les informations liées au joueur actif.
 * 
 * @author Toan Hery
 * @author Enzo Dumas
 * @author Nathael Dalle
 * @author Thomas Bourgougnon
 */
public class GestionInterface {

    private GridPane gridPane;
    private Label labelJoueurActuel;
    private String[][] grilleCourante;

    /**
     * Constructeur de GestionInterface.
     *
     * @param gridPane           La grille graphique (interface) du jeu.
     * @param labelJoueurActuel  Le label affichant le nom du joueur actif.
     */
    public GestionInterface(GridPane gridPane, Label labelJoueurActuel) {
        this.gridPane = gridPane;
        this.labelJoueurActuel = labelJoueurActuel;
    }

    /**
     * Récupère un bouton dans la grille à partir de ses coordonnées.
     *
     * @param col   Numéro de colonne (1-based).
     * @param ligne Numéro de ligne (1-based).
     * @return Le bouton correspondant s’il existe, sinon null.
     */
    public Button getBouton(int col, int ligne) {
        return (Button) gridPane.lookup("#btn_" + col + "_" + ligne);
    }

    /**
     * Initialise les boutons de la première ligne pour détecter le survol
     * de la souris et afficher un aperçu du pion à placer.
     */
    public void initialiserBoutons() {
        for (int col = 1; col <= Jeu.COLONNES; col++) {
            int finalCol = col;
            Button boutonSurvol = getBouton(finalCol, 1);

            if (boutonSurvol != null) {
                boutonSurvol.setOnMouseEntered(e -> {
                    int ligneLibre = trouverLigneDisponible(finalCol);
                    if (ligneLibre > 0) {
                        Button cible = getBouton(finalCol, ligneLibre);
                        if (cible != null) {
                            String couleur = couleurAvecTransparence();
                            cible.setStyle("-fx-background-color: " + couleur + "; -fx-background-radius: 50;");
                        }
                    }
                });

                boutonSurvol.setOnMouseExited(e -> {
                    int ligneLibre = trouverLigneDisponible(finalCol);
                    if (ligneLibre > 0) {
                        Button cible = getBouton(finalCol, ligneLibre);
                        if (cible != null) {
                            String valeur = grilleCourante[ligneLibre - 1][finalCol - 1];
                            String couleur = switch (valeur) {
                                case "R" -> "red";
                                case "J" -> "yellow";
                                default -> "black";
                            };
                            cible.setStyle("-fx-background-color: " + couleur + "; -fx-background-radius: 50;");
                        }
                    }
                });
            }
        }
    }

    /**
     * Met à jour l'affichage de la grille avec les couleurs correspondant aux pions.
     *
     * @param grille Grille de jeu (tableau 2D de String) à afficher.
     */
    public void afficherGrille(String[][] grille) {
        this.grilleCourante = grille;
        for (int ligne = 0; ligne < grille.length; ligne++) {
            for (int col = 0; col < grille[ligne].length; col++) {
                String valeur = grille[ligne][col];
                Button bouton = getBouton(col + 1, ligne + 1);
                if (bouton != null) {
                    String couleur = switch (valeur) {
                        case "R" -> "red";
                        case "J" -> "yellow";
                        default -> "black";
                    };
                    bouton.setStyle("-fx-background-color: " + couleur + "; -fx-background-radius: 50;");
                }
            }
        }
    }

    /**
     * Recherche la première ligne disponible (non occupée) dans une colonne.
     *
     * @param colonne1Based Numéro de colonne (1-based).
     * @return Numéro de ligne disponible (1-based), ou -1 si la colonne est pleine.
     */
    public int trouverLigneDisponible(int colonne1Based) {
        if (grilleCourante == null) return -1;
        int colIndex = colonne1Based - 1;
        for (int ligne = Jeu.LIGNES - 1; ligne >= 0; ligne--) {
            if (".".equals(grilleCourante[ligne][colIndex])) {
                return ligne + 1;
            }
        }
        return -1;
    }

    /**
     * Détermine si c'est au tour du joueur rouge.
     *
     * @return true si c'est au tour du joueur rouge, false sinon.
     */
    public boolean joueurRouge() {
        int r = 0, j = 0;
        if (grilleCourante == null) return true;
        for (String[] ligne : grilleCourante) {
            for (String val : ligne) {
                if ("R".equals(val)) r++;
                else if ("J".equals(val)) j++;
            }
        }
        return r <= j;
    }

    /**
     * Retourne une couleur semi-transparente en fonction du joueur courant
     * (utilisé pour les aperçus lors du survol).
     *
     * @return Code couleur en rgba.
     */
    public String couleurAvecTransparence() {
        return joueurRouge() ? "rgba(255,0,0,0.5)" : "rgba(255,255,0,0.7)";
    }

    /**
     * Met à jour le label indiquant le nom du joueur dont c’est le tour.
     *
     * @param nom Nom du joueur actif.
     */
    public void mettreAJourNomJoueur(String nom) {
        if (labelJoueurActuel != null) {
            labelJoueurActuel.setText("À toi : " + nom);
        }
    }

    /**
     * Met à jour la grille courante et réinitialise les événements liés aux boutons.
     *
     * @param grille La nouvelle grille à afficher.
     */
    public void majGrilleEtBoutons(String[][] grille) {
        afficherGrille(grille);
        initialiserBoutons();
    }
}
