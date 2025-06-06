/**
 * Fichier.java                30/06/2025
 * 
 * IUT de Rodez, 2024-2025, aucun copyright
 */
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

/**
 * Classe Fichier permet de sauvegarder, exporter et importer les données d'une partie (joueurs et grille)
 * dans un fichier texte. Elle facilite la persistance des données pour reprendre une partie plus tard.
 * 
 * @author Toan Hery
 * @author Enzo Dumas
 * @author Nathael Dalle
 * @author Thomas Bourgougnon
 */
public class Fichier {

    /**
     * Sauvegarde une partie dans un fichier texte.
     * Format :
     * <pre>
     * joueur1;joueur2;quiCommence
     * ligne1 de la grille (ex : R . J . . . R)
     * ligne2 de la grille
     * ...
     * </pre>
     *
     * @param jeu l'objet Jeu contenant la grille à sauvegarder
     * @param cheminFichier le chemin du fichier texte où sauvegarder
     * @throws IOException en cas d'erreur d'écriture
     */
    public static void sauvegarderPartie(Jeu jeu, String cheminFichier) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cheminFichier))) {
            // Informations sur les joueurs
            String ligneInfo = ParametresPartie.getJoueur1() + ";" +
                               ParametresPartie.getJoueur2() + ";" +
                               ParametresPartie.getJoueurCommence();
            writer.write(ligneInfo);
            writer.newLine();

            // Écriture de la grille ligne par ligne
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
     * Exporte une partie dans un fichier texte, en l’enregistrant dans un format lisible.
     * Format :
     * <pre>
     * Joueur1: nom
     * Joueur2: nom
     * Grille:
     * R . J . . . R
     * ...
     * </pre>
     *
     * @param jeu     l’objet Jeu à exporter
     * @param fichier le fichier de destination
     * @throws IOException si une erreur d’écriture survient
     */
    public static void exporterPartie(Jeu jeu, File fichier) throws IOException {
        List<String> lignes = new ArrayList<>();

        lignes.add("Joueur1: " + ParametresPartie.getJoueur1());
        lignes.add("Joueur2: " + ParametresPartie.getJoueur2());
        lignes.add("Grille:");

        // Ajout des lignes de la grille
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
     * Permet à l’utilisateur d’importer une partie depuis un fichier texte choisi manuellement.
     * Les données extraites sont stockées dans ParametresPartie.
     * @throws IOException en cas d’erreur de lecture
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
            String duree = null; // non utilisée ici
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

            // Conversion vers tableau 2D
            String[][] grille = new String[Jeu.LIGNES][Jeu.COLONNES];
            for (int i = 0; i < grilleTemp.size(); i++) {
                for (int j = 0; j < grilleTemp.get(i).length; j++) {
                    grille[i][j] = grilleTemp.get(i)[j];
                }
            }

            // Stockage dans les paramètres de partie
            ParametresPartie.setJoueur1(joueur1);
            ParametresPartie.setJoueur2(joueur2);
            ParametresPartie.setGrilleImportee(grille);
            ParametresPartie.setImportation(true);

            // Debug console
            System.out.println("Importation réussie :");
            System.out.println("Joueur 1 : " + joueur1);
            System.out.println("Joueur 2 : " + joueur2);
            System.out.println("Duree Partie : " + duree);
            System.out.println("Grille :");
            for (String[] row : grille) {
                System.out.println(String.join(" ", row));
            }

            // Passage automatique à la vue du jeu
            EchangeurDeVue.echangerAvec(EnsembleDesVues.VUE_PUISSANCE4);
        }
    }

    /**
     * Transforme une grille de type char[][] en une chaîne lisible avec sauts de ligne.
     *
     * @param grille grille du jeu à convertir
     * @return chaîne de caractères représentant la grille
     */
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

    /**
     * @return la grille actuellement stockée dans cet objet
     */
    public char[][] getGrille() {
        return this.grille;
    }

    /**
     * Modifie la grille stockée dans cet objet.
     * @param grille la nouvelle grille
     */
    public void setGrille(char[][] grille) {
        this.grille = grille;
    }
}
