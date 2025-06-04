package iut.info1.sae201.modele;

public class Jeu {
    private int[][] grille;
    private boolean rougeJoue;
    private boolean partieTerminee;

    public static final int LIGNES = 6;
    public static final int COLONNES = 7;

    public Jeu() {
        grille = new int[LIGNES][COLONNES];
        initialiserGrille();
        rougeJoue = true; // Le joueur rouge commence
        partieTerminee = false;
    }

    public void initialiserGrille() {
        for (int i = 0; i < LIGNES; i++) {
            for (int j = 0; j < COLONNES; j++) {
                grille[i][j] = 0; // 0 = vide, 1 = rouge, 2 = jaune
            }
        }
        partieTerminee = false;
        rougeJoue = true;
    }

    /**
     * Place un jeton dans la colonne donnée.
     * @param colonne la colonne où placer le jeton (0-based)
     * @return la ligne où le jeton a été placé, ou -1 si la colonne est pleine ou invalide
     */
    public int placerJeton(int colonne) {
        if (colonne < 0 || colonne >= COLONNES || partieTerminee) return -1;

        for (int ligne = LIGNES - 1; ligne >= 0; ligne--) {
            if (grille[ligne][colonne] == 0) {
                grille[ligne][colonne] = rougeJoue ? 1 : 2;
                return ligne;
            }
        }
        return -1; // Colonne pleine
    }

    public boolean verifierVictoire(int ligne, int colonne) {
        if (!estDansGrille(ligne, colonne)) return false;

        int joueur = grille[ligne][colonne];
        if (joueur == 0) return false;

        // Vérifie 4 directions (vertical, horizontal, diagonales)
        return verifierDirection(ligne, colonne, 1, 0, joueur) || // vertical
               verifierDirection(ligne, colonne, 0, 1, joueur) || // horizontal
               verifierDirection(ligne, colonne, 1, 1, joueur) || // diagonale \
               verifierDirection(ligne, colonne, 1, -1, joueur);  // diagonale /
    }

    private boolean verifierDirection(int ligne, int colonne, int dLigne, int dColonne, int joueur) {
        int count = 1;
        int l = ligne + dLigne;
        int c = colonne + dColonne;

        while (estDansGrille(l, c) && grille[l][c] == joueur) {
            count++;
            l += dLigne;
            c += dColonne;
        }

        l = ligne - dLigne;
        c = colonne - dColonne;
        while (estDansGrille(l, c) && grille[l][c] == joueur) {
            count++;
            l -= dLigne;
            c -= dColonne;
        }
        return count >= 4;
    }

    private boolean estDansGrille(int l, int c) {
        return l >= 0 && l < LIGNES && c >= 0 && c < COLONNES;
    }

    public boolean estGrillePleine() {
        for (int c = 0; c < COLONNES; c++) {
            if (grille[0][c] == 0) return false;
        }
        return true;
    }

    public boolean estGrillePleineColonne(int colonne) {
        return grille[0][colonne] != 0;
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
