/**
 * CEchangeurDeVue.java                10/05/2025
 * 
 * IUT de Rodez, 2024-2025, aucun copyright
 */
package iut.info1.sae201.vue;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Cette classe permet de gérer le changement de vue (écran) 
 * dans une application JavaFX.
 * 
 * @author Toan Hery
 * @author Enzo Dumas
 * @author Nathael Dalle
 * @author Thomas Bourgougnon
 */
public class EchangeurDeVue {

    /**
     * La scène actuellement affichée dans l'application.
     */
    private static Scene sceneCourante;

    /**
     * Définit la scène courante sur laquelle les vues vont être échangées.
     * 
     * @param nouvelleScene la nouvelle scène à utiliser comme scène courante
     */
    public static void setSceneCourante(Scene nouvelleScene) {
        sceneCourante = nouvelleScene;
    }

    /**
     * Change la vue affichée dans la scène courante en fonction du code de la vue.
     * 
     * @param codeVue le code correspondant à la vue à afficher
     * @throws IllegalStateException si la scène courante n'est pas encore définie
     */
    public static void echangerAvec(int codeVue) {
        // Vérifie que la scène courante est définie avant de changer la vue
        if (sceneCourante == null) {
            throw new IllegalStateException("Échange de vue impossible. Aucune scène courante n'est définie.");
        }

        try {
            // Charge le fichier FXML correspondant à la vue donnée
            FXMLLoader loader = new FXMLLoader(EchangeurDeVue.class.getResource(EnsembleDesVues.getNomVue(codeVue)));
            Parent racine = loader.load();

            // Remplace la racine de la scène courante par la nouvelle vue chargée
            sceneCourante.setRoot(racine);
        } catch (IOException e) {
            // Affiche un message d'erreur si le chargement du fichier FXML échoue
            System.err.println("Échec du chargement de la vue avec le code " + codeVue);
            e.printStackTrace();
        }
    }
}
