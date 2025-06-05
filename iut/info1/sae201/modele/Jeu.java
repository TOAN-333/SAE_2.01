package iut.info1.sae201.modele;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Jeu {
    
    public static final int LIGNES = 6;
    public static final int COLONNES = 7;
    
    private String[][] grille;
    private boolean rougeJoue; 
    private boolean partieTerminee;

    // Nouveaux attributs pour stocker les paramètres joueurs
    private String pseudoJoueur1;
    private String pseudoJoueur2;
    private String couleurJoueur1;
    private String couleurJoueur2;
    private String couleurGrille;

    // Constructeur par défaut
    public Jeu() {
        initialiserGrille();
    }

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
    
    public void initialiserGrilleImporter(String[][] grilleImportee) {
        if (grilleImportee.length != LIGNES || grilleImportee[0].length != COLONNES) {
            throw new IllegalArgumentException("Grille importée invalide");
        }
        this.grille = grilleImportee;

        // Exemple simple pour déterminer le joueur courant selon nombre de jetons
        int countR = 0, countJ = 0;
        for (int i = 0; i < LIGNES; i++) {
            for (int j = 0; j < COLONNES; j++) {
                if ("R".equals(grille[i][j])) countR++;
                else if ("J".equals(grille[i][j])) countJ++;
            }
        }
        this.rougeJoue = countR <= countJ;

        this.partieTerminee = false; // Ou calculer si victoire
    }

    public int placerJeton(int colonne) {
        int colIndex = colonne - 1;
        for (int i = LIGNES - 1; i >= 0; i--) {
            if (grille[i][colIndex].equals(".")) {
                grille[i][colIndex] = rougeJoue ? "R" : "J";
                return i;
            }
        }
        return -1;
    }

    public boolean verifierVictoire(int ligne, int colonne) {
        String symbole = grille[ligne][colonne];
        return (compterAlignes(ligne, colonne, 0, 1, symbole) + compterAlignes(ligne, colonne, 0, -1, symbole) >= 3 ||
               (compterAlignes(ligne, colonne, 1, 0, symbole) >= 3) ||
               (compterAlignes(ligne, colonne, 1, 1, symbole) + compterAlignes(ligne, colonne, -1, -1, symbole) >= 3) ||
               (compterAlignes(ligne, colonne, 1, -1, symbole) + compterAlignes(ligne, colonne, -1, 1, symbole) >= 3));
    }

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

    public boolean estGrillePleine() {
        for (int j = 0; j < COLONNES; j++) {
            if (grille[0][j].equals(".")) return false;
        }
        return true;
    }

    // Getters et setters existants
    public String[][] getGrille() { return grille; }
    public boolean isRougeJoue() { return rougeJoue; }
    public void setRougeJoue(boolean rougeJoue) { this.rougeJoue = rougeJoue; }
    public boolean isPartieTerminee() { return partieTerminee; }
    public void setPartieTerminee(boolean partieTerminee) { this.partieTerminee = partieTerminee; }
    
    
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
    
    public void setGrille(String[][] nouvelleGrille) {
        this.grille = nouvelleGrille;
    }
    
    

}
