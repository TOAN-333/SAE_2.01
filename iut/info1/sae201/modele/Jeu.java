/**
 * Jeu.java                                  02/06/2025
 * 
 * IUT de Rodez, 2024-2025, aucun copyright
 */
package iut.info1.sae201.modele;

/**
 * La classe {@code Jeu} modélise le fonctionnement du jeu Puissance 4.
 * Elle gère la grille de jeu, le placement des jetons, la détection 
 * de victoire, ainsi que les paramètres liés aux joueurs et à la couleur 
 * de la grille.
 * 
 * <p>
 * 		Elle prend en charge une grille de 6 lignes par 7 colonnes.
 * </p>
 * 
 * @author Thomas Bourgougnon
 * @author Nathael Dalle
 * @author Enzo Dumas
 * @author Toan Hery
 */
public class Jeu {

    /** Nombre de lignes de la grille. */
    public static final int LIGNES = 6;

    /** Nombre de colonnes de la grille. */
    public static final int COLONNES = 7;

    /** Grille de jeu représentée par une matrice de chaînes. */
    private String[][] grille;

    /** Indique si c'est au joueur rouge de jouer. */
    private boolean rougeJoue;

    /** Indique si la partie est terminée. */
    private boolean partieTerminee;

    // Paramètres des joueurs
    private String pseudoJoueur1;
    private String pseudoJoueur2;
    
    private String couleurJoueur1;
    private String couleurJoueur2;
    
    private String couleurGrille;

    /**
     * Initialise une nouvelle grille vide et prépare la partie.
     */
    public Jeu() {
        initialiserGrille();
    }

    /**
     *
     * @param parametres les paramètres de la partie (pseudos, couleurs, etc.)
     */
    public Jeu(Parametres parametres) {
        this.pseudoJoueur1 = parametres.getPseudoJoueur1();
        this.pseudoJoueur2 = parametres.getPseudoJoueur2();
        this.couleurJoueur1 = parametres.getCouleurJoueur1();
        this.couleurJoueur2 = parametres.getCouleurJoueur2();
        this.couleurGrille = parametres.getCouleurGrille();

        initialiserGrille();
        rougeJoue = true;
        partieTerminee = false;

        // Debug console (peut être remplacé par un Logger)
        System.out.println("Partie démarrée avec :");
        System.out.println("Joueur 1: " + pseudoJoueur1 + " en " + couleurJoueur1);
        System.out.println("Joueur 2: " + pseudoJoueur2 + " en " + couleurJoueur2);
        System.out.println("Couleur grille: " + couleurGrille);
    }

    /**
     * Initialise la grille de jeu avec des cellules vides.
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
     * Tente de placer un jeton dans la colonne indiquée.
     *
     * @param colonne numéro de la colonne (1 à 7)
     * @return l'indice de ligne où le jeton a été placé, ou -1 si la colonne est pleine
     */
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

    /**
     * Vérifie s'il y a une victoire depuis la position spécifiée.
     *
     * @param ligne ligne du dernier jeton placé
     * @param colonne colonne du dernier jeton placé
     * @return {@code true} si un alignement gagnant est détecté, sinon {@code false}
     */
    public boolean verifierVictoire(int ligne, int colonne) {
        String symbole = grille[ligne][colonne];
        return (compterAlignes(ligne, colonne, 0, 1, symbole) + 
                compterAlignes(ligne, colonne, 0, -1, symbole) >= 3 ||
                compterAlignes(ligne, colonne, 1, 0, symbole) >= 3 ||
                compterAlignes(ligne, colonne, 1, 1, symbole) + 
                compterAlignes(ligne, colonne, -1, -1, symbole) >= 3 ||
                compterAlignes(ligne, colonne, 1, -1, symbole) + 
                compterAlignes(ligne, colonne, -1, 1, symbole) >= 3);
    }

    /**
     * Compte le nombre de jetons alignés dans une direction donnée.
     *
     * @param ligne ligne de départ
     * @param colonne colonne de départ
     * @param deltaLigne direction verticale
     * @param deltaColonne direction horizontale
     * @param symbole symbole à compter ("R" ou "J")
     * @return le nombre de jetons alignés consécutifs dans la direction donnée
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
     * Vérifie si la grille est complètement remplie.
     *
     * @return {@code true} si la grille est pleine, sinon {@code false}
     */
    public boolean estGrillePleine() {
        for (int j = 0; j < COLONNES; j++) {
            if (grille[0][j].equals(".")) return false;
        }
        return true;
    }

    // Getters pour les paramètres joueurs

    public String getPseudoJoueur1() { 
    	return pseudoJoueur1; 
    }

    public String getPseudoJoueur2() { 
    	return pseudoJoueur2; 
    }

    public String getCouleurJoueur1() { 
    	return couleurJoueur1; 
    }

    public String getCouleurJoueur2() { 
    	return couleurJoueur2; 
    }

    public String getCouleurGrille() { 
    	return couleurGrille; 
    }

    // Getters et setters

    public String[][] getGrille() { 
    	return grille; 
    }

    public boolean isRougeJoue() { 
    	return rougeJoue; 
    }

    public void setRougeJoue(boolean rougeJoue) { 
    	this.rougeJoue = rougeJoue; 
    }

    public boolean isPartieTerminee() { 
    	return partieTerminee; 
    }

    public void setPartieTerminee(boolean partieTerminee) { 
    	this.partieTerminee = partieTerminee; 
    }
}
