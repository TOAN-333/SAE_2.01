package iut.info1.sae201.controlleur;

//import application.modele.Parametres;
import iut.info1.sae201.vue.EchangeurDeVue;
import iut.info1.sae201.vue.EnsembleDesVues;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ControlleurFenetreParametre {

    @FXML
    private TextField pseudoJoueur1;

    @FXML
    private TextField pseudoJoueur2;

    @FXML
    private ComboBox<String> param_colorJ1;

    @FXML
    private ComboBox<String> param_colorJ2;

    @FXML
    private ComboBox<String> param_colorGrille;

    @FXML
    private void initialize() {
        param_colorJ1.getItems().addAll("rouge", "bleu", "green", "jaune");
        param_colorJ2.getItems().addAll("rouge", "bleu", "vert", "jaune");
        param_colorGrille.getItems().addAll("bleu", "gris", "noir", "blanc");

        param_colorJ1.setValue("rouge");
        param_colorJ2.setValue("jaune");
        param_colorGrille.setValue("bleu");
        
        // Valeurs par défaut pour pseudoJoueur1 et pseudoJoueur2
        pseudoJoueur1.setText("Joueur 1");
        pseudoJoueur2.setText("Joueur 2");
    }

    /*
    // Méthode pour créer l'objet Parametres
    public Parametres recupererInfos() {
    	
        String pseudo1 = pseudoJoueur1.getText(); // ==> null
        String pseudo2 = pseudoJoueur2.getText();
        String couleurJ1 = param_colorJ1.getValue();
        String couleurJ2 = param_colorJ2.getValue();
        String couleurGrille = param_colorGrille.getValue();
        
        Parametres parametres = new Parametres("joueur 1", "joueur 2", couleurJ1, couleurJ2, couleurGrille);
        System.out.print(parametres);

        return parametres;
    }
    */
	 @FXML
	 private void handleMenu() {
		 
		 
		 //parametres.setPseudoJoueur1(pseudoJoueur1.getText());
		 
		 EchangeurDeVue.echangerAvec(EnsembleDesVues.VUE_MENU);
		 
	     // System.out.println(recupererInfos().getPseudoJoueur1());
	 }

}
