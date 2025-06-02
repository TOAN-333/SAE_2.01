package iut.info1.sae201.modele;

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
    
    // Nouveau constructeur avec paramètres
    public Jeu(Parametres parametres) {
        this.pseudoJoueur1 = parametres.getPseudoJoueur1();
        this.pseudoJoueur2 = parametres.getPseudoJoueur2();
        this.couleurJoueur1 = parametres.getCouleurJoueur1();
        this.couleurJoueur2 = parametres.getCouleurJoueur2();
        this.couleurGrille = parametres.getCouleurGrille();
        initialiserGrille();
        rougeJoue = true; // Joueur 1 commence toujours
        partieTerminee = false;
        
        // Optionnel : afficher dans la console ou logger
        System.out.println("Partie démarrée avec :");
        System.out.println("Joueur 1: " + pseudoJoueur1 + " en " + couleurJoueur1);
        System.out.println("Joueur 2: " + pseudoJoueur2 + " en " + couleurJoueur2);
        System.out.println("Couleur grille: " + couleurGrille);
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

    // Getters pour les nouveaux attributs si besoin
    public String getPseudoJoueur1() { return pseudoJoueur1; }
    public String getPseudoJoueur2() { return pseudoJoueur2; }
    public String getCouleurJoueur1() { return couleurJoueur1; }
    public String getCouleurJoueur2() { return couleurJoueur2; }
    public String getCouleurGrille() { return couleurGrille; }

    // Getters et setters existants
    public String[][] getGrille() { return grille; }
    public boolean isRougeJoue() { return rougeJoue; }
    public void setRougeJoue(boolean rougeJoue) { this.rougeJoue = rougeJoue; }
    public boolean isPartieTerminee() { return partieTerminee; }
    public void setPartieTerminee(boolean partieTerminee) { this.partieTerminee = partieTerminee; }
}
