package iut.info1.sae201.vue;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class EchangeurDeVue {

    private static Scene sceneCourante;

    public static void setSceneCourante(Scene nouvelleScene) {
        sceneCourante = nouvelleScene;
    }

    public static void echangerAvec(int codeVue) {
        if (sceneCourante == null) {
            throw new IllegalStateException("Échange de vue impossible. Aucune scène courante n'est définie.");
        }

        try {
            FXMLLoader loader = new FXMLLoader(EchangeurDeVue.class.getResource(EnsembleDesVues.getNomVue(codeVue)));
            Parent racine = loader.load();
            sceneCourante.setRoot(racine);
        } catch (IOException e) {
            System.err.println("Échec du chargement de la vue avec le code " + codeVue);
            e.printStackTrace();
        }
    }
}
