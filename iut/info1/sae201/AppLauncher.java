/**
 * AppLauncher.java                07/05/2025
 * 
 * IUT de Rodez, 2024-2025, aucun copyright
 */
package iut.info1.sae201;

import iut.info1.sae201.vue.EchangeurDeVue;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Lanceur principal de l'application JavaFX.
 * 
 * @author Toan Hery
 * @author Enzo Dumas
 * @author Nathael Dalle
 * @author Thomas Bourgougnon
 */
public class AppLauncher extends Application {

    /**
     * Point d'entrée de l'application JavaFX.
     * @param primaryStage la fenêtre principale de l'application
     * @throws Exception si le chargement du fichier FXML échoue
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("vue/Menu.fxml"));
        Scene scene = new Scene(root);
        EchangeurDeVue.setSceneCourante(scene);
        scene.getStylesheets().add(getClass().getResource("vue/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Mon application");
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    /**
     * Méthode principale qui lance l'application.
     * @param args les arguments de la ligne de commande
     */
    public static void main(String[] args) {
        launch(args);
    }
}
