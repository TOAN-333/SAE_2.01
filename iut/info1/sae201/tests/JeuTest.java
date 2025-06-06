package iut.info1.sae201.tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import iut.info1.sae201.modele.Jeu;

/**
 * Tests unitaires pour la classe Jeu.
 */
public class JeuTest {

    private Jeu jeu;

    /**
     * Initialisation d'une variable jeu de Type Jeu
     * avant chaque test.
     */
    @Before
    public void setUp() {
        jeu = new Jeu();
    }

    /**
     * Teste l'initialisation de la grille de jeu.
     */
    @Test
    public void testInitialiserGrille() {
        jeu.initialiserGrille();
        String[][] grille = jeu.getGrille();

        // Vérifie que toutes les cases sont à "."
        for (int i = 0; i < Jeu.LIGNES; i++) {
            for (int j = 0; j < Jeu.COLONNES; j++) {
                assertEquals(".", grille[i][j]);
            }
        }

        // Vérifie que c'est au joueur rouge de jouer
        assertTrue(jeu.getRougeJoue());

        // Partie non terminée
        assertFalse(jeu.getPartieTerminee());
    }

    /**
     * Teste le placement d'un jeton dans la grille.
     */
    @Test
    public void testPlacerJeton() {
        int lignePlacee = jeu.placerJeton(1);
        // doit se placer en bas
        assertEquals(Jeu.LIGNES - 1, lignePlacee);

        // Vérifie que la case correspondante contient 
        // "R" (joueur rouge commence)
        assertEquals("R", jeu.getGrille()[lignePlacee][0]);
    }

    /**
     * Teste le placement d'un jeton dans une colonne pleine.
     */
    @Test
    public void testPlacerJetonColonnePleine() {
        // Remplir une colonne complètement
        for (int i = 0; i < Jeu.LIGNES; i++) {
            int ligne = jeu.placerJeton(1);
            assertNotEquals(-1, ligne);
            // Alterner joueur pour ne pas bloquer
            jeu.setRougeJoue(!jeu.getRougeJoue());
        }
        // Maintenant la colonne 1 est pleine, placer
        // un jeton doit renvoyer -1
        assertEquals(-1, jeu.placerJeton(1));
    }

    /**
     * Teste la vérification de victoire horizontale.
     */
    @Test
    public void testVerifierVictoireHorizontal() {
        // Place 4 jetons rouges alignés horizontalement
    	// sur la dernière ligne
        String[][] grille = new String[Jeu.LIGNES][Jeu.COLONNES];
        for (int i = 0; i < Jeu.LIGNES; i++) {
            for (int j = 0; j < Jeu.COLONNES; j++) {
                grille[i][j] = ".";
            }
        }
        int ligne = Jeu.LIGNES - 1;
        for (int col = 0; col < 4; col++) {
            grille[ligne][col] = "R";
        }
        jeu.setGrille(grille);

        // Le dernier jeton placé est à la colonne 3 (0-based)
        assertTrue(jeu.verifierVictoire(ligne, 3));
    }

    /**
     * Teste si la grille est pleine.
     */
    @Test
    public void testEstGrillePleine() {
        // Grille vide -> pas pleine
        assertFalse(jeu.estGrillePleine());

        // Remplir la première ligne 
        // (donc le haut de toutes colonnes)
        String[][] grille = new String[Jeu.LIGNES][Jeu.COLONNES];
        for (int i = 0; i < Jeu.LIGNES; i++) {
            for (int j = 0; j < Jeu.COLONNES; j++) {
                grille[i][j] = "R"; // Remplit toute la grille
            }
        }
        jeu.setGrille(grille);
        assertTrue(jeu.estGrillePleine());
    }
}