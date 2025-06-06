/**
 * ControleurFenetreJeu.java                04/06/2025
 * 
 * IUT de Rodez, 2024-2025, aucun copyright
 */
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

/**
 * Contrôleur de la fenêtre de jeu.
 * Gère l'interface utilisateur, les actions et la logique de la partie.
 * 
 * @author Toan Hery
 * @author Enzo Dumas
 * @author Nathael Dalle
 * @author Thomas Bourgougnon
 */
public class ControleurFenetreJeu {

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

    /**
     * Initialise le contrôleur de la fenêtre de jeu.
     * Cette méthode est appelée automatiquement au chargement de la vue.
     */
    @FXML
    public void initialize() {
        if (ParametresPartie.isImportation()) {
            Jeu model = new Jeu();
            model = new Jeu();
            model.initialiserGrilleImporter(ParametresPartie.getGrilleImportee());

            GestionInterface gestionInterface = new GestionInterface(gridPane, labelJoueurActuel);

            System.out.println("Appel de afficherGrille()");
            gestionInterface.afficherGrille(model.getGrille());
            gestionInterface.initialiserBoutons();

            pseudoJ1.setText(ParametresPartie.getJoueur1());
            pseudoJ2.setText(ParametresPartie.getJoueur2());

            gestionChronos = new GestionChronos(labelChronoPartie, labelChronoJoueur1, labelChronoJoueur2);
            gestionPartie = new GestionPartie(model, gridPane, btnRejouer, labelJoueurActuel, gestionChronos);
            gestionSauvegarde = new GestionSauvegarde(model);

            gestionChronos.CompareChronos();
            gestionChronos.demarrerMiseAJourChronos();
            gestionChronos.demarrerChronoJoueur1();

        } else {
            // Partie normale (non importée)
            Jeu model = new Jeu();
            gestionChronos = new GestionChronos(labelChronoPartie, labelChronoJoueur1, labelChronoJoueur2);
            gestionPartie = new GestionPartie(model, gridPane, btnRejouer, labelJoueurActuel, gestionChronos);
            gestionSauvegarde = new GestionSauvegarde(model);

            GestionInterface gestionInterface = new GestionInterface(gridPane, labelJoueurActuel);

            System.out.println("Appel de afficherGrille()");
            gestionInterface.afficherGrille(model.getGrille());
            gestionInterface.initialiserBoutons();

            pseudoJ1.setText(ParametresPartie.getJoueur1());
            pseudoJ2.setText(ParametresPartie.getJoueur2());

            gestionPartie.initialiserBoutons();
            gestionChronos.CompareChronos();
            gestionChronos.demarrerMiseAJourChronos();
            gestionChronos.demarrerChronoJoueur1();
            gestionChronos.demarrerChronoPartie();
        }
    }

    /**
     * Gère l'action lorsque l'utilisateur clique sur le bouton "Rejouer".
     * @param event l'événement de clic sur le bouton
     */
    @FXML
    private void handleNouvellePartie(ActionEvent event) {
        pauseJeu();
        if (!gestionPartie.confirmerAbandon()) {
            event.consume();
            gestionChronos.demarrerChronoPartie();
            gestionChronos.CompareChronos();
            return;
        }

        gestionChronos.reset();
        Jeu model = new Jeu();
        gestionPartie = new GestionPartie(model, gridPane, btnRejouer, labelJoueurActuel, gestionChronos);
        gestionSauvegarde = new GestionSauvegarde(model);

        GestionInterface gestionInterface = new GestionInterface(gridPane, labelJoueurActuel);

        System.out.println("Appel de afficherGrille()");
        gestionInterface.afficherGrille(model.getGrille());
        gestionInterface.initialiserBoutons();

        pseudoJ1.setText(ParametresPartie.getJoueur1());
        pseudoJ2.setText(ParametresPartie.getJoueur2());

        gestionPartie.initialiserBoutons();
        gestionChronos.CompareChronos();
        gestionChronos.demarrerMiseAJourChronos();
        gestionChronos.demarrerChronoJoueur1();
        gestionChronos.demarrerChronoPartie();
    }

    /**
     * Gère l'action lorsque l'utilisateur clique sur le bouton "Menu".
     * @param event l'événement de clic sur le bouton
     */
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

    /**
     * Gère les clics sur les boutons de la grille de jeu.
     * @param event l'événement de clic sur un bouton de la grille
     */
    @FXML
    public void handleButtonClick(ActionEvent event) {
        gestionPartie.gererCoup((Button) event.getSource());
    }

    /**
     * Gère l'action lorsque l'utilisateur clique sur le bouton "Exporter".
     * @param event l'événement de clic sur le bouton
     */
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

    /**
     * Met en pause tous les chronomètres du jeu.
     */
    private void pauseJeu() {
        gestionChronos.arreterTousLesChronos();
    }

    /**
     * Affiche une alerte contenant les règles du jeu Puissance 4.
     */
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