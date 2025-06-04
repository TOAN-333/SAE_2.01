package iut.info1.sae201.modele;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class BoutonPuissance4 {

    private GridPane gridPane;
    private Jeu jeu;

    public BoutonPuissance4(GridPane gridPane, Jeu jeu) {
        this.gridPane = gridPane;
        this.jeu = jeu;
    }

    public void initialiserBoutons() {
        gridPane.getChildren().clear();

        for (int i = 0; i < Jeu.LIGNES; i++) {
            for (int j = 0; j < Jeu.COLONNES; j++) {
                Button bouton = new Button();
                bouton.setId("btn_" + i + "_" + j);
                bouton.setPrefSize(50, 50);
                bouton.getStyleClass().add("cellule");
                bouton.setStyle("-fx-background-color: lightgrey;");

                gridPane.add(bouton, j, i);
            }
        }
    }

    public void mettreAJourBouton(int ligne, int colonne, boolean rouge) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            Integer row = GridPane.getRowIndex(node);
            Integer col = GridPane.getColumnIndex(node);
            if (row != null && col != null && row == ligne && col == colonne && node instanceof Button) {
                Button btn = (Button) node;
                btn.setStyle(rouge ? "-fx-background-color: red;" : "-fx-background-color: yellow;");
                btn.setDisable(true);
            }
        }
    }

    public void desactiverTousLesBoutons() {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (node instanceof Button) {
                ((Button) node).setDisable(true);
            }
        }
    }

    public void reactiverTousLesBoutons() {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (node instanceof Button) {
                Button btn = (Button) node;
                btn.setDisable(false);
                btn.setStyle("-fx-background-color: lightgrey;");
            }
        }
    }

    public void desactiverColonnesPleines() {
        for (int c = 0; c < Jeu.COLONNES; c++) {
            if (jeu.estGrillePleineColonne(c)) {
                for (javafx.scene.Node node : gridPane.getChildren()) {
                    Integer col = GridPane.getColumnIndex(node);
                    if (col != null && col == c && node instanceof Button) {
                        ((Button) node).setDisable(true);
                    }
                }
            }
        }
    }
}
