/**
 * EchangeurDeVue.java                       02/06/2025
 * 
 * IUT de Rodez, 2024-2025, aucun copyright
 */
package iut.info1.sae201.vue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * La classe {@code EchangeurDeVue} gère le changement de vue au sein de 
 * l'application JavaFX.
 * 
 * Elle permet de charger des fichiers FXML pour améliorer les performances 
 * lors des changements de scène.
 * 
 * <p>
 * 		Cette classe utilise {@link EnsembleDesVues} pour déterminer le 
 * 		nom du fichier FXML à charger à partir d'un code de vue donné.
 * </p>
 * 
 * <p>
 * 		Elle requiert qu'une {@link Scene} courante soit définie avant 
 * 		tout échange de vue.
 * </p>
 * 
 * @author Thomas Bourgougnon
 * @author Nathael Dalle
 * @author Enzo Dumas
 * @author Toan Hery
 */
public class EchangeurDeVue {

    /** Cache des vues déjà chargées, associées à leur code vue. */
    private static Map<Integer, Parent> cache = new HashMap<>();

    /** Scène JavaFX actuellement affichée. */
    private static Scene sceneCourante;

    /**
     * Définit la scène courante de l'application.
     *
     * @param nouvelleScene la scène à utiliser pour les futurs échanges de vue
     */
    public static void setSceneCourante(Scene nouvelleScene) {
        sceneCourante = nouvelleScene;
    }

    /**
     * Remplace le contenu de la scène courante par celui correspondant 
     * au code de vue donné.
     * 
     * Si la vue a déjà été chargée, elle est récupérée. 
     * 
     * Sinon elle est chargée depuis le fichier FXML correspondant 
     * et ajoutée au cache.
     *
     * @param codeVue le code de la vue à afficher
     * @throws IllegalStateException si la scène courante n'a pas été définie
     */
    public static void echangerAvec(int codeVue) {
        if (sceneCourante == null) {
            throw new IllegalStateException("Échange de vue impossible. Pas de scène courante.");
        }

        try {
            Parent racine;

            // Vérifie si la vue est déjà en cache
            if (cache.containsKey(codeVue)) {
                racine = cache.get(codeVue);
            } else {
                racine = FXMLLoader.load(EchangeurDeVue.class.getResource(
                        EnsembleDesVues.getNomVue(codeVue)));
                cache.put(codeVue, racine);
            }

            // Met à jour la racine de la scène
            sceneCourante.setRoot(racine);

        } catch (IOException e) {
            System.out.println("Échec du chargement de la vue de code " + codeVue);
            e.printStackTrace();
        }
    }
}
