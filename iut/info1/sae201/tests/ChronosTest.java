package iut.info1.sae201.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import iut.info1.sae201.modele.Chronos;

/**
 * Tests unitaires pour la classe Chronos.
 */
public class ChronosTest {

    private Chronos chronos;

    /**
     * Initialisation un chronomètre avant chaque test.
     */
    @BeforeEach
    public void setUp() {
        chronos = new Chronos();
    }

    /**
     * Test pour vérifier que le chronomètre de la partie 
     * démarre correctement.
     * @throws InterruptedException si l'attente est interrompue
     */
    @Test
    public void testDemarrerChronoPartie_incrementeSecondes() 
    		throws InterruptedException {
        chronos.demarrerChronoPartie();
        int initial = chronos.getTempsPartie();

        // Attend 2 secondes pour vérifier l'incrémentation
        Thread.sleep(2100);

        int apres = chronos.getTempsPartie();
        assertTrue(apres >= initial + 2, "Le chrono partie doit augmenter "
                    + "d'au moins 2 secondes");
        
        chronos.arreterTousLesChronos();
    }

    /**
     * Test pour vérifier que le compte à rebours du joueur 1
     * s'écoule correctement.
     * @throws InterruptedException si l'attente est interrompue
     */
    @Test
    public void testDemarrerChronoJoueur1_compteARebours()
    		throws InterruptedException {
        chronos.demarrerChronoJoueur1(5);
        int initial = chronos.getTempsJoueur1();

        Thread.sleep(2100);

        int apres = chronos.getTempsJoueur1();
        assertTrue(apres <= initial - 2, "Le chrono joueur 1 doit "
                    + "décrémenter d'au moins 2 secondes");
        
        chronos.arreterChronoJoueur1();
    }

    /**
     * Test pour vérifier que le compte à rebours du joueur 2
     * s'écoule correctement.
     * @throws InterruptedException si l'attente est interrompue
     */
    @Test
    public void testDemarrerChronoJoueur2_compteARebours() 
    		throws InterruptedException {
        chronos.demarrerChronoJoueur2(5);
        int initial = chronos.getTempsJoueur2();

        Thread.sleep(2100);

        int apres = chronos.getTempsJoueur2();
        assertTrue(apres <= initial - 2, "Le chrono joueur 2 doit "
                    + "décrémenter d'au moins 2 secondes");
        
        chronos.arreterChronoJoueur2();
    }

    /**
     * Test pour vérifier que le listener de timeout est
     * appelé correctement.
     * @throws InterruptedException si l'attente est interrompue
     */
    @Test
    public void testTimeoutListenerCalled() throws InterruptedException {
        final boolean[] timeoutCalled = {false};
        chronos.setTimeoutListener(joueur -> {
            assertTrue(joueur == 1 || joueur == 2);
            timeoutCalled[0] = true;
        });

        chronos.demarrerChronoJoueur1(1);
        Thread.sleep(1500);

        assertTrue(timeoutCalled[0], "Le timeoutListener doit être appelé "
        		    + "pour joueur 1");
    }

    /**
     * Test pour vérifier que le chronomètre est
     * réinitialisé correctement.
     */
    @Test
    public void testReset_reinitialiseChronos() {
        chronos.demarrerChronoJoueur1(10);
        chronos.demarrerChronoJoueur2(10);
        chronos.demarrerChronoPartie();

        chronos.reset();

        assertEquals(19, chronos.getTempsJoueur1(), "Après reset, joueur1 "
                                                    + "doit être à 19");
        assertEquals(19, chronos.getTempsJoueur2(), "Après reset, joueur2 "
                                                    + "doit être à 19");
        assertEquals(0, chronos.getTempsPartie(), "Après reset, partie doit "
                                                    + "être à 0");
    }

    /**
     * Test pour vérifier que le format du temps est correct.
     */
    @Test
    public void testFormater_formatCorrect() {
        // Utilisation de la méthode publique getTempsFormatJoueur1
    	// via setTempsJoueur1
        chronos.setTempsJoueur1(75);
        String temps = chronos.getTempsFormatJoueur1();
        assertEquals("01:15", temps);
    }

    /**
     * Test pour vérifier que le temps de la partie peut être
     * défini correctement.
     */
    @Test
    public void testSetTempsPartie_parseCorrect() {
        assertDoesNotThrow(() -> chronos.setTempsPartie("42"));
        assertEquals(42, chronos.getTempsPartie());
    }

    /**
     * Test pour vérifier que l'arrêt de tous les chronomètres annule
     * se fasse correctement.
     */
    @Test
    public void testArreterChronos_doitAnnulerTasks() {
        chronos.demarrerChronoJoueur1(5);
        chronos.demarrerChronoJoueur2(5);
        chronos.demarrerChronoPartie();

        assertDoesNotThrow(() -> chronos.arreterTousLesChronos());
    }
}