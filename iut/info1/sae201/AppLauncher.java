package iut.info1.sae201;

import iut.info1.sae201.vue.EchangeurDeVue;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("vue/Menu.fxml"));
        Scene scene = new Scene(root);
        EchangeurDeVue.setSceneCourante(scene);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Mon application");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}