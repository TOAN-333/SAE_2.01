package iut.info1.sae201.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import iut.info1.sae201.modele.Chronos;
import iut.info1.sae201.modele.InitialisationPartie;
import iut.info1.sae201.modele.Jeu;
import iut.info1.sae201.modele.ParametresPartie;

// Interface correspondant au listener de timeout (supposée)
interface TimeoutListener {
    void onTimeout(int joueur);
}

/**
 *  Tests unitaires pour la classe InitialisationPartie.
 */
public class InitialisationPartieTest {

    private InitialisationPartie initialisation;
    private FakeJeu jeu;
    private FakeChronos chrono;

    // Fake Jeu minimal
    private static class FakeJeu extends Jeu {
        boolean rougeJoue;
        boolean partieTerminee = false;
        boolean grilleInitialisee = false;
        
        @Override
        public void initialiserGrille() {
            grilleInitialisee = true;
        }

        @Override
        public void setRougeJoue(boolean b) {
            rougeJoue = b;
        }

        @Override
        public boolean getRougeJoue() {
            return rougeJoue;
        }

        @Override
        public boolean getPartieTerminee() {
            return partieTerminee;
        }

        @Override
        public void setPartieTerminee(boolean b) {
            partieTerminee = b;
        }
    }

    // Fake Chronos minimal
    private static class FakeChronos extends Chronos {
        TimeoutListener timeoutListener;

        boolean resetCalled = false;
        boolean demarrerPartieCalled = false;
        boolean demarrerJ1Called = false;
        boolean demarrerJ2Called = false;

        @Override
        public void reset() {
            resetCalled = true;
        }

        @Override
        public void setTimeoutListener(TimeoutListener listener) {
            this.timeoutListener = listener;
        }

        @Override
        public void demarrerChronoPartie() {
            demarrerPartieCalled = true;
        }

        @Override
        public void demarrerChronoJoueur1(int temps) {
            demarrerJ1Called = true;
        }

        @Override
        public void demarrerChronoJoueur2(int temps) {
            demarrerJ2Called = true;
        }

    }

    /**
     * Initialisation d'une instance de InitialisationPartie, 
     * avant chaque test.
     */
    @Before
    public void setUp() {
        // Fixe les joueurs dans ParametresPartie
    	// (classe statique supposée)
        ParametresPartie.setJoueur1("Alice");
        ParametresPartie.setJoueur2("Bob");
        ParametresPartie.setJoueurCommence("Alice");

        jeu = new FakeJeu();
        chrono = new FakeChronos();

        initialisation = new InitialisationPartie(jeu, chrono);
    }

    /**
     * Test du getters pour le pseudo du joueur 1.
     */
    @Test
    public void testGetPseudoJ1() {
        assertEquals("Alice", initialisation.getPseudoJ1());
    }

    
    /**
     * Test du getters pour le pseudo du joueur 2.
     */
    @Test
    public void testGetPseudoJ2() {
        assertEquals("Bob", initialisation.getPseudoJ2());
    }

    /**
     * Test du début et de l'initialisation de la grille
     * et du chronos du joueur1.
     */
    @Test
    public void testStartInitialiseGrilleEtChronosPourJoueur1() {
        initialisation.start();

        assertTrue(jeu.grilleInitialisee);
        // joueur commence = Alice => rouge joue
        assertTrue(jeu.rougeJoue); 
        assertTrue(chrono.resetCalled);
        assertTrue(chrono.demarrerPartieCalled);
        assertTrue(chrono.demarrerJ1Called);
        assertFalse(chrono.demarrerJ2Called);
    }

    /**
     * Test du début et de l'initialisation de la grille 
     * et des chronos du joueur2.
     */
    @Test
    public void testStartInitialiseGrilleEtChronosPourJoueur2() {
        // Change joueur qui commence
        ParametresPartie.setJoueurCommence("Bob");
        initialisation.start();

        assertTrue(jeu.grilleInitialisee);
        // joueur commence = Bob => rouge ne joue pas
        assertFalse(jeu.rougeJoue); 

        assertTrue(chrono.resetCalled);
        assertTrue(chrono.demarrerPartieCalled);
        assertFalse(chrono.demarrerJ1Called);
        assertTrue(chrono.demarrerJ2Called);
    }

    /**
     * Test pour vérifier que la partie n'est pas terminée.
     */
    @Test
    public void testTimerFindeParti() {
        initialisation.start();

        // Partie non terminée au départ
        assertFalse(jeu.partieTerminee);
    }
}
