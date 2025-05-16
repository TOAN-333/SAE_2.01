package iut.info1.sae201;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MenuController {
    
    @FXML
    private Button btnJoueurVsJoueur;
    @FXML
    private Button btn_Parametre;
    @FXML
    private Button btn_Menu;
    
    // Méthode générique améliorée pour charger les vues
    private void loadView(String fxmlPath) {
        try {
            System.out.println("Chargement de la vue: " + fxmlPath);
            
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = getCurrentStage();
            
            if (stage == null) {
                // Si aucun stage n'est trouvé (cas des tests ou nouvelle fenêtre)
                stage = new Stage();
                stage.setScene(new Scene(root));
            } else {
                // Remplace la scène existante
                stage.setScene(new Scene(root));
            }
            
            stage.show();
            
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de " + fxmlPath + ":");
            e.printStackTrace();
        }
    }
    
    // Méthode pour obtenir le stage actuel de manière plus fiable
    private Stage getCurrentStage() {
    	
    	// Essayer ensuite avec btn_Parametre
        if (btn_Menu != null && btn_Menu.getScene() != null) {
            return (Stage) btn_Menu.getScene().getWindow();
        }
        
        // Essayer d'abord avec btnJoueurVsJoueur
        if (btnJoueurVsJoueur != null && btnJoueurVsJoueur.getScene() != null) {
            return (Stage) btnJoueurVsJoueur.getScene().getWindow();
        }
        
        // Essayer ensuite avec btn_Parametre
        if (btn_Parametre != null && btn_Parametre.getScene() != null) {
            return (Stage) btn_Parametre.getScene().getWindow();
        }
        
        
        
        // Si aucun stage n'est trouvé
        return null;
    }
    
    @FXML
    private void handleNouvellePartie() {
        loadView("/application/puissance4.fxml");
    }
    
    @FXML
    private void handleParametre() {
        loadView("/application/parametre.fxml");
    }
    
    @FXML
    private void handleMenu() {
        loadView("/application/Menu.fxml");
    }
}