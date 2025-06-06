/**
 * ParametresPartie.java                05/06/2025
 * 
 * IUT de Rodez, 2024-2025, aucun copyright
 */
package iut.info1.sae201.modele;

/**
 * Classe qui gère les paramètres globaux de la partie.
 * 
 * Contient les noms des joueurs, qui commence la partie,
 * ainsi que les informations relatives à une éventuelle 
 * importation de grille.
 * 
 * @author Toan Hery
 * @author Enzo Dumas
 * @author Nathael Dalle
 * @author Thomas Bourgougnon
 */
public class ParametresPartie {
    private static String joueur1 = "Rouge";    // Nom ou couleur du joueur 1
    private static String joueur2 = "Jaune";    // Nom ou couleur du joueur 2
    private static String jCommence = "Rouge";  // Joueur qui commence la partie

    /**
     * Retourne le nom/couleur du joueur 1.
     */
    public static String getJoueur1() { return joueur1; }

    /**
     * Définit le nom/couleur du joueur 1.
     */
    public static void setJoueur1(String j1) { joueur1 = j1; }

    /**
     * Retourne le nom/couleur du joueur 2.
     */
    public static String getJoueur2() { return joueur2; }

    /**
     * Définit le nom/couleur du joueur 2.
     */
    public static void setJoueur2(String j2) { joueur2 = j2; }

    /**
     * Retourne le joueur qui commence la partie.
     */
    public static String getJoueurCommence() { return jCommence; }

    /**
     * Définit le joueur qui commence la partie.
     */
    public static void setJoueurCommence(String choixJeu) { jCommence = choixJeu; }
    
    private static boolean importation = false;        // Indique si une grille a été importée
    private static String[][] grilleImportee;          // Stocke la grille importée

    /**
     * Indique si une grille est importée.
     */
    public static boolean isImportation() {
        return importation;
    }

    /**
     * Définit si une grille est importée.
     */
    public static void setImportation(boolean value) {
        importation = value;
    }

    /**
     * Retourne la grille importée.
     */
    public static String[][] getGrilleImportee() {
        return grilleImportee;
    }

    /**
     * Définit la grille importée.
     */
    public static void setGrilleImportee(String[][] grille) {
        grilleImportee = grille;
    }
    
}
