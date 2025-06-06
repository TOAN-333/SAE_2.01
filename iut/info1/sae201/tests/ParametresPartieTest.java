package iut.info1.sae201.tests;

import org.junit.jupiter.api.Test;

import iut.info1.sae201.modele.ParametresPartie;

/**
 * Tests unitaires pour la classe ParametresPartie.
 */
public class ParametresPartieTest {

    /**
     * Test du joueur 1.
     */
    @Test
    public void testJoueur1() {
        ParametresPartie.setJoueur1("Alice");
        assert ParametresPartie.getJoueur1().equals("Alice");
    }

    /**
     * Test du joueur 2.
     */
    @Test
    public void testJoueur2() {
        ParametresPartie.setJoueur2("Bob");
        assert ParametresPartie.getJoueur2().equals("Bob");
    }

    /**
     * Test du joueur qui commence la partie.
     */
    @Test
    public void testJoueurCommence() {
        ParametresPartie.setJoueurCommence("Bob");
        assert ParametresPartie.getJoueurCommence().equals("Bob");
    }

    /**
     * Test de l'importation de la partie.
     */
    @Test
    public void testImportationFalse() {
        ParametresPartie.setImportation(false);
        assert ParametresPartie.isImportation() == false;
    }

    /**
     * 
     */
    @Test
    public void testImportationTrue() {
        ParametresPartie.setImportation(true);
        assert ParametresPartie.isImportation() == true;
    }

    /**
     * Test de la grille importée.
     */
    @Test
    public void testGrilleImportee() {
        String[][] grille = {
            {"R", "J", null},
            {"J", "R", "J"},
            {null, null, "R"}
        };
        ParametresPartie.setGrilleImportee(grille);
        assert ParametresPartie.getGrilleImportee() == grille;
        assert ParametresPartie.getGrilleImportee()[0][0].equals("R");
        assert ParametresPartie.getGrilleImportee()[1][1].equals("R");
        assert ParametresPartie.getGrilleImportee()[2][2].equals("R");
    }
}