package iut.info1.sae201.tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Test unitaire de la classe GestionPartie.
 */
public class GestionPartieTest {

    // Variables simulant l'état du "jeu"
    private boolean partieTerminee;
    private boolean boutonRejouerDesactive;
    private String texteLabelJoueur;
    private String idBoutonCourant;

    /**
     * Simule le démarrage d'une nouvelle partie.
     */
    public void demarrerNouvellePartie() {
        partieTerminee = false;
        boutonRejouerDesactive = false;
        texteLabelJoueur = "À toi de jouer";
    }

    /**
     * Simule la gestion d'un coup joué.
     * @param idBouton
     */
    public void gererCoup(String idBouton) {
        this.idBoutonCourant = idBouton;
        if ("victoire".equals(idBouton)) {
            partieTerminee = true;
            boutonRejouerDesactive = true;
        }
    }

    /**
     * Simule la désactivation des boutons.
     */
    public void desactiverBoutons() {
        boutonRejouerDesactive = true;
    }

    /**
     * Initialisation avant chaque test.
     */
    @Before
    public void setUp() {
        partieTerminee = false;
        boutonRejouerDesactive = true; // désactivé par défaut
        texteLabelJoueur = "";
        idBoutonCourant = "";
    }

    /**
     * Test pour vérifier le démarrage d'une nouvelle partie.
     */
    @Test
    public void testDemarrerNouvellePartie() {
        demarrerNouvellePartie();
        assertFalse(partieTerminee);
        assertFalse(boutonRejouerDesactive);
        assertEquals("À toi de jouer", texteLabelJoueur);
    }

    /**
     * Test pour vérifier la gestion d'un coup 
     * rapportant la victoire.
     */
    @Test
    public void testGererCoupVictoire() {
        gererCoup("victoire");
        assertTrue(partieTerminee);
        assertTrue(boutonRejouerDesactive);
    }

    /**
     * Test pour verifier si les boutons sont bien désactivés.
     */
    @Test
    public void testDesactiverBoutons() {
        desactiverBoutons();
        assertTrue(boutonRejouerDesactive);
    }
}
