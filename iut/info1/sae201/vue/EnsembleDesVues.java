/**
 * EnsembleDesVues.java                       02/06/2025
 * 
 * IUT de Rodez, 2024-2025, aucun copyright
 */

package iut.info1.sae201.vue;

/**
 * La classe {@code EnsembleDesVues} centralise les différentes vues de 
 * l'application. Chaque vue est associée à un identifiant entier constant
 * utilisé pour référencer un fichier FXML correspondant.
 * 
 * <p>
 *     Cette classe permet de récupérer le nom du fichier FXML 
 *     associé à un code vue donné.
 * </p>
 * 
 * @author Thomas Bourgougnon
 * @author Nathael Dalle
 * @author Enzo Dumas
 * @author Toan Hery
 */
public class EnsembleDesVues {

    /** Code représentant la vue du menu principal. */
    public static final int VUE_MENU = 0;

    /** Code représentant la vue du jeu Puissance 4. */
    public static final int VUE_PUISSANCE4 = 1;

    /** Code représentant la vue des paramètres. */
    public static final int VUE_PARAMETRE = 2;

    /** Tableau contenant les noms des fichiers FXML correspondant aux vues. */
    private static final String[] NOM_DES_VUES = {
        "Menu.fxml", "Puissance4.fxml", "Parametre.fxml"
    };

    /**
     * Retourne le nom du fichier FXML associé au code de la vue fourni.
     *
     * @param codeVue le code de la vue (doit être compris entre 0 et {@code NOM_DES_VUES.length - 1})
     * @return le nom du fichier FXML correspondant à la vue
     * @throws IllegalArgumentException si le code de la vue est invalide
     */
    public static String getNomVue(int codeVue) {
        if (codeVue < 0 || codeVue >= NOM_DES_VUES.length) {
            throw new IllegalArgumentException("Code vue " + codeVue + " invalide");
        }
        return NOM_DES_VUES[codeVue];
    }
}
