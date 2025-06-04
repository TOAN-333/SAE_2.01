package iut.info1.sae201.modele;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

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
        // Exporte par exemple la grille et les infos des joueurs
        StringBuilder sb = new StringBuilder();
        sb.append("Joueur1: ").append(ParametresPartie.getJoueur1()).append("\n");
        sb.append("Joueur2: ").append(ParametresPartie.getJoueur2()).append("\n");
        sb.append("Grille:\n");

        for (int i = 0; i < Jeu.LIGNES; i++) {
            for (int j = 0; j < Jeu.COLONNES; j++) {
                sb.append(jeu.getGrille()[i][j]).append(" ");
            }
            sb.append("\n");
        }

        Files.writeString(fichier.toPath(), sb.toString());
    }
}
