/**
 * Jeu.java                05/06/2025
 * 
 * IUT de Rodez, 2024-2025, aucun copyright
 */
package iut.info1.sae201.modele;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe représentant le modèle du jeu Puissance 4.
 * 
 * Elle contient la grille de jeu, la logique pour placer un jeton,
 * vérifier les conditions de victoire, et gérer l'état de la partie.
 * Elle stocke aussi les paramètres des joueurs (pseudo, couleur).
 * 
 * @author Toan Hery
 * @author Enzo Dumas
 * @author Nathael Dalle
 * @author Thomas Bourgougnon
 */
public class Jeu {
    
    /** Nombre de lignes dans la grille */
    public static final int LIGNES = 6;  
    
    /** Nombre de colonnes dans la grille */
    public static final int COLONNES = 7;    
    
    private String[][] grille;                // La grille du jeu : chaque case contient "." ou "R" ou "J"
    private boolean rougeJoue;                // Indique si c'est au joueur rouge de jouer
    private boolean partieTerminee;           // Indique si la partie est terminée

    // Attributs pour stocker les paramètres des joueurs et la couleur de la grille
    private String pseudoJoueur1;
    private String pseudoJoueur2;
    private String couleurJoueur1;
    private String couleurJoueur2;
    private String couleurGrille;

    /**
     * Constructeur par défaut initialisant la grille et l'état du jeu.
     */
    public Jeu() {
        initialiserGrille();
    }

    /**
     * Initialise la grille en remplissant toutes les cases par "." 
     * et remet l'état de la partie à non terminée.
     * Le joueur rouge commence par défaut.
     */
    public void initialiserGrille() {
        grille = new String[LIGNES][COLONNES];
        for (int i = 0; i < LIGNES; i++) {
            for (int j = 0; j < COLONNES; j++) {
                grille[i][j] = ".";
            }
        }
        partieTerminee = false;
        rougeJoue = true;
    }
    
    /**
     * Initialise la grille à partir d'une grille importée.
     * Vérifie la validité de la grille puis calcule quel joueur doit jouer.
     * @param grilleImportee la grille à importer
     */
    public void initialiserGrilleImporter(String[][] grilleImportee) {
        if (grilleImportee.length != LIGNES || grilleImportee[0].length != COLONNES) {
            throw new IllegalArgumentException("Grille importée invalide");
        }
        this.grille = grilleImportee;

        // Compte le nombre de jetons rouges et jaunes pour déterminer qui joue
        int countR = 0, countJ = 0;
        for (int i = 0; i < LIGNES; i++) {
            for (int j = 0; j < COLONNES; j++) {
                if ("R".equals(grille[i][j])) countR++;
                else if ("J".equals(grille[i][j])) countJ++;
            }
        }
        // C'est au joueur rouge de jouer si nombre de jetons rouges <= nombre jaunes
        this.rougeJoue = countR <= countJ;

        // Partie non terminée par défaut (à améliorer pour vérifier victoire)
        this.partieTerminee = false;
    }

    /**
     * Place un jeton dans une colonne donnée, si la colonne contient
     * au moins un emplacement vide, le jeton descend jusqu'à la
     * première case vide sinon le jeton n'est pas placer.
     * @param colonne numéro de la colonne (1-based)
     * @return la ligne où le jeton a été placé, -1 si colonne pleine
     */
    public int placerJeton(int colonne) {
        int colIndex = colonne - 1;
        for (int i = LIGNES - 1; i >= 0; i--) {
            if (grille[i][colIndex].equals(".")) {
                grille[i][colIndex] = rougeJoue ? "R" : "J";
                return i;
            }
        }
        return -1; // Colonne pleine
    }

    /**
     * Vérifie si un joueur a gagné après avoir placé un jeton.
     * @param ligne ligne du dernier jeton placé
     * @param colonne colonne du dernier jeton placé
     * @return true si victoire détectée, false sinon
     */
    public boolean verifierVictoire(int ligne, int colonne) {
        String symbole = grille[ligne][colonne];
        return (compterAlignes(ligne, colonne, 0, 1, symbole) + compterAlignes(ligne, colonne, 0, -1, symbole) >= 3 ||
               (compterAlignes(ligne, colonne, 1, 0, symbole) >= 3) ||
               (compterAlignes(ligne, colonne, 1, 1, symbole) + compterAlignes(ligne, colonne, -1, -1, symbole) >= 3) ||
               (compterAlignes(ligne, colonne, 1, -1, symbole) + compterAlignes(ligne, colonne, -1, 1, symbole) >= 3));
    }

    /**
     * Compte le nombre de jetons alignés dans une direction donnée.
     * @param ligne position de départ en ligne
     * @param colonne position de départ en colonne
     * @param deltaLigne déplacement en ligne à chaque étape
     * @param deltaColonne déplacement en colonne à chaque étape
     * @param symbole symbole du joueur ("R" ou "J")
     * @return nombre de jetons alignés dans la direction
     */
    private int compterAlignes(int ligne, int colonne, int deltaLigne, int deltaColonne, String symbole) {
        int count = 0;
        int l = ligne + deltaLigne;
        int c = colonne + deltaColonne;
        while (l >= 0 && l < LIGNES && c >= 0 && c < COLONNES && grille[l][c].equals(symbole)) {
            count++;
            l += deltaLigne;
            c += deltaColonne;
        }
        return count;
    }

    /**
     * Vérifie si la grille ne contient p^lus aucune cellule vide
     * et donc si, l'entièreté de la grille est pleine ou non.
     * @return true si la grille est pleine, sinon false.
     */
    public boolean estGrillePleine() {
        for (int j = 0; j < COLONNES; j++) {
            if (grille[0][j].equals(".")) return false;
        }
        return true;
    }

    // Getters et setters pour accéder aux attributs privés

    /**
     * @return valeur de grille
     */
    public String[][] getGrille() { 
    	return grille; 
    }
    
    /**
     * @return valeur de rougeJoue
     */
    public boolean getRougeJoue() { 
    	return rougeJoue; 
    }
    
    /**
     * @param rougeJoue, indique si c'est au joueur rouge de jouer.
     */
    public void setRougeJoue(boolean rougeJoue) { 
    	this.rougeJoue = rougeJoue; 
    }
    
    /**
     * @return valeur de partieTerminee
     */
    public boolean getPartieTerminee() { 
    	return partieTerminee; 
    }
    
    /**
     * @param partieTerminee, indique si la partie est terminée ou non.
     */
    public void setPartieTerminee(boolean partieTerminee) { 
    	this.partieTerminee = partieTerminee; 
    }
    
    /**
     * Exporte l'état de la partie dans un fichier texte.
     * Le fichier contient les informations des joueurs, la grille, etc.
     * @param nomFichier chemin du fichier à créer
     */
    public void exporterPartieTxt(String nomFichier) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(nomFichier));

            writer.write("=== Export de la Partie ===\n");
            writer.write("Joueur 1 : " + pseudoJoueur1 + " (" + couleurJoueur1 + ")\n");
            writer.write("Joueur 2 : " + pseudoJoueur2 + " (" + couleurJoueur2 + ")\n");
            writer.write("Joueur qui a commencé : " + ParametresPartie.getJoueurCommence() + "\n");
            writer.write("Couleur de la grille : " + couleurGrille + "\n\n");

            writer.write("Grille de jeu :\n");
            for (int i = 0; i < LIGNES; i++) {
                for (int j = 0; j < COLONNES; j++) {
                    writer.write(grille[i][j] + " ");
                }
                writer.write("\n");
            }

            writer.write("\n=== Fin de l'export ===\n");
            writer.close();

            System.out.println("Exportation réussie dans " + nomFichier);
        } catch (IOException e) {
            System.err.println("Erreur d'exportation : " + e.getMessage());
        }
    }
    
    /**
     * Modifie la grille avec une nouvelle grille.
     * @param nouvelleGrille la nouvelle grille à utiliser
     */
    public void setGrille(String[][] nouvelleGrille) {
        this.grille = nouvelleGrille;
    }
    
}
