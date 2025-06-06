package iut.info1.sae201.controlleur;

import iut.info1.sae201.modele.GestionChronos;
import iut.info1.sae201.modele.GestionInterface;
import iut.info1.sae201.modele.GestionPartie;
import iut.info1.sae201.modele.GestionSauvegarde;
import iut.info1.sae201.modele.Jeu;
import iut.info1.sae201.modele.ParametresPartie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class ControlleurFenetreJeu {

    private Jeu model = new Jeu();

    private GestionChronos gestionChronos;
    private GestionPartie gestionPartie;
    private GestionSauvegarde gestionSauvegarde;

    @FXML private GridPane gridPane;
    @FXML private Button btnRejouer;
    @FXML private Button btn_Menu;
    @FXML private Button btn_Exporter;
    @FXML private Button btnSauvegarder;
    @FXML private Button btnCharger;
    @FXML private Label labelJoueurActuel;

    @FXML private Label labelChronoPartie;
    @FXML private Label labelChronoJoueur1;
    @FXML private Label labelChronoJoueur2;
    @FXML private Label pseudoJ1;
    @FXML private Label pseudoJ2;

    @FXML
    public void initialize() {
    	if (ParametresPartie.isImportation()) {
    		model = new Jeu();
    	    model.initialiserGrilleImporter(ParametresPartie.getGrilleImportee());

	        GestionInterface gestionInterface = new GestionInterface(gridPane, labelJoueurActuel);
	        gestionInterface.initialiserBoutons(); // Important si tu veux tout réinitialiser avant
	        System.out.println("Appel de afficherGrille()");
	        gestionInterface.afficherGrille(model.getGrille());

	        pseudoJ1.setText(ParametresPartie.getJoueur1());
	        pseudoJ2.setText(ParametresPartie.getJoueur2());

	        gestionChronos = new GestionChronos(labelChronoPartie, labelChronoJoueur1, labelChronoJoueur2);
	        gestionPartie = new GestionPartie(model, gridPane, btnRejouer, labelJoueurActuel, gestionChronos);
	        gestionSauvegarde = new GestionSauvegarde(model);
	        
	        gestionChronos.CompareChronos();
	        gestionChronos.demarrerMiseAJourChronos();
	        gestionChronos.demarrerChronoJoueur1();
	        gestionChronos.demarrerChronoPartie();

	        //ParametresPartie.setImportation(false);
        } else {
            // Partie normale (non importée)
            gestionChronos = new GestionChronos(labelChronoPartie, labelChronoJoueur1, labelChronoJoueur2);
            gestionPartie = new GestionPartie(model, gridPane, btnRejouer, labelJoueurActuel, gestionChronos);
            gestionSauvegarde = new GestionSauvegarde(model);

            pseudoJ1.setText(ParametresPartie.getJoueur1());
            pseudoJ2.setText(ParametresPartie.getJoueur2());

            gestionPartie.initialiserBoutons();
            gestionChronos.CompareChronos();
            gestionChronos.demarrerMiseAJourChronos();
            gestionChronos.demarrerChronoJoueur1();
            gestionChronos.demarrerChronoPartie();
        }
    }


    @FXML
    private void handleNouvellePartie(ActionEvent event) {
    	pauseJeu();
        if (!gestionPartie.confirmerAbandon()) {
            event.consume();
            gestionChronos.demarrerChronoPartie();
            gestionChronos.CompareChronos();
            return;
        }
        gestionPartie.demarrerNouvellePartie();
        
        // Démarrer le chrono de la partie et d’un joueur
        gestionChronos.demarrerChronoJoueur1(); 
        gestionChronos.demarrerChronoPartie();
    }

    @FXML
    private void handleMenu(ActionEvent event) {
    	pauseJeu();
        if (!gestionPartie.confirmerAbandon()) {
            event.consume();
            gestionChronos.demarrerChronoPartie();
            gestionChronos.CompareChronos();
            return;
        }
        gestionPartie.retourMenu();
    }

    @FXML
    public void handleButtonClick(ActionEvent event) {
        gestionPartie.gererCoup((Button) event.getSource());
    }

    @FXML
    private void handleExporter(ActionEvent event) {
    	pauseJeu();
        if (!gestionPartie.confirmerExporter()) {
            event.consume();
            gestionChronos.demarrerChronoPartie();
            gestionChronos.CompareChronos();
            return;
        }
        gestionSauvegarde.exporterPartie(gridPane.getScene().getWindow());
        gestionChronos.demarrerChronoPartie();
        gestionChronos.CompareChronos();
    }
    
    private void pauseJeu() {
        gestionChronos.arreterTousLesChronos();
    }
    
    @FXML
    private void handleRegle() {
    	pauseJeu();
    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
    	alert.setTitle("Règles du jeu");
    	alert.setHeaderText("Règles simples du Puissance 4");
    	alert.setContentText(
    	    "- Le jeu se joue à deux joueurs.\n" +
    	    "- Chaque joueur joue à tour de rôle.\n" +
    	    "- À chaque tour, un joueur place un jeton dans une colonne.\n" +
    	    "- Le jeton tombe en bas de la colonne.\n" +
    	    "- Le but est d’aligner 4 jetons de sa couleur :\n" +
    	    "   -> horizontalement,\n" +
    	    "   -> verticalement,\n" +
    	    "   -> ou en diagonale.\n" +
    	    "- Le premier à aligner 4 jetons gagne.\n" +
    	    "- Si la grille est pleine sans gagnant, c’est un match nul."
    	);
    	alert.showAndWait();
    	gestionChronos.demarrerChronoPartie();
        gestionChronos.CompareChronos();

    }
    
}
