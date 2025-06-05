package iut.info1.sae201.modele;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import iut.info1.sae201.vue.EchangeurDeVue;
import iut.info1.sae201.vue.EnsembleDesVues;
import javafx.stage.FileChooser;

public class Fichier {

	/**
     * Sauvegarde une partie dans un fichier texte.
     * Format :
     * joueur1;joueur2;quiCommence
     * ligne1 de la grille (ex : R . J . . . R)
     * ligne2 de la grille
     * ...
     */
    public static void sauvegarderPartie(Jeu jeu, String cheminFichier) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cheminFichier))) {
            // Ligne info joueurs
            String ligneInfo = ParametresPartie.getJoueur1() + ";" +
                               ParametresPartie.getJoueur2() + ";" +
                               ParametresPartie.getJoueurCommence();
            writer.write(ligneInfo);
            writer.newLine();

            // Ecrire la grille
            String[][] grille = jeu.getGrille();
            for (int i = 0; i < Jeu.LIGNES; i++) {
                StringBuilder ligne = new StringBuilder();
                for (int j = 0; j < Jeu.COLONNES; j++) {
                    ligne.append(grille[i][j]);
                    if (j < Jeu.COLONNES - 1) ligne.append(" ");
                }
                writer.write(ligne.toString());
                writer.newLine();
            }
        }
    }

    /**
     * Charge une partie depuis un fichier texte au format décrit.
     * Retourne un objet Jeu initialisé.
     */
    public static void exporterPartie(Jeu jeu, File fichier) throws IOException {
        List<String> lignes = new ArrayList<>();

        lignes.add("Joueur1: " + ParametresPartie.getJoueur1());
        lignes.add("Joueur2: " + ParametresPartie.getJoueur2());
        lignes.add("Grille:");

        for (int i = 0; i < Jeu.LIGNES; i++) {
            StringBuilder ligneGrille = new StringBuilder();
            for (int j = 0; j < Jeu.COLONNES; j++) {
                ligneGrille.append(jeu.getGrille()[i][j]).append(" ");
            }
            lignes.add(ligneGrille.toString().trim());
        }

        Files.write(fichier.toPath(), lignes);
    }

    
    /**
     * Charge une partie depuis un fichier texte au format décrit.
     * Retourne un objet Jeu initialisé.
     */
    public void importerPartie() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importer une partie");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier texte", "*.txt"));

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            List<String> lines = Files.readAllLines(file.toPath());

            String joueur1 = null;
            String joueur2 = null;
            List<String[]> grilleTemp = new ArrayList<>();

            boolean inGrille = false;

            for (String line : lines) {
                line = line.trim();

                if (line.startsWith("Joueur1:")) {
                    joueur1 = line.substring("Joueur1:".length()).trim();
                } else if (line.startsWith("Joueur2:")) {
                    joueur2 = line.substring("Joueur2:".length()).trim();
                } else if (line.equals("Grille:")) {
                    inGrille = true;
                } else if (inGrille && !line.isEmpty()) {
                    grilleTemp.add(line.split("\\s+"));
                }
            }

            // Convertir List<String[]> en String[][]
            String[][] grille = new String[Jeu.LIGNES][Jeu.COLONNES];
            for (int i = 0; i < grilleTemp.size(); i++) {
                for (int j = 0; j < grilleTemp.get(i).length; j++) {
                    grille[i][j] = grilleTemp.get(i)[j];
                }
            }

            // Stocker dans ParametresPartie (ou dans ton modèle)
            ParametresPartie.setJoueur1(joueur1);
            ParametresPartie.setJoueur2(joueur2);
            ParametresPartie.setGrilleImportee(grille);
            ParametresPartie.setImportation(true);

            // Debug console
            System.out.println("Importation réussie :");
            System.out.println("Joueur 1 : " + joueur1);
            System.out.println("Joueur 2 : " + joueur2);
            System.out.println("Grille :");
            for (String[] row : grille) {
                System.out.println(String.join(" ", row));
            }

            // Maintenant, bascule vers la vue de jeu ou autre traitement...
            EchangeurDeVue.echangerAvec(EnsembleDesVues.VUE_PUISSANCE4);
        }
    }


    
    public static String toStringGrille(char[][] grille) {
        String resultat = "";
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[i].length; j++) {
                resultat += grille[i][j] + " ";
            }
            resultat += "\n";
        }
        return resultat;
    }
    
    private char[][] grille;
    
    public char[][] getGrille() {
		return this.grille;
    }
    
    public void setGrille(char[][] grille) {
		this.grille = grille;
    }

}
