package iut.info1.sae201.vue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class EchangeurDeVue {

    private static Map<Integer, Parent> cache = new HashMap<>();
    private static Scene sceneCourante;

    public static void setSceneCourante(Scene nouvelleScene) {
        sceneCourante = nouvelleScene;
    }

    public static void echangerAvec(int codeVue) {
        if (sceneCourante == null) {
            throw new IllegalStateException("Echange de vue impossible. Pas de scène courante.");
        }
        try {
            Parent racine;
            if (cache.containsKey(codeVue)) {
                racine = cache.get(codeVue);
            } else {
                racine = FXMLLoader.load(EchangeurDeVue.class.getResource(EnsembleDesVues.getNomVue(codeVue)));
                cache.put(codeVue, racine);
            }
            sceneCourante.setRoot(racine);
        } catch (IOException e) {
            System.out.println("Echec du chargement de la vue de code " + codeVue);
            e.printStackTrace();
        }
    }
}
