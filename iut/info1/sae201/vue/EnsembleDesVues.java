/**
 * EnsembleDesVues.java                10/05/2025
 * 
 * IUT de Rodez, 2024-2025, aucun copyright
 */
package iut.info1.sae201.vue;

/**
 * Cette classe contient la liste des vues disponibles dans l'application,
 * identifiées par des codes entiers, ainsi que les noms des fichiers FXML associés.
 * 
 * Elle fournit aussi une méthode pour récupérer le nom d'une vue à partir de son code.
 * 
 * @author Toan Hery
 * @author Enzo Dumas
 * @author Nathael Dalle
 * @author Thomas Bourgougnon
 */
public class EnsembleDesVues {

    /** Code pour la vue du menu principal */
    public static final int VUE_MENU = 0;

    /** Code pour la vue du jeu Puissance4 */
    public static final int VUE_PUISSANCE4 = 1;

    /** Tableau contenant les noms des fichiers FXML des vues */
    private static final String[] NOM_DES_VUES = {
        "Menu.fxml", "puissance4.fxml"
    };

    /**
     * Retourne le nom du fichier FXML correspondant au code de la vue donné.
     * 
     * @param codeVue le code entier de la vue
     * @return le nom du fichier FXML associé à cette vue
     * @throws IllegalArgumentException si le code de la vue est invalide (hors limite)
     */
    public static String getNomVue(int codeVue) {
        if (codeVue < 0 || codeVue >= NOM_DES_VUES.length) {
            throw new IllegalArgumentException("Code vue " + codeVue + " invalide");
        }
        return NOM_DES_VUES[codeVue];
    }
    
}
