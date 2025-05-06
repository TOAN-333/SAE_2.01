/**
 * Date.java                     30/04/2025
 * 
 * IUT de Rodez, 2024-2025, aucun copyright
 */
package iut.info1.sae201;

/**
 * 
 * Cette classe permet de créer des instances "pion" construits
 * selon les caractéristiques précisées en tant qu'argument 
 * du constructeur.
 * @author Bourgougnon Thomas
 * @author Dalle Nathael
 * @author Dumas Enzo
 * @author Hery Toan
 */
public class Jeu {
    
    private static final int LIGNES = 7;
    
    private static final int COLONNES = 6;
    
    private int coX;
    private int coY;
    private int jaune;
    private int rouge;
    String couleur;
    
    int[][]plateau = {
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0}
    };

    /**
     * TODO commenter l'etat initial du constructeur
     * @param coX
     * @param coY
     * @param couleur
     */
    public Jeu(int coX, int coY, String couleur) {
        super();
        this.coX = coX;
        this.coY = coY;
        this.couleur = couleur;
    }
    
    /**
     * TODO commenter la fonctionnalité de la méthode
     */
    public void bvureyvb() {
        while(rouge < 4 || jaune < 4) {
            //empty body
        }
    }
    
    /**
     * TODO commenter la fonctionnalité de la méthode
     * @param coX
     * @param coY
     * @return true si un des deux joueurs gagne
     */
    public boolean verifierVictoire(int coX, int coY) {
        //TODO Faire la méthode de vérification de victoire
        return false;  
        
    }
    
    
    /**
     * TODO commenter la fonctionnalité de la méthode
     * @return 
     */
    private int compterALignes(int coX, int coY, int dCoX, int dCoY, char symbole) {
        int count;
        int l = coX + dCoX;
        int c = coY + dCoY;
        
        for(count = 0; l >= 0 && 1 < LIGNES && c>= 0 && c < COLONNES 
                && plateau[l][c] == symbole; count++) {
            l += dCoX;
            c += dCoY;
        }
        return count;
    }
}